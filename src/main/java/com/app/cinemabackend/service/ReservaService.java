package com.app.cinemabackend.service;

import com.app.cinemabackend.dto.request.ReservaRequestDTO;
import com.app.cinemabackend.dto.response.ReservaResponseDTO;

import java.util.List;

public interface ReservaService {

    ReservaResponseDTO procesarReserva(ReservaRequestDTO dto);

    ReservaResponseDTO obtenerPorCodigo(String codigoReserva);

    List<ReservaResponseDTO> obtenerHistorialPorEmail(String email);
}
