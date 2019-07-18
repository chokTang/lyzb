package com.szy.yishopcustomer.ResponseModel.AddToCartModel;

import com.szy.yishopcustomer.ResponseModel.Attribute.SkuModel;
import com.szy.yishopcustomer.ResponseModel.Attribute.SpecificationModel;

import java.util.List;
import java.util.Map;

/**
 * Created by 宗仁 on 2016/8/8 0008.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DataModel {
    public String goods_id;//38,
    public String goods_image;//"http://images.68shop.test/shop/5/2016/06/12/14656990858919_320_320.jpg",
    public String shop_id;//0,
    public String sku_open;//1
    public boolean show_goods_stock;
    public Map<String, SkuModel> sku_list;
    public List<SpecificationModel> spec_list;
}
