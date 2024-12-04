package com.cc5002.tarea.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Contacto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    private String email;

    private Integer celular;

    private Integer comuna_id;

    private LocalDateTime fecha;

    public Contacto() {
    }

    public Contacto(String nombre, String email, Integer celular, Integer comuna_id, LocalDateTime fecha) {
        this.nombre = nombre;
        this.email = email;
        this.celular = celular;
        this.comuna_id = comuna_id;
        this.fecha = fecha;
    }

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

    public Integer getComunaId() {
        return comuna_id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCelular(Integer celular) {
        this.celular = celular;
    }

    public void setComunaId(Integer comuna_id) {
        this.comuna_id = comuna_id;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    
}
