<script>
	import GoTop from '@/components/GoTop.vue'
	
	export default{
		name: 'app',
		components: {
			GoTop
		}
	}
</script>

<template>
	<div id="app">
		<router-view></router-view>
		<el-backtop :right="100" :bottom="100" />
	</div>
</template>

<style>
@import url('@/assets/css/iconfont.css');
* { margin: 0; padding: 0; }

body {
	background-color: #f5f5f5;
	font-weight: 400;
	font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
	line-height: 1.5;
}
a {
    cursor: pointer;
    text-decoration: none;
	/* 动画效果，css的属性值在一定的时间内从一个状态平滑的过渡到另一个状态 ease-out　先快，后慢 */
    transition: none 86ms ease-out;
}
a:hover {
	color: #00aaff;
}

html{height:100%;}
body{min-height:100%;}
/*生成相对定位的元素，相对于其正常位置进行定位*/
body{position:relative;}
</style>
