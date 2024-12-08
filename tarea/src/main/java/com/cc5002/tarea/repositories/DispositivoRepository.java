package com.cc5002.tarea.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cc5002.tarea.entities.Dispositivo;

@Repository
public interface DispositivoRepository extends JpaRepository<Dispositivo, Integer> {
    @Query(value = """
    SELECT 
        d.id,
        d.tipo,
        d.nombre,
        d.estado,
        com.nombre AS comuna_nombre,
        a.ruta_archivo,
        a.nombre_archivo
    FROM dispositivo d
    JOIN contacto c ON d.contacto_id = c.id
    JOIN comuna com ON c.comuna_id = com.id
    LEFT JOIN archivo a ON d.id = a.dispositivo_id
""", nativeQuery = true)
List<Object[]> getListaDeDispositivos();
}
