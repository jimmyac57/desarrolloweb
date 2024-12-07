package com.cc5002.tarea.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        List<Comuna> comunas = apiService.getComunasByRegion(regionId);
        return Map.of("comunas", comunas);
    }

    @GetMapping("/regiones")
    public Map<String, List<Region>> getRegionesEndPoint() {
        List<Region> region = apiService.getRegionesApi();
        return Map.of("data", region);
    }
}
