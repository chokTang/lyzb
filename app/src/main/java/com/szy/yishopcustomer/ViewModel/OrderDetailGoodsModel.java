package com.szy.yishopcustomer.ViewModel;

/**
 * Created by lw on 2016/6/16.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class OrderDetailGoodsModel {
    private String mGoodsName;
    private String mGoodsImage;
    private String mGoodsPrice;
    private String mGoodsNumber;
    private String mSpecInfo;

    public OrderDetailGoodsModel() {
    }

    public String getGoodsImage() {
        return mGoodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        mGoodsImage = goodsImage;
    }

    public String getGoodsName() {
        return mGoodsName;
    }

    public void setGoodsName(String goodsName) {
        mGoodsName = goodsName;
    }

    public String getGoodsNumber() {
        return mGoodsNumber;
    }

    public void setGoodsNumber(String goodsNumber) {
        mGoodsNumber = goodsNumber;
    }

    public String getGoodsPrice() {
        return mGoodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        mGoodsPrice = goodsPrice;
    }

    public String getSpecInfo() {
        return mSpecInfo;
    }

    public void setSpecInfo(String specInfo) {
        mSpecInfo = specInfo;
    }

}
