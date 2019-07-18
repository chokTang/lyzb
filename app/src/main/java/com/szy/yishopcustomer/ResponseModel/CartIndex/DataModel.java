package com.szy.yishopcustomer.ResponseModel.CartIndex;

import com.szy.yishopcustomer.ResponseModel.ContextModel;

import java.util.List;

/**
 * Created by zongren on 16/5/28.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DataModel {
    public CartModel cart;
    public List<UnpaidOrderModel> unpayed_list;
    public ContextModel context;
    public String freebuy_list;
    public String reachbuy_list;
    public RcModel rc_model;
}
