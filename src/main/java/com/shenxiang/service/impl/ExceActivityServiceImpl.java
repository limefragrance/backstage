package com.shenxiang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shenxiang.mapper.ActivityMapper;
import com.shenxiang.pojo.Activity;
import com.shenxiang.service.ExcelActivityService;
import org.springframework.stereotype.Service;

@Service
public class ExceActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ExcelActivityService {
}
