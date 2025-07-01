package com.distribuidora.cobros.service;

import com.distribuidora.cobros.entity.Cobro;
import com.distribuidora.cobros.repository.CobroRepository;
import com.distribuidora.cobros.messaging.EventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CobroService {

    private final EventPublisher publisher;
    private final CobroRepository repo;

    public CobroService(EventPublisher publisher, CobroRepository repo) {
        this.publisher = publisher;
        this.repo = repo;
    }

    // 📨 Lógica para evento INVENTARIO_OK (mantener existente)
    public void procesarCobro(String ordenId) {
        System.out.println("💳 Procesando cobro para orden: " + ordenId);

        // Crear cobro automático
        Cobro cobro = new Cobro();
        cobro.setOrdenId(ordenId);
        cobro.setMonto(100.00); // Monto por defecto
        cobro.setMetodoPago("TARJETA");
        cobro.setEstado("PROCESANDO");
        cobro.setFechaCobro(LocalDateTime.now());
        cobro.setCodigoTransaccion("TXN-" + UUID.randomUUID().toString().substring(0, 8));

        // Simulación simple: siempre OK
        boolean aprobado = true;
        cobro.setEstado(aprobado ? "APROBADO" : "RECHAZADO");

        repo.save(cobro);
        publisher.publicarResultadoCobro(ordenId, aprobado);
        System.out.println(aprobado ? "✅ COBRO_OK" : "❌ COBRO_FALLIDO");
    }

    // ✅ CRUD para REST API
    public Cobro crear(Cobro cobro) {
        cobro.setFechaCobro(LocalDateTime.now());
        cobro.setCodigoTransaccion("TXN-" + UUID.randomUUID().toString().substring(0, 8));
        return repo.save(cobro);
    }

    public List<Cobro> listarTodos() {
        return repo.findAll();
    }

    public Optional<Cobro> obtenerPorId(UUID id) {
        return repo.findById(id);
    }

    public Optional<Cobro> actualizar(UUID id, Cobro actualizado) {
        return repo.findById(id).map(c -> {
            c.setMonto(actualizado.getMonto());
            c.setMetodoPago(actualizado.getMetodoPago());
            c.setEstado(actualizado.getEstado());
            c.setNumeroTarjeta(actualizado.getNumeroTarjeta());
            return repo.save(c);
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