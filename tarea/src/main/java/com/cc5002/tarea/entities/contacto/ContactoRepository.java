package com.cc5002.tarea.entities.contacto;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {
    
}
