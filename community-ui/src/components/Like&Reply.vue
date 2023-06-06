<template>
    <div class="like-and-reply-box count-style">
        <span @click="emit('clickLikeBtn')" v-if="likeStatus === '0'"><i class="iconfont icon-dianzan"></i></span>
         <span @click="emit('clickLikeBtn')" v-if="likeStatus === '1'"><i class="iconfont icon-dianzan_kuai"></i></span>
        {{ likeCount }}&nbsp;&nbsp;
        <span @click="emit('clickReplyBtn')" class="iconfont icon-pinglun"></span>
        {{ commentCount }}&nbsp;&nbsp;
    </div>
</template>
<script setup lang="ts">
import '@/assets/icon/iconfont.css'
defineProps({
    likeCount: {
        type: Number,
    },
    commentCount: {
        type: Number,
    },
    likeStatus: {
        type: String,
    }
})
const emit = defineEmits(["clickLikeBtn", "clickReplyBtn"])

</script>
<style scoped>
.like-and-reply-box {
    text-align: right;
    margin-right: 20px;
    margin-top: 48px;
}

.count-style {
    font-size: var(--el-font-size-extra-small);
    color: rgb(98, 100, 102);
}
</style>