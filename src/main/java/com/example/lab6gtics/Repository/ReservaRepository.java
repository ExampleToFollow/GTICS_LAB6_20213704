package com.example.lab6gtics.Repository;

import com.example.lab6gtics.Entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    @Query(nativeQuery = true, value="select * from reserva where idUsuario = ?1 ")
    List<Reserva> getReservaPorIdCliente(int idCliente);


}
