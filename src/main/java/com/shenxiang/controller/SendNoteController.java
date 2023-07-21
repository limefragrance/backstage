package com.shenxiang.controller;

import com.shenxiang.utils.SendNoteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class SendNoteController {

    @Autowired
    private SendNoteUtil sendNoteUtil;

    @GetMapping( "/sendNote")
    public void sendNote(@RequestParam("phone") String phone, HttpServletResponse response){
        String template = "SMS_154950909";
        try {
            response.getWriter().write(sendNoteUtil.sendNoteMessgae(phone,template));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
