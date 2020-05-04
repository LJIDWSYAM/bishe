package com.atguigu.springcloud.service;

import com.atguigu.springcloud.Utils.CodeMsg;
import com.atguigu.springcloud.Utils.Result;
import com.atguigu.springcloud.entities.MiaoShaMessage;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: liujun
 * \* Date: 2019/8/12
 * \* Time: 15:00
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Component
public class SeckillClientServiceFallbackFactory implements FallbackFactory<SeckillClientService> {

    @Override
    public SeckillClientService create(Throwable throwable) {
        return new SeckillClientService() {
            @Override
            public Result<String> doMiaosha(MiaoShaMessage miaoShaMessage) {
                return Result.error(CodeMsg.stop);
            }
        };
    }
}