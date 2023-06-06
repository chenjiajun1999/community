import { createApp, toRaw } from 'vue'
import App from '@/App.vue'
import { router } from '@/router'
import ElementPlus from 'element-plus'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import { createPinia, PiniaPluginContext, PiniaPlugin } from "pinia"
import 'element-plus/dist/index.css'
import '@/assets/css/global.css'




// 数据存储本地
const setStorage = (key: string, value: any) => {
    localStorage.setItem(key, JSON.stringify(value))
}
// 获取本地数据
const getStorage = (key: string) => {
    const data = localStorage.getItem(key)
    return data ? JSON.parse(data) : {};
}

const piniaPlugin = (context: PiniaPluginContext) => {
    const { store } = context;
    // $subscribe state值发生变化时会执行传入的回调
    store.$subscribe(() => {
        // 每次修改值的时候更新localStorage数据
        setStorage(`pinia-${store.$id}`, toRaw(store.$state))
    })
    // 每次构建项目的时候从本地存储取值
    const data = getStorage(`pinia-${store.$id}`)
    return {
        ...data
    }
}

const store = createPinia()
store.use(piniaPlugin)
const app = createApp(App)

app.use(router)

app.use(ElementPlus)

// app.use(createPinia())
app.use(store)

app.mount('#app')

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}