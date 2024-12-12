package com.cc5002.tarea.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cc5002.tarea.entities.Contacto;
/*author:Jimmy Aguilera*/
@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Integer> {
@Query("""
    SELECT 
        c.comuna.id AS comunaId, 
        c.comuna.nombre AS comunaNombre, 
        COUNT(c.id) AS totalContactos
    FROM 
        Contacto c
    GROUP BY 
        c.comuna.id, c.comuna.nombre
    ORDER BY 
        totalContactos DESC
""")
List<Object[]> getTotalContactosPorComuna();
}

