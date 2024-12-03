package com.cc5002.tarea.services;

import com.cc5002.tarea.entities.dispositivo.Dispositivo;
import org.springframework.stereotype.Service;

import com.cc5002.tarea.entities.dispositivo.DispositivoRepository;

import java.util.List;

@Service
public class DispositivoService {

    private final DispositivoRepository dispositivoRepository;

    public DispositivoService(DispositivoRepository dispositivoRepository) {
        this.dispositivoRepository = dispositivoRepository;
    }

    public List<Dispositivo> obtenerDispositivosPorContactoId(Integer contactoId) {
        return dispositivoRepository.findByContacto_Id(contactoId);
    }
}
