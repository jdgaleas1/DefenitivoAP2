package com.distribuidora.inventario.service;

import com.distribuidora.inventario.entity.Producto;
import com.distribuidora.inventario.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InventarioService {

    private final ProductoRepository repo;

    public InventarioService(ProductoRepository repo) {
        this.repo = repo;
    }

    // 🔁 Lógica de evento: descontar stock
    public boolean descontarStock(Map<String, Integer> productos) {
        for (Map.Entry<String, Integer> entry : productos.entrySet()) {
            String sku = entry.getKey();
            Integer cantidad = entry.getValue();

            Producto p = repo.findById(sku).orElse(null);
            if (p == null || p.getStock() < cantidad) return false;

            p.setStock(p.getStock() - cantidad);
            repo.save(p);
        }
        return true;
    }

    // ✅ POST: registrar producto
    public Producto registrarProducto(Producto producto) {
        return repo.save(producto);
    }

    // ✅ GET todos
    public List<Producto> listarProductos() {
        return repo.findAll();
    }

    // ✅ GET uno
    public Producto obtenerProducto(String sku) {
        return repo.findById(sku).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    // ✅ PUT: actualizar producto
    public Producto actualizarProducto(String sku, Producto actualizado) {
        Producto producto = repo.findById(sku)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setNombre(actualizado.getNombre());
        producto.setStock(actualizado.getStock());
        return repo.save(producto);
    }

    // ✅ DELETE: eliminar producto
    public void eliminarProducto(String sku) {
        if (!repo.existsById(sku)) {
            throw new RuntimeException("Producto no encontrado");
        }
        repo.deleteById(sku);
    }
}