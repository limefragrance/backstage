package com.shenxiang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.shenxiang.pojo.Verify;
import com.shenxiang.service.VerifyService;
import com.shenxiang.utils.MD5Utils;
import com.shenxiang.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/login")
public class VerifyController {

    @Autowired
    private VerifyService verifyService;

    //登录验证码
    @PostMapping("/verify")
    public String login(@RequestBody Verify verify,HttpServletRequest request) throws JsonProcessingException {
        Object rightCode = request.getSession().getAttribute("rightCode");
        QueryWrapper<Verify> wrapper = new QueryWrapper<>();
        wrapper.eq("username", verify.getUsername());
        Verify one = verifyService.getOne(wrapper);
        String password = one.getPassword();
        //密码验证
        boolean b = MD5Utils.verifySaltPassword(verify.getPassword(), password);
        //判断验证码是否正确
        if (rightCode.equals(verify.getCode().toUpperCase())
                || rightCode.equals(verify.getCode().toLowerCase())) {
            if (b) {
                QueryWrapper<Verify> wrapper1 = new QueryWrapper<>();
                wrapper1.eq("username", verify.getUsername());
                long count = verifyService.count(wrapper1);
                if (count == 1) {
                    //包装token
                    Verify verify1 = new Verify();
                    verify1.setUsername(verify.getUsername());
                    verify1.setPassword(verify.getPassword());
                    String token = TokenUtils.sign(verify1);
                    return token;
                }
            } else {
                return "用户名或密码错误！";
            }
        }else {
            return "验证码错误！";
        }
        return null;
    }


    @GetMapping("/select/verify/{username}")
    public Verify selectVerify(@PathVariable("username") String username){
        QueryWrapper<Verify> wrapper=new QueryWrapper<>();
        wrapper.eq("username",username);
        Verify one = verifyService.getOne(wrapper);
        return one;
    }

    @PostMapping("/updata/pas")
    public Boolean updataPas(@RequestParam("pas") String newpas,@RequestParam("username") String username){
        String s = MD5Utils.generateSaltPassword(newpas);
        UpdateWrapper<Verify> wrapper=new UpdateWrapper<>();
        wrapper.eq("username",username);
        wrapper.set("password",s);
        boolean update = verifyService.update(wrapper);
        return update;
    }
}
