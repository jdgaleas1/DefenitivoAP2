// vite.config.js
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 3000,
    proxy: {
      // cualquier petición a /inventario se enviará a http://localhost:8082
      '/inventario': {
        target: 'http://localhost:8082',
        changeOrigin: true,
        secure: false,
        // rewrite: (path) => path, // no hace falta si mantienen el mismo path
      },
      '/ordenes': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        secure: false,
      }
      // etc.
    }
  }
})
