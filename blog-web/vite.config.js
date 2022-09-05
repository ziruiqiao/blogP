import {
	defineConfig
} from 'vite'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import {
	ElementPlusResolver
} from 'unplugin-vue-components/resolvers'
import vue from '@vitejs/plugin-vue'
import {
	loadEnv
} from 'vite';
import {
	resolve
} from 'path'; // 注意 yarn add @types/node -D
import alias from '@rollup/plugin-alias'; // 注意，如果不加这个的话，alias不生效

// https://vitejs.dev/config/
export default defineConfig({
	base: './',
	/* 这个就是webpack里面的publicPath */
	plugins: [
		alias(), // 注意这里
		vue(),
		AutoImport({
			resolvers: [ElementPlusResolver()],
		}),
		Components({
			resolvers: [ElementPlusResolver()],
		}),
	],
	resolve: {
		/* 设置路径别名 */
		alias: {
			'@': resolve(__dirname, 'src'),
			'/images': 'src/assets/images'
		},
		// extensions: ['.mjs', '.js', '.ts', '.jsx', '.tsx', '.json'] /* 默认这些，不建议.vue */
	}

})
