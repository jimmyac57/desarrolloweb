package com.cc5002.tarea.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/*author:Jimmy Aguilera*/

public class DeleteFileDTO {
    
    @NotBlank(message = "Debe ingresar un motivo.")
    @Size(max = 200, message = "El motivo no puede tener más de 200 caracteres.")
    private String motivo;

    @NotNull(message = "El id de contacto debe ser proporcionado")
    private Integer idContacto;

    @NotNull(message = "El id de dispositivo debe ser proporcionado")
    private Integer idDispositivo;

    @NotNull(message = "El id de archivo debe ser proporcionado")
    private Integer idArchivo;

    public Integer getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(Integer idArchivo) {
        this.idArchivo = idArchivo;
    }

    public Integer getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(Integer idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public Integer getIdContacto() {
        return idContacto;
    }

    public void setIdContacto(Integer idContacto) {
        this.idContacto = idContacto;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
