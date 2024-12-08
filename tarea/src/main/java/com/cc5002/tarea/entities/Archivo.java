package com.cc5002.tarea.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

/*author:Jimmy Aguilera*/

@Entity
public class Archivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "nombre_archivo", nullable = false)
    private String nombreArchivo;

    @NotNull
    @Column(name = "ruta_archivo", nullable = false)
    private String rutaArchivo;

    @ManyToOne
    @JoinColumn(name = "dispositivo_id", nullable = false)
    private Dispositivo dispositivo;

    public Archivo(){

    }
    public Archivo(String nombre_archivo, String ruta_archivo , Dispositivo dispositivo){
        this.nombreArchivo = nombre_archivo;
        this.rutaArchivo = ruta_archivo;
        this.dispositivo = dispositivo;
    }

    public Integer getId() {
        return id;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombreArchivo(String nombre_archivo) {
        this.nombreArchivo = nombre_archivo;
    }

    public void setRutaArchivo(String ruta_archivo) {
        this.rutaArchivo = ruta_archivo;
    }

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }
    
}
