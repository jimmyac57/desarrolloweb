package com.cc5002.tarea.entities.dispositivo;

import com.cc5002.tarea.entities.archivo.Archivo;
import com.cc5002.tarea.entities.contacto.Contacto;

import jakarta.persistence.*;

import java.util.List;

import javax.validation.constraints.NotNull;

@Entity
public class Dispositivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "contacto_id", nullable = false)
    private Contacto contacto;

    @OneToMany(mappedBy = "dispositivo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Archivo> archivos; // Relación bidireccional

    @NotNull
    private String nombre;

    private String descripcion;

    @NotNull
    private Integer anosUso;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoDispositivo tipo;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EstadoDispositivo estado;

    // Enum para TipoDispositivo
    public enum TipoDispositivo {
        pantalla, notebook, tablet, celular, consola,
    mouse, teclado, impresora, parlante, audífonos, otro
    }

    // Enum para EstadoDispositivo
    public enum EstadoDispositivo {
        FUNCIONA_PERFECTO, FUNCIONA_A_MEDIAS, NO_FUNCIONA
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

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

    public Integer getAnosUso() {
        return anosUso;
    }

    public void setAnosUso(Integer anosUso) {
        this.anosUso = anosUso;
    }

    public TipoDispositivo getTipo() {
        return tipo;
    }

    public void setTipo(TipoDispositivo tipo) {
        this.tipo = tipo;
    }

    public EstadoDispositivo getEstado() {
        return estado;
    }

    public void setEstado(EstadoDispositivo estado) {
        this.estado = estado;
    }

    public Contacto getContactoId() {
        return contacto;
    }

}
