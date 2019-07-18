package com.szy.yishopcustomer.ResponseModel;

import com.szy.yishopcustomer.ResponseModel.Goods.BuyenableModel;

import java.util.List;

/**
 * Created by 宗仁 on 2016/8/8 0008.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsModel {
    public List<String> shop_cat_ids;
    public String brand_id;
    public String cat_id;
    public String click_count;
    public String collect_id;
    public String collect_num;
    public String comment_num;
    public String gift_sku_id;
    public String give_integral;
    public String goods_image;
    public String goods_name;
    public String goods_price;
    public String goods_id;
    public int is_best;
    public String is_free;
    public int is_hot;
    public int is_new;
    public String is_promote;
    public String market_price;
    public String mobile_price;
    public String sale_num;
    public String shop_cat_id;
    public String shop_id;
    public String shop_name;
    public String shop_type;
    public String sku_id;
    public String warn_number;
    public TopCatModel top_cat;
    public boolean isChecked = false;
    public boolean isShown = false;
    public int goods_number = 1;
    public int cart_num;
    public BuyenableModel buy_enable;
    public int show_sale_number = 1;
    public String sales_model = "";
    public String goods_price_format;

    public String goods_dk_price_format;
    public String goods_dk_price;

    public String total_sale_count;
    public String max_integral_num;
    public String sold_rate;
    public String goods_bxprice_format;
}
