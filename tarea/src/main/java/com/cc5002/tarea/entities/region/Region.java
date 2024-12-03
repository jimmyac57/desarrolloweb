package com.cc5002.tarea.entities.region;
import com.cc5002.tarea.entities.comuna.Comuna;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


import java.util.List;

@Entity
@Table(name = "region", schema = "tarea2")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String nombre;

    @OneToMany(mappedBy = "region")
    private List<Comuna> comunas;

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

}
