package com.cc5002.tarea.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cc5002.tarea.entities.Dispositivo;
/*author:Jimmy Aguilera*/
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
    WHERE a.id = (
        SELECT MAX(a2.id)
        FROM archivo a2
        WHERE a2.dispositivo_id = d.id
    )
        ORDER BY d.id ASC
    """,
    countQuery = """
    SELECT COUNT(*)
    FROM dispositivo d
    JOIN contacto c ON d.contacto_id = c.id
    JOIN comuna com ON c.comuna_id = com.id
    LEFT JOIN archivo a ON d.id = a.dispositivo_id
    WHERE a.id = (
        SELECT MAX(a2.id)
        FROM archivo a2
        WHERE a2.dispositivo_id = d.id
    )
    """,
    nativeQuery = true)

    Page<Object[]> findDispositivosWithPagination(Pageable pageable);

    @Query(value ="""
        SELECT c.nombre,
        c.email,
        c.celular,
        r.nombre AS region_nombre,
        com.nombre AS comuna_nombre,
        d.id,
        d.nombre AS dispositivo_nombre,
        d.tipo,
        d.anos_uso,
        d.estado,
        a.ruta_archivo,
        a.nombre_archivo

        FROM dispositivo d
        JOIN contacto c ON d.contacto_id = c.id
        JOIN comuna com ON c.comuna_id = com.id
        JOIN region r ON com.region_id = r.id
        LEFT JOIN archivo a ON d.id = a.dispositivo_id
        WHERE d.id = :id
    """,nativeQuery = true)
    List<Object[]> findDispositivoConInformacionCompleta(@Param("id") Integer id);

    @Query("SELECT d.tipo AS tipo, COUNT(d) AS total FROM Dispositivo d GROUP BY d.tipo ORDER BY total DESC")
    List<Object[]> countByTipo();

}
