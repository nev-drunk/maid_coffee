<template>
  <div class="common-layout layout-admin">
    <div class="common-layout">
      <el-container class="system-container">
        <el-header class="system-header">
          <div class="system-title">
            <span class="logo-icon">☕✨</span>
            <span class="title-txt">Kyky~ Maid Coffee 店务管理系统</span>
          </div>

          <div class="user-profile">
            <el-dropdown trigger="click" @command="handleCommand">
              <span class="el-dropdown-link">
                <span class="status-dot"></span>
                主人，欢迎回来 🎀
                <el-icon class="el-icon--right"><arrow-down /></el-icon>
              </span>
              <template #dropdown>
                <el-dropdown-menu class="maid-dropdown-menu">
                  <el-dropdown-item :icon="User" command="toLogin"
                    >前往登录页</el-dropdown-item
                  >
                  <el-dropdown-item
                    :icon="SwitchButton"
                    divided
                    command="logout"
                    >退出登录</el-dropdown-item
                  >
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </el-header>

        <el-container class="sub-container">
          <el-aside width="260px" class="system-aside">
            <div class="menu-wrapper">
              <div class="menu-section-title">🐾 珈琲厅日常</div>
              <div class="custom-menu-list">
                <div
                  class="luxury-menu-item"
                  :class="{ 'is-active': $route.path === '/mainpage/tables' }"
                  @click="navigateTo('/mainpage/tables')"
                >
                  <div class="item-bg-glow"></div>
                  <span class="item-icon">🥞</span>
                  <div class="item-text">
                    <span class="cn">桌位萌娘状态</span>
                    <span class="en">TABLE STATUS</span>
                  </div>
                  <div class="active-paw">🐾</div>
                </div>

                <div
                  class="luxury-menu-item"
                  :class="{ 'is-active': $route.path === '/mainpage/menu' }"
                  @click="navigateTo('/mainpage/menu')"
                >
                  <div class="item-bg-glow"></div>
                  <span class="item-icon">🪄</span>
                  <div class="item-text">
                    <span class="cn">魔法料理菜单</span>
                    <span class="en">MAGIC MENU</span>
                  </div>
                  <div class="active-paw">🐾</div>
                </div>
              </div>

              <div class="menu-section-title" style="margin-top: 25px">
                🔒 后厨与内务
              </div>
              <div class="custom-menu-list">
                <div
                  class="luxury-menu-item"
                  :class="{ 'is-active': $route.path === '/mainpage/schedule' }"
                  @click="navigateTo('/mainpage/schedule')"
                >
                  <div class="item-bg-glow"></div>
                  <span class="item-icon">👑</span>
                  <div class="item-text">
                    <span class="cn">女仆执勤排班</span>
                    <span class="en">MAID SHIFTS</span>
                  </div>
                  <div class="active-paw">🐾</div>
                </div>

                <div
                  class="luxury-menu-item"
                  :class="{ 'is-active': $route.path === '/mainpage/vips' }"
                  @click="navigateTo('/mainpage/vips')"
                >
                  <div class="item-bg-glow"></div>
                  <span class="item-icon">💖</span>
                  <div class="item-text">
                    <span class="cn">主人会员档案</span>
                    <span class="en">VIP MASTERS</span>
                  </div>
                  <div class="active-paw">🐾</div>
                </div>
              </div>
            </div>

            <div class="aside-footer-brand">🎀 Kyky Maid Room v3.0</div>
          </el-aside>

          <el-main class="system-main">
            <div class="magic-particles">
              <span class="particle p1">❤️</span>
              <span class="particle p2">✨</span>
              <span class="particle p3">🌸</span>
              <span class="particle p4">🐾</span>
              <span class="particle p5">✨</span>
            </div>

            <div class="content-card">
              <div class="card-inner-lace">
                <router-view />
              </div>
            </div>
          </el-main>
        </el-container>
      </el-container>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ElMessage } from "element-plus";
import { useRouter, useRoute } from "vue-router";
import { ArrowDown, User, SwitchButton } from "@element-plus/icons-vue";

const router = useRouter();
const route = useRoute();

// 纯手工路由跳转，摆脱 el-menu 的束缚
const navigateTo = (path: string) => {
  if (route.path !== path) {
    router.push(path);
  }
};

const handleCommand = (command: string) => {
  if (command === "logout" || command === "toLogin") {
    ElMessage({
      message: "期待您的下次光临，主人~ 🐾",
      type: "success",
      duration: 2000,
    });
    sessionStorage.removeItem("userToken");
    router.push("/");
  }
};
</script>

<style scoped>
.system-container {
  height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #fff0f3;
  font-family: "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", sans-serif;
  overflow: hidden;
}
.sub-container {
  flex: 1;
  overflow: hidden;
}

