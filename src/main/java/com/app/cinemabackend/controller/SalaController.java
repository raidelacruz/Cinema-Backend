package com.app.cinemabackend.controller;

import com.app.cinemabackend.dto.response.ApiResponse;
import com.app.cinemabackend.dto.response.SalaDTO;
import com.app.cinemabackend.entity.Sala;
import com.app.cinemabackend.exception.ResourceNotFoundException;
import com.app.cinemabackend.repository.SalaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/salas")
@Tag(name = "Salas", description = "Endpoints para la gestión de salas de proyección")
public class SalaController {

    private final SalaRepository salaRepository;

    public SalaController(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    @GetMapping
    @Operation(summary = "Listar todas las salas del cine")
    public ResponseEntity<ApiResponse<List<SalaDTO>>> listarTodas() {
        List<SalaDTO> salas = salaRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.ok(salas, "Salas obtenidas exitosamente"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle de una sala por ID")
    public ResponseEntity<ApiResponse<SalaDTO>> obtenerPorId(@PathVariable("id") Long id) {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sala no encontrada con ID: " + id));
        return ResponseEntity.ok(ApiResponse.ok(mapToDTO(sala), "Sala encontrada"));
    }

    @PostMapping
    @Operation(summary = "Crear una nueva sala")
    public ResponseEntity<ApiResponse<SalaDTO>> crear(@RequestBody SalaDTO dto) {
        Sala sala = Sala.builder()
                .nombreNumero(dto.getNombreNumero())
                .tipoSala(dto.getTipoSala() != null ? dto.getTipoSala() : "2D")
                .estado(dto.getEstado() != null ? dto.getEstado() : "ACTIVA")
                .build();
        Sala guardada = salaRepository.save(sala);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(mapToDTO(guardada), "Sala creada exitosamente"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar sala por ID")
    public ResponseEntity<ApiResponse<SalaDTO>> actualizar(@PathVariable("id") Long id, @RequestBody SalaDTO dto) {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sala no encontrada con ID: " + id));
        if (dto.getNombreNumero() != null) sala.setNombreNumero(dto.getNombreNumero());
        if (dto.getTipoSala() != null) sala.setTipoSala(dto.getTipoSala());
        if (dto.getEstado() != null) sala.setEstado(dto.getEstado());

        Sala actualizada = salaRepository.save(sala);
        return ResponseEntity.ok(ApiResponse.ok(mapToDTO(actualizada), "Sala actualizada exitosamente"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar sala por ID")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable("id") Long id) {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sala no encontrada con ID: " + id));
        salaRepository.delete(sala);
        return ResponseEntity.ok(ApiResponse.ok(null, "Sala eliminada exitosamente"));
    }

    private SalaDTO mapToDTO(Sala s) {
        return SalaDTO.builder()
                .idSala(s.getIdSala())
                .nombreNumero(s.getNombreNumero())
                .tipoSala(s.getTipoSala())
                .estado(s.getEstado())
                .build();
    }
}
