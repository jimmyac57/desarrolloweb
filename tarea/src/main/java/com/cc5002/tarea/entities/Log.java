package com.cc5002.tarea.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
/*author:Jimmy Aguilera*/
@Entity
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(max = 200,message = "El motivo no puede tener mas de 200 caracteres")
    @Column(name = "mensaje")
    private String motivo;

    @NotNull
    @Column(name = "fecha")
    private LocalDateTime fecha;

    public Integer getId() {
        return id;
    }

    public String getMotivo() {
        return motivo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    

    
}
