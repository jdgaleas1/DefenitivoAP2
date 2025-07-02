<template>
  <router-link 
    :to="{ name: route }" 
    class="bg-gradient-to-r from-blue-500 to-indigo-600 hover:from-blue-600 hover:to-indigo-700 text-white px-4 py-2 rounded-lg shadow transition-colors duration-200 flex items-center justify-center hover:shadow-lg">
    <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4"/>
    </svg>
    {{ text }}
  </router-link>
</template>

<script setup>
defineProps({
  route: {
    type: String,
    required: true
  },
  text: {
    type: String,
    default: 'Nuevo'
  }
})
</script>