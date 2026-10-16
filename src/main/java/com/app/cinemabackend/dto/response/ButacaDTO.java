package com.app.cinemabackend.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ButacaDTO {

    private Long idButaca;
    private Long idSala;
    private String fila;
    private Integer numero;
    private String tipoButaca;
    private String estadoOperativo;
    private String codigoButaca; // e.g. "A1", "B5"
}
