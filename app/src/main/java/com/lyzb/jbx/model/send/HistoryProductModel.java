package com.lyzb.jbx.model.send;

/**
 * Created by :TYK
 * Date: 2019/5/14  11:34
 * Desc:
 */
public class HistoryProductModel {

    /**
     * goodsId : 336195
     * goodsName : 荣泰/按摩椅RT6910S 太空舱逍遥椅全身多功能按摩沙发椅 棕色
     * goodsImage : http://imgservice3.suning.cn/uimg1/b2c/image/O61jOai1VPSV7e5XQlookA.jpg_400w_400h_4e
     * goodsPrice : 30960
     * maxIntegralNum : 2301
     * shopName : 陶应財测试店铺01
     * checked : false
     */

    private boolean isSelected;
    private String goodsId;
    private String goodsName;
    private String goodsImage;
    private String goodsPrice;
    private int maxIntegralNum;
    private String shopName;
    private boolean checked;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public int getMaxIntegralNum() {
        return maxIntegralNum;
    }

    public void setMaxIntegralNum(int maxIntegralNum) {
        this.maxIntegralNum = maxIntegralNum;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
