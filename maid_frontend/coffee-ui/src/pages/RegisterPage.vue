<template>
  <div class="register-page">
    <div class="register-container">
      <div class="register-left-pic">
        <img src="../assets/register.jpg" alt="Register Mascot" />
      </div>

      <div class="register-right-form">
        <div class="form-header">
          <h1>新主人登记处 📝✨</h1>
          <p>「加入 maid~bar，开启属于你的魔法咖啡馆旅程吧~」</p>
        </div>

        <div class="form-body">
          <el-input
            class="input-class"
            placeholder="請输入你想用的用户名 🐾"
            v-model="username"
            clearable
          />
          <el-input
            class="input-class"
            placeholder="請输入你的神秘密码 🔑"
            v-model="password"
            type="password"
            show-password
          />
        </div>

        <el-button class="register-submit-btn" @click="handleRegister">
          注入魔法，完成登记！🪄
        </el-button>

        <div class="back-to-login">
          <span @click="goToLogin">🔙 已有通行证？返回登录入口</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "RegisterPage",
  data() {
    return {
      username: "",
      password: "",
    };
  },
  methods: {
    handleRegister() {
      if (this.username.trim() === "" || this.password.trim() === "") {
        this.$message.error("🍭 哎呀！主人，用户名和密码不能空着哦~");
        return;
      }

      // 请求后端注册接口
      this.$http
        .post("/register", {
          username: this.username,
          password: this.password,
        })
        .then((res) => {
          if (res.data === "注册成功") {
            this.$message({
              message: "🎉 登记成功！魔法大门已为你敞开，这就去登录吧~",
              type: "success",
            });
            // 延迟 1.5 秒跳转，给主人看一下甜甜的成功提示
            setTimeout(() => {
              this.$router.push("/");
            }, 1500);
          } else {
            this.$message.error(
              "💢 " + (res.data || "注册失败，换个名字试试框~"),
            );
          }
        })
        .catch((err) => {
          console.error(err);
          this.$message.error(
            "📡 呜呜，魔法水晶球失联了（网络错误），请稍后再试",
          );
        });
    },
    goToLogin() {
      this.$router.push("/");
    },
  },
};
</script>

<style scoped>
/* 全局页面背景：使用带有可爱粉紫调的渐变 */
.register-page {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f4ff 0%, #fff0f5 100%);
  overflow: hidden;
}

/* 核心毛玻璃双栏容器 */
.register-container {
  display: flex;
  width: 760px;
  height: 480px;
  border-radius: 32px;
  background: rgba(255, 255, 255, 0.8);
  box-shadow: 0 30px 70px rgba(135, 95, 185, 0.15);
  backdrop-filter: blur(20px);
  overflow: hidden;
  border: 2px solid rgba(255, 192, 203, 0.4);
}

/* 左侧图片面板 */
.register-left-pic {
  flex: 1;
  background: #fff5f7;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
  border-right: 1px dashed rgba(255, 192, 203, 0.6);
}

.register-left-pic img {
  max-width: 90%;
  max-height: 90%;
  object-fit: contain;
  /* 加个软糯的上下浮动萌系动画 */
  animation: floatAnimation 3s ease-in-out infinite;
}

/* 右侧表单面板 */
.register-right-form {
  flex: 1.2;
  padding: 40px 35px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  box-sizing: border-box;
}

.form-header h1 {
  margin: 0 0 8px 0;
  font-size: 26px;
  color: #4a3475;
}

.form-header p {
  margin: 0 0 24px 0;
  font-size: 13px;
  color: #8c7ba6;
  line-height: 1.4;
}

.input-class {
  width: 100%;
  margin-top: 18px;
}

/* 深度覆写 Element UI 输入框样式，让它更圆润 */
:deep(.el-input__wrapper) {
  border-radius: 999px !important;
  background-color: rgba(255, 255, 255, 0.9) !important;
  box-shadow: 0 4px 10px rgba(90, 47, 126, 0.04) !important;
}

/* 萌萌的糖果粉蓝按钮 */
.register-submit-btn {
  width: 100%;
  margin-top: 32px;
  padding: 14px 0;
  border-radius: 999px;
  background: linear-gradient(90deg, #ff99cc, #66ccff) !important;
  border: none !important;
  color: #fff !important;
  font-size: 16px;
  font-weight: bold;
  box-shadow: 0 6px 15px rgba(255, 153, 204, 0.4);
  cursor: pointer;
  transition: all 0.3s ease;
}

.register-submit-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(102, 204, 255, 0.6);
}

/* 返回登录 */
.back-to-login {
  margin-top: 20px;
  text-align: center;
  font-size: 13px;
}

.back-to-login span {
  color: #8c7ba6;
  cursor: pointer;
  text-decoration: none;
  transition: color 0.2s;
}

.back-to-login span:hover {
  color: #ff99cc;
  text-decoration: underline;
}

/* 飘浮动画 */
@keyframes floatAnimation {
  0% {
    transform: translateY(0px);
  }
  50% {
    transform: translateY(-10px);
  }
  100% {
    transform: translateY(0px);
  }
}
</style>
