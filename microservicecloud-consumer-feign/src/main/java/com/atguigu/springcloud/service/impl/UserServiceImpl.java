package com.atguigu.springcloud.service.impl;


import com.atguigu.springcloud.Utils.MD5Utils;
import com.atguigu.springcloud.dao.UserDao;
import com.atguigu.springcloud.entities.UserInfo;
import com.atguigu.springcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: liujun
 * \* Date: 2019/6/18
 * \* Time: 15:47
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;
    @Override
    public boolean checkUserName(String user_account) {
        UserInfo userInfo=userDao.selectUserinfoByUserNname(user_account);
        return userInfo==null?false:true;
    }

    @Override
    public void regist(UserInfo userInfo) {
        Date regist_time=new Date();
        userInfo.setRegist_time(regist_time);
        userInfo.setLast_login_time(regist_time);
        String password = userInfo.getUser_pwd();
        String newPwd =  MD5Utils.setDBPwd(password);
        userInfo.setUser_pwd(newPwd);
        userDao.regist(userInfo);
    }

    @Override
    public UserInfo checkLogin(UserInfo userInfo) {
        String password = userInfo.getUser_pwd();
        String newPwd =  MD5Utils.setDBPwd(password);
        userInfo.setUser_pwd(newPwd);
        UserInfo userInfo1=userDao.selectUserinfoByUserNameAndPassword(userInfo);
        return userInfo1;
    }


}