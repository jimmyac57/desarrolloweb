package com.cc5002.tarea.apicontrollers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cc5002.tarea.entities.comuna.Comuna;
import com.cc5002.tarea.entities.comuna.ComunaRepository;

@RestController
@RequestMapping("api/poblarcomunas")
public class PoblarComunasApiController {
    
    @Autowired
    private ComunaRepository comunaRepository;

    @GetMapping("/region/{region_id}")
    public List<Comuna> obtenerComunasPorRegion(@PathVariable Integer region_id) {
        return comunaRepository.findByRegionId(region_id); 
    }
}
