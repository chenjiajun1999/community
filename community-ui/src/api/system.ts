import request from "@/utils/request"
import { Page } from "@/models/pojo"

export function getDiscussPost(query: Page) {
    return request({
        url: '/discussPost',
        method: 'get',
        params: query
    })
}


export function getDiscussPostById(id: number) {
    return request({
        url: '/discussPost/' + id,
        method: 'get',
    })
}


export function getMessage(query: Page) {
    return request({
        url: '/message',
        method: 'get',
        params: query
    })
}

export function getComment(id: number, query: Page) {
    return request({
        url: '/comment/' + id,
        method: 'get',
        params: query
    })
}

export function like(query: {
    
    entityType:number,
    entityId:number

}) {
    return request({
        url: '/like',
        method: 'post',
        params: query
    })
}