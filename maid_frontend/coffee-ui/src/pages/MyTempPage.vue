<script setup>
import { useRouter } from "vue-router";
import coffeeImage from "../assets/nvpucoffee.JPG";

const router = useRouter();

const enterSystem = () => {
  router.push("/mainpage");
};
</script>

<template>
  <div class="page-wrapper">
    <div class="photo-card">
      <img :src="coffeeImage" alt="nvpucoffee" class="login-photo" />

      <h1 @click="enterSystem" class="clickable-title">{{ msg }}</h1>
    </div>

    <div>
      <ul>
        <li v-for="bean in beans" :key="bean.id">
          {{ bean.name }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
// 保持你原有的测试数据、注释和逻辑完全不动
export default {
  name: "MyTempPage",
  data() {
    return {
      msg: "ご主人様、メイドカフェへようこそ",
      flag: false,
      number: 11,
      inputType: "text",
      // beans: [
      //   { id: 1, name: "黄豆" },
      //   { id: 2, name: "绿豆" },
      //   { id: 3, name: "红豆" },
      //   { id: 4, name: "黑豆" },
      // ],
    };
  },
  methods: {
    numberAdd() {
      this.number += 1;
      if (this.number % 2 == 0) {
        this.inputType = "password";
        this.flag = false;
      } else {
        this.inputType = "text";
        this.flag = true;
      }
    },
  },
  mounted() {
    alert("Welcome to Maid Café! 😘");
  },
  watch: {
    number(newVal) {
      console.log("number changed to:", newVal);
    },
  },
};
</script>

<style scoped>
.page-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 24px;
  padding: 24px;
}

.photo-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
  padding: 24px;
  border-radius: 20px;
  background: rgba(255, 255, 255, 0.92);
  box-shadow: 0 12px 30px rgba(0, 0, 0, 0.12);
  max-width: 720px;
  width: 100%;
}

.login-photo {
  width: 100%;
  max-width: 640px;
  border-radius: 16px;
  object-fit: cover;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.12);
}

.page-wrapper > div:last-child {
  width: 100%;
}

.clickable-title {
  font-size: 2em;
  margin: 0;
  text-align: center;
  cursor: pointer; /* 鼠标移上去变成小手形状 */
  transition: all 0.3s ease; /* 让颜色和缩放过渡更丝滑 */
  color: #3a2e2b; /* 初始咖啡深巧色 */
}

/* 鼠标悬浮时，文字变成甜美的草莓粉，并微微放大 */
.clickable-title:hover {
  color: #ff6b8b;
  transform: scale(1.03);
}

div {
  text-align: center;
}
</style>
