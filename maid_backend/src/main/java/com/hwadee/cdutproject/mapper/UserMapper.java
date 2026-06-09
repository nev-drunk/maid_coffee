package com.hwadee.cdutproject.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hwadee.cdutproject.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<User> selectByCondition(@Param("user") User user);

    int saveBean(User user);

    int update(User user);

    int deleteById(int id);

    int insertList(@Param("users") List<User> users);
}
