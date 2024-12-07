package com.cc5002.tarea.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc5002.tarea.entities.Comuna;
import com.cc5002.tarea.entities.Region;
import com.cc5002.tarea.repositories.ComunaRepository;
import com.cc5002.tarea.repositories.RegionRepository;

@Service
public class ApiService {

    @Autowired
    private ComunaRepository comunaRepository;

    @Autowired
    private RegionRepository regionRepository;

    public List<Comuna> getComunasByRegion(Integer regionId) {
        return  comunaRepository.findByRegionId(regionId);
    }

    public List<Region> getRegionesApi() {
        return regionRepository.findAll();
    }

    public Boolean existsRegionById(Integer regionId) {
        return regionRepository.existsById(regionId);
    }

}
