package com.cc5002.tarea.entities.dispositivo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {
    List<Dispositivo> findByContacto_Id(Integer contactoId);

    @Query("SELECT DISTINCT c.nombre, c.email, c.celular, r.nombre, co.nombre, d.nombre, d.tipo, d.anosUso, d.estado, a.rutaArchivo, a.nombreArchivo, d.id " +
           "FROM Dispositivo d " +
           "JOIN d.contacto c " +
           "JOIN d.archivos a " +
           "JOIN c.comuna co " +
           "JOIN co.region r")
    List<Object[]> obtenerDatosDispositivos(org.springframework.data.domain.Pageable pageable);
}
