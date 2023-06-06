<template>
    <div class="nk-chat-row">
        <el-row>
            <!-- 头像 -->
            <el-col :span="2" class="nk-avatar">
                <el-avatar :size="50" :src="data.headerUrl"></el-avatar>
            </el-col>
            <!-- 帖子信息 -->
            <el-col :span="17">
                <table class="info-box">
                    <tbody>
                        <tr>
                            <td>
                                <a href="#" class="name-txt">{{data.username}}</a>
                            </td>
                        </tr>
                        <tr class="msg-txt">
                            <td>{{ data.content }}</td>
                        </tr>
                    </tbody>
                </table>
            </el-col>
            <el-col :span="5">
                <table class="time-box">
                    <tbody>
                        <tr class="time-txt">
                            <td>{{ dataFormat(data.createTime as string) }}</td>
                        </tr>
                    </tbody>
                </table>
            </el-col>
        </el-row>
    </div>
</template>

<script setup lang="ts">
import { PropType } from 'vue'
import type { MessageVO } from '@/models/vo'
import { dataFormat } from '@/utils/dataFormat'
defineProps({
    data: {
        type: Object as PropType<MessageVO>,
        required: true
    },
})
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

.nk-chat-row {
    border-bottom: 1px solid var(--el-border-color-base);
}

.info-box {
    text-align: left;
    margin-left: 20px;
    margin-top: 5px;
    margin-bottom: 5px;
}

.time-box {
    text-align: right;
    margin-right: 0px;
    margin-top: 48px;
}


.name-txt {
    font-size: var(--el-font-size-extra-base);
    color: black;
}

.msg-txt {
    font-size: var(--el-font-size-base);
    color: rgb(98, 100, 102);
}

.time-txt {
    font-size: var(--el-font-size-extra-small);
    color: rgb(98, 100, 102);
}
</style>