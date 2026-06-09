package com.hwadee.cdutproject.controller;

import com.hwadee.cdutproject.entity.Users;
import com.hwadee.cdutproject.mapper.UsersMapper;
import com.hwadee.cdutproject.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsersController {
    @Autowired
    private UsersService usersservice;
    @Autowired
    private UsersMapper mapper;

    @GetMapping("/demo")
    public List<Users> demo() {
        return mapper.selectList(null);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users users){
        boolean login = usersservice.login(users);
        return login ? "登录成功" : "登录失败";
    }

    // ==================== 新增注册接口 ====================
    @PostMapping("/register")
    public String register(@RequestBody Users users) {
        boolean success = usersservice.register(users);
        return success ? "注册成功" : "注册失败，用户名可能已存在";
    }
}