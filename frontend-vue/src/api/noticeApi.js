import apiClient from './axios'

// Spring Boot NoticeRestController(/api/notices)와 1:1로 대응하는 API 래퍼.
// 검색/페이징 파라미터는 사용하지 않는다 (PRD FR-01 참고).
export const noticeApi = {
  getList() {
    return apiClient.get('/notices')
  },
  getDetail(id) {
    return apiClient.get(`/notices/${id}`)
  },
  create(payload) {
    return apiClient.post('/notices', payload)
  },
  update(id, payload) {
    return apiClient.put(`/notices/${id}`, payload)
  },
  remove(id) {
    return apiClient.delete(`/notices/${id}`)
  },
}
