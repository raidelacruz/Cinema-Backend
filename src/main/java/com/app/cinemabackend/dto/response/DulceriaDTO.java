package com.app.cinemabackend.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DulceriaDTO {

    private Long idProducto;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private String categoria;
    private String imagenUrl;
    private Boolean disponible;

    public DulceriaDTO() {
    }

    public DulceriaDTO(Long idProducto, String nombre, String descripcion, BigDecimal precio, String categoria, String imagenUrl, Boolean disponible) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.imagenUrl = imagenUrl;
        this.disponible = disponible;
    }

    public static DulceriaDTOBuilder builder() {
        return new DulceriaDTOBuilder();
    }

    public static class DulceriaDTOBuilder {
        private Long idProducto;
        private String nombre;
        private String descripcion;
        private BigDecimal precio;
        private String categoria;
        private String imagenUrl;
        private Boolean disponible;

        public DulceriaDTOBuilder idProducto(Long idProducto) {
            this.idProducto = idProducto;
            return this;
        }

        public DulceriaDTOBuilder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public DulceriaDTOBuilder descripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public DulceriaDTOBuilder precio(BigDecimal precio) {
            this.precio = precio;
            return this;
        }

        public DulceriaDTOBuilder categoria(String categoria) {
            this.categoria = categoria;
            return this;
        }

        public DulceriaDTOBuilder imagenUrl(String imagenUrl) {
            this.imagenUrl = imagenUrl;
            return this;
        }

        public DulceriaDTOBuilder disponible(Boolean disponible) {
            this.disponible = disponible;
            return this;
        }

        public DulceriaDTO build() {
            return new DulceriaDTO(idProducto, nombre, descripcion, precio, categoria, imagenUrl, disponible);
        }
    }
}
