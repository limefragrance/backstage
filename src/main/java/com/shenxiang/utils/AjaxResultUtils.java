package com.shenxiang.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AjaxResultUtils {

    private static final int SUCCESS_CODE =1;
    private static final int FAIL_CODE=0;
    private int code;
    private String message;
    private Object data;

    public static AjaxResultUtils success(Object data){
        return new AjaxResultUtils(SUCCESS_CODE,"成功",data);
    }

    public static AjaxResultUtils fail(String message){
        return new AjaxResultUtils(FAIL_CODE,"失败",null);
    }
}
