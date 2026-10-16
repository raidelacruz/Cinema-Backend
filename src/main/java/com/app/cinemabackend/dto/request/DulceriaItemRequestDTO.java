package com.app.cinemabackend.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DulceriaItemRequestDTO {

    @NotNull(message = "El ID del producto es obligatorio")
    private Long idProducto;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    public DulceriaItemRequestDTO() {
    }

    public DulceriaItemRequestDTO(Long idProducto, Integer cantidad) {
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    public static DulceriaItemRequestDTOBuilder builder() {
        return new DulceriaItemRequestDTOBuilder();
    }

    public static class DulceriaItemRequestDTOBuilder {
        private Long idProducto;
        private Integer cantidad;

        public DulceriaItemRequestDTOBuilder idProducto(Long idProducto) {
            this.idProducto = idProducto;
            return this;
        }

        public DulceriaItemRequestDTOBuilder cantidad(Integer cantidad) {
            this.cantidad = cantidad;
            return this;
        }

        public DulceriaItemRequestDTO build() {
            return new DulceriaItemRequestDTO(idProducto, cantidad);
        }
    }
}
