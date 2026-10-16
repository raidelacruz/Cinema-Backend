package com.app.cinemabackend.controller;

import com.app.cinemabackend.dto.response.ApiResponse;
import com.app.cinemabackend.dto.response.DulceriaDTO;
import com.app.cinemabackend.service.DulceriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dulceria")
@Tag(name = "Dulcería", description = "Endpoints para el catálogo de dulcería, combos y snacks (RF06)")
public class DulceriaController {

    private final DulceriaService dulceriaService;

    public DulceriaController(DulceriaService dulceriaService) {
        this.dulceriaService = dulceriaService;
    }

    @GetMapping
    @Operation(summary = "Obtener el catálogo completo de productos de confitería y combos")
    public ResponseEntity<ApiResponse<List<DulceriaDTO>>> obtenerCatalogo() {
        List<DulceriaDTO> productos = dulceriaService.obtenerCatalogo();
        return ResponseEntity.ok(ApiResponse.ok(productos, "Catálogo de dulcería obtenido exitosamente"));
    }

    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Filtrar productos de dulcería por categoría (COMBOS, PALOMITAS, BEBIDAS, SNACKS)")
    public ResponseEntity<ApiResponse<List<DulceriaDTO>>> obtenerPorCategoria(@PathVariable("categoria") String categoria) {
        List<DulceriaDTO> productos = dulceriaService.obtenerPorCategoria(categoria);
        return ResponseEntity.ok(ApiResponse.ok(productos));
    }
}
