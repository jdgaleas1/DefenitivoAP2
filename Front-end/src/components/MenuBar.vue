<script setup>
/** All Library Import */
import { inject, ref } from 'vue';
import Notification from './Notification.vue';
import Profile from './Profile.vue';
import { PageName } from './PageName';

/** All Variables */
const navValue = inject('navbar');
const profiles = inject('profileview');
const notification = inject('notificationview');

/** All Methods */
const showNavbar = () => {
    navValue.value = !navValue.value;
    profiles.value = false
    notification.value = false
}
</script>

<template>
    <!-- Top Menubar -->
    <div class="bg-white shadow-md py-4 px-5 rounded-lg flex items-center justify-between gap-5">
        <div class="block lg:hidden" @click="showNavbar">
            <font-awesome-icon :icon="['fas', 'bars']" class="text-xl md:text-2xl text-gray-700 cursor-pointer" />
        </div>

        <!-- Mobile -->
        <h2 class="hidden lg:block text-2xl font-bold text-mainColor">{{ PageName }}</h2>
        <h2 class="block lg:hidden text-center text-mainColor text-xl md:text-2xl font-bold">MHN <font-awesome-icon :icon="['fas', 'bolt']" class="text-lightColor text-2xl md:text-3xl" /> Panel</h2>
        <!-- Mobile -->

        <div class="flex items-center justify-end gap-5 md:gap-7">
            <div class="flex items-center gap-4 md:gap-6">
                <Notification/>

                <div class="">
                    <a href="" class="text-gray-700">
                        <font-awesome-icon :icon="['fas', 'globe']" />
                    </a>
                </div>
            </div>

            <Profile/>
        </div>
    </div>
    <!-- Top Menubar -->
</template>