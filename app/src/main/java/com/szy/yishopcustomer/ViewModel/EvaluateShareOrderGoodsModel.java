package com.szy.yishopcustomer.ViewModel;

/**
 * Created by buqingqiang on 2016/6/3.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class EvaluateShareOrderGoodsModel {
    private String mGoodsImage;
    private String mGoodsName;

    public EvaluateShareOrderGoodsModel(String goodsImage, String goodsName) {
        mGoodsImage = goodsImage;
        mGoodsName = goodsName;
    }

    public String getGoodsImage() {
        return mGoodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        mGoodsImage = goodsImage;
    }

    public String getGoodsName() {
        return mGoodsName;
    }

    public void setGoodsName(String goodsName) {
        mGoodsName = goodsName;
    }
}
