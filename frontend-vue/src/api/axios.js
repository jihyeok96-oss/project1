import axios from 'axios'

// Vite dev server의 proxy 설정을 통해 /api 요청이 Spring Boot(8081)로 전달된다.
const apiClient = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'application/json',
  },
})

export default apiClient
