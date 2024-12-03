package com.cc5002.tarea.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;
import com.cc5002.tarea.entities.contacto.Contacto;
import com.cc5002.tarea.entities.contacto.ContactoRepository;
import com.cc5002.tarea.entities.comuna.ComunaRepository;
import com.cc5002.tarea.entities.comuna.Comuna;
import com.cc5002.tarea.entities.region.Region;
import com.cc5002.tarea.entities.region.RegionRepository;

@Controller
@RequestMapping("/donacion")
public class DonacionController {

    @Autowired
    private RegionRepository regionRepository;

    @GetMapping(("/"))
    public String mostrarFormulario(Model model) {
        List<Region> regiones = regionRepository.findAll(); // Obtén las regiones
        model.addAttribute("regiones", regiones);  
        return "agregar-donacion";
    }

    @PostMapping
    public String procesarFormularioDonacion(Model model) {
        // Lógica de procesamiento de donación
        return "agregar-donacion";
        
    }
    
}
