<template>
  <div class="me-view-body" v-title :data-title="title">
    <el-container class="me-view-container">
      <!--<el-aside class="me-area">-->
        <!--<ul class="me-operation-list">-->
          <!--<li class="me-operation-item">-->
            <!--<el-button type="primary" icon="el-icon-edit"></el-button>-->
          <!--</li>-->
        <!--</ul>-->
      <!--</el-aside>-->
      <el-main>

        <div class="me-view-card">
          <h1 class="me-view-title">{{article.title}}</h1>
          <div class="me-view-author">
            <a class="">
              <img class="me-view-picture" :src="article.author.avatar"/>
            </a>
            <div class="me-view-info">
              <span>{{article.author}}</span>
              <div class="me-view-meta">
                <span>{{createDate}}</span>
                <span>views   {{article.viewCounts}}</span>
                <span>comments   {{article.commentCounts}}</span>
              </div>

            </div>
            <el-button
              v-if="this.article.author.id == this.$store.state.id"
              @click="editArticle()"
              style="position: absolute;left: 60%;"
              size="small"
              round
              icon="el-icon-edit">edit</el-button>
          </div>
          <div class="me-view-content">
            <markdown-editor :editor=article.editor></markdown-editor>
          </div>

          <div class="me-view-end">
            <el-alert
              title="Article End..."
              type="success"
              center
              :closable="false">
            </el-alert>
          </div>

          <div class="me-view-tag">
            Tags:
            <!--<el-tag v-for="t in article.tags" 
                        :key="t.id" 
                        class="me-view-tag-item" 
                        size="mini" type="success">{{t.tagName}}</el-tag>-->
            <el-button @click="tagOrCategory('tags', t.id)" 
                        size="mini" 
                        type="primary" 
                        v-for="t in article.tags" 
                        :key="t.id" round plain>{{t.tagName}}</el-button>
          </div>

          <div class="me-view-tag">
            Categories:
            <!--<span style="font-weight: 600">
                    {{article.category.categoryName}}
                </span>-->
            <el-button @click="tagOrCategory('categories', article.category.id)" 
                        size="small" 
                        type="primary" 
                        round plain>{{article.category.categoryName}}</el-button>
          </div>

          <div class="me-view-comment">
            <div class="me-view-comment-write">
              <el-row :gutter="20">
                <el-col :span="2">
                  <a class="">
                    <img class="me-view-picture" :src="avatar"/>
                  </a>
                </el-col>
                <el-col :span="22">
                  <el-input
                    type="textarea"
                    :autosize="{ minRows: 2}"
                    placeholder="your comments..."
                    class="me-view-comment-text"
                    v-model="comment.content"
                    resize="none">
                  </el-input>
                </el-col>
              </el-row>

              <el-row :gutter="20">
                <el-col :span="2" :offset="22">
                  <el-button type="text" @click="publishComment()">publish comment</el-button>
                </el-col>
              </el-row>
            </div>

            <div class="me-view-comment-title">
              <span>{{article.commentCounts}} comments</span>
            </div>

            <commment-item
              v-for="(c,index) in comments"
              :comment="c"
              :articleId="article.id"
              :index="index"
              :rootCommentCounts="comments.length"
              @commentCountsIncrement="commentCountsIncrement"
              :key="c.id">
            </commment-item>

          </div>

        </div>
      </el-main>

    </el-container>
  </div>
</template>

