package com.app.cinemabackend.service.impl;

import com.app.cinemabackend.dto.request.BloquearButacasRequestDTO;
import com.app.cinemabackend.dto.request.FuncionHorarioRequestDTO;
import com.app.cinemabackend.dto.response.BloqueoResponseDTO;
import com.app.cinemabackend.dto.response.FuncionButacaDTO;
import com.app.cinemabackend.dto.response.FuncionHorarioDTO;
import com.app.cinemabackend.entity.*;
import com.app.cinemabackend.exception.ConflictException;
import com.app.cinemabackend.exception.ResourceNotFoundException;
import com.app.cinemabackend.repository.*;
import com.app.cinemabackend.service.FuncionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncionServiceImpl implements FuncionService {

    private static final int TIMEOUT_MINUTOS = 5;

    private final FuncionHorarioRepository funcionHorarioRepository;
    private final PeliculaRepository peliculaRepository;
    private final SalaRepository salaRepository;
    private final ButacaRepository butacaRepository;
    private final FuncionButacaRepository funcionButacaRepository;

    public FuncionServiceImpl(FuncionHorarioRepository funcionHorarioRepository,
                              PeliculaRepository peliculaRepository,
                              SalaRepository salaRepository,
                              ButacaRepository butacaRepository,
                              FuncionButacaRepository funcionButacaRepository) {
        this.funcionHorarioRepository = funcionHorarioRepository;
        this.peliculaRepository = peliculaRepository;
        this.salaRepository = salaRepository;
        this.butacaRepository = butacaRepository;
        this.funcionButacaRepository = funcionButacaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FuncionHorarioDTO> obtenerFuncionesPorPelicula(Long idPelicula) {
        return funcionHorarioRepository.findByPeliculaIdPeliculaOrderByFechaHoraInicioAsc(idPelicula).stream()
                .map(this::mapFuncionToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public FuncionHorarioDTO obtenerPorId(Long idFuncion) {
        FuncionHorario funcion = funcionHorarioRepository.findById(idFuncion)
                .orElseThrow(() -> new ResourceNotFoundException("Función no encontrada con ID: " + idFuncion));
        return mapFuncionToDTO(funcion);
    }

    @Override
    @Transactional
    public FuncionHorarioDTO crearFuncion(FuncionHorarioRequestDTO dto) {
        Pelicula pelicula = peliculaRepository.findById(dto.getIdPelicula())
                .orElseThrow(() -> new ResourceNotFoundException("Película no encontrada con ID: " + dto.getIdPelicula()));

        Sala sala = salaRepository.findById(dto.getIdSala())
                .orElseThrow(() -> new ResourceNotFoundException("Sala no encontrada con ID: " + dto.getIdSala()));

        int duracion = pelicula.getDuracionMinutos() != null ? pelicula.getDuracionMinutos() : 120;
        OffsetDateTime inicio = dto.getFechaHoraInicio();
        OffsetDateTime fin = dto.getFechaHoraFin() != null ? dto.getFechaHoraFin() : inicio.plusMinutes(duracion);

        // Validar cruce de horarios en la misma sala
        List<FuncionHorario> cruces = funcionHorarioRepository.findFuncionesSolapadas(sala.getIdSala(), inicio, fin);
        if (!cruces.isEmpty()) {
            FuncionHorario existente = cruces.get(0);
            throw new ConflictException(
                    "Cruce de horario en " + sala.getNombreNumero() +
                    ": ya está programada '" + existente.getPelicula().getTitulo() +
                    "' hasta las " + existente.getFechaHoraFin().toLocalTime() +
                    ". Elige otra hora o sala."
            );
        }

        FuncionHorario funcion = FuncionHorario.builder()
                .pelicula(pelicula)
                .sala(sala)
                .fechaHoraInicio(inicio)
                .fechaHoraFin(fin)
                .formato(dto.getFormato() != null ? dto.getFormato().toUpperCase() : "2D")
                .version(dto.getVersion() != null ? dto.getVersion().toUpperCase() : "DOBLADA")
                .precioBase(dto.getPrecioBase())
                .estado(dto.getEstado() != null ? dto.getEstado().toUpperCase() : "PROGRAMADA")
                .build();

        FuncionHorario guardada = funcionHorarioRepository.save(funcion);

        // Inicializar todas las butacas físicas de la sala para esta función
        List<Butaca> butacasSala = butacaRepository.findBySalaIdSalaOrderByFilaAscNumeroAsc(sala.getIdSala());
        List<FuncionButaca> funcionButacas = butacasSala.stream()
                .map(b -> FuncionButaca.builder()
                        .funcionHorario(guardada)
                        .butaca(b)
                        .sala(sala)
                        .estadoDisponibilidad("DISPONIBLE")
                        .bloqueadoHasta(null)
                        .build())
                .collect(Collectors.toList());

        funcionButacaRepository.saveAll(funcionButacas);

        return mapFuncionToDTO(guardada);
    }

    @Override
    @Transactional
    public FuncionHorarioDTO actualizarFuncion(Long idFuncion, FuncionHorarioRequestDTO dto) {
        FuncionHorario funcion = funcionHorarioRepository.findById(idFuncion)
                .orElseThrow(() -> new ResourceNotFoundException("Función no encontrada con ID: " + idFuncion));

        if (dto.getIdPelicula() != null) {
            Pelicula pelicula = peliculaRepository.findById(dto.getIdPelicula())
                    .orElseThrow(() -> new ResourceNotFoundException("Película no encontrada con ID: " + dto.getIdPelicula()));
            funcion.setPelicula(pelicula);
        }

        if (dto.getIdSala() != null) {
            Sala sala = salaRepository.findById(dto.getIdSala())
                    .orElseThrow(() -> new ResourceNotFoundException("Sala no encontrada con ID: " + dto.getIdSala()));
            funcion.setSala(sala);
        }

        OffsetDateTime inicio = dto.getFechaHoraInicio() != null ? dto.getFechaHoraInicio() : funcion.getFechaHoraInicio();
        int duracion = funcion.getPelicula().getDuracionMinutos() != null ? funcion.getPelicula().getDuracionMinutos() : 120;
        OffsetDateTime fin = dto.getFechaHoraFin() != null ? dto.getFechaHoraFin() : inicio.plusMinutes(duracion);

        // Validar cruce de horarios excluyendo esta misma función
        List<FuncionHorario> cruces = funcionHorarioRepository
                .findFuncionesSolapadas(funcion.getSala().getIdSala(), inicio, fin)
                .stream()
                .filter(f -> !f.getIdFuncion().equals(idFuncion))
                .collect(Collectors.toList());

        if (!cruces.isEmpty()) {
            FuncionHorario existente = cruces.get(0);
            throw new ConflictException(
                    "Cruce de horario en " + funcion.getSala().getNombreNumero() +
                    ": ya está programada '" + existente.getPelicula().getTitulo() +
                    "' hasta las " + existente.getFechaHoraFin().toLocalTime() +
                    ". Elige otra hora o sala."
            );
        }

        funcion.setFechaHoraInicio(inicio);
        funcion.setFechaHoraFin(fin);
        if (dto.getFormato() != null) funcion.setFormato(dto.getFormato().toUpperCase());
        if (dto.getVersion() != null) funcion.setVersion(dto.getVersion().toUpperCase());
        if (dto.getPrecioBase() != null) funcion.setPrecioBase(dto.getPrecioBase());
        if (dto.getEstado() != null) funcion.setEstado(dto.getEstado().toUpperCase());

        FuncionHorario actualizada = funcionHorarioRepository.save(funcion);
        return mapFuncionToDTO(actualizada);
    }

    @Override
    @Transactional
    public void eliminarFuncion(Long idFuncion) {
        FuncionHorario funcion = funcionHorarioRepository.findById(idFuncion)
                .orElseThrow(() -> new ResourceNotFoundException("Función no encontrada con ID: " + idFuncion));

        List<FuncionButaca> butacas = funcionButacaRepository.findByFuncionHorarioIdFuncionConDetalleButaca(idFuncion);
        boolean tieneOcupadas = butacas.stream()
                .anyMatch(fb -> "OCUPADA".equalsIgnoreCase(fb.getEstadoDisponibilidad()));

        if (tieneOcupadas) {
            throw new ConflictException("No se puede eliminar la función porque ya tiene butacas vendidas.");
        }

        funcionButacaRepository.deleteAll(butacas);
        funcionHorarioRepository.delete(funcion);
    }

    @Override
    @Transactional
    public List<FuncionButacaDTO> obtenerMapaButacas(Long idFuncion) {
        if (!funcionHorarioRepository.existsById(idFuncion)) {
            throw new ResourceNotFoundException("Función no encontrada con ID: " + idFuncion);
        }

        // 1. Liberar automáticamente las butacas cuyo bloqueo de 5 min ya expiró
        funcionButacaRepository.liberarButacasExpiradas(OffsetDateTime.now());

        // 2. Consultar el estado actualizado de la sala
        List<FuncionButaca> lista = funcionButacaRepository.findByFuncionHorarioIdFuncionConDetalleButaca(idFuncion);

        OffsetDateTime ahora = OffsetDateTime.now();
        return lista.stream().map(fb -> {
            boolean expirada = fb.getBloqueadoHasta() != null && fb.getBloqueadoHasta().isBefore(ahora);
            String estadoReal = fb.getEstadoDisponibilidad();
            if ("BLOQUEADA".equalsIgnoreCase(estadoReal) && expirada) {
                estadoReal = "DISPONIBLE";
            }
            return FuncionButacaDTO.builder()
                    .idFuncionButaca(fb.getIdFuncionButaca())
                    .idButaca(fb.getButaca().getIdButaca())
                    .fila(fb.getButaca().getFila())
                    .numero(fb.getButaca().getNumero())
                    .codigoButaca(fb.getButaca().getFila() + fb.getButaca().getNumero())
                    .tipoButaca(fb.getButaca().getTipoButaca())
                    .estadoDisponibilidad(estadoReal)
                    .bloqueadoHasta(fb.getBloqueadoHasta())
                    .bloqueadaExpirada(expirada)
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BloqueoResponseDTO bloquearButacas(BloquearButacasRequestDTO dto) {
        FuncionHorario funcion = funcionHorarioRepository.findById(dto.getIdFuncion())
                .orElseThrow(() -> new ResourceNotFoundException("Función no encontrada con ID: " + dto.getIdFuncion()));

        OffsetDateTime ahora = OffsetDateTime.now();
        funcionButacaRepository.liberarButacasExpiradas(ahora);

        List<FuncionButaca> butacasSolicitadas = funcionButacaRepository.findByFuncionAndButacaIds(
                dto.getIdFuncion(), dto.getButacaIds());

        if (butacasSolicitadas.size() != dto.getButacaIds().size()) {
            throw new ResourceNotFoundException("Una o más butacas no pertenecen a esta función o no existen");
        }

        OffsetDateTime nuevoBloqueadoHasta = ahora.plusMinutes(TIMEOUT_MINUTOS);
        List<Long> bloqueadasExitosas = new ArrayList<>();

        for (FuncionButaca fb : butacasSolicitadas) {
            String estado = fb.getEstadoDisponibilidad();

            if ("OCUPADA".equalsIgnoreCase(estado)) {
                throw new ConflictException(String.format("La butaca %s%d ya fue comprada por otro cliente",
                        fb.getButaca().getFila(), fb.getButaca().getNumero()));
            }

            if ("BLOQUEADA".equalsIgnoreCase(estado) && fb.getBloqueadoHasta() != null && fb.getBloqueadoHasta().isAfter(ahora)) {
                throw new ConflictException(String.format("La butaca %s%d está siendo reservada por otro usuario. Intenta más tarde.",
                        fb.getButaca().getFila(), fb.getButaca().getNumero()));
            }

            fb.setEstadoDisponibilidad("BLOQUEADA");
            fb.setBloqueadoHasta(nuevoBloqueadoHasta);
            bloqueadasExitosas.add(fb.getButaca().getIdButaca());
        }

        funcionButacaRepository.saveAll(butacasSolicitadas);

        return BloqueoResponseDTO.builder()
                .bloqueadoExitoso(true)
                .mensaje(String.format("Se bloquearon %d butacas por %d minutos para completar el pago.",
                        bloqueadasExitosas.size(), TIMEOUT_MINUTOS))
                .idFuncion(dto.getIdFuncion())
                .butacasBloqueadas(bloqueadasExitosas)
                .expiraEn(nuevoBloqueadoHasta)
                .segundosRestantes(Duration.between(ahora, nuevoBloqueadoHasta).getSeconds())
                .build();
    }

    @Override
    @Transactional
    public void liberarButacas(Long idFuncion, List<Long> butacaIds) {
        List<FuncionButaca> butacas = funcionButacaRepository.findByFuncionAndButacaIds(idFuncion, butacaIds);
        for (FuncionButaca fb : butacas) {
            if ("BLOQUEADA".equalsIgnoreCase(fb.getEstadoDisponibilidad())) {
                fb.setEstadoDisponibilidad("DISPONIBLE");
                fb.setBloqueadoHasta(null);
            }
        }
        funcionButacaRepository.saveAll(butacas);
    }

    private FuncionHorarioDTO mapFuncionToDTO(FuncionHorario f) {
        return FuncionHorarioDTO.builder()
                .idFuncion(f.getIdFuncion())
                .idPelicula(f.getPelicula().getIdPelicula())
                .tituloPelicula(f.getPelicula().getTitulo())
                .posterUrl(f.getPelicula().getPosterUrl())
                .idSala(f.getSala().getIdSala())
                .nombreSala(f.getSala().getNombreNumero())
                .fechaHoraInicio(f.getFechaHoraInicio())
                .fechaHoraFin(f.getFechaHoraFin())
                .formato(f.getFormato())
                .version(f.getVersion())
                .precioBase(f.getPrecioBase())
                .estado(f.getEstado())
                .build();
    }
}