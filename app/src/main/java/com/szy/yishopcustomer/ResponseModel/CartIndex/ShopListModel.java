package com.szy.yishopcustomer.ResponseModel.CartIndex;

import com.szy.yishopcustomer.ResponseModel.Goods.BonusModel;
import com.szy.yishopcustomer.Util.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by lw on 2016/7/6.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class ShopListModel {
    //促销商品在map中的key值
    public static final String TYPE_ACT_LIST = "act_list";

    public boolean select;
    public ArrayList<BonusModel> bonus_list;
    public ShopInfoModel shop_info;

    //由于促销套餐所以要动态解析
//    public TreeMap<String, GoodsModel> goods_list;
//    public TreeMap<String, String> goods_list;
    //由于map后台没法排序，所以数据类型改成list
    public List<GoodsModel> goods_list;

    //用于
    public List<Object> goods_list_copy = new ArrayList<>();


    public TreeMap<String, WholeModel> whole_list;

//    public TreeMap<String, GoodsModel> goods_list_copy_two;
//    //除去促销套餐的数据集合，为了兼容以前的使用
//    public TreeMap<String, GoodsModel> getGoodList() {
//        if (goods_list_copy_two == null || goods_list_copy_two.size() <= 0) {
//            TreeMap<String, GoodsModel> goods_list = new TreeMap<>();
//
//            for (Map.Entry<String, String> entry : this.goods_list.entrySet()) {
//                if (!TYPE_ACT_LIST.equals(entry.getKey())) {
//                    goods_list.put(entry.getKey(), JSON.parseObject(entry.getValue(), GoodsModel.class));
//                }
//            }
//            goods_list_copy_two = goods_list;
//        }
//        return goods_list_copy_two;
//    }
}
