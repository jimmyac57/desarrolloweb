package com.cc5002.tarea.entities.archivo;

import java.util.List;

import com.cc5002.tarea.entities.dispositivo.Dispositivo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "archivo", schema = "tarea2")
public class Archivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O la estrategia adecuada para tu base de datos
    private Integer id;

    @NotNull
    private String rutaArchivo;

    @NotNull
    private String nombreArchivo;

    @ManyToOne
    @JoinColumn(name = "dispositivo_id", nullable = false)
    private Dispositivo dispositivo; // Relación con Dispositivo

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
}
