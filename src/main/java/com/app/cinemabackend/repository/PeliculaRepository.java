package com.app.cinemabackend.repository;

import com.app.cinemabackend.entity.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {

    List<Pelicula> findByEstadoIgnoreCase(String estado);

    @Query("SELECT p FROM Pelicula p WHERE UPPER(p.estado) LIKE 'ACTIV%' OR UPPER(p.estado) = 'DISPONIBLE' OR p.estado IS NULL")
    List<Pelicula> findPeliculasActivas();

    List<Pelicula> findByGeneroIgnoreCase(String genero);

    @Query("SELECT p FROM Pelicula p WHERE LOWER(p.titulo) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
           "OR LOWER(p.genero) LIKE LOWER(CONCAT('%', :filtro, '%'))")
    List<Pelicula> buscarPorTituloOGenero(@Param("filtro") String filtro);
}
