<script setup></script>

<template>
  <section class="space-y-6 py-10 text-center">
    <h1 class="text-3xl font-bold">Notice Project</h1>
    <p class="mx-auto max-w-lg text-slate-600">
      Spring Boot REST API + Vue 3로 만든 가장 단순한 공지사항 게시판입니다.<br />
      전체 목록을 한 번에 불러온 뒤, 검색과 페이징은 화면(Vue)에서 처리합니다.
    </p>
    <RouterLink
      to="/notices"
      class="inline-block rounded-lg bg-indigo-600 px-6 py-3 text-sm font-medium text-white hover:bg-indigo-700"
    >
      공지사항 보러가기
    </RouterLink>
  </section>
</template>
