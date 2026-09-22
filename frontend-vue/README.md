# notice-project / frontend

Vue 3 + Vite + Pinia + Vue Router + Axios + Tailwind CSS 4 기반의 공지사항 프런트엔드입니다.
검색과 페이징은 서버가 아닌 이 프로젝트(Vue)에서 처리합니다. (`PRD.md` 참고)

## 요구 사항

- Node.js 20+ 권장
- 백엔드(Spring Boot, `../backend/notice`)가 `http://localhost:8081`에서 실행 중이어야 합니다.

## 시작하기

```bash
cd frontend
npm install
npm run dev
```

개발 서버는 기본적으로 `http://localhost:5173`에서 실행되며, `/api`로 시작하는 요청은
`vite.config.js`의 proxy 설정을 통해 백엔드(`http://localhost:8081`)로 전달됩니다.

## 빌드

```bash
npm run build
npm run preview
```

## 디렉터리 구조

```text
src/
  api/
    axios.js        # axios 인스턴스 (baseURL: /api)
    noticeApi.js     # Notice REST API 래퍼
  stores/
    notice.js        # Pinia store (목록/검색/페이징/CRUD 상태)
  router/
    index.js         # Vue Router 설정
  views/
    HomeView.vue
    NoticeListView.vue
    NoticeDetailView.vue
    NoticeFormView.vue  # 등록/수정 공용
  components/
    Pagination.vue
  App.vue
  main.js
  style.css
```

## 참고

- 백엔드 `application.yaml` 기준 서버 포트는 `8081`입니다. PRD의 `NFR-05`에 8080으로
  적힌 부분과 다르므로, 실제 포트(8081)에 맞춰 `vite.config.js`의 proxy를 설정했습니다.
- CORS는 백엔드에 별도 설정을 추가하지 않고, Vite dev server의 proxy로 처리합니다
  (PRD: "Vite 개발 서버 proxy를 사용하거나 Spring CORS를 사용한다. 하나의 방식만 선택").
  프로덕션 배포 시에는 별도의 리버스 프록시 또는 백엔드 CORS 설정이 필요합니다.
