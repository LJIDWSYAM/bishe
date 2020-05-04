package com.atguigu.springcloud.service.impl;


import com.atguigu.springcloud.dao.QQDao;
import com.atguigu.springcloud.entities.QQUserInfo;
import com.atguigu.springcloud.service.QQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QQServiceInfoImpl implements QQService {
    @Autowired
    QQDao qqDao;
    @Override
    public void insertintoQquserInfo(QQUserInfo qqUserInfo) {
        qqDao.insertintoQquserInfo(qqUserInfo);
    }

    @Override
    public QQUserInfo selectQuserInfoByOpenid(String openid) {
        QQUserInfo qqUserInfo=qqDao.selectQuserInfoByOpenid(openid);
        return qqUserInfo;
    }
}
