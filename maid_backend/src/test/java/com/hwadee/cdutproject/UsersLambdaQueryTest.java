package com.hwadee.cdutproject;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hwadee.cdutproject.entity.Users;
import com.hwadee.cdutproject.mapper.UsersMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UsersLambdaQueryTest {

    @Autowired
    private UsersMapper usersMapper;

    @Test
    public void testLambdaQueryWrapper() {
        System.out.println("========== LambdaQueryWrapper 查询 users 表 ==========");

        System.out.println("\n【示例1】两个条件：查询年龄>=20 且 年龄<=25 的用户");
        LambdaQueryWrapper<Users> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.ge(Users::getAge, 20)
                .le(Users::getAge, 25);
        List<Users> users1 = usersMapper.selectList(wrapper1);
        System.out.println("查询结果: " + users1);
        System.out.println("结果数量: " + users1.size());

        System.out.println("\n【示例2】两个条件：查询年龄在20-30岁之间 且 邮箱包含'qq.com' 的用户");
        LambdaQueryWrapper<Users> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.between(Users::getAge, 20, 30)
                .like(Users::getEmail, "qq.com");
        List<Users> users2 = usersMapper.selectList(wrapper2);
        System.out.println("查询结果: " + users2);
        System.out.println("结果数量: " + users2.size());

        System.out.println("\n【示例3】三个条件：查询用户名='李四' 且 年龄=25 且 未删除的用户");
        LambdaQueryWrapper<Users> wrapper3 = new LambdaQueryWrapper<>();
        wrapper3.eq(Users::getUsername, "李四")
                .eq(Users::getAge, 25)
                .eq(Users::getIsDeleted, 0);
        List<Users> users3 = usersMapper.selectList(wrapper3);
        System.out.println("查询结果: " + users3);
        System.out.println("结果数量: " + users3.size());

        System.out.println("\n【示例4】复杂条件：查询年龄>20 且 (邮箱包含'qq' 或 邮箱包含'163') 的用户");
        LambdaQueryWrapper<Users> wrapper4 = new LambdaQueryWrapper<>();
        wrapper4.gt(Users::getAge, 20)
                .and(w -> w.like(Users::getEmail, "qq").or().like(Users::getEmail, "163"));
        List<Users> users4 = usersMapper.selectList(wrapper4);
        System.out.println("查询结果: " + users4);
        System.out.println("结果数量: " + users4.size());

        System.out.println("\n【示例5】指定字段 + 排序：只查询用户名、年龄和邮箱，按年龄降序");
        LambdaQueryWrapper<Users> wrapper5 = new LambdaQueryWrapper<>();
        wrapper5.select(Users::getUsername, Users::getAge, Users::getEmail)
                .gt(Users::getAge, 20)
                .orderByDesc(Users::getAge);
        List<Users> users5 = usersMapper.selectList(wrapper5);
        System.out.println("查询结果: " + users5);
        System.out.println("结果数量: " + users5.size());

        System.out.println("\n【示例6】链式调用：查询年龄>=25 且 未删除的用户，按创建时间降序");
        List<Users> users6 = usersMapper.selectList(
                new LambdaQueryWrapper<Users>()
                        .ge(Users::getAge, 25)
                        .eq(Users::getIsDeleted, 0)
                        .orderByDesc(Users::getCreateTime)
        );
        System.out.println("查询结果: " + users6);
        System.out.println("结果数量: " + users6.size());

        System.out.println("\n========== LambdaQueryWrapper 查询完成 ==========");
    }

}
