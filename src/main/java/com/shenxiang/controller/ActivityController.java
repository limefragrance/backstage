package com.shenxiang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shenxiang.pojo.Activity;
import com.shenxiang.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @GetMapping("/activity")
    public List<Activity> activityList(){
        List<Activity> list = activityService.list();
        return list;
    }

    @GetMapping("/search/activity")
    public List<Activity> searchActity(@RequestParam("actname") String actname){
        QueryWrapper<Activity> wrapper=new QueryWrapper<>();
        wrapper.like("act_name",actname);
        List<Activity> list =activityService.list(wrapper);
        return list;
    }

    @PostMapping("/add/activity")
    public String addActity(@RequestBody Activity activity){
        if(activity.getActName()!=""&&activity.getStartTime()!=null
        &&activity.getEndTime()!=null&&activity.getGift()!=""
        &&activity.getInventory()!=null){
            activityService.save(activity);
            return "添加成功！";
        }else{
            return "请填写内容！";
        }
    }

    //开关
    @PostMapping("/change/switch")
    public String changeSwitch(@RequestParam("state") Boolean state,@RequestParam("id") Integer id){
        UpdateWrapper<Activity> wrapper=new UpdateWrapper<>();
        wrapper.eq("id",id);
        wrapper.set("state",state);
        boolean update = activityService.update(wrapper);
        if(update){
            return "改变成功！";
        }else {
            return "改变失败！";
        }
    }

    //根据id查询
    @GetMapping("/select/{id}")
    public List<Activity> selectById(@PathVariable("id") Integer id){
        QueryWrapper<Activity> wrapper=new QueryWrapper<>();
        wrapper.eq("id",id);
        List<Activity> list = activityService.list(wrapper);
        return list;
    }

    //编辑
    @PostMapping("/updata")
    public String updata(@RequestBody Activity activity){
        if(activity.getActName()!=""&&activity.getStartTime()!=null
                &&activity.getEndTime()!=null&&activity.getGift()!=""
                &&activity.getInventory()!=null){
            activityService.updateById(activity);
            return "编辑成功！";
        }else{
            return "请填写内容！";
        }
    }

    //根据id删除
    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Integer id){
        boolean b = activityService.removeById(id);
        if(b){
            return "删除成功！";
        }else {
            return "删除失败！";
        }
    }

    //批量删除
    @DeleteMapping("/delete/batch/{ids}")
    public String deleteBatchById(@PathVariable("ids") ArrayList<Integer> ids){
        boolean b = activityService.removeBatchByIds(ids);
        if(b){
            return "批量删除成功！";
        }else {
            return "批量删除失败！";
        }
    }

    //分页功能
    @GetMapping("/activity/page")
    public Page<Activity> selectActivityByPage(@RequestParam(value="current",defaultValue = "1") Integer pn){
        //构造分页参数
        Page<Activity> page = new Page<>(pn, 11);
        QueryWrapper<Activity> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //调用page进行分页
        Page<Activity> pages = activityService.page(page,wrapper);
        return pages;
    }

}
