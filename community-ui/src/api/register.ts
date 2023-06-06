import request from "@/utils/request"
import {RegisterDTO} from "@/models/dto"
export function checkUsername(username:string){
    return request({
        url: '/register/username/'+username +'/status',
        method: 'get',
    })
}

export function checkEmail(email:string){
    return request({
        url: '/register/email/'+email+'/status',
        method: 'get',
    })
}

export function register(user:RegisterDTO){
    return request({
        url: '/register',
        method: 'post',
        data: user,
    })
}