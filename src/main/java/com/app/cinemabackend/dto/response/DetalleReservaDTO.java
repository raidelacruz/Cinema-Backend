package com.app.cinemabackend.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DetalleReservaDTO {

    private String tipoItem;
    private String descripcion;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    public DetalleReservaDTO() {
    }

    public DetalleReservaDTO(String tipoItem, String descripcion, Integer cantidad, BigDecimal precioUnitario, BigDecimal subtotal) {
        this.tipoItem = tipoItem;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public static DetalleReservaDTOBuilder builder() {
        return new DetalleReservaDTOBuilder();
    }

    public static class DetalleReservaDTOBuilder {
        private String tipoItem;
        private String descripcion;
        private Integer cantidad;
        private BigDecimal precioUnitario;
        private BigDecimal subtotal;

        public DetalleReservaDTOBuilder tipoItem(String tipoItem) {
            this.tipoItem = tipoItem;
            return this;
        }

        public DetalleReservaDTOBuilder descripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public DetalleReservaDTOBuilder cantidad(Integer cantidad) {
            this.cantidad = cantidad;
            return this;
        }

        public DetalleReservaDTOBuilder precioUnitario(BigDecimal precioUnitario) {
            this.precioUnitario = precioUnitario;
            return this;
        }

        public DetalleReservaDTOBuilder subtotal(BigDecimal subtotal) {
            this.subtotal = subtotal;
            return this;
        }

        public DetalleReservaDTO build() {
            return new DetalleReservaDTO(tipoItem, descripcion, cantidad, precioUnitario, subtotal);
        }
    }
}
