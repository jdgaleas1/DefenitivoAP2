<template>
  <div>
    <!-- Label -->
    <label :for="id" class="block text-sm font-medium text-gray-700 mb-2">
      {{ label }}
      <span v-if="required" class="text-red-500">*</span>
    </label>

    <!-- Input Field -->
    <div v-if="type !== 'select' && type !== 'textarea'" class="relative">
      <!-- Prefix Icon/Text -->
      <div v-if="prefix" class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
        <span class="text-gray-500 sm:text-sm">{{ prefix }}</span>
      </div>
      
      <input :id="id"
             :type="type"
             :value="modelValue"
             @input="$emit('update:modelValue', $event.target.value)"
             :placeholder="placeholder"
             :min="min"
             :max="max"
             :step="step"
             :class="inputClasses"
             class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-4 focus:ring-blue-100 focus:border-blue-500 transition-all duration-200">
      
      <!-- Suffix Icon -->
      <div v-if="suffix" class="absolute inset-y-0 right-0 pr-3 flex items-center pointer-events-none">
        <span class="text-gray-500 sm:text-sm">{{ suffix }}</span>
      </div>
    </div>

    <!-- Select Field -->
    <select v-else-if="type === 'select'"
            :id="id"
            :value="modelValue"
            @change="$emit('update:modelValue', $event.target.value)"
            :class="inputClasses"
            class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-4 focus:ring-blue-100 focus:border-blue-500 transition-all duration-200">
      <option value="">{{ placeholder || `Selecciona ${label.toLowerCase()}` }}</option>
      <option v-for="option in options" :key="option.value || option" :value="option.value || option">
        {{ option.label || option }}
      </option>
    </select>

    <!-- Textarea Field -->
    <textarea v-else-if="type === 'textarea'"
              :id="id"
              :value="modelValue"
              @input="$emit('update:modelValue', $event.target.value)"
              :placeholder="placeholder"
              :rows="rows"
              :class="inputClasses"
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-4 focus:ring-blue-100 focus:border-blue-500 transition-all duration-200">
    </textarea>

    <!-- Error Message -->
    <p v-if="error" class="text-red-600 text-sm mt-1">{{ error }}</p>
    
    <!-- Help Text -->
    <p v-else-if="help" class="text-gray-500 text-sm mt-1">{{ help }}</p>
  </div>
</template>

<script setup>
import { computed } from 'vue'

// Props
const props = defineProps({
  id: {
    type: String,
    required: true
  },
  label: {
    type: String,
    required: true
  },
  type: {
    type: String,
    default: 'text'
  },
  modelValue: {
    type: [String, Number],
    default: ''
  },
  placeholder: {
    type: String,
    default: ''
  },
  required: {
    type: Boolean,
    default: false
  },
  error: {
    type: String,
    default: ''
  },
  help: {
    type: String,
    default: ''
  },
  prefix: {
    type: String,
    default: ''
  },
  suffix: {
    type: String,
    default: ''
  },
  min: {
    type: [String, Number],
    default: undefined
  },
  max: {
    type: [String, Number],
    default: undefined
  },
  step: {
    type: [String, Number],
    default: undefined
  },
  rows: {
    type: Number,
    default: 3
  },
  options: {
    type: Array,
    default: () => []
  }
})

// Emits
defineEmits(['update:modelValue'])

// Computed
const inputClasses = computed(() => {
  const baseClasses = []
  
  if (props.error) {
    baseClasses.push('border-red-500 bg-red-50')
  }
  
  if (props.prefix) {
    baseClasses.push('pl-8')
  }
  
  if (props.suffix) {
    baseClasses.push('pr-8')
  }
  
  return baseClasses.join(' ')
})
</script>