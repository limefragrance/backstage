package com.shenxiang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shenxiang.mapper.VerifyMapper;
import com.shenxiang.pojo.Verify;
import com.shenxiang.service.VerifyService;
import org.springframework.stereotype.Service;

@Service
public class VerifyServiceImpl extends ServiceImpl<VerifyMapper, Verify> implements VerifyService {
}
