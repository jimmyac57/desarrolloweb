package com.cc5002.tarea.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cc5002.tarea.entities.Archivo;

/*author:Jimmy Aguilera*/

@Repository
public interface ArchivoRepository extends JpaRepository<Archivo, Integer> {
    @Query(value ="""
    SELECT 
        a.id AS archivo_id,
        a.ruta_archivo,
        a.nombre_archivo,
        d.nombre,
        c.id AS contacto_id,
        c.email AS contacto_email,
        d.id AS dispositivo_id
    FROM archivo a
    JOIN dispositivo d ON a.dispositivo_id = d.id
    JOIN contacto c ON d.contacto_id = c.id
    """, nativeQuery = true)
    List<Object[]> getArchivos();
}
