package com.cc5002.tarea.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Archivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_archivo", nullable = false)
    private String nombre_archivo;

    @Column(name = "ruta_archivo", nullable = false)
    private String ruta_archivo;

    public Archivo(){

    }
    public Archivo(String nombre_archivo, String ruta_archivo){
        this.nombre_archivo = nombre_archivo;
        this.ruta_archivo = ruta_archivo;
    }

    public Integer getId() {
        return id;
    }

    public String getNombreArchivo() {
        return nombre_archivo;
    }

    public String getRutaArchivo() {
        return ruta_archivo;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombreArchivo(String nombre_archivo) {
        this.nombre_archivo = nombre_archivo;
    }

    public void setRutaArchivo(String ruta_archivo) {
        this.ruta_archivo = ruta_archivo;
    }
    
}
