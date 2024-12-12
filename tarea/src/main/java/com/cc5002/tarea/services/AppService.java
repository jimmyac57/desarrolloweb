package com.cc5002.tarea.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.cc5002.tarea.dto.ArchivoDTO;
import com.cc5002.tarea.dto.ComentarioDTO;
import com.cc5002.tarea.dto.DispositivoDTO;
import com.cc5002.tarea.dto.DonacionFormDTO;
import com.cc5002.tarea.entities.Archivo;
import com.cc5002.tarea.entities.Comentario;
import com.cc5002.tarea.entities.Comuna;
import com.cc5002.tarea.entities.Contacto;
import com.cc5002.tarea.entities.Dispositivo;
import com.cc5002.tarea.entities.Log;
import com.cc5002.tarea.entities.Region;
import com.cc5002.tarea.repositories.ArchivoRepository;
import com.cc5002.tarea.repositories.ComentarioRepository;
import com.cc5002.tarea.repositories.ContactoRepository;
import com.cc5002.tarea.repositories.DispositivoRepository;
import com.cc5002.tarea.repositories.LogRepository;

import java.nio.file.Path;
import java.nio.file.Paths;
/*author:Jimmy Aguilera*/
@Service
public class AppService {

    private final ApiService apiService;
    private final ContactoRepository contactoRepository;
    private final DispositivoRepository dispositivoRepository;
    private final ArchivoRepository archivoRepository;
    private final ComentarioRepository comentarioRepository;
    private final LogRepository logRepository;

    public AppService(
        ApiService apiService, 
        ContactoRepository contactoRepository,
        DispositivoRepository dispositivoRepository, 
        ArchivoRepository archivoRepository, 
        ComentarioRepository comentarioRepository,
        LogRepository logRepository) {
            this.apiService = apiService;
            this.contactoRepository = contactoRepository;
            this.dispositivoRepository = dispositivoRepository;
            this.archivoRepository = archivoRepository;
            this.comentarioRepository = comentarioRepository;
            this.logRepository = logRepository;
    }

    @Value("${app.static.dir}")
    private String staticDir;

    public List<Region> getRegiones() {
        return apiService.getRegionesApi();
    }


    public Map<String, Object> handlePostRequest(DonacionFormDTO donacionFormDTO, BindingResult bindingResult,
            List<Region> regiones) {
        Map<String, Object> response = new HashMap<>();

        // Convierte los archivos en ArchivoDTO
        for (DispositivoDTO dispositivo : donacionFormDTO.getDispositivos()) {
            dispositivo.convertirArchivosAGuardados();
        }

        // Validar región
        if (donacionFormDTO.getRegion() != null && !validarRegion(donacionFormDTO.getRegion())) {
            bindingResult.rejectValue("region", "Invalid.region",
                    "La Región seleccionada no es válida. Pruebe con las opciones proporcionadas.");
        }

        // Validar comuna
        if (donacionFormDTO.getRegion() != null && donacionFormDTO.getComuna() != null) {
            Map<String, String> erroresComuna = validateComuna(donacionFormDTO.getComuna(),
                    donacionFormDTO.getRegion());
            if (!erroresComuna.isEmpty()) {
                bindingResult.rejectValue("comuna", "Invalid.comuna", erroresComuna.get("comuna"));
            }
        }

        validarArchivos(donacionFormDTO.getDispositivos(), bindingResult);

        if (bindingResult.hasErrors()) {
            response.put("regiones", regiones);
            if (donacionFormDTO.getRegion() != null) {
                response.put("comunas", apiService.getComunasByRegionApi(donacionFormDTO.getRegion()));
                response.put("region", apiService.getRegionActualApi(donacionFormDTO.getRegion()));
                if (donacionFormDTO.getComuna() != null && donacionFormDTO.getRegion() != null) {
                    response.put("comuna", apiService.getComunaActualApi(donacionFormDTO.getRegion(),
                            donacionFormDTO.getComuna()));
                }
            }
            response.put("errors", bindingResult);
            response.put("donacionFormDTO", donacionFormDTO);
        } else {
            response.put("exito", true);
        }

        return response;
    }

    public Boolean validarRegion(Integer regionId) {
        return apiService.existsRegionByIdApi(regionId);
    }

    public Map<String, String> validateComuna(Integer comunaId, Integer regionId) {
        Map<String, String> errores = new HashMap<>();

        if (!apiService.existsComunaByIdApi(comunaId)) {
            errores.put("comuna", "La Comuna seleccionada no existe, pruebe con las opciones proporcionadas");
        } else if (!apiService.existsByIdAndRegionIdApi(regionId, comunaId)) {
            errores.put("comuna", "La Comuna seleccionada no pertenece a la Región especificada.");
        }

        return errores;
    }

