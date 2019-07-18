package com.szy.yishopcustomer.ResponseModel.GoodsList;

import com.szy.yishopcustomer.ResponseModel.FilterModel;
import com.szy.yishopcustomer.ResponseModel.GoodsModel;

import java.util.List;

/**
 * Created by 宗仁 on 2016/8/8 0008.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DataModel {
    public PageModel page;
    public List<GoodsModel> list;
    public ContextModel context;
    public FilterModel filter;
    public String keyword;
}
