package com.app.cinemabackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pelicula")
@Getter
@Setter
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pelicula")
    private Long idPelicula;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "sinopsis", columnDefinition = "TEXT")
    private String sinopsis;

    @Column(name = "duracion_minutos")
    private Integer duracionMinutos;

    @Column(name = "clasificacion")
    private String clasificacion;

    @Column(name = "genero")
    private String genero;

    @Column(name = "poster_url", columnDefinition = "TEXT")
    private String posterUrl;

    @Column(name = "estado")
    private String estado;

    public Pelicula() {
    }

    public Pelicula(Long idPelicula, String titulo, String sinopsis, Integer duracionMinutos, String clasificacion, String genero, String posterUrl, String estado) {
        this.idPelicula = idPelicula;
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.duracionMinutos = duracionMinutos;
        this.clasificacion = clasificacion;
        this.genero = genero;
        this.posterUrl = posterUrl;
        this.estado = estado;
    }

    public static PeliculaBuilder builder() {
        return new PeliculaBuilder();
    }

    public static class PeliculaBuilder {
        private Long idPelicula;
        private String titulo;
        private String sinopsis;
        private Integer duracionMinutos;
        private String clasificacion;
        private String genero;
        private String posterUrl;
        private String estado;

        public PeliculaBuilder idPelicula(Long idPelicula) {
            this.idPelicula = idPelicula;
            return this;
        }

        public PeliculaBuilder titulo(String titulo) {
            this.titulo = titulo;
            return this;
        }

        public PeliculaBuilder sinopsis(String sinopsis) {
            this.sinopsis = sinopsis;
            return this;
        }

        public PeliculaBuilder duracionMinutos(Integer duracionMinutos) {
            this.duracionMinutos = duracionMinutos;
            return this;
        }

        public PeliculaBuilder clasificacion(String clasificacion) {
            this.clasificacion = clasificacion;
            return this;
        }

        public PeliculaBuilder genero(String genero) {
            this.genero = genero;
            return this;
        }

        public PeliculaBuilder posterUrl(String posterUrl) {
            this.posterUrl = posterUrl;
            return this;
        }

        public PeliculaBuilder estado(String estado) {
            this.estado = estado;
            return this;
        }

        public Pelicula build() {
            return new Pelicula(idPelicula, titulo, sinopsis, duracionMinutos, clasificacion, genero, posterUrl, estado);
        }
    }
}
