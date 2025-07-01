package com.distribuidora.cobros.messaging;

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

    public void publicarResultadoCobro(String ordenId, boolean aprobado) {
        Map<String, Object> evento = new HashMap<>();
        evento.put("evento", aprobado ? "COBRO_OK" : "COBRO_FALLIDO");
        evento.put("ordenId", ordenId);

        rabbitTemplate.convertAndSend("ordenes-exchange",
                aprobado ? "cobro.ok" : "cobro.fallo",
                evento);
    }
}
