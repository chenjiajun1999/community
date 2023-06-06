<template>
    <div>
        <el-row v-clickoutside="hideReplyBtn" @click="inputFocus" class="nk-reply">
            <el-col :span="3">
                <el-avatar class="header-img" :size="50" :src="userHeader"></el-avatar>
            </el-col>
            <el-col :span="17" class="reply-info">
                <div tabindex="0" contenteditable="true" id="replyInput" spellcheck="false" placeholder="输入评论..."
                    class="reply-input" @focus="showReplyBtn" @input="onDivInput($event)">
                </div>
            </el-col>
            <el-col :span="4" class="reply-btn-box" v-show="btnShow">
                <el-button class="reply-btn" size="medium" @click="sendComment" type="primary">发表评论</el-button>
            </el-col>
        </el-row>
        <el-empty v-if="loading" />
        <div v-if="!loading" v-for="(item, i) in data.rows" :key="i" class="author-title reply-father">
            <el-row>
                <el-col :span="3">
                    <el-avatar class="header-img" :size="50" :src="item.headerUrl"></el-avatar>
                </el-col>
                <el-col :span="16" class="author-info">
                    <span class="author-name">{{ item.username }}</span>
                    <span class="author-time">{{ dataFormat(item.createTime as string) }}</span>
                    <div class="talk-box">
                        <p><span class="reply">{{ item.content }}</span></p>
                    </div>
                </el-col>
                <el-col :span="3">
                    <like-reply-comp :like-status="item.likeStatus" :like-count="item.likeCount"
                        :comment-count="item.reply.total" v-on:click-like-btn="onClickLikeBtn(i)" />
                </el-col>
            </el-row>


            <div class="reply-box">
                <div v-for="(reply, j) in item.reply.rows" :key="j" class="author-title">
                    <el-row>
                        <el-col :span="3">
                            <el-avatar class="header-img" :size="50" :src="reply.headerUrl"></el-avatar>
                        </el-col>
                        <el-col :span="16" class="author-info">
                            <span class="author-name">{{ reply.username }}</span>
                            <span class="author-time">{{ dataFormat(reply.createTime as string) }}</span>
                            <div class="talk-box">
                                <p><span v-if="reply.targetUsername">回复 {{ reply.targetUsername }}:&nbsp;&nbsp;</span>
                                    <span class="reply">{{ reply.content }}</span>
                                </p>
                            </div>
                        </el-col>
                        <el-col :span="4">
                            <!-- <table style="text-align: right;margin-right: 0px;margin-top: 10px;">
                                <tbody>
                                    <tr style="font-size: var(--el-font-size-extra-small);color: rgb(98, 100, 102);">
                                        <td>点赞&nbsp;&nbsp;{{ reply.status }}
                                            &nbsp;&nbsp;|&nbsp;&nbsp;回帖&nbsp;&nbsp;</td>
                                    </tr>
                                </tbody>
                            </table> -->
                            <like-reply-comp :like-status="reply.likeStatus" :like-count="reply.likeCount"
                                v-on:click-like-btn="onClickLikeBtnInReply(i, j)" />
                        </el-col>
                        <div class="reply-box"></div>
                    </el-row>
                </div>
            </div>


            <el-row v-show="inputShow(i)" class="nk-reply nk-comment-reply">
                <el-col :span="3">
                    <el-avatar class="header-img" :size="40" :src="userHeader"></el-avatar>
                </el-col>
                <el-col :span="17" class="reply-info">
                    <div tabindex="0" contenteditable="true" spellcheck="false" placeholder="输入评论..."
                        @input="onDivInput($event)" class="reply-input reply-comment-input"></div>
                </el-col>
                <el-col :span="4" class=" reply-btn-box">
                    <el-button class="reply-btn" size="medium" @click="sendCommentReply(i)" type="primary">发表评论
                    </el-button>
                </el-col>
            </el-row>
        </div>
        <el-pagination class="reply-page" background layout="prev, pager, next" v-model:currentPage="page.pageNum"
            :total="data.total" @current-change="handleCurrentChange">
        </el-pagination>
    </div>
</template>

