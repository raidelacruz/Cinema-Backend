package com.app.cinemabackend.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class FuncionHorarioDTO {

    private Long idFuncion;
    private Long idPelicula;
    private String tituloPelicula;
    private String posterUrl;
    private Long idSala;
    private String nombreSala;
    private OffsetDateTime fechaHoraInicio;
    private OffsetDateTime fechaHoraFin;
    private String formato;
    private String version;
    private BigDecimal precioBase;
    private String estado;

    public FuncionHorarioDTO() {
    }

    public FuncionHorarioDTO(Long idFuncion, Long idPelicula, String tituloPelicula, String posterUrl, Long idSala, String nombreSala, OffsetDateTime fechaHoraInicio, OffsetDateTime fechaHoraFin, String formato, String version, BigDecimal precioBase, String estado) {
        this.idFuncion = idFuncion;
        this.idPelicula = idPelicula;
        this.tituloPelicula = tituloPelicula;
        this.posterUrl = posterUrl;
        this.idSala = idSala;
        this.nombreSala = nombreSala;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.formato = formato;
        this.version = version;
        this.precioBase = precioBase;
        this.estado = estado;
    }

    public static FuncionHorarioDTOBuilder builder() {
        return new FuncionHorarioDTOBuilder();
    }

    public static class FuncionHorarioDTOBuilder {
        private Long idFuncion;
        private Long idPelicula;
        private String tituloPelicula;
        private String posterUrl;
        private Long idSala;
        private String nombreSala;
        private OffsetDateTime fechaHoraInicio;
        private OffsetDateTime fechaHoraFin;
        private String formato;
        private String version;
        private BigDecimal precioBase;
        private String estado;

        public FuncionHorarioDTOBuilder idFuncion(Long idFuncion) {
            this.idFuncion = idFuncion;
            return this;
        }

        public FuncionHorarioDTOBuilder idPelicula(Long idPelicula) {
            this.idPelicula = idPelicula;
            return this;
        }

        public FuncionHorarioDTOBuilder tituloPelicula(String tituloPelicula) {
            this.tituloPelicula = tituloPelicula;
            return this;
        }

        public FuncionHorarioDTOBuilder posterUrl(String posterUrl) {
            this.posterUrl = posterUrl;
            return this;
        }

        public FuncionHorarioDTOBuilder idSala(Long idSala) {
            this.idSala = idSala;
            return this;
        }

        public FuncionHorarioDTOBuilder nombreSala(String nombreSala) {
            this.nombreSala = nombreSala;
            return this;
        }

        public FuncionHorarioDTOBuilder fechaHoraInicio(OffsetDateTime fechaHoraInicio) {
            this.fechaHoraInicio = fechaHoraInicio;
            return this;
        }

        public FuncionHorarioDTOBuilder fechaHoraFin(OffsetDateTime fechaHoraFin) {
            this.fechaHoraFin = fechaHoraFin;
            return this;
        }

        public FuncionHorarioDTOBuilder formato(String formato) {
            this.formato = formato;
            return this;
        }

        public FuncionHorarioDTOBuilder version(String version) {
            this.version = version;
            return this;
        }

        public FuncionHorarioDTOBuilder precioBase(BigDecimal precioBase) {
            this.precioBase = precioBase;
            return this;
        }

        public FuncionHorarioDTOBuilder estado(String estado) {
            this.estado = estado;
            return this;
        }

        public FuncionHorarioDTO build() {
            return new FuncionHorarioDTO(idFuncion, idPelicula, tituloPelicula, posterUrl, idSala, nombreSala, fechaHoraInicio, fechaHoraFin, formato, version, precioBase, estado);
        }
    }
}
