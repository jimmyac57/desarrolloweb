package com.cc5002.tarea.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.qos.logback.core.model.Model;

@Controller
@RequestMapping("/dispositivos/info")
public class InfoDonacionController {

    @GetMapping("/{id}")
    public String obtenerInfoDispositivo(@PathVariable("id") Integer id,Model model){
        return "info-dispositivo";
    }

}
