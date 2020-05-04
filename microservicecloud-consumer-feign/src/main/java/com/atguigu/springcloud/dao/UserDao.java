package com.atguigu.springcloud.dao;


import com.atguigu.springcloud.entities.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author : liujun
 * @date : ${DATA}
 */
@Repository
@Mapper
public interface UserDao {
     UserInfo selectUserinfoByUserNname(String user_account);
     void regist(UserInfo userInfo);
     UserInfo selectUserinfoByUserNameAndPassword(UserInfo userInfo);
}
