package com.szy.yishopcustomer.ResponseModel.User;

import com.szy.yishopcustomer.ResponseModel.ContextModel;

/**
 * Created by 宗仁 on 16/7/27.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DataModel {
    public InfoModel info;
    public int bonus_count;
    public int cart_count;
    public OrderModel order_counts;
    public ContextModel context;
    public int no_read_count;
    public String user_center_bgimage;
    public UserRankModel user_rank;
    public Boolean is_distrib;
    public Boolean is_recommend_reg;
    public DistribModel app_distrib;
    public ShopInfoModel shop_info;

    public String  freebuy_order_counts;
    public String  reachbuy_order_counts;

    public String is_scancode_enable;

    //积分模式  0系统积分  1 店铺独立积分
    public String integral_model;

    /**
     * 0，申请商家入驻
     * 5，暂时隐藏界面
     * else 查看商家入驻状态
     */
    public String progress;

}
