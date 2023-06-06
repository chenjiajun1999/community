/**
 * entity
 */
export interface Page {

    pageNum: number
    pageSize: number
    orderByColumn?: string
    isAsc?: string

}

export interface User {

    id?: number
    username?: string
    password?: string
    salt?: string
    email?: string
    type?: number
    status?: number
    activationCode?: string
    headerUrl?: string
    createTime?: string
}

export interface DiscussPost {

    id?: number
    userId?: number
    title?: string
    content?: string
    type?: number
    status?: number
    createTime?: string
    commentCount?: number
    score?: number
}

export interface Message {

    id?: number
    fromId?: number
    toId?: number
    conversationId?: string
    content?: string

    /**
     * 0-未读;1-已读;2-删除;
     */
    status?: number
    createTime?: string

}

export interface Comment {

    id?: number
    userId?: number
    entityType?: number
    entityId?: number
    targetId?: number
    content?: string
    status?: number
    createTime?: string

}


export interface DiscussPostInfo {

    user: User
    discussPost: DiscussPost

}

export interface MessageInfo {

    unreadCount: number
    targetUser: User
    message: Message

}


export interface CommentInfo {

    user: User
    comment: Comment
    isShow: Boolean
    count: number
    reply?: CommentInfo[]

}


/**
 * demo
 */
export const user: User = {
    username: 'Lana Del Rey',
    headerUrl: 'http://images.nowcoder.com/head/100t.png',
}
export const discussPost: DiscussPost = {
    id: 109,
    userId: 0,
    title: '因特网求职暖春计划',
    content: `金三银四的金三已经到了，你还沉浸在过年的喜悦中吗？ 
    如果是，那我要让你清醒一下了：目前大部分公司已经开启了内推，正式网申也将在3月份陆续开始，金三银四，春招的求职黄金时期已经来啦！！！ 再不准备，
    作为19应届生的你可能就找不到工作了。。。作为20届实习生的你可能就找不到实习了。。。 现阶段时间紧，任务重，能做到短时间内快速提升的也就只有算法了， 
    那么算法要怎么复习？重点在哪里？常见笔试面试算法题型和解题思路以及最优代码是怎样的？ 
    跟左程云老师学算法，不仅能解决以上所有问题，还能在短时间内得到最大程度的提升！！！`,
    type: 0,
    status: 0,
    createTime: '2019-04-04T03:53:36',
    commentCount: 0,
    score: 0.0
}
export const message: Message = {
    id: 0,
    fromId: 111,
    toId: 112,
    conversationId: '111_112',
    content: '你好啊',
    status: 0,
    createTime: '2019-05-08T08:56:46',
}


export const discussPostInfoList: DiscussPostInfo[] = []
export const messageInfoList: MessageInfo[] = []
for (var i = 0; i < 5; i++) {
    discussPostInfoList.push({ user: user, discussPost: discussPost })
    messageInfoList.push({ targetUser: user, message: message, unreadCount: 1 })
}


