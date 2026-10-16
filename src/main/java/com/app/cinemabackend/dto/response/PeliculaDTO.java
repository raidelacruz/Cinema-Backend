package com.app.cinemabackend.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeliculaDTO {

    private Long idPelicula;
    private String titulo;
    private String sinopsis;
    private Integer duracionMinutos;
    private String clasificacion;
    private String genero;
    private String posterUrl;
    private String estado;
}
