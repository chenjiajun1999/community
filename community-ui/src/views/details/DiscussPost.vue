<template>
    <div class="nk-post nk-main-repair">
        <el-row>
            <el-col :span="6"></el-col>
            <el-col :span="12">
                <div class="post-box">
                    <el-empty v-if="loading" />
                    <div v-if="!loading">
                        <posts-row class="post-header" :data="data" />
                        <div class="post-info">
                            {{ data.content }}
                        </div>
                    </div>
                </div>
                <div class="post-box">
                    <comment class="post-comment" />
                </div>
            </el-col>
            <el-col :span="6"></el-col>
        </el-row>
    </div>
</template>

<script setup lang="ts">
import { reactive, ref, Ref, PropType } from 'vue'
import { DiscussPostVO } from '@/models/vo'
import { getDiscussPostById } from '@/api/system'
import PostsRow from '@/components/PostsRow.vue'
import Comment from '@/views/details/Comment.vue'
import { useRoute } from 'vue-router'

const data = ref<DiscussPostVO>({})
const route = useRoute()
const loading = ref(true)
// const data = reactive(info)

loading.value = true
getDiscussPostById(Number(route.params.id)).then(res => {

    data.value = res.data
    loading.value = false

})


</script>

<style scoped lang="css">
.nk-post .post-box {
    background-color: white;
}

.nk-post .post-header {
    padding: 15px;
    margin: 0px 20px;
}

.nk-post .post-info {
    margin-bottom: 20px;
    padding: 30px;
}

.nk-post .post-comment {
    margin: 0px 20px;
}
</style>