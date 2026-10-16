package com.app.cinemabackend.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeliculaDTO {

    private Long idPelicula;
    private String titulo;
    private String sinopsis;
    private Integer duracionMinutos;
    private String clasificacion;
    private String genero;
    private String posterUrl;
    private String estado;

    public PeliculaDTO() {
    }

    public PeliculaDTO(Long idPelicula, String titulo, String sinopsis, Integer duracionMinutos, String clasificacion, String genero, String posterUrl, String estado) {
        this.idPelicula = idPelicula;
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.duracionMinutos = duracionMinutos;
        this.clasificacion = clasificacion;
        this.genero = genero;
        this.posterUrl = posterUrl;
        this.estado = estado;
    }

    public static PeliculaDTOBuilder builder() {
        return new PeliculaDTOBuilder();
    }

    public static class PeliculaDTOBuilder {
        private Long idPelicula;
        private String titulo;
        private String sinopsis;
        private Integer duracionMinutos;
        private String clasificacion;
        private String genero;
        private String posterUrl;
        private String estado;

        public PeliculaDTOBuilder idPelicula(Long idPelicula) {
            this.idPelicula = idPelicula;
            return this;
        }

        public PeliculaDTOBuilder titulo(String titulo) {
            this.titulo = titulo;
            return this;
        }

        public PeliculaDTOBuilder sinopsis(String sinopsis) {
            this.sinopsis = sinopsis;
            return this;
        }

        public PeliculaDTOBuilder duracionMinutos(Integer duracionMinutos) {
            this.duracionMinutos = duracionMinutos;
            return this;
        }

        public PeliculaDTOBuilder clasificacion(String clasificacion) {
            this.clasificacion = clasificacion;
            return this;
        }

        public PeliculaDTOBuilder genero(String genero) {
            this.genero = genero;
            return this;
        }

        public PeliculaDTOBuilder posterUrl(String posterUrl) {
            this.posterUrl = posterUrl;
            return this;
        }

        public PeliculaDTOBuilder estado(String estado) {
            this.estado = estado;
            return this;
        }

        public PeliculaDTO build() {
            return new PeliculaDTO(idPelicula, titulo, sinopsis, duracionMinutos, clasificacion, genero, posterUrl, estado);
        }
    }
}
