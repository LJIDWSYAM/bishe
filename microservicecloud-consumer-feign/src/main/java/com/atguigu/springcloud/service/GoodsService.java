package com.atguigu.springcloud.service;



import com.atguigu.springcloud.Utils.Page;
import com.atguigu.springcloud.entities.Goods;
import com.atguigu.springcloud.entities.MiaoShaMessage;
import com.atguigu.springcloud.entities.MiaoshaGoods;

import java.util.List;

public interface GoodsService {

    List<Goods>  getAllGoods();

    int getGoodsCount();

    List<Goods>  getAllGoodsByPage(Page page);

    MiaoshaGoods selectMiaoshaGoods(String goodsId);

    void recoveryStockAndRedis(MiaoShaMessage miaoShaMessage);

}
