package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.Utils.CodeMsg;
import com.atguigu.springcloud.Utils.RedisUtil;
import com.atguigu.springcloud.Utils.Result;
import com.atguigu.springcloud.entities.*;
import com.atguigu.springcloud.service.GoodsService;
import com.atguigu.springcloud.service.MqSendService;
import com.atguigu.springcloud.service.OrderService;
import com.atguigu.springcloud.service.SeckillClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
public class OrderController {
    @Autowired
    SeckillClientService seckillClientService;
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
    public Result<String> doMiaosha(String miaoshagoods_id, HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute("UserInfo");
        String user_account = userInfo.getUser_account();
        MiaoShaMessage miaoShaMessage=new MiaoShaMessage();
        miaoShaMessage.setMiaoshagoods_id(miaoshagoods_id);
        miaoShaMessage.setUser_account(user_account);
        return seckillClientService.doMiaosha(miaoShaMessage);
    }


    /*
    * 跳转到订单页面*/
    @GetMapping("/buy/{miaoshagoods_id}")
    public ModelAndView buy(@PathVariable("miaoshagoods_id")String miaoshagoods_id, HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute("UserInfo");
        String user_account = userInfo.getUser_account();
        ModelAndView modelAndView=new ModelAndView();
        List<Address> address=orderService.selectAddressByUserAccount(user_account);
        MiaoshaGoods miaoshaGoods=goodsService.selectMiaoshaGoods(miaoshagoods_id);
        modelAndView.addObject("miaoshaGoods",miaoshaGoods);
        modelAndView.addObject("Address",address);
        modelAndView.setViewName("checkoutNotOnTime");
        return modelAndView;
    }

    @GetMapping("/buyNotInTime/{miaoshagoods_id}")
    public ModelAndView buyNotInTime(@PathVariable("miaoshagoods_id")String miaoshagoods_id, HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute("UserInfo");
        String user_account = userInfo.getUser_account();
        ModelAndView modelAndView=new ModelAndView();
        List<Address> address=orderService.selectAddressByUserAccount(user_account);
        MiaoshaGoods miaoshaGoods=goodsService.selectMiaoshaGoods(miaoshagoods_id);
        MiaoShaMessage miaoShaMessage=new MiaoShaMessage();
        miaoShaMessage.setUser_account(user_account);
        miaoShaMessage.setMiaoshagoods_id(miaoshagoods_id);
        Order order=orderService.isRepeatOrder(miaoShaMessage);
        Long orderTimeOut=order.getCreate_time().getTime()+1800000;
        Long nowTime=new Date().getTime();
        Long timeDifference=orderTimeOut-nowTime;
        if (timeDifference<=0){//超时未支付改变订单状态
            //修改状态
            orderService.updateOrderState(miaoShaMessage);
            //恢复库存
            goodsService.recoveryStockAndRedis(miaoShaMessage);
            modelAndView.setViewName("checkout");
            return modelAndView;
        }
        modelAndView.addObject("timeDifference", timeDifference);
        modelAndView.addObject("miaoshaGoods", miaoshaGoods);
        modelAndView.addObject("Address", address);
        modelAndView.setViewName("checkoutNotOnTime");
        return modelAndView;
    }

    /*
    * 订单支付*/
    @RequestMapping("/payment")
    public ModelAndView doOrder(ModelAndView modelAndView, HttpSession session,
                                String miaoshaGoodsId, String address_id) {
        //判断登录
        UserInfo userInfo = (UserInfo) session.getAttribute("UserInfo");
        if (userInfo == null) {
            modelAndView.setViewName("sendredirect:/");
            return modelAndView;
        }
        // 支付环节需要，地址id，账号，秒杀商品id。
        String user_account = userInfo.getUser_account();//miaoshaGoodsId,address_id已有
        //下订单   (判断库存，减少库存，存入订单表)
        //判断库存  select miaosha_stock from miaoshaGoods where miaoshagoods_id = miaoshaGoodsId
//        int num = goodsService.selectMiaoshaGoods(miaoshaGoodsId).getMiaosha_stock();
//        if (num > 0) {
//            String key="goods"+miaoshaGoodsId;
//            Object o = redisUtil.lPop(key);
//            if(o != null){
//                //说明有库存
        //减少库存   update miaosha_goods set miaosha_stock = miaosha_stock -1 where miaoshagoods_id = 3
        //插入订单 insert into orderinfo(order_no,address_id,miaoshagoods_id,user_id,create_time,buy_count) VALUES()
        //        OrderInfo orderInfo = orderService.doOrderInfo(miaoshaGoodsId,userInfo);

        OrderDetailInfo orderdetailInfo = orderService.insertOrderdetailInfo(miaoshaGoodsId, user_account, address_id);
        String order_no = orderdetailInfo.getOrder_no();
        //假设现在是立即支付  引入支付宝
        modelAndView.setViewName("redirect:/alipay/pay?order_no=" + order_no);
        return modelAndView;
    }

    /*
     * 轮询订单结果*/
    @ResponseBody
    @RequestMapping("/miaoShaResult/{miaoshagoods_id}")
    public Result<String> miaoShaResult(@PathVariable("miaoshagoods_id")String miaoshagoods_id, HttpSession session,
                                        String miaoshaGoodsId, String address_id) {
        //判断登录
        UserInfo userInfo = (UserInfo) session.getAttribute("UserInfo");
        if (userInfo == null) {
            return Result.error(CodeMsg.loginError);//code 5000
        }
        MiaoShaMessage miaoShaMessage=new MiaoShaMessage();
        miaoShaMessage.setMiaoshagoods_id(miaoshagoods_id);
        miaoShaMessage.setUser_account(userInfo.getUser_account());
        Order order=orderService.isRepeatOrder(miaoShaMessage);
        if (order!=null){
            return Result.success(order.getOrder_no());
        }
        return Result.success("1");//继续轮询
    }


}