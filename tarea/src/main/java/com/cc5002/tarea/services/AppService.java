package com.cc5002.tarea.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.cc5002.tarea.dto.ArchivoDTO;
import com.cc5002.tarea.dto.DispositivoDTO;
import com.cc5002.tarea.dto.DonacionFormDTO;
import com.cc5002.tarea.entities.Archivo;
import com.cc5002.tarea.entities.Comuna;
import com.cc5002.tarea.entities.Contacto;
import com.cc5002.tarea.entities.Dispositivo;
import com.cc5002.tarea.entities.Region;
import com.cc5002.tarea.repositories.ArchivoRepository;
import com.cc5002.tarea.repositories.ComunaRepository;
import com.cc5002.tarea.repositories.ContactoRepository;
import com.cc5002.tarea.repositories.DispositivoRepository;
import com.cc5002.tarea.repositories.RegionRepository;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AppService {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private ComunaRepository comunaRepository;

    @Autowired
    private ContactoRepository contactoRepository;

    @Autowired
    private ArchivoRepository archivoRepository;

    private final String pathStatic;

    public AppService() throws IOException {
        Path staticDir = Paths.get(ResourceUtils.getFile("classpath:static").getAbsolutePath());
        this.pathStatic = staticDir.toString();
        System.out.println("la ruta es:" + pathStatic);
    }

    public List<Region> getRegiones() {
        return regionRepository.findAll();
    }

    public List<Dispositivo> getDispositivosByPage(Integer page) {
        return dispositivoRepository.findAll();
    }

    public List<Comuna> getComunasByRegion(Integer regionId) {
        return comunaRepository.findByRegionId(regionId);
    }

    public Comuna getInitComuna(Integer region_id, Integer comuna_id) {
        return comunaRepository.findByIdAndRegionId(region_id, comuna_id);
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
            System.out.println("Validando comuna" + donacionFormDTO.getComuna());
            Map<String, String> erroresComuna = validateComuna(donacionFormDTO.getComuna(),
                    donacionFormDTO.getRegion());
            if (!erroresComuna.isEmpty()) {
                bindingResult.rejectValue("comuna", "Invalid.comuna", erroresComuna.get("comuna"));
            }
        }

        validarArchivos(donacionFormDTO.getDispositivos(), bindingResult);

        // Ahora revisa si hay errores
        if (bindingResult.hasErrors()) {
            // Cargar datos para el formulario
            response.put("regiones", regiones);
            if (donacionFormDTO.getRegion() != null) {
                response.put("comunas", comunaRepository.findByRegionId(donacionFormDTO.getRegion()));
                response.put("region", regionRepository.findById(donacionFormDTO.getRegion()));
                if (donacionFormDTO.getComuna() != null && donacionFormDTO.getRegion() != null) {
                    response.put("comuna", comunaRepository.findByIdAndRegionId(donacionFormDTO.getRegion(),
                            donacionFormDTO.getComuna()));
                }
            }
            response.put("errors", bindingResult);
            System.out.println("Errores en el formulario archivo:" + donacionFormDTO.toString());
            response.put("donacionFormDTO", donacionFormDTO);
        } else {
            response.put("exito", true);
        }

        return response;
    }

    public Boolean validarRegion(Integer regionId) {
        return regionRepository.existsById(regionId);
    }

    public Map<String, String> validateComuna(Integer comunaId, Integer regionId) {
        Map<String, String> errores = new HashMap<>();

        if (!comunaRepository.existsById(comunaId)) {
            errores.put("comuna", "La Comuna seleccionada no existe, pruebe con las opciones proporcionadas");
        } else if (!comunaRepository.existsByIdAndRegionId(regionId, comunaId)) {
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
                    // Si un archivo tiene nombre vacío, añade un error
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
                } else if (archivo.getSize() > 5 * 1024 * 1024) {
                    bindingResult.rejectValue(
                            "dispositivos[" + i + "].archivos",
                            "Invalid.archivos",
                            "El archivo " + archivo.getOriginalFilename()
                                    + " excede el tamaño máximo permitido de 5 MB.");
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
        Comuna comuna = comunaRepository.findById(dto.getComuna()).get();
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
        System.out.println("Static path: " + pathStatic);

        for (ArchivoDTO archivo : archivosdto) {
            String _originalFilename = archivo.getArchivo().getOriginalFilename();
            System.out.println("Nombre del archivo: " + _originalFilename);

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
            System.out.println("Nombre del archivo único: " + _filename);

            // Validar la extensión del archivo
            String _extension = _originalFilename.substring(_originalFilename.lastIndexOf('.') + 1).toLowerCase();
            if (!_extension.matches("jpg|jpeg|png|gif")) {
                throw new IllegalArgumentException("Extensión de archivo inválida: " + _extension);
            }

            // Construir el nombre del archivo final y la ruta
            String imgFilename = _filename + "." + _extension;
            System.out.println("Nombre del archivo final: " + imgFilename);
            String directoryPathImg = "/uploads/" + dispositivo.getId();
            String relativePathImg = directoryPathImg + "/" + imgFilename;
            String finalPath = pathStatic + relativePathImg;

            String deviceDirectoryPath = pathStatic + "/uploads/" + dispositivo.getId();
            Path deviceDirectory = Paths.get(deviceDirectoryPath);

            if (!Files.exists(deviceDirectory)) {
                try {
                    Files.createDirectories(deviceDirectory);
                    System.out.println("Directorio creado para el dispositivo con ID: " + dispositivo.getId());
                } catch (IOException e) {
                    throw new RuntimeException(
                            "No se pudo crear el directorio para el dispositivo con ID: " + dispositivo.getId(), e);
                }
            }

            System.out.println("Final image path: " + finalPath);

            // Crear el directorio de uploads si no existe
            Path directoryPath = Paths.get(pathStatic + "/uploads");
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
                System.out.println("Directorio de uploads creado.");
            }

            // Guardar el archivo en el sistema
            Path path = Paths.get(finalPath);
            try (InputStream inputStream = archivo.getArchivo().getInputStream()) {
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Archivo guardado: " + archivo.getArchivo().getOriginalFilename() + "con tamaño"
                        + archivo.getArchivo().getBytes() + " en: " + path.toAbsolutePath());
                System.out.println("Archivo guardado correctamente en: " + path.toAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException("No se pudo guardar el archivo.", e);
            }

            // Crear una nueva entidad Archivo
            Archivo archivoEntity = new Archivo();
            archivoEntity.setNombreArchivo(imgFilename);
            archivoEntity.setRutaArchivo(directoryPathImg);
            archivoEntity.setDispositivo(dispositivo);

            // Guardar la entidad Archivo en la base de datos
            archivoRepository.save(archivoEntity);
            System.out.println("Archivo registrado en la base de datos con nombre: " + imgFilename);
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
    

}
