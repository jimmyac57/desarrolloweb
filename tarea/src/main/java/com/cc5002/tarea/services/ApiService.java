package com.cc5002.tarea.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cc5002.tarea.entities.Comentario;
import com.cc5002.tarea.entities.Comuna;
import com.cc5002.tarea.entities.Dispositivo;
import com.cc5002.tarea.entities.Region;
import com.cc5002.tarea.repositories.ArchivoRepository;
import com.cc5002.tarea.repositories.ComentarioRepository;
import com.cc5002.tarea.repositories.ComunaRepository;
import com.cc5002.tarea.repositories.ContactoRepository;
import com.cc5002.tarea.repositories.DispositivoRepository;
import com.cc5002.tarea.repositories.RegionRepository;
/*author:Jimmy Aguilera*/
@Service
public class ApiService {

    private final ComunaRepository comunaRepository;
    private final RegionRepository regionRepository;
    private final DispositivoRepository dispositivoRepository;
    private final ComentarioRepository comentarioRepository;
    private final ContactoRepository contactoRepository;
    private final ArchivoRepository archivoRepository;

    public ApiService(
        ComunaRepository comunaRepository, 
        RegionRepository regionRepository, 
        DispositivoRepository dispositivoRepository, 
        ComentarioRepository comentarioRepository, 
        ContactoRepository contactoRepository,
        ArchivoRepository archivoRepository) {
            this.comunaRepository = comunaRepository;
            this.regionRepository = regionRepository;
            this.dispositivoRepository = dispositivoRepository;
            this.comentarioRepository = comentarioRepository;
            this.contactoRepository = contactoRepository;
            this.archivoRepository = archivoRepository;
    }

    public List<Comuna> getComunasByRegionApi(Integer regionId) {
        return  comunaRepository.findByRegionId(regionId);
    }

    public List<Region> getRegionesApi() {
        return regionRepository.findAll();
    }

    public Boolean existsRegionByIdApi(Integer regionId) {
        return regionRepository.existsById(regionId);
    }

    public Comuna getComunaActualApi(Integer region_id,Integer comuna_id) {
        return comunaRepository.findByIdAndRegionId(region_id, comuna_id);
    }

    public Boolean existsComunaByIdApi(Integer comuna_id) {
        return comunaRepository.existsById(comuna_id);
    }

    public Boolean existsByIdAndRegionIdApi(Integer regionId, Integer comunaId) {
        return comunaRepository.existsByIdAndRegionId(regionId, comunaId);
    }

    public Region getRegionActualApi(Integer region_id) {
        return regionRepository.findById(region_id).get();
    }

    public Page<Object[]> getListaDeDispositivosApi(int page, int size) {
        Pageable pageable = PageRequest.of(page, size); 
        Page<Object[]> dispositivos = dispositivoRepository.findDispositivosWithPagination(pageable); 
        return dispositivos;
    }

    public List<Object[]> findDispositivoConInfoApi(Integer dispositivo_id) {
        return dispositivoRepository.findDispositivoConInformacionCompleta(dispositivo_id);
    }

    public List<Comentario> getComentariosByIdDispositivosApi(Integer dispositivo_id) {
        return comentarioRepository.findByDispositivoId(dispositivo_id);
    }

    public Dispositivo getDispositivoByIdApi(Integer dispositivo_id) {
        return dispositivoRepository.findById(dispositivo_id).get();
    }

    public List<Object[]> obtenerDispositivosPorTipoApi() {
        return dispositivoRepository.countByTipo();
    }

    public List<Object[]> obtenerContactosPorComunaApi() {
        return contactoRepository.getTotalContactosPorComuna();
    }

    public List<Object[]> obtenerArchivosApi() {
        return archivoRepository.getArchivos();
    }
}
