package com.app.cinemabackend.repository;

import com.app.cinemabackend.entity.FuncionButaca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionButacaRepository extends JpaRepository<FuncionButaca, Long> {

    @Query("SELECT fb FROM FuncionButaca fb JOIN FETCH fb.butaca b " +
           "WHERE fb.funcionHorario.idFuncion = :idFuncion " +
           "ORDER BY b.fila ASC, b.numero ASC")
    List<FuncionButaca> findByFuncionHorarioIdFuncionConDetalleButaca(@Param("idFuncion") Long idFuncion);

    Optional<FuncionButaca> findByFuncionHorarioIdFuncionAndButacaIdButaca(Long idFuncion, Long idButaca);

    @Query("SELECT fb FROM FuncionButaca fb JOIN FETCH fb.butaca " +
           "WHERE fb.funcionHorario.idFuncion = :idFuncion AND fb.butaca.idButaca IN :butacaIds")
    List<FuncionButaca> findByFuncionAndButacaIds(
            @Param("idFuncion") Long idFuncion,
            @Param("butacaIds") List<Long> butacaIds);

    @Modifying
    @Query("UPDATE FuncionButaca fb SET fb.estadoDisponibilidad = 'DISPONIBLE', fb.bloqueadoHasta = NULL " +
           "WHERE fb.estadoDisponibilidad = 'BLOQUEADA' AND fb.bloqueadoHasta < :ahora")
    int liberarButacasExpiradas(@Param("ahora") OffsetDateTime ahora);
}
