package com.distribuidora.cobros.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Cobro {

    @Id
    @GeneratedValue
    private UUID id;

    private String ordenId;
    private Double monto;
    private String metodoPago;
    private String estado;
    private LocalDateTime fechaCobro;
    private String numeroTarjeta;
    private String codigoTransaccion;
}