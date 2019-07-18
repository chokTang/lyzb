package com.szy.yishopcustomer.Constant;

import android.Manifest;


import com.lyzb.jbx.BuildConfig;

import java.net.URI;

/**
 * Created by liuzhifeng on 2017/1/5.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class Business {
    private static String BASE_URL = Config.BASE_URL;
    //动态获取的权限
    public static final String[] PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public static final int PEIRMISSION_CODE = 10001;
    public static final String PATTERN_WHOLESALE_STORE_WEBVIEW = "^(?:https://|http://){0,1}" + getBaseWebUrl();
    public static final String PATTERN_JI_SHI_HUI_WEBVIEW = "^(?:https://|http://){0,1}" + getJiShiHuiBaseWebUrl();

    /**
     * 判断link url 是否为同城生活 weburl
     */
    public static final String PATTERN_WEB_MENU = Api.WEB_SAMECITY;
    public static final String PATTERN_WEB_CITYLIFE = Api.WEB_SAMCITY_T;

    /**
     * 判断link url 是否包含 http
     */
    public static final String PATTERN_WEB = ".*http.*";

    public static final String PATTERN_GET_INGOT = "^page/getIngot";
    public static final String PATTERN_MY_INGOT = "^page/myIngot";
    public static final String PATTERN_ARTICLE_TWO = "^article/(\\d+)$";
    public static final String PATTERN_ARTICLE_LIST = "^(/article/list/\\d+.html)$";
    public static final String PATTERN_BALANCE_ONE = "^page/balance$";
    public static final String PATTERN_COLLECTION_ONE = "^page/collection$";
    public static final String PATTERN_PAGE_GROUP_ON = "^page/groupon$";
    public static final String PATTERN_FORCE_INSIDE_GOODS_DETAIL = "^link/" + BASE_URL + "/goods-([0-9]+).html$";
    public static final String PATTERN_FORCE_INSIDE = "^link/(.*?)$";
    public static final String PATTERN_GOODS_FOUR = "^goods/(\\d+)$";
    public static final String PATTERN_GOODS_LIST_EIGHT = "^/search\\.html\\?{0,1}(.*)";
    public static final String PATTERN_GOODS_LIST_SEVEN = "^brand/(\\d+)$";
    public static final String PATTERN_GOODS_LIST_SIX = "^category/(\\d+)$";
    public static final String PATTERN_SHOP_GOODS_LIST = "^category/(\\d+)/shop_id=(\\d+)$";
    public static final String PATTERN_GROUP_BUY_ONE = "^page/group_buy$";
    public static final String PATTERN_GROUP_BUY_SHOP = "^page/group_buy/shop_id=(\\d+)$";
    public static final String PATTERN_GROUP_BUY_LIST = "page/group_buy/(\\d+)";

    public static final String PATTERN_USER_CARD = "^page/user/card$";

    public static final String PATTERN_GOODS_LIST_BRAND_ID = "^brand_id/(\\d+)";
    public static final String PATTERN_ORDER_LIST_ONE = "^page/order$";
    public static final String PATTERN_PAGE_CART = "^page/cart$";
    public static final String PATTERN_PAGE_INDEX = "^page/index$";
    public static final String PATTERN_PAGE_CATEGORY = "^page/category$";
    public static final String PATTERN_PAGE_SHOP_STREET = "^page/shop_street$";
    public static final String PATTERN_PAGE_INVENTORY = "^page/inventory$";
    public static final String PATTERN_PAGE_EXCHANGE = "^page/exchange$";

    public static final String PATTERN_PAGE_USER = "^page/user$";
    public static final String PATTERN_PHONE = "^tel\\:(\\d+)$";
    public static final String PATTERN_SHOP_STREET_THREE = "^page/shop_street$";
    public static final String PATTERN_SHOP_TWO = "^shop/(\\d+)$";

    private static final String PATTERN_PREFIX = "^(?:https://|http://){0,1}(?:[A-Z_a-z]+\\.){0," +
            "1}(?:" + getDomain(BASE_URL) + "){0,1}/";

    public static final String PATTERN_SHOP_GOODS_LIST_THREE = PATTERN_PREFIX + "shop\\-list\\-" +
            "(\\d+)\\-(\\d+)\\-(\\d+)\\-(\\d+)\\-(\\d+)\\-(\\d+)\\-(\\d+)\\-" +
            "(\\d|asc|desc|ASC|DESC)\\-(\\d+)\\-(0|1|grid|list)";
    public static final String PATTERN_SHOP_GOODS_LIST_TWO = PATTERN_PREFIX + "shop\\-list\\-" +
            "(\\d+)\\-(\\d+)";
    private static final String PATTERN_SUFFIX = "(?:\\.html){0,1}$";
    public static final String PATTERN_ARTICLE_ONE = PATTERN_PREFIX + "article/(\\d+)" +
            PATTERN_SUFFIX;
    public static final String PATTERN_CHECKOUT = PATTERN_PREFIX + "checkout" + PATTERN_SUFFIX;

    public static final String PATTERN_SHOP_LIST = PATTERN_PREFIX + "shop-list-(\\d+)-(\\d+)" + PATTERN_SUFFIX;

    public static final String PATTERN_GOODS_LIST_FIVE = PATTERN_PREFIX + "list\\-(\\d+)\\-(\\d+)" +
            "\\-(\\d+)\\-(\\d+)\\-(\\d+)\\-(\\d+)\\-(\\d+)\\-(\\d+)\\-(\\d|asc|desc|ASC|DESC)\\-" +
            "(\\d+(?:_\\d+)*)\\-(0|1|grid|list)\\-(\\d+(?:_\\d+)*)\\-(\\d+)\\-(\\d+)\\-(\\d+" +
            "(?:_\\d+)*(?:\\-\\d+(?:_\\d+)*)*)+" + PATTERN_SUFFIX;
    public static final String PATTERN_GOODS_LIST_FOUR = PATTERN_PREFIX + "list\\-(\\d+)\\-(\\d+)" +
            "\\-(\\d+)\\-(\\d+)\\-(\\d+)\\-(\\d+)\\-(\\d+)\\-(\\d+)\\-(\\d|asc|desc|ASC|DESC)\\-" +
            "(\\d+(?:_\\d+)*)\\-(0|1|grid|list)\\-(\\d+(?:_\\d+)*)\\-(\\d+)\\-(\\d+)" +
            PATTERN_SUFFIX;
    public static final String PATTERN_GOODS_LIST_ONE = PATTERN_PREFIX + "list\\-(\\d+)" +
            PATTERN_SUFFIX;
    public static final String PATTERN_GOODS_LIST_THREE = PATTERN_PREFIX + "list\\-(\\d+)\\-" +
            "(\\d+)\\-(\\d+)\\-(\\d+)\\-(\\d+)\\-(\\d+)\\-(\\d+)\\-(\\d+)\\-" +
            "(\\d|asc|desc|ASC|DESC)\\-(\\d+(?:_\\d+)*)\\-(0|1|grid|list)\\-(\\d+(?:_\\d+)*)" +
            PATTERN_SUFFIX;
    public static final String PATTERN_GOODS_LIST_TWO = PATTERN_PREFIX + "list\\-(\\d+)\\-(\\d+)" +
            "\\-(\\d+)\\-(\\d+)\\-(\\d+)\\-(\\d+)\\-(\\d+)\\-(\\d+)\\-(\\d|asc|desc|ASC|DESC)\\-" +
            "(\\d+(?:_\\d+)*)\\-(0|1|grid|list)" + PATTERN_SUFFIX;
    public static final String PATTERN_GOODS_ONE = PATTERN_PREFIX + "(\\d+)" + PATTERN_SUFFIX;
    public static final String PATTERN_GOODS_THREE = PATTERN_PREFIX + "goods-(\\d+)" +
            PATTERN_SUFFIX;
    public static final String PATTERN_GOODS_TWO = PATTERN_PREFIX + "goods/(\\d+)" + PATTERN_SUFFIX;
    public static final String PATTERN_GROUP_BUY_LIST_FIVE = PATTERN_PREFIX +
            "group\\-buy\\-list\\-(\\d+)\\-(\\d+)\\-(\\d+)\\-(\\d+)\\-(\\d|asc|desc|ASC|DESC)" +
            PATTERN_SUFFIX;
    public static final String PATTERN_GROUP_BUY_LIST_FOUR = PATTERN_PREFIX +
            "group\\-buy\\-list\\-(\\d+)\\-(\\d+)\\-(\\d+)" + PATTERN_SUFFIX;
    public static final String PATTERN_GROUP_BUY_LIST_ONE = PATTERN_PREFIX + "group\\-buy\\-list"
            + PATTERN_SUFFIX;
    public static final String PATTERN_GROUP_BUY_LIST_SIX = PATTERN_PREFIX +
            "activity/group\\-buy" + PATTERN_SUFFIX;
    public static final String PATTERN_GROUP_BUY_LIST_THREE = PATTERN_PREFIX +
            "group\\-buy\\-list\\-(\\d+)\\-(\\d+)" + PATTERN_SUFFIX;
    public static final String PATTERN_GROUP_BUY_LIST_TWO = PATTERN_PREFIX +
            "group\\-buy\\-list\\-(\\d+)" + PATTERN_SUFFIX;
    public static final String PATTERN_INSIDE = "(" + PATTERN_PREFIX + "(?:.*?)" + PATTERN_SUFFIX
            + ")";
    public static final String PATTERN_LOGIN = PATTERN_PREFIX + "login" + PATTERN_SUFFIX;
    public static final String PATTERN_SHOP_GOODS_LIST_ONE = PATTERN_PREFIX + "shop\\-list\\-" +
            "(\\d+)" + PATTERN_SUFFIX;
    public static final String PATTERN_SHOP_ONE = PATTERN_PREFIX + "shop/(\\d+)" + PATTERN_SUFFIX;
    public static final String PATTERN_SHOP_STREET_ONE = PATTERN_PREFIX + "shops" + PATTERN_SUFFIX;
    public static final String PATTERN_SHOP_STREET_TWO = PATTERN_PREFIX + "shop/street/index" +
            PATTERN_SUFFIX;

    public static final String PATTERN_INTEGRAL_OUT_LINE_PAYREACHBUY = PATTERN_PREFIX + "user/integral/payment" + PATTERN_SUFFIX;
    public static final String PATTERN_INTEGRAL_OUT_LINE_PAYREACHBUY_SHOP = PATTERN_PREFIX + "user/integral/payment\\.html\\?shop_id=(\\d+)";

    public static final String PATTERN_PAGE_ARTICLE_LIST = "page/article_cat/(\\d+)" +
            PATTERN_SUFFIX;
    public static final String PATTERN_PAGE_TOPIC = PATTERN_PREFIX + "topic/(\\d+)" + PATTERN_SUFFIX;
    public static final String PATTERN_GROUPON_SHOP = "page/groupon/shop_id=(\\d+)" +
            PATTERN_SUFFIX;
    public static final String PATTERN_SHOP_CLASS = "^page/shop/class$";

    public static String getDomain(String urlString) {
        URI uri = URI.create(urlString);
        String host = uri.getHost();
        try {
            return host.substring(host.indexOf(".") + 1);
        } catch (Exception e) {

        }
        return host;
    }

    public static String getBaseWebUrl() {
        String baseUrl = Config.BASE_URL;
        String str = baseUrl.substring(baseUrl.indexOf(".", 9));
        if (BuildConfig.DEBUG) {
            return "mkt" + str;
        } else {
            return "market" + str;
        }

    }

    public static String getJiShiHuiBaseWebUrl() {
        String baseUrl = Config.BASE_URL;
        if (BuildConfig.DEBUG) {
            return "apijshtest.lyzb.cn";
        } else {
            return "apijsh.lyzb.cn";
        }

    }

    /***
     * 同城生活 专题页面url 来源-首页banner
     * @return
     */
    public static boolean isCityH5Url(String url) {

        if (url.contains("lbsnew/index.html") || url.contains("lbs/index.html")) {
            return true;
        } else {
            return false;
        }
    }
}
