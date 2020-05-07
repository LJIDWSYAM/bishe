package com.atguigu.springcloud.service;


import com.atguigu.springcloud.entities.Address;

import java.util.List;

public interface AddressService {

    Address selectAddressInfoByAddress_id(String address_id);
    void insertAddress(Address address);
    List<Address> selectAllAddresssByUser_account(String user_account);
    boolean deleteAddress (String address_id);
}
