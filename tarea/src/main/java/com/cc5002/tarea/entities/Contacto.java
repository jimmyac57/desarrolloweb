package com.cc5002.tarea.entities;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String nombre;

    @NotNull
    private String email;

    
    private Integer celular;

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

    public Integer getCelular() {
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

    public void setCelular(Integer celular) {
        this.celular = celular;
    }

    public void setComuna(Comuna comuna2) {
        this.comuna = comuna2;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public static Map<String, String> validateContacto(String nombre, String email, Integer celular) {
        Map<String, String> errors = new HashMap<>();
        //validacion de nombre
        errors.put("nombre", "");
        if (nombre == null || nombre.isEmpty()) {
            errors.put("nombre", "El nombre es obligatorio");
        } else if (nombre.length() > 80) {
            errors.put("nombre", "El nombre no puede tener más de 80 caracteres");
        } else if (nombre.length() < 3) {
            errors.put("nombre", "El nombre no puede tener menos de 3 caracteres");
        } else if (!nombre.matches("[A-Za-z\\s]+")) {
            errors.put("nombre", "El nombre solo puede contener letras");
        }
        //validacion de email
        errors.put("email", "");
        if (email == null || email.isEmpty()) {
            errors.put("email", "El email es obligatorio");
        } else if (email.length() > 30) {
            errors.put("email", "El email no puede tener más de 30 caracteres");
        } else if (email.length() < 5) {
            errors.put("email", "El email no puede tener menos de 5 caracteres");
        } else if (!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            errors.put("email", "El email no tiene un formato válido. Ejemplo: usuario@dominio.com");
        }
        //validacion de celular
        errors.put("celular", "");
        if (celular != null) {
            if (celular.toString().length() < 3) {
                errors.put("celular", "El celular no puede tener menos de 3 dígitos");
            } else if(celular.toString().length() > 15){
                errors.put("celular", "El celular no puede tener más de 15 dígitos");
            }
        }
        return errors;
    }

    

}
