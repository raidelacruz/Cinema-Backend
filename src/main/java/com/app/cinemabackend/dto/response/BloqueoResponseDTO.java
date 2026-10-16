package com.app.cinemabackend.dto.response;

import lombok.*;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BloqueoResponseDTO {

    private boolean bloqueadoExitoso;
    private String mensaje;
    private Long idFuncion;
    private List<Long> butacasBloqueadas;
    private OffsetDateTime expiraEn;
    private long segundosRestantes;
}
