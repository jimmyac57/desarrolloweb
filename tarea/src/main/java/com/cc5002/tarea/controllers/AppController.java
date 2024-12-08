package com.cc5002.tarea.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cc5002.tarea.dto.DispositivoDTO;
import com.cc5002.tarea.dto.DonacionFormDTO;
import com.cc5002.tarea.services.AppService;

import jakarta.validation.Valid;

/*author:Jimmy Aguilera*/

@Controller
@RequestMapping("/")
public class AppController {
    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping({ "/dispositivos", "/dispositivos/" })
    public String dispositivos() {
        
        return "ver-dispositivos";
    }

    @GetMapping({ "/donacion/", "/donacion" })
    public String mostrarFormulario(Model model) {
        if (!model.containsAttribute("donacionFormDTO")) {
            DonacionFormDTO dto = new DonacionFormDTO();
            dto.getDispositivos().add(new DispositivoDTO());
            model.addAttribute("donacionFormDTO", dto);
        }
        if (!model.containsAttribute("regiones")) {
            model.addAttribute("regiones", appService.getRegiones());
        }
        if (!model.containsAttribute("exito")) {
            model.addAttribute("exito", false);
        }
        return "trydto";
    }

    @PostMapping({ "/validar", "/validar/" })
    public String procesarFormulario(
            @Valid @ModelAttribute("donacionFormDTO") DonacionFormDTO donacionFormDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        Map<String, Object> result = appService.handlePostRequest(donacionFormDTO, bindingResult,
                appService.getRegiones());

        if (result.containsKey("errors")) {
            // Preparar el contexto de errores
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.donacionFormDTO",
                    result.get("errors"));

            redirectAttributes.addFlashAttribute("donacionFormDTO", result.get("donacionFormDTO"));
            redirectAttributes.addFlashAttribute("regiones", result.get("regiones"));
            redirectAttributes.addFlashAttribute("comunas", result.get("comunas"));
            redirectAttributes.addFlashAttribute("exito", false);
            return "redirect:/donacion";
        }

        // Si todo es válido, continuar con la lógica posterior
        appService.guardarDonacion(donacionFormDTO);
        redirectAttributes.addFlashAttribute("exito", true);
        System.out.println(donacionFormDTO.toString());
        return "redirect:/donacion";
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
