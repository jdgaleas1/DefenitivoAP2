<script setup>
/** All Library Import  */
import { reactive, ref } from 'vue'
import { PageName } from '@/components/PageName'

/** All Instance */
PageName.value = 'Password Update'

/** All Variables */
const schema = reactive({
  old_password: 'required|min:4|max:25',
  new_password: 'required|min:4|max:25',
  confirm_password: 'required|passwords_mismatch:@new_password'
})
</script>

<template>
  <div class="bg-white rounded-lg p-5 shadow-md w-full mt-10">
    <vee-form :validation-schema="schema" validate-on-input>
      
      <!-- Old Password -->
      <div class="mb-6">
        <label for="old-password" class="block text-sm font-bold text-gray-700 mb-1.5">Old Password <span class="text-rose-400">*</span></label>
        <vee-field type="password" id="old-password" name="old_password" placeholder="Old password" class="block w-full rounded-md border border-gray-200 bg-gray-100 p-2.5 text-gray-800 placeholder:text-gray-400 focus:outline-1 focus:outline-mainColor"/>
        <ErrorMessage name="old_password" class="text-xs text-rose-500 font-semibold my-2"/>
      </div>

      <!-- New Password & Confirm Password -->
      <div class="grid grid-cols-1 md:grid-cols-2 gap-5 mb-6">
        <div>
          <label for="new-password" class="block text-sm font-bold text-gray-700 mb-1.5">New Password <span class="text-rose-400">*</span></label>
          <vee-field type="password" id="new-password" name="new_password" placeholder="New password" class="block w-full rounded-md border border-gray-200 bg-gray-100 p-2.5 text-gray-800 placeholder:text-gray-400 focus:outline-1 focus:outline-mainColor"/>
          <ErrorMessage name="new_password" class="text-xs text-rose-500 font-semibold my-2"/>
        </div>

        <div>
          <label for="confirm-password" class="block text-sm font-bold text-gray-700 mb-1.5">Confirm Password <span class="text-rose-400">*</span></label>
          <vee-field type="password" id="confirm-password" name="confirm_password" placeholder="Confirm password" class="block w-full rounded-md border border-gray-200 bg-gray-100 p-2.5 text-gray-800 placeholder:text-gray-400 focus:outline-1 focus:outline-mainColor"/>
          <ErrorMessage name="confirm_password" class="text-xs text-rose-500 font-semibold my-2"/>
        </div>
      </div>

      <!-- Submit -->
      <button type="submit" class="px-6 py-2 bg-mainColor hover:bg-mainColor-dark text-white font-semibold rounded-md transition-colors duration-300 cursor-pointer">
        Change Password
      </button>
    </vee-form>
  </div>
</template>
