package com.distribuidora.despacho.service;

import com.distribuidora.despacho.entity.Despacho;
import com.distribuidora.despacho.repository.DespachoRepository;
import com.distribuidora.despacho.messaging.EventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DespachoService {

    private final DespachoRepository repo;
    private final EventPublisher publisher;

    public DespachoService(DespachoRepository repo, EventPublisher publisher) {
        this.repo = repo;
        this.publisher = publisher;
    }

    // ✅ POST: crear despacho manual
    public Despacho crear(Despacho despacho) {
        despacho.setFechaDespacho(LocalDateTime.now());
        return repo.save(despacho);
    }

    // ✅ GET: todos
    public List<Despacho> listarTodos() {
        return repo.findAll();
    }

    // ✅ GET: por ID
    public Optional<Despacho> obtenerPorId(UUID id) {
        return repo.findById(id);
    }

    // ✅ PUT: actualizar estado o dirección
    public Optional<Despacho> actualizar(UUID id, Despacho nuevo) {
        return repo.findById(id).map(d -> {
            d.setDireccion(nuevo.getDireccion());
            d.setEstado(nuevo.getEstado());
            return repo.save(d);
        });
    }

    // ✅ DELETE
    public boolean eliminar(UUID id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    // 📨 Lógica para evento COBRO_OK
    public void procesarDespacho(String ordenId) {
        Despacho despacho = new Despacho();
        despacho.setOrdenId(ordenId);
        despacho.setDireccion("Dirección generada automáticamente");
        despacho.setEstado("PREPARANDO");
        despacho.setFechaDespacho(LocalDateTime.now());

        repo.save(despacho);
        publisher.publicarListoParaEnvio(ordenId);
    }
}
