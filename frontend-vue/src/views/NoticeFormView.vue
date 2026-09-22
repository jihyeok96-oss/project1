<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useNoticeStore } from '@/stores/notice'

// id가 없으면 등록 화면, 있으면 수정 화면으로 동작한다 (하나의 컴포넌트를 재사용).
const props = defineProps({
  id: { type: Number, default: null },
})

const store = useNoticeStore()
const router = useRouter()

const isEdit = computed(() => props.id !== null)

const form = reactive({
  title: '',
  content: '',
  author: '',
})

const errors = reactive({
  title: '',
  content: '',
  author: '',
})

const submitting = ref(false)
const loadError = ref('')

onMounted(async () => {
  if (!isEdit.value) return

  try {
    const notice = await store.fetchNotice(props.id)
    form.title = notice.title
    form.content = notice.content
    form.author = notice.author
  } catch (e) {
    loadError.value = store.error
  }
})

// 클라이언트 입력 검증 (FR-08)
function validate() {
  errors.title = ''
  errors.content = ''
  errors.author = ''

  const title = form.title.trim()
  const content = form.content.trim()
  const author = form.author.trim()

  if (!title) errors.title = '제목을 입력하세요.'
  else if (title.length > 200) errors.title = '제목은 200자 이내로 입력하세요.'

  if (!content) errors.content = '내용을 입력하세요.'

  if (!author) errors.author = '작성자를 입력하세요.'
  else if (author.length > 50) errors.author = '작성자 이름은 50자 이내로 입력하세요.'

  return !errors.title && !errors.content && !errors.author
}

async function handleSubmit() {
  if (!validate()) return

  submitting.value = true
  const payload = {
    title: form.title.trim(),
    content: form.content.trim(),
    author: form.author.trim(),
  }

  try {
    if (isEdit.value) {
      await store.updateNotice(props.id, payload)
      router.push(`/notices/${props.id}`)
    } else {
      const created = await store.createNotice(payload)
      router.push(`/notices/${created.id}`)
    }
  } catch (e) {
    // 서버 오류 메시지는 store.error에 저장되어 화면에 표시된다.
  } finally {
    submitting.value = false
  }
}

function handleCancel() {
  if (isEdit.value) {
    router.push(`/notices/${props.id}`)
  } else {
    router.push('/notices')
  }
}
</script>

<template>
  <section class="mx-auto max-w-2xl space-y-6">
    <h1 class="text-2xl font-bold">{{ isEdit ? '공지사항 수정' : '공지사항 등록' }}</h1>

    <p v-if="loadError" class="rounded-lg bg-red-50 px-4 py-3 text-sm text-red-600">
      {{ loadError }}
    </p>

    <form v-else class="space-y-5" @submit.prevent="handleSubmit">
      <div>
        <label class="mb-1 block text-sm font-medium text-slate-700">제목</label>
        <input
          v-model="form.title"
          type="text"
          maxlength="200"
          class="w-full rounded-lg border border-slate-300 px-3 py-2 text-sm focus:border-indigo-500 focus:outline-none"
        />
        <p v-if="errors.title" class="mt-1 text-xs text-red-600">{{ errors.title }}</p>
      </div>

      <div>
        <label class="mb-1 block text-sm font-medium text-slate-700">내용</label>
        <textarea
          v-model="form.content"
          rows="10"
          class="w-full rounded-lg border border-slate-300 px-3 py-2 text-sm focus:border-indigo-500 focus:outline-none"
        ></textarea>
        <p v-if="errors.content" class="mt-1 text-xs text-red-600">{{ errors.content }}</p>
      </div>

      <div>
        <label class="mb-1 block text-sm font-medium text-slate-700">작성자</label>
        <input
          v-model="form.author"
          type="text"
          maxlength="50"
          class="w-full rounded-lg border border-slate-300 px-3 py-2 text-sm focus:border-indigo-500 focus:outline-none"
        />
        <p v-if="errors.author" class="mt-1 text-xs text-red-600">{{ errors.author }}</p>
      </div>

      <p v-if="store.error" class="rounded-lg bg-red-50 px-4 py-3 text-sm text-red-600">
        {{ store.error }}
      </p>

      <div class="flex justify-end gap-2 pt-2">
        <button
          type="button"
          class="rounded-lg border border-slate-300 px-4 py-2 text-sm font-medium text-slate-600 hover:bg-slate-50"
          @click="handleCancel"
        >
          취소
        </button>
        <button
          type="submit"
          :disabled="submitting"
          class="rounded-lg bg-indigo-600 px-4 py-2 text-sm font-medium text-white hover:bg-indigo-700 disabled:opacity-50"
        >
          {{ submitting ? '저장 중...' : '저장' }}
        </button>
      </div>
    </form>
  </section>
</template>
