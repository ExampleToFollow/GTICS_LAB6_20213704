package com.example.lab6gtics.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "mesa", schema = "labcito")
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmesa", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @NotNull
    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    @Size(max = 45)
    @NotNull
    @Column(name = "ubicacion", nullable = false, length = 45)
    private String ubicacion;

    @NotNull
    @Column(name = "disponibles", nullable = false)
    private Integer disponibles;

    @OneToMany(mappedBy = "idMesa")
    private Set<Reserva> reservas = new LinkedHashSet<>();

}