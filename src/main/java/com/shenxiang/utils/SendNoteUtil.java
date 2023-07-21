package com.shenxiang.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SendNoteUtil {

    //验证平台信息 开发者无需任何更改
    private static final String dysmsapi = "dysmsapi.aliyuncs.com";

    DefaultProfile profile = DefaultProfile.getProfile("default", "LTAI5tGCNSbcAWM9MqrCCq3t", "1YiD19Rx1vuHctNqX41kGH6OqR8GNQ");
    IAcsClient client = new DefaultAcsClient(profile);
    //这一步的两个参数,一个是要发送验证码的手机号 一个是模板Code用来区分登录注册
    public String sendNoteMessgae(String PhoneNumbers,String template){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i=0;i<6;i++){
            sb.append(random.nextInt(10));
        }
        CommonRequest request = new CommonRequest();
        //request.setSysProtocol(ProtocolType.HTTPS);
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(dysmsapi);
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("PhoneNumbers", PhoneNumbers);//接受验证码的手机号
        request.putQueryParameter("SignName", "阿里云短信测试");//签名
        //模板代码,我暂时用的参数,你可以直接写成模板码,模板码参考第八步
        request.putQueryParameter("TemplateCode", template);
        //用户定义的验证码内容
        request.putQueryParameter("TemplateParam","{code:"+sb.toString()+"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            String returnStr = response.getData();
            System.out.println(returnStr);
            JSONObject jsonObject = JSONObject.parseObject(returnStr);
            //返回的信息
            return sb.toString();
        } catch (ServerException e) {
            return e.getErrMsg();
        } catch (ClientException e) {
            return e.getErrMsg();
        }
    }

}
