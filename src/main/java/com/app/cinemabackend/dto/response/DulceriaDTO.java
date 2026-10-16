package com.app.cinemabackend.dto.response;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DulceriaDTO {

    private Long idProducto;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private String categoria;
    private String imagenUrl;
    private Boolean disponible;
}
