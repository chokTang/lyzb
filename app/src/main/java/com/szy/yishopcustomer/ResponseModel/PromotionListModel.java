package com.szy.yishopcustomer.ResponseModel;

import com.szy.yishopcustomer.ResponseModel.Attribute.SkuModel;
import com.szy.yishopcustomer.ResponseModel.Attribute.SpecificationModel;
import com.szy.yishopcustomer.ResponseModel.Distrib.DistribModel;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Smart on 2017/11/21.
 */

public class PromotionListModel {

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean{
        public TreeMap<String,PromotionList> list;
        public DistribModel.DataBean.Context context;
    }

    public static class PromotionList{
        public ActInfo act_info;
        public TreeMap<String,SkuModel> sku_list;

        public int buy_number = 1;
    }

    public static class ActInfo{

        /**
         * act_id : 375
         * act_name : 21e
         * act_title : null
         * act_type : 10
         * act_img : null
         * start_time : 1510903736
         * end_time : 1511508536
         * is_finish : 1
         * purchase_num : 0
         * status : 1
         * is_recommend : 1
         * shop_id : 2
         * site_id : 0
         * ext_info : a:3:{s:10:"price_mode";s:1:"0";s:9:"act_price";s:2:"11";s:13:"discount_show";s:1:"1";}
         * use_range : 1
         * sort : 255
         * reason : null
         * price_mode : 0
         * act_price : 11
         * discount_show : 1
         * goods_info : [{"act_id":"375","act_price":"0.30","goods_id":"1045","goods_name":"康师傅香凝荔枝饮品450ml","goods_price":"2.80","goods_image":"/system/config/default_image/default_goods_image_0.png","spec_ids":null,"spec_vids":[""],"goods_number":"999","min_price":"0.30","max_price":"0.30","min_goods_price":"2.80","max_goods_price":"1111.00","spec_list":null,"sku_id":"2902"},{"act_id":"375","act_price":"1.19","goods_id":"1051","goods_name":"后台添加","goods_price":"11.00","goods_image":"/system/config/default_image/default_goods_image_0.png","spec_ids":"","spec_vids":[""],"goods_number":"2","min_price":"1.19","max_price":"1.19","min_goods_price":"11.00","max_goods_price":"11.00","spec_list":null,"sku_id":"2903"},{"act_id":"375","act_price":"0.00","goods_id":"1053","goods_name":"测试123","goods_price":"0.01","goods_image":"/system/config/default_image/default_goods_image_0.png","spec_ids":null,"spec_vids":[""],"goods_number":"47","min_price":"0.00","max_price":"0.00","min_goods_price":"0.01","max_goods_price":"0.01","spec_list":null,"sku_id":"2906"},{"act_id":"375","act_price":"9.51","goods_id":"1063","goods_name":"测试商品规格禁用功能","goods_price":"88.00","goods_image":"/shop/2/gallery/2017/11/06/15099535283860.jpg","spec_ids":"1461|1464","spec_vids":["779","887"],"goods_number":"100","min_price":"9.51","max_price":"9.51","min_goods_price":"88.00","max_goods_price":"88.00","spec_list":[{"cat_id":"43","attr_id":127,"attr_name":"颜色","is_default":"1","attr_values":{"1461":{"spec_id":"1461","spec_image":null,"attr_vid":"887","attr_value":"红色","attr_desc":null},"1462":{"spec_id":"1462","spec_image":null,"attr_vid":"888","attr_value":"绿色","attr_desc":null},"1463":{"spec_id":"1463","spec_image":null,"attr_vid":"889","attr_value":"紫色","attr_desc":null}}},{"cat_id":"43","attr_id":101,"attr_name":"尺码","is_default":"0","attr_values":{"1464":{"spec_id":"1464","spec_image":null,"attr_vid":"779","attr_value":"S","attr_desc":null},"1465":{"spec_id":"1465","spec_image":null,"attr_vid":"780","attr_value":"L","attr_desc":null},"1466":{"spec_id":"1466","spec_image":null,"attr_vid":"781","attr_value":"M","attr_desc":null}}}],"sku_id":0}]
         * min_all_goods : 101.81
         * max_all_goods : 1210.01
         * min_goods_diff : 90.81
         * max_goods_diff : 1199.01
         */

        public String act_id;
        public String act_name;
        public String act_title;
        public String act_type;
        public String act_img;
        public String start_time;
        public String end_time;
        public String is_finish;
        public String purchase_num;
        public String status;
        public String is_recommend;
        public String shop_id;
        public String site_id;
        public String ext_info;
        public String use_range;
        public String sort;
        public String reason;
        public String price_mode;
        public String act_price;
        public String act_price_format;
        public String act_price_min;
        public String act_price_min_format;
        public String act_price_max;
        public String act_price_max_format;
        public String discount_show;
        public double min_all_goods;
        public String min_all_goods_format;
        public double max_all_goods;
        public String max_all_goods_format;
        public double min_goods_diff;
        public String min_goods_diff_format;
        public double max_goods_diff;
        public String max_goods_diff_format;
        public List<GoodsInfoBean> goods_info;

        public static class GoodsInfoBean {
            /**
             * act_id : 375
             * act_price : 0.30
             * goods_id : 1045
             * goods_name : 康师傅香凝荔枝饮品450ml
             * goods_price : 2.80
             * goods_image : /system/config/default_image/default_goods_image_0.png
             * spec_ids : null
             * spec_vids : [""]
             * goods_number : 999
             * min_price : 0.30
             * max_price : 0.30
             * min_goods_price : 2.80
             * max_goods_price : 1111.00
             * spec_list : null
             * sku_id : 2902
             */

            public String act_id;
            public String act_price;
            public String goods_id;
            public String goods_name;
            public String goods_price;
            public String goods_image;
            public String spec_ids;
            public String goods_number;
            public String min_price;
            public String max_price;
            public String min_goods_price;
            public String max_goods_price;
            public List<SpecificationModel> spec_list;
            public String sku_id;
            public List<String> spec_vids;

            public String specValue;
        }
    }

}
