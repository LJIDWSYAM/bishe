package com.atguigu.springcloud.service.impl;


import com.atguigu.springcloud.dao.AddressDao;
import com.atguigu.springcloud.entities.Address;
import com.atguigu.springcloud.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressDao addressDao;
    @Override
    public Address selectAddressInfoByAddress_id(String address_id) {
        Address address=addressDao.selectAddressInfoByAddress_id(address_id);
        return address;
    }

    @Override
    public void insertAddress(Address address) {
        addressDao.insertAddress(address);
    }

    @Override
    public List<Address> selectAllAddresssByUser_account(String user_account) {
        return addressDao.selectAllAddresssByUser_account(user_account);
    }

    @Override
    public boolean deleteAddress(String address_id) {
        addressDao.deleteAddress(address_id);
        return true;
    }
}