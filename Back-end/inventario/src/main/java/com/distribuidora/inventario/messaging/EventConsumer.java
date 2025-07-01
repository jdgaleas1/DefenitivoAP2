package com.distribuidora.inventario.messaging;

import com.distribuidora.inventario.service.InventarioService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EventConsumer {

    private final InventarioService service;
    private final EventPublisher publisher;

    public EventConsumer(InventarioService service, EventPublisher publisher) {
        this.service = service;
        this.publisher = publisher;
    }

    @RabbitListener(queues = "ordenes-queue")
    public void recibirEventoOrdenCreada(Map<String, Object> evento) {
        System.out.println("📩 Evento recibido: " + evento);

        String ordenId = (String) evento.get("ordenId");
        List<Map<String, Object>> productos = (List<Map<String, Object>>) evento.get("productos");

        Map<String, Integer> stockPorSku = new HashMap<>();
        for (Map<String, Object> p : productos) {
            String sku = (String) p.get("sku");
            Integer cantidad = (Integer) p.get("cantidad");
            stockPorSku.put(sku, cantidad);
        }

        boolean ok = service.descontarStock(stockPorSku);
        if (ok) {
            System.out.println("✅ INVENTARIO_OK para orden: " + ordenId);
            publisher.publicarInventarioOk(ordenId);
        } else {
            System.out.println("❌ INVENTARIO_FALLO para orden: " + ordenId);
            publisher.publicarInventarioFallo(ordenId);
        }
    }
}

