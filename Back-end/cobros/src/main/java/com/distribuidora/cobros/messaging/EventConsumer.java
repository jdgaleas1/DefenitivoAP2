package com.distribuidora.cobros.messaging;

import com.distribuidora.cobros.service.CobroService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EventConsumer {

    private final CobroService service;

    public EventConsumer(CobroService service) {
        this.service = service;
    }

    @RabbitListener(queues = "inventario-ok-queue")
    public void recibirInventarioOk(Map<String, Object> evento) {
        System.out.println("💰 Evento INVENTARIO_OK recibido: " + evento);

        String ordenId = (String) evento.get("ordenId");
        service.procesarCobro(ordenId);
    }
}
