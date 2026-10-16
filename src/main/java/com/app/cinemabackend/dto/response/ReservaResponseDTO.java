package com.app.cinemabackend.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class ReservaResponseDTO {

    private Long idReserva;
    private String codigoReserva;
    private String clienteNombre;
    private String clienteEmail;
    private String peliculaTitulo;
    private String salaNombre;
    private OffsetDateTime fechaHoraFuncion;
    private BigDecimal total;
    private String estado;
    private String metodoPago;
    private String codigoPickup;
    private String qrBase64;
    private OffsetDateTime fechaCreacion;
    private List<DetalleReservaDTO> detalles;

    public ReservaResponseDTO() {
    }

    public ReservaResponseDTO(Long idReserva, String codigoReserva, String clienteNombre, String clienteEmail, String peliculaTitulo, String salaNombre, OffsetDateTime fechaHoraFuncion, BigDecimal total, String estado, String metodoPago, String codigoPickup, String qrBase64, OffsetDateTime fechaCreacion, List<DetalleReservaDTO> detalles) {
        this.idReserva = idReserva;
        this.codigoReserva = codigoReserva;
        this.clienteNombre = clienteNombre;
        this.clienteEmail = clienteEmail;
        this.peliculaTitulo = peliculaTitulo;
        this.salaNombre = salaNombre;
        this.fechaHoraFuncion = fechaHoraFuncion;
        this.total = total;
        this.estado = estado;
        this.metodoPago = metodoPago;
        this.codigoPickup = codigoPickup;
        this.qrBase64 = qrBase64;
        this.fechaCreacion = fechaCreacion;
        this.detalles = detalles;
    }

    public static ReservaResponseDTOBuilder builder() {
        return new ReservaResponseDTOBuilder();
    }

    public static class ReservaResponseDTOBuilder {
        private Long idReserva;
        private String codigoReserva;
        private String clienteNombre;
        private String clienteEmail;
        private String peliculaTitulo;
        private String salaNombre;
        private OffsetDateTime fechaHoraFuncion;
        private BigDecimal total;
        private String estado;
        private String metodoPago;
        private String codigoPickup;
        private String qrBase64;
        private OffsetDateTime fechaCreacion;
        private List<DetalleReservaDTO> detalles;

        public ReservaResponseDTOBuilder idReserva(Long idReserva) {
            this.idReserva = idReserva;
            return this;
        }

        public ReservaResponseDTOBuilder codigoReserva(String codigoReserva) {
            this.codigoReserva = codigoReserva;
            return this;
        }

        public ReservaResponseDTOBuilder clienteNombre(String clienteNombre) {
            this.clienteNombre = clienteNombre;
            return this;
        }

        public ReservaResponseDTOBuilder clienteEmail(String clienteEmail) {
            this.clienteEmail = clienteEmail;
            return this;
        }

        public ReservaResponseDTOBuilder peliculaTitulo(String peliculaTitulo) {
            this.peliculaTitulo = peliculaTitulo;
            return this;
        }

        public ReservaResponseDTOBuilder salaNombre(String salaNombre) {
            this.salaNombre = salaNombre;
            return this;
        }

        public ReservaResponseDTOBuilder fechaHoraFuncion(OffsetDateTime fechaHoraFuncion) {
            this.fechaHoraFuncion = fechaHoraFuncion;
            return this;
        }

        public ReservaResponseDTOBuilder total(BigDecimal total) {
            this.total = total;
            return this;
        }

        public ReservaResponseDTOBuilder estado(String estado) {
            this.estado = estado;
            return this;
        }

        public ReservaResponseDTOBuilder metodoPago(String metodoPago) {
            this.metodoPago = metodoPago;
            return this;
        }

        public ReservaResponseDTOBuilder codigoPickup(String codigoPickup) {
            this.codigoPickup = codigoPickup;
            return this;
        }

        public ReservaResponseDTOBuilder qrBase64(String qrBase64) {
            this.qrBase64 = qrBase64;
            return this;
        }

        public ReservaResponseDTOBuilder fechaCreacion(OffsetDateTime fechaCreacion) {
            this.fechaCreacion = fechaCreacion;
            return this;
        }

        public ReservaResponseDTOBuilder detalles(List<DetalleReservaDTO> detalles) {
            this.detalles = detalles;
            return this;
        }

        public ReservaResponseDTO build() {
            return new ReservaResponseDTO(idReserva, codigoReserva, clienteNombre, clienteEmail, peliculaTitulo, salaNombre, fechaHoraFuncion, total, estado, metodoPago, codigoPickup, qrBase64, fechaCreacion, detalles);
        }
    }
}
