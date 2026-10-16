package com.app.cinemabackend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "pelicula")
public class Pelicula {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pelicula")
    private Long idPelicula;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String sinopsis;

    @Column(name = "duracion_minutos", nullable = false)
    private Integer duracionMinutos;

    @Column(length = 20)
    private String clasificacion;

    @Column(length = 60)
    private String genero;

    @Column(name = "poster_url", columnDefinition = "TEXT")
    private String posterUrl;

    @Column(nullable = false, length = 20)
    private String estado;

    // Constructores vacíos para JPA
    public Pelicula() {}

    // Getters y Setters manuales (Reemplazan a @Data)
    public Long getIdPelicula() { return idPelicula; }
    public void setIdPelicula(Long idPelicula) { this.idPelicula = idPelicula; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getSinopsis() { return sinopsis; }
    public void setSinopsis(String sinopsis) { this.sinopsis = sinopsis; }

    public Integer getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(Integer duracionMinutos) { this.duracionMinutos = duracionMinutos; }

    public String getClasificacion() { return clasificacion; }
    public void setClasificacion(String clasificacion) { this.clasificacion = clasificacion; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}