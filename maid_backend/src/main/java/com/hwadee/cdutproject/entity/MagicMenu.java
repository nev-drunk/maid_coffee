package com.hwadee.cdutproject.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString
@TableName("magic_menu")
public class MagicMenu implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String dishName;     // 料理名称
    private BigDecimal price;    // 价格
    private String category;     // 分类
    private Integer status;      // 状态 1-在售 0-售罄
    private String magicEffect;  // 魔法寄语
}