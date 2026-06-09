# ☕✨ Kyky~ Maid Coffee 店务管理系统

> 一只可爱的女仆咖啡厅店务管理系统 — 桌位、菜单、排班、会员，一站式魔法管理 🎀🐾

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Vue](https://img.shields.io/badge/Vue-3.5-4FC08D.svg)](https://vuejs.org/)
[![Vite](https://img.shields.io/badge/Vite-8.0-646CFF.svg)](https://vitejs.dev/)
[![Element Plus](https://img.shields.io/badge/Element%20Plus-2.14-409EFF.svg)](https://element-plus.org/)

---

## 📖 目录

- [项目简介](#-项目简介)
- [技术栈](#-技术栈)
- [功能概览](#-功能概览)
- [项目结构](#-项目结构)
- [快速开始](#-快速开始)
- [API 接口](#-api-接口)

---

## 🎀 项目简介

**Maid Coffee 店务管理系统**是一套为女仆主题咖啡厅量身打造的管理后台。主人（店长/管理员）可以通过它轻松打理咖啡厅的日常运营——从桌位状态、魔法菜单到女仆排班和会员档案，一站式搞定。

### ✨ 亮点

- 💖 **萌系 UI** — 粉嫩配色 + 猫爪交互 + 浮动粒子特效，沉浸式女仆咖啡厅体验
- 🪄 **登录彩蛋** — 登录按钮会在前两次点击时调皮地瞬移逃跑，第三次才能抓住它完成登录
- 🔐 **路由守卫** — 基于 sessionStorage token 的前端鉴权，未登录自动拦截
- 🎨 **卡片化设计** — 桌位状态卡片、魔法菜单卡片、3D 女仆照片墙、VIP 铭牌卡片，每块业务都有独立的视觉语言
- 📸 **图片上传** — 女仆排班模块支持上传女仆真实照片（后端本地存储）

---

## 🛠 技术栈

### 前端

| 技术 | 版本 | 说明 |
|------|------|------|
| Vue | 3.5 | 组合式 API + `<script setup>` |
| Vite | 8.0 | 开发服务器 + 构建工具 |
| Element Plus | 2.14 | UI 组件库（el-table、el-form、el-dialog 等） |
| Vue Router | 4.6 | 前端路由 + beforeEach 导航守卫 |
| Axios | 1.16 | HTTP 请求，封装为全局 `this.$http` |
| vue-cropper | 1.1 | 图片裁剪组件（女仆照片上传） |

### 后端

| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 3.4.0 | Java 企业级微服务框架 |
| MyBatis-Plus | 3.5.5 | 增强 ORM，Lambda QueryWrapper 简化 CRUD |
| Druid | 1.2.14 | 阿里巴巴开源数据库连接池 |
| MySQL | 8.0 | 关系型数据库 |
| Lombok | 1.18 | 简化 Java Bean 代码 |
| Maven | — | 项目构建与依赖管理 |

### 数据库

- 数据库名：`shop`
- 字符集：`utf8` / `utf8mb4`
- 下划线自动转驼峰（`map-underscore-to-camel-case: true`）

---

## 🧩 功能概览

```
🐾 珈琲厅日常
├── 🥞 桌位萌娘状态    查看/管理咖啡厅桌位及对应女仆的侍奉状态
└── 🪄 魔法料理菜单    增删改查魔法菜单（菜品名、分类、价格、魔法咒语）

🔒 后厨与内务
├── 👑 女仆执勤排班    女仆照片上传 + 排班管理（班次、休息日）
└── 💖 主人会员档案    管理 VIP 主人的会员等级、好感度与档案信息

🔐 门禁系统
├── 🚪 登录           主人身份验证（含逃跑按钮彩蛋 🐾）
└── 📝 注册           新主人注册入驻
```

### 各模块详情

**桌位萌娘状态** — 以卡片网格展示每个桌位，卡片上清晰标注桌号与当前状态（空闲 / 侍奉中），侍奉中的桌位有高亮动效。

**魔法料理菜单** — 以卡片网格展示菜品，每张卡片包含分类飘带、菜名、价格和一句魔法咒语描述。售罄菜品有特殊的置灰样式。

**女仆执勤排班** — 3D 拟物卡片 + 照片展示框，支持上传/裁剪女仆照片，标注班次时间与休息日状态。

**主人会员档案** — VIP 铭牌风格卡片，支持按会员等级（金银铜等）显示不同配色，登记好感度与来访信息。

---

## 📁 项目结构

```
maid_coffee/
│
├── maid_backend/                          # ☕ Spring Boot 后端
│   ├── pom.xml                            #   Maven 依赖配置
│   └── src/main/
│       ├── resources/
│       │   └── application.yml            #   数据库连接、端口、MyBatis-Plus 配置
│       └── java/com/hwadee/cdutproject/
│           ├── CdutProjectApplication.java #   Spring Boot 启动类
│           ├── config/
│           │   └── WebMvcConfig.java       #   CORS 跨域配置
│           ├── controller/                 #   控制器层
│           │   ├── UsersController.java    #     用户登录 / 注册
│           │   ├── TableController.java    #     桌位 CRUD
│           │   ├── MenuController.java     #     菜单 CRUD
│           │   ├── ScheduleController.java #     排班 CRUD
│           │   └── VipController.java      #     会员 CRUD
│           ├── entity/                     #   实体类
│           │   ├── Users.java              #     用户
│           │   ├── CoffeeTable.java        #     桌位
│           │   ├── MagicMenu.java          #     菜品
│           │   ├── MaidSchedule.java       #     排班
│           │   └── MasterVip.java          #     会员
│           ├── mapper/                     #   MyBatis-Plus Mapper 接口
│           └── service/                    #   业务逻辑层
│               ├── UsersService.java
│               └── impl/
│                   └── UsersServiceImpl.java
│
├── maid_frontend/                         # 🎀 Vue 3 前端
│   ├── package.json                       #   工作区依赖（axios、element-plus、vue-router）
│   └── coffee-ui/                         #   前端主项目
│       ├── vite.config.js                 #     Vite 配置（端口 5174 + /api 代理到 :8081）
│       ├── index.html                     #     入口 HTML
│       └── src/
│           ├── main.js                    #     应用入口（挂载 Element Plus、全局 $http）
│           ├── style.css                  #     全局样式
│           ├── App.vue                    #     根组件
│           ├── router.js                  #     路由配置 + beforeEach 导航守卫
│           ├── assets/                    #     静态资源（背景图、装饰图）
│           ├── components/                #     公共组件
│           └── pages/                     #     页面组件
│               ├── Login.vue              #       登录页（毛玻璃面板 + 逃跑按钮）
│               ├── RegisterPage.vue       #       注册页
│               ├── MyTempPage.vue         #       登录后过渡页
│               ├── MainPage.vue           #       主布局（侧边栏 + 顶栏 + 内容区）
│               └── subpages/              #       核心业务子页面
│                   ├── TableManage.vue    #         桌位萌娘状态
│                   ├── MenuManage.vue     #         魔法料理菜单
│                   ├── MaidScheduleManage.vue  #    女仆执勤排班
│                   └── MasterVipManage.vue     #    主人会员档案
│
└── README.md                              # 📄 本文件
```

---

## 🚀 快速开始

### 环境要求

| 工具 | 版本要求 |
|------|----------|
| JDK | 17+ |
| Node.js | 18+ |
| MySQL | 8.0+ |
| Maven | 3.6+ |

### 1. 克隆项目

```bash
git clone <your-repo-url>
cd maid_coffee
```

### 2. 准备数据库

在 MySQL 中创建数据库：

```sql
CREATE DATABASE IF NOT EXISTS shop
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;
```

然后修改 `maid_backend/src/main/resources/application.yml` 中的数据库连接信息：

```yaml
spring:
  datasource:
    username: <你的数据库用户名>
    password: <你的数据库密码>
    url: jdbc:mysql://localhost:3306/shop?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
```

> MyBatis-Plus 会在启动时自动根据实体类建表，无需手动执行 SQL。

### 3. 启动后端

```bash
cd maid_backend

# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

后端启动后运行在 **http://localhost:8081**

### 4. 启动前端

```bash
cd maid_frontend/coffee-ui

npm install
npm run dev
```

前端启动后运行在 **http://localhost:5174**

> Vite 已配置代理：所有 `/api` 前缀的请求自动转发到后端 `http://localhost:8081`，无需手动处理跨域。

### 5. 打开浏览器

访问 **http://localhost:5174** 🎉

首次使用请先注册账号，然后登录进入主页面。

---

## 📡 API 接口

> 注意：前端通过 Vite 代理将 `/api/*` 转发到后端，路径中的 `/api` 前缀在转发时会被去掉。

### 用户

| 方法 | 路径 | 说明 |
|------|------|------|
| `POST` | `/api/login` | 用户登录 |
| `POST` | `/api/register` | 用户注册 |
| `GET` | `/api/demo` | 测试：查询全部用户 |

### 桌位管理

| 方法 | 路径 | 说明 |
|------|------|------|
| `GET` | `/api/tables` | 获取桌位列表 |
| `POST` | `/api/tables` | 新增桌位 |
| `PUT` | `/api/tables/{id}` | 更新桌位 |
| `DELETE` | `/api/tables/{id}` | 删除桌位 |

### 菜单管理

| 方法 | 路径 | 说明 |
|------|------|------|
| `GET` | `/api/menus` | 获取菜单列表 |
| `POST` | `/api/menus` | 新增菜品 |
| `PUT` | `/api/menus/{id}` | 更新菜品 |
| `DELETE` | `/api/menus/{id}` | 删除菜品 |

### 排班管理

| 方法 | 路径 | 说明 |
|------|------|------|
| `GET` | `/api/schedules` | 获取排班列表 |
| `POST` | `/api/schedules` | 新增排班 |
| `PUT` | `/api/schedules/{id}` | 更新排班 |
| `DELETE` | `/api/schedules/{id}` | 删除排班 |

### 会员管理

| 方法 | 路径 | 说明 |
|------|------|------|
| `GET` | `/api/vips` | 获取会员列表 |
| `POST` | `/api/vips` | 新增会员 |
| `PUT` | `/api/vips/{id}` | 更新会员 |
| `DELETE` | `/api/vips/{id}` | 删除会员 |

---

## 📄 License

MIT

---

<p align="center">
  <sub>Made with 💖🍰🐾 by Kyky Maid Room</sub>
</p>
