<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { PageName } from '@/components/PageName'
import BaseFormView from '@/components/CreateForm/BaseFormView.vue'
import FormSection from '@/components/CreateForm/FormSection.vue'
import FormField from '@/components/CreateForm/FormField.vue'
import apiService from '@/services/api'

// Título de la página
PageName.value = 'Crear Producto'

// Router para navegación
const router = useRouter()

// Estado reactivo
const loading = ref(false)
const error = ref(null)
const success = ref(false)

// Formulario reactivo
const form = reactive({
  nombre: '',
  descripcion: '',
  stock: 0,
  precio: 0,
  categoria: '',
})

// Validaciones
const errors = reactive({
  nombre: '',
  stock: '',
  precio: '',
  categoria: ''
})

// Categorías predefinidas
const categorias = [
  'Electrónicos',
  'Ropa y Accesorios', 
  'Hogar y Jardín',
  'Deportes',
  'Libros',
  'Salud y Belleza',
  'Alimentos',
  'Otros'
]

// Íconos para las secciones
const basicInfoIcon = {
  template: `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24">
               <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
             </svg>`
}

const inventoryIcon = {
  template: `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24">
               <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1"/>
             </svg>`
}

const additionalIcon = {
  template: `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24">
               <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"/>
             </svg>`
}

// Función para validar el formulario
const validateForm = () => {
  // Limpiar errores previos
  Object.keys(errors).forEach(key => errors[key] = '')
  
  let isValid = true

  if (!form.nombre.trim()) {
    errors.nombre = 'El nombre del producto es obligatorio'
    isValid = false
  }

  if (form.stock < 0) {
    errors.stock = 'El stock no puede ser negativo'
    isValid = false
  }

  if (form.precio <= 0) {
    errors.precio = 'El precio debe ser mayor a 0'
    isValid = false
  }

  if (!form.categoria.trim()) {
    errors.categoria = 'La categoría es obligatoria'
    isValid = false
  }

  return isValid
}

// Función para crear producto
const crearProducto = async () => {
  if (!validateForm()) return

  loading.value = true
  error.value = null
  success.value = false

  try {
    await apiService.crearProducto(form)
    success.value = true
    
    // Limpiar formulario después del éxito
    setTimeout(() => {
      limpiarFormulario()
      success.value = false
    }, 2000)

  } catch (err) {
    error.value = 'Error al crear el producto. Por favor, intenta nuevamente.'
    console.error(err)
  } finally {
    loading.value = false
  }
}

// Función para volver a la lista
const volverALista = () => {
  router.push({ name: 'productos' })
}

// Función para limpiar formulario
const limpiarFormulario = () => {
  Object.keys(form).forEach(key => {
    form[key] = typeof form[key] === 'number' ? 0 : ''
  })
  Object.keys(errors).forEach(key => errors[key] = '')
  error.value = null
  success.value = false
}
</script>

<template>
  <BaseFormView
    title="Crear Nuevo Producto"
    subtitle="Agrega un nuevo producto al inventario"
    :loading="loading"
    :header-icon="basicInfoIcon"
    :success="success"
    :error="error"
    :formData="form" 
    success-message="¡Producto creado exitosamente!"
    loading-text="Creando..."
    submit-button-text="Crear Producto"
    @back="volverALista"
    @submit="crearProducto"
    @clear="limpiarFormulario"
  >
    
    <!-- Información Básica -->
    <FormSection title="Información Básica" :icon="basicInfoIcon" icon-color="blue">
      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <FormField
          id="nombre"
          label="Nombre del Producto"
          v-model="form.nombre"
          placeholder="Ingresa el nombre del producto"
          :required="true"
          :error="errors.nombre"
        />

        <FormField
          id="categoria"
          label="Categoría"
          type="select"
          v-model="form.categoria"
          :options="categorias"
          :required="true"
          :error="errors.categoria"
        />
      </div>

      <div class="mt-6">
        <FormField
          id="descripcion"
          label="Descripción"
          type="textarea"
          v-model="form.descripcion"
          placeholder="Describe el producto (opcional)"
          :rows="3"
        />
      </div>
    </FormSection>

    <!-- Inventario y Precios -->
    <FormSection title="Inventario y Precios" :icon="inventoryIcon" icon-color="green">
      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <FormField
          id="stock"
          label="Stock Inicial"
          type="number"
          v-model.number="form.stock"
          placeholder="0"
          :min="0"
          :required="true"
          :error="errors.stock"
        />

        <FormField
          id="precio"
          label="Precio"
          type="number"
          v-model.number="form.precio"
          placeholder="0.00"
          prefix="$"
          :min="0"
          :step="0.01"
          :required="true"
          :error="errors.precio"
        />
      </div>
    </FormSection>

  </BaseFormView>
</template>