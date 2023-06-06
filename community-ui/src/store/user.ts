import { getToken, setToken, removeToken ,Cookies} from '@/utils/cookies'
import { login, getLoginUser, logout } from '@/api/login'
import { defineStore } from 'pinia'
import { LoginDTO } from '@/models/dto'
import { ElMessage } from 'element-plus'

export default defineStore({
    id: 'user',
    state: () => ({
        isLogin: false,
        token: getToken(),
        name: '',
        avatar: '',
        // roles: [],
        // permissions: []
    }),
    getters: {
    },
    actions: {
        setToken(token: string) {
            this.token = token
        },
        setName(name: string) {
            this.name = name
        },
        setAvatar(avatar: string) {
            this.avatar = avatar
        },
        changeState() {
            this.isLogin = !this.isLogin
        },

        
        // setRoles(roles: never[]) {
        //     this.roles = roles
        // },
        // setPermissions(permissions: never[]) {
        //     this.permissions = permissions
        // },

        // resolve(data) 触发 then((data)=>{})

        login(body: LoginDTO) {

            return new Promise<void>((resolve, reject) => {
                login(body).then(res => {
                    this.changeState()
                    this.setToken(res.token)
                    setToken(res.token)
                    this.getInfo()
                    resolve()
                }).catch(error => {
                    reject(error)
                })

            })

        },
        getInfo() {
            return new Promise<void>((resolve, reject) => {
                getLoginUser().then(res => {
                    const user = res.data
                    const avatar = user.headerUrl === "" ? "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" : user.headerUrl
                    this.setName(user.userName)
                    this.setAvatar(avatar)
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },
        // 退出系统
        logout() {
            return new Promise<void>((resolve, reject) => {
                logout(this.token).then(() => {
                    this.changeState()
                    this.setToken('')
                    removeToken()
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },

    }
})