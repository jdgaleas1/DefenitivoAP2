<script setup>
/** All Library Import */
import { ref, inject } from 'vue';

/** All Variables */
const activeMenu = ref(null)
const navValue = inject('navbar')

/** All Methods */
const toggleMenu = (menuName) => {
  activeMenu.value = activeMenu.value === menuName ? null : menuName
}
</script>

<template>
    <!-- Navbar Start -->
    <div class="min-w-72 min-h-screen h-full origin-left fixed top-0 lg:translate-x-0 left-0 z-10 bg-gray-800 text-gray-50 py-7 px-9 transition-all ease-in-out duration-500" :class="!navValue ? '-translate-x-[150%]' : ''">
        <span class="block lg:hidden absolute top-5 right-5 cursor-pointer" @click="navValue = false"><font-awesome-icon :icon="['fas', 'xmark']" class="text-lg" /></span>
        <h2 class="text-center text-lightColor text-2xl font-bold">MHN <font-awesome-icon :icon="['fas', 'bolt']" class="text-mainColor text-3xl" /> Panel</h2>
        <router-link :to="{name: 'dashboard'}" class="py-2.5 px-4 rounded-lg bg-gray-700 mt-12 text-sm font-medium text-gray-300 flex items-center gap-3"><font-awesome-icon :icon="['fas', 'house']" /> Dashboard</router-link>
        <h4 class="text-sm font-semibold font-outfit mt-8 mb-6 text-gray-200">System Setting</h4>

        <ul class="space-y-6" id="mhnNav">

            <!-- Menu Ordenes -->
            <li><router-link :to="{ name: 'ordenes' }"  class="text-sm font-medium text-gray-300 hover:text-lightColor flex items-center gap-3"><font-awesome-icon :icon="['fab', 'wpforms']" />Ordenes</router-link></li>

            <!-- Menu Productos -->
            <li><router-link :to="{ name: 'productos' }" class="text-sm font-medium text-gray-300 hover:text-lightColor flex items-center gap-3"><font-awesome-icon :icon="['fas', 'cart-shopping']" />Productos</router-link></li>

            <!-- Menu Cobros -->
            <li><router-link :to="{ name: 'cobros' }" class="text-sm font-medium text-gray-300 hover:text-lightColor flex items-center gap-3"><font-awesome-icon :icon="['fas', 'money-bill']" />obros</router-link></li>

             <!-- Menu Despacho -->
            <li><router-link :to="{ name: 'despacho' }" class="text-sm font-medium text-gray-300 hover:text-lightColor flex items-center gap-3"><font-awesome-icon :icon="['fas', 'briefcase']" />Despacho</router-link></li>

             <!-- Menu Envios -->
            <li><router-link :to="{ name: 'envios' }" class="text-sm font-medium text-gray-300 hover:text-lightColor flex items-center gap-3"><font-awesome-icon :icon="['fas', 'paper-plane']" />Envios</router-link></li>

            <!--
            
            <li @click="toggleMenu('menu3')">
                <a href="javascript:void(0)" class="text-sm font-medium text-gray-300 flex items-center justify-between gap-3 hover:text-lightColor">
                    <span class="flex items-center gap-3"><font-awesome-icon :icon="['fas', 'lock']" /> Authentication</span>
                    <font-awesome-icon :icon="['fas', 'chevron-right']" class="transition duration-300 text-xs" />
                </a>
                <transition name="slide">
                    <ul v-show="activeMenu === 'menu3'" class="overflow-hidden bg-gray-800 py-1.5 my-3 space-y-4 list-disc ps-8 transition-all ease-in-out duration-500 marker:text-lightColor">
                        <li><router-link :to="{ name: 'register' }" class="text-sm font-medium text-gray-300 hover:text-lightColor">Register</router-link></li>
                        <li><router-link :to="{ name: 'login' }" class="text-sm font-medium text-gray-300 hover:text-lightColor">Login</router-link></li>
                    </ul>   
                </transition>
            </li>
            <li @click="toggleMenu('menu5')">
                <a href="javascript:void(0)" class="text-sm font-medium text-gray-300 flex items-center justify-between gap-3 hover:text-lightColor">
                    <span class="flex items-center gap-3"><font-awesome-icon :icon="['fas', 'bug']" /> Error Pages</span>
                    <font-awesome-icon :icon="['fas', 'chevron-right']" class="transition duration-300 text-xs" />
                </a>
                <transition name="slide">
                    <ul v-show="activeMenu === 'menu5'" class="overflow-hidden bg-gray-800 py-1.5 my-3 space-y-4 list-disc ps-8 transition-all ease-in-out duration-500 marker:text-lightColor">
                        <li><router-link :to="{ name: '403Error' }" class="text-sm font-medium text-gray-300 hover:text-lightColor">403 Error</router-link></li>
                        <li><router-link :to="{ name: '404Error' }" class="text-sm font-medium text-gray-300 hover:text-lightColor">404 Error</router-link></li>
                    </ul>   
                </transition>
            </li>
            -->
        </ul>
    </div>
    <!-- Navbar End -->
</template>

<style scoped>
.slide-enter-active,
.slide-leave-active {
  transition: max-height 0.4s ease-in-out, opacity 0.4s ease-in-out;
}

.slide-enter-from,
.slide-leave-to {
  max-height: 0;
  opacity: 0;
}

.slide-enter-to,
.slide-leave-from {
  max-height: 1000px;
  opacity: 1;
}

</style>