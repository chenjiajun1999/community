import request from "@/utils/request"

export function getStatusById(id: number) {
    return request({
        url: '/register/' + id + '/status',
        method: 'get',
    })
}

export function activate(id: number, activationCode: string) {
    return request({
        url: '/register/' + activationCode + '/activation/' + id,
        method: 'post',
    })
}