<template>
  <div class="subpage-container animated-fadeIn">
    <div class="page-header">
      <div class="title-area">
        <h2>🪄 魔法料理屋 <span class="en-title">/ Magic Menu</span></h2>
        <p class="sub-text">「把爱意与萌力，融进主人的每一口甜点里~ 🐾」</p>
      </div>
      <el-button type="primary" class="maid-luxury-btn" @click="openAddDialog">
        研制绝密新品 🍳
      </el-button>
    </div>

    <el-row :gutter="25" class="menu-luxury-grid">
      <el-col :span="8" v-for="item in menuList" :key="item.id">
        <div
          class="luxury-menu-card"
          :class="{ 'sold-out-card': item.status === 0 }"
        >
          <div class="category-ribbon">🎀 {{ item.category }}</div>

          <div class="menu-card-header">
            <h3>{{ item.dishName }}</h3>
            <span class="price-tag">🪙 {{ item.price }}</span>
          </div>

          <div class="menu-card-body">
            <p class="magic-quote">
              “ {{ item.magicEffect || "这道菜被调皮的妖精藏起了咒语~" }} ”
            </p>
          </div>

          <div class="menu-card-actions">
            <div class="status-switch">
              <span class="status-text">{{
                item.status === 1 ? "🪄 营业中" : "💤 售罄辣"
              }}</span>
              <el-switch
                v-model="item.status"
                :active-value="1"
                :inactive-value="0"
                active-color="#ff6b8b"
                @change="toggleStatus(item)"
              />
            </div>
            <div class="action-buttons">
              <span class="action-icon edit" @click="openEditDialog(item)"
                >施法微调 📝</span
              >
              <span class="action-icon delete" @click="deleteDish(item.id)"
                >封印 🔒</span
              >
            </div>
          </div>

          <div class="sold-out-stamp" v-if="item.status === 0">🐾 售罄喵</div>
        </div>
      </el-col>
    </el-row>

    <el-dialog
      v-model="dialogVisible"
      :title="isEdit ? '✨ 修正料理配方' : '✨ 注入新料理魔力'"
      width="460px"
      class="maid-dialog"
    >
      <el-form :model="form" label-width="90px" class="maid-form">
        <el-form-item label="料理名称">
          <el-input
            v-model="form.dishName"
            placeholder="如: 萌娘特制魔法蛋包饭✨"
          />
        </el-form-item>
        <el-form-item label="料理分类">
          <el-select
            v-model="form.category"
            placeholder="请选择类型"
            style="width: 100%"
          >
            <el-option label="魔法主食 🍛" value="魔法主食" />
            <el-option label="治愈甜点 🍰" value="治愈甜点" />
            <el-option label="萌系饮品 🍹" value="萌系饮品" />
          </el-select>
        </el-form-item>
        <el-form-item label="魔力价格">
          <el-input-number
            v-model="form.price"
            :precision="2"
            :step="1"
            :min="0"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="魔法寄语">
          <el-input
            v-model="form.magicEffect"
            type="textarea"
            :rows="3"
            placeholder="吃完会充满力量的咒语..."
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <span class="cancel-txt-btn" @click="dialogVisible = false"
            >丢弃配方</span
          >
          <el-button type="primary" class="maid-luxury-btn" @click="submitForm"
            >烹饪上桌！</el-button
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
const menuList = ref<any[]>([]);
const dialogVisible = ref(false);
const isEdit = ref(false);

// 表单响应式数据
const form = ref<any>({
  id: null,
  dishName: "",
  category: "魔法主食",
  price: 0.0,
  magicEffect: "",
  status: 1,
});

// 1. 查：获取魔法菜单列表（对应后端 /api/menu/list）
const getMenu = () => {
  proxy.$http
    .get("/api/menu/list")
    .then((res: any) => {
      menuList.value = res.data;
    })
    .catch((err: any) => {
      console.error(err);
      ElMessage.error("📡 呜呜，后厨水晶球失联了...");
    });
};

const openAddDialog = () => {
  isEdit.value = false;
  form.value = {
    id: null,
    dishName: "",
    category: "魔法主食",
    price: 0.0,
    magicEffect: "",
    status: 1,
  };
  dialogVisible.value = true;
};

const openEditDialog = (row: any) => {
  isEdit.value = true;
  form.value = { ...row }; // 深拷贝，防止未保存就修改了视图
  dialogVisible.value = true;
};

// 2. 改：切换上下架状态（对应后端 /api/menu/update）
const toggleStatus = (row: any) => {
  proxy.$http.put("/api/menu/update", row).then((res: any) => {
    ElMessage.success("✨ 料理结界状态更新！");
  });
};

// 3. 增/改：提交表单
const submitForm = () => {
  if (!form.value.dishName.trim()) {
    ElMessage.warning("🍭 主人，料理还没有名字呢~");
    return;
  }
  const url = isEdit.value ? "/api/menu/update" : "/api/menu/add";
  const method = isEdit.value ? "put" : "post";

  proxy.$http[method](url, form.value).then((res: any) => {
    ElMessage.success("🎉 " + res.data);
    dialogVisible.value = false;
    getMenu(); // 刷新列表
  });
};

