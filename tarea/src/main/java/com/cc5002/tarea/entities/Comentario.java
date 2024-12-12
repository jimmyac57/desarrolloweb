package com.cc5002.tarea.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

/*author:Jimmy Aguilera*/

@Entity
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String nombre;

    @NotNull
    private String texto;

    @NotNull
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "dispositivo_id", nullable = false)
    private Dispositivo dispositivo;

    public Comentario(String nombre, String texto, LocalDateTime fecha, Dispositivo dispositivo2) {
        this.nombre = nombre;
        this.texto = texto;
        this.fecha = fecha;
        this.dispositivo = dispositivo2;
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

    public Dispositivo getDispositivo() {
        return dispositivo;
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

    public void setDispositivo(Dispositivo dispositivo2) {
        this.dispositivo = dispositivo2;
    }

    public Comentario() {

    }

    @Override
    public String toString() {
        return "Comentario{" +
                ", nombre='" + nombre + '\'' +
                ", texto='" + texto + '\'' +
                ", fecha=" + fecha +
                ", dispositivo=" + (dispositivo != null ? dispositivo.getId() : "null") +
                '}';
    }

}
