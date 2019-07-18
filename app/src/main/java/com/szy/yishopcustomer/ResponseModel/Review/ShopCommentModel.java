package com.szy.yishopcustomer.ResponseModel.Review;

/**
 * Created by buqingqiang on 2016/12/22.
 */

public class ShopCommentModel {
    //    服务态度
    public Float shop_service;
    //    发货速度
    public Float shop_speed;
    //    物流服务
    public Float logistics_speed;

    public ShopCommentModel(Float shopService, Float shopSpeed, Float logisticsSpeed) {
        this.shop_service = shopService;
        this.shop_speed = shopSpeed;
        this.logistics_speed = logisticsSpeed;
    }
}
