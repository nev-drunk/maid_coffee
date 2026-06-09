package com.hwadee.cdutproject.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@ToString
@TableName("maid_schedule")
public class MaidSchedule implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String maidCode;     // 女仆专属工号 (例如: MAID001)
    private String maidName;     // 女仆名字 (例如: 蕾姆)
    private LocalDate workDate;  // 执勤日期
    private String shift;        // 班次
    private String status;       // 状态
    private String avatarUrl;
}