package com.atguigu.springcloud.dao;



import com.atguigu.springcloud.entities.Address;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AddressDao {

    Address selectAddressInfoByAddress_id(String address_id);
    void insertAddress(Address address);
    List<Address> selectAllAddresssByUser_account(String user_account);
    boolean deleteAddress (String address_id);
}
