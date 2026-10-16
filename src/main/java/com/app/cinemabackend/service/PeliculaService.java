package com.app.cinemabackend.service;

import com.app.cinemabackend.dto.request.PeliculaRequestDTO;
import com.app.cinemabackend.dto.response.PeliculaDTO;

import java.util.List;

public interface PeliculaService {

    List<PeliculaDTO> obtenerCartelera();

    List<PeliculaDTO> obtenerTodas();

    PeliculaDTO obtenerPorId(Long idPelicula);

    List<PeliculaDTO> filtrarPorGenero(String genero);

    List<PeliculaDTO> buscarPorTitulo(String query);

    PeliculaDTO crearPelicula(PeliculaRequestDTO dto);

    PeliculaDTO actualizarPelicula(Long idPelicula, PeliculaRequestDTO dto);

    void eliminarPelicula(Long idPelicula);
}
