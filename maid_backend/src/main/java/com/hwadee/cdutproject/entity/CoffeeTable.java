package com.hwadee.cdutproject.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import java.io.Serializable;

@Data
@ToString
@TableName("coffee_table")
public class CoffeeTable implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String tableNumber; // 桌号
    private Integer capacity;    // 容纳人数
    private String status;       // 状态: 空闲中 / 侍奉中
    private String currentMaid;  // 当前侍奉女仆
    private String memo;         // 备注
}