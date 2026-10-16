package com.app.cinemabackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "butaca")
@Getter
@Setter
public class Butaca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_butaca")
    private Long idButaca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sala", nullable = false)
    private Sala sala;

    @Column(name = "fila", nullable = false)
    private String fila;

    @Column(name = "numero", nullable = false)
    private Integer numero;

    @Column(name = "tipo_butaca")
    private String tipoButaca;

    @Column(name = "estado_operativo")
    private String estadoOperativo;

    public Butaca() {
    }

    public Butaca(Long idButaca, Sala sala, String fila, Integer numero, String tipoButaca, String estadoOperativo) {
        this.idButaca = idButaca;
        this.sala = sala;
        this.fila = fila;
        this.numero = numero;
        this.tipoButaca = tipoButaca;
        this.estadoOperativo = estadoOperativo;
    }

    public static ButacaBuilder builder() {
        return new ButacaBuilder();
    }

    public static class ButacaBuilder {
        private Long idButaca;
        private Sala sala;
        private String fila;
        private Integer numero;
        private String tipoButaca;
        private String estadoOperativo;

        public ButacaBuilder idButaca(Long idButaca) {
            this.idButaca = idButaca;
            return this;
        }

        public ButacaBuilder sala(Sala sala) {
            this.sala = sala;
            return this;
        }

        public ButacaBuilder fila(String fila) {
            this.fila = fila;
            return this;
        }

        public ButacaBuilder numero(Integer numero) {
            this.numero = numero;
            return this;
        }

        public ButacaBuilder tipoButaca(String tipoButaca) {
            this.tipoButaca = tipoButaca;
            return this;
        }

        public ButacaBuilder estadoOperativo(String estadoOperativo) {
            this.estadoOperativo = estadoOperativo;
            return this;
        }

        public Butaca build() {
            return new Butaca(idButaca, sala, fila, numero, tipoButaca, estadoOperativo);
        }
    }
}
