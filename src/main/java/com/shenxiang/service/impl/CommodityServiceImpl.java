package com.shenxiang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shenxiang.mapper.CommodityMapper;
import com.shenxiang.pojo.Commodity;
import com.shenxiang.service.CommodityService;
import org.springframework.stereotype.Service;

@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements CommodityService {

}
