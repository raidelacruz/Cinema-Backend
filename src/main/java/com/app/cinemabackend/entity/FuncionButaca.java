package com.app.cinemabackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;

@Entity
@Table(name = "funcion_butaca")
@Getter
@Setter
public class FuncionButaca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcion_butaca")
    private Long idFuncionButaca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcion", nullable = false)
    private FuncionHorario funcionHorario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_butaca", nullable = false)
    private Butaca butaca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sala")
    private Sala sala;

    @Column(name = "estado_disponibilidad", nullable = false)
    private String estadoDisponibilidad;

    @Column(name = "bloqueado_hasta")
    private OffsetDateTime bloqueadoHasta;

    public FuncionButaca() {
    }

    public FuncionButaca(Long idFuncionButaca, FuncionHorario funcionHorario, Butaca butaca, Sala sala, String estadoDisponibilidad, OffsetDateTime bloqueadoHasta) {
        this.idFuncionButaca = idFuncionButaca;
        this.funcionHorario = funcionHorario;
        this.butaca = butaca;
        this.sala = sala;
        this.estadoDisponibilidad = estadoDisponibilidad;
        this.bloqueadoHasta = bloqueadoHasta;
    }

    public static FuncionButacaBuilder builder() {
        return new FuncionButacaBuilder();
    }

    public static class FuncionButacaBuilder {
        private Long idFuncionButaca;
        private FuncionHorario funcionHorario;
        private Butaca butaca;
        private Sala sala;
        private String estadoDisponibilidad;
        private OffsetDateTime bloqueadoHasta;

        public FuncionButacaBuilder idFuncionButaca(Long idFuncionButaca) {
            this.idFuncionButaca = idFuncionButaca;
            return this;
        }

        public FuncionButacaBuilder funcionHorario(FuncionHorario funcionHorario) {
            this.funcionHorario = funcionHorario;
            return this;
        }

        public FuncionButacaBuilder butaca(Butaca butaca) {
            this.butaca = butaca;
            return this;
        }

        public FuncionButacaBuilder sala(Sala sala) {
            this.sala = sala;
            return this;
        }

        public FuncionButacaBuilder estadoDisponibilidad(String estadoDisponibilidad) {
            this.estadoDisponibilidad = estadoDisponibilidad;
            return this;
        }

        public FuncionButacaBuilder bloqueadoHasta(OffsetDateTime bloqueadoHasta) {
            this.bloqueadoHasta = bloqueadoHasta;
            return this;
        }

        public FuncionButaca build() {
            return new FuncionButaca(idFuncionButaca, funcionHorario, butaca, sala, estadoDisponibilidad, bloqueadoHasta);
        }
    }
}
