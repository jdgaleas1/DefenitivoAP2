<script setup>
import { ref, onMounted, computed } from 'vue'
import BaseTableView from '@/components/BaseTableView.vue'
import CreateButton from '@/components/Buttons/CreateButton.vue'
import { PageName } from '@/components/PageName'
import apiService from '@/services/api'

// Título de la página
PageName.value = 'Ordenes'

// Estado reactivo
const ordenes = ref([])
const loading = ref(false)
const error = ref(null)
const page = ref(1)
const itemsPerPage = 10
const searchQuery = ref('')

// Configuración de columnas actualizada
const columns = [
  { key: 'id', label: 'ID' },
  { key: 'cliente', label: 'Cliente' },
  { key: 'productos', label: 'Productos' },
  { key: 'total', label: 'Total' },
  { key: 'estado', label: 'Estado' },
  { key: 'fechaCreacion', label: 'Fecha' },
  { key: 'acciones', label: 'Acciones' }
]

// Filtrado y paginado actualizado
const filteredOrdenes = computed(() => {
  if (!searchQuery.value) return ordenes.value
  return ordenes.value.filter(orden =>
    orden.cliente?.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
    orden.estado?.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
    orden.id?.toString().includes(searchQuery.value)
  )
})

const paginatedOrdenes = computed(() => {
  const start = (page.value - 1) * itemsPerPage
  return filteredOrdenes.value.slice(start, start + itemsPerPage)
})

const totalPages = computed(() =>
  Math.ceil(filteredOrdenes.value.length / itemsPerPage)
)

const totalCount = computed(() => filteredOrdenes.value.length)

// Funciones
const cargarOrdenes = async () => {
  loading.value = true
  error.value = null
  try {
    const res = await apiService.obtenerOrdenes()
    ordenes.value = res.data || res // Ajustar según la respuesta de tu API
  } catch (err) {
    error.value = 'Error al cargar las ordenes'
    console.error(err)
  } finally {
    loading.value = false
  }
}

