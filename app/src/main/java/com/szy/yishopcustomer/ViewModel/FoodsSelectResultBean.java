package com.szy.yishopcustomer.ViewModel;

import com.szy.yishopcustomer.ViewModel.samecity.FoodsSelectTitleBean;

import java.util.List;

/**
 * Created by :TYK
 * Date: 2019/1/3  16:18
 * Desc:
 */
public class FoodsSelectResultBean {

    /**
     * shopId : 932
     * userId : 7499
     * siteId : 0
     * shopName : 乌苏市阿不旦抓饭店
     * shopImage : /shop/932/images/2018/06/06/15282527296166.jpg
     * shopLogo : /shop/932/images/2018/06/06/15282527296166.jpg
     * minPice : 15
     * acer : 1
     * goodsCatNameList : [{"catId":11111165,"catName":"西北菜"},{"catId":11111174,"catName":"创意菜"}]
     * evalScore : 5
     * distance : 0
     * userShopPower : 1
     * takeOut : false
     * prodectName : 阿不旦干果抓饭￥15.00
     * acerLabel : 1
     * jibPice : 14
     * saleSkip : 0
     */

    private String shopId;
    private String userId;
    private String siteId;
    private String shopName;
    private String shopImage;
    private String shopLogo;
    private String minPice;
    private String acer;
    private String evalScore;
    private String distance;
    private String userShopPower;
    private boolean takeOut;
    private String prodectName;
    private String acerLabel;
    private String jibPice;
    private String saleSkip;
    private List<GoodsCatNameListBean> goodsCatNameList;
    private List<FoodsSelectTitleBean> personTypeList;

    public List<FoodsSelectTitleBean> getPersonTypeList() {
        return personTypeList;
    }

    public void setPersonTypeList(List<FoodsSelectTitleBean> personTypeList) {
        this.personTypeList = personTypeList;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopImage() {
        return shopImage;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public String getMinPice() {
        return minPice;
    }

    public void setMinPice(String minPice) {
        this.minPice = minPice;
    }

    public String getAcer() {
        return acer;
    }

    public void setAcer(String acer) {
        this.acer = acer;
    }

    public String getEvalScore() {
        return evalScore;
    }

    public void setEvalScore(String evalScore) {
        this.evalScore = evalScore;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getUserShopPower() {
        return userShopPower;
    }

    public void setUserShopPower(String userShopPower) {
        this.userShopPower = userShopPower;
    }

    public boolean isTakeOut() {
        return takeOut;
    }

    public void setTakeOut(boolean takeOut) {
        this.takeOut = takeOut;
    }

    public String getProdectName() {
        return prodectName;
    }

    public void setProdectName(String prodectName) {
        this.prodectName = prodectName;
    }

    public String getAcerLabel() {
        return acerLabel;
    }

    public void setAcerLabel(String acerLabel) {
        this.acerLabel = acerLabel;
    }

    public String getJibPice() {
        return jibPice;
    }

    public void setJibPice(String jibPice) {
        this.jibPice = jibPice;
    }

    public String getSaleSkip() {
        return saleSkip;
    }

    public void setSaleSkip(String saleSkip) {
        this.saleSkip = saleSkip;
    }

    public List<GoodsCatNameListBean> getGoodsCatNameList() {
        return goodsCatNameList;
    }

    public void setGoodsCatNameList(List<GoodsCatNameListBean> goodsCatNameList) {
        this.goodsCatNameList = goodsCatNameList;
    }

    public static class GoodsCatNameListBean {
        /**
         * catId : 11111165
         * catName : 西北菜
         */

        private String catId;
        private String catName;

        public String getCatId() {
            return catId;
        }

        public void setCatId(String catId) {
            this.catId = catId;
        }

        public String getCatName() {
            return catName;
        }

        public void setCatName(String catName) {
            this.catName = catName;
        }
    }
}
