package com.app.cinemabackend.repository;

import com.app.cinemabackend.entity.Butaca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ButacaRepository extends JpaRepository<Butaca, Long> {

    List<Butaca> findBySalaIdSalaOrderByFilaAscNumeroAsc(Long idSala);

    List<Butaca> findBySalaIdSalaAndEstadoOperativoIgnoreCase(Long idSala, String estadoOperativo);
}
