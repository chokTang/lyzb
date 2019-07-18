package com.szy.yishopcustomer.Other;

import com.szy.common.Other.CommonEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 宗仁 on 2017/1/22.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsNumberEvent extends CommonEvent {
    public List<GoodsEventModel> goodsInCart;

    public GoodsNumberEvent(int what, Object messageSource, List<GoodsEventModel> goodsList) {
        this(what, messageSource);
        goodsInCart.addAll(goodsList);
    }

    public GoodsNumberEvent(int what, Object messageSource, GoodsEventModel goodsModel) {
        this(what, messageSource);
        goodsInCart.add(goodsModel);
    }

    public GoodsNumberEvent(int what, Object messageSource) {
        super(what);
        setMessageSource(messageSource.getClass().getSimpleName());
        goodsInCart = new ArrayList<>();
    }
}
