package com.app.cinemabackend.dto.response;

import lombok.*;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FuncionButacaDTO {

    private Long idFuncionButaca;
    private Long idButaca;
    private String fila;
    private Integer numero;
    private String codigoButaca; // "A1", "A2", etc.
    private String tipoButaca;
    private String estadoDisponibilidad; // DISPONIBLE, OCUPADA, BLOQUEADA
    private OffsetDateTime bloqueadoHasta;
    private boolean bloqueadaExpirada;
}
