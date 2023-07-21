package com.shenxiang.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shenxiang.pojo.Activity;
import com.shenxiang.pojo.User;
import com.shenxiang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    //查询
    @ResponseBody
    @GetMapping("/user")
    public List<User> selectUser(){
        List<User> list = userService.list();
//        System.out.println(list);
        return list;
    }

    //根据用户名批量查询
    @ResponseBody
    @GetMapping("/search")
    public List<User> selectUserByName(@RequestParam(value="name") String name){
        List<User> list=userService.selectUserByName(name);
//        System.out.println(list);
        return list;
    }

    //分页查询
    @ResponseBody
    @GetMapping("/user/page")
    public Page<User> selectUserByPage(@RequestParam(value="current",defaultValue = "1") Integer pn){
        //构造分页参数
        Page<User> page = new Page<>(pn, 11);
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //调用page进行分页
        Page<User> pages = userService.page(page,wrapper);
        return pages;
    }


    //添加
    @ResponseBody
    @PostMapping("/addUser")
    public String addUser(@RequestBody User user){
        if(user.getName()!=""&&user.getAge()!=null
                &&user.getGender()!=""
                &&user.getBirthday()!=null
                &&user.getAddr()!=""){
            userService.addUser(user);
            return "添加成功！";
        }else {
            return "请填写正确内容！";
        }
    }

    //修改
    @ResponseBody
    @PostMapping("/updataUser")
    public String updataUser(@RequestBody User user){
        if(user.getName()!=""&&user.getAge()!=null
                &&user.getGender()!=""
                &&user.getBirthday()!=null
                &&user.getAddr()!=""){
            userService.updataUser(user);
            return "修改成功！";
        }else {
            return "请填写正确内容！";
        }
    }

    //删除
    @ResponseBody
    @GetMapping("/deleteUser")
    public void deleteUser(@RequestParam("userId") Integer id){
        userService.removeById(id);
    }
}
