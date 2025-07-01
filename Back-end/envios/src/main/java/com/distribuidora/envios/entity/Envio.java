package com.distribuidora.envios.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Envio {

    @Id
    @GeneratedValue
    private UUID id;

    private String ordenId;
    private String transportadora;
    private String tracking;
    private String estado;
    private LocalDateTime fechaEnvio;
}
