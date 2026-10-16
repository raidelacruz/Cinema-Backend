package com.app.cinemabackend.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BloquearButacasRequestDTO {

    @NotNull(message = "El ID de la función es obligatorio")
    private Long idFuncion;

    @NotEmpty(message = "Debe seleccionar al menos una butaca")
    private List<Long> butacaIds;
}
