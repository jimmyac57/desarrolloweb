package com.cc5002.tarea.services;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cc5002.tarea.entities.archivo.Archivo;
import com.cc5002.tarea.entities.archivo.ArchivoRepository;
import com.cc5002.tarea.entities.comuna.Comuna;
import com.cc5002.tarea.entities.comuna.ComunaRepository;
import com.cc5002.tarea.entities.contacto.Contacto;
import com.cc5002.tarea.entities.contacto.ContactoRepository;
import com.cc5002.tarea.entities.dispositivo.Dispositivo;
import com.cc5002.tarea.entities.dispositivo.DispositivoRepository;
import com.cc5002.tarea.entities.region.Region;
import com.cc5002.tarea.entities.region.RegionRepository;

@Service
public class AppService {
    public void handlePostRequest(
        String nombre,
        String email,
        Integer celular,
        Integer region_id,
        Integer comuna_id,
        String dispositivo,
        MultipartFile archivo) throws IOException {

        // Obtén la fecha actual
        LocalDateTime fecha_creacion = LocalDateTime.now();


        }
    

    public void validarComuna(Map<String, String> errores, ComunaRepository comunaRepository, Integer comuna_id) {
        if (comuna_id == null) {
            errores.put("comuna_id", "La comuna es obligatoria, por favor seleccione una comuna.");
        } else if (!comunaRepository.existsById(comuna_id)) {
            errores.put("comuna_id", "La comuna seleccionada no es válida.");
        }
    }
}
