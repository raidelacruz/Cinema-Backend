package com.app.cinemabackend.controller;

import com.app.cinemabackend.dto.request.PeliculaRequestDTO;
import com.app.cinemabackend.dto.response.ApiResponse;
import com.app.cinemabackend.dto.response.PeliculaDTO;
import com.app.cinemabackend.service.PeliculaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/peliculas")
@RequiredArgsConstructor
@Tag(name = "Películas", description = "Endpoints para el catálogo de películas y cartelera (RF03)")
public class PeliculaController {

    private final PeliculaService peliculaService;

    @GetMapping
    @Operation(summary = "Listar todas las películas de la cartelera activa")
    public ResponseEntity<ApiResponse<List<PeliculaDTO>>> listarCartelera(
            @RequestParam(name = "todas", defaultValue = "false") boolean todas) {
        List<PeliculaDTO> peliculas = todas ? peliculaService.obtenerTodas() : peliculaService.obtenerCartelera();
        return ResponseEntity.ok(ApiResponse.ok(peliculas, "Películas obtenidas exitosamente"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle y ficha técnica de una película por ID")
    public ResponseEntity<ApiResponse<PeliculaDTO>> obtenerPorId(@PathVariable("id") Long id) {
        PeliculaDTO pelicula = peliculaService.obtenerPorId(id);
        return ResponseEntity.ok(ApiResponse.ok(pelicula));
    }

    @GetMapping("/genero/{genero}")
    @Operation(summary = "Filtrar películas por género cinematográfico")
    public ResponseEntity<ApiResponse<List<PeliculaDTO>>> filtrarPorGenero(@PathVariable("genero") String genero) {
        List<PeliculaDTO> peliculas = peliculaService.filtrarPorGenero(genero);
        return ResponseEntity.ok(ApiResponse.ok(peliculas));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar películas por coincidencia en título o género")
    public ResponseEntity<ApiResponse<List<PeliculaDTO>>> buscar(@RequestParam("q") String query) {
        List<PeliculaDTO> peliculas = peliculaService.buscarPorTitulo(query);
        return ResponseEntity.ok(ApiResponse.ok(peliculas));
    }

    @PostMapping
    @Operation(summary = "Registrar una nueva película (Administración RF09)")
    public ResponseEntity<ApiResponse<PeliculaDTO>> crearPelicula(@Valid @RequestBody PeliculaRequestDTO dto) {
        PeliculaDTO nueva = peliculaService.crearPelicula(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(nueva, "Película creada exitosamente"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar información de una película existente")
    public ResponseEntity<ApiResponse<PeliculaDTO>> actualizarPelicula(
            @PathVariable("id") Long id,
            @Valid @RequestBody PeliculaRequestDTO dto) {
        PeliculaDTO actualizada = peliculaService.actualizarPelicula(id, dto);
        return ResponseEntity.ok(ApiResponse.ok(actualizada, "Película actualizada exitosamente"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Dar de baja una película (cambiar estado a INACTIVO)")
    public ResponseEntity<ApiResponse<Void>> eliminarPelicula(@PathVariable("id") Long id) {
        peliculaService.eliminarPelicula(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Película dada de baja exitosamente"));
    }
}
