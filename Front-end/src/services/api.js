// src/services/api.js
import axios from 'axios';

// Crear instancia sin baseURL para que proxy en vue.config.js funcione
const api = axios.create({ baseURL: '' });

export default {
  // Productos
  obtenerProductos: () => api.get('/productos'),
  eliminarProducto: id => api.delete(`/productos/${id}`),
  crearProducto: (producto) => api.post('/productos', producto),
  obtenerProducto: id => api.get(`/productos/${id}`),
  actualizarProducto: (id, producto) => api.put(`/productos/${id}`, producto),

  // Ordenes
  obtenerOrdenes: () => api.get('/ordenes'),
  eliminarOrden: id => api.delete(`/ordenes/${id}`),
  crearOrden: (orden) => api.post('/ordenes', orden),
  obtenerOrden: id => api.get(`/ordenes/${id}`),
  actualizarOrden: (id, orden) => api.put(`/ordenes/${id}`, orden),
};