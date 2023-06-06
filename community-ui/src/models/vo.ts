export interface DiscussPostVO {

    id?: number;
    userId?: number;
    title?: string;
    content?: string;
    type?: number;
    status?: number;
    createTime?: string;
    commentCount?: number;
    score?: number;
    username?: string;
    headerUrl?: string;

    likeCount?: number;
    likeStatus?: string;
}

export interface MessageVO {

    id: number;
    fromId: number;
    toId: number;
    conversationId: string;
    content: string;
    status: number;
    createTime: string;

    userId: number;
    headerUrl: string;
    username: string;

    unreadCount: number;
}


export interface CommentDataTable {

    total?:number
    rows?:[{

        id: number;
        userId: number;
        entityType: number;
        entityId: number;
        targetId: number;
        content: string;
        status: number;
        createTime: string;
        username: string;
        headerUrl: string;
        targetUsername: string;

        likeCount?: number;
        likeStatus?: string;

        reply: CommentDataTable

    }]
}
