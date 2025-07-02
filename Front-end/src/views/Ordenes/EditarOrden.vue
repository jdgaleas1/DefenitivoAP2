<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { PageName } from '@/components/PageName'
import BaseFormView from '@/components/CreateForm/BaseFormView.vue'
import FormSection from '@/components/CreateForm/FormSection.vue'
import FormField from '@/components/CreateForm/FormField.vue'
import apiService from '@/services/api'

// Título de la página
PageName.value = 'Editar Orden'

// Router para navegación
const router = useRouter()
const route = useRoute()

// ID de la orden a editar
const ordenId = route.params.id

// Estado reactivo
const loading = ref(false)
const loadingOrden = ref(false)
const error = ref(null)
const success = ref(false)
const loadingProductos = ref(false)
const ordenEncontrada = ref(false)

// Lista de productos disponibles
const productos = ref([])

// Formulario reactivo para orden
const form = reactive({
  cliente: '',
  estado: 'CREADA',
  productos: [
    {
      sku: '',
      cantidad: 1
    }
  ]
})

// Datos originales de la orden (para comparar cambios)
const ordenOriginal = ref({})

// Validaciones
const errors = reactive({
  cliente: '',
  estado: '',
  productos: []
})

// Estados predefinidos para órdenes
const estados = [
  { value: 'CREADA', label: 'Creada' },
  { value: 'PROCESANDO', label: 'Procesando' },
  { value: 'COMPLETADA', label: 'Completada' },
  { value: 'CANCELADA', label: 'Cancelada' }
]

// Total calculado automáticamente
const totalCalculado = computed(() => {
  return form.productos.reduce((total, producto) => {
    const productoInfo = productos.value.find(p => p.sku === producto.sku)
    if (productoInfo && producto.cantidad > 0) {
      return total + (productoInfo.precio * producto.cantidad)
    }
    return total
  }, 0)
})

// Verificar si hay cambios en el formulario
const hayCancanios = computed(() => {
  if (!ordenOriginal.value.cliente) return false
  
  return (
    form.cliente !== ordenOriginal.value.cliente ||
    form.estado !== ordenOriginal.value.estado ||
    JSON.stringify(form.productos) !== JSON.stringify(ordenOriginal.value.productos) ||
    Math.abs(totalCalculado.value - (ordenOriginal.value.total || 0)) > 0.01
  )
})

// Íconos para las secciones
const orderIcon = {
  template: `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24">
               <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"/>
             </svg>`
}

const clientIcon = {
  template: `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24">
               <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
             </svg>`
}

const statusIcon = {
  template: `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24">
               <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
             </svg>`
}

const productIcon = {
  template: `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24">
               <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4"/>
             </svg>`
}

// Cargar datos al montar el componente
onMounted(async () => {
  await Promise.all([
    cargarOrden(),
    cargarProductos()
  ])
})

// Función para cargar la orden a editar
const cargarOrden = async () => {
  if (!ordenId) {
    error.value = 'ID de orden no proporcionado'
    return
  }

  loadingOrden.value = true
  try {
    const response = await apiService.obtenerOrden(ordenId)
    const orden = response.data || response
    
    if (orden) {
      // Llenar el formulario con los datos de la orden
      form.cliente = orden.cliente || ''
      form.estado = orden.estado || 'CREADA'
      form.productos = orden.productos?.length ? 
        orden.productos.map(p => ({
          sku: p.sku || '',
          cantidad: p.cantidad || 1
        })) : 
        [{ sku: '', cantidad: 1 }]
      
      // Guardar datos originales para comparación
      ordenOriginal.value = {
        cliente: orden.cliente || '',
        estado: orden.estado || 'CREADA',
        productos: JSON.parse(JSON.stringify(form.productos)),
        total: orden.total || 0
      }
      
      ordenEncontrada.value = true
    } else {
      error.value = 'Orden no encontrada'
    }
  } catch (err) {
    error.value = 'Error al cargar la orden. Verifica que el ID sea correcto.'
    console.error('Error al cargar orden:', err)
  } finally {
    loadingOrden.value = false
  }
}

// Función para cargar productos desde el API
const cargarProductos = async () => {
  loadingProductos.value = true
  try {
    const response = await apiService.obtenerProductos()
    productos.value = response.data?.map(producto => ({
      sku: producto.sku,
      nombre: producto.nombre,
      precio: producto.precio || 0,
      stock: producto.stock || 0,
      value: producto.sku,
      label: `${producto.nombre} - $${producto.precio?.toFixed(2)} (Stock: ${producto.stock})`
    })) || []
  } catch (err) {
    console.error('Error al cargar productos:', err)
    // Productos de ejemplo si falla la carga
    productos.value = [
      { sku: 'LP001', nombre: 'Laptop HP', precio: 899.99, stock: 15, value: 'LP001', label: 'Laptop HP - $899.99 (Stock: 15)' },
      { sku: 'MS002', nombre: 'Mouse inalámbrico', precio: 29.99, stock: 25, value: 'MS002', label: 'Mouse inalámbrico - $29.99 (Stock: 25)' },
      { sku: 'KB003', nombre: 'Teclado mecánico', precio: 79.99, stock: 12, value: 'KB003', label: 'Teclado mecánico - $79.99 (Stock: 12)' }
    ]
  } finally {
    loadingProductos.value = false
  }
}

