package com.atguigu.springcloud.service;


import com.atguigu.springcloud.entities.Address;

public interface AddressService {

    Address selectAddressInfoByAddress_id(String address_id);
    void insertAddress(Address address);
}
