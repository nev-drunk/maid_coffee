package com.hwadee.cdutproject;

import com.hwadee.cdutproject.entity.User;
import com.hwadee.cdutproject.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CdutProjectApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Test
    public  void tsetInsert(){
        for(int i=0;i<10;i++){
            User user=new User();
            user.setUsername("boo"+i);
            user.setPassword("555");
            user.setRealName("pig"+i);
            userMapper.insert(user);
        }
    }

//    @Test
//    void contextLoads() {
//        System.out.println("========== 开始测试二级缓存功能 ==========");
//
//        User user = new User();
//        user.setUsername("a");
//        user.setPassword("123");
//
//        System.out.println("\n【第1次查询】相同条件: username='a', password='123'");
//        System.out.println("预期: 访问数据库，并将结果存入二级缓存");
//        List<User> users1 = userMapper.selectByCondition(user);
//        System.out.println("查询结果: " + users1);
//        System.out.println("结果数量: " + users1.size());
//
//        System.out.println("\n【第2次查询】相同条件: username='a', password='123'");
//        System.out.println("预期: 从二级缓存中获取，不访问数据库（缓存命中）");
//        List<User> users2 = userMapper.selectByCondition(user);
//        System.out.println("查询结果: " + users2);
//        System.out.println("结果数量: " + users2.size());
//
//        System.out.println("\n【第3次查询】相同条件: username='a', password='123'");
//        System.out.println("预期: 继续从二级缓存中获取（缓存命中）");
//        List<User> users3 = userMapper.selectByCondition(user);
//        System.out.println("查询结果: " + users3);
//        System.out.println("结果数量: " + users3.size());
//
//        System.out.println("\n========== 测试完成 ==========");
//        System.out.println("说明: 查看控制台日志中的 'Cache Hit Ratio' 指标");
//        System.out.println("- 第1次查询后，命中率约为 0.0 (0/1)");
//        System.out.println("- 第2次查询后，命中率约为 0.5 (1/2)");
//        System.out.println("- 第3次查询后，命中率约为 0.67 (2/3)");
//        System.out.println("命中率越高，说明二级缓存效果越好！");
//    }
//// ... existing code ...
//    @Test
//public void testInsert(){
//    User user = new User();
//    user.setUsername("bb");
//    user.setPassword("888");
//    user.setRealName("小肥");
//    int count = userMapper.saveBean(user);
//    System.out.println(count);
//}
//@Test
//public void testUpdate(){
//    User user = new User();
//    user.setId(2);
//    user.setUsername("kk");
//    user.setPassword("666");
//    user.setRealName("康神");
//    int count = userMapper.update(user);
//    System.out.println(count);
//}
//@Test
//    public void testDelete(){
//    int count = userMapper.deleteById(3);
//    System.out.println(count);
//}
//    // ... existing code ...
//    @Test
//    public void testInsertList(){
//        List<User> users = new ArrayList<>();
//        for(int i = 0; i < 10; i++){
//            User user = new User();
//            user.setUsername("body" + i);
//            user.setPassword("520" + i);
//            user.setRealName("王森旭" + i);
//            users.add(user);
//        }
//        int count = userMapper.insertList(users);
//        System.out.println("批量插入成功，共插入 " + count + " 条记录");
//    }

}


