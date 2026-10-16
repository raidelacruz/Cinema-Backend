package com.app.cinemabackend.controller;

import com.app.cinemabackend.dto.response.ApiResponse;
import com.app.cinemabackend.dto.response.ButacaDTO;
import com.app.cinemabackend.entity.Butaca;
import com.app.cinemabackend.repository.ButacaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/butacas")
@Tag(name = "Butacas Físicas", description = "Endpoints para consultar la distribución física de asientos de una sala")
public class ButacaController {

    private final ButacaRepository butacaRepository;

    public ButacaController(ButacaRepository butacaRepository) {
        this.butacaRepository = butacaRepository;
    }

    @GetMapping("/sala/{idSala}")
    @Operation(summary = "Obtener el listado de butacas físicas de una sala ordenadas por fila y número")
    public ResponseEntity<ApiResponse<List<ButacaDTO>>> listarButacasPorSala(@PathVariable("idSala") Long idSala) {
        List<Butaca> butacas = butacaRepository.findBySalaIdSalaOrderByFilaAscNumeroAsc(idSala);
        List<ButacaDTO> dtos = butacas.stream()
                .map(b -> ButacaDTO.builder()
                        .idButaca(b.getIdButaca())
                        .idSala(b.getSala().getIdSala())
                        .fila(b.getFila())
                        .numero(b.getNumero())
                        .codigoButaca(b.getFila() + b.getNumero())
                        .tipoButaca(b.getTipoButaca())
                        .estadoOperativo(b.getEstadoOperativo())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.ok(dtos, "Butacas de la sala obtenidas exitosamente"));
    }
}
