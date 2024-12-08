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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String nombre;

    @NotNull
    private String tipo;
    
    @NotNull
    private Integer anosUso;

    private String descripcion;

    
    @ManyToOne
    @NotNull
    @JoinColumn(name = "contacto_id", nullable = false)
    private Contacto contacto;

    @NotNull
    private String estado;

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
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

    public String getEstado() {
        return estado;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
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

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public static String mapearTipo(Integer tipo){
        Map<Integer, String> tipos = new HashMap<>();
        tipos.put(1, "pantalla");
        tipos.put(2, "notebook");
        tipos.put(3, "tablet");
        tipos.put(4, "celular");
        tipos.put(5, "consola");
        tipos.put(6, "mouse");
        tipos.put(7, "teclado");
        tipos.put(8, "impresora");
        tipos.put(9, "parlante");
        tipos.put(10, "audifonos");
        tipos.put(11, "otro");
        return tipos.get(tipo);
    }

    public static String mapearEstado(Integer estado){
        Map<Integer, String> estados = new HashMap<>();
        estados.put(1, "funciona perfecto");
        estados.put(2, "funciona a medias");
        estados.put(3, "no funciona");
        return estados.get(estado);
    }

    public Dispositivo() {
    }

    public Dispositivo(Contacto contacto, String nombre,  String descripcion, String tipo, Integer anosUso, String estado) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.anosUso = anosUso;
        this.descripcion = descripcion;
        this.contacto = contacto;
        this.estado = estado;
    }


}
