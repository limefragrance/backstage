package com.shenxiang.controller;

import com.shenxiang.service.MailService;
import com.shenxiang.utils.MailCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;

@RestController
public class VerificationController {
    @Autowired
    private MailService mailService;

    @Resource
    private TemplateEngine templateEngine;

    @GetMapping("/verify")
    public String sendVerificationCode(@RequestParam("email") String email) {
        if(email!=""&&email!=null){
            // 收信人
            String to = email;
            // 邮件消息内容
            String message = "详情：您正在尝试进行登录操作，若非是您本人的行为，请忽略!";
            // 随机生成验证码
            String code = MailCodeUtils.getCode();
            // 设置邮件内容
            Context context = new Context();
            context.setVariable("message", message);
            context.setVariable("code", code);
            String mail = templateEngine.process("mailtemplate.html", context);
            // 发送邮件
            mailService.sendHtmlMail(to, "邮箱验证码", mail);
            return code;
        }else{
            return "请填写邮箱账户！";
        }

    }
}
