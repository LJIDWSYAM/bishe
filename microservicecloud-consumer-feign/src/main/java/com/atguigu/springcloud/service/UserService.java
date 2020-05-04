package com.atguigu.springcloud.service;


import com.atguigu.springcloud.entities.UserInfo;

/**
 * @author : liujun
 * @date : ${DATA}
 */
public interface UserService {
    boolean checkUserName(String user_account);
    void regist(UserInfo userInfo);
    UserInfo checkLogin(UserInfo userInfo);
}