// Función para agregar producto
const agregarProducto = () => {
  form.productos.push({
    sku: '',
    cantidad: 1
  })
  // Agregar nuevo error para el producto
  errors.productos.push({ sku: '', cantidad: '' })
}

// Función para eliminar producto
const eliminarProducto = (index) => {
  if (form.productos.length > 1) {
    form.productos.splice(index, 1)
    errors.productos.splice(index, 1)
  }
}

// Función para obtener información del producto por SKU
const getProductoInfo = (sku) => {
  return productos.value.find(p => p.sku === sku)
}

// Función para validar stock disponible
const validarStock = (sku, cantidad) => {
  const producto = getProductoInfo(sku)
  return producto ? cantidad <= producto.stock : false
}

// Función para validar el formulario
const validateForm = () => {
  // Limpiar errores previos
  errors.cliente = ''
  errors.estado = ''
  errors.productos = []
  
  let isValid = true

  // Validar cliente
  if (!form.cliente.trim()) {
    errors.cliente = 'El nombre del cliente es requerido'
    isValid = false
  }

  // Validar estado
  if (!form.estado.trim()) {
    errors.estado = 'Debe seleccionar un estado'
    isValid = false
  }

  // Validar que haya al menos un producto
  if (form.productos.length === 0) {
    isValid = false
  }

  // Validar cada producto
  form.productos.forEach((producto, index) => {
    const errorProducto = { sku: '', cantidad: '' }
    
    if (!producto.sku.trim()) {
      errorProducto.sku = 'Debe seleccionar un producto'
      isValid = false
    }

    if (producto.cantidad <= 0) {
      errorProducto.cantidad = 'La cantidad debe ser mayor a 0'
      isValid = false
    }

    // Validar stock disponible
    if (producto.sku && producto.cantidad > 0) {
      if (!validarStock(producto.sku, producto.cantidad)) {
        const productoInfo = getProductoInfo(producto.sku)
        errorProducto.cantidad = `Stock insuficiente. Disponible: ${productoInfo?.stock || 0}`
        isValid = false
      }
    }

    errors.productos.push(errorProducto)
  })

  // Verificar SKUs duplicados
  const skusSeleccionados = form.productos.map(p => p.sku).filter(Boolean)
  const skusDuplicados = skusSeleccionados.filter((sku, index, arr) => 
    arr.indexOf(sku) !== index
  )
  
  if (skusDuplicados.length > 0) {
    form.productos.forEach((producto, index) => {
      if (skusDuplicados.includes(producto.sku)) {
        if (!errors.productos[index]) errors.productos[index] = { sku: '', cantidad: '' }
        errors.productos[index].sku = 'No se pueden repetir productos'
        isValid = false
      }
    })
  }

  return isValid
}

// Función para actualizar orden
const actualizarOrden = async () => {
  if (!validateForm()) return

  // Verificar si hay cambios
  if (!hayCancanios.value) {
    error.value = 'No se han detectado cambios en la orden'
    return
  }

  loading.value = true
  error.value = null
  success.value = false

  try {
    const ordenData = {
      cliente: form.cliente.trim(),
      total: totalCalculado.value,
      estado: form.estado,
      productos: form.productos
        .filter(p => p.sku.trim()) // Solo productos válidos
        .map(p => ({
          sku: p.sku,
          cantidad: p.cantidad
        }))
    }

    await apiService.actualizarOrden(ordenId, ordenData)
    success.value = true
    
    // Actualizar datos originales después del éxito
    ordenOriginal.value = {
      cliente: ordenData.cliente,
      estado: ordenData.estado,
      productos: JSON.parse(JSON.stringify(ordenData.productos)),
      total: ordenData.total
    }
    
    // Redirigir a la lista después del éxito
    setTimeout(() => {
      router.push({ name: 'ordenes' })
    }, 2000)

  } catch (err) {
    error.value = 'Error al actualizar la orden. Por favor, intenta nuevamente.'
    console.error(err)
  } finally {
    loading.value = false
  }
}

// Función para volver a la lista
const volverALista = () => {
  if (hayCancanios.value) {
    const confirmar = confirm('Tienes cambios sin guardar. ¿Estás seguro de que quieres salir?')
    if (!confirmar) return
  }
  router.push({ name: 'ordenes' })
}

