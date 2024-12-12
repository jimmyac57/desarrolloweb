package com.cc5002.tarea.dto;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

/*author:Jimmy Aguilera*/

public class ArchivoDTO {

    @NotEmpty(message = "Debe adjuntar un archivo.")
    private MultipartFile archivo;

    // Getters y Setters
    public MultipartFile getArchivo() {
        return archivo;
    }

    public void setArchivo(MultipartFile archivo) {
        this.archivo = archivo;
    }

    @Override
    public String toString() {
        return "ArchivoDTO{" +
                "archivo=" + (archivo != null ? archivo.getOriginalFilename() : "null") +
                '}';
    }
}
