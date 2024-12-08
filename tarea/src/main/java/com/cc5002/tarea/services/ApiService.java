package com.cc5002.tarea.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc5002.tarea.entities.Comuna;
import com.cc5002.tarea.entities.Region;
import com.cc5002.tarea.repositories.ComunaRepository;
import com.cc5002.tarea.repositories.DispositivoRepository;
import com.cc5002.tarea.repositories.RegionRepository;

@Service
public class ApiService {

    @Autowired
    private ComunaRepository comunaRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private DispositivoRepository dispositivosRepository;

    public List<Comuna> getComunasByRegion(Integer regionId) {
        return  comunaRepository.findByRegionId(regionId);
    }

    public List<Region> getRegionesApi() {
        return regionRepository.findAll();
    }

    public Boolean existsRegionById(Integer regionId) {
        return regionRepository.existsById(regionId);
    }

    public List<Object[]> getListaDeDispositivosApi(){
        return dispositivosRepository.getListaDeDispositivos();
    }

}
