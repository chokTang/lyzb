package com.szy.yishopcustomer.Util;

import com.szy.common.Util.BaseUrlManager;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Business;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Other.ActivityPatternModel;
import com.szy.yishopcustomer.Other.BonusPatternModel;
import com.szy.yishopcustomer.Other.EventPatternModel;
import com.szy.yishopcustomer.Other.PhonePatternModel;

/**
 * Created by 宗仁 on 2017/1/6.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class YSCBaseUrlManager extends BaseUrlManager {
    public YSCBaseUrlManager(String link) {
        super();
        addPattern(new PhonePatternModel(Business.PATTERN_PHONE));

        addPattern(new EventPatternModel(Business.PATTERN_PAGE_CART, EventWhat
                .EVENT_OPEN_CART_TAB));
        addPattern(new EventPatternModel(Business.PATTERN_PAGE_INDEX, EventWhat
                .EVENT_OPEN_INDEX));
        addPattern(new EventPatternModel(Business.PATTERN_PAGE_CATEGORY, EventWhat
                .EVENT_OPEN_CATEGORY_TAB));
        addPattern(new EventPatternModel(Business.PATTERN_PAGE_USER, EventWhat
                .EVENT_OPEN_USER_TAB));
/*        addPattern(new EventPatternModel(Business.PATTERN_PAGE_INVENTORY, EventWhat
                .EVENT_OPEN_INVENTORY_TAB));*/
        addPattern(new ActivityPatternModel(Business.PATTERN_PAGE_INVENTORY, "InventoryActivity",
                true));
        addPattern(new ActivityPatternModel(Business.PATTERN_PAGE_EXCHANGE, "IntegralMallActivity",
                false));

        addPattern(new EventPatternModel(Business.PATTERN_PAGE_SHOP_STREET, EventWhat
                .EVENT_OPEN_SHOP_STREET_TAB));

        addPattern(new ActivityPatternModel(Business.PATTERN_ORDER_LIST_ONE, "OrderListActivity",
                true).addReplacement(Key.KEY_ORDER_STATUS.getValue(), Macro.ORDER_TYPE_ALL));

        addPattern(new ActivityPatternModel(Business.PATTERN_COLLECTION_ONE,
                "CollectionActivity", true));
        addPattern(new ActivityPatternModel(Business.PATTERN_PAGE_GROUP_ON,
                "GroupOnListActivity", false));
        addPattern(new ActivityPatternModel(Business.PATTERN_BALANCE_ONE,
                "AccountBalanceActivity", true));
        addPattern(new ActivityPatternModel(Business.PATTERN_GOODS_ONE, "GoodsActivity").
                addReplacement(Key.KEY_GOODS_ID.getValue(), "$1"));
        addPattern(new ActivityPatternModel(Business.PATTERN_GOODS_TWO, "GoodsActivity").
                addReplacement(Key.KEY_GOODS_ID.getValue(), "$1"));
        addPattern(new ActivityPatternModel(Business.PATTERN_GOODS_THREE, "GoodsActivity").
                addReplacement(Key.KEY_GOODS_ID.getValue(), "$1"));
        addPattern(new ActivityPatternModel(Business.PATTERN_GOODS_FOUR, "GoodsActivity").
                addReplacement(Key.KEY_GOODS_ID.getValue(), "$1"));
        addPattern(new ActivityPatternModel(Business.PATTERN_ARTICLE_ONE, "YSCWebViewActivity").
                addReplacement(Key.KEY_URL.getValue(), Api.API_ARTICLE + "$1"));
        addPattern(new ActivityPatternModel(Business.PATTERN_ARTICLE_TWO, "YSCWebViewActivity").
                addReplacement(Key.KEY_URL.getValue(), Api.API_ARTICLE + "$1"));

        addPattern(new ActivityPatternModel(Business.PATTERN_ARTICLE_LIST, "YSCWebViewActivity").
                addReplacement(Key.KEY_URL.getValue(), Config.BASE_URL + "$1"));
        addPattern(new ActivityPatternModel(Business.PATTERN_SHOP_STREET_ONE,
                "ShopStreetActivity"));
        addPattern(new ActivityPatternModel(Business.PATTERN_SHOP_STREET_TWO,
                "ShopStreetActivity"));
        addPattern(new ActivityPatternModel(Business.PATTERN_SHOP_STREET_THREE,
                "ShopStreetActivity"));
        addPattern(new ActivityPatternModel(Business.PATTERN_SHOP_ONE, "ShopActivity").
                addReplacement(Key.KEY_SHOP_ID.getValue(), "$1"));
        addPattern(new ActivityPatternModel(Business.PATTERN_SHOP_TWO, "ShopActivity").
                addReplacement(Key.KEY_SHOP_ID.getValue(), "$1"));

        addPattern(new ActivityPatternModel(Business.PATTERN_SHOP_LIST, "ShopActivity").
                addReplacement(Key.KEY_SHOP_ID.getValue(), "$1").
                addReplacement(Key.KEY_CATEGORY.getValue(), "$2"));

        addPattern(new ActivityPatternModel(Business.PATTERN_INTEGRAL_OUT_LINE_PAYREACHBUY, "IntegralOutLinePayActivity"));
        addPattern(new ActivityPatternModel(Business.PATTERN_INTEGRAL_OUT_LINE_PAYREACHBUY_SHOP, "IntegralOutLinePayActivity").addReplacement(Key.KEY_SHOP_ID.getValue(), "$1"));

        addPattern(new ActivityPatternModel(Business.PATTERN_GROUP_BUY_LIST_ONE,

                "GroupBuyListActivity"));

        addPattern(new ActivityPatternModel(Business.PATTERN_GROUP_BUY_LIST_TWO,
                "GroupBuyListActivity").
                addReplacement(Key.KEY_GROUP_BUY_ACT_ID.getValue(), "$1"));
        addPattern(new ActivityPatternModel(Business.PATTERN_GROUP_BUY_LIST_THREE,
                "GroupBuyListActivity").
                addReplacement(Key.KEY_GROUP_BUY_ACT_ID.getValue(), "$1").
                addReplacement(Key.KEY_CATEGORY.getValue(), "$2"));
        addPattern(new ActivityPatternModel(Business.PATTERN_GROUP_BUY_LIST_FOUR,
                "GroupBuyListActivity").
                addReplacement(Key.KEY_GROUP_BUY_ACT_ID.getValue(), "$1").
                addReplacement(Key.KEY_CATEGORY.getValue(), "$2").
                addReplacement(Key.KEY_PAGE.getValue(), "$3"));
        addPattern(new ActivityPatternModel(Business.PATTERN_GROUP_BUY_LIST_FIVE,
                "GroupBuyListActivity").
                addReplacement(Key.KEY_GROUP_BUY_ACT_ID.getValue(), "$1").
                addReplacement(Key.KEY_CATEGORY.getValue(), "$2").
                addReplacement(Key.KEY_PAGE.getValue(), "$3").
                addReplacement(Key.KEY_SORT.getValue(), "$4").
                addReplacement(Key.KEY_ORDER.getValue(), "$5"));
        addPattern(new ActivityPatternModel(Business.PATTERN_GROUP_BUY_LIST_SIX,
                "GroupBuyListActivity"));

        addPattern(new ActivityPatternModel(Business.PATTERN_GROUP_BUY_ONE, "GroupBuyActivity"));
        addPattern(new ActivityPatternModel(Business.PATTERN_USER_CARD, "UserCardActivity"));

        addPattern(new ActivityPatternModel(Business.PATTERN_GOODS_LIST_ONE, "GoodsListActivity").
                addReplacement(Key.KEY_CATEGORY.getValue(), "$1"));
        addPattern(new ActivityPatternModel(Business.PATTERN_GOODS_LIST_TWO, "GoodsListActivity").
                addReplacement(Key.KEY_CATEGORY.getValue(), "$1").
                addReplacement(Key.KEY_PAGE.getValue(), "$2").
                addReplacement(Key.KEY_TYPE.getValue(), "$3").
                addReplacement(Key.KEY_IS_SELF.getValue(), "$4").
                addReplacement(Key.KEY_IS_FREE.getValue(), "$5").
                addReplacement(Key.KEY_IS_CASH.getValue(), "$6").
                addReplacement(Key.KEY_IS_STOCK.getValue(), "$7").
                addReplacement(Key.KEY_SORT.getValue(), "$8").
                addReplacement(Key.KEY_ORDER.getValue(), "$9").
                addReplacement(Key.KEY_REGION.getValue(), "$10").
                addReplacement(Key.KEY_STYLE.getValue(), "$11"));
        addPattern(new ActivityPatternModel(Business.PATTERN_GOODS_LIST_THREE, "GoodsListActivity").
                addReplacement(Key.KEY_CATEGORY.getValue(), "$1").
                addReplacement(Key.KEY_PAGE.getValue(), "$2").
                addReplacement(Key.KEY_TYPE.getValue(), "$3").
                addReplacement(Key.KEY_IS_SELF.getValue(), "$4").
                addReplacement(Key.KEY_IS_FREE.getValue(), "$5").
                addReplacement(Key.KEY_IS_CASH.getValue(), "$6").
                addReplacement(Key.KEY_IS_STOCK.getValue(), "$7").
                addReplacement(Key.KEY_SORT.getValue(), "$8").
                addReplacement(Key.KEY_ORDER.getValue(), "$9").
                addReplacement(Key.KEY_REGION.getValue(), "$10").
                addReplacement(Key.KEY_STYLE.getValue(), "$11").
                addReplacement(Key.KEY_GOODS_BRAND_ID.getValue(), "$12"));
        addPattern(new ActivityPatternModel(Business.PATTERN_GOODS_LIST_BRAND_ID,
                "GoodsListActivity").
                addReplacement(Key.KEY_GOODS_BRAND_ID.getValue(), "$1"));
        addPattern(new ActivityPatternModel(Business.PATTERN_GOODS_LIST_FOUR, "GoodsListActivity").
                addReplacement(Key.KEY_CATEGORY.getValue(), "$1").
                addReplacement(Key.KEY_PAGE.getValue(), "$2").
                addReplacement(Key.KEY_TYPE.getValue(), "$3").
                addReplacement(Key.KEY_IS_SELF.getValue(), "$4").
                addReplacement(Key.KEY_IS_FREE.getValue(), "$5").
                addReplacement(Key.KEY_IS_CASH.getValue(), "$6").
                addReplacement(Key.KEY_IS_STOCK.getValue(), "$7").
                addReplacement(Key.KEY_SORT.getValue(), "$8").
                addReplacement(Key.KEY_ORDER.getValue(), "$9").
                addReplacement(Key.KEY_REGION.getValue(), "$10").
                addReplacement(Key.KEY_STYLE.getValue(), "$11").
                addReplacement(Key.KEY_GOODS_BRAND_ID.getValue(), "$12").
                addReplacement(Key.KEY_PRICE_MIN.getValue(), "$13").
                addReplacement(Key.KEY_PRICE_MAX.getValue(), "$14"));
        addPattern(new ActivityPatternModel(Business.PATTERN_GOODS_LIST_FIVE, "GoodsListActivity").
                addReplacement(Key.KEY_CATEGORY.getValue(), "$1").
                addReplacement(Key.KEY_PAGE.getValue(), "$2").
                addReplacement(Key.KEY_TYPE.getValue(), "$3").
                addReplacement(Key.KEY_IS_SELF.getValue(), "$4").
                addReplacement(Key.KEY_IS_FREE.getValue(), "$5").
                addReplacement(Key.KEY_IS_CASH.getValue(), "$6").
                addReplacement(Key.KEY_IS_STOCK.getValue(), "$7").
                addReplacement(Key.KEY_SORT.getValue(), "$8").
                addReplacement(Key.KEY_ORDER.getValue(), "$9").
                addReplacement(Key.KEY_REGION.getValue(), "$10").
                addReplacement(Key.KEY_STYLE.getValue(), "$11").
                addReplacement(Key.KEY_GOODS_BRAND_ID.getValue(), "$12").
                addReplacement(Key.KEY_PRICE_MIN.getValue(), "$13").
                addReplacement(Key.KEY_PRICE_MAX.getValue(), "$14").
                addReplacement(Key.KEY_FILTER.getValue(), "$15"));
        addPattern(new ActivityPatternModel(Business.PATTERN_GOODS_LIST_SIX, "GoodsListActivity").
                addReplacement(Key.KEY_CATEGORY.getValue(), "$1"));
        addPattern(new ActivityPatternModel(Business.PATTERN_GROUP_BUY_LIST,
                "GroupBuyListActivity").
                addReplacement(Key.KEY_GROUP_BUY_ACT_ID.getValue(), "$1"));
        addPattern(new ActivityPatternModel(Business.PATTERN_GOODS_LIST_SEVEN, "GoodsListActivity").
                addReplacement(Key.KEY_GROUP_BUY_ACT_ID.getValue(), "$1"));
        addPattern(new BonusPatternModel(Business.PATTERN_GOODS_LIST_EIGHT));

        addPattern(new ActivityPatternModel(Business.PATTERN_FORCE_INSIDE_GOODS_DETAIL, "GoodsActivity").
                addReplacement(Key.KEY_GOODS_ID.getValue(), "$1"));

        addPattern(new ActivityPatternModel(Business.PATTERN_FORCE_INSIDE, "YSCWebViewActivity").
                addReplacement(Key.KEY_URL.getValue(), "$1"));
        addPattern(new ActivityPatternModel(Business.PATTERN_PAGE_ARTICLE_LIST,
                "YSCWebViewActivity").
                addReplacement(Key.KEY_URL.getValue(), Api.API_ARTICLE_LIST + "?cat_id=" + "$1"));

        addPattern(new ActivityPatternModel(Business.PATTERN_PAGE_TOPIC, "YSCWebViewActivity").
                addReplacement(Key.KEY_URL.getValue(), Api.API_ARTICLE_TOPIC + "$1"));

        addPattern(new ActivityPatternModel(Business.PATTERN_SHOP_GOODS_LIST_ONE,
                "ShopGoodsListActivity").
                addReplacement(Key.KEY_SHOP_ID.getValue(), "$1"));
        addPattern(new ActivityPatternModel(Business.PATTERN_SHOP_GOODS_LIST_TWO,
                "ShopGoodsListActivity").
                addReplacement(Key.KEY_SHOP_ID.getValue(), "$1").
                addReplacement(Key.KEY_CATEGORY.getValue(), "$2"));
        addPattern(new ActivityPatternModel(Business.PATTERN_SHOP_GOODS_LIST_THREE,
                "ShopGoodsListActivity").
                addReplacement(Key.KEY_SHOP_ID.getValue(), "$1").
                addReplacement(Key.KEY_CATEGORY.getValue(), "$2").
                addReplacement(Key.KEY_PAGE.getValue(), "$3").
                addReplacement(Key.KEY_IS_FREE.getValue(), "$4").
                addReplacement(Key.KEY_IS_CASH.getValue(), "$5").
                addReplacement(Key.KEY_IS_STOCK.getValue(), "$6").
                addReplacement(Key.KEY_SORT.getValue(), "$7").
                addReplacement(Key.KEY_REGION.getValue(), "$8").
                addReplacement(Key.KEY_ORDER.getValue(), "$9").
                addReplacement(Key.KEY_STYLE.getValue(), "$10"));
        addPattern(new ActivityPatternModel(Business.PATTERN_GROUPON_SHOP, "GroupOnListActivity").
                addReplacement(Key.KEY_SHOP_ID.getValue(), "$1"));
        addPattern(new ActivityPatternModel(Business.PATTERN_GROUP_BUY_SHOP, "GroupBuyActivity").
                addReplacement(Key.KEY_SHOP_ID.getValue(), "$1"));
        addPattern(new ActivityPatternModel(Business.PATTERN_SHOP_GOODS_LIST, "GoodsListActivity").
                addReplacement(Key.KEY_CATEGORY.getValue(), "$1").
                addReplacement(Key.KEY_SHOP_ID.getValue(), "$2"));
        addPattern(new ActivityPatternModel(Business.PATTERN_SHOP_CLASS, "ShopClassActivity"));

        addPattern(new ActivityPatternModel(Business.PATTERN_JI_SHI_HUI_WEBVIEW, "MallWebviewActivity", true).
                addReplacement(Key.KEY_URL.getValue(), link));

        addPattern(new ActivityPatternModel(Business.PATTERN_WEB_MENU, "ProjectH5Activity", false).
                addReplacement(Key.KEY_URL.getValue(), link));

        addPattern(new ActivityPatternModel(Business.PATTERN_WEB_CITYLIFE, "ProjectH5Activity", false).
                addReplacement(Key.KEY_URL.getValue(), link));

        addPattern(new ActivityPatternModel(Business.PATTERN_WEB, "YSCWebViewActivity", false).
                addReplacement(Key.KEY_URL.getValue(), link));

        addPattern(new ActivityPatternModel(Business.PATTERN_MY_INGOT, "IngotListActivity", true));
        addPattern(new ActivityPatternModel(Business.PATTERN_GET_INGOT, "GetIngotActivity", false));
    }
}
