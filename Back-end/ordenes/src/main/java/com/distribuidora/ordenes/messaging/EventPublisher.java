package com.distribuidora.ordenes.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import com.distribuidora.ordenes.config.RabbitConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;



@Component
public class EventPublisher {

    private final AmqpTemplate rabbitTemplate;

    public EventPublisher(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarOrdenCreada(UUID ordenId, List<Map<String, Object>> productos) {
        Map<String, Object> evento = new HashMap<>();
        evento.put("evento", "ORDEN_CREADA");
        evento.put("ordenId", ordenId.toString());
        evento.put("productos", productos);

        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.ROUTING_KEY,
                evento
        );
    }

}
