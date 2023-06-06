import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        name: 'home',
        component: () => import('@/views/pages/Home.vue'),
    },
    {
        path: '/p/:id',
        name: 'discussPost',
        component: () => import('@/views/details/DiscussPost.vue'),
    },
    {
        path: '/message',
        name: 'message',
        component: () => import('@/views/pages/Message.vue'),
    },
    {
        path: '/register',
        name: 'register',
        component: () => import('@/views/pages/Register.vue'),
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('@/views/pages/Login.vue'),
    },
    {
        path: '/:id/result',
        name: 'result',
        component: () => import('@/views/pages/Activate.vue'),
    }, 
    {
        path: '/:activateCode/activation/:userId',
        name: 'activate',
        component: () => import('@/views/pages/Activate.vue'),
    },

]

export const router = createRouter({

    history: createWebHistory(),
    routes,
})

