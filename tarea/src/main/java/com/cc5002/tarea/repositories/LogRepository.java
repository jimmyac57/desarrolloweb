package com.cc5002.tarea.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cc5002.tarea.entities.Log;
/*author:Jimmy Aguilera*/
@Repository
public interface LogRepository extends JpaRepository<Log, Integer> {
}
