<template>
  <div class="login-page">
    <div class="side-panel left-panel"></div>
    <div class="center-panel">
      <div class="login-panel" :style="panelStyle">
        <h1>maid💕bar秘密入口~</h1>
        <p class="sub-title">「欢迎回屋，请主人进行身份验证 🐾」</p>

        <el-input
          class="input-class"
          placeholder="请输入用户名 ✨"
          v-model="username"
          clearable
        />
        <el-input
          class="input-class"
          placeholder="请输入密码 🔑"
          v-model="password"
          type="password"
          show-password
        />

        <el-button class="login-button" @click="login">
          {{ buttonText }}
        </el-button>

        <div class="switch-action">
          <span @click="goToRegister">还没有账号？点击注册新主人 📝</span>
        </div>

        <p class="hint-text" v-if="clickCount > 0">{{ hintText }}</p>
      </div>
    </div>
    <div class="side-panel right-panel"></div>
  </div>
</template>

<script>
export default {
  name: "LoginPage",
  data() {
    return {
      username: "",
      password: "",
      clickCount: 0,
      offset: {
        top: 0,
        left: 0,
      },
    };
  },
  computed: {
    panelStyle() {
      if (this.clickCount === 0) {
        return {
          position: "relative",
          transition: "all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275)",
        };
      } else {
        return {
          position: "absolute",
          top: this.offset.top + "px",
          left: this.offset.left + "px",
          // 稍微拉长一点点过渡时间，配合更Q弹的贝塞尔曲线，抓猫猫时有果冻质感
          transition: "all 0.4s cubic-bezier(0.175, 0.885, 0.42, 1.35)",
          zIndex: 10,
        };
      }
    },
    buttonText() {
      return "开启魔法大门 🪄";
    },
    hintText() {
      if (this.clickCount === 1) return "💢 哼，您还不是VIP用户！";
      if (this.clickCount === 2) return "🐾 贴贴~ 请联系客服充值后再试！";
      return "";
    },
  },
  methods: {
    login() {
      if (this.username.trim() === "" || this.password.trim() === "") {
        this.$message.error("🍭 哎呀！用户名或密码不能为空哦~");
        return;
      }
      this.clickCount++;
      if (this.clickCount < 3) {
        this.teleport();
      } else {
        this.submitLogin();
      }
    },
    submitLogin() {
      this.$http
        .post("/login", {
          username: this.username,
          password: this.password,
        })
        .then((res) => {
          console.log("后端返回数据：", res.data);

          if (res.data === "登录成功") {
            sessionStorage.setItem("userToken", "MaidCoffeeToken123");
            localStorage.removeItem("userToken");
            this.$message.success("🎉 终于被你抓到啦！欢迎回屋~");
            this.clickCount = 0;
            this.$router.push("/mytemp");
          } else {
            this.$message.error("❌ 校验失败，用户名或密码记错了吧？");
            this.clickCount = 0;
          }
        })
        .catch((err) => {
          console.error(err);
          this.$message.error("📡 呜呜，网络水晶球失联了，请稍后再试");
          this.clickCount = 0;
        });
    },
    teleport() {
      const cardWidth = 360;
      const cardHeight = 420; // 稍微调高一点预留给新文字的空间
      const minTop = 60;
      const maxTop = window.innerHeight - cardHeight - 60;
      const minLeft = 60;
      const maxLeft = window.innerWidth - cardWidth - 60;
      this.offset.top =
        Math.floor(Math.random() * (maxTop - minTop + 1)) + minTop;
      this.offset.left =
        Math.floor(Math.random() * (maxLeft - minLeft + 1)) + minLeft;
    },
    goToRegister() {
      this.$router.push("/register");
    },
  },
};
</script>

<style scoped>
.login-page {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background: #f5f4ff;
  overflow: hidden;
  position: relative; /* 核心：允许登录框全屏起飞 */
}
.side-panel {
  flex: 1;
  min-height: 100vh;
  background-size: cover;
  background-position: center;
}
.left-panel {
  background-image: url("../assets/background.JPG");
}
.center-panel {
  flex: 0 0 420px;
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-image: url("../assets/background_middle.JPG");
  background-size: cover;
  background-position: center;
}
.right-panel {
  background-image: url("../assets/background_right.PNG");
}

/* 升级：充满粉紫少女感的高斯模糊登录面板 */
.login-panel {
  width: 100%;
  max-width: 360px;
  margin: 0 16px;
  padding: 42px 28px;
  border-radius: 32px; /* 更圆润的R角 */
  background: rgba(255, 255, 255, 0.78); /* 微微提高亮度 */
  box-shadow: 0 30px 65px rgba(120, 80, 160, 0.15); /* 柔和的薰衣草紫阴影 */
  backdrop-filter: blur(16px);
  text-align: center;
  box-sizing: border-box;
  border: 1.5px solid rgba(255, 192, 203, 0.3); /* 淡淡的粉色描边 */
}

.login-panel h1 {
  margin: 0 0 4px 0;
  font-size: 28px;
  color: #4a3475;
  font-weight: bold;
}

.sub-title {
  margin: 0 0 16px 0;
  font-size: 13px;
  color: #8c7ba6;
}

.input-class {
  width: 100%;
  margin-top: 18px;
}

/* 穿透修改 Element UI 输入框，让它跟注册页一样圆嘟嘟 */
:deep(.el-input__wrapper) {
  border-radius: 999px !important;
  box-shadow: 0 4px 10px rgba(90, 47, 126, 0.03) !important;
}

/* 升级：渐变马卡龙色登录按钮 */
.login-button {
  width: 100%;
  margin-top: 28px;
  padding: 14px 0;
  border-radius: 999px;
  background: linear-gradient(
    90deg,
    #66ccff,
    #ff99cc
  ) !important; /* 经典萌系粉蓝渐变 */
  border: none !important;
  color: #fff !important;
  font-size: 16px;
  font-weight: bold;
  box-shadow: 0 6px 15px rgba(102, 204, 255, 0.4);
  transition: all 0.2s ease;
}

.login-button:hover {
  transform: scale(1.02);
  box-shadow: 0 8px 20px rgba(255, 153, 204, 0.5);
}

.switch-action {
  margin-top: 18px;
  font-size: 13px;
  color: #8c7ba6;
}
.switch-action span {
  cursor: pointer;
  text-decoration: none;
  transition: color 0.2s;
}
.switch-action span:hover {
  color: #ff99cc;
  text-decoration: underline;
}

/* 傲娇提示词样式 */
.hint-text {
  margin: 16px 0 0 0;
  color: #ff4d7e;
  font-size: 13px;
  font-weight: bold;
  animation: shake 0.3s ease; /* 提示出现时带有一点可爱的抖动效果 */
}

/* 软萌的抖动小动画 */
@keyframes shake {
  0%,
  100% {
    transform: translateX(0);
  }
  25% {
    transform: translateX(-4px);
  }
  75% {
    transform: translateX(4px);
  }
}
</style>
