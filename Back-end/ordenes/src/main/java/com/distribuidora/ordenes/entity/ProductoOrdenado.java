package com.distribuidora.ordenes.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import java.util.UUID;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

@Data
@Embeddable
public class ProductoOrdenado {
    private String sku;
    private Integer cantidad;
}
