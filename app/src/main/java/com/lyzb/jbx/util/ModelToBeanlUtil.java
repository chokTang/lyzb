package com.lyzb.jbx.util;

import android.text.TextUtils;

import com.lyzb.jbx.model.send.DefaultProductModel;
import com.lyzb.jbx.model.send.GoodsModel;

/**
 * Created by :TYK
 * Date: 2019/5/14  15:15
 * Desc:
 */
public class ModelToBeanlUtil {
    public static GoodsModel doDefaultProModelToGoodsMode(DefaultProductModel defaultProductModel) {
        GoodsModel goodsModel = new GoodsModel();
        goodsModel.setGoods_id(defaultProductModel.getGoods_id());
        if (!TextUtils.isEmpty(defaultProductModel.getSku_name())){
            goodsModel.setGoods_name(defaultProductModel.getSku_name());
        }else {
            goodsModel.setGoods_name(defaultProductModel.getGoods().getGoods_name());
        }
        goodsModel.setGoods_image(defaultProductModel.getGoods_image());
        goodsModel.setGoods_price(defaultProductModel.getGoods_price());
        goodsModel.setShop_name(defaultProductModel.getShop_name());
        goodsModel.setMax_integral_num(defaultProductModel.getMax_integral_num());
        goodsModel.setSelected(defaultProductModel.isSelected());
        return goodsModel;
    }
}
