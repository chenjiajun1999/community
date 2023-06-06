<template>
    <div class="nk-home nk-main-repair">
        <el-row>
            <el-col :span="6"></el-col>
            <el-col :span="12">
                <el-tabs type="card" @tab-click="handleClick">
                    <el-tab-pane v-for="label in tabLabel" :label="label">
                        <el-empty v-if="loading" />
                        <posts-row v-if="!loading" :data="i" v-for="i in data"/>
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
import { reactive, ref, Ref, PropType } from 'vue'
import PostsRow from '@/components/PostsRow.vue'
import { getDiscussPost } from '@/api/system'
import { DiscussPostVO } from '@/models/vo'
import { Page } from '@/models/pojo'

const loading = ref(false)

const tabLabel = ref(['最新', '最热'])
const map = new Map([["最新", "createTime"],["最热", "commentCount"]]);


const total = ref(0)
const page = reactive<Page>({
    pageNum: 1,
    pageSize: 4,
    orderByColumn: "createTime",
    isAsc: "desc"
})

const data = ref<DiscussPostVO[]>([])
const handleClick = (tab: any, event: Event) => {

    page.orderByColumn = map.get(tab.props.label) || "createTime"
    handleCurrentChange()

}
const handleCurrentChange = () => {

    getDiscussPost({
        pageNum: page.pageNum,
        orderByColumn: page.orderByColumn,
        pageSize: page.pageSize,
        isAsc: page.isAsc,
    }).then(res => {
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