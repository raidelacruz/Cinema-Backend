package com.app.cinemabackend.repository;

import com.app.cinemabackend.entity.DulceriaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DulceriaProductoRepository extends JpaRepository<DulceriaProducto, Long> {

    List<DulceriaProducto> findByDisponibleTrue();

    List<DulceriaProducto> findByCategoriaIgnoreCaseAndDisponibleTrue(String categoria);
}
