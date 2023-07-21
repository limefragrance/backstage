package com.shenxiang.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shenxiang.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    //查询所有用户
    List<User> selectAllUser();

    //添加
    void addUser(User user);

    //根据用户名批量查询
    List<User> selectUserByName(String name);

    //修改
    void updataUser(User user);

}
