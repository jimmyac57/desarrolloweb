package com.cc5002.tarea.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

/*author:Jimmy Aguilera*/

@Entity
public class Comentario {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String nombre;

    @NotNull
    private String texto;

    @NotNull
    private LocalDateTime fecha;

    @NotNull
    private Integer dispositivo_id;

    public Comentario() {
    }

    public Comentario(String nombre, String texto, LocalDateTime fecha, Integer dispositivo_id) {
        this.nombre = nombre;
        this.texto = texto;
        this.fecha = fecha;
        this.dispositivo_id = dispositivo_id;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTexto() {
        return texto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Integer getDispositivoId() {
        return dispositivo_id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setDispositivoId(Integer dispositivo_id) {
        this.dispositivo_id = dispositivo_id;
    }


    
}
