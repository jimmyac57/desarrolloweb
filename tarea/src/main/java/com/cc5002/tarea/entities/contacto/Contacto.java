package com.cc5002.tarea.entities.contacto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cc5002.tarea.entities.comuna.Comuna;
import com.cc5002.tarea.entities.dispositivo.Dispositivo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "contacto", schema = "tarea2")
public class Contacto {
    
    @Id
    @SequenceGenerator(
        name = "contacto_sequence",
        sequenceName = "contacto_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "contacto_sequence"
    )
    private Integer id;

    @NotNull
    private String nombre;

    @NotNull
    private String email;

    private Integer celular;

    @NotNull
    private LocalDateTime fecha_creacion;

    @ManyToOne
    @JoinColumn(name = "comuna_id", nullable = false)
    private Comuna comuna;

    @OneToMany(mappedBy = "contacto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Dispositivo> dispositivos;

    // getters

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public Integer getCelular() {
        return celular;
    }

    public LocalDateTime getFecha_creacion() {
        return fecha_creacion;
    }


   

}
