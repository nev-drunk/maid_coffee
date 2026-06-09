<template>
  <div class="subpage-container animated-fadeIn">
    <div class="page-header">
      <div class="title-area">
        <h2>
          💖 圣殿契约·主人入籍录
          <span class="en-title">/ Master VIP Profiles</span>
        </h2>
        <p class="sub-text">
          「这里精心铭刻着每一位回屋主人的羁绊与深厚好感度喵~」
        </p>
      </div>
      <el-button type="primary" class="maid-luxury-btn" @click="openAddDialog">
        举办来访入籍仪式 📝
      </el-button>
    </div>

    <el-row :gutter="25" class="vip-luxury-grid">
      <el-col :span="8" v-for="item in vipList" :key="item.id">
        <div class="luxury-vip-card" :class="getVipClass(item.vipLevel)">
          <div class="card-lace-border"></div>

          <div class="vip-card-header">
            <div class="vip-no">NO.{{ String(item.id).padStart(4, "0") }}</div>
            <div class="vip-level-crown">
              {{ getCrownIcon(item.vipLevel) }} {{ item.vipLevel }}
            </div>
          </div>

          <div class="vip-card-body">
            <h3 class="master-honorific">✨ {{ item.masterName }} ✨</h3>

            <div class="love-points-pool">
              <div class="pool-meta">
                <span>🌸 羁绊好感度:</span>
                <span class="pts-text">{{ item.lovePoints }} P</span>
              </div>
              <el-progress
                :percentage="Math.min((item.lovePoints / 1000) * 100, 100)"
                :stroke-width="8"
                color="#ff99cc"
                :show-text="false"
              />
            </div>

            <div class="first-visit-time">
              🕒 初次缔结契约: {{ formatDate(item.registerTime) }}
            </div>
          </div>

          <div class="vip-card-actions">
            <span class="vip-action-btn charge" @click="openEditDialog(item)"
              >注入魔力(积分充值) 🪙</span
            >
            <span class="vip-action-btn break" @click="deleteVip(item.id)"
              >斩断契约 🔒</span
            >
          </div>
        </div>
      </el-col>
    </el-row>

    <el-dialog
      v-model="dialogVisible"
      :title="
        isEdit
          ? '✨ 注入魔力，加深与主人的羁绊'
          : '✨ 举行神圣的眷属入籍契约仪式'
      "
      width="420px"
    >
      <el-form :model="form" label-width="95px" class="maid-form">
        <el-form-item label="主人尊称">
          <el-input
            v-model="form.masterName"
            placeholder="如: 傲娇的张总 / 欧皇大人"
            :disabled="isEdit"
          />
        </el-form-item>
        <el-form-item label="会员头衔">
          <el-select v-model="form.vipLevel" style="width: 100%">
            <el-option label="萌新主人 ☕" value="萌新主人" />
            <el-option label="核心Vip主人 👑" value="核心Vip主人" />
            <el-option label="幻神主人 🪐 (至高神)" value="幻神主人" />
          </el-select>
        </el-form-item>
        <el-form-item label="好感度魔力">
          <el-input-number
            v-model="form.lovePoints"
            :min="0"
            :step="50"
            style="width: 100%"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <span class="cancel-txt-btn" @click="dialogVisible = false"
            >仪式中止</span
          >
          <el-button type="primary" class="maid-luxury-btn" @click="submitForm"
            >赐予全知契约✨</el-button
          >
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, getCurrentInstance } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";

const { proxy } = getCurrentInstance() as any;
const vipList = ref([]);
const dialogVisible = ref(false);
const isEdit = ref(false);
const form = ref<any>({
  id: null,
  masterName: "",
  vipLevel: "萌新主人",
  lovePoints: 0,
});

const getVips = () => {
  proxy.$http.get("/api/vips/list").then((res: any) => {
    vipList.value = res.data;
  });
};

const openAddDialog = () => {
  isEdit.value = false;
  form.value = {
    id: null,
    masterName: "",
    vipLevel: "萌新主人",
    lovePoints: 0,
  };
  dialogVisible.value = true;
};

const openEditDialog = (row: any) => {
  isEdit.value = true;
  form.value = { ...row };
  dialogVisible.value = true;
};

const getVipClass = (level: string) => {
  if (level === "幻神主人") return "level-god";
  if (level === "核心Vip主人") return "level-vip";
  return "level-newbie";
};

const getCrownIcon = (level: string) => {
  if (level === "幻神主人") return "🪐";
  if (level === "核心Vip主人") return "👑";
  return "☕";
};

const formatDate = (dateStr: any) => {
  if (!dateStr) return "上古神话次元";
  return dateStr.replace("T", " ").substring(0, 16);
};

