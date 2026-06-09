package com.hwadee.cdutproject.service;

import com.hwadee.cdutproject.entity.Users;

public interface UsersService {

    // 你原本可能有的登录方法
    boolean login(Users users);

    // ==================== 新增注册接口方法 ====================
    boolean register(Users users);
}