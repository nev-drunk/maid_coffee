package com.hwadee.cdutproject.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ToString
@TableName("master_vip")
public class MasterVip implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;       // 关联用户账号ID
    private String masterName;    // 主人尊称
    private String vipLevel;      // 会员等级
    private Integer lovePoints;   // 好感度积分
    private LocalDateTime registerTime; // 回屋时间
}