// Función para restaurar formulario a valores originales
const restaurarFormulario = () => {
  if (!ordenOriginal.value.cliente) return
  
  form.cliente = ordenOriginal.value.cliente
  form.estado = ordenOriginal.value.estado
  form.productos = JSON.parse(JSON.stringify(ordenOriginal.value.productos))
  
  // Limpiar errores
  errors.cliente = ''
  errors.estado = ''
  errors.productos = []
  error.value = null
  success.value = false
}
</script>

<template>
  <!-- Mostrar loading mientras se carga la orden -->
  <div v-if="loadingOrden" class="flex justify-center items-center min-h-64">
    <div class="text-center">
      <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-indigo-600 mx-auto"></div>
      <p class="mt-4 text-gray-600">Cargando orden...</p>
    </div>
  </div>

  <!-- Mostrar error si no se encuentra la orden -->
  <div v-else-if="error && !ordenEncontrada" class="max-w-md mx-auto mt-8">
    <div class="bg-red-50 border border-red-200 rounded-lg p-6 text-center">
      <div class="flex justify-center mb-4">
        <svg class="w-12 h-12 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-2.5l-6.928-12c-.77-.833-2.694-.833-3.464 0L.938 16.5C.168 17.333 1.13 19 2.67 19z"/>
        </svg>
      </div>
      <h3 class="text-lg font-medium text-red-800 mb-2">Error al cargar la orden</h3>
      <p class="text-red-600 mb-4">{{ error }}</p>
      <button @click="volverALista" 
              class="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md text-white bg-red-600 hover:bg-red-700 transition-colors">
        Volver a la lista
      </button>
    </div>
  </div>

  <!-- Formulario de edición -->
  <BaseFormView
    v-else-if="ordenEncontrada"
    title="Editar Orden"
    :subtitle="`Modificar orden #${ordenId.toString().slice(-8)}`"
    :loading="loading"
    :header-icon="orderIcon"
    :success="success"
    :error="error"
    :formData="form" 
    success-message="¡Orden actualizada exitosamente!"
    loading-text="Actualizando orden..."
    submit-button-text="Actualizar Orden"
    clear-button-text="Restaurar"
    :show-clear-button="hayCancanios"
    @back="volverALista"
    @submit="actualizarOrden"
    @clear="restaurarFormulario"
  >
    
    <!-- Indicador de cambios -->
    <div v-if="hayCancanios" class="mb-6 p-4 bg-yellow-50 border border-yellow-200 rounded-lg">
      <div class="flex items-center">
        <svg class="w-5 h-5 text-yellow-600 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-2.5l-6.928-12c-.77-.833-2.694-.833-3.464 0L.938 16.5C.168 17.333 1.13 19 2.67 19z"/>
        </svg>
        <span class="text-yellow-800 font-medium">Tienes cambios sin guardar</span>
      </div>
      <p class="text-yellow-700 text-sm mt-1">Asegúrate de guardar los cambios antes de salir</p>
    </div>

    <!-- Información del Cliente -->
    <FormSection title="Información del Cliente" :icon="clientIcon" icon-color="blue">
      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <FormField
          id="cliente"
          label="Nombre del Cliente"
          type="text"
          v-model="form.cliente"
          placeholder="Ingresa el nombre del cliente"
          :required="true"
          :error="errors.cliente"
        />

        <!-- Campo informativo del total -->
        <div class="flex items-center p-4 bg-green-50 border border-green-200 rounded-lg">
          <div class="flex-shrink-0">
            <svg class="w-5 h-5 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1"/>
            </svg>
          </div>
          <div class="ml-3">
            <p class="text-sm font-medium text-green-800">Total Actualizado</p>
            <p class="text-lg font-bold text-green-900">
              ${{ totalCalculado.toLocaleString('es-ES', { minimumFractionDigits: 2 }) }}
            </p>
            <p v-if="Math.abs(totalCalculado - (ordenOriginal.total || 0)) > 0.01" 
               class="text-xs text-green-700">
              Original: ${{ (ordenOriginal.total || 0).toLocaleString('es-ES', { minimumFractionDigits: 2 }) }}
            </p>
          </div>
        </div>
      </div>
    </FormSection>

    <!-- Estado de la Orden -->
    <FormSection title="Estado de la Orden" :icon="statusIcon" icon-color="green">
      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <FormField
          id="estado"
          label="Estado de la Orden"
          type="select"
          v-model="form.estado"
          :options="estados"
          :required="true"
          :error="errors.estado"
        />

        <!-- Campo informativo -->
        <div class="flex items-center p-4 bg-blue-50 border border-blue-200 rounded-lg">
          <div class="flex-shrink-0">
            <svg class="w-5 h-5 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
            </svg>
          </div>
          <div class="ml-3">
            <p class="text-sm text-blue-700">
              Puedes cambiar el estado de la orden. Los cambios se aplicarán inmediatamente al guardar.
            </p>
          </div>
        </div>
      </div>
    </FormSection>

    <!-- Productos de la Orden -->
    <FormSection title="Productos de la Orden" :icon="productIcon" icon-color="purple" :show-border="false">
      <div class="space-y-4">
        <div v-for="(producto, index) in form.productos" :key="index" 
             class="p-4 border border-gray-200 rounded-lg bg-gray-50 hover:bg-gray-100 transition-colors">
          
          <!-- Header del producto -->
          <div class="flex justify-between items-center mb-4">
            <h4 class="text-sm font-medium text-gray-700">
              Producto {{ index + 1 }}
              <span v-if="producto.sku" class="ml-2 text-xs text-gray-500">
                ({{ producto.sku }})
              </span>
            </h4>
            <button
              v-if="form.productos.length > 1"
              @click="eliminarProducto(index)"
              type="button"
              class="text-red-600 hover:text-red-800 p-1 rounded-full hover:bg-red-100 transition-colors"
            >
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
              </svg>
            </button>
          </div>

          <!-- Campos del producto -->
          <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
            <FormField
              :id="`sku_${index}`"
              label="Producto (SKU)"
              type="select"
              v-model="producto.sku"
              :options="productos"
              :loading="loadingProductos"
              placeholder="Selecciona un producto"
              :required="true"
              :error="errors.productos[index]?.sku"
            />

            <FormField
              :id="`cantidad_${index}`"
              label="Cantidad"
              type="number"
              v-model.number="producto.cantidad"
              placeholder="1"
              :min="1"
              :max="getProductoInfo(producto.sku)?.stock || 999"
              :required="true"
              :error="errors.productos[index]?.cantidad"
            />

            <!-- Información del producto seleccionado -->
            <div v-if="producto.sku && getProductoInfo(producto.sku)" 
                 class="flex items-center p-3 bg-white border border-gray-200 rounded-lg">
              <div class="flex-shrink-0">
                <svg class="w-5 h-5 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 7h.01M7 3h5c.512 0 1.024.195 1.414.586l7 7a2 2 0 010 2.828l-7 7a2 2 0 01-2.828 0l-7-7A1.994 1.994 0 013 12V7a4 4 0 014-4z"/>
                </svg>
              </div>
              <div class="ml-3">
                <p class="text-sm font-medium text-gray-900">
                  ${{ getProductoInfo(producto.sku).precio?.toFixed(2) }}
                </p>
                <p class="text-xs text-gray-500">
                  Stock: {{ getProductoInfo(producto.sku).stock }}
                </p>
              </div>
            </div>
          </div>

          <!-- Subtotal del producto -->
          <div v-if="producto.sku && producto.cantidad > 0 && getProductoInfo(producto.sku)" 
               class="mt-3 p-2 bg-green-50 border border-green-200 rounded">
            <p class="text-sm text-green-800">
              <span class="font-medium">Subtotal:</span>
              ${{ (getProductoInfo(producto.sku).precio * producto.cantidad).toFixed(2) }}
            </p>
          </div>
        </div>

        <!-- Botón para agregar producto -->
        <div class="flex justify-center">
          <button
            @click="agregarProducto"
            type="button"
            class="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 transition-colors"
          >
            <svg class="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6v6m0 0v6m0-6h6m-6 0H6"/>
            </svg>
            Agregar Producto
          </button>
        </div>

        <!-- Resumen del total -->
        <div v-if="form.productos.some(p => p.sku && p.cantidad > 0)" 
             class="mt-6 p-4 bg-gradient-to-r from-blue-50 to-indigo-50 border border-blue-200 rounded-lg">
          <div class="flex justify-between items-center">
            <div>
              <h3 class="text-lg font-medium text-gray-900">Total Actualizado</h3>
              <p class="text-sm text-gray-600">
                {{ form.productos.filter(p => p.sku && p.cantidad > 0).length }} productos
              </p>
            </div>
            <div class="text-right">
              <p class="text-2xl font-bold text-indigo-600">
                ${{ totalCalculado.toLocaleString('es-ES', { minimumFractionDigits: 2 }) }}
              </p>
              <p v-if="Math.abs(totalCalculado - (ordenOriginal.total || 0)) > 0.01" 
                 class="text-sm text-gray-500">
                Antes: ${{ (ordenOriginal.total || 0).toLocaleString('es-ES', { minimumFractionDigits: 2 }) }}
              </p>
            </div>
          </div>
        </div>
      </div>
    </FormSection>

  </BaseFormView>
</template>

<style scoped>
.loading-select {
  opacity: 0.6;
  pointer-events: none;
}

/* Animaciones suaves para agregar/quitar productos */
.v-enter-active, .v-leave-active {
  transition: all 0.3s ease;
}

.v-enter-from, .v-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}
</style>