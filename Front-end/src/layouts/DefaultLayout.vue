<script setup>
/** All Library Import */
import { provide, ref } from 'vue';
import { RouterView } from 'vue-router';
import NavBar from '@/components/NavBar.vue';
import MenuBar from '@/components/MenuBar.vue';

/** All Variables */
const navbar = ref(false);
const profileview = ref(false);
const notificationview = ref(false);

/** Provider */
provide('navbar', navbar)
provide('profileview', profileview)
provide('notificationview', notificationview)
</script>

<template>
  <!-- Dashboard Start -->
  <div class="flex justify-between">
    <NavBar/>

    <div class="flex-1 ms-0 lg:ms-72 py-5 px-2 xl:px-5 xxl:px-10 bg-gray-50 min-h-screen max-sm:w-full">
      <MenuBar/>
      <section class="py-10">
        <router-view v-slot="{ Component, route }">
          <transition name="route" mode="out-in">
            <div :key="route.name">
              <component :is="Component" />
            </div>
          </transition>
        </router-view>
      </section>
    </div>
  </div>
  <!-- Dashboard End -->
</template>

<style scoped>
.route-enter-from {
  opacity: 0;
  transform: translateY(10px);
}
.route-enter-active, .route-leave-active {
  transition: all 0.2s ease-out;
}
.route-leave-to {
  opacity: 0;
  transform: translateY(10px);
}
</style>