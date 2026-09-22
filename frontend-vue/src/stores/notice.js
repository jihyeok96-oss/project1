import { defineStore } from 'pinia'
import { noticeApi } from '@/api/noticeApi'

function extractErrorMessage(err) {
  return err.response?.data?.message || err.message || '알 수 없는 오류가 발생했습니다.'
}

// 검색/페이징은 서버가 아닌 이 store에서 클라이언트 사이드로 처리한다 (PRD FR-06, FR-07).
//
//   notices (전체 목록)
//     └─ filteredNotices (검색어로 필터링)
//          └─ totalPages
//          └─ paginatedNotices (10건 단위로 slice)
export const useNoticeStore = defineStore('notice', {
  state: () => ({
    notices: [],
    currentNotice: null,
    loading: false,
    error: null,
    keyword: '',
    page: 1,
    pageSize: 10,
  }),

  getters: {
    filteredNotices(state) {
      const keyword = state.keyword.trim().toLowerCase()
      if (!keyword) return state.notices

      return state.notices.filter((notice) => {
        return (
          notice.title?.toLowerCase().includes(keyword) ||
          notice.content?.toLowerCase().includes(keyword) ||
          notice.author?.toLowerCase().includes(keyword)
        )
      })
    },

    totalPages() {
      return Math.max(1, Math.ceil(this.filteredNotices.length / this.pageSize))
    },

    paginatedNotices() {
      const start = (this.page - 1) * this.pageSize
      const end = start + this.pageSize
      return this.filteredNotices.slice(start, end)
    },
  },

  actions: {
    setKeyword(keyword) {
      this.keyword = keyword
      this.page = 1 // 검색어가 바뀌면 1페이지로 이동 (FR-06)
    },

    setPage(page) {
      this.page = page
    },

    async fetchNotices() {
      this.loading = true
      this.error = null
      try {
        const { data } = await noticeApi.getList()
        this.notices = data
      } catch (err) {
        this.error = extractErrorMessage(err)
        throw err
      } finally {
        this.loading = false
      }
    },

    async fetchNotice(id) {
      this.loading = true
      this.error = null
      this.currentNotice = null
      try {
        const { data } = await noticeApi.getDetail(id)
        this.currentNotice = data
        return data
      } catch (err) {
        this.error = extractErrorMessage(err)
        throw err
      } finally {
        this.loading = false
      }
    },

    async createNotice(payload) {
      this.loading = true
      this.error = null
      try {
        const { data } = await noticeApi.create(payload)
        return data
      } catch (err) {
        this.error = extractErrorMessage(err)
        throw err
      } finally {
        this.loading = false
      }
    },

    async updateNotice(id, payload) {
      this.loading = true
      this.error = null
      try {
        const { data } = await noticeApi.update(id, payload)
        this.currentNotice = data
        return data
      } catch (err) {
        this.error = extractErrorMessage(err)
        throw err
      } finally {
        this.loading = false
      }
    },

    async deleteNotice(id) {
      this.loading = true
      this.error = null
      try {
        await noticeApi.remove(id)
      } catch (err) {
        this.error = extractErrorMessage(err)
        throw err
      } finally {
        this.loading = false
      }
    },
  },
})
