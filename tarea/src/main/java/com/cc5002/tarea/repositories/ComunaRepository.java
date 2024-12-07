package com.cc5002.tarea.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cc5002.tarea.entities.Comuna;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Integer> {
    @Query("SELECT c FROM Comuna c WHERE c.region.id = :regionId")
    List<Comuna> findByRegionId(@Param("regionId") Integer regionId);

    @Query("SELECT COUNT(c)>0 FROM Comuna c WHERE c.region.id = :regionId AND c.id = :comunaId")
    Boolean existsByIdAndRegionId(@Param("regionId") Integer regionId, @Param("comunaId") Integer comunaId);

    
}
