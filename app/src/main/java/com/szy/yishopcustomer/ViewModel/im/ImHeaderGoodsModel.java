package com.szy.yishopcustomer.ViewModel.im;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/10.
 */

public class ImHeaderGoodsModel implements Serializable {

    private int chatType;   //聊天的类型(单聊 群聊)

    //商品信息
    private String goodsId;
    private String imageUrl;
    private String goodsName;
    private String goodPrice;

    //店铺信息
    private String shopId;      //单聊: 店铺ID          群聊:群ID
    private String shopImName;  //单聊: 店铺环信账号     群聊:群ID
    private String shopName;    //单聊: 店铺昵称         群聊:群名称
    private String shopHeadimg; //单聊: 店铺的环信头像   群聊:群组头像

    private boolean isBanned;


    public int getChatType() {
        return chatType;
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(String goodPrice) {
        this.goodPrice = goodPrice;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopImName() {
        return shopImName;
    }

    public void setShopImName(String shopImName) {
        this.shopImName = shopImName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopHeadimg() {
        return shopHeadimg;
    }

    public void setShopHeadimg(String shopHeadimg) {
        this.shopHeadimg = shopHeadimg;
    }

    public boolean getIsBanned() {
        return isBanned;
    }

    public void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }
}
