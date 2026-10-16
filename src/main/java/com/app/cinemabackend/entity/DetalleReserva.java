package com.app.cinemabackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_reserva")
@Getter
@Setter
public class DetalleReserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long idDetalle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reserva", nullable = false)
    private Reserva reserva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_butaca")
    private Butaca butaca;

    @Column(name = "tipo_item") // BUTACA, DULCERIA
    private String tipoItem;

    @Column(name = "descripcion_item")
    private String descripcionItem;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precio_unitario", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioUnitario;

    @Column(name = "subtotal", precision = 10, scale = 2, nullable = false)
    private BigDecimal subtotal;

    public DetalleReserva() {
    }

    public DetalleReserva(Long idDetalle, Reserva reserva, Butaca butaca, String tipoItem, String descripcionItem, Integer cantidad, BigDecimal precioUnitario, BigDecimal subtotal) {
        this.idDetalle = idDetalle;
        this.reserva = reserva;
        this.butaca = butaca;
        this.tipoItem = tipoItem;
        this.descripcionItem = descripcionItem;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public static DetalleReservaBuilder builder() {
        return new DetalleReservaBuilder();
    }

    public static class DetalleReservaBuilder {
        private Long idDetalle;
        private Reserva reserva;
        private Butaca butaca;
        private String tipoItem;
        private String descripcionItem;
        private Integer cantidad;
        private BigDecimal precioUnitario;
        private BigDecimal subtotal;

        public DetalleReservaBuilder idDetalle(Long idDetalle) {
            this.idDetalle = idDetalle;
            return this;
        }

        public DetalleReservaBuilder reserva(Reserva reserva) {
            this.reserva = reserva;
            return this;
        }

        public DetalleReservaBuilder butaca(Butaca butaca) {
            this.butaca = butaca;
            return this;
        }

        public DetalleReservaBuilder tipoItem(String tipoItem) {
            this.tipoItem = tipoItem;
            return this;
        }

        public DetalleReservaBuilder descripcionItem(String descripcionItem) {
            this.descripcionItem = descripcionItem;
            return this;
        }

        public DetalleReservaBuilder cantidad(Integer cantidad) {
            this.cantidad = cantidad;
            return this;
        }

        public DetalleReservaBuilder precioUnitario(BigDecimal precioUnitario) {
            this.precioUnitario = precioUnitario;
            return this;
        }

        public DetalleReservaBuilder subtotal(BigDecimal subtotal) {
            this.subtotal = subtotal;
            return this;
        }

        public DetalleReserva build() {
            return new DetalleReserva(idDetalle, reserva, butaca, tipoItem, descripcionItem, cantidad, precioUnitario, subtotal);
        }
    }
}
