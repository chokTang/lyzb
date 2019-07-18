package com.szy.yishopcustomer.ResponseModel.Address;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by 宗仁 on 16/8/4.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DataModel {
    @JSONField(name = "default")
    public int isDefaultAddress;
    public boolean switchEnabled;
    public AddressModel model;
}
