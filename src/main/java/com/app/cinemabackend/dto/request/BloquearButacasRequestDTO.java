package com.app.cinemabackend.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BloquearButacasRequestDTO {

    @NotNull(message = "El ID de la función es obligatorio")
    private Long idFuncion;

    @NotEmpty(message = "Debe seleccionar al menos una butaca")
    private List<Long> butacaIds;

    public BloquearButacasRequestDTO() {
    }

    public BloquearButacasRequestDTO(Long idFuncion, List<Long> butacaIds) {
        this.idFuncion = idFuncion;
        this.butacaIds = butacaIds;
    }

    public static BloquearButacasRequestDTOBuilder builder() {
        return new BloquearButacasRequestDTOBuilder();
    }

    public static class BloquearButacasRequestDTOBuilder {
        private Long idFuncion;
        private List<Long> butacaIds;

        public BloquearButacasRequestDTOBuilder idFuncion(Long idFuncion) {
            this.idFuncion = idFuncion;
            return this;
        }

        public BloquearButacasRequestDTOBuilder butacaIds(List<Long> butacaIds) {
            this.butacaIds = butacaIds;
            return this;
        }

        public BloquearButacasRequestDTO build() {
            return new BloquearButacasRequestDTO(idFuncion, butacaIds);
        }
    }
}
