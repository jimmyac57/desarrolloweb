package com.cc5002.tarea.entities;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.validation.constraints.NotNull;

/*author:Jimmy Aguilera*/

@Entity
public class Dispositivo {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String nombre;

    @NotNull
    private Integer tipo;
    
    @NotNull
    private Integer anosUso;

    private String descripcion;

    
    @ManyToOne
    @JoinColumn(name = "contacto_id", nullable = false)
    private Contacto contacto;

    @NotNull
    private Integer estado;

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
        return anosUso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Contacto getContacto() {
        return contacto;
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
        this.anosUso = anos_uso;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setContacto(Contacto contacto2) {
        this.contacto = contacto2;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public static Map<String, String> validateDispositivo(String nombre, String descripcion, Integer tipo, Integer uso, Integer estado) {

        Map<String, String> errores = new HashMap<>();
        errores.put("dispositivo","");
        if (nombre == null || nombre.isEmpty()) {
            errores.put("dispositivo", "El nombre del dispositivo es obligatorio");
        } else if(nombre.length() > 80){
            errores.put("dispositivo", "El nombre del dispositivo no puede tener más de 80 caracteres");
        } else if(nombre.length() < 3){
            errores.put("dispositivo", "El nombre del dispositivo no puede tener menos de 3 caracteres");
        } else if (!nombre.matches("[A-Za-z0-9\\s]+")) {
            errores.put("dispositivo", "El nombre del dispositivo solo puede contener letras y números.");
        }

        errores.put("descripcion", "");
        if (descripcion != null && !descripcion.isEmpty()) {
            if (descripcion.length() < 5) {
                errores.put("descripcion", "La descripción debe tener al menos 5 caracteres.");
            } else if (descripcion.length() > 200) {
                errores.put("descripcion", "La descripción no puede tener más de 200 caracteres.");
            } else if (!descripcion.matches("[A-Za-z0-9\\s!@#$%^&*(),.?\":{}|<>_-]+")) {
                errores.put("descripcion", "La descripción contiene caracteres inválidos.");
            }
        }
        errores.put("tipo", "");
        if (tipo == null) {
            errores.put("tipo", "El tipo no puede estar vacío.");
        } else if (tipo < 1 || tipo > 11) {
            errores.put("tipo", "El tipo no es válido.");
        }

        errores.put("uso", "");
        if (uso == null) {
            errores.put("uso", "Los años de uso no pueden estar vacíos.");
        } else if (uso < 1 || uso > 99) {
            errores.put("uso", "Los años de uso deben estar entre 1 y 99.");
        }

    
        errores.put("estado", "");
        if (estado == null) {
            errores.put("estado", "El estado no puede estar vacío.");
        } else if (estado < 1 || estado > 3) {
            errores.put("estado", "El estado no es válido.");
        }

        return errores;
    }


}
