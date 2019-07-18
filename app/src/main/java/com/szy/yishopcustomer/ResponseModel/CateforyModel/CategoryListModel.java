package com.szy.yishopcustomer.ResponseModel.CateforyModel;

import java.util.List;

/**
 * Created by liwe on 2017/5/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class CategoryListModel {
    public String cat_id;
    public String cat_name;
    public String cat_image;
    public int cat_level;
    public String cat_link ;

    public List<SubcategoryModel> items;
}
