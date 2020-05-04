package springcloud.service.impl;


import com.atguigu.springcloud.entities.Goods;
import com.atguigu.springcloud.entities.MiaoShaMessage;
import com.atguigu.springcloud.entities.MiaoshaGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springcloud.Utils.Page;
import springcloud.Utils.RedisUtil;
import springcloud.dao.GoodsDao;
import springcloud.service.GoodsService;

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


}
