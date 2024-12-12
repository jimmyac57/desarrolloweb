package com.cc5002.tarea.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cc5002.tarea.dto.DeleteFileDTO;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import com.cc5002.tarea.services.AppService;

/*author:Jimmy Aguilera*/

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AppService appService;


    @GetMapping
    public String adminHome(Model model) {
        model.addAttribute("deleteFileDTO", new DeleteFileDTO());
        if (!model.containsAttribute("exito")) {
            model.addAttribute("exito", null);
        }
        return "admin";
    }


    @PostMapping({ "/eliminar/", "/eliminar" })
    public String eliminarArchivo(
        @Valid @ModelAttribute("deleteFileDTO") DeleteFileDTO deleteFileDTO,
        BindingResult bindingResult,
        RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            System.out.println("Errores de validación encontrados: " + bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.deleteFileDTO", bindingResult);
            redirectAttributes.addFlashAttribute("deleteFileDTO", deleteFileDTO);
            redirectAttributes.addFlashAttribute("exito", false);
            return "redirect:/admin";
        }

        appService.deleteFile(deleteFileDTO.getIdArchivo());
        appService.addLog(deleteFileDTO.getMotivo(),deleteFileDTO.getIdArchivo());
        
        redirectAttributes.addFlashAttribute("exito", true);
        redirectAttributes.addFlashAttribute("deleteFileDTO", deleteFileDTO);
        return "redirect:/admin";
    }
}
