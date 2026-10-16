package com.app.cinemabackend.controllers;

import com.app.cinemabackend.models.Pelicula;
import com.app.cinemabackend.repositories.PeliculaRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/peliculas")
@CrossOrigin(origins = "*")
public class PeliculaController {
 
    private final PeliculaRepository peliculaRepository;

    public PeliculaController(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    @GetMapping
    public List<Pelicula> listarPeliculasActivas() {
        return peliculaRepository.findByEstado("ACTIVA");
    }
}
