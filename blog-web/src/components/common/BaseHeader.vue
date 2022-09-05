<template>
  <el-header class="me-area">
    <el-row class="me-header">

      <el-col :span="4" class="me-header-left">
        <p style="text-align: center;">
        <router-link to="/" class="me-title">
          <img :src=src_img />
        </router-link>
        </p>
      </el-col>

      <el-col v-if="!simple" :span="16">
        <el-menu :router=true menu-trigger="click" active-text-color="#00aaff" 
        :default-active="activeIndex"
          mode="horizontal">
          <el-menu-item index="/">Main</el-menu-item>
          <el-menu-item index="type/categories" @click="jump('type', 'categories')">Categories</el-menu-item>
          <el-menu-item index="type/tags" @click="jump('type', 'tags')">Tags</el-menu-item>
          <el-menu-item index="/archives">Archives</el-menu-item>

          <el-col :span="4" :offset="4">
            <el-menu-item index="/write"><i class="el-icon-edit"></i>Write Article</el-menu-item>
          </el-col>

        </el-menu>
      </el-col>

      <template v-else>
        <slot></slot>
      </template>

      <el-col :span="4">
        <el-menu :router=true 
                  menu-trigger="click" 
                  mode="horizontal" 
                  active-text-color="#00aaff"
                  style="height: 100%">

          <template v-if="!user.login">
            <el-menu-item index="/login">
              <el-button link>Login</el-button>
            </el-menu-item>
            <el-menu-item index="/register">
              <el-button link>Register</el-button>
            </el-menu-item>
          </template>
          <template v-else>
            <el-sub-menu index>
              <template v-slot:title>
                <img class="me-header-picture" :src="user.avatar" />
              </template>
              <el-menu-item index @click="logout"><i class="el-icon-back"></i>Logout</el-menu-item>
            </el-sub-menu>
          </template>
        </el-menu>
      </el-col>

    </el-row>
  </el-header>
</template>

<script>
export default {
  name: 'BaseHeader',
  props: {
    activeIndex: String,
    simple: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      user: {
        login: false,
        avatar: ""
      },
      src_img: '../../src/assets/img/logo.png'
    }
  },
  computed: {
  },
  methods: {
    jump(n, s){
      this.$router.push({name:n, params:{type:s}});
    }
  }
}
</script>

<style>
.el-button {
  color: #00aaff;
}

.el-header {
  position: fixed;
  z-index: 1024;
  min-width: 100%;
  box-shadow: 0 2px 3px hsla(0, 0%, 7%, .1), 0 0 0 1px hsla(0, 0%, 7%, .1);
}

.me-title {
  margin-top: 10px;
  font-size: 24px;
}

.me-header-left {
  margin-top: 10px;
}

.me-title img {
  max-height: 2.4rem;
  max-width: 100%;
}

.me-header-picture {
  width: 36px;
  height: 36px;
  border: 1px solid #ddd;
  border-radius: 50%;
  vertical-align: middle;
  background-color: #00aaff;
}
</style>
