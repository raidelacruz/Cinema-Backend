package com.app.cinemabackend.repository;

import com.app.cinemabackend.entity.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {

    List<Sala> findByEstadoIgnoreCase(String estado);
}
