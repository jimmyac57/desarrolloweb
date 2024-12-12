package com.cc5002.tarea.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cc5002.tarea.entities.Comentario;
import com.cc5002.tarea.entities.Comuna;
import com.cc5002.tarea.entities.Region;
import com.cc5002.tarea.services.ApiService;

/*author:Jimmy Aguilera*/

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;

    }

    @GetMapping("/comuna/{region_id}")
    public Map<String, List<Comuna>> getComunasByRegionEndPoint(@PathVariable("region_id") Integer regionId) {
        List<Comuna> comunas = apiService.getComunasByRegionApi(regionId);
        return Map.of("comunas", comunas);
    }

    @GetMapping("/regiones")
    public Map<String, List<Region>> getRegionesEndPoint() {
        List<Region> region = apiService.getRegionesApi();
        return Map.of("data", region);
    }

    @GetMapping("/listadispositivos/{page}")
    public Map<String, Object> getListaDeDispositivosApiEndPoint(@PathVariable("page") Integer page) {
        return Map.of("data", apiService.getListaDeDispositivosApi(page, 5));
    }

    @GetMapping("/info/{id}")
    public Map<String, Map<String, Object>> findDispositivoConInformacionCompletaEndPoint(
            @PathVariable("id") Integer id) {
        List<Object[]> data = apiService.findDispositivoConInfoApi(id);

        Map<String, Object> contactoInfo = new HashMap<>();
        Map<String, Object> dispositivoInfo = new HashMap<>();
        List<String> archivos = new ArrayList<>();

        for (Object[] row : data) {
            contactoInfo.put("nombre", row[0]);
            contactoInfo.put("email", row[1]);
            contactoInfo.put("celular", row[2]);
            contactoInfo.put("region", row[3]);
            contactoInfo.put("comuna", row[4]);
            dispositivoInfo.put("id", row[5]);
            dispositivoInfo.put("nombre", row[6]);
            dispositivoInfo.put("tipo", row[7]);
            dispositivoInfo.put("anosUso", row[8]);
            dispositivoInfo.put("estado", row[9]);
            String rutaCompleta = row[10] + "/" + row[11];
            archivos.add(rutaCompleta);
        }
        Map<String, Object> resultData = Map.of("contacto", contactoInfo, "dispositivo", dispositivoInfo, "archivos",
                archivos);
        return Map.of("data", resultData);

    }

    @GetMapping("/comentarios/{id}")
    List<Comentario> findComentariosByDispositivoIdEndPoint(@PathVariable("id") Integer id) {
        return apiService.getComentariosByIdDispositivosApi(id);
    }

    @GetMapping("/graficos/dispositivos")
    public List<Map<String, Object>> obtenerDispositivosPorTipoEndPoint() {
        List<Object[]> resultados = apiService.obtenerDispositivosPorTipoApi();
        return resultados.stream().map(resultado -> {
            Map<String, Object> dispositivoData = new HashMap<>();
            dispositivoData.put("tipo", resultado[0]);
            dispositivoData.put("totalDispositivos", resultado[1]);
            return dispositivoData;
        }).collect(Collectors.toList());
    }

    @GetMapping("/graficos/contactos")
    public List<Map<String, Object>> obtenerContactosPorComunaEndPoint() {
        List<Object[]> resultados = apiService.obtenerContactosPorComunaApi();


        return resultados.stream()
                .map(resultado -> {
                    Map<String, Object> comunaData = new HashMap<>();
                    comunaData.put("id", resultado[0]); 
                    comunaData.put("comuna", resultado[1]); 
                    comunaData.put("totalContactos", resultado[2]); 
                    return comunaData;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/admin/data")
    public List<Map<String,Object>> obtenerArchivosEndPoint(){
        List<Object[]> resultados = apiService.obtenerArchivosApi();

        return resultados.stream()
            .map(data -> {
                Map<String, Object> archivoData = new HashMap<>();
                archivoData.put("id_archivo", data[0]);
                archivoData.put("ruta_archivo", data[1]+"/"+data[2]);
                archivoData.put("nombre_dispositivo", data[3]);
                archivoData.put("id_contacto", data[4]);
                archivoData.put("email_contacto", data[5]);
                archivoData.put("id_dispositivo", data[6]);
                return archivoData;
            })
            .collect(Collectors.toList());
    }

}
