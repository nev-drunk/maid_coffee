package com.hwadee.cdutproject.controller;

import com.hwadee.cdutproject.entity.MagicMenu;
import com.hwadee.cdutproject.mapper.MagicMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MagicMenuMapper magicMenuMapper;

    // 1. 获取魔法菜单列表
    @GetMapping("/list")
    public List<MagicMenu> getMenuList() {
        return magicMenuMapper.selectList(null);
    }

    // 2. 研发了新料理 (添加)
    @PostMapping("/add")
    public String addDish(@RequestBody MagicMenu menu) {
        int rows = magicMenuMapper.insert(menu);
        return rows > 0 ? "新料理成功注入魔法！🪄" : "研发失败";
    }

    // 3. 修改料理价格或售罄状态 (修改)
    @PutMapping("/update")
    public String updateDish(@RequestBody MagicMenu menu) {
        int rows = magicMenuMapper.updateById(menu);
        return rows > 0 ? "料理信息调整成功~" : "修改失败";
    }

    // 4. 下架不再制作的料理 (删除)
    @DeleteMapping("/delete/{id}")
    public String deleteDish(@PathVariable Integer id) {
        int rows = magicMenuMapper.deleteById(id);
        return rows > 0 ? "料理已从魔法书抹去~" : "删除失败";
    }
}