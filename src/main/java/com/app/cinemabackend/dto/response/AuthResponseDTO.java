package com.app.cinemabackend.dto.response;

public class AuthResponseDTO {
    private String token;
    private Long idUsuario;
    private String nombres;
    private String apellidos;
    private String correoElectronico;
    private String rol;

    public AuthResponseDTO() {}

    public AuthResponseDTO(String token, Long idUsuario, String nombres, String apellidos, String correoElectronico, String rol) {
        this.token = token;
        this.idUsuario = idUsuario;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correoElectronico = correoElectronico;
        this.rol = rol;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}