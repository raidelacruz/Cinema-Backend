package com.app.cinemabackend.controller;

import com.app.cinemabackend.dto.request.BloquearButacasRequestDTO;
import com.app.cinemabackend.dto.request.FuncionHorarioRequestDTO;
import com.app.cinemabackend.dto.response.ApiResponse;
import com.app.cinemabackend.dto.response.BloqueoResponseDTO;
import com.app.cinemabackend.dto.response.FuncionButacaDTO;
import com.app.cinemabackend.dto.response.FuncionHorarioDTO;
import com.app.cinemabackend.service.FuncionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funciones")
@RequiredArgsConstructor
@Tag(name = "Funciones y Butacas", description = "Endpoints para horarios, mapa de asientos y concurrencia (RF04, RF05)")
public class FuncionController {

    private final FuncionService funcionService;

    @GetMapping("/pelicula/{idPelicula}")
    @Operation(summary = "Consultar horarios disponibles para una película específica")
    public ResponseEntity<ApiResponse<List<FuncionHorarioDTO>>> obtenerPorPelicula(@PathVariable("idPelicula") Long idPelicula) {
        List<FuncionHorarioDTO> funciones = funcionService.obtenerFuncionesPorPelicula(idPelicula);
        return ResponseEntity.ok(ApiResponse.ok(funciones));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener información de una función por su ID")
    public ResponseEntity<ApiResponse<FuncionHorarioDTO>> obtenerPorId(@PathVariable("id") Long id) {
        FuncionHorarioDTO funcion = funcionService.obtenerPorId(id);
        return ResponseEntity.ok(ApiResponse.ok(funcion));
    }

    @PostMapping
    @Operation(summary = "Crear nueva función y programar butacas de la sala (Admin RF09)")
    public ResponseEntity<ApiResponse<FuncionHorarioDTO>> crearFuncion(@Valid @RequestBody FuncionHorarioRequestDTO dto) {
        FuncionHorarioDTO nueva = funcionService.crearFuncion(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(nueva, "Función y butacas programadas exitosamente"));
    }

    @GetMapping("/{id}/butacas")
    @Operation(summary = "Obtener el mapa interactivo de asientos con estados en tiempo real (RF04)")
    public ResponseEntity<ApiResponse<List<FuncionButacaDTO>>> obtenerMapaButacas(@PathVariable("id") Long id) {
        List<FuncionButacaDTO> mapa = funcionService.obtenerMapaButacas(id);
        return ResponseEntity.ok(ApiResponse.ok(mapa, "Mapa de butacas actualizado"));
    }

    @PostMapping("/bloquear-butacas")
    @Operation(summary = "Bloqueo temporal de asientos con timeout de 5 minutos para evitar colisiones (RF05)")
    public ResponseEntity<ApiResponse<BloqueoResponseDTO>> bloquearButacas(
            @Valid @RequestBody BloquearButacasRequestDTO dto) {
        BloqueoResponseDTO respuesta = funcionService.bloquearButacas(dto);
        return ResponseEntity.ok(ApiResponse.ok(respuesta, "Asientos bloqueados temporalmente"));
    }

    @PostMapping("/{id}/liberar-butacas")
    @Operation(summary = "Liberar manualmente butacas en caso de que el usuario cancele la compra")
    public ResponseEntity<ApiResponse<Void>> liberarButacas(
            @PathVariable("id") Long id,
            @RequestBody List<Long> butacaIds) {
        funcionService.liberarButacas(id, butacaIds);
        return ResponseEntity.ok(ApiResponse.ok(null, "Butacas liberadas exitosamente"));
    }
}
