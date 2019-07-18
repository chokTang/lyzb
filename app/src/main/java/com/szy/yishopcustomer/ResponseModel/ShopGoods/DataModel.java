package com.szy.yishopcustomer.ResponseModel.ShopGoods;

import com.szy.yishopcustomer.ResponseModel.GoodsList.PageModel;
import com.szy.yishopcustomer.ResponseModel.Shop.ShopInfoModel;

import java.util.List;

/**
 * Created by buqingqiang on 2016/7/8.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DataModel {
    public ShopInfoModel shop_info;
    public List<ShopGoodsItemModel> list;
    public PageModel page;
}
