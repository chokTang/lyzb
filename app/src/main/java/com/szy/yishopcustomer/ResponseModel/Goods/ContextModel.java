package com.szy.yishopcustomer.ResponseModel.Goods;

/**
 * Created by liwei on 2016/7/28.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ContextModel {
    public CartModel cart;
    public long current_time;// 1469698190,
    public CommentUserInfoModel user_info;
    public class CartModel {
        public String goods_count;
    }
    public ConfigModel config;
}
