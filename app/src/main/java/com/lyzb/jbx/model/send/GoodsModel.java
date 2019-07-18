package com.lyzb.jbx.model.send;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by :TYK
 * Date: 2019/5/14  15:10
 * Desc:
 */
public class GoodsModel implements Serializable {

    /**
     * goods_name : 东方宝石靓肤滋养霜（白麝香）300ml（身体乳保湿滋润补水香体）（意大利原装进口）
     * goods_video :
     * local_service : 0
     * goods_price : 80.5
     * goods_bxprice_format : 宝箱价￥80.5
     * goods_id : 83174
     * take_out_status : 0
     * sku_id : 103149
     * shop_name : 乐家优选
     * tags :
     * max_integral_num : 0
     * shop_id : 4274
     * goods_image : http://img10.360buyimg.com/cms/jfs/t7984/62/261523518/125771/dc3a3983/599135e2Nf5abd8e6.jpg?x-oss-process=image/resize,m_pad,limit_0,h_400,w_400
     * goods_number : 999
     */
    private boolean isSelected = false;

    private String goods_name;
    private String goods_video;
    private int local_service;
    private String goods_price;
    private String goods_bxprice_format;
    private String goods_id;
    private int take_out_status;
    private String sku_id;
    private String shop_name;
    private String tags;
    private String max_integral_num;
    private String shop_id;
    private String goods_image;
    private int goods_number;



    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_video() {
        return goods_video;
    }

    public void setGoods_video(String goods_video) {
        this.goods_video = goods_video;
    }

    public int getLocal_service() {
        return local_service;
    }

    public void setLocal_service(int local_service) {
        this.local_service = local_service;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_bxprice_format() {
        return goods_bxprice_format;
    }

    public void setGoods_bxprice_format(String goods_bxprice_format) {
        this.goods_bxprice_format = goods_bxprice_format;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public int getTake_out_status() {
        return take_out_status;
    }

    public void setTake_out_status(int take_out_status) {
        this.take_out_status = take_out_status;
    }

    public String getSku_id() {
        return sku_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getMax_integral_num() {
        return max_integral_num;
    }

    public void setMax_integral_num(String max_integral_num) {
        this.max_integral_num = max_integral_num;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image;
    }

    public int getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(int goods_number) {
        this.goods_number = goods_number;
    }


}
