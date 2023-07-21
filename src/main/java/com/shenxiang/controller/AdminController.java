package com.shenxiang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.shenxiang.pojo.Admin;
import com.shenxiang.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    //查询信息
    @GetMapping("/select/admin")
    public List<Admin> selectAdmin(@RequestParam("name") String username){
        QueryWrapper<Admin> wrapper=new QueryWrapper<>();
        wrapper.eq("username",username);
        List<Admin> list = adminService.list(wrapper);
        return list;
    }

    //保存管理员的信息
    @PostMapping("/set/admin")
    public boolean setAdmin(@RequestParam("email") String email,
                         @RequestParam("nickname") String nickname,
                         @RequestParam("mineinc") String mineinc,
                         @RequestParam("school") String school,
                         @RequestParam("addr") String addr,
                         @RequestParam("phone") String phone,
                         @RequestParam("mineinfo") String mineinfo,
                         @RequestParam("username") String username){
        if(email!=""&&nickname!=""&&mineinc!=""
                &&school!=""&&addr!=""&&phone!=""&&mineinfo!=""
        &&email!=null&&nickname!=null&&mineinc!=null
                &&school!=null&&addr!=null&&phone!=null&&mineinfo!=null){
            UpdateWrapper<Admin> wrapper=new UpdateWrapper<>();
            wrapper.eq("username",username);
            wrapper.set("email",email)
                    .set("nickname",nickname)
                    .set("mineinc",mineinc)
                    .set("school",school)
                    .set("addr",addr)
                    .set("phone",phone)
                    .set("mineinfo",mineinfo);
            boolean update = adminService.update(wrapper);
            return update;
        }else {
            return false;
        }

    }
}
