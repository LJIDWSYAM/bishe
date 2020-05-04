package com.atguigu.springcloud.dao;



import com.atguigu.springcloud.Utils.Page;
import com.atguigu.springcloud.entities.Goods;
import com.atguigu.springcloud.entities.MiaoShaMessage;
import com.atguigu.springcloud.entities.MiaoshaGoods;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GoodsDao {

    List<Goods> selectGoods();

    int selectGoodsCount();

    List<Goods> selectGoodsByPage(Page page);

    MiaoshaGoods selectMiaoshaGoods(String goodsId);

    void recoveryStock(MiaoShaMessage miaoShaMessage);

}
