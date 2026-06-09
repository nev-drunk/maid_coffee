<template>
  <div class="subpage-container animated-fadeIn">
    <div class="page-header">
      <div class="title-area">
        <h2>
          👑 女仆执勤官邸 <span class="en-title">/ 3D Maid Photo Roster</span>
        </h2>
        <p class="sub-text">
          「在此上传并校对女仆们的真实御影，为主人们展现最迷人的侍奉姿态吧~」
        </p>
      </div>
      <el-button type="primary" class="maid-luxury-btn" @click="openAddDialog">
        召唤新女仆入籍 📅
      </el-button>
    </div>

    <el-row :gutter="25" class="schedule-luxury-grid">
      <el-col :span="6" v-for="item in scheduleList" :key="item.id">
        <div
          class="luxury-3d-card"
          :class="{ 'rest-day-note': item.status === '休息喵' }"
        >
          <div class="photo-shimmer-layer"></div>

          <div class="maid-photo-frame">
            <img
              v-if="item.avatarUrl"
              :src="
                item.avatarUrl.startsWith('http')
                  ? item.avatarUrl
                  : 'http://localhost:8081' + item.avatarUrl
              "
              class="maid-real-photo"
              alt="Maid Photo"
            />
            <div v-else class="photo-placeholder">
              <span class="ph-icon">📷</span>
              <span class="ph-txt">暂无御影·点击右下角添加</span>
            </div>

            <div
              class="badge-lace"
              :class="{ 'on-duty': item.status === '在岗' }"
            >
              {{ item.status === "在岗" ? "🐾 值班中" : "💤 梦境中" }}
            </div>
          </div>

          <div class="card-identity-plate">
            <div class="id-row">
              <h3 class="maid-title-name">{{ item.maidName }}</h3>
              <span class="maid-code-badge">ID: {{ item.maidCode }}</span>
            </div>

            <div class="shift-details">
              <p>
                <span>📅 执勤星历:</span> <strong>{{ item.workDate }}</strong>
              </p>
              <p>
                <span>🪄 守护班次:</span>
                <strong class="shift-tag">{{ item.shift }}</strong>
              </p>
            </div>
          </div>

          <div class="note-footer-actions">
            <span class="mini-txt-btn" @click="openEditDialog(item)"
              >修饰御影/改班 📝</span
            >
            <span class="mini-txt-btn del" @click="deleteSchedule(item.id)"
              >斩断通告 ❌</span
            >
          </div>
        </div>
      </el-col>
    </el-row>

    <el-dialog
      v-model="dialogVisible"
      :title="isEdit ? '✨ 修饰萌娘御影与值班时空' : '✨ 签发新的执勤召唤令'"
      width="450px"
    >
      <el-form :model="form" label-width="95px" class="maid-form">
        <el-form-item label="女仆真人照">
          <el-upload
            class="maid-photo-uploader"
            action=""
            :show-file-list="false"
            :auto-upload="false"
            :on-change="handleFileChange"
          >
            <img
              v-if="form.avatarUrl"
              :src="
                form.avatarUrl.startsWith('http')
                  ? form.avatarUrl
                  : 'http://localhost:8081' + form.avatarUrl
              "
              class="uploader-preview-img"
              alt="Preview"
            />
            <div v-else class="uploader-trigger-box">
              <el-icon class="uploader-icon"><Plus /></el-icon>
              <div class="el-upload__text">上传真实御影 (JPG/PNG)</div>
            </div>
          </el-upload>
          <div class="upload-tip">点击上方方块即可唤醒高级多维裁剪镜面喵~</div>
        </el-form-item>

        <el-form-item label="女仆工号">
          <el-input
            v-model="form.maidCode"
            placeholder="如: MAID001"
            :disabled="isEdit"
          />
        </el-form-item>
        <el-form-item label="女仆花名">
          <el-input v-model="form.maidName" placeholder="如: 蕾姆 / 樱乃酱" />
        </el-form-item>
        <el-form-item label="执勤日期">
          <el-date-picker
            v-model="form.workDate"
            type="date"
            placeholder="选择降临日期"
            value-format="YYYY-MM-DD"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="出勤班次">
          <el-select v-model="form.shift" style="width: 100%">
            <el-option label="早班喵 ☀️ (元气满满)" value="早班喵" />
            <el-option label="晚班喵 🌙 (霓虹魅影)" value="晚班喵" />
          </el-select>
        </el-form-item>
        <el-form-item label="结界在岗">
          <el-radio-group v-model="form.status">
            <el-radio value="在岗">在岗侍奉 🐾</el-radio>
            <el-radio value="休息喵">放假休息 💤</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <span class="cancel-txt-btn" @click="dialogVisible = false"
            >合上日志</span
          >
          <el-button type="primary" class="maid-luxury-btn" @click="submitForm"
            >时空锚定！</el-button
          >
        </div>
      </template>
    </el-dialog>

    <el-dialog
      v-model="cropperVisible"
      title="✂️ 皇家御影视界裁剪镜"
      width="600px"
      destroy-on-close
    >
      <div class="cropper-wrapper">
        <vue-cropper
          ref="cropperRef"
          :img="rawImgUrl"
          outputType="png"
          :autoCrop="true"
          :autoCropWidth="320"
          :autoCropHeight="240"
          :fixed="true"
          :fixedNumber="[4, 3]"
          :centerBox="true"
          :high="true"
          :info="false"
        />
      </div>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="cropperVisible = false">取消放弃</el-button>
          <el-button type="primary" class="maid-luxury-btn" @click="executeCrop"
            >注入时空卡片 ✨</el-button
          >
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, getCurrentInstance } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { Plus } from "@element-plus/icons-vue";
// 🌸 核心导入：只引入组件，规避打包不稳定的 CSS 路径
import { VueCropper } from "vue-cropper";

