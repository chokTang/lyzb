package com.szy.yishopcustomer.ResponseModel;

import com.szy.yishopcustomer.Constant.Macro;

/**
 * Created by 宗仁 on 2016/9/2.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class SortModel {
    public String name;//综合",
    public String param;//"sort",
    public String value;//0,
    public String sort;//"goods_sort",
    public String order;//0,
    public String url;//"list.html?cat_id=4&go=1&brand_id=139_78&filter_attr=0-0-0-0&style=grid",
    public String selected;//1

    @Override
    public String toString() {
        String name = this.name;
        if (sort.equalsIgnoreCase(Macro.SORT_BY_SALE)) {
            if (order.equalsIgnoreCase(Macro.ORDER_BY_DESC)) {
                name = "销量从高到低";
            } else if (order.equalsIgnoreCase(Macro.ORDER_BY_ASC)) {
                name = "销量从低到高";
            }
        } else if (sort.equalsIgnoreCase(Macro.SORT_BY_TIME)) {
            if (order.equalsIgnoreCase(Macro.ORDER_BY_DESC)) {
                name = "最新发布";
            } else if (order.equalsIgnoreCase(Macro.ORDER_BY_ASC)) {
                name = "最早发布";
            }

        } else if (sort.equalsIgnoreCase(Macro.SORT_BY_PRICE)) {
            if (order.equalsIgnoreCase(Macro.ORDER_BY_DESC)) {
                name = "价格从高到低";
            } else if (order.equalsIgnoreCase(Macro.ORDER_BY_ASC)) {
                name = "价格从低到高";
            }

        } else if (sort.equalsIgnoreCase(Macro.SORT_BY_COLLECTION)) {
            if (order.equalsIgnoreCase(Macro.ORDER_BY_DESC)) {
                name = "人气从高到低";
            } else if (order.equalsIgnoreCase(Macro.ORDER_BY_ASC)) {
                name = "人气从低到高";
            }

        } else if (sort.equalsIgnoreCase(Macro.SORT_BY_COMMENT)) {
            if (order.equalsIgnoreCase(Macro.ORDER_BY_DESC)) {
                name = "评论从高到低";
            } else if (order.equalsIgnoreCase(Macro.ORDER_BY_ASC)) {
                name = "评论从低到高";
            }
        }

        return name;
    }
}
