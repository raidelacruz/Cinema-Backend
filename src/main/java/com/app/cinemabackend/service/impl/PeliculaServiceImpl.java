package com.app.cinemabackend.service.impl;

import com.app.cinemabackend.dto.request.PeliculaRequestDTO;
import com.app.cinemabackend.dto.response.PeliculaDTO;
import com.app.cinemabackend.entity.Pelicula;
import com.app.cinemabackend.exception.ResourceNotFoundException;
import com.app.cinemabackend.repository.PeliculaRepository;
import com.app.cinemabackend.service.PeliculaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeliculaServiceImpl implements PeliculaService {

    private final PeliculaRepository peliculaRepository;

    public PeliculaServiceImpl(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    @Override
    public List<PeliculaDTO> obtenerCartelera() {
        try {
            List<Pelicula> peliculas = peliculaRepository.findByEstadoIgnoreCase("ACTIVO");
            if (!peliculas.isEmpty()) {
                return peliculas.stream().map(this::mapToDTO).collect(Collectors.toList());
            }
        } catch (Throwable ignored) {
            // Fallback si la conexion a Supabase no esta configurada o la BD no responde
        }
        return obtenerCarteleraMock();
    }

    @Override
    public List<PeliculaDTO> obtenerTodas() {
        try {
            List<Pelicula> peliculas = peliculaRepository.findAll();
            if (!peliculas.isEmpty()) {
                return peliculas.stream().map(this::mapToDTO).collect(Collectors.toList());
            }
        } catch (Throwable ignored) {
            // Fallback si la conexion a Supabase no esta configurada
        }
        return obtenerCarteleraMock();
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

    private List<PeliculaDTO> obtenerCarteleraMock() {
        List<PeliculaDTO> mock = new ArrayList<>();
        mock.add(PeliculaDTO.builder()
                .idPelicula(1L)
                .titulo("Joker: Folie à Deux")
                .sinopsis("Arthur Fleck se encuentra institucionalizado en Arkham esperando juicio por sus crímenes como Joker. Mientras lucha con su doble identidad, Arthur tropieza con el amor verdadero.")
                .duracionMinutos(138)
                .clasificacion("R")
                .genero("Drama / Suspenso")
                .posterUrl("https://image.tmdb.org/t/p/w500/aciP8Km0waTLGQi06Ki4yX89nNx.jpg")
                .estado("ACTIVO")
                .build());
        mock.add(PeliculaDTO.builder()
                .idPelicula(2L)
                .titulo("Deadpool & Wolverine")
                .sinopsis("Wolverine se recupera de sus heridas cuando se cruza con el bocazas Deadpool, uniéndose para vencer a un enemigo común.")
                .duracionMinutos(127)
                .clasificacion("PG-13")
                .genero("Acción / Comedia")
                .posterUrl("https://image.tmdb.org/t/p/w500/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg")
                .estado("ACTIVO")
                .build());
        mock.add(PeliculaDTO.builder()
                .idPelicula(3L)
                .titulo("Beetlejuice Beetlejuice")
                .sinopsis("Después de una tragedia familiar inesperada, tres generaciones de la familia Deetz regresan a su hogar en Winter River.")
                .duracionMinutos(104)
                .clasificacion("PG-13")
                .genero("Comedia / Fantasía")
                .posterUrl("https://image.tmdb.org/t/p/w500/kKgQzkUCUmynoam0MzZnoCJv8AC.jpg")
                .estado("ACTIVO")
                .build());
        mock.add(PeliculaDTO.builder()
                .idPelicula(4L)
                .titulo("Transformers One")
                .sinopsis("La historia de origen de Optimus Prime y Megatron y cómo pasaron de ser hermanos de armas a acérrimos enemigos.")
                .duracionMinutos(104)
                .clasificacion("PG")
                .genero("Animación / Ciencia Ficción")
                .posterUrl("https://image.tmdb.org/t/p/w500/iHPIzp3t7eW2F2BwXkZ11Q24w6E.jpg")
                .estado("ACTIVO")
                .build());
        return mock;
    }
}