const { proxy } = getCurrentInstance() as any;
const scheduleList = ref<any[]>([]);
const dialogVisible = ref(false);
const isEdit = ref(false);
const form = ref<any>({
  id: null,
  maidCode: "",
  maidName: "",
  workDate: "",
  shift: "早班喵",
  status: "在岗",
  avatarUrl: "",
});

// ✂️ 裁剪相关响应式魔法矩阵
const cropperVisible = ref(false);
const cropperRef = ref();
const rawImgUrl = ref("");
let currentRawFile = null as any;

const getSchedules = () => {
  proxy.$http.get("/api/schedules/list").then((res: any) => {
    scheduleList.value = res.data;
  });
};

// 🔮 拦截原生图片选择，扔进虚空裁剪阵法中
const handleFileChange = (uploadFile: any) => {
  const rawFile = uploadFile.raw;
  if (!rawFile) return;

  if (rawFile.type !== "image/jpeg" && rawFile.type !== "image/png") {
    ElMessage.error("🍭 主人，照片必须 be JPG 或 PNG 格式喵！");
    return;
  }
  if (rawFile.size / 1024 / 1024 > 5) {
    ElMessage.error("🍭 照片文件不能超过 5MB 喵！");
    return;
  }

  currentRawFile = rawFile;
  // 生成本地虚拟高阶解析路径
  rawImgUrl.value = URL.createObjectURL(rawFile);
  cropperVisible.value = true;
};

// ✂️ 将裁剪网格锁定的数据流打包送还后端
const executeCrop = () => {
  if (!cropperRef.value) return;

  cropperRef.value.getCropBlob((blob: Blob) => {
    const formData = new FormData();
    formData.append("file", blob, currentRawFile.name || "avatar.png");

    proxy.$http
      .post("/api/schedules/upload-avatar", formData, {
        headers: { "Content-Type": "multipart/form-data" },
      })
      .then((res: any) => {
        if (res.data && res.data.code === 200) {
          form.value.avatarUrl = res.data.data;
        } else {
          form.value.avatarUrl = res.data;
        }
        ElMessage.success("📷 御影黄金比例裁剪并注入成功！");
        cropperVisible.value = false;
      })
      .catch(() => {
        ElMessage.error("🔒 契约结界上传失败喵~");
      });
  });
};

