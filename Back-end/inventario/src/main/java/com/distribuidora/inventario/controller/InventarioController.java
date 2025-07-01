package com.distribuidora.inventario.controller;

import com.distribuidora.inventario.entity.Producto;
import com.distribuidora.inventario.service.InventarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    private final InventarioService servicio;

    public InventarioController(InventarioService servicio) {
        this.servicio = servicio;
    }

    // ✅ POST: registrar producto
    @PostMapping("/productos")
    public Producto registrarProducto(@RequestBody Producto producto) {
        return servicio.registrarProducto(producto);
    }

    // ✅ GET: listar todos los productos
    @GetMapping("/productos")
    public List<Producto> listarProductos() {
        return servicio.listarProductos();
    }

    // ✅ GET: obtener producto por SKU
    @GetMapping("/productos/{sku}")
    public Producto obtenerProducto(@PathVariable String sku) {
        return servicio.obtenerProducto(sku);
    }

    // ✅ PUT: actualizar producto
    @PutMapping("/productos/{sku}")
    public Producto actualizarProducto(@PathVariable String sku, @RequestBody Producto producto) {
        return servicio.actualizarProducto(sku, producto);
    }

    // ✅ DELETE: eliminar producto
    @DeleteMapping("/productos/{sku}")
    public void eliminarProducto(@PathVariable String sku) {
        servicio.eliminarProducto(sku);
    }

    // 🔁 POST: descontar stock (para eventos de ventas)
    @PostMapping("/descontar-stock")
    public boolean descontarStock(@RequestBody Map<String, Integer> productos) {
        return servicio.descontarStock(productos);
    }
}