package com.szy.yishopcustomer.ResponseModel.ShopGoodsList;

import com.szy.yishopcustomer.ResponseModel.CategoryModel;
import com.szy.yishopcustomer.ResponseModel.FilterModel;
import com.szy.yishopcustomer.ResponseModel.GoodsModel;
import com.szy.yishopcustomer.ResponseModel.PageModel;
import com.szy.yishopcustomer.ResponseModel.ParamsModel;

import java.util.List;

/**
 * Created by 宗仁 on 2016/7/8.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DataModel {
    public FilterModel filter; //筛选里面的值
    public List<GoodsModel> list; //商品列表的值
    public PageModel page;//控制页面加载更多的 cur_page page_count page_size
    public List<CategoryModel> category; //不清楚 好像并没有卵用
    public int show_sale_number;//好像并没有卵用
    public ParamsModel params; //参数的集合 sort  order 需要
    public String display;//默认的风格

}
