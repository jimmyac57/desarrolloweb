package com.cc5002.tarea.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cc5002.tarea.entities.Region;
import com.cc5002.tarea.services.AppService;

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

    @GetMapping({"/donacion","/donacion/"})
    public String donacion(Model model) {
        List<Region> regiones = appService.getRegiones();
        if (!model.containsAttribute("exito")) {
            model.addAttribute("exito", false);
        }
        if (!model.containsAttribute("errores")) {
            Map<String, String> errores = new HashMap<>();
            errores.put("nombre", "");
            errores.put("email", "");
            errores.put("celular", "");
            errores.put("region", "");
            errores.put("comuna", "");
            errores.put("dispositivo", "");
            errores.put("descripcion", "");
            errores.put("tipo", "");
            errores.put("uso", "");
            errores.put("estado", "");
            model.addAttribute("errores", errores);
        }
        model.addAttribute("regiones", regiones);
        return "donacion";
    }

    @PostMapping("/validacion")
    public String validar(
        @RequestParam(required = false) String nombre,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) Integer celular, 
        @RequestParam(required = false) Integer region,
        @RequestParam(required = false) Integer comuna,
        @RequestParam(required = false) String dispositivo,
        @RequestParam(required = false) String descripcion, 
        @RequestParam(required=false) Integer tipo,
        @RequestParam(required=false) Integer uso,
        @RequestParam(required=false) Integer estado,
        @RequestParam(required=false) MultipartFile[] archivos, 
        RedirectAttributes redirectAttributes
        ) {
            
        Map<String, String> errores = appService.handlePostRequest(
            nombre,
            email,
            celular,
            region,
            comuna,
            dispositivo,
            descripcion,
            tipo,
            uso,
            estado,
            archivos
        );

        // Verificar si existen errores reales (valores no vacíos)
        boolean hayErrores = errores.values().stream().anyMatch(error -> !error.isEmpty());

        // Si hay errores, redirigir de nuevo al formulario y no establecer "exito"
        if (hayErrores) {
            redirectAttributes.addFlashAttribute("errores", errores);

            return "redirect:/donacion";
        }

        System.out.println("No hay errores");
        redirectAttributes.addFlashAttribute("exito", true);
        return "redirect:/donacion";
    }

    @GetMapping({"/dispositivos","/dispositivos/"})
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
