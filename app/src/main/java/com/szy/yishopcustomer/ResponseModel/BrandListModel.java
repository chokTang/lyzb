package com.szy.yishopcustomer.ResponseModel;

import java.util.List;

/**
 * Created by 宗仁 on 2016/8/31.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BrandListModel {
    public String name;// "品牌",
    public String param;// "brand_id",
    public String url;//"list.html?cat_id=4&amp;go=1&amp;brand_id={0}&amp;style=grid",
    public List<BrandModel> items;
}
