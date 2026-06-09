import { createRouter, createWebHistory } from "vue-router";
import Login from "./pages/Login.vue";
import MyTempPage from "./pages/MyTempPage.vue";
import MainPage from "./pages/MainPage.vue";
import RegisterPage from "./pages/RegisterPage.vue";

// 🐾 引入我们在 subpages 下新建的 4 个萌萌哒核心业务页面
import TableManage from "./pages/subpages/TableManage.vue";
import MenuManage from "./pages/subpages/MenuManage.vue";
import MaidScheduleManage from "./pages/subpages/MaidScheduleManage.vue";
import MasterVipManage from "./pages/subpages/MasterVipManage.vue";

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/register",
    name: "Register",
    component: RegisterPage,
  },
  {
    path: "/mytemp",
    name: "MyTempPage",
    component: MyTempPage,
  },
  {
    path: "/mainpage",
    name: "MainPage",
    component: MainPage,
    children: [
      {
        path: "",
        name: "DefaultMain",
        component: TableManage, // 默认展示桌位状态
      },
      {
        path: "tables",
        name: "TableManage",
        component: TableManage, // 🐾 桌位与萌娘状态
      },
      {
        path: "menu",
        name: "MenuManage",
        component: MenuManage, // 🪄 魔法料理菜单已经正式营业啦！
      },
      {
        path: "schedule",
        name: "MaidScheduleManage",
        component: MaidScheduleManage, // 👑 女仆执勤排班
      },
      {
        path: "vips",
        name: "MasterVipManage",
        component: MasterVipManage, // 💖 主人会员档案
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

// 🔴 全天候硬核保安守卫
router.beforeEach((to, from, next) => {
  // 盘查会话内存中的临时通行证
  const token = sessionStorage.getItem("userToken");

  console.log("【硬核保安系统】当前盘查通行证:", token);
  console.log("【硬核保安 token】对方企图硬闯的目标页面:", to.path);

  // 如果企图跳过登录，直接进入 /mainpage（包含其所有子路由）或者 /mytemp
  if (to.path.startsWith("/mainpage") || to.path === "/mytemp") {
    if (token === "MaidCoffeeToken123") {
      // 检查通过，安全放行！
      next();
    } else {
      // 查无此证，当场抓获！
      alert("🚨 保安无情拦截：主人，检测到有人企图绕过门禁，请先登录！🐾");
      next("/"); // 强制遣送回登录页
    }
  } else {
    // 登录页和注册页不需要通行证，直接放行
    next();
  }
});

export default router;
