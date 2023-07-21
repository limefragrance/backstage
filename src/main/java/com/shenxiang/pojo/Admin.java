package com.shenxiang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String nickname;
    private String username;
    private String email;
    private String userimg;
    private String school;
    private String phone;
    private String addr;
    private String mineinfo;
    private String mineinc;
}