// 4. 删：下架并抹去料理（对应后端 /api/menu/delete/{id}）
const deleteDish = (id: number) => {
  ElMessageBox.confirm("确定要将这款料理从魔法书里永久封印吗？🐾", "结界警告", {
    confirmButtonText: "确定封印",
    cancelButtonText: "留它一命",
    type: "warning",
  }).then(() => {
    proxy.$http.delete(`/api/menu/delete/${id}`).then((res: any) => {
      ElMessage.success("🔒 " + res.data);
      getMenu();
    });
  });
};

onMounted(() => {
  getMenu();
});
</script>

<style scoped>
/* 渐入场动画 */
.animated-fadeIn {
  animation: fadeIn 0.5s ease-out;
}
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
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
  font-weight: bold;
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

/* 奢华渐变萌按钮 */
.maid-luxury-btn {
  background: linear-gradient(135deg, #ff99cc 0%, #ff6b8b 100%) !important;
  border: none !important;
  color: #fff !important;
  border-radius: 20px;
  font-weight: bold;
  padding: 10px 20px;
  box-shadow: 0 5px 15px rgba(255, 107, 139, 0.3);
  transition: all 0.3s cubic-bezier(0.175, 0.885, 0.32, 1.275);
}
.maid-luxury-btn:hover {
  transform: translateY(-2px) scale(1.03);
  box-shadow: 0 8px 20px rgba(255, 107, 139, 0.5);
}

/* 拟物化魔法手账卡片 */
.menu-luxury-grid {
  margin-top: 10px;
}
.luxury-menu-card {
  background: #ffffff;
  border-radius: 24px;
  padding: 28px 24px 20px 24px;
  margin-bottom: 25px;
  position: relative;
  overflow: hidden;
  border: 2px solid transparent;
  box-shadow: 0 8px 25px rgba(165, 135, 195, 0.06);
  transition: all 0.4s cubic-bezier(0.165, 0.84, 0.44, 1);
}
.luxury-menu-card:hover {
  transform: translateY(-6px);
  border-color: #ffb6c1;
  box-shadow: 0 15px 30px rgba(255, 107, 139, 0.15);
}

/* 分类丝带 */
.category-ribbon {
  position: absolute;
  top: 0;
  right: 20px;
  background: #fff0f3;
  color: #ff6b8b;
  font-size: 11px;
  font-weight: bold;
  padding: 4px 12px;
  border-radius: 0 0 10px 10px;
}

.menu-card-header {
  display: flex;
  justify-content: space-between;
  align-items: baseline;
  margin-top: 8px;
}
.menu-card-header h3 {
  color: #3a2e2b;
  font-size: 18px;
  margin: 0;
}
.price-tag {
  font-size: 19px;
  font-weight: 900;
  color: #ff6b8b;
}

/* 便签纸风格料理描述 */
.menu-card-body {
  background: #fdfbf7;
  border-left: 3px solid #f3e9dc;
  padding: 10px 12px;
  margin: 16px 0;
  border-radius: 0 12px 12px 0;
}
.magic-quote {
  margin: 0;
  font-size: 13px;
  color: #7d6b67;
  font-style: italic;
  line-height: 1.4;
}

/* 卡片动作栏 */
.menu-card-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 16px;
  border-top: 1px dashed #f0ebf7;
  padding-top: 12px;
}
.status-text {
  font-size: 12px;
  color: #8c7ba6;
  margin-right: 6px;
  font-weight: bold;
}

/* 抛弃死板按钮，改成高级文字Icon链接 */
.action-buttons {
  display: flex;
  gap: 12px;
}
.action-icon {
  font-size: 12px;
  cursor: pointer;
  font-weight: bold;
  transition: color 0.2s;
}
.action-icon.edit {
  color: #66ccff;
}
.action-icon.edit:hover {
  color: #33b5ff;
  text-decoration: underline;
}
.action-icon.delete {
  color: #ff4d7e;
}
.action-icon.delete:hover {
  color: #ff1a53;
  text-decoration: underline;
}

/* 售罄盖章特效 */
.sold-out-card {
  opacity: 0.55;
  background: #fafafa;
}
.sold-out-stamp {
  position: absolute;
  top: 30%;
  left: 32%;
  transform: rotate(-15deg);
  border: 3px dashed #ff4d7e;
  color: #ff4d7e;
  font-weight: 900;
  padding: 6px 16px;
  border-radius: 12px;
  font-size: 16px;
  background: rgba(255, 255, 255, 0.9);
}

/* 穿透修改弹窗圆角 */
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
.cancel-txt-btn:hover {
  color: #666;
}
.dialog-footer {
  display: flex;
  align-items: center;
  justify-content: flex-end;
}
</style>
