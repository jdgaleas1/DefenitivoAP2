package com.distribuidora.despacho.messaging;

import com.distribuidora.despacho.service.DespachoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EventConsumer {

    private final DespachoService service;

    public EventConsumer(DespachoService service) {
        this.service = service;
    }

    @RabbitListener(queues = "cobro-ok-queue")
    public void recibirCobroOk(Map<String, Object> evento) {
        System.out.println("📥 Evento COBRO_OK recibido: " + evento);
        String ordenId = (String) evento.get("ordenId");
        service.procesarDespacho(ordenId);
    }
}
