package com.hwadee.cdutproject.controller;

import com.hwadee.cdutproject.entity.CoffeeTable;
import com.hwadee.cdutproject.mapper.CoffeeTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
public class TableController {

    @Autowired
    private CoffeeTableMapper coffeeTableMapper;

    // 1. 查询所有桌位状态
    @GetMapping("/list")
    public List<CoffeeTable> getAllTables() {
        return coffeeTableMapper.selectList(null);
    }

    // 2. 迎来新桌位 (添加)
    @PostMapping("/add")
    public String addTable(@RequestBody CoffeeTable table) {
        int rows = coffeeTableMapper.insert(table);
        return rows > 0 ? "新桌位添加成功喵！" : "添加失败qwq";
    }

    // 3. 改变桌位状态/更换侍奉女仆 (修改)
    @PutMapping("/update")
    public String updateTable(@RequestBody CoffeeTable table) {
        int rows = coffeeTableMapper.updateById(table);
        return rows > 0 ? "桌位状态更新成功✨" : "更新失败，找不到该桌位";
    }

    // 4. 撤走桌位 (删除)
    @DeleteMapping("/delete/{id}")
    public String deleteTable(@PathVariable Integer id) {
        int rows = coffeeTableMapper.deleteById(id);
        return rows > 0 ? "桌位已成功撤走~" : "删除失败";
    }
}