<template>
	<div class="common-layout" data-title="zirui-blog">
		<el-container>
			<el-container>
				<el-main class="me-article">
					<ArticleScrollPage></ArticleScrollPage>
				</el-main>
				<el-aside>
					<CardMe></CardMe>
					<CardTag :tags="hotTags"></CardTag>
					<CardArticle cardHeader="Hot Articles" :articles="hotArticles">
					</CardArticle>
					<CardArchive cardHeader="Archives" :archives="archives">
					</CardArchive>
					<CardArticle cardHeader="New Articles" :articles="newArticles">
					</CardArticle>
				</el-aside>
			</el-container>
		</el-container>
	</div>
</template>

<script>
import ArticleScrollPage from "@/components/common/ArticleScrollPage.vue"
import CardMe from '@/components/card/CardMe.vue'
import CardTag from '@/components/card/CardTag.vue'
import CardArticle from '@/components/card/CardArticle.vue'
import CardArchive from '@/components/card/CardArchive.vue'
import { getHotTags } from "@/api/tag.js";
import { getHotArticles, getNewArticles,getArchives } from "@/api/article.js";

export default {
	data() {
		return {
			hotTags: [],
			hotArticles: [],
			newArticles: [],
			archives: []
		}
	},
	components: {
		ArticleScrollPage,
		CardMe,
		CardTag,
		CardArticle,
		CardArchive
	},
	created() {
		this.getHotTags();
		this.getHotArticles();
		this.getNewArticles();
		this.getArchives();
	},
	methods: {
		getArchives() {
			//发起http请求 请求后端的数据
			getArchives().then((res) => {
				////res.data = Result (success,msg,data)
				if (res.data.success) {
					this.archives = res.data.data;
				} else {
					this.$message.error(res.data.msg);
				}
			}).catch((err) => {
				this.$message.error("系统错误");
			}).finally(() => {
			})
		},
		getHotTags() {
			//发起http请求 请求后端的数据
			getHotTags().then((res) => {
				////res.data = Result (success,msg,data)
				if (res.data.success) {
					this.hotTags = res.data.data;
				} else {
					this.$message.error(res.data.msg);
				}
			}).catch((err) => {
				this.$message.error("系统错误");
			}).finally(() => {
			})
		},
		getHotArticles() {
			//发起http请求 请求后端的数据
			getHotArticles().then((res) => {
				////res.data = Result (success,msg,data)
				if (res.data.success) {
					this.hotArticles = res.data.data;
				} else {
					this.$message.error(res.data.msg);
				}
			}).catch((err) => {
				this.$message.error("系统错误");
			}).finally(() => {
			})
		},
		getNewArticles() {
			//发起http请求 请求后端的数据
			getNewArticles().then((res) => {
				////res.data = Result (success,msg,data)
				if (res.data.success) {
					this.newArticles = res.data.data;
				} else {
					this.$message.error(res.data.msg);
				}
			}).catch((err) => {
				this.$message.error("系统错误");
			}).finally(() => {
			})
		}
	},
}
</script>

<style scoped>
.el-container {
	width: 960px;
}

.el-aside {
	margin-left: 20px;
	width: 260px;
}

.el-main {
	padding: 0px;
	line-height: 16px;
}

.el-card {
	border-radius: 0;
}

.el-card:not(:first-child) {
	margin-top: 20px;
}
</style>