const openAddDialog = () => {
  isEdit.value = false;
  form.value = {
    id: null,
    maidCode: "",
    maidName: "",
    workDate: "",
    shift: "早班喵",
    status: "在岗",
    avatarUrl: "",
  };
  dialogVisible.value = true;
};

const openEditDialog = (row: any) => {
  isEdit.value = true;
  form.value = { ...row };
  dialogVisible.value = true;
};

const submitForm = () => {
  if (!form.value.maidCode || !form.value.maidName) {
    ElMessage.warning("🍭 主人，工号和花名不能为空哦！");
    return;
  }
  const url = isEdit.value ? "/api/schedules/update" : "/api/schedules/add";
  const method = isEdit.value ? "put" : "post";
  proxy.$http[method](url, form.value).then((res: any) => {
    ElMessage.success("📅 " + res.data);
    dialogVisible.value = false;
    getSchedules();
  });
};

const deleteSchedule = (id: number) => {
  ElMessageBox.confirm("确定要无情抹去这次的女仆执勤通告吗？", "召唤撤回", {
    confirmButtonText: "确定撤回",
    cancelButtonText: "继续执勤",
    type: "warning",
  }).then(() => {
    proxy.$http.delete(`/api/schedules/delete/${id}`).then((res: any) => {
      ElMessage.success("❌ " + res.data);
      getSchedules();
    });
  });
};

onMounted(() => {
  getSchedules();
});
</script>

<style scoped>
/* ==============================================
   🔮 注入 vue-cropper 核心结界样式 (防御 Vite 路径报错)
   ============================================== */
:deep(.vue-cropper) {
  position: relative;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
  user-select: none;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  direction: ltr;
  touch-action: none;
  background-image: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAMUlEQVQ4T2NkYGAQYcAP3uCTZhw1gGGYgAYG9D90g6gBtBCgKBlFi+BgNIBhkMAVAwAMBZ8Yf0g4gAAAAABJRU5ErkJggg==");
}
:deep(.cropper-box),
:deep(.cropper-box-canvas),
:deep(.cropper-drag-box),
:deep(.cropper-crop-box),
:deep(.cropper-face) {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
}
:deep(.cropper-box-canvas) {
  will-change: transform;
}
:deep(.cropper-drag-box) {
  background-color: rgba(0, 0, 0, 0.55);
  opacity: 0.6;
  cursor: crosshair;
}
:deep(.cropper-crop-box) {
  border: 1px dashed #ff99cc;
}
:deep(.cropper-crop-box) .cropper-view-box {
  display: block;
  overflow: hidden;
  width: 100%;
  height: 100%;
  outline: 1px solid #ff6b8b;
  outline-color: rgba(255, 107, 139, 0.75);
}
:deep(.cropper-face) {
  background-color: #fff;
  opacity: 0;
  cursor: move;
}

/* 🎨 追加高阶智能裁剪镜交互视窗样式 */
.cropper-wrapper {
  width: 100%;
  height: 400px;
  background: #262626;
  border-radius: 16px;
  overflow: hidden;
  border: 2px dashed #ffcad4;
}

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

/* 💎 3D 高奢拟物照片卡片 */
.luxury-3d-card {
  background: #ffffff;
  border-radius: 20px;
  padding: 12px;
  margin-bottom: 25px;
  position: relative;
  box-shadow: 0 10px 25px rgba(58, 46, 43, 0.06);
  border: 1px solid #f2e9e8;
  transition: all 0.4s cubic-bezier(0.165, 0.84, 0.44, 1);
  transform-style: preserve-3d;
}
.luxury-3d-card:hover {
  transform: translateY(-8px) rotateX(4deg) rotateY(-2deg);
  box-shadow: -5px 15px 30px rgba(255, 107, 139, 0.18);
}
.rest-day-note {
  opacity: 0.65;
  background: #fafafa;
}

