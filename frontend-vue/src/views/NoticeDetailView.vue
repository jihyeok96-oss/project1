<script setup>
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useNoticeStore } from '@/stores/notice'

const props = defineProps({
  id: { type: Number, required: true },
})

const store = useNoticeStore()
const router = useRouter()

onMounted(() => {
  // 상세 조회 시 백엔드가 hits를 1 증가시킨다 (FR-02).
  store.fetchNotice(props.id).catch(() => {})
})

function formatDate(value) {
  if (!value) return ''
  return value.replace('T', ' ').slice(0, 16)
}

async function handleDelete() {
  if (!window.confirm('정말 삭제하시겠습니까?')) return
  try {
    await store.deleteNotice(props.id)
    router.push('/notices')
  } catch (e) {
    // 오류 메시지는 store.error에 저장되어 화면에 표시된다.
  }
}
</script>

<template>
  <section class="space-y-6">
    <p v-if="store.loading" class="py-10 text-center text-slate-500">불러오는 중입니다...</p>

    <p v-else-if="store.error" class="rounded-lg bg-red-50 px-4 py-3 text-sm text-red-600">
      {{ store.error }}
    </p>

    <article
      v-else-if="store.currentNotice"
      class="space-y-4 rounded-lg border border-slate-200 p-6"
    >
      <h1 class="text-2xl font-bold">{{ store.currentNotice.title }}</h1>

      <div class="flex flex-wrap gap-4 border-b border-slate-100 pb-4 text-sm text-slate-500">
        <span>작성자: {{ store.currentNotice.author }}</span>
        <span>조회수: {{ store.currentNotice.hits }}</span>
        <span>등록일: {{ formatDate(store.currentNotice.createdAt) }}</span>
      </div>

      <p class="whitespace-pre-line leading-relaxed text-slate-800">
        {{ store.currentNotice.content }}
      </p>

      <div class="flex justify-end gap-2 pt-4">
        <RouterLink
          to="/notices"
          class="rounded-lg border border-slate-300 px-4 py-2 text-sm font-medium text-slate-600 hover:bg-slate-50"
        >
          목록
        </RouterLink>
        <RouterLink
          :to="`/notices/${props.id}/edit`"
          class="rounded-lg bg-slate-800 px-4 py-2 text-sm font-medium text-white hover:bg-slate-900"
        >
          수정
        </RouterLink>
        <button
          type="button"
          class="rounded-lg bg-red-600 px-4 py-2 text-sm font-medium text-white hover:bg-red-700"
          @click="handleDelete"
        >
          삭제
        </button>
      </div>
    </article>
  </section>
</template>
