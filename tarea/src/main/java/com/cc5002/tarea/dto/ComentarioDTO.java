package com.cc5002.tarea.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/*author:Jimmy Aguilera*/

public class ComentarioDTO {

    @NotEmpty(message = "El nombre del comentario es obligatorio.")
    @Size(min = 3,message = "El nombre debe tener al menos 3 caracteres.")
    @Size(max = 80,message = "El nombre debe tener como máximo 80 caracteres.")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "El nombre solo puede contener letras y espacios.")
    private String nombre;

    @NotEmpty(message = "El texto del comentario es obligatorio.")
    @Size(min = 5,message = "El texto del comentario debe tener al menos 5 caracteres.")
    @Size(max = 200,message = "El texto del comentario debe tener como máximo 200 caracteres.")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "El texto del comentario solo puede contener letras y números.")
    private String texto;

    
    public String getNombre() {
        return nombre;
    }

    public String getTexto() {
        return texto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return "ComentarioDTO{" +
                "nombre='" + nombre + '\'' +
                ", texto='" + texto + '\'' +
                '}';
    }


    
}
