package com.szy.yishopcustomer.ResponseModel.Goods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwei on 2016/12/10.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DataModel {
    public GoodsModel goods;
    public SkuModel sku;
    public ShopInfoModel shop_info;
    public String shop_goods_count;//0",
    public String shop_collect_count;//6",
    public String comment_count;// "1",
    public ContextModel context;
    public ArrayList<BonusModel> bonus_list;
    public ArrayList<PickUpModel> pickup;
    public ArrayList<RankPriceModel> rank_prices;
    public Boolean show_freight_region;
    public boolean can_not_buy;
    public ShareModel share;

    public String invite_code;

    public List<TopGoodsModel> sale_top_list;
    public List<TopGoodsModel> collect_top_list;
    public boolean show_stock;
    public String rank_message;//"您享受吃瓜群众价：￥0.01"
    public String his_id;//soket通信用到的
}
