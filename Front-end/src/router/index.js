import { createRouter, createWebHistory } from 'vue-router'
import Dashboard from '../views/Dashboard.vue'
import DefaultLayout from '@/layouts/DefaultLayout.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    // Default Routes
    {
      path: '/',
      component: DefaultLayout,
      children: [
        {
          path: '/',
          name: 'dashboard',
          component: Dashboard,
        },
        {
          path: '/update-profile',
          name: 'updateProfile',
          component: () => import('../views/Profile/UpdateProfile.vue'),
        },
        {
          path: '/update-password',
          name: 'updatePassword',
          component: () => import('../views/Profile/UpdatePassword.vue'),
        },

        // Ordenes
        {
          path: '/listar-ordenes',
          name: 'ordenes',
          component: () => import('../views/Ordenes/ListarOrdenes.vue'),
        },
        {
          path: '/ordenes/crear',
          name: 'crearOrden',
          component: () => import('../views/Ordenes/CrearOrden.vue'),
        },
        {
          path: '/ordenes/editar',
          name: 'editarOrden',
          component: () => import('../views/Ordenes/EditarOrden.vue'),
        },
        // Productos
        {
          path: '/lista-productos',
          name: 'productos',
          component: () => import('../views/Productos/ListarPruductos.vue'),
        },
        {
          path: '/productos/crear',
          name: 'crearProducto',
          component: () => import('../views/Productos/CrearProducto.vue'),
        },

        // Cobros
        {
          path: '/lista-cobros',
          name: 'cobros',
          component: () => import('../views/Cobros/ListaCobros.vue'),
        },
        
        // Despacho
        {
          path: '/lista-despacho',
          name: 'despacho',
          component: () => import('../views/Despacho/ListaDespacho.vue'),
        },

        // Envios
        {
          path: '/lista-envios',
          name: 'envios',
          component: () => import('../views/Envios/ListaEnvios.vue'),
        },        
      ]
    },

    // Other Routes
    {
      path: '/error/403',
      name: '403Error',
      component: () => import('../views/Errors/403.vue'),
    },
    {
      path: '/error/404',
      name: '404Error',
      component: () => import('../views/Errors/404.vue'),
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('../views/Auth/Registration.vue'),
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/Auth/Login.vue'),
    },
  ],
})

export default router
