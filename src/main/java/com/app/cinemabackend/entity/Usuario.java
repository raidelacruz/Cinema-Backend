package com.app.cinemabackend.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(nullable = false, length = 80)
    private String nombres;

    @Column(nullable = false, length = 80)
    private String apellidos;

    @Column(name = "correo_electronico", nullable = false, unique = true, length = 200)
    private String correoElectronico;

    @Column(name = "password_hash", nullable = false, length = 200)
    private String passwordHash;

    @Column(length = 20)
    private String telefono;

    @Column(nullable = false, length = 20)
    private String rol = "CLIENTE";

    @Column(nullable = false, length = 20)
    private String estado = "ACTIVO";

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private OffsetDateTime fechaRegistro;

    @PrePersist
    protected void onCreate() {
        if (this.fechaRegistro == null) {
            this.fechaRegistro = OffsetDateTime.now();
        }
    }

    public Usuario() {}

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { 
        this.idUsuario = idUsuario; 
    }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { 
        this.nombres = nombres; 
    }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { 
        this.apellidos = apellidos; 
    }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico; 
    }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash; 
    }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) {
        this.telefono = telefono; 
    }

    public String getRol() { return rol; }
    public void setRol(String rol) { 
        this.rol = rol; 
    }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { 
        this.estado = estado; 
    }

    public OffsetDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(OffsetDateTime fechaRegistro) { 
        this.fechaRegistro = fechaRegistro; 
    }
}