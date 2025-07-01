package com.distribuidora.envios.messaging;

import com.distribuidora.envios.service.EnvioService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EventConsumer {

    private final EnvioService service;

    public EventConsumer(EnvioService service) {
        this.service = service;
    }

    @RabbitListener(queues = "despacho-listo-queue")
    public void recibirEventoDespachoListo(Map<String, Object> evento) {
        System.out.println("📦 Evento LISTO_PARA_ENVIO recibido: " + evento);
        String ordenId = (String) evento.get("ordenId");
        service.procesarEnvio(ordenId);
    }
}
