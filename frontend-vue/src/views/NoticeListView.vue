<script setup>
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useNoticeStore } from '@/stores/notice'
import Pagination from '@/components/Pagination.vue'

const store = useNoticeStore()
const route = useRoute()
const router = useRouter()

const keywordInput = ref('')

onMounted(async () => {
  // URL query에서 검색어/페이지를 복원한다 (FR-07, AC-10)
  const initialKeyword = typeof route.query.keyword === 'string' ? route.query.keyword : ''
  const initialPage = Number(route.query.page) || 1

  keywordInput.value = initialKeyword
  store.keyword = initialKeyword
  store.page = initialPage

  await store.fetchNotices().catch(() => {})

  // 저장된 페이지가 실제 페이지 범위를 벗어나면 보정한다.
  if (store.page > store.totalPages) {
    store.setPage(store.totalPages)
    syncQuery()
  }
})

function syncQuery() {
  const query = {}
  if (store.keyword) query.keyword = store.keyword
  if (store.page > 1) query.page = String(store.page)
  router.replace({ query })
}

function handleSearch() {
  store.setKeyword(keywordInput.value)
  syncQuery()
}

function handlePageChange(page) {
  store.setPage(page)
  syncQuery()
}

function formatDate(value) {
  if (!value) return ''
  return value.replace('T', ' ').slice(0, 16)
}
</script>

<template>
  <section class="space-y-6">
    <div class="flex flex-wrap items-center justify-between gap-4">
      <h1 class="text-2xl font-bold">공지사항</h1>
      <RouterLink
        to="/notices/new"
        class="rounded-lg bg-indigo-600 px-4 py-2 text-sm font-medium text-white hover:bg-indigo-700"
      >
        등록
      </RouterLink>
    </div>

    <form class="flex gap-2" @submit.prevent="handleSearch">
      <input
        v-model="keywordInput"
        type="text"
        placeholder="제목, 내용, 작성자로 검색"
        class="w-full rounded-lg border border-slate-300 px-3 py-2 text-sm focus:border-indigo-500 focus:outline-none"
      />
      <button
        type="submit"
        class="whitespace-nowrap rounded-lg bg-slate-800 px-4 py-2 text-sm font-medium text-white hover:bg-slate-900"
      >
        검색
      </button>
    </form>

    <p v-if="store.error" class="rounded-lg bg-red-50 px-4 py-3 text-sm text-red-600">
      {{ store.error }}
    </p>

    <div v-if="store.loading" class="py-10 text-center text-slate-500">불러오는 중입니다...</div>

    <template v-else>
      <div class="overflow-x-auto rounded-lg border border-slate-200">
        <table class="w-full min-w-[600px] text-left text-sm">
          <thead class="bg-slate-100 text-slate-600">
            <tr>
              <th class="w-16 px-4 py-3">번호</th>
              <th class="px-4 py-3">제목</th>
              <th class="w-32 px-4 py-3">작성자</th>
              <th class="w-24 px-4 py-3">조회수</th>
              <th class="w-40 px-4 py-3">등록일</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="notice in store.paginatedNotices"
              :key="notice.id"
              class="border-t border-slate-100 hover:bg-slate-50"
            >
              <td class="px-4 py-3 text-slate-500">{{ notice.id }}</td>
              <td class="px-4 py-3">
                <RouterLink
                  :to="`/notices/${notice.id}`"
                  class="font-medium text-indigo-600 hover:underline"
                >
                  {{ notice.title }}
                </RouterLink>
              </td>
              <td class="px-4 py-3">{{ notice.author }}</td>
              <td class="px-4 py-3">{{ notice.hits }}</td>
              <td class="px-4 py-3 text-slate-500">{{ formatDate(notice.createdAt) }}</td>
            </tr>
            <tr v-if="store.paginatedNotices.length === 0">
              <td colspan="5" class="px-4 py-10 text-center text-slate-400">
                검색 결과가 없습니다.
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <Pagination
        v-if="store.totalPages > 1"
        :current-page="store.page"
        :total-pages="store.totalPages"
        @change="handlePageChange"
      />
    </template>
  </section>
</template>
