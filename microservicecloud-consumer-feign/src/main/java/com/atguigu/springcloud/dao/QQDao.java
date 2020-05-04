package com.atguigu.springcloud.dao;


import com.atguigu.springcloud.entities.QQUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface QQDao {
     void insertintoQquserInfo(QQUserInfo qqUserInfo);
     QQUserInfo selectQuserInfoByOpenid(String openId);
}
