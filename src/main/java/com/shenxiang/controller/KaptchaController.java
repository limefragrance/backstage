package com.shenxiang.controller;

import cn.hutool.core.codec.Base64;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class KaptchaController {
    //验证码工具
    @Autowired
    DefaultKaptcha defaultKaptcha;

    @RequestMapping(value = "/code")
    public String defaultKaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");
        response.addHeader("Cache-Control", "post-check = 0,pre-check =0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        String createText = defaultKaptcha.createText();

        request.getSession().setAttribute("rightCode", createText);
//        System.out.println("验证码内容:" + (String) request.getSession().getAttribute("rightCode"));
        BufferedImage image = defaultKaptcha.createImage(createText);

        // 转为Base64
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", stream);
        String imgString = "data:image/gif;base64," + Base64.encode(stream.toByteArray());
//        System.out.println(imgString);
        stream.flush();
        stream.close();
        // 将数据存入并将其返回
        return imgString;
    }
}
