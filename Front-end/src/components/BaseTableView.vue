<template>
  <!-- Modern Base Table View Template -->
  <div class="bg-gradient-to-br from-white to-gray-50 rounded-2xl shadow-xl border border-gray-100 overflow-hidden">
    <!-- Header Section -->
    <div class="bg-gradient-to-r from-blue-600 via-purple-600 to-indigo-700 px-8 py-6">
      <div class="flex items-center justify-between">
        <div class="flex items-center space-x-3">
          <div class="p-2 bg-white/20 rounded-lg backdrop-blur-sm">
            <component :is="headerIcon" class="w-6 h-6 text-white" />
          </div>
          <h1 class="text-2xl font-bold text-white">{{ title }}</h1>
        </div>
        <div class="text-white/80 text-sm font-medium">
          {{ subtitle || 'Panel de Control' }}
        </div>
      </div>
    </div>

    <!-- Content Section -->
    <div class="p-8">
      <!-- Loading State -->
      <div v-if="loading" class="text-center py-16">
        <div class="inline-flex items-center justify-center w-16 h-16 bg-gradient-to-r from-blue-500 to-purple-600 rounded-full mb-4">
          <div class="w-8 h-8 border-3 border-white border-t-transparent rounded-full animate-spin"></div>
        </div>
        <p class="text-gray-600 font-medium">{{ loadingText }}</p>
        <p class="text-gray-400 text-sm mt-1">Por favor espera un momento</p>
      </div>

      <!-- Error State -->
      <div v-else-if="error" class="bg-gradient-to-r from-red-50 to-pink-50 border border-red-200 rounded-xl p-6 mb-8 relative overflow-hidden">
        <div class="absolute top-0 left-0 w-full h-1 bg-gradient-to-r from-red-500 to-pink-500"></div>
        <div class="flex items-center space-x-3">
          <div class="p-2 bg-red-100 rounded-lg">
            <svg class="w-5 h-5 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
            </svg>
          </div>
          <div class="flex-1">
            <p class="text-red-800 font-medium">{{ error }}</p>
            <p class="text-red-600 text-sm mt-1">Ha ocurrido un problema al cargar los datos</p>
          </div>
          <button @click="$emit('retry')"
                  class="px-4 py-2 bg-gradient-to-r from-red-500 to-pink-500 text-white rounded-lg font-medium transition-all duration-200 hover:shadow-lg hover:scale-105 active:scale-95">
            <span class="flex items-center space-x-2">
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15"/>
              </svg>
              <span>Reintentar</span>
            </span>
          </button>
        </div>
      </div>

      <!-- Main Content -->
      <template v-else>
        <!-- Stats & Actions Bar -->
        <div class="flex flex-col lg:flex-row lg:items-center lg:justify-between gap-6 mb-8">
          <!-- Stats Card -->
          <div v-if="showStats" class="bg-gradient-to-r from-emerald-500 to-teal-600 rounded-xl p-6 text-white min-w-fit">
            <div class="flex items-center space-x-3">
              <div class="p-2 bg-white/20 rounded-lg backdrop-blur-sm">
                <component :is="statsIcon" class="w-6 h-6" />
              </div>
              <div>
                <p class="text-white/80 text-sm font-medium">{{ statsLabel }}</p>
                <p class="text-2xl font-bold">{{ statsValue }}</p>
              </div>
            </div>
          </div>

          <!-- Search Bar or Custom Actions -->
          <div class="flex-1 flex justify-end">
            <!-- Search Bar -->
            <div v-if="showSearch" class="relative max-w-md w-full">
              <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <svg class="h-5 w-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
                </svg>
              </div>
              <input :value="searchQuery" 
                     @input="$emit('search', $event.target.value)"
                     type="search" 
                     :placeholder="searchPlaceholder"
                     class="w-full pl-10 pr-4 py-3 border border-gray-200 rounded-xl focus:ring-4 focus:ring-blue-100 focus:border-blue-500 transition-all duration-200 bg-white shadow-sm placeholder-gray-400">
              <div v-if="searchQuery" class="absolute inset-y-0 right-0 pr-3 flex items-center">
                <button @click="$emit('clear-search')" class="text-gray-400 hover:text-gray-600 transition-colors">
                  <svg class="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
                  </svg>
                </button>
              </div>
            </div>
            
            <!-- Custom Actions Slot -->
            <slot name="actions"></slot>
          </div>
        </div>

        <!-- Table Container -->
        <div class="bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden">
          <div class="overflow-x-auto">
            <table class="w-full">
              <!-- Table Header -->
              <thead>
                <tr class="bg-gradient-to-r from-gray-50 to-gray-100">
                  <th v-for="column in columns" 
                      :key="column.key"
                      class="px-6 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                    {{ column.label }}
                  </th>
                </tr>
              </thead>
              
              <!-- Table Body -->
              <tbody class="divide-y divide-gray-100">
                <!-- Empty State -->
                <tr v-if="!items || items.length === 0">
                  <td :colspan="columns.length" class="px-6 py-16 text-center">
                    <div class="flex flex-col items-center space-y-3">
                      <div class="p-4 bg-gray-100 rounded-full">
                        <component :is="emptyIcon" class="w-8 h-8 text-gray-400" />
                      </div>
                      <div>
                        <p class="text-gray-500 font-medium">{{ emptyMessage }}</p>
                        <p class="text-gray-400 text-sm mt-1">{{ emptySubMessage }}</p>
                      </div>
                    </div>
                  </td>
                </tr>
                
                <!-- Data Rows - Slot for custom content -->
                <slot name="table-rows" :items="items"></slot>
              </tbody>
            </table>
          </div>
        </div>

        <!-- Pagination -->
        <div v-if="showPagination && totalPages > 1" class="mt-8 flex justify-center">
          <nav class="relative z-0 inline-flex rounded-lg shadow-sm bg-white border border-gray-200 overflow-hidden">
            <slot name="pagination"></slot>
          </nav>
        </div>
      </template>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'

