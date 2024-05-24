package com.example.lab6gtics.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "reserva", schema = "labcito")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idreserva", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario idUsuario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idmesa", nullable = false)
    private Mesa idMesa;

    @NotNull
    @Column(name = "fechainicio", nullable = false)
    private LocalDate fechaInicio;

    @NotNull
    @Column(name = "fechafin", nullable = false)
    private LocalDate fechaFin;

}