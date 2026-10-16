package com.app.cinemabackend.service.impl;

import com.app.cinemabackend.dto.request.PeliculaRequestDTO;
import com.app.cinemabackend.dto.response.PeliculaDTO;
import com.app.cinemabackend.entity.Pelicula;
import com.app.cinemabackend.exception.ResourceNotFoundException;
import com.app.cinemabackend.repository.PeliculaRepository;
import com.app.cinemabackend.service.PeliculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PeliculaServiceImpl implements PeliculaService {

    private final PeliculaRepository peliculaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PeliculaDTO> obtenerCartelera() {
        return peliculaRepository.findByEstadoIgnoreCase("ACTIVO").stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PeliculaDTO> obtenerTodas() {
        return peliculaRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PeliculaDTO obtenerPorId(Long idPelicula) {
        Pelicula pelicula = peliculaRepository.findById(idPelicula)
                .orElseThrow(() -> new ResourceNotFoundException("Película no encontrada con ID: " + idPelicula));
        return mapToDTO(pelicula);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PeliculaDTO> filtrarPorGenero(String genero) {
        return peliculaRepository.findByGeneroIgnoreCase(genero).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PeliculaDTO> buscarPorTitulo(String query) {
        return peliculaRepository.buscarPorTituloOGenero(query).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PeliculaDTO crearPelicula(PeliculaRequestDTO dto) {
        Pelicula pelicula = Pelicula.builder()
                .titulo(dto.getTitulo())
                .sinopsis(dto.getSinopsis())
                .duracionMinutos(dto.getDuracionMinutos())
                .clasificacion(dto.getClasificacion())
                .genero(dto.getGenero())
                .posterUrl(dto.getPosterUrl())
                .estado(dto.getEstado() != null ? dto.getEstado().toUpperCase() : "ACTIVO")
                .build();
        return mapToDTO(peliculaRepository.save(pelicula));
    }

    @Override
    @Transactional
    public PeliculaDTO actualizarPelicula(Long idPelicula, PeliculaRequestDTO dto) {
        Pelicula pelicula = peliculaRepository.findById(idPelicula)
                .orElseThrow(() -> new ResourceNotFoundException("Película no encontrada con ID: " + idPelicula));

        pelicula.setTitulo(dto.getTitulo());
        pelicula.setSinopsis(dto.getSinopsis());
        pelicula.setDuracionMinutos(dto.getDuracionMinutos());
        pelicula.setClasificacion(dto.getClasificacion());
        pelicula.setGenero(dto.getGenero());
        pelicula.setPosterUrl(dto.getPosterUrl());
        if (dto.getEstado() != null) {
            pelicula.setEstado(dto.getEstado().toUpperCase());
        }

        return mapToDTO(peliculaRepository.save(pelicula));
    }

    @Override
    @Transactional
    public void eliminarPelicula(Long idPelicula) {
        Pelicula pelicula = peliculaRepository.findById(idPelicula)
                .orElseThrow(() -> new ResourceNotFoundException("Película no encontrada con ID: " + idPelicula));
        // Soft delete / cambio de estado a INACTIVO para preservar integridad referencial
        pelicula.setEstado("INACTIVO");
        peliculaRepository.save(pelicula);
    }

    private PeliculaDTO mapToDTO(Pelicula p) {
        return PeliculaDTO.builder()
                .idPelicula(p.getIdPelicula())
                .titulo(p.getTitulo())
                .sinopsis(p.getSinopsis())
                .duracionMinutos(p.getDuracionMinutos())
                .clasificacion(p.getClasificacion())
                .genero(p.getGenero())
                .posterUrl(p.getPosterUrl())
                .estado(p.getEstado())
                .build();
    }
}