// Props
const props = defineProps({
  // Header
  title: {
    type: String,
    required: true
  },
  subtitle: {
    type: String,
    default: 'Panel de Control'
  },
  headerIcon: {
    type: [String, Object],
    default: () => ({
      template: `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24">
                   <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4"/>
                 </svg>`
    })
  },
  
  // States
  loading: {
    type: Boolean,
    default: false
  },
  error: {
    type: String,
    default: null
  },
  loadingText: {
    type: String,
    default: 'Cargando datos...'
  },
  
  // Stats
  showStats: {
    type: Boolean,
    default: true
  },
  statsLabel: {
    type: String,
    default: 'Total de Elementos'
  },
  statsValue: {
    type: [String, Number],
    default: 0
  },
  statsIcon: {
    type: [String, Object],
    default: () => ({
      template: `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24">
                   <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 12l3-3 3 3 4-4M8 21l4-4 4 4M3 4h18M4 4h16v12a1 1 0 01-1 1H5a1 1 0 01-1-1V4z"/>
                 </svg>`
    })
  },
  
  // Search
  showSearch: {
    type: Boolean,
    default: true
  },
  searchQuery: {
    type: String,
    default: ''
  },
  searchPlaceholder: {
    type: String,
    default: 'Buscar...'
  },
  
  // Table
  columns: {
    type: Array,
    required: true
  },
  items: {
    type: Array,
    default: () => []
  },
  
  // Empty state
  emptyMessage: {
    type: String,
    default: 'No hay elementos disponibles'
  },
  emptySubMessage: {
    type: String,
    default: 'Comienza agregando algunos elementos'
  },
  emptyIcon: {
    type: [String, Object],
    default: () => ({
      template: `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24">
                   <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 13V6a2 2 0 00-2-2H6a2 2 0 00-2 2v7m16 0v5a2 2 0 01-2 2H6a2 2 0 01-2-2v-5m16 0h-2.586a1 1 0 00-.707.293l-2.414 2.414a1 1 0 01-.707.293h-3.172a1 1 0 01-.707-.293l-2.414-2.414A1 1 0 006.586 13H4"/>
                 </svg>`
    })
  },
  
  // Pagination
  showPagination: {
    type: Boolean,
    default: false
  },
  totalPages: {
    type: Number,
    default: 1
  }
})

// Emits
defineEmits(['retry', 'search', 'clear-search'])
</script>

<style scoped>
/* Custom animations */
@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.group:hover .scale-hover {
  transform: scale(1.05);
}

/* Smooth scrollbar */
.overflow-x-auto::-webkit-scrollbar {
  height: 6px;
}

.overflow-x-auto::-webkit-scrollbar-track {
  background: #f1f5f9;
  border-radius: 10px;
}

.overflow-x-auto::-webkit-scrollbar-thumb {
  background: linear-gradient(90deg, #3b82f6, #8b5cf6);
  border-radius: 10px;
}

.overflow-x-auto::-webkit-scrollbar-thumb:hover {
  background: linear-gradient(90deg, #2563eb, #7c3aed);
}
</style>