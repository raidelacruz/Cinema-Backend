package com.app.cinemabackend.service.impl;

import com.app.cinemabackend.config.JwtService;
import com.app.cinemabackend.dto.request.LoginRequestDTO;
import com.app.cinemabackend.dto.request.RegisterRequestDTO;
import com.app.cinemabackend.dto.response.AuthResponseDTO;
import com.app.cinemabackend.entity.Usuario;
import com.app.cinemabackend.repository.UsuarioRepository;
import com.app.cinemabackend.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UsuarioRepository usuarioRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        Usuario usuario = usuarioRepository.findByCorreoElectronico(request.getCorreoElectronico())
                .orElseThrow(() -> new RuntimeException("Credenciales incorrectas"));

        if (!"ACTIVO".equals(usuario.getEstado())) {
            throw new RuntimeException("El usuario está inactivo");
        }

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPasswordHash())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        String token = jwtService.generateToken(usuario.getCorreoElectronico(), usuario.getRol());

        return new AuthResponseDTO(
                token,
                usuario.getIdUsuario(),
                usuario.getNombres(),
                usuario.getApellidos(),
                usuario.getCorreoElectronico(),
                usuario.getRol()
        );
    }

    @Override
    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (usuarioRepository.existsByCorreoElectronico(request.getCorreoElectronico())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Usuario nuevo = new Usuario();
        nuevo.setNombres(request.getNombres());
        nuevo.setApellidos(request.getApellidos());
        nuevo.setCorreoElectronico(request.getCorreoElectronico());
        nuevo.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        nuevo.setTelefono(request.getTelefono());
        nuevo.setRol("CLIENTE");
        nuevo.setEstado("ACTIVO");

        Usuario guardado = usuarioRepository.save(nuevo);
        String token = jwtService.generateToken(guardado.getCorreoElectronico(), guardado.getRol());

        return new AuthResponseDTO(
                token,
                guardado.getIdUsuario(),
                guardado.getNombres(),
                guardado.getApellidos(),
                guardado.getCorreoElectronico(),
                guardado.getRol()
        );
    }
}