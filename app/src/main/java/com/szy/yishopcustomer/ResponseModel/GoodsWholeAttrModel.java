package com.szy.yishopcustomer.ResponseModel;

import com.alibaba.fastjson.annotation.JSONField;
import com.szy.yishopcustomer.ResponseModel.Attribute.SpecificationModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Smart on 2017/9/9.
 */

public class GoodsWholeAttrModel {

    /**
     * code : 0
     * data : {"spec_list":{"774":{"spec_id":"774","spec_image":null,"attr_vid":"464","attr_value":"单人","attr_desc":null,"sku_id":"1669","goods_number":"993","goods_price":"6.00"},"775":{"spec_id":"775","spec_image":null,"attr_vid":"466","attr_value":"双人","attr_desc":null,"sku_id":"1670","goods_number":"997","goods_price":"6.00"}},"goods":{"goods_id":"670","goods_name":"测试批发价格","cat_id":"63","cat_id1":"48","cat_id2":"62","cat_id3":"63","shop_id":"6","sku_open":"1","sku_id":"1665","goods_subname":"测试批发价格","goods_price":"4.00","market_price":"6.00","mobile_price":"0.00","give_integral":"0","goods_number":"7949","warn_number":"8","goods_sn":null,"goods_barcode":null,"goods_image":"/taobao-yun-images/539909840468/desc-images/TB2rlJBXmOI.eBjy1zkXXadxFXa_!!58584361.jpg","goods_images":{"767":["","","","",""],"768":["","","","",""],"769":["","","","",""],"770":["","","","",""]},"brand_id":null,"top_layout_id":"0","bottom_layout_id":"0","packing_layout_id":"0","service_layout_id":"0","click_count":"258","keywords":"","goods_info":null,"invoice_type":"0","is_repair":"0","user_discount":"0","stock_mode":"0","comment_num":"0","sale_num":"8","collect_num":"0","goods_audit":"1","goods_status":"1","goods_reason":"","is_delete":"0","is_virtual":"0","is_best":"0","is_new":"0","is_hot":"0","is_promote":"0","contract_ids":"","supplier_id":null,"freight_id":"6","goods_stockcode":"","goods_volume":"","goods_weight":"","goods_remark":null,"goods_sort":"255","add_time":"1504493916","last_time":"1504493916","audit_time":"0","edit_items":null,"act_id":"237","goods_moq":"1","lib_goods_id":"0","other_attrs":"a:0:{}","filter_attr_ids":"15","filter_attr_vids":"458,230","pricing_mode":"0","goods_unit":"件","sales_area":"0","sales_model":"1","goods_button_name":"","goods_button_url":"","region_code":"13,06,09","is_free":"1","free_set":"0","limit_sale":"0","collect_count":"0","shop_name":"广缘超市","is_supply":"0","brand_name":null,"start_price":"0.00","button_content":"购买按钮显示内容","button_url":null,"show_price":"1","region_name":"河北省 保定市 徐水区","base_sku_list":[{"sku_id":"1665","spec_ids":"767|771|772|773|774","goods_price":"6.00","goods_number":"975","spec_names":"尺码：S|净含量：510ml|适用人数：单人|存储容量：16GB|存储容量：16GB"},{"sku_id":"1666","spec_ids":"767|771|772|773|775","goods_price":"6.00","goods_number":"988","spec_names":"尺码：S|净含量：510ml|适用人数：双人|存储容量：16GB|存储容量：16GB"},{"sku_id":"1667","spec_ids":"768|771|772|773|774","goods_price":"6.00","goods_number":"998","spec_names":"尺码：M|净含量：510ml|适用人数：单人|存储容量：16GB|存储容量：16GB"},{"sku_id":"1668","spec_ids":"768|771|772|773|775","goods_price":"6.00","goods_number":"1000","spec_names":"尺码：M|净含量：510ml|适用人数：双人|存储容量：16GB|存储容量：16GB"},{"sku_id":"1669","spec_ids":"769|771|772|773|774","goods_price":"6.00","goods_number":"993","spec_names":"尺码：S 建议98斤以下|净含量：510ml|适用人数：单人|存储容量：16GB|存储容量：16GB"},{"sku_id":"1670","spec_ids":"769|771|772|773|775","goods_price":"6.00","goods_number":"997","spec_names":"尺码：S 建议98斤以下|净含量：510ml|适用人数：双人|存储容量：16GB|存储容量：16GB"},{"sku_id":"1671","spec_ids":"770|771|772|773|774","goods_price":"6.00","goods_number":"999","spec_names":"尺码：L 建议110-120斤|净含量：510ml|适用人数：单人|存储容量：16GB|存储容量：16GB"},{"sku_id":"1672","spec_ids":"770|771|772|773|775","goods_price":"6.00","goods_number":"999","spec_names":"尺码：L 建议110-120斤|净含量：510ml|适用人数：双人|存储容量：16GB|存储容量：16GB"}],"sku_list":{"767|771|772|773|774":{"sku_id":"1665","spec_ids":"767|771|772|773|774","goods_price":"6.00","goods_number":"975","spec_names":"尺码：S|净含量：510ml|适用人数：单人|存储容量：16GB|存储容量：16GB"},"767|771|772|773|775":{"sku_id":"1666","spec_ids":"767|771|772|773|775","goods_price":"6.00","goods_number":"988","spec_names":"尺码：S|净含量：510ml|适用人数：双人|存储容量：16GB|存储容量：16GB"},"768|771|772|773|774":{"sku_id":"1667","spec_ids":"768|771|772|773|774","goods_price":"6.00","goods_number":"998","spec_names":"尺码：M|净含量：510ml|适用人数：单人|存储容量：16GB|存储容量：16GB"},"768|771|772|773|775":{"sku_id":"1668","spec_ids":"768|771|772|773|775","goods_price":"6.00","goods_number":"1000","spec_names":"尺码：M|净含量：510ml|适用人数：双人|存储容量：16GB|存储容量：16GB"},"769|771|772|773|774":{"sku_id":"1669","spec_ids":"769|771|772|773|774","goods_price":"6.00","goods_number":"993","spec_names":"尺码：S 建议98斤以下|净含量：510ml|适用人数：单人|存储容量：16GB|存储容量：16GB"},"769|771|772|773|775":{"sku_id":"1670","spec_ids":"769|771|772|773|775","goods_price":"6.00","goods_number":"997","spec_names":"尺码：S 建议98斤以下|净含量：510ml|适用人数：双人|存储容量：16GB|存储容量：16GB"},"770|771|772|773|774":{"sku_id":"1671","spec_ids":"770|771|772|773|774","goods_price":"6.00","goods_number":"999","spec_names":"尺码：L 建议110-120斤|净含量：510ml|适用人数：单人|存储容量：16GB|存储容量：16GB"},"770|771|772|773|775":{"sku_id":"1672","spec_ids":"770|771|772|773|775","goods_price":"6.00","goods_number":"999","spec_names":"尺码：L 建议110-120斤|净含量：510ml|适用人数：双人|存储容量：16GB|存储容量：16GB"}},"spec_list":[{"cat_id":"63","attr_id":13,"attr_name":"尺码","is_default":"1","attr_values":{"767":{"spec_id":"767","spec_image":"","attr_vid":"177","attr_value":"S","attr_desc":null},"768":{"spec_id":"768","spec_image":"","attr_vid":"178","attr_value":"M","attr_desc":null},"769":{"spec_id":"769","spec_image":"","attr_vid":"226","attr_value":"S 建议98斤以下","attr_desc":null},"770":{"spec_id":"770","spec_image":"","attr_vid":"228","attr_value":"L 建议110-120斤","attr_desc":null}}},{"cat_id":"63","attr_id":44,"attr_name":"存储容量","is_default":"0","attr_values":{"771":{"spec_id":"771","spec_image":null,"attr_vid":"518","attr_value":"16GB","attr_desc":null}}},{"cat_id":"63","attr_id":7,"attr_name":"净含量","is_default":"0","attr_values":{"772":{"spec_id":"772","spec_image":null,"attr_vid":"124","attr_value":"510ml","attr_desc":null}}},{"cat_id":"63","attr_id":43,"attr_name":"存储容量","is_default":"0","attr_values":{"773":{"spec_id":"773","spec_image":null,"attr_vid":"514","attr_value":"16GB","attr_desc":null}}},{"cat_id":"63","attr_id":34,"attr_name":"适用人数","is_default":"0","attr_values":{"774":{"spec_id":"774","spec_image":null,"attr_vid":"464","attr_value":"单人","attr_desc":null,"is_invalid":0},"775":{"spec_id":"775","spec_image":null,"attr_vid":"466","attr_value":"双人","attr_desc":null,"is_invalid":0}}}],"attr_list":[{"attr_name":"产地","is_brand":0,"attr_values":"江西省,中国大陆"}],"contract_list":null,"packing_layout":"","service_layout":"","question_list":[{"questions_id":"1","shop_id":"6","question":"你叫什么","answer":"我叫商之翼","sort":"2"},{"questions_id":"3","shop_id":"6","question":"过雨樱桃血满枝","answer":"弄色奇花红间紫","sort":"3"},{"questions_id":"2","shop_id":"6","question":"世界如此美妙","answer":"你却如此暴躁","sort":"233"}],"comment_count":"0","comment":null,"unit_name":"件","is_compare":false,"is_collect":false,"shop_collect":false,"show_sale_number":"1"},"context":{"current_time":1504921734,"user_info":{"user_id":137,"user_name":"SZY130XFXD3148","nickname":"SZY130XFXD3148","headimg":"/upload/images/2017/07/28/15012253692037.jpg","email":null,"email_validated":0,"mobile":"13001163148","mobile_validated":1,"is_seller":0,"shop_id":0,"last_time":1504921240,"last_ip":"100.97.126.36","last_region_code":"13,06,09","user_rank":{"rank_id":12,"rank_name":"银牌会员","rank_img":"/backend/1/images/2017/04/12/14919738234059.jpg","min_points":11,"max_points":1000,"type":0,"is_special":0}},"config":{"mall_logo":"/system/config/mall/mall_logo_0.jpg","site_name":"商之翼·云商城","user_center_logo":"/system/config/mall/user_center_logo_0.png","mall_region_code":"44,12,02","mall_region_name":{"44":"广东省","44,12":"肇庆市","44,12,02":"端州区"},"mall_address":"秦皇半岛51号楼3层　","site_icp":"630102020001131verfdf","site_copyright":"<script type=\"text/javascript\"><\/script>秦皇岛懒到家（www.qhd001.com）版权所有，并保留所有权利。<\/br>63010202000113","site_powered_by":"","mall_phone":"13333344125","mall_email":"zhaoyunlong@68ecshop.com","mall_wx_qrcode":"/system/config/mall/mall_wx_qrcode_0.jpg","mall_qq":"111111111","mall_wangwang":"111111111111","default_user_portrait":"/system/config/default_image/default_user_portrait_0.png","favicon":"http://68yun.oss-cn-beijing.aliyuncs.com/images/746/system/config/website/favicon_0.jpg","aliim_enable":"0"},"cart":{"goods_count":6}}}
     * message :
     */

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {

        public Map<String,SpecListBean> spec_list;
        public String goods;
        public String context;
        public static class SpecListBean {
                /**
                 * spec_id : 774
                 * spec_image : null
                 * attr_vid : 464
                 * attr_value : 单人
                 * attr_desc : null
                 * sku_id : 1669
                 * goods_number : 993
                 * goods_price : 6.00
                 */

                public String spec_id;
                public String spec_image;
                public String attr_vid;
                public String attr_value;
                public String attr_desc;
                public String sku_id;
                public String goods_number;
                public String goods_price;
        }
    }
}
