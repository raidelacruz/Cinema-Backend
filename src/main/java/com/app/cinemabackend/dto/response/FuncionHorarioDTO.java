package com.app.cinemabackend.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FuncionHorarioDTO {

    private Long idFuncion;
    private Long idPelicula;
    private String tituloPelicula;
    private String posterUrl;
    private Long idSala;
    private String nombreSala;
    private OffsetDateTime fechaHoraInicio;
    private OffsetDateTime fechaHoraFin;
    private String formato;
    private String version;
    private BigDecimal precioBase;
    private String estado;
}
