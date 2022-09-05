<template>
	 <div class="me-ct-body" v-title :data-title="title">
	    <el-container class="me-ct-container">
	      <el-main>
	        <div class="me-ct-title me-area">
	          <template v-if="this.$route.params.type === 'tag'">
	            <img class="me-ct-picture" :src="ct.avatar?ct.avatar:defaultAvatar"/>
	            <h3 class="me-ct-name">{{ct.tagName}}</h3>
	          </template>
	
	          <template v-else>
	            <img class="me-ct-picture" :src="ct.avatar?ct.avatar:defaultAvatar"/>
	            <h3 class="me-ct-name">{{ct.categoryName}}</h3>
	            <p>{{ct.description}}</p>
	          </template>
	
	          <span class="me-ct-meta">{{ct.articles}} Articles</span>
	        </div>
	
	        <div class="me-ct-articles">
	          <article-scroll-page v-bind:query="article"></article-scroll-page>
	        </div>
	
	      </el-main>
	    </el-container>
	  </div>
</template>

<script>
	import defaultAvatar from '@/assets/img/logo.png'
	import ArticleScrollPage from "@/components/common/ArticleScrollPage.vue"
	import {getTagDetailById} from "@/api/tag.js"
	import {getCategoryDetailById} from "@/api/category.js"
	export default {
		name: "BlogCategoryTag",
		data(){
			return {
				defaultAvatar: defaultAvatar,
				ct : {},
				article : {
					tagId:null,
					categoryId:null
				}
			}
		},
		created(){
			//进行后端请求 请求数据
			this.getCategoryOrTagAndArticles();
		},
		methods:{
			getCategoryOrTagAndArticles(){
				var type = this.$route.params.type;
				var id = this.$route.params.id;
				console.log(type);
				if('tags' == type){
					this.getTagDetailById(id);
					this.article.tagId = id;
					this.article.categoryId = null;
				}
				if('categories' == type){
					this.getCategoryDetailById(id);
					this.article.categoryId = id;
					this.article.tagId = null;
				}
			},
			getTagDetailById(id){
				//发起http请求 请求后端的数据
				getTagDetailById(id).then((res)=>{
					////res.data = Result (success,msg,data)
					if(res.data.success){
						this.ct = res.data.data;
					}else{
						this.$message.error(res.data.msg);
					}
				}).catch((err)=>{
					this.$message.error("系统错误");
				}).finally(()=>{
				})
			},
			getCategoryDetailById(id){
				//发起http请求 请求后端的数据
				getCategoryDetailById(id).then((res)=>{
					////res.data = Result (success,msg,data)
					if(res.data.success){
						this.ct = res.data.data;
					}else{
						this.$message.error(res.data.msg);
					}
				}).catch((err)=>{
					this.$message.error("系统错误");
				}).finally(()=>{
				})
			}
		},
		computed:{
			title(){
				if(this.$route.params.type == 'tag'){
					return "Tag-Zirui"
				}else{
					return "Article Category-Zirui"
				}
			}
		},
		components:{
			"article-scroll-page": ArticleScrollPage
		}
	}
</script>

<style>
  .me-ct-body {
    margin: 60px auto 140px;
    min-width: 100%;
  }

  .el-main {
    padding: 0;
  }

  .me-ct-title {
    text-align: center;
    height: 150px;
    padding: 20px;
  }

  .me-ct-picture {
    width: 60px;
    height: 60px;
  }

  .me-ct-name {
    font-size: 28px;
  }

  .me-ct-meta {
    font-size: 12px;
    color: #969696;
  }

  .me-ct-articles {
    width: 640px;
    margin: 30px auto;
  }

</style>