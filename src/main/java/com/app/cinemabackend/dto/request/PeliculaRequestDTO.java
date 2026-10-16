package com.app.cinemabackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeliculaRequestDTO {

    @NotBlank(message = "El título de la película es obligatorio")
    private String titulo;

    private String sinopsis;

    @NotNull(message = "La duración en minutos es obligatoria")
    @Positive(message = "La duración debe ser mayor a cero")
    private Integer duracionMinutos;

    private String clasificacion;

    @NotBlank(message = "El género es obligatorio")
    private String genero;

    private String posterUrl;

    private String estado;

    public PeliculaRequestDTO() {
    }

    public PeliculaRequestDTO(String titulo, String sinopsis, Integer duracionMinutos, String clasificacion, String genero, String posterUrl, String estado) {
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.duracionMinutos = duracionMinutos;
        this.clasificacion = clasificacion;
        this.genero = genero;
        this.posterUrl = posterUrl;
        this.estado = estado;
    }

    public static PeliculaRequestDTOBuilder builder() {
        return new PeliculaRequestDTOBuilder();
    }

    public static class PeliculaRequestDTOBuilder {
        private String titulo;
        private String sinopsis;
        private Integer duracionMinutos;
        private String clasificacion;
        private String genero;
        private String posterUrl;
        private String estado;

        public PeliculaRequestDTOBuilder titulo(String titulo) {
            this.titulo = titulo;
            return this;
        }

        public PeliculaRequestDTOBuilder sinopsis(String sinopsis) {
            this.sinopsis = sinopsis;
            return this;
        }

        public PeliculaRequestDTOBuilder duracionMinutos(Integer duracionMinutos) {
            this.duracionMinutos = duracionMinutos;
            return this;
        }

        public PeliculaRequestDTOBuilder clasificacion(String clasificacion) {
            this.clasificacion = clasificacion;
            return this;
        }

        public PeliculaRequestDTOBuilder genero(String genero) {
            this.genero = genero;
            return this;
        }

        public PeliculaRequestDTOBuilder posterUrl(String posterUrl) {
            this.posterUrl = posterUrl;
            return this;
        }

        public PeliculaRequestDTOBuilder estado(String estado) {
            this.estado = estado;
            return this;
        }

        public PeliculaRequestDTO build() {
            return new PeliculaRequestDTO(titulo, sinopsis, duracionMinutos, clasificacion, genero, posterUrl, estado);
        }
    }
}
