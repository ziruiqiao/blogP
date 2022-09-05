import { createRouter, 
    createWebHashHistory, 
    createWebHistory } from "vue-router";
import Home from '@/views/Home.vue';
	
const index = () => 
      import(/* webpackChunkName: "index"*/ 
             '@/views/index.vue')
const categoryTag = () => 
      import(/* webpackChunkName: "blogallcategorytag"*/ 
             '@/views/blog/BlogAllCategoryTag.vue')
const blogcategorytag = () => 
      import(/* webpackChunkName: "blogcategorytag"*/ 
             '@/views/blog/BlogCategoryTag.vue')
const blogview = () => 
      import(/* webpackChunkName: "blogview"*/ 
             '@/views/blog/BlogView.vue')

const routes = [
	{
    path: '/',
    name: 'Home',
    component: Home,
    children: [
      {
        path: '/',
		    component: index
      },
      {
        path: '/try',
        component: index
      },
      {
        path: '/type/:type',
        name: 'type',
        component: categoryTag
      },
      {
        path: 'type/:type/:id',
        name: 'typeId',
        component: blogcategorytag
      },
      {
        path: '/view/:id',
        component: blogview
      }
    ]
	},
];


const router = createRouter({
  history: createWebHistory(),
  routes,
});
// 全局守卫
router.beforeEach((to, from, next)=>{
    next();//通行证
})
export default router;
