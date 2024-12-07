package com.cc5002.tarea.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cc5002.tarea.entities.Contacto;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Integer> {
}
