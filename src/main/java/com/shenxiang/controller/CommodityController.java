package com.shenxiang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shenxiang.pojo.Commodity;
import com.shenxiang.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping("/commodity")
public class CommodityController {

    @Autowired
    private CommodityService commodityService;

    //首页表格数据
    @GetMapping("/getDesc")
    public List<Commodity> getDesc(){
        QueryWrapper<Commodity> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("pyear")
                .orderByDesc("pmonth")
                .orderByDesc("pday")
                .last("limit 0 , 9");
        List<Commodity> list = commodityService.list(wrapper);
        return list;
    }

    //获取统计数据
    @GetMapping("/getStatistics")
    public Map<String,Integer> getStatistics(){
        Map<String,Integer> map=new HashMap<>();
        Commodity commodity;
        commodity = new Commodity();
        String[] str1={"allMate","allP","allNova","allEnjoy","allHonor","allY"};
        String[] str={"Mate系列","P系列","Nova系列","畅享系列","Honor系列","Y系列"};
        for(int i=0;i<str.length;i++){
            QueryWrapper<Commodity>  wrapper=new QueryWrapper<>();
            wrapper.eq("pname",str[i]);
            List<Commodity> list = commodityService.list(wrapper);
            Integer all=0;
            for(int j=0;j<list.size();j++){
                commodity=list.get(j);
                all+=commodity.getPyear();
            }
            map.put(str1[i],all);
            all=0;
        }
        return map;
    }

    //折线图数据
    @GetMapping("/linechart")
    public List<Integer[]> linechart(){
        List<Integer[]> allList=new ArrayList<>();
        Commodity commodity;
        commodity = new Commodity();
        String[] str={"Mate系列","P系列","Nova系列","畅享系列","Honor系列","Y系列"};
        for(int i=0;i<str.length;i++){
            QueryWrapper<Commodity>  wrapper=new QueryWrapper<>();
            wrapper.eq("pname",str[i]);
            Integer d=0,m=0,y=0;
            List<Commodity> list = commodityService.list(wrapper);
            for(int j=0;j<list.size();j++){
                commodity=list.get(j);
                y+=commodity.getPyear();
                m+=commodity.getPmonth();
                d+=commodity.getPday();
            }
            Integer[] num={d,m,y};
            allList.add(num);
            y=0;m=0;d=0;
        }
        return allList;
    }


    //总库存剩余量
    @GetMapping("/all/inventory")
    public Map<String,Integer> allInventory(){
        QueryWrapper<Commodity> wrapper=new QueryWrapper<>();
        wrapper.select("pinventory","pyear","pmonth","pday");
        List<Commodity> list=commodityService.list(wrapper);
        int allInventory=0;
        int allYear=0;
        int allMonth=0;
        int allDay=0;
        Commodity commodity;
        commodity = new Commodity();
        for(int i=0;i<list.size();i++){
            commodity=list.get(i);
            allInventory+=commodity.getPinventory();
            allYear+=commodity.getPyear();
            allMonth+=commodity.getPmonth();
            allDay+=commodity.getPday();
        }
        Map<String,Integer> map=new HashMap<>();
        map.put("allInventory",allInventory);
        map.put("allYear",allYear);
        map.put("allMonth",allMonth);
        map.put("allDay",allDay);
        return map;
    }

    //分页查询
    @GetMapping("/page")
    public Page<Commodity> selectCommodityByPage(@RequestParam(value="current",defaultValue = "1") Integer pn){
        //构造分页参数
        Page<Commodity> page = new Page<>(pn, 10);
        QueryWrapper<Commodity> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //调用page进行分页
        Page<Commodity> pages = commodityService.page(page,wrapper);
        return pages;
    }

