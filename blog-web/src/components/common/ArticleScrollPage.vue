<template>
  <scroll-page
    :loading="loading"
    :offset="offset"
    :no-data="noData"
    @load="load"
  >
    <article-item
      v-for="article in articles"
      :key="article.id"
      v-bind="article"
    >
    </article-item>
  </scroll-page>
</template>

<script>
import ArticleItem from "@/components/ArticleItem.vue";
import ScrollPage from "@/components/scrollpage/index.vue";
import { getAllArticles } from "@/api/article.js";

export default {
  name: "ArticleScrollPage",
  props: {
    offset: {
      type: Number,
      default: 0,
    },
    query: {
      type:Object,
      default(){
        return{}
      }
    },
  },
  watch: {},
  created() {
    this.getArticles();
  },
  data() {
    return {
      loading: false,
      noData: false,
      //分页信息
      innerPage: {
        pageSize: 10,
        page: 1,
        tagId: null,
        categoryId: null,
      },
      //文章列表
      articles: [],
    };
  },
  methods: {
    load() {
      //下拉触发分页的时候 进行接口加载
      // alert("触发分页");
      this.getArticles();
    },
    getArticles() {
      this.loading = true;
      this.innerPage.tagId = this.query.tagId;
      this.innerPage.categoryId = this.query.categoryId;
      console.log(this.innerPage);

      getAllArticles(this.innerPage)
        .then((res) => {
          if (res.data.success) {
            if (res.data.data.length <= 0) {
              this.noData = true;
            } else {
              this.noData = false;
              this.articles = this.articles.concat(res.data.data);
              this.innerPage.page += 1;
            }
          } else {
            this.$message({
              type: "error",
              message: res.data.message,
              showClose: true,
            });
          }
        })
        .catch((err) => {
          //有报错
          this.$message({
            type: "error",
            message: "文章加载失败!",
            showClose: true,
          });
        })
        .finally(() => {
          this.loading = false;
        });
    },
  },
  components: {
    "article-item": ArticleItem,
    "scroll-page": ScrollPage,
  },
};
</script>

<style scoped>
.el-card {
  border-radius: 0;
}

.el-card:not(:first-child) {
  margin-top: 10px;
}
</style>
