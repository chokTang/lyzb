package com.szy.yishopcustomer.ResponseModel.Shop;

import com.szy.yishopcustomer.ResponseModel.AppIndex.ArticleItemModel;
import com.szy.yishopcustomer.ResponseModel.CategoryModel;

import java.util.List;

/**
 * Created by buqingqiang on 16/7/28.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DataModel {
    public String shop_id;
    public ShopInfoModel shop_info;
    public List<BonusModel> bonus_list;
    public String region_name;//"region_name":"河北省,秦皇岛市,海港区",
    public String duration_time;//"duration_time":1,
    public boolean is_collect;//"is_collect":null,
    public String collect_count;//"collect_count":"2",
    public String title;//"title":"首页-鞋柜自营旗舰店",
    public String position;//"position":"index",
    public String goods_count;
    public List<CategoryModel> category;
    public ContextModel context;
    public List<ArticleItemModel> article;
    public boolean is_opening;
    public String shop_header_style;
    public String freebuy_enable;
}
