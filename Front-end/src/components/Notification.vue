<script setup>
/** Library Import */
import { ref, inject } from 'vue';

/** Variables */
const notifications = inject('notificationview')
const profiles = inject('profileview')
const navValue = inject('navbar')

/** Methods */
const showNotification = () => {
    notifications.value = !notifications.value;
    profiles.value = false;
    notification.value = false;
}
</script>
<template>
    <!-- Notification -->
    <div class="relative">
        <a href="javascript:void(0)" @click.prevent="showNotification" class="text-rose-600">
            <font-awesome-icon :icon="['fas', 'bell']" class="cursor-pointer" />
        </a>

        <div class="absolute top-[110%] right-0 origin-top w-56 md:w-72 bg-white shadow rounded-md z-10 transition ease-in duration-300" :class="!notifications ? 'scale-0 opacity-0 invisible' : ''">
            <div class="flex items-center justify-between gap-3 py-2.5 px-4 border-b border-gray-200">
                <h4 class="text-base font-medium text-gray-800">Notifications</h4>
                <a href="" class="text-sm font-normal text-mainColor">Mark all as read</a>
            </div>
            
            <!-- No Notification -->
            <div class="h-52 text-center flex flex-col items-center justify-center gap-1.5">
                <i class="fa-solid fa-file-excel text-gray-500 text-5xl"></i>
                <p class="text-sm font-normal text-gray-600">No Unread Notifications Found!</p>
            </div>

            <!-- Notification List -->
            <div class="divide-y-2 divide-gray-200 h-52 overflow-y-auto">
                <div class="py-2.5 px-5">
                    <h4 class="text-sm font-medium text-gray-700">Fix YouTube Tools</h4>
                    <p class="text-xs font-normal text-gray-600 mt-1 mb-1.5">Lorem ipsum dolor sit amet consectetur adipisicing elit. Excepturi, fuga...</p>
                    <p class="text-xs font-medium"><font-awesome-icon :icon="['fas', 'clock']" class="text-mainColor" /> <span class="text-gray-600">1 hour ago</span></p>
                </div>
                <div class="py-2.5 px-5">
                    <h4 class="text-sm font-medium text-gray-700">Fix YouTube Tools</h4>
                    <p class="text-xs font-normal text-gray-600 mt-1 mb-1.5">Lorem ipsum dolor sit amet consectetur adipisicing elit. Excepturi, fuga...</p>
                    <p class="text-xs font-medium"><font-awesome-icon :icon="['fas', 'clock']" class="text-mainColor" /> <span class="text-gray-600">1 hour ago</span></p>
                </div>
                <div class="py-2.5 px-5">
                    <h4 class="text-sm font-medium text-gray-700">Fix YouTube Tools</h4>
                    <p class="text-xs font-normal text-gray-600 mt-1 mb-1.5">Lorem ipsum dolor sit amet consectetur adipisicing elit. Excepturi, fuga...</p>
                    <p class="text-xs font-medium"><font-awesome-icon :icon="['fas', 'clock']" class="text-mainColor" /> <span class="text-gray-600">1 hour ago</span></p>
                </div>
                <div class="py-2.5 px-5">
                    <h4 class="text-sm font-medium text-gray-700">Fix YouTube Tools</h4>
                    <p class="text-xs font-normal text-gray-600 mt-1 mb-1.5">Lorem ipsum dolor sit amet consectetur adipisicing elit. Excepturi, fuga...</p>
                    <p class="text-xs font-medium"><font-awesome-icon :icon="['fas', 'clock']" class="text-mainColor" /> <span class="text-gray-600">1 hour ago</span></p>
                </div>
            </div>
            <div class="py-2.5 px-4 border-t border-gray-200">
                <a href="" class="text-sm font-normal text-mainColor flex items-center justify-center gap-2"><font-awesome-icon :icon="['fas', 'circle-right']" /> All Notifications</a>
            </div>
        </div>
    </div>
    <!-- Notification -->
</template>