<script>
  import MarkdownEditor from '@/components/markdown/MarkdownEditor.vue'
  import CommmentItem from '@/components/comment/CommentItem.vue'
  import {viewArticle} from '@/api/article.js'
  import {getCommentsByArticle, publishComment} from '@/api/comment.js'

  import default_avatar from '@/assets/img/default_avatar.png'

  import { formatTime } from "@/utils/time.js";

  export default {
    name: 'BlogView',
    created() {
      this.getArticle()
    },
    watch: {
      '$route': 'getArticle'
    },
    data() {
      return {
        article: {
          id: '',
          title: '',
          commentCounts: 0,
          viewCounts: 0,
          summary: '',
          author: {},
          tags: [],
          category:{},
          createDate: '',
          editor: {
            value: '',
            toolbarsFlag: false,
            subfield: false,
            defaultOpen: 'preview'
          }
        },
        comments: [],
        comment: {
          article: {},
          content: ''
        }
      }
    },
    computed: {
      avatar() {
        let avatar = this.$store.state.avatar

        if (avatar.length > 0) {
          return avatar
        }
        return default_avatar
      },
      title() {
        return `${this.article.title} - ?????? - ????????????`
      },
      createDate() {
        return formatTime(this.article.createDate);
      }
    },
    methods: {
      tagOrCategory(type, id) {
        this.$router.push({path: `/${type}/${id}`})
      },
      editArticle() {
        this.$router.push({path: `/write/${this.article.id}`})
      },
      getArticle() {
        let that = this
        viewArticle(that.$route.params.id).then(data => {
          Object.assign(that.article, data.data)
          that.article.editor.value = data.data.body.content

          that.getCommentsByArticle()
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '??????????????????', showClose: true})
          }
        })
      },
      publishComment() {
        let that = this
        if (!that.comment.content) {
          return;
        }
        that.comment.article.id = that.article.id
        let parms = {articleId:that.article.id,content:that.comment.content}
        publishComment(parms,this.$store.state.token).then(data => {
          if(data.success){
            that.$message({type: 'success', message: '????????????', showClose: true})
            that.comment.content = ''
            that.comments.unshift(data.data)
            that.commentCountsIncrement()

          }else{
               that.$message({type: 'error', message: data.msg, showClose: true})
          }

        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '????????????', showClose: true})
          }
        })
      },

      getCommentsByArticle() {
        let that = this
        getCommentsByArticle(that.article.id).then(data => {
          if(data.success){
               that.comments = data.data
          }else{
             that.$message({type: 'error', message: '??????????????????', showClose: true})
          }
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '??????????????????', showClose: true})
          }
        })
      },
      commentCountsIncrement() {
        this.article.commentCounts += 1
      }
    },
    components: {
      'markdown-editor': MarkdownEditor,
      CommmentItem
    },
    //?????????????????? ??????body????????????
    beforeRouteEnter(to, from, next) {
      window.document.body.style.backgroundColor = '#fff';
      next();
    },
    beforeRouteLeave(to, from, next) {
      window.document.body.style.backgroundColor = '#f5f5f5';
      next();
    }
  }
</script>

<style>
  .me-view-body {
    margin: 100px auto 140px;
  }

  .me-view-container {
    width: 800px;
  }

  .el-main {
    overflow: hidden;
  }

  .me-view-title {
    font-size: 34px;
    font-weight: 800;
    line-height: 1.3;
  }

  .me-view-author {
    /*margin: 30px 0;*/
    margin-top: 30px;
    vertical-align: middle;
  }

  .me-view-picture {
    width: 40px;
    height: 40px;
    border: 1px solid #ddd;
    border-radius: 50%;
    vertical-align: middle;
    background-color: #5fb878;
  }

  .me-view-info {
    display: inline-block;
    vertical-align: middle;
    margin-left: 8px;
  }

  .me-view-meta {
    font-size: 12px;
    color: #969696;
  }

  .me-view-end {
    margin-top: 20px;
  }

  .me-view-tag {
    margin-top: 20px;
    padding-left: 6px;
    border-left: 4px solid #c5cac3;
  }

  .me-view-tag-item {
    margin: 0 4px;
  }

  .me-view-comment {
    margin-top: 60px;
  }

  .me-view-comment-title {
    font-weight: 600;
    border-bottom: 1px solid #f0f0f0;
    padding-bottom: 20px;
  }

  .me-view-comment-write {
    margin-top: 20px;
  }

  .me-view-comment-text {
    font-size: 16px;
  }

  .v-show-content {
    padding: 8px 25px 15px 30px !important;
  }

  .v-note-wrapper .v-note-panel {
    box-shadow: none !important;
  }

  .v-note-wrapper .v-note-panel .v-note-show 
  .v-show-content, .v-note-wrapper .v-note-panel 
  .v-note-show .v-show-content-html {
    background: #fff !important;
  }


</style>