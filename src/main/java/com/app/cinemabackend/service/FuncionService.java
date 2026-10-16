package com.app.cinemabackend.service;

import com.app.cinemabackend.dto.request.BloquearButacasRequestDTO;
import com.app.cinemabackend.dto.request.FuncionHorarioRequestDTO;
import com.app.cinemabackend.dto.response.BloqueoResponseDTO;
import com.app.cinemabackend.dto.response.FuncionButacaDTO;
import com.app.cinemabackend.dto.response.FuncionHorarioDTO;

import java.util.List;

public interface FuncionService {

    List<FuncionHorarioDTO> obtenerFuncionesPorPelicula(Long idPelicula);

    FuncionHorarioDTO obtenerPorId(Long idFuncion);

    FuncionHorarioDTO crearFuncion(FuncionHorarioRequestDTO dto);

    List<FuncionButacaDTO> obtenerMapaButacas(Long idFuncion);

    BloqueoResponseDTO bloquearButacas(BloquearButacasRequestDTO dto);

    void liberarButacas(Long idFuncion, List<Long> butacaIds);
}
