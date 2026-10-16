package com.app.cinemabackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeliculaRequestDTO {

    @NotBlank(message = "El título de la película es obligatorio")
    private String titulo;

    private String sinopsis;

    @NotNull(message = "La duración en minutos es obligatoria")
    @Positive(message = "La duración debe ser mayor a cero")
    private Integer duracionMinutos;

    private String clasificacion;

    @NotBlank(message = "El género es obligatorio")
    private String genero;

    private String posterUrl;

    private String estado;
}