<!--https://blog.csdn.net/zLanaDelRey/article/details/100997792-->
<script setup lang="ts">
import { reactive, ref, Ref, Directive, DirectiveBinding } from 'vue'
import { CommentDataTable } from '@/models/vo'
import { getComment } from '@/api/system'
import { dataFormat } from '@/utils/dataFormat'
import { useRoute } from 'vue-router'
import useStore from '@/store/user'
import LikeReplyComp from '@/components/Like&Reply.vue';
import { like } from '@/api/system'

const store = useStore()

const btnShow = ref(false)
const index = ref(0)
const replyComment = ref('')
const userName = ref('Lana Del Rey')
const userHeader = ref(store.avatar || 'http://images.nowcoder.com/head/100t.png')
const userId = ref(19870621)
const to = ref('')
const toId = ref(-1)

const data = ref<CommentDataTable>({})
const route = useRoute()

// const total = ref(4)
const loading = ref(true)
const page = reactive({
    pageNum: 1,
    pageSize: 4,
    orderByColumn: "createTime",
    isAsc: "desc"
})

const onClickLikeBtn = (index: number) => {
    if (data.value.rows) {
        like({
            entityType: 1,
            entityId: data.value.rows[index].id
        }).then(res => {
            if (data.value.rows) {
                data.value.rows[index].likeStatus = res.data.likeStatus
                data.value.rows[index].likeCount = res.data.likeCount
            }
        })
    }
}
const onClickLikeBtnInReply = (index: number, replyIndex: number) => {

    if (data.value.rows) {
        like({
            entityType: 1,
            entityId: Number(data.value.rows[index].reply.rows?.[replyIndex].id)
        }).then(res => {
            if (data.value.rows) {
                let reply = data.value.rows[index].reply.rows?.[replyIndex]
                if (reply) {
                    reply.likeStatus = res.data.likeStatus
                    reply.likeCount = res.data.likeCount
                }
            }
        })
    }
}


const handleCurrentChange = () => {

    getComment(
        Number(route.params.id),
        {
            pageNum: page.pageNum,
            pageSize: page.pageSize,
        }).then(res => {
            // total.value = res.total
            data.value = res
            loading.value = false
        })

}

interface HTMLElementPlus extends HTMLElement {
    __vueClickOutside__?: any
}
const vClickoutside: Directive = {
    // 初始化指令
    created(el: HTMLElementPlus, binding: DirectiveBinding) {
        const documentHandler = (e: Event) => {
            // 这里判断点击的元素是否是本身，是本身，则返回
            if (el.contains(e.target as Node || null)) {
                return false;
            }
            // 只有不是本身 才会响应事件
            // 判断指令中是否绑定了函数
            if (binding.value) {
                // 如果绑定了函数 则调用那个函数，此处binding.value就是handleClose方法
                binding.value(e);
            }
        }

        // 给当前元素绑定个私有变量，方便在unbind中可以解除事件监听
        el.__vueClickOutside__ = documentHandler
        document.addEventListener('click', documentHandler)

    },
    // vue 弹出框 下面 响应
    unmounted(el: HTMLElementPlus) {
        // 解除事件监听
        document.removeEventListener('click', el.__vueClickOutside__)
        delete el.__vueClickOutside__
    },
}

const messageTitleClick = (num: number) => {
    console.log("点击了消息", num)
}

const inputFocus = () => {
    let replyInput = document.getElementById('replyInput')
    if (replyInput) {
        replyInput.style.padding = "8px 8px"
        replyInput.style.border = "2px solid black"
        replyInput.focus()
    }
}
const showReplyBtn = () => {
    btnShow.value = true
}
const hideReplyBtn = () => {
    let replyInput = document.getElementById('replyInput')
    btnShow.value = false
    if (replyInput) {
        replyInput.style.padding = "10px"
        replyInput.style.border = "none"
    }
}


const showReplyInput = (i: number, name: string, id: number) => {
    // data.value[index.value].isShow = false
    // index.value = i
    // data.value[i].isShow = true
    // to.value = name
    // toId.value = id
}
const inputShow = (i: number) => {
    // return data.value[i].isShow
}
const sendComment = () => {
    if (!replyComment.value) {
    } else {
    }
}
const sendCommentReply = (i: number) => {
    if (!replyComment.value) {
    } else {
    }
}
const onDivInput = (event: Event) => {
    let e = event.target as HTMLInputElement
    replyComment.value = e.innerHTML
}


