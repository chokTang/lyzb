package com.szy.yishopcustomer.ResponseModel.Goods;

import com.szy.yishopcustomer.ResponseModel.Attribute.SkuModel;
import com.szy.yishopcustomer.ResponseModel.Attribute.SpecificationModel;

import java.util.List;
import java.util.Map;

/**
 * Created by lw on 2016/7/20.
 */
public class GoodsModel {
    public String goods_id;//"30",
    public String goods_name;//"Daphne/达芙妮2016夏季新款 优雅粗方跟露趾高跟凉鞋女1016303032 黄色 37",
    public String cat_id;//"228",
    public String shop_id;//"2",
    public String sku_open;//"1",
    public String sku_id;//"79",
    public String sku_name;
    public String goods_subname;//"",
    public String goods_price;//"199.00",
    public String market_price;//null,
    public String mobile_price;//"0.00",
    public String give_integral;//"0",
    public String goods_number;//"45",
    public String warn_number;//"0",
    public String goods_sn;//"",
    public String goods_barcode;//"",
    public String goods_image;//"/shop/2/2016/06/08/14653790595546.jpg",
    public String brand_id;//"0",
    public String top_layout_id;//"0",
    public String bottom_layout_id;//"0",
    public String click_count;//"0",
    public String keywords;//null,
    public String goods_info;//null,
    public String invoice_type;//"1",
    public String is_repair;//"1",
    public String user_discount;//"1",
    public String stock_mode;//"0",
    public String comment_num;//"1",
    public String sale_num;//"4",
    public String collect_num;//"1",
    public String goods_audit;//"1",
    public String goods_status;//"1",
    public String goods_reason;//null,
    public String is_delete;//"0",
    public String is_virtual;//"0",
    public String is_best;//"0",
    public String is_new;//"0",
    public String is_hot;//"0",
    public String is_promote;//"0",
    public String contract_ids;//null,
    public String supplier_id;//null,
    public String freight_id;//"2",
    public String goods_volume;//null,
    public String goods_weight;//null,
    public String goods_remark;//null,
    public String goods_sort;//"255",
    public String add_time;//"1465379122",
    public String last_time;//"1467164804",
    public String act_id;//"0",
    public String region_code;//"13,03,02",
    public String is_free;//"0",
    public String free_set;//"0",
    public String limit_sale;//"0",
    public String comment_count;
    public CommentItemModel comment;
    public boolean is_collect;// false,
    public boolean shop_collect;//false
    public String region_name;//"河北省 秦皇岛市 海港区",
    public Map<String, SkuModel> sku_list;
    public List<SpecificationModel> spec_list;
    public List<AttrListModel> attr_list;
    public int goods_moq;
    public List<ContractModel> contract_list;
    public String goods_video;
    public boolean show_sale_number;

    public String unit_name;
    public String max_integral_num;
}