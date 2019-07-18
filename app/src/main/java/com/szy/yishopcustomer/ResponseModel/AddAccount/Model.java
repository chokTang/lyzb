package com.szy.yishopcustomer.ResponseModel.AddAccount;

import java.util.List;

/**
 * Created by liuzhifeng on 2017/3/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class Model {
    public int code;
    public List<TypeItemsModel> type_items;
    public String type;
    public String user_name;//": "卖家",
    public String email1;//": "13223344466@qq.com",
    public String email;//": "1*********6@qq.com",
    public String mobile;//": "132****4466",
    public String mobile1;//": "13223344466",
    public String email_validated;//": 1,
    public String mobile_validated;//": 1
    public boolean show_captcha;
}
