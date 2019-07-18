package com.szy.yishopcustomer.ViewModel;

import com.alibaba.fastjson.annotation.JSONField;
import com.szy.yishopcustomer.ResponseModel.Attribute.AttributeModel;
import com.szy.yishopcustomer.ResponseModel.Attribute.SkuModel;
import com.szy.yishopcustomer.ResponseModel.Attribute.SpecificationModel;
import com.szy.yishopcustomer.ResponseModel.Attribute.SpecificationModelTwo;
import com.szy.yishopcustomer.ResponseModel.Goods.GoodsModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Smart on 2017/8/11.
 */

public class FreeSkuListModel {


    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {
        public GoodsModel goods;
        public String goods_id;//38,
        public String goods_image;//"http://images.68shop.test/shop/5/2016/06/12/14656990858919_320_320.jpg",
        public String shop_id;//0,
        public String sku_open;//1
        public Map<String, SkuModel> sku_list;
        public List<SpecificationModelTwo> spec_list;
        public boolean is_sku=true;
        public String show_goods_stock;
    }
}
