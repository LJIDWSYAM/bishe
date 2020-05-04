package com.atguigu.springcloud.dao;



import com.atguigu.springcloud.entities.Address;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AddressDao {

    Address selectAddressInfoByAddress_id(String address_id);
    void insertAddress(Address address);
}
