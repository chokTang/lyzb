package com.szy.yishopcustomer.ResponseModel.AppIndex;

import java.util.List;
import java.util.Map;

/**
 * Created by 宗仁 on 16/8/29.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class TemplateModel {
    public String temp_code;
    public String data;

    public String message;

    public Extinfo ext_info;

    public List<GuessGoodsModel> mGoodsList;//猜你喜欢的列表 适合主页的

    public class Extinfo {
        public Map<String, String> cat;
    }
}
