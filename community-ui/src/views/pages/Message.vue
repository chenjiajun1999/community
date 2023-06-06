<template>
    <div class="nk-message nk-main-repair">
        <el-row>
            <el-col :span="6"></el-col>
            <el-col :span="12">
                <el-tabs type="card" @tab-click="handleClick">
                    <el-tab-pane v-for="label in tabLabel" :label="label">
                        <el-empty v-if="loading" />
                        <message-row :data="i" v-for="i in data"/>
                        <el-pagination background layout="prev, pager, next" v-model:currentPage="page.pageNum"
                            :total="total" @current-change="handleCurrentChange"/>
                    </el-tab-pane>

                </el-tabs>
            </el-col>
            <el-col :span="6"></el-col>
        </el-row>
    </div>
</template>

<script setup lang="ts">
import { Ref, ref, reactive } from 'vue'
import { MessageVO } from '@/models/vo'
import MessageRow from '@/components/MessageRow.vue'
import { getMessage } from '@/api/system'
import {Page} from '@/models/pojo'

const total = ref(0)
const loading = ref(false)
const tabLabel = ref(['朋友私信', '系统通知'])
const map = new Map([["朋友私信", "系统通知"],["chat", "notice"]]);

const type = ref("chat")
const page = reactive<Page>({
    pageNum:1,
    pageSize:4,
})

const totalUnreadCount = ref(0)
const data = ref<MessageVO[]>([])
// const data = reactive({messageInfoList})

const handleClick = (tab: any, event: unknown) => {
    type.value = map.get(tab.props.label) || "chat"
    handleCurrentChange()
}
const handleCurrentChange = () => {

    getMessage({
        pageNum: page.pageNum,
        pageSize: page.pageSize,

    }).then(res => {
        totalUnreadCount.value = res.data
        total.value = res.total
        data.value = res.rows
        loading.value = false
    })
}

// init
loading.value = true
handleCurrentChange()
</script>

<style scoped>
.el-tabs {
    background-color: white;
    padding: 20px;
}
.el-pagination {
    margin-top: 30px;
    text-align: center;
}
</style>