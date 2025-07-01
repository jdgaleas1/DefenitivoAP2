package com.distribuidora.inventario.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Producto {
    @Id
    private String sku;
    private String nombre;
    private Integer stock;
}
