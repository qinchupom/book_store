import { createRouter, createWebHistory } from 'vue-router'
import Layout from "../layout/Layout";

const routes = [
  {
    path: '/',
    name: 'Layout',
    redirect:"user",
    component: Layout,
    children:[
      {
        path:'user',
        name:'user',
        component:() => import("@/pages/views/User")
      },
      {
        path: 'book',
        name: 'book',
        component: () => import("@/pages/views/Book")
      },
      {
        path: 'person',
        name: 'Person',
        component: () => import("@/pages/views/Person")
      },
      {
        path: 'password',
        name: 'Password',
        component: () => import("@/pages/views/Password")
      },
      {
        path: 'lendrecord',
        name: 'LendRecord',
        component: () => import("@/pages/views/LendRecord")
      },
      {
        path:'dashboard',
        name:'Dashboard',
        component:() => import("@/pages/views/Dashboard")
      },
      {
        path: 'bookwithuser',
        name: 'BookWithUser',
        component: () => import("@/pages/views/BookWithUser")
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import("@/pages/views/Login")
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import("@/pages/views/Register")
  },
  {
    path: '/forget',
    name: 'Forget',
    component: () => import("@/pages/views/Forget")
  },
  {
    path: '/home',
    name: 'home',
    component:() => import("@/pages/home/Home")
  },
  {
    path: '/books',
    name: 'writer',
    component:() => import("@/pages/books/Books")
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
