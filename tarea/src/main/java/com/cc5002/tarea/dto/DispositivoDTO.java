package com.cc5002.tarea.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/*author:Jimmy Aguilera*/

public class DispositivoDTO {

    @NotEmpty(message = "El nombre del dispositivo es obligatorio.")
    @Size(min = 3, max = 80, message = "El nombre del dispositivo debe tener entre 3 y 80 caracteres.")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "El nombre del dispositivo solo puede contener letras y números.")
    @NotBlank(message = "El nombre del dispositivo no puede estar en blanco.")
    private String nombre;

    @Pattern(regexp = "^$|.{5,200}", message = "La descripción debe tener entre 5 y 200 caracteres si se proporciona.")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "La descripción solo puede contener letras y números.")
    private String descripcion;

    @NotNull(message = "El tipo de dispositivo es obligatorio.")
    @Min(value = 1, message = "El tipo de dispositivo seleccionado no es válido.")
    @Max(value = 11, message = "El tipo de dispositivo seleccionado no es válido.")
    private Integer tipo;

    @NotNull(message = "Los años de uso son obligatorios.")
    @Min(value = 1, message = "Los años de uso deben ser mayores a 0.")
    @Max(value = 99, message = "Los años de uso deben ser menores a 100.")
    @Digits(integer = 2, fraction = 0, message = "Los años de uso deben ser un número entero de 1 o 2 dígitos.")
    private Integer uso;

    @NotNull(message = "El estado es obligatorio.")
    @Min(value = 1, message = "El estado seleccionado no es válido.")
    @Max(value = 3, message = "El estado seleccionado no es válido.")
    private Integer estado;

    @Size(min = 1, message = "Debe adjuntar al menos un archivo.")
    @Size(max = 3, message = "No puede subir más de 3 archivos por dispositivo.")
    private List<MultipartFile> archivos = null;

    @Valid
    private List<ArchivoDTO> archivosGuardados = new ArrayList<>();

    // Método para convertir los MultipartFiles en ArchivoDTO
    public void convertirArchivosAGuardados() {
        this.archivosGuardados.clear();
        for (MultipartFile archivo : this.archivos) {
            ArchivoDTO archivoDTO = new ArchivoDTO();
            archivoDTO.setArchivo(archivo);
            this.archivosGuardados.add(archivoDTO);
        }
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getUso() {
        return uso;
    }

    public void setUso(Integer uso) {
        this.uso = uso;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public List<MultipartFile> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<MultipartFile> archivos) {
        this.archivos = archivos;
    }

    public List<ArchivoDTO> getArchivosGuardados() {
        return archivosGuardados;
    }

    public void setArchivosGuardados(List<ArchivoDTO> archivosGuardados) {
        this.archivosGuardados = archivosGuardados;
    }

    @Override
    public String toString() {
        return "DispositivoDTO{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", tipo='" + tipo + '\'' +
                ", uso=" + uso +
                ", estado='" + estado + '\'' +
                ", archivos=" + (archivos != null ? archivos.size() + " archivos adjuntos" : "sin archivos") +
                ", archivosGuardados=" + archivosGuardados +
                '}';
    }
}