    public void validarArchivos(List<DispositivoDTO> dispositivos, BindingResult bindingResult) {
        for (int i = 0; i < dispositivos.size(); i++) {
            DispositivoDTO dispositivo = dispositivos.get(i);
            for (MultipartFile archivo : dispositivo.getArchivos()) {
                String _originalFilename = archivo.getOriginalFilename();
                if (_originalFilename == null || _originalFilename.isEmpty()) {
                    bindingResult.rejectValue(
                            "dispositivos[" + i + "].archivos",
                            "Invalid.archivos",
                            "Seleccionar un archivo es obligatorio");
                } else if (_originalFilename.length() > 255) {
                    bindingResult.rejectValue(
                            "dispositivos[" + i + "].archivos",
                            "Invalid.archivos",
                            "El nombre del archivo " + archivo.getOriginalFilename()
                                    + " excede el tamaño máximo permitido de 255 caracteres.");

                } else if (_originalFilename.contains("..")) {
                    bindingResult.rejectValue(
                            "dispositivos[" + i + "].archivos",
                            "Invalid.archivos",
                            "El nombre del archivo " + archivo.getOriginalFilename()
                                    + " contiene caracteres inválidos.");
                } else if (archivo.getSize() == 0) {
                    bindingResult.rejectValue(
                            "dispositivos[" + i + "].archivos",
                            "Invalid.archivos",
                            "Uno o más archivos no contienen datos.");
                } else {
                    String _extension = _originalFilename.substring(_originalFilename.lastIndexOf('.') + 1)
                            .toLowerCase();
                    if (!_extension.matches("jpg|jpeg|png|gif")) {
                        bindingResult.rejectValue(
                                "dispositivos[" + i + "].archivos",
                                "Invalid.archivos",
                                "El archivo " + archivo.getOriginalFilename()
                                        + " tiene una extensión inválida. Extensiones permitidas: jpg, jpeg, png, gif.");
                    }
                }
            }
        }
    }

