<template>
  <div class="nk-index">
    <!-- Element Plus 布局容器 -->
    <el-container>
      <!-- 头部 -->
      <el-header height="60px" class="nk-layer">
        <page-header></page-header>
      </el-header>
      <!-- 页面主内容 交给路由转发 -->
      <el-main>
        <router-view></router-view>
      </el-main>
      <!-- 尾部 -->
      <el-footer height="180px" class="nk-layer">
        <page-footer></page-footer>
      </el-footer>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import PageFooter from '@/components/PageFooter.vue'
import PageHeader from '@/components/PageHeader.vue'
import { ref, onMounted } from 'vue'
import useStore from '@/store/user'
import { useRouter } from 'vue-router'
import { getLoginUser } from '@/api/login'

// const style = ref({minHeight: '0px'})
const minHeight = ref('0px')

const store = useStore()
const router = useRouter()
if (store.token) {
  getLoginUser().then().catch(error => {
    // token 失效退出登录
    store.logout()
    router.push('')
  })
}

onMounted(() => {
  minHeight.value = window.innerHeight - 180 + 'px'
})

</script>

<style scoped>
.nk-layer {
  background-color: #343a40;
  color: var(--el-text-color-primary);
  text-align: center;
  width: 100%;
  min-width: 1080px;
}

.el-header {
  position: fixed;
  z-index: 10;
  top: 0;
}

.el-footer {
  position: relative;
  bottom: 0;
}

.el-main {
  background-color: #eee;
  color: var(--el-text-color-primary);
  text-align: left;
  line-height: 40px;
  min-height: v-bind(minHeight)
}

/* body > .el-container {
} */
</style>