package com.distribuidora.inventario.messaging;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EventPublisher {

    private final AmqpTemplate rabbitTemplate;

    public EventPublisher(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarInventarioOk(String ordenId) {
        Map<String, Object> evento = new HashMap<>();
        evento.put("evento", "INVENTARIO_OK");
        evento.put("ordenId", ordenId);

        rabbitTemplate.convertAndSend("ordenes-exchange", "inventario.ok", evento);
    }

    public void publicarInventarioFallo(String ordenId) {
        Map<String, Object> evento = new HashMap<>();
        evento.put("evento", "INVENTARIO_FALLO");
        evento.put("ordenId", ordenId);

        rabbitTemplate.convertAndSend("ordenes-exchange", "inventario.fallo", evento);
    }
}
