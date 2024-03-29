package com.atguigu.springcloud.controller;
import com.atguigu.springcloud.entities.Address;
import com.atguigu.springcloud.entities.UserInfo;
import com.atguigu.springcloud.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: liujun
 * \* Date: 2019/6/18
 * \* Time: 15:32
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Controller
@RequestMapping("/Address")
public class addressController {
    @Autowired
    AddressService addressService;
    @ResponseBody
    @RequestMapping("/SelectAddress")
    public Address SelectAddress(String address_id){
        Address address=addressService.selectAddressInfoByAddress_id(address_id);
        return address;
    }
    @ResponseBody
    @RequestMapping("/insertAddress")
    public boolean insertAddress(Address address, HttpSession session){
        UserInfo userInfo= (UserInfo) session.getAttribute("UserInfo");
        address.setUser_account(userInfo.getUser_account());
        addressService.insertAddress(address);
        return true;
    }
    @ResponseBody
    @RequestMapping("/PersonalAllAddress")
    public List<Address> selectAllAddresssByUser_account( HttpSession session){
        UserInfo userInfo= (UserInfo) session.getAttribute("UserInfo");
        List<Address> addresses=new ArrayList<>();
        addresses=addressService.selectAllAddresssByUser_account(userInfo.getUser_account());
        return addresses;
    }
    @ResponseBody
    @RequestMapping("/deleteAddress")
    public boolean deleteAddress( String address_id){
        return addressService.deleteAddress(address_id);
    }
}