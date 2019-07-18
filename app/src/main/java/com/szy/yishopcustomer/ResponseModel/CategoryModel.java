package com.szy.yishopcustomer.ResponseModel;

import java.util.List;

/**
 * Created by 宗仁 on 2017/1/10.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class CategoryModel {
    public String cat_sort;//"cat_sort":255,
    public String cat_id;//"cat_id":33,
    public String cat_name;//"cat_name":"时尚单鞋",
    public String parent_id;//"parent_id":0,
    public String shop_id;//"shop_id":4,
    public String cat_level;//"cat_level":1,
    public String keywords;//"keywords":null,
    public String cat_desc;//"cat_desc":null,
    public String site_id;//"site_id":0,
    public int cart_num;//"cart_num":0
    public boolean selected;
    public List<CategoryModel> chr_list;
    public boolean isParent;
    public int type;
}
