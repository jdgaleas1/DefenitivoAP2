// vite.config.js
import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'
import tailwindcss from '@tailwindcss/vite'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
    tailwindcss(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  server: {
    port: 5173, // tu dev server sigue en 5173
    proxy: {
      '/productos': {
        target: 'http://localhost:8082',
        changeOrigin: true,
        secure: false,
      },
      '/ordenes': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        secure: false,
      },
      '/cobros': {
        target: 'http://localhost:8083',
        changeOrigin: true,
        secure: false,
      }
      // añade otros endpoints si los necesitas…
    }
  }
})
