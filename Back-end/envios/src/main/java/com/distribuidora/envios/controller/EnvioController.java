package com.distribuidora.envios.controller;

import com.distribuidora.envios.entity.Envio;
import com.distribuidora.envios.service.EnvioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/envios")
public class EnvioController {

    private final EnvioService service;

    public EnvioController(EnvioService service) {
        this.service = service;
    }

    // ✅ POST /envios
    @PostMapping
    public ResponseEntity<Envio> crear(@RequestBody Envio envio) {
        Envio creado = service.crear(envio);
        return ResponseEntity.status(201).body(creado);
    }

    // ✅ GET /envios
    @GetMapping
    public List<Envio> listarTodos() {
        return service.listarTodos();
    }

    // ✅ GET /envios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Envio> obtenerPorId(@PathVariable UUID id) {
        Optional<Envio> envio = service.obtenerPorId(id);
        return envio.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // ✅ PUT /envios/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Envio> actualizar(@PathVariable UUID id, @RequestBody Envio actualizado) {
        Optional<Envio> envioActualizado = service.actualizar(id, actualizado);
        return envioActualizado.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // ✅ DELETE /envios/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        boolean eliminado = service.eliminar(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
