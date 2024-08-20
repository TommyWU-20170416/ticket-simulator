import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', redirect: '/home' },
    { path: '/home', component: () => import('@/views/HomeView.vue') },
    {
      path: '/customer',
      component: () => import('@/views/CustomerView.vue'),
      children: [
        {
          path: 'schedule',
          component: () => import('@/views/customer/ScheduleView.vue')
        },
        {
          path: 'ticketstype',
          component: () => import('@/views/customer/TicketsTypeView.vue')
        },
        {
          path: 'ticketprice',
          component: () => import('@/views/customer/TicketPriceCalculationView.vue')
        }
      ]
    },
    { path: '/member', component: () => import('@/views/MemberView.vue') },
    { path: '/ticket', component: () => import('@/views/TicketView.vue') },
    { path: '/travel', component: () => import('@/views/TravelView.vue') }
  ]
})

export default router
