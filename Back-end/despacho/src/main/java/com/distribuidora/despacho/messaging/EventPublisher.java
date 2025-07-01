package com.distribuidora.despacho.messaging;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EventPublisher {

    private final AmqpTemplate rabbitTemplate;

    public EventPublisher(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarListoParaEnvio(String ordenId) {
        rabbitTemplate.convertAndSend(
                "ordenes-exchange",
                "despacho.listo",
                Map.of("evento", "LISTO_PARA_ENVIO", "ordenId", ordenId)
        );
        System.out.println("📤 Evento LISTO_PARA_ENVIO publicado para orden: " + ordenId);
    }
}
