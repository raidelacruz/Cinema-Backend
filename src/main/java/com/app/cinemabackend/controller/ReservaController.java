package com.app.cinemabackend.controller;

import com.app.cinemabackend.dto.request.ReservaRequestDTO;
import com.app.cinemabackend.dto.response.ApiResponse;
import com.app.cinemabackend.dto.response.ReservaResponseDTO;
import com.app.cinemabackend.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@Tag(name = "Reservas y Pagos", description = "Endpoints para compra de boletos, pagos atómicos y boletos QR (RF07, RF08)")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    @Operation(summary = "Procesar pago y confirmar reserva generando boleto electrónico y código QR único (RF07, RF08)")
    public ResponseEntity<ApiResponse<ReservaResponseDTO>> procesarReserva(
            @Valid @RequestBody ReservaRequestDTO dto) {
        ReservaResponseDTO reserva = reservaService.procesarReserva(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(reserva, "¡Reserva y compra confirmada con éxito!"));
    }

    @GetMapping("/{codigo}")
    @Operation(summary = "Consultar detalle de una reserva por su código único")
    public ResponseEntity<ApiResponse<ReservaResponseDTO>> obtenerPorCodigo(
            @PathVariable("codigo") String codigo) {
        ReservaResponseDTO reserva = reservaService.obtenerPorCodigo(codigo);
        return ResponseEntity.ok(ApiResponse.ok(reserva));
    }

    @GetMapping("/historial")
    @Operation(summary = "Consultar el historial de compras y boletos de un cliente por email (RF11 Mis Reservas)")
    public ResponseEntity<ApiResponse<List<ReservaResponseDTO>>> obtenerHistorial(
            @RequestParam("email") String email) {
        List<ReservaResponseDTO> historial = reservaService.obtenerHistorialPorEmail(email);
        return ResponseEntity.ok(ApiResponse.ok(historial, "Historial de compras obtenido"));
    }
}
