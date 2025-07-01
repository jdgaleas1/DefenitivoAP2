package com.distribuidora.cobros.controller;

import com.distribuidora.cobros.entity.Cobro;
import com.distribuidora.cobros.service.CobroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cobros")
public class CobroController {

    private final CobroService service;

    public CobroController(CobroService service) {
        this.service = service;
    }

    // ✅ POST /cobros
    @PostMapping
    public ResponseEntity<Cobro> crear(@RequestBody Cobro cobro) {
        Cobro creado = service.crear(cobro);
        return ResponseEntity.status(201).body(creado);
    }

    // ✅ GET /cobros
    @GetMapping
    public List<Cobro> listarTodos() {
        return service.listarTodos();
    }

    // ✅ GET /cobros/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Cobro> obtenerPorId(@PathVariable UUID id) {
        Optional<Cobro> cobro = service.obtenerPorId(id);
        return cobro.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // ✅ PUT /cobros/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Cobro> actualizar(@PathVariable UUID id, @RequestBody Cobro actualizado) {
        Optional<Cobro> cobroActualizado = service.actualizar(id, actualizado);
        return cobroActualizado.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // ✅ DELETE /cobros/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        boolean eliminado = service.eliminar(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}