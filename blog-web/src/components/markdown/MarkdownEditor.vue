<template>
    <v-md-editor v-model="text" height="400px"></v-md-editor>
</template>

<script>
// import { upload } from "@/api/upload";
export default {
  name: "MarkdownEditor",
  props: {
    editor: Object,
  },
  data() {
    return {
      text: 'test',
    };
  },
  mounted() {
  },
  methods: {
    // imgAdd(pos, $file) {
    //   let that = this;
    //   let formdata = new FormData();
    //   formdata.append("image", $file);

    //   upload(formdata)
    //     .then((data) => {
    //       // 第二步.将返回的url替换到文本原位置![...](./0) -> ![...](url)
    //       if (data.success) {
    //         that.$refs.md.$img2Url(pos, data.data);
    //       } else {
    //         that.$message({
    //           message: data.msg,
    //           type: "error",
    //           showClose: true,
    //         });
    //       }
    //     })
    //     .catch((err) => {
    //       that.$message({ message: err, type: "error", showClose: true });
    //     });
    // },
  },
};
</script>
<style scoped>
.me-editor {
  z-index: 6 !important;
}

.v-note-wrapper.fullscreen {
  top: 60px !important;
}
</style>
