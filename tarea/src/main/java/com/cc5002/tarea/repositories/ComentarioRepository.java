package com.cc5002.tarea.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cc5002.tarea.entities.Comentario;
/*author:Jimmy Aguilera*/
@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
    @Query("SELECT c FROM Comentario c WHERE c.dispositivo.id = :dispositivoId")
    List<Comentario> findByDispositivoId(@Param("dispositivoId") Integer dispositivoId);
}
