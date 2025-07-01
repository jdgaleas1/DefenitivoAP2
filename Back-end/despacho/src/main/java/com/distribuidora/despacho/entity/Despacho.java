package com.distribuidora.despacho.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Despacho {

    @Id
    @GeneratedValue
    private UUID id;

    private String ordenId;
    private String direccion;
    private String estado;
    private LocalDateTime fechaDespacho;
}
