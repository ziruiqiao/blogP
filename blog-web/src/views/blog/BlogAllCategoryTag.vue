<template>
	<div class="me-allct-body" v-title :data-title="categoryTagTitle" >
	    <el-container class="me-allct-container">
	      <el-main>
	        <el-tabs v-model="activeN">
	          <el-tab-pane label="Article Categories" name="categories">
	            <ul class="me-allct-items">
	              <li v-for="c in categorys" @click="view(c.id)" :key="c.id" 
                        class="me-allct-item">
	                <div class="me-allct-content">
	                  <a class="me-allct-info">
	                    <img class="me-allct-img" 
                            :src="c.avatar?c.avatar:defaultAvatar"/>
	                    <h4 class="me-allct-name">{{c.categoryName}}</h4>
	                    <p class="me-allct-description">{{c.description}}</p>
	                  </a>
	
	                  <div class="me-allct-meta">
	                    <span>{{c.articles}} Articles</span>
	                  </div>
	                </div>
	              </li>
				  <li class="me-allct-item">
					<div class="me-allct-content">
						<a class="me-allct-info">
							<el-icon :size="100" class="me-allct-icon1">
								<CirclePlus/>
							</el-icon>
	                  	</a>
						<div class="me-allct-meta">
	                    	<span>Add</span>
	                  	</div>
					</div>
				  </li>
	            </ul>
	          </el-tab-pane>
	          <el-tab-pane label="Tags" name="tags">
	            <ul class="me-allct-items">
	              <li v-for="t in tags" @click="view(t.id)" :key="t.id" 
                        class="me-allct-item">
	                <div class="me-allct-content">
	                  <a class="me-allct-info">
	                    <img class="me-allct-img" 
                            :src="t.avatar?t.avatar:defaultAvatar"/>
	                    <h4 class="me-allct-name">{{t.tagName}}</h4>
	                  </a>
	
	                  <div class="me-allct-meta">
	                    <span>{{t.articles}}  Articles</span>
	                  </div>
	                </div>
	              </li>
				  <li class="me-allct-item">
					<div class="me-allct-content">
						<a class="me-allct-info">
							<el-icon :size="100" class="me-allct-icon2">
								<CirclePlus/>
							</el-icon>
	                  	</a>
						<div class="me-allct-meta">
	                    	<span>Add</span>
	                  	</div>
					</div>
				  </li>
	            </ul>
	          </el-tab-pane>
	        </el-tabs>
	      </el-main>
	    </el-container>
	  </div>
</template>

<script>
	import defaultAvatar from '@/assets/img/logo.png'
	import {listTagsDetail} from "@/api/tag.js"
	import {listCategorysDetail} from "@/api/category.js"
	export default {
		name: "BlogAllCategoryTag",
		data(){
			return {
				categorys: [],
				tags: [],
				defaultAvatar : defaultAvatar,
			}
		},
		computed:{
			activeN: {
				get(){
					return this.$route.params.type;
				},
				set(newValue) {
					if(newValue == "tags") {
						this.$router.push({name:'type',params:{type:'tags'}});
					} else {
						this.$router.push({name:'type',params:{type:'categories'}});
					}
				}
			},
			categoryTagTitle() {
				if(this.activeN == 'categories'){
					return "Article Categories - ZIRUI"
				}else{
					return "Tags - ZIRUI"
				}
			}
		},
		created(){
			this.listCategorys();
			this.listTags();
		},
		methods:{
			//获取全部的分类列表
			listCategorys(){
				//发起http请求 请求后端的数据
				listCategorysDetail().then((res)=>{
					////res.data = Result (success,msg,data)
					if(res.data.success){
						this.categorys = res.data.data;
					}else{  
						this.$message.error(res.data.msg);
					}
				}).catch((err)=>{
					this.$message.error("系统错误");
				}).finally(()=>{
				})
			},
			//获取全部的标签列表
			listTags(){
				//发起http请求 请求后端的数据
				listTagsDetail().then((res)=>{
					////res.data = Result (success,msg,data)
					if(res.data.success){
						this.tags = res.data.data;
					}else{
						this.$message.error(res.data.msg);
					}
				}).catch((err)=>{
					this.$message.error("系统错误");
				}).finally(()=>{
				})
			},
			view(id) {
				this.$router.push({
					name:'typeId',
					params:{type:`${this.activeN}`,id:id}
				});
			}
		}
	}
</script>

<style>
  .me-allct-body {
    margin: 60px auto 140px;
  }

  .me-allct-container {
    width: 1000px;
  }

  .me-allct-items {
    padding-top: 2rem;
  }

  .me-allct-item {
    width: 25%;
    display: inline-block;
    margin-bottom: 2.4rem;
    padding: 0 .7rem;
    box-sizing: border-box;
  }

  .me-allct-content {
    display: inline-block;
    width: 100%;
    background-color: #fff;
    border: 1px solid #f1f1f1;
    transition: border-color .3s;
    text-align: center;
    padding: 1.5rem 0;
  }

  .me-allct-info {
    cursor: pointer;
  }

  .me-allct-img {
    margin: 0 0 10px;
    width: 60px;
    height: 60px;
    vertical-align: middle;

  }

  .me-allct-name {
    font-size: 21px;
    font-weight: 150;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    margin-top: 4px;
  }

  .me-allct-description {
    min-height: 50px;
    font-size: 13px;
    line-height: 25px;
  }

  .me-allct-meta {
    font-size: 12px;
    color: #969696;
  }
  .me-allct-icon1 {
	margin: 27.5px 0
  }
</style>