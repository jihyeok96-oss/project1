<script setup></script>

<template>
  <div class="min-h-screen bg-slate-50 text-slate-900">
    <header class="border-b border-slate-200 bg-white">
      <div class="mx-auto flex max-w-4xl items-center justify-between px-4 py-4">
        <RouterLink to="/" class="text-lg font-bold text-indigo-600">
          Notice Project
        </RouterLink>
        <nav class="flex gap-4 text-sm font-medium text-slate-600">
          <RouterLink to="/" class="hover:text-indigo-600">홈</RouterLink>
          <RouterLink to="/notices" class="hover:text-indigo-600">공지사항</RouterLink>
        </nav>
      </div>
    </header>

    <main class="mx-auto max-w-4xl px-4 py-8">
      <RouterView />
    </main>
  </div>
</template>