.maid-photo-frame {
  width: 100%;
  height: 240px;
  border-radius: 14px;
  overflow: hidden;
  position: relative;
  background: #fff5f6;
  border: 1px solid rgba(0, 0, 0, 0.03);
}
.maid-real-photo {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center 15%; /* 🌸 智能聚焦：默认向上微调，大幅优先露脸 */
  transition: transform 0.5s;
}
.luxury-3d-card:hover .maid-real-photo {
  transform: scale(1.05);
}

.photo-shimmer-layer {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
  z-index: 3;
  background: linear-gradient(
    135deg,
    rgba(255, 255, 255, 0) 30%,
    rgba(255, 255, 255, 0.12) 50%,
    rgba(255, 255, 255, 0) 70%
  );
  background-size: 200% 200%;
  transition: all 0.5s;
}
.luxury-3d-card:hover .photo-shimmer-layer {
  background-position: right bottom;
}

.photo-placeholder {
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #ccb9b6;
  gap: 8px;
}
.photo-placeholder .ph-icon {
  font-size: 36px;
  opacity: 0.6;
}
.photo-placeholder .ph-txt {
  font-size: 11px;
  font-weight: bold;
}

.badge-lace {
  position: absolute;
  top: 12px;
  right: 12px;
  background: rgba(58, 46, 43, 0.85);
  color: #fff;
  font-size: 11px;
  font-weight: bold;
  padding: 4px 10px;
  border-radius: 20px;
  backdrop-filter: blur(4px);
  z-index: 2;
}
.badge-lace.on-duty {
  background: rgba(255, 107, 139, 0.9);
  box-shadow: 0 2px 8px rgba(255, 107, 139, 0.4);
}

.card-identity-plate {
  padding: 14px 6px 4px 6px;
}
.id-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}
.maid-title-name {
  margin: 0;
  color: #3a2e2b;
  font-size: 17px;
  font-weight: 900;
}
.maid-code-badge {
  font-size: 10px;
  color: #a69888;
  border: 1px dashed #d1c2b0;
  padding: 1px 5px;
  border-radius: 4px;
  font-family: monospace;
}

.shift-details {
  font-size: 12.5px;
  color: #7d6b67;
  line-height: 1.6;
}
.shift-details p {
  margin: 3px 0;
  display: flex;
  justify-content: space-between;
}
.shift-details strong {
  color: #3a2e2b;
}
.shift-tag {
  color: #ff6b8b !important;
  font-weight: bold;
}

.note-footer-actions {
  display: flex;
  justify-content: space-between;
  margin-top: 12px;
  border-top: 1px dashed #f2e9e8;
  padding-top: 10px;
  font-size: 11px;
}
.mini-txt-btn {
  cursor: pointer;
  color: #66ccff;
  font-weight: bold;
}
.mini-txt-btn.del {
  color: #ff99cc;
}
.mini-txt-btn:hover {
  text-decoration: underline;
}

.maid-photo-uploader {
  text-align: center;
}
.uploader-trigger-box {
  border: 2px dashed #ffcad4;
  border-radius: 16px;
  background: #fffbfb;
  cursor: pointer;
  width: 140px;
  height: 175px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 10px;
  transition: all 0.3s;
}
.uploader-trigger-box:hover {
  border-color: #ff6b8b;
  background: #fff5f6;
}
.uploader-icon {
  font-size: 24px;
  color: #ff99cc;
  margin-bottom: 8px;
}
.uploader-preview-img {
  width: 140px;
  height: 175px;
  object-fit: cover;
  border-radius: 16px;
  border: 2px solid #ffb6c1;
}
.upload-tip {
  font-size: 11px;
  color: #a69888;
  margin-top: 5px;
  line-height: 1.3;
}
</style>
