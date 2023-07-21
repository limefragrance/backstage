package com.shenxiang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Verify {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String username;
    private String password;

    @TableField(exist = false)
    private String code;
}
