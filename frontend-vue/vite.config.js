import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import tailwindcss from '@tailwindcss/vite'
import path from 'path'
import { fileURLToPath } from 'url'

const __dirname = path.dirname(fileURLToPath(import.meta.url))

export default defineConfig({
  plugins: [vue(), tailwindcss()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src'),
    },
  },
  server: {
    port: 5173,
    // Spring Boot(8081)로 API 요청을 프록시한다.
    // CORS 설정은 백엔드/프런트엔드 중 하나만 사용한다는 PRD 원칙에 따라
    // 여기서는 Vite proxy 방식을 사용하고 백엔드에는 별도 CORS 설정을 추가하지 않는다.
    proxy: {
      '/api': {
        target: 'http://localhost:8081',
        changeOrigin: true,
      },
    },
  },
})
