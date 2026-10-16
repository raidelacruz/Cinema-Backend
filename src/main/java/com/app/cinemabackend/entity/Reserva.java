package com.app.cinemabackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reserva")
@Getter
@Setter
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long idReserva;

    @Column(name = "codigo_reserva", unique = true, nullable = false)
    private String codigoReserva;

    @Column(name = "cliente_nombre", nullable = false)
    private String clienteNombre;

    @Column(name = "cliente_email", nullable = false)
    private String clienteEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcion", nullable = false)
    private FuncionHorario funcionHorario;

    @Column(name = "total", precision = 10, scale = 2, nullable = false)
    private BigDecimal total;

    @Column(name = "estado")
    private String estado; // CONFIRMADA, CANCELADA, PENDIENTE

    @Column(name = "codigo_qr", columnDefinition = "TEXT")
    private String codigoQr; // Imagen en base64 o texto firmado

    @Column(name = "codigo_pickup")
    private String codigoPickup; // Codigo de recojo rapido para dulceria

    @Column(name = "metodo_pago")
    private String metodoPago;

    @Column(name = "fecha_creacion")
    private OffsetDateTime fechaCreacion;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleReserva> detalles = new ArrayList<>();

    public Reserva() {
    }

    public Reserva(Long idReserva, String codigoReserva, String clienteNombre, String clienteEmail, FuncionHorario funcionHorario, BigDecimal total, String estado, String codigoQr, String codigoPickup, String metodoPago, OffsetDateTime fechaCreacion, List<DetalleReserva> detalles) {
        this.idReserva = idReserva;
        this.codigoReserva = codigoReserva;
        this.clienteNombre = clienteNombre;
        this.clienteEmail = clienteEmail;
        this.funcionHorario = funcionHorario;
        this.total = total;
        this.estado = estado;
        this.codigoQr = codigoQr;
        this.codigoPickup = codigoPickup;
        this.metodoPago = metodoPago;
        this.fechaCreacion = fechaCreacion;
        this.detalles = detalles != null ? detalles : new ArrayList<>();
    }

    public static ReservaBuilder builder() {
        return new ReservaBuilder();
    }

    public static class ReservaBuilder {
        private Long idReserva;
        private String codigoReserva;
        private String clienteNombre;
        private String clienteEmail;
        private FuncionHorario funcionHorario;
        private BigDecimal total;
        private String estado;
        private String codigoQr;
        private String codigoPickup;
        private String metodoPago;
        private OffsetDateTime fechaCreacion;
        private List<DetalleReserva> detalles = new ArrayList<>();

        public ReservaBuilder idReserva(Long idReserva) {
            this.idReserva = idReserva;
            return this;
        }

        public ReservaBuilder codigoReserva(String codigoReserva) {
            this.codigoReserva = codigoReserva;
            return this;
        }

        public ReservaBuilder clienteNombre(String clienteNombre) {
            this.clienteNombre = clienteNombre;
            return this;
        }

        public ReservaBuilder clienteEmail(String clienteEmail) {
            this.clienteEmail = clienteEmail;
            return this;
        }

        public ReservaBuilder funcionHorario(FuncionHorario funcionHorario) {
            this.funcionHorario = funcionHorario;
            return this;
        }

        public ReservaBuilder total(BigDecimal total) {
            this.total = total;
            return this;
        }

        public ReservaBuilder estado(String estado) {
            this.estado = estado;
            return this;
        }

        public ReservaBuilder codigoQr(String codigoQr) {
            this.codigoQr = codigoQr;
            return this;
        }

        public ReservaBuilder codigoPickup(String codigoPickup) {
            this.codigoPickup = codigoPickup;
            return this;
        }

        public ReservaBuilder metodoPago(String metodoPago) {
            this.metodoPago = metodoPago;
            return this;
        }

        public ReservaBuilder fechaCreacion(OffsetDateTime fechaCreacion) {
            this.fechaCreacion = fechaCreacion;
            return this;
        }

        public ReservaBuilder detalles(List<DetalleReserva> detalles) {
            this.detalles = detalles;
            return this;
        }

        public Reserva build() {
            return new Reserva(idReserva, codigoReserva, clienteNombre, clienteEmail, funcionHorario, total, estado, codigoQr, codigoPickup, metodoPago, fechaCreacion, detalles);
        }
    }
}