export const commentInfoList: CommentInfo[] = [{

    count: 2,
    isShow: false,

    user: {
        username: 'Lana Del Rey',
        headerUrl: 'https://ae01.alicdn.com/kf/Hd60a3f7c06fd47ae85624badd32ce54dv.jpg',
    },
    comment: {
        content: '我发布一张新专辑Norman Fucking Rockwell,大家快来听啊!',
        createTime: '2019-04-04T03:53:36',
        status: 15
    },



    reply: [{
        count: 1,
        isShow: false,
        user: {
            username: 'Taylor Swift',
            headerUrl: 'https://ae01.alicdn.com/kf/H94c78935ffa64e7e977544d19ecebf06L.jpg',
        },
        comment: {
            content: '我很喜欢你的新专辑！！',
            createTime: '2019-04-04T03:53:36',
            status: 15
        },

    }, {
        count: 0,
        isShow: false,
        user: {
            username: 'Ariana Grande',
            headerUrl: 'https://ae01.alicdn.com/kf/Hf6c0b4a7428b4edf866a9fbab75568e6U.jpg',
        },
        comment: {
            content: '别忘记宣传我们的合作单曲啊',
            createTime: '2019-04-04T03:53:36',
            status: 5
        },
    }, {
        count: 0,
        isShow: false,
        user: {
            username: 'Ariana Grande',
            headerUrl: 'https://ae01.alicdn.com/kf/Hf6c0b4a7428b4edf866a9fbab75568e6U.jpg',
        },
        comment: {
            content: '别忘记宣传我们的合作单曲啊',
            createTime: '2019-04-04T03:53:36',
            status: 5
        },
    }, {
        count: 0,
        isShow: false,
        user: {
            username: 'Ariana Grande',
            headerUrl: 'https://ae01.alicdn.com/kf/Hf6c0b4a7428b4edf866a9fbab75568e6U.jpg',
        },
        comment: {
            content: '别忘记宣传我们的合作单曲啊',
            createTime: '2019-04-04T03:53:36',
            status: 5
        },
    }]
}, {

    count: 2,
    isShow: false,

    user: {
        username: 'Lana Del Rey',
        headerUrl: 'https://ae01.alicdn.com/kf/Hd60a3f7c06fd47ae85624badd32ce54dv.jpg',
    },
    comment: {
        content: '我发布一张新专辑Norman Fucking Rockwell,大家快来听啊!',
        createTime: '2019-04-04T03:53:36',
        status: 15
    },



    reply: [{
        count: 1,
        isShow: false,
        user: {
            username: 'Taylor Swift',
            headerUrl: 'https://ae01.alicdn.com/kf/H94c78935ffa64e7e977544d19ecebf06L.jpg',
        },
        comment: {
            content: '我很喜欢你的新专辑！！',
            createTime: '2019-04-04T03:53:36',
            status: 15
        },

    }]
}, {

    count: 2,
    isShow: false,

    user: {
        username: 'Lana Del Rey',
        headerUrl: 'https://ae01.alicdn.com/kf/Hd60a3f7c06fd47ae85624badd32ce54dv.jpg',
    },
    comment: {
        content: '我发布一张新专辑Norman Fucking Rockwell,大家快来听啊!',
        createTime: '2019-04-04T03:53:36',
        status: 15
    },



    reply: [{
        count: 1,
        isShow: false,
        user: {
            username: 'Taylor Swift',
            headerUrl: 'https://ae01.alicdn.com/kf/H94c78935ffa64e7e977544d19ecebf06L.jpg',
        },
        comment: {
            content: '我很喜欢你的新专辑！！',
            createTime: '2019-04-04T03:53:36',
            status: 15
        },

    }]
}]



export interface CommentDataTable {

    // user: User
    // comment: Comment
    // isShow: Boolean
    // count: number
    // reply?: CommentInfo[]

    total: number
    rows: {
        comment: Comment
        user: User
        targetUser: User
        reply: CommentDataTable
    }[]
}


export const commentDataTable: CommentDataTable = {

    total: 2,
    rows: [
        {
            user: {
                username: 'Lana Del Rey',
                headerUrl: 'https://ae01.alicdn.com/kf/Hd60a3f7c06fd47ae85624badd32ce54dv.jpg',
            },
            targetUser:{},
            comment: {
                content: '我发布一张新专辑 Norman Fucking Rockwel，大家快来听啊！',
                createTime: '2019-04-04T03:53:36',
                status: 15
            },
            reply: {
                total: 0,
                rows: []
            },
        },
        {
            user: {
                username: 'Lana Del Rey',
                headerUrl: 'https://ae01.alicdn.com/kf/Hd60a3f7c06fd47ae85624badd32ce54dv.jpg',
            },
            targetUser:{},
            comment: {
                content: '我发布一张新专辑 Norman Fucking Rockwell，大家快来听啊！',
                createTime: '2019-04-04T03:53:36',
                status: 15
            },
            reply: {
                total: 0,
                rows: []
            },
        }
    ]
}
