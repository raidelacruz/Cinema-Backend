package com.app.cinemabackend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReservaRequestDTO {

    @NotNull(message = "El ID de la función es obligatorio")
    private Long idFuncion;

    @NotEmpty(message = "Debe incluir al menos una butaca")
    private List<Long> butacaIds;

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String clienteNombre;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El formato de correo no es válido")
    private String clienteEmail;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;

    private List<DulceriaItemRequestDTO> itemsDulceria = new ArrayList<>();

    public ReservaRequestDTO() {
    }

    public ReservaRequestDTO(Long idFuncion, List<Long> butacaIds, String clienteNombre, String clienteEmail, String metodoPago, List<DulceriaItemRequestDTO> itemsDulceria) {
        this.idFuncion = idFuncion;
        this.butacaIds = butacaIds;
        this.clienteNombre = clienteNombre;
        this.clienteEmail = clienteEmail;
        this.metodoPago = metodoPago;
        this.itemsDulceria = itemsDulceria != null ? itemsDulceria : new ArrayList<>();
    }

    public static ReservaRequestDTOBuilder builder() {
        return new ReservaRequestDTOBuilder();
    }

    public static class ReservaRequestDTOBuilder {
        private Long idFuncion;
        private List<Long> butacaIds;
        private String clienteNombre;
        private String clienteEmail;
        private String metodoPago;
        private List<DulceriaItemRequestDTO> itemsDulceria = new ArrayList<>();

        public ReservaRequestDTOBuilder idFuncion(Long idFuncion) {
            this.idFuncion = idFuncion;
            return this;
        }

        public ReservaRequestDTOBuilder butacaIds(List<Long> butacaIds) {
            this.butacaIds = butacaIds;
            return this;
        }

        public ReservaRequestDTOBuilder clienteNombre(String clienteNombre) {
            this.clienteNombre = clienteNombre;
            return this;
        }

        public ReservaRequestDTOBuilder clienteEmail(String clienteEmail) {
            this.clienteEmail = clienteEmail;
            return this;
        }

        public ReservaRequestDTOBuilder metodoPago(String metodoPago) {
            this.metodoPago = metodoPago;
            return this;
        }

        public ReservaRequestDTOBuilder itemsDulceria(List<DulceriaItemRequestDTO> itemsDulceria) {
            this.itemsDulceria = itemsDulceria;
            return this;
        }

        public ReservaRequestDTO build() {
            return new ReservaRequestDTO(idFuncion, butacaIds, clienteNombre, clienteEmail, metodoPago, itemsDulceria);
        }
    }
}
