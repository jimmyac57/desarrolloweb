package com.cc5002.tarea.entities.comuna;

import java.util.List;

import com.cc5002.tarea.entities.contacto.Contacto;
import com.cc5002.tarea.entities.region.Region;

import jakarta.persistence.*;

@Entity
@Table(name = "comuna", schema = "tarea2")
public class Comuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @OneToMany(mappedBy = "comuna", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contacto> contactos;

    // Getters
    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Region getRegion() {
        return region;
    }
}