    public void guardarDonacion(DonacionFormDTO dto) {
        Comuna comuna = apiService.getComunaActualApi(dto.getRegion(), dto.getComuna());
        Contacto contacto = guardarContacto(dto.getNombre(), dto.getEmail(), dto.getCelular(), comuna);
        for (DispositivoDTO dispositivoDTO : dto.getDispositivos()) {
            Dispositivo dispositivo = guardarDispositivo(contacto, dispositivoDTO);
            try {
                guardarArchivos(dispositivoDTO.getArchivosGuardados(), dispositivo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Contacto guardarContacto(String nombre, String email, String celular, Comuna comuna) {
        Contacto contacto = new Contacto();
        contacto.setNombre(nombre);
        contacto.setEmail(email);
        contacto.setCelular(celular);
        contacto.setComuna(comuna);
        contacto.setFecha(LocalDateTime.now());

        return contactoRepository.save(contacto);

    }

    private Dispositivo guardarDispositivo(Contacto contacto, DispositivoDTO dispositivoDTO) {
        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setNombre(dispositivoDTO.getNombre());
        String tipoMapeado= Dispositivo.mapearTipo(dispositivoDTO.getTipo());
        dispositivo.setTipo(tipoMapeado);
        dispositivo.setAnosUso(dispositivoDTO.getUso());
        dispositivo.setDescripcion(dispositivoDTO.getDescripcion());
        dispositivo.setContacto(contacto);
        String estadoMapeado= Dispositivo.mapearEstado(dispositivoDTO.getEstado());
        dispositivo.setEstado(estadoMapeado);

        return dispositivoRepository.save(dispositivo);

    }

    private void guardarArchivos(List<ArchivoDTO> archivosdto, Dispositivo dispositivo) throws IOException {

        for (ArchivoDTO archivo : archivosdto) {
            String _originalFilename = archivo.getArchivo().getOriginalFilename();

            // Validar el nombre del archivo
            if (_originalFilename == null || _originalFilename.isEmpty()) {
                throw new IllegalArgumentException("El archivo no tiene un nombre válido.");
            }

            // Generar un hash único para el archivo
            MessageDigest md;
            try {
                md = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("SHA-256 algorithm not found.", e);
            }
            md.update(_originalFilename.getBytes("UTF-8"));
            byte[] hash = md.digest();
            String _filename;
            try (Formatter formatter = new Formatter()) {
                for (byte b : hash) {
                    formatter.format("%02x", b);
                }
                _filename = formatter.toString();
            }

            // Validar la extensión del archivo
            String _extension = _originalFilename.substring(_originalFilename.lastIndexOf('.') + 1).toLowerCase();
            if (!_extension.matches("jpg|jpeg|png|gif")) {
                throw new IllegalArgumentException("Extensión de archivo inválida: " + _extension);
            }

            // ruta del archivo y la carpeta
            String imgFilename = _filename + "." + _extension;
            String uploadDir = staticDir + "/uploads";
            String deviceDirectoryPath = uploadDir + "/"+ dispositivo.getId();
            String finalPath = deviceDirectoryPath + "/" + imgFilename;
            Path deviceDirectory = Paths.get(deviceDirectoryPath);

            // Crear el directorio de uploads si no existe
            Path directoryPath = Paths.get(uploadDir);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            // crear el directorio del id del dispositivo si no existe
            if (!Files.exists(deviceDirectory)) {
                try {
                    Files.createDirectories(deviceDirectory);
                } catch (IOException e) {
                    throw new RuntimeException(
                            "No se pudo crear el directorio para el dispositivo con ID: " + dispositivo.getId(), e);
                }
            }

            

            // Guardar el archivo en el sistema
            Path path = Paths.get(finalPath);
            try (InputStream inputStream = archivo.getArchivo().getInputStream()) {
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("No se pudo guardar el archivo.", e);
            }

            Archivo archivoEntity = new Archivo();
            String resumePathImg = "/uploads/" + dispositivo.getId();
            archivoEntity.setNombreArchivo(imgFilename);
            archivoEntity.setRutaArchivo(resumePathImg);
            archivoEntity.setDispositivo(dispositivo);

            // Guardar la entidad Archivo en la base de datos
            archivoRepository.save(archivoEntity);
        }
    }

    public String mapearTipo(Integer indice) {
        String[] tipos = {
            "pantalla", "notebook", "tablet", "celular",
            "consola", "mouse", "teclado", "impresora",
            "parlante", "audífonos", "otro"
        };
        return tipos[indice - 1];
    }

    public String mapearEstado(Integer indice) {
        String[] estados = {
            "funciona perfecto", "funciona a medias", "no funciona"
        };
        return estados[indice - 1];
    }
    
    public Map<String,Page<Object[]>> getListaDeDispositivos(Integer page, Integer size) {
        Page<Object[]> dispositivos = apiService.getListaDeDispositivosApi(page, size);
        return Map.of("dispositivos", dispositivos);
    }

    public Map<String,Object> findDispositivosConInfo(Integer id){
        List<Object[]> data = apiService.findDispositivoConInfoApi(id);
        Map<String, Object> contactoInfo = new HashMap<>();
        Map<String, Object> dispositivoInfo = new HashMap<>();
        List<String> archivos = new ArrayList<>();
        for (Object[] row : data) {
            contactoInfo.put("nombre", row[0]);
            contactoInfo.put("email", row[1]);
            contactoInfo.put("telefono", row[2]);
            contactoInfo.put("region", row[3]);
            contactoInfo.put("comuna", row[4]);
            dispositivoInfo.put("id_dispositivo", row[5]);
            dispositivoInfo.put("nombre", row[6]);
            dispositivoInfo.put("tipo", row[7]);
            dispositivoInfo.put("anosUso", row[8]);
            dispositivoInfo.put("estado", row[9]);
            String rutaCompleta = row[10] + "/" + row[11];
            archivos.add(rutaCompleta);
        }
        return Map.of("contacto", contactoInfo, "dispositivo", dispositivoInfo, "archivos", archivos);
    }

    public void guardarComentario(ComentarioDTO dto, Integer id) {
        Comentario comentario = new Comentario();
        comentario.setNombre(dto.getNombre());
        comentario.setTexto(dto.getTexto());
        comentario.setFecha(LocalDateTime.now());
        Dispositivo dispositivo = apiService.getDispositivoByIdApi(id);
        comentario.setDispositivo(dispositivo);
        System.out.println("\n");
        System.out.println(comentario.toString());
        System.out.println("\n");
        comentarioRepository.save(comentario);
    }


    public void deleteFile(Integer id_archivo) {
        archivoRepository.deleteById(id_archivo);
    }


    public String addLog(String motivo, Integer id){
        String logMessage = "Eliminando archivo " + id + " por usuario admin, motivo: " + motivo;
        Log log = new Log();
        log.setMotivo(logMessage);
        log.setFecha(LocalDateTime.now());
        logRepository.save(log);
        return "Archivo eliminado";
    }
}
