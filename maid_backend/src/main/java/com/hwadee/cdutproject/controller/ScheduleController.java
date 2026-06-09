package com.hwadee.cdutproject.controller;

import com.hwadee.cdutproject.entity.MaidSchedule;
import com.hwadee.cdutproject.mapper.MaidScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping({"/api/schedules", "/schedules"})
@CrossOrigin // 稳妥起见，允许前端跨域调试
public class ScheduleController {

    @Autowired
    private MaidScheduleMapper maidScheduleMapper;

    // 📸 从配置文件读取本地存储路径，如果未配置，默认存放在 D:/maid/uploads/
    @Value("${maid.upload-folder:D:/maid/uploads/}")
    private String uploadFolder;

    /**
     * 📷 核心升级：真人御影上传接口
     * 呼应前端 <el-upload action="/api/schedules/upload-avatar">
     */
    @PostMapping("/upload-avatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("🍭 主人，上传的照片是空的喵~");
        }

        try {
            // 1. 确保服务器本地文件夹存在
            File folder = new File(uploadFolder);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // 2. 使用 UUID 生成唯一文件名，防止名字重复被覆盖
            String originalFilename = file.getOriginalFilename();
            String suffix = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            } else {
                suffix = ".jpg"; // 兜底后缀
            }
            String newFilename = UUID.randomUUID().toString() + suffix;

            // 3. 把图片从内存传输并保存到本地磁盘中
            File destFile = new File(folder, newFilename);
            file.transferTo(destFile);

            // 4. 构建前端可以直接访问的虚拟网络静态映射 URL
            // 前端拿到这个链接后，会自动回填到 form.avatarUrl 中，随表单一起持久化到数据库
            String avatarUrl = "/" + newFilename;

            // 包装成前端需要的 data 格式返回
            return ResponseEntity.ok().body(new Result(200, avatarUrl));
        } catch (IOException e) {
            return ResponseEntity.status(500).body("🔒 契约结界相片上传失败：" + e.getMessage());
        }
    }

    // 1. 查看所有女仆的排班执勤表
    @GetMapping("/list")
    public List<MaidSchedule> getSchedules() {
        return maidScheduleMapper.selectList(null);
    }

    // 2. 召唤女仆执勤 (添加排班)
    @PostMapping("/add")
    public String addSchedule(@RequestBody MaidSchedule schedule) {
        int rows = maidScheduleMapper.insert(schedule);
        return rows > 0 ? "全新女仆 " + schedule.getMaidName() + " 成功入籍官邸！✨" : "排班失败";
    }

    // 3. 调整执勤状态或换班 (修改)
    @PutMapping("/update")
    public String updateSchedule(@RequestBody MaidSchedule schedule) {
        int rows = maidScheduleMapper.updateById(schedule);
        return rows > 0 ? "女仆御影与时空通告修改成功！📝" : "调整失败";
    }

    // 4. 取消某次执勤安排 (删除)
    @DeleteMapping("/delete/{id}")
    public String deleteSchedule(@PathVariable Integer id) {
        int rows = maidScheduleMapper.deleteById(id);
        return rows > 0 ? "已成功将该女仆的执勤通告从契约中抹去。❌" : "删除失败";
    }

    /**
     * 💡 专为前端 Element Plus 的封装习惯准备的通用返回内部类
     */
    private static class Result {
        private Integer code;
        private Object data;

        public Result(Integer code, Object data) {
            this.code = code;
            this.data = data;
        }

        public Integer getCode() { return code; }
        public void setCode(Integer code) { this.code = code; }
        public Object getData() { return data; }
        public void setData(Object data) { this.data = data; }
    }
}