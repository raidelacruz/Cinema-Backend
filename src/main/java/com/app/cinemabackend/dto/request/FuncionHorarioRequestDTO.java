package com.app.cinemabackend.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FuncionHorarioRequestDTO {

    @NotNull(message = "El ID de la película es obligatorio")
    private Long idPelicula;

    @NotNull(message = "El ID de la sala es obligatorio")
    private Long idSala;

    @NotNull(message = "La fecha y hora de inicio es obligatoria")
    private OffsetDateTime fechaHoraInicio;

    private OffsetDateTime fechaHoraFin;

    private String formato; // 2D, 3D, IMAX, VIP

    private String version; // DOBLADA, SUBTITULADA

    @NotNull(message = "El precio base es obligatorio")
    @Positive(message = "El precio debe ser positivo")
    private BigDecimal precioBase;

    private String estado;
}
