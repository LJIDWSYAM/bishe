package com.atguigu.springcloud.vo;




import com.atguigu.springcloud.Utils.Page;
import com.atguigu.springcloud.entities.Goods;

import java.util.List;

public class GoodsPage {

    private List<Goods> goods;

    private Page page;

    public List<Goods>  getGoods() {
        return goods;
    }

    public void setGoods(List<Goods>  goods) {
        this.goods = goods;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
