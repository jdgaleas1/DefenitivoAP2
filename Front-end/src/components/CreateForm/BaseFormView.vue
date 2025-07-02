<script setup>
import { ref, computed } from 'vue'

// Props del componente
const props = defineProps({
  // Configuración básica
  title: {
    type: String,
    required: true
  },
  subtitle: {
    type: String,
    default: ''
  },
  headerIcon: {
    type: Object,
    required: true
  },
  
  // Estados
  loading: {
    type: Boolean,
    default: false
  },
  error: {
    type: String,
    default: null
  },
  success: {
    type: Boolean,
    default: false
  },
  successMessage: {
    type: String,
    default: '¡Registro creado exitosamente!'
  },
  
  // Configuración del formulario
  formData: {
    type: Object,
    required: true
  },
  formErrors: {
    type: Object,
    default: () => ({})
  },
  isValidForm: {
    type: Boolean,
    default: true // Cambiado a true por defecto
  },
  
  // Textos personalizables
  submitButtonText: {
    type: String,
    default: 'Crear'
  },
  loadingText: {
    type: String,
    default: 'Creando...'
  },
  backButtonText: {
    type: String,
    default: 'Volver a la lista'
  },
  clearButtonText: {
    type: String,
    default: 'Limpiar'
  },
  requiredFieldsText: {
    type: String,
    default: 'Los campos marcados con * son obligatorios'
  },
  
  // Configuración de botones
  showClearButton: {
    type: Boolean,
    default: true
  },
  showBackButton: {
    type: Boolean,
    default: true
  }
})

// Eventos emitidos
const emit = defineEmits([
  'submit',
  'clear',
  'back'
])

// Funciones
const handleSubmit = () => {
  emit('submit')
}

const handleClear = () => {
  emit('clear')
}

const handleBack = () => {
  emit('back')
}
</script>

<template>
  <div class="bg-gradient-to-br from-blue-50 via-indigo-50 to-purple-50">
    <div class="container mx-auto px-4 py-8">
      <!-- Header -->
      <div class="mb-8">
        <div class="flex items-center justify-between">
          <div class="flex items-center space-x-4">
            <div class="w-12 h-12 bg-gradient-to-r from-blue-500 to-purple-600 rounded-xl flex items-center justify-center text-white shadow-lg">
              <component :is="headerIcon" class="w-6 h-6" />
            </div>
            <div>
              <h1 class="text-3xl font-bold text-gray-900">{{ title }}</h1>
              <p v-if="subtitle" class="text-gray-600 mt-1">{{ subtitle }}</p>
            </div>
          </div>
          
          <!-- Botón de volver -->
          <button 
            v-if="showBackButton"
            @click="handleBack" 
            class="inline-flex items-center px-4 py-2 border border-gray-300 text-sm font-medium rounded-lg text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-4 focus:ring-blue-100 transition-all duration-200 hover:shadow-md">
            <svg class="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"/>
            </svg>
            {{ backButtonText }}
          </button>
        </div>
      </div>

      <!-- Alertas -->
      <div v-if="success" class="mb-6 p-4 bg-gradient-to-r from-green-50 to-emerald-50 border border-green-200 rounded-lg">
        <div class="flex items-center">
          <svg class="w-5 h-5 text-green-600 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
          </svg>
          <span class="text-green-800 font-medium">{{ successMessage }}</span>
        </div>
      </div>

      <div v-if="error" class="mb-6 p-4 bg-gradient-to-r from-red-50 to-rose-50 border border-red-200 rounded-lg">
        <div class="flex items-center">
          <svg class="w-5 h-5 text-red-600 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
          </svg>
          <span class="text-red-800 font-medium">{{ error }}</span>
        </div>
      </div>

      <!-- Formulario -->
      <div class="bg-white rounded-2xl shadow-xl border border-gray-100 overflow-hidden">
        <div class="p-8">
          <form @submit.prevent="handleSubmit" class="space-y-6">
            <!-- Slot para el contenido del formulario - CORREGIDO -->
            <slot :formData="formData" :formErrors="formErrors"></slot>

            <!-- Botones -->
            <div class="flex items-center justify-end space-x-4 pt-6 border-t border-gray-200">
              <button
                v-if="showClearButton"
                type="button"
                @click="handleClear"
                class="px-6 py-3 border border-gray-300 text-sm font-medium rounded-lg text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-4 focus:ring-gray-100 transition-all duration-200"
                :disabled="loading"
              >
                {{ clearButtonText }}
              </button>
              
              <button
                type="submit"
                :disabled="!isValidForm || loading"
                class="px-8 py-3 bg-gradient-to-r from-blue-500 to-indigo-600 text-white text-sm font-medium rounded-lg hover:from-blue-600 hover:to-indigo-700 focus:outline-none focus:ring-4 focus:ring-blue-100 transition-all duration-200 disabled:opacity-50 disabled:cursor-not-allowed hover:shadow-lg"
              >
                <span v-if="loading" class="flex items-center">
                  <svg class="animate-spin -ml-1 mr-3 h-4 w-4 text-white" fill="none" viewBox="0 0 24 24">
                    <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"/>
                    <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"/>
                  </svg>
                  {{ loadingText }}
                </span>
                <span v-else>{{ submitButtonText }}</span>
              </button>
            </div>
          </form>
        </div>
      </div>

      <!-- Información adicional -->
      <div class="mt-8 text-center">
        <p class="text-gray-500 text-sm">
          {{ requiredFieldsText }}
        </p>
        
        <!-- Slot para información adicional -->
        <slot name="additional-info" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  max-width: 1200px;
}
</style>