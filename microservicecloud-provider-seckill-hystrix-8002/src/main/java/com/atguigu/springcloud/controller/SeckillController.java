package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.Utils.CodeMsg;
import com.atguigu.springcloud.Utils.RedisUtil;
import com.atguigu.springcloud.Utils.Result;
import com.atguigu.springcloud.entities.*;
import com.atguigu.springcloud.service.GoodsService;
import com.atguigu.springcloud.service.MqSendService;
import com.atguigu.springcloud.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: liujun
 * \* Date: 2019/6/24
 * \* Time: 8:47
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Controller
@RequestMapping("/order")
public class SeckillController {
    @Autowired
    OrderService orderService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    MqSendService mqSendService;


    @ResponseBody
    @RequestMapping("/doMiaosha")
    public Result<String> doMiaosha(@RequestBody MiaoShaMessage miaoShaMessage, HttpSession session){
        System.out.println("8002");
        String miaoshagoods_id=miaoShaMessage.getMiaoshagoods_id();
//        System.out.println(miaoShaMessage);
        Order order=orderService.isRepeatOrder(miaoShaMessage);
        if (order==null){//如果没有订单，生成订单
            String key="goods"+miaoshagoods_id;
            Object o = redisUtil.lPop(key);
            if(o == null){
                return Result.error(CodeMsg.goodsSaled);
            }
            mqSendService.sendMiaoshaMessage(miaoShaMessage);
            return Result.success("排队中,请耐心等待几秒。。");
        }
        else {
            //如果有订单，查看订单状态
            int state=order.getState();
            if (state==1){//已支付完成
                return Result.error(CodeMsg.goodsRepeatPurchase);
            }else if(state==0) {//未完成支付
                return Result.error(CodeMsg.goodsNoPay);
            }else {//超时未支付
                return Result.error(CodeMsg.goodsTimeOut);
            }

        }

    }


}