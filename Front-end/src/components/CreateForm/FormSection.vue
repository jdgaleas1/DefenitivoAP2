<template>
  <div :class="{ 'border-b border-gray-200 pb-6': showBorder }">
    <h3 class="text-lg font-semibold text-gray-900 mb-4 flex items-center">
      <div class="w-8 h-8 rounded-lg flex items-center justify-center mr-3" :class="iconBgClass">
        <component :is="icon" class="w-4 h-4 text-white" />
      </div>
      {{ title }}
    </h3>
    
    <slot></slot>
  </div>
</template>

<script setup>
import { computed } from 'vue'

// Props
const props = defineProps({
  title: {
    type: String,
    required: true
  },
  icon: {
    type: [String, Object],
    default: () => ({
      template: `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24">
                   <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
                 </svg>`
    })
  },
  iconColor: {
    type: String,
    default: 'blue',
    validator: (value) => ['blue', 'green', 'purple', 'red', 'yellow'].includes(value)
  },
  showBorder: {
    type: Boolean,
    default: true
  }
})

// Computed
const iconBgClass = computed(() => {
  const colors = {
    blue: 'bg-gradient-to-r from-blue-500 to-indigo-600',
    green: 'bg-gradient-to-r from-emerald-500 to-green-600',
    purple: 'bg-gradient-to-r from-purple-500 to-pink-600',
    red: 'bg-gradient-to-r from-red-500 to-rose-600',
    yellow: 'bg-gradient-to-r from-yellow-500 to-orange-600'
  }
  return colors[props.iconColor] || colors.blue
})
</script>