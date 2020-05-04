package com.atguigu.springcloud.service;

import com.atguigu.springcloud.Utils.Result;
import com.atguigu.springcloud.entities.MiaoShaMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

/**
 * @author : liujun
 * @date : ${DATA}
 */

@FeignClient(value = "MICROSERVICECLOUD-SECKILL",fallbackFactory =SeckillClientServiceFallbackFactory.class)
public interface SeckillClientService
{
    @RequestMapping("/order/doMiaosha")
    public Result<String> doMiaosha(@RequestBody MiaoShaMessage miaoShaMessage);

}