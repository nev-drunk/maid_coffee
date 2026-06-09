package com.hwadee.cdutproject.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@TableName("user")
public class User implements Serializable {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String realName;
    private String email;



}
