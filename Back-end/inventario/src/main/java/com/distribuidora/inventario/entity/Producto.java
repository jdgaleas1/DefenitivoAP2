package com.distribuidora.inventario.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
public class Producto {
    @Id
    private String sku;

    private String nombre;

    private Integer stock;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal precio = BigDecimal.ZERO; // Valor por defecto
}