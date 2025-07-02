<script setup>
/** All Library Import  */
import { reactive, ref } from 'vue'
import { PageName } from '@/components/PageName'

/** All Instance */
PageName.value = 'Profile Update'

/** All Variables */
const schema = reactive({
  full_name: 'required',
  email: 'required|email',
  address: 'required'
})

const files = ref([])
</script>

<template>
  <div class="bg-white rounded-lg p-5 shadow-md w-full">
    <vee-form :validation-schema="schema" validate-on-input>

      <!-- Image Upload -->
      <div class="mb-6">
        <label for="image-upload" class="block text-sm font-bold text-gray-700 mb-1.5">
          Profile Image <span class="text-rose-400">*</span>
        </label>
        <FilePond name="image_upload" id="image-upload" allow-multiple="false" accepted-file-types="image/png, image/jpeg" max-file-size="2MB" label-idle="Drop image or <span class='filepond--label-action'>Browse</span>" :files="files" @update:files="files = $event" />
        <ErrorMessage name="image_upload" class="text-xs text-rose-500 font-semibold my-2"/>
      </div>

      <!--Full Name | Email | Phone -->
      <div class="grid grid-cols-1 md:grid-cols-3 gap-5 mb-6">

        <div>
          <label for="full-name" class="block text-sm font-bold text-gray-700 mb-1.5">Full Name <span class="text-rose-400">*</span></label>
          <vee-field type="text" id="full-name" name="full_name" placeholder="Your full name" class="block w-full rounded-md border border-gray-200 bg-gray-100 p-2.5 text-gray-800 placeholder:text-gray-400 focus:outline-1 focus:outline-mainColor"/>
          <ErrorMessage name="full_name" class="text-xs text-rose-500 font-semibold my-2"/>
        </div>

        <div>
          <label for="email" class="block text-sm font-bold text-gray-700 mb-1.5">Email <span class="text-rose-400">*</span></label>
          <vee-field type="email" id="email" name="email" placeholder="example@mail.com" class="block w-full rounded-md border border-gray-200 bg-gray-100 p-2.5 text-gray-800 placeholder:text-gray-400 focus:outline-1 focus:outline-mainColor"/>
          <ErrorMessage name="email" class="text-xs text-rose-500 font-semibold my-2"/>
        </div>

        <div>
          <label for="phone" class="block text-sm font-bold text-gray-700 mb-1.5">Phone <span class="text-rose-400">*</span></label>
          <vue-tel-input name="phone" id="phone" mode="international" required class="block w-full p-1.5 rounded-md border !border-gray-200 bg-gray-100 text-gray-800 placeholder:text-gray-400 focus:outline-1 focus:outline-mainColor shadow-none placeholder:text-sm" />
          <ErrorMessage name="phone" class="text-xs text-rose-500 font-semibold my-2"/>
        </div>
      </div>

      <!-- Address (full width) -->
      <div class="mb-6">
        <label for="address" class="block text-sm font-bold text-gray-700 mb-1.5">Address <span class="text-rose-400">*</span></label>
        <vee-field as="textarea" id="address" name="address" placeholder="Your address" class="block w-full rounded-md border border-gray-200 bg-gray-100 p-2.5 text-gray-800 placeholder:text-gray-400 focus:outline-1 focus:outline-mainColor resize-none h-24"/>
        <ErrorMessage name="address" class="text-xs text-rose-500 font-semibold my-2"/>
      </div>

      <!-- Submit -->
      <button type="submit" class="w-full bg-mainColor hover:bg-mainColor-dark text-white font-semibold py-3 rounded-lg transition-colors duration-300 cursor-pointer">
        Update Profile
      </button>
    </vee-form>
  </div>
</template>