.system-header {
  background: linear-gradient(to bottom, #ffffff 0%, #fff6f8 100%);
  border-bottom: 4px dashed #ffb6c1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  height: 65px !important;
  box-shadow: 0 4px 15px rgba(255, 182, 193, 0.25);
  z-index: 10;
}
.title-txt {
  font-size: 19px;
  font-weight: 900;
  color: #ff6b8b;
  letter-spacing: 0.5px;
}
.logo-icon {
  font-size: 22px;
  animation: float 3s infinite ease-in-out;
}

.el-dropdown-link {
  cursor: pointer;
  color: #ff6b8b;
  display: flex;
  align-items: center;
  font-weight: bold;
  padding: 8px 16px;
  border-radius: 25px;
  background-color: #ffffff;
  border: 2px solid #ffcad4;
  box-shadow: 0 4px 10px rgba(255, 182, 193, 0.15);
  transition: all 0.2s ease;
}
.status-dot {
  width: 7px;
  height: 7px;
  background-color: #66ccff;
  border-radius: 50%;
  margin-right: 8px;
  box-shadow: 0 0 6px #66ccff;
}

.layout-admin {
  height: 100%;
  overflow: hidden;
}

.system-aside {
  background: linear-gradient(180deg, #3a2e2b 0%, #2b211f 100%);
  box-shadow: 4px 0 15px rgba(43, 33, 31, 0.3);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  z-index: 5;
  padding-top: 20px;
}
.menu-wrapper {
  padding: 0 12px;
}

/* 菜单小分组精致抬头 */
.menu-section-title {
  font-size: 11px;
  color: #8c736c;
  font-weight: bold;
  letter-spacing: 1px;
  padding-left: 12px;
  margin-bottom: 10px;
  text-transform: uppercase;
}

.custom-menu-list {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

/* 💎 独立游戏级拟物菜单项 */
.luxury-menu-item {
  display: flex;
  align-items: center;
  padding: 12px 16px;
  border-radius: 16px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  border: 1px solid transparent;
  transition:
    all 0.4s cubic-bezier(0.25, 0.8, 0.25, 1),
    padding-left 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
}

/* 拟物化发光内衬 */
.item-bg-glow {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(
    90deg,
    rgba(255, 107, 139, 0) 0%,
    rgba(255, 107, 139, 0.15) 100%
  );
  opacity: 0;
  transition: opacity 0.3s;
  z-index: 1;
}

.item-icon {
  font-size: 18px;
  margin-right: 14px;
  z-index: 2;
  transition: transform 0.3s;
}

.item-text {
  display: flex;
  flex-direction: column;
  z-index: 2;
}
.item-text .cn {
  font-size: 13.5px;
  color: #d5bdaf;
  font-weight: bold;
  transition: color 0.3s;
}
.item-text .en {
  font-size: 9px;
  color: #6e5954;
  font-family: sans-serif;
  font-weight: bold;
  margin-top: 2px;
  transition: color 0.3s;
}

/* 隐藏的猫爪，激活时探头 */
.active-paw {
  position: absolute;
  right: -20px;
  font-size: 16px;
  opacity: 0;
  transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
  z-index: 2;
}

/* 🌟 悬停效果：微微亮起 */
.luxury-menu-item:hover {
  background-color: rgba(255, 255, 255, 0.03);
}
.luxury-menu-item:hover .item-icon {
  transform: scale(1.15) rotate(5deg);
}
.luxury-menu-item:hover .item-text .cn {
  color: #fff;
}

/*3D横向飘出书签视效 */
.luxury-menu-item.is-active {
  background: linear-gradient(90deg, #ff6b8b 0%, #ff85a1 100%) !important;
  border: 1px solid #ff99cc;
  padding-left: 22px; /* ← 用 padding 代替 translateX */
  will-change: padding-left;
  box-shadow: 0 4px 15px rgba(255, 107, 139, 0.4);
}

.luxury-menu-item.is-active .item-bg-glow {
  opacity: 1;
}
.luxury-menu-item.is-active .item-icon {
  transform: scale(1.2) rotate(-5deg);
}
.luxury-menu-item.is-active .item-text .cn {
  color: #ffffff !important;
}
.luxury-menu-item.is-active .item-text .en {
  color: #ffe3e8 !important;
}
.luxury-menu-item.is-active .active-paw {
  right: 14px;
  opacity: 1;
}

.aside-footer-brand {
  text-align: center;
  padding: 15px;
  font-size: 11px;
  color: #5c4a45;
  font-family: monospace;
}

.system-main {
  background-color: #fff0f3;
  padding: 24px;
  position: relative;
  overflow-y: auto;
  min-height: 100%; /* ← 加这一行 */
  scrollbar-gutter: stable;
}

.system-main::after {
  content: "";
  display: block;
  height: 1px; /* 只要极其微小的高度就能防止底部回弹露馅 */
}

/* 缓缓上升的背景魔法悬浮粒子 */
.magic-particles {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  pointer-events: none;
  overflow: hidden;
  z-index: 1;
}
.particle {
  position: absolute;
  bottom: -20px;
  font-size: 14px;
  opacity: 0.15;
  animation: floatUp 8s infinite linear;
}
.p1 {
  left: 10%;
  animation-delay: 0s;
  animation-duration: 9s;
}
.p2 {
  left: 35%;
  animation-delay: 2s;
  font-size: 18px;
}
.p3 {
  left: 55%;
  animation-delay: 4s;
  animation-duration: 7s;
}
.p4 {
  left: 80%;
  animation-delay: 1s;
  font-size: 12px;
}
.p5 {
  left: 92%;
  animation-delay: 5s;
  animation-duration: 10s;
}

.content-card {
  background: #ffffff;
  padding: 8px;
  border-radius: 24px;
  box-shadow: 0 12px 40px rgba(255, 133, 161, 0.14);
  min-height: 100%;
  position: relative;
  z-index: 2;
  transition: box-shadow 0.3s;
}
.content-card:hover {
  box-shadow: 0 16px 45px rgba(255, 107, 139, 0.22);
}
.card-inner-lace {
  border: 2px dashed #f5e6e8;
  border-radius: 18px;
  padding: 24px;
  min-height: 100%;
  background: #ffffff;
}

@keyframes float {
  0%,
  100% {
    transform: translateY(0) rotate(0deg);
  }
  50% {
    transform: translateY(-4px) rotate(3deg);
  }
}
@keyframes floatUp {
  0% {
    transform: translateY(0) translateX(0) rotate(0deg);
    opacity: 0;
  }
  10% {
    opacity: 0.25;
  }
  90% {
    opacity: 0.15;
  }
  100% {
    transform: translateY(-100vh) translateX(30px) rotate(360deg);
    opacity: 0;
  }
}
</style>
