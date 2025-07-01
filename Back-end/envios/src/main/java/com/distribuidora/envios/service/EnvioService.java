package com.distribuidora.envios.service;

import com.distribuidora.envios.entity.Envio;
import com.distribuidora.envios.repository.EnvioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class EnvioService {

    private final EnvioRepository repo;

    public EnvioService(EnvioRepository repo) {
        this.repo = repo;
    }

    // 🔁 Procesar evento LISTO_PARA_ENVIO
    public Envio procesarEnvio(String ordenId) {
        Envio envio = new Envio();
        envio.setOrdenId(ordenId);
        envio.setTransportadora("FastExpress");
        envio.setTracking("TRK-" + UUID.randomUUID().toString().substring(0, 8));
        envio.setEstado("EN_RUTA");
        envio.setFechaEnvio(LocalDateTime.now());

        return repo.save(envio);
    }

    // ✅ CRUD para REST
    public Envio crear(Envio envio) {
        envio.setFechaEnvio(LocalDateTime.now());
        return repo.save(envio);
    }

    public List<Envio> listarTodos() {
        return repo.findAll();
    }

    public Optional<Envio> obtenerPorId(UUID id) {
        return repo.findById(id);
    }

    public Optional<Envio> actualizar(UUID id, Envio actualizado) {
        return repo.findById(id).map(e -> {
            e.setEstado(actualizado.getEstado());
            e.setTracking(actualizado.getTracking());
            e.setTransportadora(actualizado.getTransportadora());
            return repo.save(e);
        });
    }

    public boolean eliminar(UUID id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}
