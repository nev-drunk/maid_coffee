<template>
  <div class="subpage-container animated-fadeIn">
    <div class="page-header">
      <div class="title-area">
        <h2>
          🐾 咖啡座与萌娘状态
          <span class="en-title">/ Table & Maid Status</span>
        </h2>
        <p class="sub-text">
          「当前大厅的茶座结界一切正常，女仆们正在努力侍奉中哦~」
        </p>
      </div>
      <el-button type="primary" class="maid-luxury-btn" @click="openAddDialog">
        迎来新卡座 🎀
      </el-button>
    </div>

    <el-row :gutter="25" class="table-luxury-grid">
      <el-col :span="6" v-for="item in tableList" :key="item.id">
        <div
          class="luxury-table-card"
          :class="{ 'serving-card': item.status === '侍奉中' }"
        >
          <div class="table-badge">✨ {{ item.tableNumber }}</div>

          <div class="card-main-content">
            <div class="status-indicator-bar">
              <span
                class="dot"
                :class="{ 'dot-active': item.status === '侍奉中' }"
              ></span>
              <span class="status-txt">{{ item.status }}</span>
            </div>

            <div class="table-meta-info">
              <p class="meta-row">
                <span>👥 容纳限额:</span>
                <strong class="val">{{ item.capacity }} 人座</strong>
              </p>
              <p class="meta-row">
                <span>🪄 担当女仆:</span>
                <strong
                  class="val maid-name"
                  :class="{ 'has-maid': item.currentMaid }"
                >
                  {{ item.currentMaid || "暂无安排 💤" }}
                </strong>
              </p>
            </div>

            <div class="table-memo-pad">
              <p class="memo-text">
                {{ item.memo || "本桌暂时没有写下备忘契约喵~" }}
              </p>
            </div>
          </div>

          <div class="table-card-actions">
            <span class="action-link edit" @click="openEditDialog(item)"
              >变更状态 🪄</span
            >
            <span class="action-link delete" @click="deleteTable(item.id)"
              >撤走卡座 🔒</span
            >
          </div>

          <div class="serving-heart-icon" v-if="item.status === '侍奉中'">
            💖
          </div>
        </div>
      </el-col>
    </el-row>

    <el-dialog
      v-model="dialogVisible"
      :title="isEdit ? '✨ 变更桌位结界状态' : '✨ 迎来全新主题卡座'"
      width="420px"
    >
      <el-form :model="form" label-width="95px" class="maid-form">
        <el-form-item label="桌位编号">
          <el-input
            v-model="form.tableNumber"
            placeholder="如: 喵喵座 / A03"
            :disabled="isEdit"
          />
        </el-form-item>
        <el-form-item label="最大容纳">
          <el-input-number
            v-model="form.capacity"
            :min="1"
            :max="12"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="结界状态">
          <el-select v-model="form.status" style="width: 100%">
            <el-option label="空闲中 🍃" value="空闲中" />
            <el-option label="侍奉中 🔥" value="侍奉中" />
          </el-select>
        </el-form-item>
        <el-form-item label="担当女仆">
          <el-input
            v-model="form.currentMaid"
            placeholder="请输入值班女仆工号/花名"
          />
        </el-form-item>
        <el-form-item label="魔法备忘">
          <el-input
            v-model="form.memo"
            type="textarea"
            :rows="2"
            placeholder="记录主人们的特殊癖好或点单信息..."
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <span class="cancel-txt-btn" @click="dialogVisible = false"
            >维持原样</span
          >
          <el-button type="primary" class="maid-luxury-btn" @click="submitForm"
            >契约刻印！</el-button
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
const tableList = ref<any[]>([]);
const dialogVisible = ref(false);
const isEdit = ref(false);
const form = ref<any>({
  id: null,
  tableNumber: "",
  capacity: 2,
  status: "空闲中",
  currentMaid: "",
  memo: "",
});

const getTables = () => {
  proxy.$http.get("/api/tables/list").then((res: any) => {
    tableList.value = res.data;
  });
};

const openAddDialog = () => {
  isEdit.value = false;
  form.value = {
    id: null,
    tableNumber: "",
    capacity: 2,
    status: "空闲中",
    currentMaid: "",
    memo: "",
  };
  dialogVisible.value = true;
};

const openEditDialog = (row: any) => {
  isEdit.value = true;
  form.value = { ...row };
  dialogVisible.value = true;
};

