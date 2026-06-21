import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      // 将 @ 映射到 src 目录，解决 @/api 找不到文件的报错
      '@': path.resolve(__dirname, './src')
    }
  }
})