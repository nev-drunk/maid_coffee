import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 5174, // 锁定你的前端端口
    proxy: {
      // 只要前端请求以 /api 开头，Vite 就会自动帮你不留痕迹地转发到实际后端
      "/api": {
        target: "http://localhost:8081", // 你后端的真实基准地址
        changeOrigin: true, // 允许跨域
        rewrite: (path) => path.replace(/^\/api/, ""), // 转发时把路径里的 /api 悄悄去掉
      },
    },
  },
});
