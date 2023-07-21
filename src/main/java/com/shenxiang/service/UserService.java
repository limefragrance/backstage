package com.shenxiang.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.shenxiang.pojo.User;

import java.util.List;

public interface UserService extends IService<User> {
    //查询所有用户
    List<User> selectAllUser();

    //添加
    void addUser(User user);

    //根据用户名批量查询
    List<User> selectUserByName(String name);

    //修改
    void updataUser(User user);

}
