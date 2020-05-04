package com.atguigu.springcloud.service;


import com.atguigu.springcloud.entities.QQUserInfo;

public interface QQService {
    void insertintoQquserInfo(QQUserInfo qqUserInfo);
    QQUserInfo selectQuserInfoByOpenid(String Openid);
}
