package com.cc5002.tarea.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cc5002.tarea.entities.Comuna;
import com.cc5002.tarea.entities.Contacto;
import com.cc5002.tarea.entities.Dispositivo;
import com.cc5002.tarea.entities.Region;
import com.cc5002.tarea.repositories.ComunaRepository;
import com.cc5002.tarea.repositories.DispositivoRepository;
import com.cc5002.tarea.repositories.RegionRepository;

@Service
public class AppService {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private ComunaRepository comunaRepository;

    public List<Region> getRegiones() {
        return regionRepository.findAll();
    }

    public List<Dispositivo> getDispositivosByPage(Integer page) {
        return dispositivoRepository.findAll();
    }

    public Map<String,String> handlePostRequest(
        String nombre,
        String email,
        Integer celular,
        Integer region,
        Integer comuna,
        String dispositivo,
        String descripcion,
        Integer tipo,
        Integer uso,
        Integer estado,
        MultipartFile[] archivos){
        
        Map<String, String> errores = new HashMap<>();
        
        Contacto contacto = new Contacto();

        Map<String, String> erroresContacto = Contacto.validateContacto(nombre,email,celular);

        Map<String, String> erroresRegion = validateRegion(region);

        Map<String, String> erroresComuna = validateComuna(comuna, region);

        Dispositivo newDispositivo = new Dispositivo();

        Map<String, String> erroresDispositivo = Dispositivo.validateDispositivo(dispositivo, descripcion, tipo, uso, estado);

        errores.putAll(erroresContacto);
        errores.putAll(erroresRegion);
        errores.putAll(erroresComuna);
        errores.putAll(erroresDispositivo);
    
        return errores;

    }

    public Map<String, String> validateRegion(Integer regionId) {
        Map<String, String> errores = new HashMap<>();
        if (regionId == null) {
            errores.put("region", "La región es obligatoria");
        } else if (!regionRepository.existsById(regionId)) {
            errores.put("region", "La región seleccionada no existe");
        } else {
            errores.put("region", "");
        }
        return errores;
    }
    

    public Map<String, String> validateComuna(Integer comunaId, Integer regionId) {
        Map<String, String> errores = new HashMap<>();
    
        if (comunaId == null) {
            errores.put("comuna", "El ID de la comuna no puede estar vacío.");
        } else if (!comunaRepository.existsById(comunaId)) {
            errores.put("comuna", "La Comuna seleccionada no existe.");
        } else if (!comunaRepository.existsByIdAndRegionId(comunaId, regionId)) {
            errores.put("comuna", "La Comuna seleccionada no pertenece a la Región especificada.");
        }
    
        return errores;
    }
    
    
}