const dateStr = (date: string) => {
    //获取js 时间戳
    let time = new Date().getTime()
    //去掉 js 时间戳后三位，与 php 时间戳保持一致
    time = (time - parseInt(date)) / 1000
    //存储转换值
    let s
    if (time < 60 * 10) {//十分钟内
        return '刚刚'
    } else if ((time < 60 * 60) && (time >= 60 * 10)) {
        //超过十分钟少于1小时
        s = Math.floor(time / 60)
        return s + "分钟前"
    } else if ((time < 60 * 60 * 24) && (time >= 60 * 60)) {
        //超过1小时少于24小时
        s = Math.floor(time / 60 / 60)
        return s + "小时前"
    } else if ((time < 60 * 60 * 24 * 30) && (time >= 60 * 60 * 24)) {
        //超过1天少于30天内
        s = Math.floor(time / 60 / 60 / 24)
        return s + "天前"
    } else {
        //超过30天ddd
        let ans = new Date(parseInt(date))
        return ans.getFullYear() + "/" + (ans.getMonth() + 1) + "/" + ans.getDate()
    }
}


// init
handleCurrentChange()
</script>
<style scoped lang="css">
.nk-reply {
    padding: 20px;
    border-bottom: 1px solid var(--el-border-color-base);
}

.nk-reply .header-img {
    display: inline-block;
    margin: 0px 9px;
    margin-top: 6px;
    text-align: center;
    /* vertical-align: top; */

}

.nk-reply .reply-info {
    display: inline-block;
    margin-left: 10px;
    width: 100%;
    margin: 10px 0;
}

@media screen and (max-width: 1200px) {
    .nk-reply .reply-info {
        width: 80%;
    }
}

.nk-reply .reply-info .reply-input {
    /* margin-left: 35px; */
    min-height: 20px;
    line-height: 22px;
    padding: 10px 10px;
    color: #ccc;
    background-color: #fff;
    border-radius: 5px;
}

.nk-reply .reply-info .reply-input:empty:before {
    content: attr(placeholder);
}

.nk-reply .reply-info .reply-input:focus:before {
    content: none;
}

.nk-reply .reply-info .reply-input:focus {
    padding: 8px 8px;
    border: 2px solid rgb(0, 0, 0);
    box-shadow: none;
    outline: none;
}

.nk-reply .reply-btn-box {
    height: 25px;
    margin: 12px 0;
}

.nk-reply .reply-btn-box .reply-btn {
    position: relative;
    float: right;
    margin-right: 5px;
}

.nk-comment-reply {
    margin-left: 115px;
}

.nk-comment-reply .reply-input {
    width: flex;
}

.author-title:not(:last-child) {
    border-bottom: 1px solid rgba(178, 186, 194, 0.3);
}

.author-title {
    margin-top: 1rem;
    padding: 10px;
}

.author-title .header-img {
    display: inline-block;
    margin: 0px 18px;
    margin-top: 10px;
    text-align: center;
}

.author-title .author-info {
    display: inline-block;
    margin-left: 5px;
    width: 60%;
    height: 40px;
    line-height: 20px;
}

.author-title .author-info>span {
    display: block;
    cursor: pointer;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.author-title .author-info .author-name {
    font-size: var(--el-font-size-extra-base);
    color: black;
}

.author-title .author-info .author-time {
    margin-top: 5px;
    font-size: var(--el-font-size-extra-small);
    color: rgb(98, 100, 102)
}

.author-title .icon-btn {
    width: 15%;
    padding: 0 !important;
    float: right;
    /* margin-top: 5px; */
}

@media screen and (max-width: 1200px) {
    .author-title .icon-btn {
        width: 20%;
        padding: 7px;
    }
}

.author-title .icon-btn>span {
    cursor: pointer;
}

.author-title .icon-btn .iconfont {
    margin: 0 5px;
}

.author-title .talk-box {
    margin-top: 15px;
}

.author-title .talk-box>p {
    margin: 0;
}

.author-title .talk-box .reply {
    font-size: var(--el-font-size-extra-base);
    line-height: 20px;
    color: black
}

.author-title .reply-box {
    margin: 10px 0 0 115px;
    background-color: #efefef;
}

.reply-page {
    margin-top: 30px;
    padding-bottom: 30px;
    text-align: center;
}

.status {
    font-size: var(--el-font-size-extra-small);
    color: rgb(98, 100, 102);
}

.status-tab {
    text-align: right;
    margin-right: 0px;
    margin-top: 10px;
}
</style>
