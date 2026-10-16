package com.app.cinemabackend.controllers;

import com.app.cinemabackend.models.Butaca;
import com.app.cinemabackend.repositories.ButacaRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/butacas")
@CrossOrigin(origins = "*")
public class ButacaController {
    
    private final ButacaRepository butacaRepository;

    public ButacaController(ButacaRepository butacaRepository) {
        this.butacaRepository = butacaRepository;
    }


    // Endpoint para obtener el "Array" de butacas de una sala específica  <--- (FILA, NUMERO, ESTADO)
    @GetMapping("/sala/{idSala}")
    public List<Butaca> listarButacasPorSala(@PathVariable Long idSala) {
        return butacaRepository.findBySalaIdSalaOrderByFilaAscNumeroAsc(idSala);
    }
}