const submitForm = () => {
  if (!form.value.tableNumber.trim()) {
    ElMessage.warning("🍭 主人，桌位还没有名字编号呢~");
    return;
  }
  const url = isEdit.value ? "/api/tables/update" : "/api/tables/add";
  const method = isEdit.value ? "put" : "post";
  proxy.$http[method](url, form.value).then((res: any) => {
    ElMessage.success("🐾 " + res.data);
    dialogVisible.value = false;
    getTables();
  });
};

const deleteTable = (id: number) => {
  ElMessageBox.confirm("确定要将这个充满回忆的卡座撤回仓库吗？", "结界重置", {
    confirmButtonText: "确定撤走",
    cancelButtonText: "留下它喵",
    type: "warning",
  }).then(() => {
    proxy.$http.delete(`/api/tables/delete/${id}`).then((res: any) => {
      ElMessage.success("🔒 " + res.data);
      getTables();
    });
  });
};

onMounted(() => {
  getTables();
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
  transition: all 0.3s cubic-bezier(0.175, 0.885, 0.32, 1.275);
}
.maid-luxury-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(255, 107, 139, 0.5);
}

/* 拟物化卡片 */
.luxury-table-card {
  background: #fff;
  border-radius: 20px;
  padding: 24px 20px 16px 20px;
  margin-bottom: 25px;
  position: relative;
  overflow: hidden;
  box-shadow: 0 8px 22px rgba(165, 135, 195, 0.06);
  border: 2px solid transparent;
  transition: all 0.35s ease;
}
.luxury-table-card:hover {
  transform: translateY(-5px);
  border-color: #ffb6c1;
  box-shadow: 0 12px 28px rgba(255, 107, 139, 0.12);
}

/* 傲娇侍奉状态高亮 */
.serving-card {
  background: #fff5f7;
  border: 2px dashed #ffb6c1;
}

.table-badge {
  position: absolute;
  top: 0;
  left: 0;
  background: #3a2e2b;
  color: #fff;
  font-size: 12px;
  font-weight: bold;
  padding: 5px 16px;
  border-radius: 0 0 16px 0;
  letter-spacing: 0.5px;
}
.status-indicator-bar {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  margin-bottom: 15px;
}
.status-indicator-bar .dot {
  width: 8px;
  height: 8px;
  background: #a291c3;
  border-radius: 50%;
  margin-right: 6px;
}
.status-indicator-bar .dot-active {
  background: #ff4d7e;
  box-shadow: 0 0 8px #ff4d7e;
}
.status-txt {
  font-size: 11px;
  color: #8c7ba6;
  font-weight: bold;
}

.table-meta-info {
  margin: 12px 0;
}
.meta-row {
  display: flex;
  justify-content: space-between;
  margin: 6px 0;
  font-size: 13px;
  color: #7d6b67;
}
.meta-row .val {
  color: #3a2e2b;
}
.meta-row .maid-name {
  color: #999;
  font-style: italic;
}
.meta-row .has-maid {
  color: #ff6b8b;
  font-style: normal;
  font-weight: bold;
}

/* 便签贴记事本 */
.table-memo-pad {
  background: #fdfbf7;
  border-top: 1px solid #f5ebd8;
  padding: 8px 10px;
  border-radius: 8px;
  min-height: 52px;
  margin-top: 12px;
}
.memo-text {
  margin: 0;
  font-size: 12px;
  color: #8a7874;
  line-height: 1.4;
  font-style: italic;
}

.table-card-actions {
  display: flex;
  justify-content: space-between;
  border-top: 1px dashed #f0ebf7;
  padding-top: 12px;
  margin-top: 12px;
}
.action-link {
  font-size: 12px;
  cursor: pointer;
  font-weight: bold;
}
.action-link.edit {
  color: #66ccff;
}
.action-link.delete {
  color: #ff99cc;
}
.action-link:hover {
  text-decoration: underline;
}

.serving-heart-icon {
  position: absolute;
  bottom: 45%;
  right: 10px;
  font-size: 32px;
  opacity: 0.12;
  animation: pulse 1.5s infinite ease-in-out;
}
@keyframes pulse {
  0%,
  100% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.2);
  }
}

:deep(.el-dialog) {
  border-radius: 24px !important;
  padding: 20px;
}
.cancel-txt-btn {
  color: #999;
  cursor: pointer;
  font-size: 14px;
  margin-right: 20px;
}
.dialog-footer {
  display: flex;
  align-items: center;
  justify-content: flex-end;
}
</style>
