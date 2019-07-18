package com.lyzb.jbx.model.statistics;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author wyx
 * @role 名片商场交易记录2级实体。
 * @time 2019 2019/4/18 15:26
 */

public class AnalysisTransactionContentModel implements MultiItemEntity {
    /**
     * 商品总数量
     */
    private int goodsNumber;
    /**
     * 商品ID
     */
    private int goodsId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品价格
     */
    private double goodsPrice;

    /**
     * 团购企业账号名
     */
    private String accountName;
    /**
     * 团购企业账号使用者名称
     */
    private String userName;

    public int getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(int goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }


    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int getItemType() {
        return 1;
    }
}
