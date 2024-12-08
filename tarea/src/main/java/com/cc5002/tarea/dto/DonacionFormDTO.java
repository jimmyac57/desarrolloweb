package com.cc5002.tarea.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class DonacionFormDTO {

    @NotEmpty(message = "El nombre es obligatorio.")
    @NotBlank(message = "El nombre no puede estar en blanco.")
    @Size(min = 3, max = 80, message = "El nombre debe tener entre 3 y 80 caracteres.")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "El nombre solo puede contener letras y espacios.")
    private String nombre;

    @NotEmpty(message = "El email es obligatorio.")
    @Email(message = "Debe ser un email válido. Ejemplo: correo@dominio.cl")
    private String email;

    // No marcamos celular como obligatorio, pero podrías hacerlo si quisieras.
    private String celular;

    @NotNull(message = "La región es obligatoria.")
    private Integer region;

    @NotNull(message = "La comuna es obligatoria.")
    private Integer comuna;

    // Lista de dispositivos. Se validará cada uno si se agrega @Valid sobre la lista.
    // Podrías poner @Size para limitar la cantidad de dispositivos si lo deseas.
    @jakarta.validation.Valid
    @NotEmpty(message = "Debe agregar al menos un dispositivo.")
    private List<DispositivoDTO> dispositivos = new ArrayList<>();

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Integer getRegion() {
        return region;
    }

    public void setRegion(Integer region) {
        this.region = region;
    }

    public Integer getComuna() {
        return comuna;
    }

    public void setComuna(Integer comuna) {
        this.comuna = comuna;
    }

    public List<DispositivoDTO> getDispositivos() {
        return dispositivos;
    }

    public void setDispositivos(List<DispositivoDTO> dispositivos) {
        this.dispositivos = dispositivos;
    }

    @Override
    public String toString() {
        return "DonacionFormDTO{" +
                "nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", celular='" + celular + '\'' +
                ", region=" + region +
                ", comuna=" + comuna +
                ", dispositivos=" + dispositivos.toString() +
                '}';
    }
}