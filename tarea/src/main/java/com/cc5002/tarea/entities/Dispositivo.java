package com.cc5002.tarea.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Dispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    private Integer tipo;
    
    private Integer anos_uso;

    private String descripcion;

    private Integer contacto_id;

    private Integer estado;

    public Dispositivo() {
    }

    public Dispositivo(String nombre, Integer tipo, Integer anos_uso, String descripcion, Integer contacto_id, Integer estado) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.anos_uso = anos_uso;
        this.descripcion = descripcion;
        this.contacto_id = contacto_id;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getTipo() {
        return tipo;
    }

    public Integer getAnosUso() {
        return anos_uso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Integer getContactoId() {
        return contacto_id;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public void setAnosUso(Integer anos_uso) {
        this.anos_uso = anos_uso;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setContactoId(Integer contacto_id) {
        this.contacto_id = contacto_id;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    
}
