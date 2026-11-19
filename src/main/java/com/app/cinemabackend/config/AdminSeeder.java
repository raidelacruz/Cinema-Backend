package com.app.cinemabackend.config;

import com.app.cinemabackend.entity.Usuario;
import com.app.cinemabackend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        String adminEmail = "admin@cinepolis.com";
        if (!usuarioRepository.existsByCorreoElectronico(adminEmail)) {
            Usuario admin = new Usuario();
            admin.setNombres("Admin");
            admin.setApellidos("Cinepolis");
            admin.setCorreoElectronico(adminEmail);
            admin.setPasswordHash(passwordEncoder.encode("admin123"));
            admin.setRol("ADMIN");
            admin.setEstado("ACTIVO");
            usuarioRepository.save(admin);
            System.out.println(">>> Usuario ADMIN creado: " + adminEmail + " / admin123");
        }
    }
}