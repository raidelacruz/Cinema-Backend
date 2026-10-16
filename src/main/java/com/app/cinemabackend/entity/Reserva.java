package com.app.cinemabackend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reserva")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @Builder.Default
    private List<DetalleReserva> detalles = new ArrayList<>();
}
