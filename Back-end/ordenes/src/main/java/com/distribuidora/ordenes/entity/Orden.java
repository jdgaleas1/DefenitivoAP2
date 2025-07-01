package com.distribuidora.ordenes.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;


@Data
@Entity
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String cliente;
    private Double total;
    private String estado;
    private LocalDateTime fechaCreacion;

    @ElementCollection
    private List<ProductoOrdenado> productos; // 🔽 Aquí sí está dentro de la clase
}
