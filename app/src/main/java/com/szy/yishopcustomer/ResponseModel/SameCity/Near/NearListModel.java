package com.szy.yishopcustomer.ResponseModel.SameCity.Near;

import java.util.List;

/**
 * @author
 * @role
 * @time 2018 15:16
 */

public class NearListModel {


    /**
     * shopId : 1236
     * userId : 111978
     * siteId : 0
     * shopName : 集食惠海王星门店
     * shopLogo : /shop/1236/images/2018/05/25/15272380072328.jpg
     * minPice : 2
     * acer : 2
     * evalScore : 5
     * shopDescription : 抱团企业送元宝，买多少就送多少。
     * distance : 21.66122621463422
     */


    public int shopId;
    public int saleSkip;
    public int userId;
    public int siteId;
    public String shopName;
    public String shopLogo;
    public String takeOut;
    public int minPice;

    public String acerLabel;
    public int acer;
    public float evalScore;

    public String prodectName;

    public boolean takeOutStatus;
    public String userShopPower;

    public List<CatNameList> goodsCatNameList;

    public String shopDescription;
    public String soldNum;
    public double distance;

    public static class CatNameList {
        public int catId;
        public String catName;
    }
}
