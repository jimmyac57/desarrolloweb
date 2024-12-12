package com.cc5002.tarea.entities;

import java.time.LocalDateTime;
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
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String nombre;

    @NotNull
    private String email;

    
    private String celular;

    @NotNull
    @Column(name = "fecha_creacion")
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "comuna_id", nullable = false)
    private Comuna comuna;

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getCelular() {
        return celular;
    }

    public Comuna getComuna() {
        return comuna;
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

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setComuna(Comuna comuna2) {
        this.comuna = comuna2;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }


    public Contacto() {
    }
    
    public Contacto(String nombre, String email, String celular, Comuna comuna, LocalDateTime fecha) {
        this.nombre = nombre;
        this.email = email;
        this.celular = celular;
        this.comuna = comuna;
        this.fecha = fecha;
    }
    
    

    

}
