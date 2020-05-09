package com.atguigu.springcloud.controller;


import com.atguigu.springcloud.entities.UserInfo;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;


/**
 * \* Created with IntelliJ IDEA.
 * \* User: liujun
 * \* Date: 2019/6/18
 * \* Time: 14:20
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Controller

public class indexController {
    @RequestMapping("/")
    public String regist(){
        return "index";
    }


    @RequestMapping("/listProducts")
    public ModelAndView listProducts(HttpSession session, ModelAndView modelAndView){
        UserInfo userInfo = (UserInfo) session.getAttribute("UserInfo");
        if (userInfo == null) {
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }
        modelAndView.setViewName("products");
        return modelAndView;
    }


    @RequestMapping("/orderInfo")
    public String orderInfo(){
        return "orderInfo";
    }

    @RequestMapping("/persional")
    public String persional(){
        return "personal";
    }

    @RequestMapping("/admin")
    public String admin(){
        return "products2";
    }
    @RequestMapping("/errors")
    public String error(){
        return "error";
    }
}