const submitForm = () => {
  if (!form.value.masterName.trim()) {
    ElMessage.warning("🍭 主人，还没写下大名尊称呢~");
    return;
  }
  const url = isEdit.value ? "/api/vips/update" : "/api/vips/add";
  const method = isEdit.value ? "put" : "post";
  proxy.$http[method](url, form.value).then((res: any) => {
    ElMessage.success("💖 " + res.data);
    dialogVisible.value = false;
    getVips();
  });
};

const deleteVip = (id: number) => {
  ElMessageBox.confirm(
    "注销档案将彻底抹除主人的好感度积分与特权契约，保安将进行无情驱逐，确定吗？！",
    "神圣契约破裂警告",
    {
      confirmButtonText: "确定驱逐",
      cancelButtonText: "网开一面",
      type: "danger",
    },
  ).then(() => {
    proxy.$http.delete(`/api/vips/delete/${id}`).then((res: any) => {
      ElMessage.success("🔒 " + res.data);
      getVips();
    });
  });
};

onMounted(() => {
  getVips();
});
</script>

<style scoped>
.animated-fadeIn {
  animation: fadeIn 0.4s ease-out;
}
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(8px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 25px;
  border-bottom: 2px dashed #ffb6c1;
  padding-bottom: 15px;
}
.page-header h2 {
  color: #4a3475;
  margin: 0;
  font-size: 24px;
}
.en-title {
  font-size: 14px;
  color: #c0b3d6;
  font-weight: normal;
}
.sub-text {
  margin: 4px 0 0 0;
  font-size: 13px;
  color: #8c7ba6;
}

.maid-luxury-btn {
  background: linear-gradient(135deg, #ff99cc 0%, #ff6b8b 100%) !important;
  border: none !important;
  color: #fff !important;
  border-radius: 20px;
  font-weight: bold;
  box-shadow: 0 5px 15px rgba(255, 107, 139, 0.3);
  transition: all 0.3s ease;
}
.maid-luxury-btn:hover {
  transform: translateY(-2px);
}

/* 精致二次元水晶会员卡 */
.luxury-vip-card {
  border-radius: 24px;
  padding: 24px;
  margin-bottom: 25px;
  position: relative;
  overflow: hidden;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.05);
  transition: all 0.4s cubic-bezier(0.165, 0.84, 0.44, 1);
}
.luxury-vip-card:hover {
  transform: translateY(-6px) scale(1.01);
  box-shadow: 0 15px 35px rgba(255, 133, 161, 0.25);
}

/* 不同等级的主人卡片异色皮肤 */
.level-newbie {
  background: linear-gradient(135deg, #fffefe 0%, #f7ede2 100%);
  border: 2px solid #e3d5ca;
}
.level-vip {
  background: linear-gradient(135deg, #fff5f7 0%, #ffebf0 100%);
  border: 2px solid #ffb6c1;
}
.level-god {
  background: linear-gradient(135deg, #f3e8ff 0%, #e0c3fc 100%);
  border: 2px solid #b388ff;
}

.card-lace-border {
  position: absolute;
  top: 6px;
  bottom: 6px;
  left: 6px;
  right: 6px;
  border: 1px dashed rgba(58, 46, 43, 0.15);
  border-radius: 18px;
  pointer-events: none;
}

.vip-card-header {
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  font-weight: bold;
  font-family: monospace;
  color: #8a7874;
}
.level-god .vip-card-header {
  color: #6a1b9a;
}
.vip-level-crown {
  font-family: sans-serif;
  font-size: 13px;
}

.vip-card-body {
  text-align: center;
  margin: 15px 0 10px 0;
}
.master-honorific {
  color: #3a2e2b;
  font-size: 20px;
  margin: 0 0 16px 0;
  font-weight: 900;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.02);
}
.level-god .master-honorific {
  color: #4a148c;
}

.love-points-pool {
  background: rgba(255, 255, 255, 0.6);
  padding: 10px 14px;
  border-radius: 14px;
  margin-bottom: 15px;
}
.pool-meta {
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  color: #7d6b67;
  margin-bottom: 6px;
  font-weight: bold;
}
.pts-text {
  color: #ff6b8b;
  font-weight: 900;
}

.first-visit-time {
  font-size: 11px;
  color: #a69888;
  text-align: left;
}

.vip-card-actions {
  display: flex;
  justify-content: space-between;
  border-top: 1px dashed rgba(58, 46, 43, 0.1);
  padding-top: 12px;
  margin-top: 12px;
  font-size: 12px;
}
.vip-action-btn {
  cursor: pointer;
  font-weight: bold;
}
.vip-action-btn.charge {
  color: #66ccff;
}
.vip-action-btn.break {
  color: #ff4d7e;
  opacity: 0.7;
}
.vip-action-btn:hover {
  text-decoration: underline;
  opacity: 1;
}
</style>
