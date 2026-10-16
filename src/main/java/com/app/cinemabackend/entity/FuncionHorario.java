package com.app.cinemabackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "funcion_horario")
@Getter
@Setter
public class FuncionHorario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcion")
    private Long idFuncion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pelicula", nullable = false)
    private Pelicula pelicula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sala", nullable = false)
    private Sala sala;

    @Column(name = "fecha_hora_inicio", nullable = false)
    private OffsetDateTime fechaHoraInicio;

    @Column(name = "fecha_hora_fin")
    private OffsetDateTime fechaHoraFin;

    @Column(name = "formato")
    private String formato;

    @Column(name = "version")
    private String version;

    @Column(name = "precio_base", precision = 10, scale = 2)
    private BigDecimal precioBase;

    @Column(name = "estado")
    private String estado;

    public FuncionHorario() {
    }

    public FuncionHorario(Long idFuncion, Pelicula pelicula, Sala sala, OffsetDateTime fechaHoraInicio, OffsetDateTime fechaHoraFin, String formato, String version, BigDecimal precioBase, String estado) {
        this.idFuncion = idFuncion;
        this.pelicula = pelicula;
        this.sala = sala;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.formato = formato;
        this.version = version;
        this.precioBase = precioBase;
        this.estado = estado;
    }

    public static FuncionHorarioBuilder builder() {
        return new FuncionHorarioBuilder();
    }

    public static class FuncionHorarioBuilder {
        private Long idFuncion;
        private Pelicula pelicula;
        private Sala sala;
        private OffsetDateTime fechaHoraInicio;
        private OffsetDateTime fechaHoraFin;
        private String formato;
        private String version;
        private BigDecimal precioBase;
        private String estado;

        public FuncionHorarioBuilder idFuncion(Long idFuncion) {
            this.idFuncion = idFuncion;
            return this;
        }

        public FuncionHorarioBuilder pelicula(Pelicula pelicula) {
            this.pelicula = pelicula;
            return this;
        }

        public FuncionHorarioBuilder sala(Sala sala) {
            this.sala = sala;
            return this;
        }

        public FuncionHorarioBuilder fechaHoraInicio(OffsetDateTime fechaHoraInicio) {
            this.fechaHoraInicio = fechaHoraInicio;
            return this;
        }

        public FuncionHorarioBuilder fechaHoraFin(OffsetDateTime fechaHoraFin) {
            this.fechaHoraFin = fechaHoraFin;
            return this;
        }

        public FuncionHorarioBuilder formato(String formato) {
            this.formato = formato;
            return this;
        }

        public FuncionHorarioBuilder version(String version) {
            this.version = version;
            return this;
        }

        public FuncionHorarioBuilder precioBase(BigDecimal precioBase) {
            this.precioBase = precioBase;
            return this;
        }

        public FuncionHorarioBuilder estado(String estado) {
            this.estado = estado;
            return this;
        }

        public FuncionHorario build() {
            return new FuncionHorario(idFuncion, pelicula, sala, fechaHoraInicio, fechaHoraFin, formato, version, precioBase, estado);
        }
    }
}
