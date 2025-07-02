<script setup>
/** All Library Import */
import { ref, reactive } from 'vue';

/** All Variables */
const schema = reactive({
  email: 'required|email',
  password: 'min:4|max:25',
})
const showPassword = ref(false)

/** All Method */
const togglePassword = () => {
  showPassword.value = !showPassword.value
}
</script>

<template>
    <section class="min-h-screen flex items-center justify-center bg-gradient-to-r from-teal-400 to-teal-700/60">
        <div class="max-w-lg w-full mx-auto px-2">
            <div class="bg-white p-5 md:p-8 rounded-lg shadow-md">
                <h2 class="text-center text-3xl font-bold text-mainColor text-shadow-2xs">Sign In</h2>
                <p class="text-sm text-gray-500 font-medium text-center mt-2">Welcome Back!</p>

                <!-- Social Login -->
                <div class="grid grid-cols-2 gap-1 md:gap-3 items-center mt-5">
                    <a href="" class="flex justify-center items-center gap-1 md:gap-2 border border-gray-300 rounded-md py-1.5 px-1.5 md:px-2">
                        <svg xmlns="http://www.w3.org/2000/svg" class="w-4 md:w-5 h-4 md:h-5" x="0px" y="0px" width="100" height="100" viewBox="0 0 48 48">
                        <path fill="#FFC107" d="M43.611,20.083H42V20H24v8h11.303c-1.649,4.657-6.08,8-11.303,8c-6.627,0-12-5.373-12-12c0-6.627,5.373-12,12-12c3.059,0,5.842,1.154,7.961,3.039l5.657-5.657C34.046,6.053,29.268,4,24,4C12.955,4,4,12.955,4,24c0,11.045,8.955,20,20,20c11.045,0,20-8.955,20-20C44,22.659,43.862,21.35,43.611,20.083z"></path><path fill="#FF3D00" d="M6.306,14.691l6.571,4.819C14.655,15.108,18.961,12,24,12c3.059,0,5.842,1.154,7.961,3.039l5.657-5.657C34.046,6.053,29.268,4,24,4C16.318,4,9.656,8.337,6.306,14.691z"></path><path fill="#4CAF50" d="M24,44c5.166,0,9.86-1.977,13.409-5.192l-6.19-5.238C29.211,35.091,26.715,36,24,36c-5.202,0-9.619-3.317-11.283-7.946l-6.522,5.025C9.505,39.556,16.227,44,24,44z"></path><path fill="#1976D2" d="M43.611,20.083H42V20H24v8h11.303c-0.792,2.237-2.231,4.166-4.087,5.571c0.001-0.001,0.002-0.001,0.003-0.002l6.19,5.238C36.971,39.205,44,34,44,24C44,22.659,43.862,21.35,43.611,20.083z"></path>
                        </svg>
                        <span class="text-xs md:text-base text-gray-700 font-semibold md:font-medium font-outfit">Signin with Google</span>
                    </a>
                    <a href="" class="flex justify-center items-center gap-1 md:gap-2 border border-gray-300 rounded-md py-1.5 px-1.5 md:px-2">
                        <svg xmlns="http://www.w3.org/2000/svg" class="w-4 md:w-5 h-4 md:h-5" x="0px" y="0px" width="100" height="100" viewBox="0 0 48 48">
                        <linearGradient id="Ld6sqrtcxMyckEl6xeDdMa_uLWV5A9vXIPu_gr1" x1="9.993" x2="40.615" y1="9.993" y2="40.615" gradientUnits="userSpaceOnUse"><stop offset="0" stop-color="#2aa4f4"></stop><stop offset="1" stop-color="#007ad9"></stop></linearGradient><path fill="url(#Ld6sqrtcxMyckEl6xeDdMa_uLWV5A9vXIPu_gr1)" d="M24,4C12.954,4,4,12.954,4,24s8.954,20,20,20s20-8.954,20-20S35.046,4,24,4z"></path><path fill="#fff" d="M26.707,29.301h5.176l0.813-5.258h-5.989v-2.874c0-2.184,0.714-4.121,2.757-4.121h3.283V12.46 c-0.577-0.078-1.797-0.248-4.102-0.248c-4.814,0-7.636,2.542-7.636,8.334v3.498H16.06v5.258h4.948v14.452 C21.988,43.9,22.981,44,24,44c0.921,0,1.82-0.084,2.707-0.204V29.301z"></path>
                        </svg>
                        <span class="text-xs md:text-base text-gray-700 font-semibold md:font-medium font-outfit">Signin with FaceBook</span>
                    </a>
                </div>

                <!-- Devider -->
                <div class="flex items-center justify-center my-4">
                    <div class="w-20 border-t border-gray-300"></div>
                    <span class="mx-4 text-gray-500 font-medium">or</span>
                    <div class="w-20 border-t border-gray-300"></div>
                </div>


                <!-- Login Form -->
                <vee-form :validation-schema="schema" validate-on-input>
                    <!-- Email -->
                    <div class="mb-4">
                        <label for="email" class="block text-sm font-bold text-gray-700 mb-1.5">Email <span class="text-rose-400">*</span></label>
                        <vee-field type="email" name="email" id="email" class="block w-full p-2.5 rounded-md border border-gray-200  focus:outline-1 focus:outline-mainColor placeholder:text-sm" placeholder="Email" required />
                        <ErrorMessage name="email" class="text-xs text-rose-500 font-semibold my-2" />
                    </div>

                    <!-- Password -->
                    <div class="mb-4">
                        <label for="password" class="block text-sm font-bold text-gray-700 mb-1.5">Password <span class="text-rose-400">*</span></label>
                        <div class="flex">
                            <vee-field :type="showPassword ? 'text' : 'password'" name="password" id="password" class="block w-full p-2.5 rounded-l-md border border-gray-200 focus:outline-1 focus:outline-mainColor placeholder:text-sm" placeholder="********" required />
                            <div class="py-2 px-3 rounded-r-md flex items-center justify-center border-t border-r border-b border-gray-200 cursor-pointer" @click="togglePassword">
                                <span class="text-gray-600 font-medium"><font-awesome-icon :icon="['fas', showPassword ? 'eye-slash' : 'eye']" /></span>
                            </div>
                        </div>
                        <ErrorMessage name="password" class="text-xs text-rose-500 font-semibold my-2" />
                    </div>

                    <!-- Login Option -->
                    <div class="flex items-center justify-between gap-4 mb-6">
                        <div class="flex items-center">
                            <input id="remember" type="checkbox" class="w-4 h-4 text-white bg-gray-100 accent-mainColor border-gray-300 rounded focus:outline-0 focus:ring-2 focus:ring-mainColor" />
                            <label for="remember" class="ml-2 text-sm text-gray-700 font-medium cursor-pointer">Remember Me?</label>
                        </div>

                        <a href="" class="inline text-sm text-mainColor font-medium hover:underline">Forget Password?</a>
                    </div>

                    <!-- Submit Button -->
                    <button type="submit" class="bg-mainColor text-white block w-full border-0 py-1 px-4 text-lg font-semibold font-outfit rounded-md">Login</button>
                </vee-form>

                <p class="text-sm text-gray-500 font-medium text-center mt-4">Dont have an account? <router-link :to="{ name: 'register' }" class="text-mainColor">Sign Up</router-link></p>
            </div>
        </div>
    </section>
</template>