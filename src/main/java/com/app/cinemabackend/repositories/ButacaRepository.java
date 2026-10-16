package com.app.cinemabackend.repositories;

import com.app.cinemabackend.models.Butaca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

public interface ButacaRepository extends JpaRepository<Butaca, Long> {
    // Este metodo busca todos las butacas de una sala ordenadas por fila y numero
    List<Butaca> findBySalaIdSalaOrderByFilaAscNumeroAsc(Long idSala);

}
