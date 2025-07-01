package com.distribuidora.despacho.controller;

import com.distribuidora.despacho.entity.Despacho;
import com.distribuidora.despacho.service.DespachoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/despachos")
public class DespachoController {

    private final DespachoService service;

    public DespachoController(DespachoService service) {
        this.service = service;
    }

    // ✅ POST /despachos
    @PostMapping
    public ResponseEntity<Despacho> crear(@RequestBody Despacho despacho) {
        Despacho creado = service.crear(despacho);
        return ResponseEntity.status(201).body(creado);
    }

    // ✅ GET /despachos
    @GetMapping
    public List<Despacho> listarTodos() {
        return service.listarTodos();
    }

    // ✅ GET /despachos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Despacho> obtenerPorId(@PathVariable UUID id) {
        Optional<Despacho> despacho = service.obtenerPorId(id);
        return despacho.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // ✅ PUT /despachos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Despacho> actualizar(@PathVariable UUID id, @RequestBody Despacho actualizado) {
        Optional<Despacho> despachoActualizado = service.actualizar(id, actualizado);
        return despachoActualizado.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // ✅ DELETE /despachos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        boolean eliminado = service.eliminar(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
