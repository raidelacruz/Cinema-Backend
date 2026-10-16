package com.app.cinemabackend.repository;

import com.app.cinemabackend.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    Optional<Reserva> findByCodigoReserva(String codigoReserva);

    List<Reserva> findByClienteEmailOrderByFechaCreacionDesc(String clienteEmail);
}
