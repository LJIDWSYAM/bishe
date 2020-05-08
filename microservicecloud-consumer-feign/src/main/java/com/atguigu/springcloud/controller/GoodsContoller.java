package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.Utils.Page;
import com.atguigu.springcloud.Utils.RedisUtil;
import com.atguigu.springcloud.entities.Goods;
import com.atguigu.springcloud.entities.GoodsAndMiaoShaGoods;
import com.atguigu.springcloud.entities.MiaoshaGoods;
import com.atguigu.springcloud.service.GoodsService;
import com.atguigu.springcloud.vo.GoodsPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/goods")
public class GoodsContoller {

    @Autowired
    GoodsService goodsService;
    @Autowired
    RedisUtil redisUtil;

    @RequestMapping("/showGoods")
    @ResponseBody
    public GoodsPage showGoods(String pageNum) {

        Page page = new Page();

        page.setPageNum(Integer.parseInt(pageNum));//当前的页数
        page.setPageSize(3);//页面显示的记录数


        //查询出goods表中的记录数
        int count = goodsService.getGoodsCount();

        page.setTotalCount(count);

        List<Goods> goods = goodsService.getAllGoodsByPage(page);

        GoodsPage goodsPage = new GoodsPage();//list《goods》和总页数的包装类型

        goodsPage.setGoods(goods);
        goodsPage.setPage(page);

        return goodsPage;
    }

    /*
     * */
    @GetMapping("/miaoshaGoods/{goodsId}")
    public ModelAndView selectMiaoshaGoods(@PathVariable("goodsId") String goodsId, ModelAndView modelAndView) {
        MiaoshaGoods miaoshaGoods = goodsService.selectMiaoshaGoods(goodsId);
        Long beginTime = miaoshaGoods.getBegin_time().getTime();
        Long endTime = miaoshaGoods.getEnd_time().getTime();
        long nowTime = new Date().getTime();
        Long howLongEnd;
        Long howLongBegin;
        int numBer = miaoshaGoods.getMiaosha_stock();
        int status = 0;
        if (numBer > 0) {
            if (beginTime > nowTime) {
                status = 0;//活动未开始
                howLongBegin = (beginTime - nowTime);
                modelAndView.addObject("howLongBegin", howLongBegin);
            } else if (endTime < nowTime) {
                status = 1;//活动已结束
            } else {
                status = 2;//活动正在进行
                howLongEnd = (endTime - nowTime);
                modelAndView.addObject("howLongEnd", howLongEnd);
            }
        } else {
            status = 3;//已售完
        }

        if (status == 0 || status == 2) {

            String key = "goods" + miaoshaGoods.getMiaoshagoods_id();

            //将商品库存存放在redis中
            long stock = redisUtil.lGetListSize(key);

            if (stock <= 0) {
                for (int i = 0; i < miaoshaGoods.getMiaosha_stock(); i++) {
                    redisUtil.Rpush(key, 1);
                }
            }

        }
        modelAndView.addObject("miaoShaGoods", miaoshaGoods);
        modelAndView.addObject("goodsSatus", status);
        modelAndView.addObject("goodsId", goodsId);
        modelAndView.setViewName("product");
        return modelAndView;
    }

    @Value("C:\\Users\\liujun\\Desktop\\bishe\\biyeshiji-master\\microservicecloud-consumer-feign\\src\\main\\resources\\static\\img")
    private String filePath;
    @ResponseBody
    @RequestMapping(value = "/insertGoodsAndMiaoShaGoods")
    public boolean insertGoodsAndMiaoShaGoods(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request,
            GoodsAndMiaoShaGoods goodsAndMiaoShaGoods) throws IOException {
        if(file.isEmpty()){
            throw new NullPointerException("文件为空");
        }
        //获取原始图片的拓展名
        String originalFileName = file.getOriginalFilename();
        String prefix = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        //新的文件名
        String newFileName = UUID.randomUUID() +"."+ prefix;
        //封装上传文件位置的全路径
        File targetFile = new File(filePath,newFileName);
        //把本地文件上传到封装上传文件位置的全路径
        file.transferTo(targetFile);
        goodsAndMiaoShaGoods.setGoods_img(newFileName);
        try {
            goodsService.insertGoodsAndMiaoShaGoods(goodsAndMiaoShaGoods);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }
}