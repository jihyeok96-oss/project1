import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import NoticeListView from '@/views/NoticeListView.vue'
import NoticeDetailView from '@/views/NoticeDetailView.vue'
import NoticeFormView from '@/views/NoticeFormView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
  },
  {
    path: '/notices',
    name: 'notice-list',
    component: NoticeListView,
  },
  {
    path: '/notices/new',
    name: 'notice-new',
    component: NoticeFormView,
  },
  {
    // id는 숫자만 허용한다 (라우터 단에서 ID 파라미터 검증)
    path: '/notices/:id(\\d+)',
    name: 'notice-detail',
    component: NoticeDetailView,
    props: (route) => ({ id: Number(route.params.id) }),
  },
  {
    path: '/notices/:id(\\d+)/edit',
    name: 'notice-edit',
    component: NoticeFormView,
    props: (route) => ({ id: Number(route.params.id) }),
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
