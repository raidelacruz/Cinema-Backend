package com.app.cinemabackend.service;

import com.app.cinemabackend.dto.response.DulceriaDTO;

import java.util.List;

public interface DulceriaService {

    List<DulceriaDTO> obtenerCatalogo();

    List<DulceriaDTO> obtenerPorCategoria(String categoria);
}
