package com.hwadee.cdutproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwadee.cdutproject.entity.Users;
import com.hwadee.cdutproject.mapper.UsersMapper;
import com.hwadee.cdutproject.service.UsersService;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Override
    public boolean login(Users users){
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getUsername, users.getUsername());
        Users bean = baseMapper.selectOne(wrapper);
        if(bean != null && bean.getPassword().equals(users.getPassword())){
            return true;
        }
        return false;
    }

    @Override
    public boolean register(Users users) {
        // 1. 健壮性校验：用户名或密码为空则直接返回失败
        if (users.getUsername() == null || users.getUsername().trim().isEmpty() ||
                users.getPassword() == null || users.getPassword().trim().isEmpty()) {
            return false;
        }

        // 2. 检查数据库中是否已存在该用户名（统一改成你 login 方法里的 Lambda 优雅写法）
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getUsername, users.getUsername());
        Users existingUser = baseMapper.selectOne(wrapper);

        if (existingUser != null) {
            // 用户名已存在，拒绝注册
            return false;
        }

        // 3. 执行插入操作（利用继承来自带的 baseMapper 执行插入）
        int rows = baseMapper.insert(users);

        // 插入成功会影响 1 行数据
        return rows > 0;
    }
}