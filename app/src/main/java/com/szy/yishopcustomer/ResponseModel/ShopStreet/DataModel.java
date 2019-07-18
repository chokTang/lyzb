package com.szy.yishopcustomer.ResponseModel.ShopStreet;

import com.szy.yishopcustomer.ResponseModel.AppIndex.ShopListItemModel;
import com.szy.yishopcustomer.ResponseModel.PageModel;

import java.util.List;

/**
 * Created by buqingqiang on 16/6/15.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DataModel {
    public List<ShopListItemModel> list;
    public List<ShopStreetCateOneItemModel> cls_list;
    public List<ShopStreetCateTwoItemModel> cat_list;
    public PageModel page;
}