    //搜索
    @GetMapping("/condition/get")
    public Page<Commodity> getCondition(@RequestParam(value="current",defaultValue = "1") Integer pn,
                                        @RequestParam("value1") String pname,
                                        @RequestParam("value2") String pmodel,
                                        @RequestParam("value3") String ptype){
        //构造分页参数
        Page<Commodity> page = new Page<>(pn, 10);
        QueryWrapper<Commodity> wrapper=new QueryWrapper<>();
        if((pname!=""&&pmodel==""&&ptype=="")
                ||(pname==""&&pmodel!=""&&ptype=="")
                ||(pname==""&&pmodel==""&&ptype!="")){
            wrapper.eq("pname",pname)
                    .or().eq("pmodel",pmodel)
                    .or().eq("ptype",ptype);
        }
        if((pname==""&&pmodel!=""&&ptype!="")
                ||(pname!=""&&pmodel==""&&ptype!="")
                ||(pname!=""&&pmodel!=""&&ptype=="")){
            wrapper.eq("pname",pname).eq("pmodel",pmodel)
                    .or().eq("pname",pname).eq("ptype",ptype)
                    .or().eq("pmodel",pmodel).eq("ptype",ptype);
        }
        if(pname!=""&&pmodel!=""&&ptype!=""){
            wrapper.eq("pname",pname).eq("pmodel",pmodel).eq("ptype",ptype);
        }
        //调用page进行分页
        Page<Commodity> pages = commodityService.page(page,wrapper);
        return pages;
    }

    //添加商品
    @PostMapping("/add/commodity")
    public String addCommodity(@RequestBody Commodity commodity){
        if(commodity.getPname()!=""&&commodity.getPmodel()!=""
        &&commodity.getPtype()!=""&&commodity.getPinventory()!=null
        &&commodity.getPyear()!=null&&commodity.getPmonth()!=null
                &&commodity.getPday()!=null){
            //根据pname，pmodel，ptype 查询这一列是否在数据库中，如果在添加，否则进行修改
            QueryWrapper<Commodity> wrapper=new QueryWrapper<>();
            wrapper.eq("pname",commodity.getPname())
                    .eq("pmodel",commodity.getPmodel())
                    .eq("ptype",commodity.getPtype());
            long count = commodityService.count(wrapper);
            //存在
            if(count==1){
                UpdateWrapper<Commodity> updateWrapper=new UpdateWrapper<>();
                updateWrapper.eq("pname",commodity.getPname())
                        .eq("pmodel",commodity.getPmodel())
                        .eq("ptype",commodity.getPtype());
                updateWrapper.set("pinventory",commodity.getPinventory())
                        .set("pyear",commodity.getPyear())
                        .set("pmonth",commodity.getPmonth())
                        .set("pday",commodity.getPday());
                boolean update = commodityService.update(updateWrapper);
                if(update){
                    return "添加成功！";
                }
            }else {
                boolean save = commodityService.save(commodity);
                if(save){
                    return "添加成功！";
                }else{
                    return "添加失败！";
                }
            }
        }else{
            return "请填写正确的内容！";
        }
        return null;
    }

    //根据id查询
    @GetMapping("/get/{id}")
    public List<Commodity> getById(@PathVariable("id") Integer id){
        QueryWrapper<Commodity> wrapper=new QueryWrapper<>();
        wrapper.eq("id",id);
        List<Commodity> list = commodityService.list(wrapper);
        return list;
    }

    //更新
    @PostMapping("/updata")
    public String updata(@RequestBody Commodity commodity){
        if(commodity.getPname()!=""&&commodity.getPmodel()!=""
                &&commodity.getPtype()!=""&&commodity.getPinventory()!=null
                &&commodity.getPyear()!=null&&commodity.getPmonth()!=null
                &&commodity.getPday()!=null){
            commodityService.updateById(commodity);
            return "编辑成功！";
        }else{
            return "请填写内容！";
        }
    }

    //删除
    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Integer id){
        boolean b = commodityService.removeById(id);
        if(b){
            return "删除成功！";
        }else {
            return "删除失败！";
        }
    }

    //批量删除
    @DeleteMapping("/delete/batch/{ids}")
    public String deleteBatchById(@PathVariable("ids") ArrayList<Integer> ids){
        boolean b = commodityService.removeBatchByIds(ids);
        if(b){
            return "批量删除成功！";
        }else {
            return "批量删除失败！";
        }
    }



}
