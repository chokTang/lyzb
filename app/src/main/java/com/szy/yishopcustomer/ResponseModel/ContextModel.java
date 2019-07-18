package com.szy.yishopcustomer.ResponseModel;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Map;

/**
 * Created by 宗仁 on 2016/5/31.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ContextModel {
    public long current_time;
    public UserInfoModel user_info;
    public CartModel cart;
    public ConfigModel config;
}
