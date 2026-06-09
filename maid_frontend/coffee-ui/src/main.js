import { createApp } from "vue";
import App from "./App.vue";
import router from "./router"; // 1. 确保导入了路由

// 导入 Element Plus 及其样式
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
// 导入 Element Plus 所有图标
import * as ElementPlusIconsVue from "@element-plus/icons-vue";

// 导入 Axios 并配置基础路径
import axios from "axios";
axios.defaults.baseURL = "/api";

const app = createApp(App);

// 2. 注册所有 Element Plus 图标组件
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component);
}

// 3. 挂载 Axios 到全局属性 $http
app.config.globalProperties.$http = axios;

// 4. 严格注意：必须注册路由和 ElementPlus 插件
app.use(router);
app.use(ElementPlus);

// 5. 最后挂载到 DOM
app.mount("#app");
