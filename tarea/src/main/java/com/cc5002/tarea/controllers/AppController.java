package com.cc5002.tarea.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cc5002.tarea.entities.Region;
import com.cc5002.tarea.repositories.RegionRepository;

@Controller
@RequestMapping("/")
public class AppController {

    @Autowired
    private RegionRepository regionRepository;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/donacion/")
    public String donacion(Model model) {
        List<Region> regiones = regionRepository.findAll();
        model.addAttribute("regiones", regiones);
        return "donacion";
    }

    @PostMapping("/validacion/")
    public String validar() {
        return "tasks";
    }

    @GetMapping("/dispositivos/")
    public String dispositivos() {
        return "ver-dispositivos";
    }

    

    @GetMapping("/dispositivos/{page}/")
    public String dispositivos(@PathVariable Integer page) {
        return "ver-dispositivos";
    }

    @GetMapping("/dispositivos/info/{id}/")
    public String info(@PathVariable Integer id) {
        return "informacion-dispositivo";

    }
    
}
