package com.hwadee.cdutproject.controller;

import com.hwadee.cdutproject.entity.MasterVip;
import com.hwadee.cdutproject.mapper.MasterVipMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vips")
public class VipController {

    @Autowired
    private MasterVipMapper masterVipMapper;

    // 1. 查看所有回过屋的主人档案
    @GetMapping("/list")
    public List<MasterVip> getVipList() {
        return masterVipMapper.selectList(null);
    }

    // 2. 为新主人登记尊称办卡 (添加)
    @PostMapping("/add")
    public String addVip(@RequestBody MasterVip vip) {
        int rows = masterVipMapper.insert(vip);
        return rows > 0 ? "新主人档案建立成功！💖" : "登记失败";
    }

    // 3. 主人消费升级、增加好感度积分 (修改)
    @PutMapping("/update")
    public String updateVip(@RequestBody MasterVip vip) {
        int rows = masterVipMapper.updateById(vip);
        return rows > 0 ? "主人好感度或等级已变更！✨" : "变更失败";
    }

    // 4. 彻底注销某个档案 (删除)
    @DeleteMapping("/delete/{id}")
    public String deleteVip(@PathVariable Integer id) {
        int rows = masterVipMapper.deleteById(id);
        return rows > 0 ? "档案已封存" : "删除失败";
    }
}