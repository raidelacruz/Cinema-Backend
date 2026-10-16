package com.app.cinemabackend.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class FuncionHorarioRequestDTO {

    @NotNull(message = "El ID de la película es obligatorio")
    private Long idPelicula;

    @NotNull(message = "El ID de la sala es obligatorio")
    private Long idSala;

    @NotNull(message = "La fecha y hora de inicio es obligatoria")
    private OffsetDateTime fechaHoraInicio;

    private OffsetDateTime fechaHoraFin;

    private String formato;

    private String version;

    @NotNull(message = "El precio base es obligatorio")
    @Positive(message = "El precio debe ser positivo")
    private BigDecimal precioBase;

    private String estado;

    public FuncionHorarioRequestDTO() {
    }

    public FuncionHorarioRequestDTO(Long idPelicula, Long idSala, OffsetDateTime fechaHoraInicio, OffsetDateTime fechaHoraFin, String formato, String version, BigDecimal precioBase, String estado) {
        this.idPelicula = idPelicula;
        this.idSala = idSala;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.formato = formato;
        this.version = version;
        this.precioBase = precioBase;
        this.estado = estado;
    }

    public static FuncionHorarioRequestDTOBuilder builder() {
        return new FuncionHorarioRequestDTOBuilder();
    }

    public static class FuncionHorarioRequestDTOBuilder {
        private Long idPelicula;
        private Long idSala;
        private OffsetDateTime fechaHoraInicio;
        private OffsetDateTime fechaHoraFin;
        private String formato;
        private String version;
        private BigDecimal precioBase;
        private String estado;

        public FuncionHorarioRequestDTOBuilder idPelicula(Long idPelicula) {
            this.idPelicula = idPelicula;
            return this;
        }

        public FuncionHorarioRequestDTOBuilder idSala(Long idSala) {
            this.idSala = idSala;
            return this;
        }

        public FuncionHorarioRequestDTOBuilder fechaHoraInicio(OffsetDateTime fechaHoraInicio) {
            this.fechaHoraInicio = fechaHoraInicio;
            return this;
        }

        public FuncionHorarioRequestDTOBuilder fechaHoraFin(OffsetDateTime fechaHoraFin) {
            this.fechaHoraFin = fechaHoraFin;
            return this;
        }

        public FuncionHorarioRequestDTOBuilder formato(String formato) {
            this.formato = formato;
            return this;
        }

        public FuncionHorarioRequestDTOBuilder version(String version) {
            this.version = version;
            return this;
        }

        public FuncionHorarioRequestDTOBuilder precioBase(BigDecimal precioBase) {
            this.precioBase = precioBase;
            return this;
        }

        public FuncionHorarioRequestDTOBuilder estado(String estado) {
            this.estado = estado;
            return this;
        }

        public FuncionHorarioRequestDTO build() {
            return new FuncionHorarioRequestDTO(idPelicula, idSala, fechaHoraInicio, fechaHoraFin, formato, version, precioBase, estado);
        }
    }
}
