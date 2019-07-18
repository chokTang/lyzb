package com.szy.yishopcustomer.ResponseModel.Checkout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.ResponseModel.CartIndex.ActInfo;
import com.szy.yishopcustomer.ResponseModel.CartIndex.GoodsModel;
import com.szy.yishopcustomer.ResponseModel.Goods.ActivityModel;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by 宗仁 on 2016/7/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShopItemModel {
    public String select;
    public List<BonusItemModel> bonus_list;
    public ShopInfoModel shop_info;
    public List<GoodsItemModel> goods_list;

    public TreeMap<String, String> act_list;

    public List<ShipItemModel> shipping_list;
    public ShopOrderModel order;
    public List limit_goods_ids;
    public List<StoreCardModel> store_card_list;

    public String shop_integral_num;



    //将act促销得商品转换一下数据添加到goodlist中
    //促销商品和普通商品得样子是一摸一样得，本来就应该在goodlist中
    //因为某些原因才单独提出来(参考购物车得促销)
    public void addGoodListForActList() {
        if (goods_list != null && act_list != null) {
            for (Map.Entry<String, String> actInfoEntry : act_list.entrySet()) {
                TreeMap<String, ActInfo> stringActInfoTreeMap = JSON.parseObject(actInfoEntry.getValue(), new TypeReference<TreeMap<String, ActInfo>>() {
                });
                for (Map.Entry<String, ActInfo> entry1 : stringActInfoTreeMap.entrySet()) {
                    for(Map.Entry<String, GoodsModel> entry2 : entry1.getValue().goods_list.entrySet()) {


                        //之后得代码可以提取出来，等以后用到再说。
                        GoodsModel goodsModel =  entry2.getValue();
                        GoodsItemModel goodsItemModel = new GoodsItemModel();
                        Field[] field1 = goodsItemModel.getClass().getDeclaredFields();
                        Field[] field2 = goodsModel.getClass().getDeclaredFields();

                        try {
                            for (int j = 0; j < field1.length; j++) { // 遍历所有属性
                                String name = field1[j].getName(); // 获取属性的名字
                                name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                                String type = field1[j].getGenericType().toString(); // 获取属性的类型

                                for(int i = 0 ; i < field2.length ; i ++) {
                                    String name2 = field2[i].getName(); // 获取属性的名字
                                    name2 = name2.substring(0, 1).toUpperCase() + name2.substring(1); // 将属性的首字符大写，方便构造get，set方法
                                    String type2 = field2[i].getGenericType().toString(); // 获取属性的类型

                                    if(type2.equals(type) && name2.equals(name)) {
                                        Field f1 = field1[j];
                                        Field f2 = field2[i];
                                        f1.setAccessible(true);// 设置些属性是可以访问的
                                        f2.setAccessible(true);
                                        f1.set(goodsItemModel,f2.get(goodsModel));
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();

                        }

                        if(goodsItemModel.activity == null) {
                            goodsItemModel.activity = new ActivityModel();
                        }
                        goodsItemModel.activity.act_type = Macro.ACTIVITY_TYPE_GOODS_MIX;
                        goods_list.add(goodsItemModel);
                    }
                }
            }
        }
    }


}
