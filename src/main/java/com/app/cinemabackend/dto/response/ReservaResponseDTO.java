package com.app.cinemabackend.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private String codigoPickup; // Recojo de dulceria (RF06)
    private String qrBase64;     // Imagen QR para acceso directo (RF08)
    private OffsetDateTime fechaCreacion;
    private List<DetalleReservaDTO> detalles;
}
