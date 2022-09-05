<template>
  <el-card>
    <h1 class="me-author-name">zirui-blog</h1>
    <div class="me-author-description">
      <span>
        <el-icon color="#409EFC"><Location /></el-icon>&nbsp;Canada Ottawa
      </span><br>
      <span>
        <el-icon color="#409EFC"><Orange /></el-icon>&nbsp;java development
      </span>
    </div>
    <div class="me-author-tool">
      <i @click="showTool(wx)" :title="wx.title" class="iconfont icon-weixin"></i>
      <i @click="showTool(linkedin)" :title="linkedin.title" class="iconfont icon-linkedin"></i>
    </div>
  </el-card>

</template>

<script>
  export default {
    name: 'CardMe',
    data() {
      return {
        wx: {title: 'WeiChat', message: 'Qzr6132995878'},
        linkedin: {
          title: 'LinkedIn',
          message: 'linkedIn link'
          //'<a target="_blank" href="https://space.bilibili.com/473844125">https://space.bilibili.com/473844125</a>'
        }
      }
    },
    methods: {
      showTool(tool) {
        this.$message({
          duration: 0,
          showClose: true,
          dangerouslyUseHTMLString: true,
          message: '<strong>' + tool.message + '</strong>'
        });
      }
    }
  }
</script>

<style scoped>
  @import url('@/assets/css/iconfont.css');
  .me-author-name {
    text-align: center;
    font-size: 30px;
    border-bottom: 1px solid #00aaff;
  }

  .me-author-description {
    padding: 8px 0;
  }

  .me-icon-job {
    padding-left: 16px;
  }

  .me-author-tool {
    text-align: center;
    padding-top: 10px;
  }

  .me-author-tool i {
    cursor: pointer;
    padding: 4px 10px;
    font-size: 30px;
  }
</style>