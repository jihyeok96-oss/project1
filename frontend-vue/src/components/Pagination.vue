<script setup>
import { computed } from 'vue'

const props = defineProps({
  currentPage: { type: Number, required: true },
  totalPages: { type: Number, required: true },
})

const emit = defineEmits(['change'])

const pages = computed(() => Array.from({ length: props.totalPages }, (_, i) => i + 1))

function go(page) {
  if (page < 1 || page > props.totalPages || page === props.currentPage) return
  emit('change', page)
}
</script>

<template>
  <nav class="flex flex-wrap items-center justify-center gap-1 pt-2">
    <button
      type="button"
      class="rounded-md border border-slate-300 px-3 py-1.5 text-sm text-slate-600 disabled:cursor-not-allowed disabled:opacity-40"
      :disabled="currentPage === 1"
      @click="go(currentPage - 1)"
    >
      이전
    </button>

    <button
      v-for="page in pages"
      :key="page"
      type="button"
      class="min-w-[2.25rem] rounded-md px-3 py-1.5 text-sm"
      :class="
        page === currentPage
          ? 'bg-indigo-600 text-white'
          : 'text-slate-600 hover:bg-slate-100'
      "
      @click="go(page)"
    >
      {{ page }}
    </button>

    <button
      type="button"
      class="rounded-md border border-slate-300 px-3 py-1.5 text-sm text-slate-600 disabled:cursor-not-allowed disabled:opacity-40"
      :disabled="currentPage === totalPages"
      @click="go(currentPage + 1)"
    >
      다음
    </button>
  </nav>
</template>
