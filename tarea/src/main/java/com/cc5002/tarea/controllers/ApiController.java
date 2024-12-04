package com.cc5002.tarea.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cc5002.tarea.entities.Comuna;
import com.cc5002.tarea.repositories.ComunaRepository;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ComunaRepository comunaRepository;

    @GetMapping("/region/{region_id}")
    public List<Comuna> obtenerComunasPorRegion(@PathVariable Integer region_id) {
        return comunaRepository.findByRegionId(region_id);
    }
}
