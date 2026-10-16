package com.app.cinemabackend.repository;

import com.app.cinemabackend.entity.FuncionHorario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface FuncionHorarioRepository extends JpaRepository<FuncionHorario, Long> {

    List<FuncionHorario> findByPeliculaIdPeliculaOrderByFechaHoraInicioAsc(Long idPelicula);

    List<FuncionHorario> findByPeliculaIdPeliculaAndEstadoOrderByFechaHoraInicioAsc(Long idPelicula, String estado);

    @Query("SELECT f FROM FuncionHorario f WHERE f.pelicula.idPelicula = :idPelicula " +
           "AND f.fechaHoraInicio >= :desde AND f.fechaHoraInicio <= :hasta " +
           "ORDER BY f.fechaHoraInicio ASC")
    List<FuncionHorario> findFuncionesPorRangoFecha(
            @Param("idPelicula") Long idPelicula,
            @Param("desde") OffsetDateTime desde,
            @Param("hasta") OffsetDateTime hasta);
}
