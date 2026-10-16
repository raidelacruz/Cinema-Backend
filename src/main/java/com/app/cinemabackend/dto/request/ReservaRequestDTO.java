package com.app.cinemabackend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private String metodoPago; // TARJETA_CREDITO, TARJETA_DEBITO, PUNTOS_CLUB

    @Builder.Default
    private List<DulceriaItemRequestDTO> itemsDulceria = new ArrayList<>();
}
