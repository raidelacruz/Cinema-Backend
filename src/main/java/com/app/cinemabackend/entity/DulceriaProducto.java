package com.app.cinemabackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "dulceria_producto")
@Getter
@Setter
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
    private Boolean disponible = true;

    public DulceriaProducto() {
    }

    public DulceriaProducto(Long idProducto, String nombre, String descripcion, BigDecimal precio, String categoria, String imagenUrl, Boolean disponible) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.imagenUrl = imagenUrl;
        this.disponible = disponible != null ? disponible : true;
    }

    public static DulceriaProductoBuilder builder() {
        return new DulceriaProductoBuilder();
    }

    public static class DulceriaProductoBuilder {
        private Long idProducto;
        private String nombre;
        private String descripcion;
        private BigDecimal precio;
        private String categoria;
        private String imagenUrl;
        private Boolean disponible = true;

        public DulceriaProductoBuilder idProducto(Long idProducto) {
            this.idProducto = idProducto;
            return this;
        }

        public DulceriaProductoBuilder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public DulceriaProductoBuilder descripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public DulceriaProductoBuilder precio(BigDecimal precio) {
            this.precio = precio;
            return this;
        }

        public DulceriaProductoBuilder categoria(String categoria) {
            this.categoria = categoria;
            return this;
        }

        public DulceriaProductoBuilder imagenUrl(String imagenUrl) {
            this.imagenUrl = imagenUrl;
            return this;
        }

        public DulceriaProductoBuilder disponible(Boolean disponible) {
            this.disponible = disponible;
            return this;
        }

        public DulceriaProducto build() {
            return new DulceriaProducto(idProducto, nombre, descripcion, precio, categoria, imagenUrl, disponible);
        }
    }
}
