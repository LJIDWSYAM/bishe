package com.atguigu.springcloud.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class GoodsAndMiaoShaGoods implements Serializable {
    private int goods_id;
    private String goods_name;
    private double goods_price;
    private String goods_desc;
    private int goods_stock;
    private String goods_img;

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(double goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_desc() {
        return goods_desc;
    }

    public void setGoods_desc(String goods_desc) {
        this.goods_desc = goods_desc;
    }

    public int getGoods_stock() {
        return goods_stock;
    }

    public void setGoods_stock(int goods_stock) {
        this.goods_stock = goods_stock;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public int getMiaoshagoods_id() {
        return miaoshagoods_id;
    }

    public void setMiaoshagoods_id(int miaoshagoods_id) {
        this.miaoshagoods_id = miaoshagoods_id;
    }

    public BigDecimal getMiaosha_price() {
        return miaosha_price;
    }

    public void setMiaosha_price(BigDecimal miaosha_price) {
        this.miaosha_price = miaosha_price;
    }

    public int getMiaosha_stock() {
        return miaosha_stock;
    }

    public void setMiaosha_stock(int miaosha_stock) {
        this.miaosha_stock = miaosha_stock;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    private int miaoshagoods_id;
    private BigDecimal miaosha_price;
    private int miaosha_stock;
    private String begin_time;
    private String end_time;
    private Goods goods;

}
