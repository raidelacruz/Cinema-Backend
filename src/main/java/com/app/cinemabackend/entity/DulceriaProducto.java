package com.app.cinemabackend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "dulceria_producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DulceriaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "precio", precision = 10, scale = 2, nullable = false)
    private BigDecimal precio;

    @Column(name = "categoria")
    private String categoria; // COMBO, PALOMITAS, BEBIDAS, DULCES

    @Column(name = "imagen_url", columnDefinition = "TEXT")
    private String imagenUrl;

    @Column(name = "disponible")
    @Builder.Default
    private Boolean disponible = true;
}
