package com.shenxiang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Commodity {

    @TableId(type=IdType.AUTO)
    private Integer id;
    private String pname;
    private String pmodel;
    private String ptype;
    private Integer pinventory;
    private Integer pyear;
    private Integer pmonth;
    private Integer pday;
}
