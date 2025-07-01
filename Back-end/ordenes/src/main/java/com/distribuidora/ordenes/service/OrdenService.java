package com.distribuidora.ordenes.service;

import com.distribuidora.ordenes.entity.Orden;
import com.distribuidora.ordenes.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.distribuidora.ordenes.messaging.EventPublisher;
import java.util.stream.Collectors;

import java.util.List;
import java.util.UUID;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;


@Service
public class OrdenService {
    private final OrdenRepository repo;
    private final EventPublisher eventPublisher;

    public OrdenService(OrdenRepository repo, EventPublisher publisher) {
        this.repo = repo;
        this.eventPublisher = publisher;
    }

    public Orden crearOrden(Orden orden) {
        orden.setFechaCreacion(LocalDateTime.now());
        orden.setEstado("CREADA");

        Orden guardada = repo.save(orden); // <-- Se guarda la orden

        // 🔽 Aquí va tu código para extraer productos y publicar el evento real
        List<Map<String, Object>> productosMap = guardada.getProductos().stream()
                .map(p -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("sku", p.getSku());
                    map.put("cantidad", p.getCantidad());
                    return map;
                })
                .collect(Collectors.toList());


        eventPublisher.publicarOrdenCreada(guardada.getId(), productosMap);

        return guardada;
    }

    public List<Orden> obtenerTodas() {
        return repo.findAll();
    }

    public Optional<Orden> obtenerPorId(UUID id) {
        return repo.findById(id);
    }

    public Orden actualizarOrden(UUID id, Orden actualizada) {
        return repo.findById(id)
                .map(o -> {
                    o.setCliente(actualizada.getCliente());
                    o.setTotal(actualizada.getTotal());
                    o.setEstado(actualizada.getEstado());
                    return repo.save(o);
                })
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
    }

    public void eliminarOrden(UUID id) {
        repo.deleteById(id);
    }
}
