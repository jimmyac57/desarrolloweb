package com.cc5002.tarea.controllers;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cc5002.tarea.dto.DispositivoDTO;
import com.cc5002.tarea.dto.DonacionFormDTO;
import com.cc5002.tarea.dto.ComentarioDTO;
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
    public String dispositivos(@RequestParam(defaultValue = "0") Integer page, Model model) {
        Map<String, Page<Object[]>> result = appService.getListaDeDispositivos(page, 5);
        model.addAttribute("dispositivos", result.get("dispositivos"));
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
        return "donacionForm";
    }

    @PostMapping({ "/validar", "/validar/" })
    public String procesarFormulario(
            @Valid @ModelAttribute("donacionFormDTO") DonacionFormDTO donacionFormDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        Map<String, Object> result = appService.handlePostRequest(donacionFormDTO, bindingResult,
                appService.getRegiones());

        if (result.containsKey("errors")) {
            //pasar los errores
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.donacionFormDTO",
                    result.get("errors"));
            //pasar el input que ya se habia ingresado
            redirectAttributes.addFlashAttribute("donacionFormDTO", result.get("donacionFormDTO"));
            redirectAttributes.addFlashAttribute("regiones", result.get("regiones"));
            redirectAttributes.addFlashAttribute("comunas", result.get("comunas"));
            redirectAttributes.addFlashAttribute("exito", false);
            return "redirect:/donacion";
        }

        appService.guardarDonacion(donacionFormDTO);
        redirectAttributes.addFlashAttribute("exito", true);
        System.out.println(donacionFormDTO.toString());
        return "redirect:/donacion";

    }

    @GetMapping({ "/informaciondispositivos/{id}", "/informaciondispositivos/{id}/" })
    public String info(@PathVariable Integer id, Model model) {
        Map<String, Object> data = appService.findDispositivosConInfo(id);
        model.addAttribute("data", data);
        if (!model.containsAttribute("comentarioDTO")) {
            ComentarioDTO dto = new ComentarioDTO();
            model.addAttribute("comentarioDTO", dto);
        }
        if (!model.containsAttribute("exito")) {
            model.addAttribute("exito", false);
        }
        return "informacion-dispositivos";
    }

    @PostMapping({ "/validacioncomentario", "/validacioncomentario/" })
    public String procesarComentario(
            @RequestParam("deviceId") Integer id,
            @Valid @ModelAttribute("comentarioDTO") ComentarioDTO comentarioDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        System.out.println("ID del dispositivo a validar comentario: " + id);

        if (bindingResult.hasErrors()) {
            //pasar los errores 
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.comentarioDTO",
                    bindingResult);
            //pasar el input que ya se habia ingresado
            redirectAttributes.addFlashAttribute("comentarioDTO", comentarioDTO);
            return "redirect:/informaciondispositivos/" + id;
        }

        appService.guardarComentario(comentarioDTO, id);

        redirectAttributes.addFlashAttribute("exito", true);
        return "redirect:/informaciondispositivos/" + id;
    }

    @GetMapping({ "/graficodispositivo", "/graficodispositivo/" })
    public String graficoDispositivo() {
        return "grafico-dispositivos";
    }

    @GetMapping({ "/graficocontacto", "/graficocontacto/" })
    public String graficoContactoByComuna() {
        return "grafico-contactos";
    }



    


}
