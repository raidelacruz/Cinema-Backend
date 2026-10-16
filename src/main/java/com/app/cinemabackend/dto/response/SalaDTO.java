package com.app.cinemabackend.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaDTO {

    private Long idSala;
    private String nombreNumero;
    private String tipoSala;
    private String estado;
}
