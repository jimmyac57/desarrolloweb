package com.cc5002.tarea.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cc5002.tarea.entities.dispositivo.DispositivoRepository;


@Controller
@RequestMapping("/dispositivos")
public class VerDispositivosController {

    @Autowired
    private DispositivoRepository dispositivoRepository;

    @GetMapping("/")
    public String mostrarDispositivos(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "5") Integer size,
        Model model) {

        Pageable pageable = PageRequest.of(page, size);
        List<Object[]> dispositivos = dispositivoRepository.obtenerDatosDispositivos(pageable);

        model.addAttribute("dispositivos", dispositivos);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);

        return "ver-dispositivos";
    }
    
}
