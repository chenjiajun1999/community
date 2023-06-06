<template>
    <div class="nk-post-row">
        <el-row>

            <el-col :span="2" class="nk-avatar">
                <el-avatar :size="50" :src="data.headerUrl"></el-avatar>
            </el-col>
            <el-col :span="18">
                <table class="info-box">
                    <tbody>
                        <tr>
                            <td>
                                <a :href="'/p/' + data.id" style="color:black;">{{ data.title }}</a>
                            </td>
                        </tr>
                        <tr class="info-style">
                            <td>{{ data.username }}&nbsp;&nbsp;发布于&nbsp;&nbsp;
                                {{ dataFormat(data.createTime as string) }}
                            </td>
                        </tr>
                    </tbody>
                </table>
            </el-col>
            <el-col :span="4">
                <like-reply-comp :like-status="data.likeStatus" :like-count="data.likeCount"
                    :comment-count="data.commentCount" v-on:click-like-btn="onClickLikeBtn()" />
            </el-col>
        </el-row>
    </div>
</template>

<script setup lang="ts">
import LikeReplyComp from '@/components/Like&Reply.vue';
import { PropType,ref } from 'vue'
import { DiscussPostVO } from '@/models/vo'
import { dataFormat } from '@/utils/dataFormat'
import { like } from '@/api/system'
const props = defineProps({
    data: {
        type: Object as PropType<DiscussPostVO>,
        required: true
    },
})

const onClickLikeBtn = () => {

    like({
        entityType: 0,
        entityId: Number(props.data.id)
    }).then(res => {
        props.data.likeStatus = res.data.likeStatus
        props.data.likeCount = res.data.likeCount
    })


}
</script>

<style scoped>
a:link {
    text-decoration: none;
}

a:visited {
    text-decoration: none;
}

a:hover {
    text-decoration: none;
    color: #000000;
}

a:active {
    text-decoration: none;
    color: #000000;
}

.nk-avatar {
    text-align: center;
    margin-top: 25px;
}

.nk-post-row {
    border-bottom: 1px solid var(--el-border-color-base);
}

.info-box {
    text-align: left;
    margin-left: 20px;
    margin-top: 5px;
    margin-bottom: 5px;
}

.info-style {
    font-size: var(--el-font-size-extra-small);
    color: rgb(98, 100, 102);
}
</style>