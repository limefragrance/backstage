package com.shenxiang.utils;




public class MailCodeUtils {
    /**
     * @Description生成6位随机验证码
     * @return
     */
    public static String getCode(){
        String str = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String code = "";
        for(int i= 0;i<6;i++){
            int index = (int)(Math.random()*str.length());
            code+=str.charAt(index);
        }
        return code;
    }

}
