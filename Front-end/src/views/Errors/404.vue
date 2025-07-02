<script setup>

</script>

<template>
    <!-- 403 Start -->
    <section class="bg-gray-50 h-screen flex flex-col items-center justify-center text-center">
        <img src="@/assets/img/errors/404.png" alt="404 Image" class="w-92 mx-auto mb-6">
        <h1 class="text-4xl font-bold text-gray-800 mb-4">404 Not Found</h1>
        <p class="text-lg text-gray-600 mb-6">Oops! The page you are looking for doesn't exist.</p>
        <router-link :to="{ name: 'dashboard' }" class="block py-2 px-4 md:px-6 bg-mainColor text-gray-100 font-outfit font-semibold text-base rounded-lg shadow-lg transition ease-in duration-300 hover:opacity-70 cursor-pointe">Back to Home</router-link>
    </section>
    <!-- 403 End -->
</template>