package com.shenxiang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shenxiang.mapper.UserMapper;
import com.shenxiang.pojo.User;
import com.shenxiang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl  extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    //查询所有用户
    @Override
    public List<User> selectAllUser() {
        return userMapper.selectAllUser();
    }

    //添加
    @Override
    public void addUser(User user) {
        userMapper.addUser(user);
    }

    //根据用户名批量查询
    @Override
    public List<User> selectUserByName(String name) {
        return userMapper.selectUserByName(name);
    }

    //修改
    @Override
    public void updataUser(User user) {
        userMapper.updataUser(user);
    }

}
