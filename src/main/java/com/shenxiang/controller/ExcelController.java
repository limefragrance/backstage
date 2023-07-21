package com.shenxiang.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.shenxiang.pojo.Activity;
import com.shenxiang.pojo.Commodity;
import com.shenxiang.service.ExcelActivityService;
import com.shenxiang.service.ExcelCommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class ExcelController {
    @Autowired
    private ExcelActivityService excelActivityService;

    @Autowired
    private ExcelCommodityService excelCommodityService;

    //活动表导出
    @GetMapping("/excel/export/activity")
    public void exportActivity(HttpServletResponse response) throws Exception {
        //从数据库查询所有数据
        List<Activity> excelActivities = excelActivityService.list();
        System.out.println(excelActivities);
        //在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("id", "ID");
        writer.addHeaderAlias("actName", "活动名称");
        writer.addHeaderAlias("startTime", "开始时间");
        writer.addHeaderAlias("endTime", "结束时间");
        writer.addHeaderAlias("gift", "礼品");
        writer.addHeaderAlias("inventory", "库存");
        writer.addHeaderAlias("state", "是否开始");
        //一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(excelActivities, true);
        //设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
        String fileName = URLEncoder.encode("活动信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
        //输出流
        ServletOutputStream out = response.getOutputStream();
        //刷新到输出流
        writer.flush(out, true);
        out.close();
        writer.close();
    }

    //活动表导入
    @PostMapping("/excel/import/activity")
    public void importExcel(MultipartFile file) throws Exception {
        //1.第一种 头必须和实体(英文)一样
        //文件处理成io流
        InputStream in = file.getInputStream();
        //io流给ExcelReader
        ExcelReader excelReader=ExcelUtil.getReader(in);
        //读取数据且转化为list 要求表头为中文
        //List<User> list = excelReader.readAll(User.class);
        //2.第二种导入方式
        //忽略第一行头(第一行是中文的情况),直接读取表的内容
        List<List<Object>> list = excelReader.read(1);
        List<Activity> activities = CollUtil.newArrayList();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        for (List<Object> row: list) {
            Activity activity=new Activity();
            activity.setActName(row.get(1).toString());
            activity.setStartTime(formatter.parse(row.get(2).toString()));
            activity.setEndTime(formatter.parse(row.get(3).toString()));
            activity.setGift(row.get(4).toString());
            activity.setInventory(Integer.valueOf(row.get(5).toString()));
            activity.setState(Boolean.valueOf(row.get(6).toString()) );
            activities.add(activity);
        }
        //批量注册进数据库
        excelActivityService.saveBatch(activities);
    }

    //商品表导出
    @GetMapping("/excel/export/commodity")
    public void exportCommodity(HttpServletResponse response) throws Exception {
        //从数据库查询所有数据
        List<Commodity> excelActivities = excelCommodityService.list();
        System.out.println(excelActivities);
        //在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("id", "ID");
        writer.addHeaderAlias("pname", "品牌名称");
        writer.addHeaderAlias("pmodel", "型号");
        writer.addHeaderAlias("ptype", "类型");
        writer.addHeaderAlias("pinventory", "库存");
        writer.addHeaderAlias("pyear", "年销量");
        writer.addHeaderAlias("pmonth", "月销量");
        writer.addHeaderAlias("pday", "日销量");
        //一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(excelActivities, true);
        //设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
        String fileName = URLEncoder.encode("商品信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
        //输出流
        ServletOutputStream out = response.getOutputStream();
        //刷新到输出流
        writer.flush(out, true);
        out.close();
        writer.close();
    }

    //商品表导入
    @PostMapping("/excel/import/commodity")
    public void importCommodity(MultipartFile file) throws Exception {
        //1.第一种 头必须和实体(英文)一样
        //文件处理成io流
        InputStream in = file.getInputStream();
        //io流给ExcelReader
        ExcelReader excelReader=ExcelUtil.getReader(in);
        //读取数据且转化为list 要求表头为中文
        //List<User> list = excelReader.readAll(User.class);
        //2.第二种导入方式
        //忽略第一行头(第一行是中文的情况),直接读取表的内容
        List<List<Object>> list = excelReader.read(1);
        List<Commodity> commodities = CollUtil.newArrayList();
        for (List<Object> row: list) {
            Commodity commodity=new Commodity();
            commodity.setPname(row.get(1).toString());
            commodity.setPmodel(row.get(2).toString());
            commodity.setPtype(row.get(3).toString());
            commodity.setPinventory(Integer.valueOf(row.get(4).toString()));
            commodity.setPyear(Integer.valueOf(row.get(5).toString()));
            commodity.setPmonth(Integer.valueOf(row.get(6).toString()));
            commodity.setPday(Integer.valueOf(row.get(7).toString()));
            commodities.add(commodity);
        }
        //批量注册进数据库
        excelCommodityService.saveBatch(commodities);
    }
}
