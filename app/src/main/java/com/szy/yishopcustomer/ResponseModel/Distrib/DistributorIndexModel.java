package com.szy.yishopcustomer.ResponseModel.Distrib;

import com.szy.yishopcustomer.ResponseModel.AppIndex.GoodsItemModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.PageModel;

import java.util.List;

/**
 * Created by liwei on 2017/9/26.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class DistributorIndexModel {

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {
        public List<GoodsItemModel> list;
        public PageModel page;
        public ShopInfoModel shop_info;

        public static class ShopInfoModel {
            public String shop_name;// "lol",
            public String shop_headimg;// "/system/config/default_image/default_micro_shop_image_0.jpg",
            public String shop_background;// null,
        }
    }
}
