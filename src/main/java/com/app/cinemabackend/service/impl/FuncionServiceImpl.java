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

        FuncionHorario funcion = FuncionHorario.builder()
                .pelicula(pelicula)
                .sala(sala)
                .fechaHoraInicio(dto.getFechaHoraInicio())
                .fechaHoraFin(dto.getFechaHoraFin() != null ? dto.getFechaHoraFin() : dto.getFechaHoraInicio().plusMinutes(pelicula.getDuracionMinutos() != null ? pelicula.getDuracionMinutos() : 120))
                .formato(dto.getFormato() != null ? dto.getFormato().toUpperCase() : "2D")
                .version(dto.getVersion() != null ? dto.getVersion().toUpperCase() : "DOBLADA")
                .precioBase(dto.getPrecioBase())
                .estado(dto.getEstado() != null ? dto.getEstado().toUpperCase() : "PROGRAMADA")
                .build();

        FuncionHorario guardada = funcionHorarioRepository.save(funcion);

        // Inicializar todas las butacas fisicas de la sala para esta funcion
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
    public List<FuncionButacaDTO> obtenerMapaButacas(Long idFuncion) {
        if (!funcionHorarioRepository.existsById(idFuncion)) {
            throw new ResourceNotFoundException("Función no encontrada con ID: " + idFuncion);
        }

        // 1. Liberar automaticamente las butacas cuyo bloqueo de 5 min ya expiro
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
        // Liberar butacas expiradas para tener el estado mas fresco
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

            // Aplicar bloqueo temporal de 5 minutos
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
