<script setup>
import { ref, onMounted, computed } from 'vue'
import BaseTableView from '@/components/BaseTableView.vue'
import CreateButton from '@/components/Buttons/CreateButton.vue'
import { PageName } from '@/components/PageName'
import apiService from '@/services/api'

// Título de la página
PageName.value = 'Productos'

// Estado reactivo
const productos = ref([])
const loading = ref(false)
const error = ref(null)
const page = ref(1)
const itemsPerPage = 10
const searchQuery = ref('')

// Configuración de columnas
const columns = [
  { key: 'id', label: 'ID' },
  { key: 'producto', label: 'Producto' },
  { key: 'stock', label: 'Stock' },
  { key: 'acciones', label: 'Acciones' }
]

// Filtrado y paginado
const filteredProductos = computed(() => {
  if (!searchQuery.value) return productos.value
  return productos.value.filter(p =>
    p.nombre.toLowerCase().includes(searchQuery.value.toLowerCase())
  )
})

const paginatedProductos = computed(() => {
  const start = (page.value - 1) * itemsPerPage
  return filteredProductos.value.slice(start, start + itemsPerPage)
})

const totalPages = computed(() =>
  Math.ceil(filteredProductos.value.length / itemsPerPage)
)

const totalCount = computed(() => filteredProductos.value.length)

// Funciones
const cargarProductos = async () => {
  loading.value = true
  error.value = null
  try {
    const res = await apiService.obtenerProductos()
    productos.value = res.data
  } catch (err) {
    error.value = 'Error al cargar los productos'
    console.error(err)
  } finally {
    loading.value = false
  }
}

const eliminarProducto = async id => {
  if (!confirm('¿Eliminar este producto?')) return
  try {
    await apiService.eliminarProducto(id)
    await cargarProductos()
  } catch (err) {
    error.value = 'Error al eliminar el producto'
    console.error(err)
  }
}

const onSearch = query => {
  searchQuery.value = query
  page.value = 1
}

const onClearSearch = () => {
  searchQuery.value = ''
  page.value = 1
}

// Ícono personalizado para productos
const productIcon = {
  template: `
    <svg fill="none" stroke="currentColor" viewBox="0 0 24 24">
      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4"/>
    </svg>
  `
}

onMounted(cargarProductos)
</script>

<template>
  <BaseTableView
    title="Gestión de Productos"
    subtitle="Panel de Control"
    :header-icon="productIcon"
    :loading="loading"
    :error="error"
    loading-text="Cargando productos..."
    :show-stats="true"
    stats-label="Total de Productos"
    :stats-value="totalCount.toLocaleString()"
    :show-search="true"
    :search-query="searchQuery"
    search-placeholder="Buscar productos..."
    :columns="columns"
    :items="paginatedProductos"
    empty-message="No se encontraron productos"
    empty-sub-message="Intenta con otros términos de búsqueda o agrega nuevos productos"
    :show-pagination="true"
    :total-pages="totalPages"
    @retry="cargarProductos"
    @search="onSearch"
    @clear-search="onClearSearch"
  >
    
    <!-- Botón de crear en el slot de acciones -->
    <template #actions>
      <CreateButton 
        route="crearProducto"
        text="Nuevo Producto"
      />
    </template>
  
    <!-- Slot para las filas de la tabla -->
    <template #table-rows="{ items }">
      <tr v-for="producto in items" 
          :key="producto.id" 
          class="hover:bg-gradient-to-r hover:from-blue-50 hover:to-indigo-50 transition-all duration-200 group">
        
        <!-- ID -->
        <td class="px-6 py-4 whitespace-nowrap">
          <div class="flex items-center">
            <div class="w-8 h-8 bg-gradient-to-r from-blue-500 to-purple-600 rounded-lg flex items-center justify-center text-white text-xs font-bold">
              {{ producto.id }}
            </div>
          </div>
        </td>
        
        <!-- Producto -->
        <td class="px-6 py-4 whitespace-nowrap">
          <div class="flex items-center space-x-3">
            <div class="w-10 h-10 bg-gradient-to-br from-gray-100 to-gray-200 rounded-lg flex items-center justify-center">
              <svg class="w-5 h-5 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4"/>
              </svg>
            </div>
            <div>
              <p class="text-gray-900 font-medium">{{ producto.nombre }}</p>
              <p class="text-gray-500 text-sm">Producto #{{ producto.id }}</p>
            </div>
          </div>
        </td>
        
        <!-- Stock -->
        <td class="px-6 py-4 whitespace-nowrap">
          <div class="flex items-center space-x-3">
            <span class="inline-flex items-center px-3 py-1 rounded-full text-xs font-semibold shadow-sm" 
                  :class="producto.stock > 10 
                    ? 'bg-gradient-to-r from-emerald-500 to-green-600 text-white' 
                    : 'bg-gradient-to-r from-red-500 to-rose-600 text-white'">
              {{ producto.stock }} unidades
            </span>
            <div class="w-16 bg-gray-200 rounded-full h-2">
              <div class="h-2 rounded-full transition-all duration-300" 
                   :class="producto.stock > 10 ? 'bg-gradient-to-r from-emerald-400 to-green-500' : 'bg-gradient-to-r from-red-400 to-rose-500'"
                   :style="`width: ${Math.min(producto.stock / 20 * 100, 100)}%`">
              </div>
            </div>
          </div>
        </td>
        
        <!-- Acciones -->
        <td class="px-6 py-4 whitespace-nowrap">
          <div class="flex items-center space-x-2">
            <router-link :to="{ name: 'formElement', params: { id: producto.id } }" 
                       class="inline-flex items-center px-3 py-2 border border-transparent text-sm font-medium rounded-lg text-white bg-gradient-to-r from-blue-500 to-indigo-600 hover:from-blue-600 hover:to-indigo-700 focus:outline-none focus:ring-4 focus:ring-blue-100 transition-all duration-200 hover:shadow-lg hover:scale-105 active:scale-95">
              <svg class="w-4 h-4 mr-1.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"/>
              </svg>
              Editar
            </router-link>
            
            <button @click="eliminarProducto(producto.id)" 
                    class="inline-flex items-center px-3 py-2 border border-transparent text-sm font-medium rounded-lg text-white bg-gradient-to-r from-red-500 to-rose-600 hover:from-red-600 hover:to-rose-700 focus:outline-none focus:ring-4 focus:ring-red-100 transition-all duration-200 hover:shadow-lg hover:scale-105 active:scale-95">
              <svg class="w-4 h-4 mr-1.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"/>
              </svg>
              Eliminar
            </button>
          </div>
        </td>
      </tr>
    </template>

    <!-- Slot para paginación personalizada -->
    <template #pagination>
      <v-pagination
        v-model="page"
        :pages="totalPages"
        :range-size="15"
        class="flex"
      />
    </template>
  </BaseTableView>
</template>