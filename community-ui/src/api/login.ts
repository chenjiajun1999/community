import request from "@/utils/request"
import { LoginDTO } from "@/models/dto"

// 登录方法
export function login(data:LoginDTO) {
    return request({
        url: '/login',
        method: 'post',
        data: data
    })
}

// 获取用户详细信息
export function getLoginUser() {
    return request({
        url: '/user',
        method: 'get'
    })
}

// 退出方法
export function logout(token:string) {
    return request({
        url: '/logout',
        method: 'post',
        data: token
    })
}
export function getCodeImg() {
    return request({
        url: '/captcha',
        method: 'get',
    })
}