const eliminarOrden = async id => {
  if (!confirm('¿Eliminar esta orden?')) return
  try {
    await apiService.eliminarOrden(id)
    await cargarOrdenes()
  } catch (err) {
    error.value = 'Error al eliminar la orden'
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

// Función auxiliar para formatear fecha
const formatearFecha = (fecha) => {
  if (!fecha) return 'N/A'
  return new Date(fecha).toLocaleDateString('es-ES', {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}

// Función auxiliar para obtener el color del estado
const getEstadoColor = (estado) => {
  switch (estado?.toLowerCase()) {
    case 'creada':
      return 'bg-gradient-to-r from-blue-500 to-blue-600 text-white'
    case 'procesando':
      return 'bg-gradient-to-r from-yellow-500 to-orange-600 text-white'
    case 'completada':
      return 'bg-gradient-to-r from-emerald-500 to-green-600 text-white'
    case 'cancelada':
      return 'bg-gradient-to-r from-red-500 to-rose-600 text-white'
    default:
      return 'bg-gradient-to-r from-gray-500 to-gray-600 text-white'
  }
}

// Función auxiliar para contar productos
const contarProductos = (productos) => {
  if (!productos || !Array.isArray(productos)) return 0
  return productos.reduce((total, producto) => total + (producto.cantidad || 0), 0)
}

// Ícono personalizado para ordenes
const orderIcon = {
  template: `
    <svg fill="none" stroke="currentColor" viewBox="0 0 24 24">
      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"/>
    </svg>
  `
}

onMounted(cargarOrdenes)
</script>

<template>
  <BaseTableView
    title="Gestión de Ordenes"
    subtitle="Panel de Control"
    :header-icon="orderIcon"
    :loading="loading"
    :error="error"
    loading-text="Cargando ordenes..."
    :show-stats="true"
    stats-label="Total de Ordenes"
    :stats-value="totalCount.toLocaleString()"
    :show-search="true"
    :search-query="searchQuery"
    search-placeholder="Buscar por cliente, estado o ID..."
    :columns="columns"
    :items="paginatedOrdenes"
    empty-message="No se encontraron ordenes"
    empty-sub-message="Intenta con otros términos de búsqueda o agrega nuevas ordenes"
    :show-pagination="true"
    :total-pages="totalPages"
    @retry="cargarOrdenes"
    @search="onSearch"
    @clear-search="onClearSearch"
  >
    
    <!-- Botón de crear en el slot de acciones -->
    <template #actions>
      <CreateButton 
        route="crearOrden"
        text="Nueva Orden"
      />
    </template>
  
    <!-- Slot para las filas de la tabla -->
    <template #table-rows="{ items }">
      <tr v-for="orden in items" 
          :key="orden.id" 
          class="hover:bg-gradient-to-r hover:from-blue-50 hover:to-indigo-50 transition-all duration-200 group">
        
        <!-- ID -->
        <td class="px-6 py-4 whitespace-nowrap">
          <div class="flex items-center">
            <div class="w-8 h-8 bg-gradient-to-r from-purple-500 to-indigo-600 rounded-lg flex items-center justify-center text-white text-xs font-bold">
              #
            </div>
            <div class="ml-3">
              <p class="text-sm font-medium text-gray-900">{{ orden.id?.toString().slice(-8) }}</p>
              <p class="text-xs text-gray-500">UUID</p>
            </div>
          </div>
        </td>
        
        <!-- Cliente -->
        <td class="px-6 py-4 whitespace-nowrap">
          <div class="flex items-center space-x-3">
            <div class="w-10 h-10 bg-gradient-to-br from-blue-100 to-blue-200 rounded-lg flex items-center justify-center">
              <svg class="w-5 h-5 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
              </svg>
            </div>
            <div>
              <p class="text-gray-900 font-medium">{{ orden.cliente || 'Sin cliente' }}</p>
              <p class="text-gray-500 text-sm">Cliente</p>
            </div>
          </div>
        </td>
        
        <!-- Productos -->
        <td class="px-6 py-4 whitespace-nowrap">
          <div class="flex items-center space-x-3">
            <div class="w-10 h-10 bg-gradient-to-br from-green-100 to-green-200 rounded-lg flex items-center justify-center">
              <svg class="w-5 h-5 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4"/>
              </svg>
            </div>
            <div>
              <p class="text-gray-900 font-medium">{{ orden.productos?.length || 0 }} productos</p>
              <p class="text-gray-500 text-sm">{{ contarProductos(orden.productos) }} unidades</p>
            </div>
          </div>
        </td>

        <!-- Total -->
        <td class="px-6 py-4 whitespace-nowrap">
          <div class="flex items-center space-x-2">
            <span class="text-lg font-bold text-gray-900">
              ${{ orden.total?.toLocaleString('es-ES', { minimumFractionDigits: 2 }) || '0.00' }}
            </span>
          </div>
        </td>

        <!-- Estado -->
        <td class="px-6 py-4 whitespace-nowrap">
          <span class="inline-flex items-center px-3 py-1 rounded-full text-xs font-semibold shadow-sm" 
                :class="getEstadoColor(orden.estado)">
            {{ orden.estado || 'Sin estado' }}
          </span>
        </td>

        <!-- Fecha -->
        <td class="px-6 py-4 whitespace-nowrap">
          <div class="text-sm text-gray-900">{{ formatearFecha(orden.fechaCreacion) }}</div>
        </td>
        
        <!-- Acciones -->
        <td class="px-6 py-4 whitespace-nowrap">
          <div class="flex items-center space-x-2">
            <!--
            <router-link :to="{ name: 'verOrden', params: { id: orden.id } }" 
                       class="inline-flex items-center px-3 py-2 border border-transparent text-sm font-medium rounded-lg text-white bg-gradient-to-r from-green-500 to-emerald-600 hover:from-green-600 hover:to-emerald-700 focus:outline-none focus:ring-4 focus:ring-green-100 transition-all duration-200 hover:shadow-lg hover:scale-105 active:scale-95">
              <svg class="w-4 h-4 mr-1.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/>
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"/>
              </svg>
              Ver
            </router-link>
            -->
            <router-link :to="{ name: 'editarOrden', params: { id: orden.id } }" 
                       class="inline-flex items-center px-3 py-2 border border-transparent text-sm font-medium rounded-lg text-white bg-gradient-to-r from-blue-500 to-indigo-600 hover:from-blue-600 hover:to-indigo-700 focus:outline-none focus:ring-4 focus:ring-blue-100 transition-all duration-200 hover:shadow-lg hover:scale-105 active:scale-95">
              <svg class="w-4 h-4 mr-1.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"/>
              </svg>
              Editar
            </router-link>
            
            <button @click="eliminarOrden(orden.id)" 
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