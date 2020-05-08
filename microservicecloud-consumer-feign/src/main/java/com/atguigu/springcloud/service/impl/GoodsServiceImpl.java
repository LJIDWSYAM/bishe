package com.atguigu.springcloud.service.impl;


import com.atguigu.springcloud.Utils.Page;
import com.atguigu.springcloud.Utils.RedisUtil;
import com.atguigu.springcloud.dao.GoodsDao;
import com.atguigu.springcloud.entities.Goods;
import com.atguigu.springcloud.entities.GoodsAndMiaoShaGoods;
import com.atguigu.springcloud.entities.MiaoShaMessage;
import com.atguigu.springcloud.entities.MiaoshaGoods;
import com.atguigu.springcloud.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsDao goodsDao;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public List<Goods> getAllGoods() {

        return goodsDao.selectGoods();
    }

    @Override
    public int getGoodsCount() {
        return  goodsDao.selectGoodsCount();
    }

    @Override
    public List<Goods> getAllGoodsByPage(Page page) {

        return goodsDao.selectGoodsByPage(page);
    }

    @Override
    public MiaoshaGoods selectMiaoshaGoods(String goodsId) {
        return goodsDao.selectMiaoshaGoods(goodsId);
    }

    @Override
    public void recoveryStockAndRedis(MiaoShaMessage miaoShaMessage) {
        goodsDao.recoveryStock(miaoShaMessage);
        String key = "goods"+miaoShaMessage.getMiaoshagoods_id();
        redisUtil.Rpush(key,1);

    }

    @Override
    public boolean insertGoodsAndMiaoShaGoods(GoodsAndMiaoShaGoods goodsAndMiaoShaGoods) throws ParseException {
        Goods goods=new Goods();
        goods.setGoods_name(goodsAndMiaoShaGoods.getGoods_name());
        goods.setGoods_desc(goodsAndMiaoShaGoods.getGoods_desc());
        goods.setGoods_price(goodsAndMiaoShaGoods.getGoods_price());
        goods.setGoods_img(goodsAndMiaoShaGoods.getGoods_img());
        goodsDao.insertGoods(goods);
        MiaoshaGoods miaoshaGoods=new MiaoshaGoods();
        miaoshaGoods.setGoods_id(goods.getGoods_id());
        miaoshaGoods.setMiaosha_price(goodsAndMiaoShaGoods.getMiaosha_price());
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = sdf2.parse(goodsAndMiaoShaGoods.getBegin_time());
        Date d2 = sdf2.parse(goodsAndMiaoShaGoods.getEnd_time());
        miaoshaGoods.setBegin_time(d1);
        miaoshaGoods.setMiaosha_stock(goodsAndMiaoShaGoods.getMiaosha_stock());
        miaoshaGoods.setEnd_time(d2);
        goodsDao.insertMiaoShaGoods(miaoshaGoods);

        return true;
    }


}
