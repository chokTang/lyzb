package com.szy.yishopcustomer.Constant;

/**
 * Created by zongren on 2016/3/16.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 * Put values that remain the same with all clients.
 */
public class Api {

    /****
     * 配置测试环境和正式环境的基地址
     * 需要修改 values-config.xml API接口基地址
     * RELEASE
     */

    /***
     * 同城生活 API url (java端) 正式地址
     */
    public static final String SAME_CITY_URL = "http://m.jbxgo.com/lbsapi";

    /***
     * 同城生活 API 图片拼接地址 基地址
     */
    public static final String API_CITY_HOME_MER_IMG_URL = "https://lyzbjbx.oss-cn-hangzhou.aliyuncs.com/lyzbjbxgo";

    /*** 零售商城 图片拼接 基地址 **/
    public static final String API_HEAD_IMG_URL = "http://lyzbimage.jbxgo.com/lyzbjbxgo";

    /***
     * 首页 菜单栏 同城生活-专题页面url 跳转 正则验证
     * 判断url(link)是否包含 lbsnew/index.html or lbs/index.html
     *  .*lbsnew/index.html.*|.*lbs/index.html.*
     *
     */
    public static final String WEB_SAMECITY = ".*webview_type=2.*";
    public static final String WEB_SAMCITY_T = ".*lbsnew/index.html.*|.*lbs/index.html.*";


    /***
     * 二维码扫描 地址配置
     */
    public static final String SCAN_URL = "m.jbxgo.com";

    /***
     * 邀请好友注册 url
     */
    public static final String API_REGIS_SHARE_INGOT = "http://m.jbxgo.com/register.html";

    /***
     * 批发商城H5 url
     */
    public static final String H5_MALL_URL = "http://mkt.jbxgo.com";

    /***
     * 同城生活H5 url
     * http://m.jbxgo.com/lbsnew/index.html#
     * http://m.jbxgo.com/lbs/index.html#
     * http://192.168.200.126:8080/index#
     */
    public static final String H5_CITYLIFE_URL = "http://m.jbxgo.com/lbsnew/index.html#";


    /***
     * 首页模版url
     * 进入同城生活 home 原生
     */
    public static final String CITYLIFE_HOME = "lbs/home";
    public static final String CITYLIFE_HOME_NEW = "lbsnew/home";

    /***
     * 同城生活 API 图片上传 api
     */
    public static final String API_CITYLIFE_UPIMG = SAME_CITY_URL + "/lbs/oss/ossAuthentication";

    /***
     * 商城 分享元宝  分享地址
     */
    public static final String API_INGOT_PAGEURL = "/lbsnew/index.html#/grabAcer?";

//    /***
//     * TEST 测试地址
//     *
//     * 同城生活 api url (java端)
//     */
//    public static final String SAME_CITY_URL = "http://m.jibaoh.com/lbsapi";
//
//    /***
//     * 同城生活 api 图片拼接地址 基地址
//     */
//    public static final String API_CITY_HOME_MER_IMG_URL = "http://images.jibaoh.com";
//
//    /*** 零售商城 图片拼接 基地址 **/
//    public static final String API_HEAD_IMG_URL = "http://images.jibaoh.com";
//
//    /***
//     * 首页 菜单栏 同城生活-专题页面url 跳转 正则验证
//     * 判断url(link)是否包含 lbsnew/index.html or lbs/index.html
//     *  .*lbsnew/index.html.*|.*lbs/index.html.*
//     *
//     */
//    public static final String WEB_SAMECITY = ".*webview_type=2.*";
//    public static final String WEB_SAMCITY_T = ".*lbsnew/index.html.*|.*lbs/index.html.*";
//
//    /***
//     * 二维码扫描 地址配置
//     */
//    public static final String SCAN_URL = "m.jibaoh.com";
//
//    /***
//     * 邀请好友注册 url
//     */
//    public static final String API_REGIS_SHARE_INGOT = "http://m.jibaoh.com/register.html";
//
//    /***
//     * 批发商城H5 url
//     */
//    public static final String H5_MALL_URL = "http://mkt.jibaoh.com";
//
//    /***
//     * 同城生活H5 url    本地IP: http://192.168.200.126:8080/index#   http://m.jibaoh.com/lbs/index.html#
//     */
//    public static final String H5_CITYLIFE_URL = "http://m.jibaoh.com/lbsnew/index.html#";
//
//    /***
//     * 首页模版url
//     * 进入同城生活 home 原生
//     */
//    public static final String CITYLIFE_HOME = "lbs/home";
//    public static final String CITYLIFE_HOME_NEW = "lbsnew/home";
//
//    /***
//     * 同城生活 API 图片上传 api
//     */
//    public static final String API_CITYLIFE_UPIMG = SAME_CITY_URL + "/lbs/oss/ossAuthentication";
//
//    /***
//     * 商城 分享元宝  分享地址
//     */
//    public static final String API_INGOT_PAGEURL = "/lbsnew/index.html#/grabAcer?";

    /***
     * API 地址
     */
    public static final String API_ACCOUNT_BALANCE = Config.BASE_URL + "/user/center/index";
    public static final String API_ADDRESS_LIST = Config.BASE_URL + "/user/address/list";
    public static final String API_ADD_ADDRESS = Config.BASE_URL + "/user/address/add";
    public static final String API_ADD_TO_CART = Config.BASE_URL + "/cart/add";
    public static final String API_GOODS_MIX_AMOUNT = Config.BASE_URL + "/goods/goods-mix-amount";
    public static final String API_APP_GUIDE = Config.BASE_URL + "/site/app-guide";
    public static final String API_APP_INDEX = Config.BASE_URL + "/site/app-index";
    public static final String API_APP_INFO = Config.BASE_URL + "/site/app-info";
    public static final String API_ARTICLE = Config.BASE_URL + "/article/info?id=";
    public static final String API_ARTICLE_LIST = Config.BASE_URL + "/article/list";
    public static final String API_ARTICLE_TOPIC = Config.BASE_URL + "/topic/";
    public static final String API_BONUS = Config.BASE_URL + "/user/bonus";
    public static final String API_BONUS_DELETE = Config.BASE_URL + "/user/bonus/delete";
    public static final String API_CART_CHANGE_NUMBER = Config.BASE_URL + "/cart/change-number";
    public static final String API_CART_DELETE = Config.BASE_URL + "/cart/delete";
    public static final String API_CART_INDEX = Config.BASE_URL + "/cart/index";
    public static final String API_CART_SELECT = Config.BASE_URL + "/cart/select";
    //public static final String API_CATEGORY = Config.BASE_URL + "/site/cat-list";
    public static final String API_CATEGORY = Config.BASE_URL + "/category";
    public static final String API_CHANGE_BEST_TIME = Config.BASE_URL +
            "/checkout/change-best-time";
    public static final String API_CHANGE_INVOICE = Config.BASE_URL + "/checkout/change-invoice";
    public static final String API_CHANGE_PICKUP = Config.BASE_URL + "/checkout/change-pickup";
    public static final String API_CHANGE_PAYMENT = Config.BASE_URL + "/checkout/change-payment";
    public static final String API_CHECKOUT = Config.BASE_URL + "/checkout";
    public static final String API_CHECKOUT_CHANGE_ADDRESS = Config.BASE_URL +
            "/checkout/change-address";
    public static final String API_CHECKOUT_RESUBMIT = Config.BASE_URL + "/checkout/resubmit";
    public static final String API_CHECKOUT_SUBMIT = Config.BASE_URL + "/checkout/submit";
    public static final String API_CHECK_SMS_CAPTCHA = Config.BASE_URL +
            "/register/check-sms-captcha";
    public static final String API_COLLECTION_GOODS = Config.BASE_URL + "/user/collect/goods";
    public static final String API_COLLECTION_SHOP = Config.BASE_URL + "/user/collect/shop";
    public static final String API_COLLECT_TOGGLE = Config.BASE_URL + "/user/collect/toggle";
    public static final String API_COLLECT_BATCH = Config.BASE_URL + "/user/collect/batch-add-goods";
    public static final String API_DELETE_ADDRESS = Config.BASE_URL + "/user/address/del";
    public static final String API_DEPOSIT_ADD = Config.BASE_URL + "/user/deposit/add";
    public static final String API_DEPOSIT_LIST = Config.BASE_URL + "/user/deposit";
    public static final String API_DEPOSIT_LIST_CANCEL = Config.BASE_URL + "/user/deposit/cancel";
    public static final String API_DEPOSIT_LIST_DELETE = Config.BASE_URL + "/user/deposit/delete";
    public static final String API_CAPITAL_ACCOUNT = Config.BASE_URL + "/user/capital-account";
    public static final String API_EDIT_ADDRESS = Config.BASE_URL + "/user/address/edit";
    public static final String API_EVALUATE = Config.BASE_URL + "/user/evaluate/index";
    public static final String API_EVALUATE_INFO = Config.BASE_URL + "/user/evaluate/info";
    public static final String API_FIND_PASSWORD_EMAIL_CAPTCHA = Config.BASE_URL +
            "/user/find-password/email-captcha";
    public static final String API_FIND_PASSWORD_RESET = Config.BASE_URL +
            "/user/find-password/reset";
    public static final String API_FIND_PASSWORD_SMS_CAPTCHA = Config.BASE_URL +
            "/user/find-password/sms-captcha";
    public static final String API_FIND_PASSWORD_STEP_ONE = Config.BASE_URL + "/user/find-password";
    public static final String API_FIND_PASSWORD_VALIDATE_EMAIL = Config.BASE_URL +
            "/user/find-password/validate?type=email";
    public static final String API_FIND_PASSWORD_VALIDATE_MOBILE = Config.BASE_URL +
            "/user/find-password/validate?type=mobile";
    public static final String API_INVENTORY_CATEGORY = Config.BASE_URL +
            "/user/order/purchased-list-cat-ids";
    public static final String API_INVENTORY_GOODS = Config.BASE_URL + "/user/order/purchased-list";
    public static final String API_GET_REGION_NAME = Config.BASE_URL + "/site/region-gps";
    public static final String API_GET_WEIXIN = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static final String API_GOODS_CHANGE_LOCATION = Config.BASE_URL +
            "/goods/change-location";
    public static final String API_GOODS_COMMENT = Config.BASE_URL + "/goods/comment";
    public static final String API_GOODS_DESC = Config.BASE_URL + "/goods/desc";
    public static final String API_GOODS_DETAIL = Config.BASE_URL + "/goods/";
    public static final String API_FULLCUT_LIST = Config.BASE_URL + "/full-cut-list-";
    public static final String API_GOODS_LIST = Config.BASE_URL + "/list";
    public static final String API_GOODS_LIST_SEARCH = Config.BASE_URL + "/search";
    public static final String API_GOODS_SKU = Config.BASE_URL + "/goods/sku";
    public static final String API_GO_CHECKOUT = Config.BASE_URL + "/cart/go-checkout";
    public static final String API_GROUP_BUY = Config.BASE_URL + "/activity/group-buy";
    public static final String API_GROUP_BUY_LIST = Config.BASE_URL + "/group-buy-list";
    public static final String API_INDEX_SITE = Config.BASE_URL + "/site/tpl-data";
    public static final String API_LOGIN = Config.BASE_URL + "/site/login";
    public static final String API_LOGIN_BIND = Config.BASE_URL + "/bind/bind/mobile-bind";
    public static final String API_CHECK_BIND = Config.BASE_URL + "/bind/bind/check-mobile";
    public static final String API_LOGIN_BIND_SMS_CAPTCHA = Config.BASE_URL +
            "/bind/bind/sms-captcha";
    public static final String API_LOGIN_OTHER = Config.BASE_URL + "/website/act-login";
    public static final String API_MESSAGE = Config.BASE_URL + "/site/internal";
    public static final String API_MESSAGE_INFO = Config.BASE_URL + "/user/message/message-info";
    public static final String API_MESSAGE_NO_READ = Config.BASE_URL +
            "/user/message/messege-count";
    public static final String API_ORDER_CANCEL = Config.BASE_URL + "/user/order/edit-order";
    public static final String API_ORDER_DELETE = Config.BASE_URL + "/user/order/delete";
    public static final String API_ORDER_CANCEL_CONFIRM = Config.BASE_URL + "/user/order/cancel";
    public static final String API_ORDER_CONFIRM = Config.BASE_URL + "/user/order/confirm";
    public static final String API_ORDER_INFO = Config.BASE_URL + "/user/order/info";
    public static final String API_ORDER_DELAY = Config.BASE_URL + "/user/order/delay";
    public static final String API_ORDER_LIST = Config.BASE_URL + "/user/order";
    public static final String API_PAYMENT = Config.BASE_URL + "/payment";
    public static final String API_PUBLISH_SHARE_ORDER = Config.BASE_URL +
            "/user/evaluate/eval-goods";
    public static final String API_PUBLISH_SHARE_REPLY_ORDER = Config.BASE_URL +
            "/user/evaluate/eval-goods";
    public static final String API_PUBLISH_SHOP = Config.BASE_URL + "/user/evaluate/eval-shop";
    public static final String API_QR_CODE = Config.BASE_URL + "/goods/qrcode?id=";
    public static final String API_QUICK_BUY = Config.BASE_URL + "/cart/quick-buy";
    public static final String API_RECEIVE_BONUS = Config.BASE_URL + "/user/bonus/receive";
    public static final String API_RECHARGE = Config.BASE_URL + "/user/recharge/online-recharge";
    public static final String API_REGION_LIST = Config.BASE_URL + "/site/region-list";
    public static final String API_REGION_NAME = Config.BASE_URL + "/site/region-names";
    public static final String API_SALE_REGION_NAME = Config.BASE_URL + "/site/sale-region-names";
    public static final String API_REGISTER_FINISH_MOBILE = Config.BASE_URL + "/register/mobile";
    public static final String API_REGISTER_PHONE_EXIST = Config.BASE_URL +
            "/register/client-validate?model" +
            "=c2VydmljZVxyZWdpc3Rlclxtb2RlbHNcTW9iaWxlUmVnaXN0ZXJNb2RlbA%3D%3D&attribute=mobile";
    public static final String API_REGISTER_SMS_CAPTCHA = Config.BASE_URL + "/register/sms-captcha";
    public static final String API_REMOVE_CART = Config.BASE_URL + "/cart/remove";
    public static final String API_RESULT = Config.BASE_URL + "/checkout/result";
    public static final String API_RESULT_PAY_AGAIN = Config.BASE_URL + "/checkout/result";
    public static final String API_REVIEW_UPLOAD = Config.BASE_URL + "/site/upload-image";
    public static final String API_SCAN_SHOP = Config.BASE_URL + "/list/get-barcode-goods";
    public static final String API_SEARCH = Config.BASE_URL + "/site/hot-search";
    public static final String API_SET_DEFAULT_ADDRESS = Config.BASE_URL +
            "/user/address/set-default";
    public static final String API_SHOP = Config.BASE_URL + "/shop/";
    public static final String API_SHOP_GOODS_LIST = Config.BASE_URL + "/shop/list/index";
    public static final String API_SHOP_QR_CODE = Config.BASE_URL + "/shop/index/qrcode";
    public static final String API_SHOP_STREET = Config.BASE_URL + "/shop/street/shop-street-list";
    public static final String API_SIGN_OFF = Config.BASE_URL + "/site/logout";
    public static final String API_SITE = Config.BASE_URL + "/subsite/selector";
    public static final String API_SITE_SELECT = Config.BASE_URL + "/subsite";
    public static final String API_USER = Config.BASE_URL + "/user";
    public static final String API_USER_ADDRESS = Config.BASE_URL + "/user/address";
    public static final String API_USER_AGREEMENT = Config.BASE_URL + "/register";
    public static final String API_USER_INFO = Config.BASE_URL + "/user/profile/edit-base";
    public static final String API_USER_PAY = Config.BASE_URL + "/checkout/pay";
    public static final String API_USER_PROFILE_INDEX = Config.BASE_URL + "/user/profile/index";
    public static final String API_USER_PROFILE_UPLOAD = Config.BASE_URL + "/user/profile/up-load";
    public static final String API_USER_SET_PAYMENT = Config.BASE_URL + "/checkout/set-payment";
    public static final String API_USER_WEIXIN_LOGIN = Config.BASE_URL + "/login";
    public static final String API_VIEW_EXPRESS = Config.BASE_URL + "/user/order/express";
    public static final String API_VIEW_EXCHANGE_EXPRESS = Config.BASE_URL + "/user/integral/express";
    public static final String DELETE_GOODS_COLLECTION = Config.BASE_URL +
            "/user/collect/delete-collect";
    public static final String API_SHOP_SEARCH = Config.BASE_URL + "/search";
    public static final String API_DEPOSIT_ACCOUNT_VALIDATE = Config.BASE_URL +
            "/user/deposit-account/validate";
    public static final String API_CANCEL_GOODS_LIST_TWO = Config.BASE_URL + "/user/deposit-account/bind";
    public static final String API_CANCEL_GOODS_LIST_SMS = Config.BASE_URL + "/user/deposit-account/sms-captcha";
    public static final String API_DEPOSIT_ACCOUNT_EMAIL_CAPTCHA = Config.BASE_URL +
            "/user/deposit-account/email-captcha";
    public static final String API_BACK_DETAIL_BACK_INFO = Config.BASE_URL + "/user/back/info";
    public static final String API_BACK_CONFIRMSYS = Config.BASE_URL + "/user/back/confirm-sys";
    public static final String API_BACK_CANCELSYS = Config.BASE_URL + "/user/back/cancel-sys";
    public static final String API_BACK_SHIPPEDSYS = Config.BASE_URL + "/user/back/shipped-sys";
    public static final String API_USER_BACK_CANCEL = Config.BASE_URL + "/user/back/cancel";
    public static final String API_BACK_LIST = Config.BASE_URL + "/user/back";
    public static final String API_BACK_APPLY = Config.BASE_URL + "/user/back/apply";
    public static final String API_BACK_EDIT = Config.BASE_URL + "/user/back/edit";
    public static final String API_BACK_DELIVERY = Config.BASE_URL + "/user/back/edit-order";
    public static final String API_USER_GROUP_ON_LIST = Config.BASE_URL + "/user/groupon";
    public static final String API_USER_GROUP_0N_DETAIL = Config.BASE_URL + "/groupon-join-";
    public static final String API_GROUP_ON_LIST = Config.BASE_URL + "/groupon";
    public static final String API_RECHARGE_RECORD = Config.BASE_URL + "/user/recharge";
    public static final String API_SHOP_SUPPORT = Config.BASE_URL + "/shop/support";
    public static final String API_SHOP_SUPPORT_MESSAGE = Config.BASE_URL + "/shop/support/message";
    public static final String API_USER_RECOMMEND_SHOP = Config.BASE_URL + "/user/recommend-shop";
    public static final String API_USER_RECOMMEND_SHOP_ADD = Config.BASE_URL + "/user/recommend-shop/add";
    public static final String API_CANCEL_GROUPON = Config.BASE_URL + "/activity/groupon/cancel";
    public static final String API_GROUPON_RULE = Config.BASE_URL + "/groupon-rule-";

    public static final String API_USER_RECHARGE_CARD = Config.BASE_URL + "/user/recharge-card";
    public static final String API_USER_CARD_RECHARGE_INFO = Config.BASE_URL + "/user/recharge-card/info";

    public static final String API_EXCHANGE_BONUS_LIST = Config.BASE_URL + "/integralmall/index/bonus-list";
    public static final String API_EXCHANGE_BONUS_EXCHANGE = Config.BASE_URL + "/integralmall/index/bonus-exchange";
    //    public static final String API_USER_EXCHANGE = Config.BASE_URL + "/user/exchange";
    public static final String API_USER_EXCHANGE_DETAIL = Config.BASE_URL + "/user/exchange/detail";

    public static final String API_GOODS_SEARCH_PICKUP = Config.BASE_URL + "/goods/search-pickup";

    public static final String API_SECURITY_VALIDATE = Config.BASE_URL +
            "/user/security/validate";
    public static final String API_SECURITY_EDIT_PASSWORD = Config.BASE_URL +
            "/user/security/edit-password";
    public static final String API_SECURITY_EDIT_SURPLUS_PASSWORD = Config.BASE_URL +
            "/user/security/edit-surplus-password";

    public static final String API_SECURITY_CLOSE_SURPLUS_PASSWORD = Config.BASE_URL +
            "/user/security/close-surplus-password";

    public static final String API_SECURITY_EDIT_MOBILE = Config.BASE_URL + "/user/security/edit-mobile";
    public static final String API_SECURITY_SMS_CAPTCHA = Config.BASE_URL +
            "/user/security/sms-captcha";
    public static final String API_SECURITY_EMAIL_CAPTCHA = Config.BASE_URL +
            "/user/security/email-captcha";

    public static final String API_USER_RECOMMEND = Config.BASE_URL +
            "/user/recommend";

    public static final String API_DISTRIBUTOR_APPLY_CHECK = Config.BASE_URL +
            "/distributor/apply/check";
    public static final String API_DISTRIBUTOR_APPLY = Config.BASE_URL +
            "/distributor/apply";
    public static final String API_DISTRIB = Config.BASE_URL +
            "/distrib";
    public static final String API_DISTRIB_INCOME = Config.BASE_URL +
            "/distrib/income";
    public static final String API_DISTRIB_INCOME_ADD = Config.BASE_URL +
            "/distrib/income/add";
    public static final String API_DISTRIB_INCOME_RECORD = Config.BASE_URL +
            "/distrib/income/record";
    public static final String API_DISTRIB_INCOME_DETAILS = Config.BASE_URL +
            "/distrib/income/details";
    public static final String API_DISTRIB_ORDER = Config.BASE_URL +
            "/distrib/order";

    public static final String API_CART_BOX_GOODS_LIST = Config.BASE_URL + "/cart/box-goods-list";

    public static final String API_DISTRIB_TEAM = Config.BASE_URL +
            "/distrib/team";
    public static final String API_DISTRIB_HELP = Config.BASE_URL +
            "/distrib/help";

    public static final String API_USER_ORDER_POSTMAN_INFO = Config.BASE_URL + "/user/order/postmen-info";
    public static final String API_USER_ORDER_GET_PICKUP_ADDRESS = Config.BASE_URL + "/user/order/get-pickup-address";
    public static final String API_FREEBUY_CART_GET_SHOP_BAG = Config.BASE_URL + "/freebuy/cart/get-shopping-bag";
    public static final String API_FREEBUY_CART_BATCH_ADD = Config.BASE_URL + "/freebuy/cart/batch-add";

    public static final String API_FREEBUY_SCAN_BUY_GET_SKU = Config.BASE_URL + "/freebuy/scan-buy/get-sku";
    public static final String API_FREEBUY_CART_ADD = Config.BASE_URL + "/freebuy/cart/add";
    public static final String API_FREEBUY_CART_GOODS_COUNT = Config.BASE_URL + "/freebuy/cart/goods-count";
    public static final String API_FREEBUY_CART_INDEX = Config.BASE_URL + "/freebuy/cart/index";
    public static final String API_FREEBUY_CART_CHANGE_NUMBER = Config.BASE_URL + "/freebuy/cart/change-number";
    public static final String API_FREEBUY_CART_DELETE = Config.BASE_URL + "/freebuy/cart/delete";
    public static final String API_FREEBUY_GO_CHECKOUT = Config.BASE_URL + "/freebuy/cart/go-checkout";
    public static final String API_FREEBUY_ORDER_INFO = Config.BASE_URL + "/user/freebuy-order/info";
    public static final String API_FREEBUY_ORDER_LIST = Config.BASE_URL + "/user/freebuy-order";
    public static final String API_FREEBUY_SEARCH_SHOP = Config.BASE_URL + "/freebuy/search-shop/index";

    public static final String API_SHOP_CLASS = Config.BASE_URL + "/shop/class";
    public static final String API_SET_PASSWORD = Config.BASE_URL + "/bind/bind/set-password";
    public static final String API_ORDER_CANCEL_SYS = Config.BASE_URL + "/user/order/cancel-sys";
    public static final String API_ORDER_CONFIRM_SYS = Config.BASE_URL + "/user/order/confirm-sys";


    public static final String API_USRE_PROFILE_EDIT_REAL = Config.BASE_URL + "/user/profile/edit-real";
    public static final String API_USER_STORE_RECHARGE_CARD = Config.BASE_URL + "/user/store-recharge-card";
    public static final String API_USER_STORE_RECHARGE_CARD_INFO = Config.BASE_URL + "/user/store-recharge-card/info";

    public static final String API_USER_STORE_RECHARGE_CARD_LOG_INFO = Config.BASE_URL + "/user/store-recharge-card/log-list";

    public static final String API_GOODS_GET_WHOLE_ATTR = Config.BASE_URL + "/goods/get-whole-attr";
    public static final String API_GOODS_GET_STEP_PRICE = Config.BASE_URL + "/goods/get-step-price";

    public static final String API_CART_BATCH_ADD = Config.BASE_URL + "/cart/batch-add";
    public static final String API_CART_BATCH_QUICK_BUY = Config.BASE_URL + "/cart/batch-quick-buy";

    public static final String API_DISTRIBUTOR_INDEX = Config.BASE_URL + "/distributor/index";
    public static final String API_DISTRIBUTOR_LIST = Config.BASE_URL + "/distrib/list";
    public static final String API_DISTRIB_LIST_SALE = Config.BASE_URL + "/distrib/list/sale";
    public static final String API_DISTRIB_SHOP_SET = Config.BASE_URL + "/distrib/shop-set/index";
    public static final String API_DISTRIB_SHOP_SET_EDIT_SHOP = Config.BASE_URL + "/distrib/shop-set/edit-shop";

    public static final String API_SHOP_BONUS_LIST = Config.BASE_URL + "/shop/bonus/list";
    public static final String API_REACHBUY = Config.BASE_URL + "/reachbuy";

    public static final String API_REACHBUY_ADD_TO_CART = Config.BASE_URL + "/reachbuy/cart/add";
    public static final String API_REACHBUY_REMOVE_CART = Config.BASE_URL + "/reachbuy/cart/remove";
    public static final String API_REACHBUY_CART_INDEX = Config.BASE_URL + "/reachbuy/cart/index";
    public static final String API_REACHBUY_CART_CHANGE_NUMBER = Config.BASE_URL + "/reachbuy/cart/change-number";
    public static final String API_REACHBUY_CART_DELETE = Config.BASE_URL + "/reachbuy/cart/delete";
    public static final String API_REACHBUY_GO_CHECKOUT = Config.BASE_URL + "/reachbuy/cart/go-checkout";
    public static final String API_REACHBUY_QUICK_BUY = Config.BASE_URL + "/reachbuy/cart/quick-buy";

    public static final String API_REACHBUY_ORDER_LIST = Config.BASE_URL + "/user/reachbuy-order";
    public static final String API_REACHBUY_ORDER_INFO = Config.BASE_URL + "/user/reachbuy-order/info";

    public static final String API_USER_SECURITY = Config.BASE_URL + "/user/security";


    //账户安全
    public static final String API_USER_SECURITY_EDIT_EMAIL = Config.BASE_URL +
            "/user/security/edit-email";
    public static final String API_USER_COMPLAINT_ADD = Config.BASE_URL + "/user/complaint/add";
    public static final String API_USER_COMPLAINT_EDIT = Config.BASE_URL + "/user/complaint/edit";

    public static final String API_USER_GIFT_CARD_INDEX = Config.BASE_URL + "/user/gift-card/index";
    public static final String API_USER_GIFT_CARD_EXIT = Config.BASE_URL + "/user/gift-card/exit";
    public static final String API_USER_GIFT_CARD_GOODS = Config.BASE_URL + "/user/gift-card/goods";
    public static final String API_USER_GIFT_CARD = Config.BASE_URL + "/user/gift-card/";

    public static final String API_CART_ADD_GIFT = Config.BASE_URL + "/cart/add-gift";

    public static final String API_USER_COMPLAINT = Config.BASE_URL + "/user/complaint";
    public static final String API_USER_COMPLAINT_VIEW = Config.BASE_URL + "/user/complaint/view";

    //    http://m.test.68mall.com/package.html?sku_id=2906
    public static final String API_PACKAGE = Config.BASE_URL + "/package";

    public static final String API_USER_COMPLAINT_DEL = Config.BASE_URL + "/user/complaint/del";
    public static final String API_USER_COMPLAINT_INTERVENTION = Config.BASE_URL + "/user/complaint/intervention";

    public static final String API_SECURITY_CLIENT_VALIDATE = Config.BASE_URL + "/user/security/client-validate/";
    public static final String API_SECURITY_CHECKOUT_MODEL = Config.BASE_URL + "/user/security/validate-mobile";
    public static final String API_SECURITY_SEND_CODE = Config.BASE_URL + "/user/security/send-verify-code";
    public static final String API_SECURITY_BIND_MODEL = Config.BASE_URL + "/user/security/update-mobile";
    public static final String API_LOGIN_SMS_CAPTCHA = Config.BASE_URL + "/site/sms-captcha";
    public static final String API_REFRESH_CAPTCHA = Config.BASE_URL + "/site/captcha.html?refresh=1&format=raw";
    public static final String API_CAPTCHA = Config.BASE_URL + "/site/captcha.html";

    public static final String API_MESSAGE_READ = Config.BASE_URL + "/user/message/read";
    public static final String API_MESSAGE_DELETE = Config.BASE_URL + "/user/message/delete";

    public static final String API_SITE_SUBSITE = Config.BASE_URL + "/site/subsite-location";

    public static final String API_INTEGRALMALL = Config.BASE_URL + "/integralmall";
    public static final String API_USER_INTEGRAL_SHOP_POINTS = Config.BASE_URL + "/user/integral/shop-points";
    public static final String API_USER_INTEGRAL_DETAIL = Config.BASE_URL + "/user/integral/detail";
    public static final String API_USER_INTEGRAL_ORDER_LIST = Config.BASE_URL + "/user/integral/order-list";
    public static final String API_USER_INTEGRAL_ORDER_INFO = Config.BASE_URL + "/user/integral/order-info";
    public static final String API_INTEGRAL_QUICK_BUY = Config.BASE_URL + "/integralmall/cart/quick-buy";

    public static final String API_INTEGRAL_CHECKOUT = Config.BASE_URL + "/integralmall/checkout";
    public static final String API_INTEGRAL_CHECKOUT_SUBMIT = Config.BASE_URL + "/integralmall/checkout/submit";

    public static final String API_INTEGRAL_CHANGE_SHIPPING = Config.BASE_URL + "/integralmall/checkout/change-shipping";
    public static final String API_INTEGRAL_CHANGE_CHANGE_BEST_TIME = Config.BASE_URL + "/integralmall/checkout/change-best-time";

    public static final String API_INTEGRAL_RESULT_PAY_AGAIN = Config.BASE_URL + "/integralmall/checkout/result";

    public static final String API_USER_DEPOSIT_ACCOUNT_DELETE = Config.BASE_URL + "/user/deposit-account/delete";

    public static final String API_USER_INTEGRAL_INDEX = Config.BASE_URL + "/user/integral/index";

    public static final String API_USER_SCAN_CODE_INDEX = Config.BASE_URL + "/user/scan-code/index";
    public static final String API_USER_CARD_GET_CODE = Config.BASE_URL + "/user/card/get-code";
    public static final String API_USER_CARD = Config.BASE_URL + "/user/card";

    public static final String API_USER_SCAN_CODE_SCAN = Config.BASE_URL + "/user/scan-code/scan";

    public static final String API_USER_SCAN_CODE_PAYMENT = Config.BASE_URL + "/user/scan-code/payment";

    public static final String API_USER_INTEGRAL_OUT_LINE_PAY = Config.BASE_URL + "/user/integral/payment";

    public static final String API_FULL_CUT_LIST = Config.BASE_URL + "/full-cut-list-";

    public static final int SUCCESS_INT = 200;
    public static final String SUCCESS_STRING = "200";
    public static final String HUND_STRING = "500";


    /*** 零售商城 首页  猜你喜欢 **/
    public static final String API_GUESS_LIKE_URL = Config.BASE_URL + "/guess/prefer";

    /***我的元宝-超值推荐+低价换购 **/
    public static final String API_INGOT_RECOMM = Config.BASE_URL + "/index/default/get-nav-topic-goods.html";
    /***抢元宝记录**/
    public static final String API_INGOT_ROB_RECORD = Config.BASE_URL + "/share/check-detail.html";
    /***赠送元宝**/
    public static final String API_INGOT_SEND = Config.BASE_URL + "/integral/giving-integral.html";
    /***分享元宝**/
    public static final String API_INGOT_SHARE = Config.BASE_URL + "/share/share-integral.html";

    /*** 分享元宝记录 **/
    public static final String API_INGOT_SHARE_RECORD = Config.BASE_URL + "/share/share-detail.html";
    /*** 我的元宝-可用元宝 **/
    public static final String API_INGOT_USABLE = Config.BASE_URL + "/user/account-manage/total-integral.html";
    /*** 我的元宝-元宝明细 **/
    public static final String API_INGOT_DETAIL = Config.BASE_URL + "/integral/integral-detail.html";
    /*** 我的元宝-即将过期 **/
    public static final String API_INGOT_EXPIRE = Config.BASE_URL + "/integral/expire-integral-remind.html";

    /*** 用户邀请码 **/
    public static final String API_USER_CODE = Config.BASE_URL + "/distributor/share/invite-code";
    /*** 批发商城进入权限 API  **/
    public static final String H5_MALL_USER = Config.BASE_URL + "/user/share/mktpermissions";
    /*** 预存货款迁入 获取预存货款数据 **/
    public static final String API_MONEY_DATA = Config.BASE_URL + "/user/jsh-pre-deposit/index";
    /*** 预存货款迁入 数据迁入 **/
    public static final String API_MONEY_INTO = Config.BASE_URL + "/user/jsh-pre-deposit/transfer";
    /*** 预存货款迁入 迁入成功 **/
    public static final String API_MONEY_SUCCESS = Config.BASE_URL + "/user/jsh-pre-deposit/success";
    /*** 广告页面数据 **/
    public static final String API_ADS_INFO = Config.BASE_URL + "/index/information/image-info";
    /*** 商品详情页面 立即砍价 **/
    public static final String API_BARGAIN_NOW = Config.BASE_URL + "/goods/bargain";
    /*** 商品详情页面 帮砍名单 **/
    public static final String API_BARGAIN_HELP_LIST = Config.BASE_URL + "/activity/bargain/help-list";
    /*** 老版本H5页面 分享数据 **/
    public static final String API_H5_SHARE = Config.BASE_URL + "/topic/topic-share";


    /***首页区域商品及店家接口**/
    public static final String API_SHOP_AND_PRODUCT = Config.BASE_URL + "/esapi/lbs/home/goodsAndShops";


    /*** 同城生活 首页附近商家列表 **/
    public static final String API_HOME_NEAR_SHOP_URL = Config.BASE_URL + "/esapi/lbs/home/shopNearby";


    /*** 同城生活 首页元宝换购列表 **/
    public static final String API_HOME_INGOTS_BUY_URL = Config.BASE_URL + "/esapi/lbs/home/repurchase";

    /*** 搜索提示语 **/
    public static final String API_SEARCH_KEY_LIST = Config.BASE_URL + "/esapi/lbs/search/suggest";
    /*** 零售商城 图片购 **/
    public static final String API_IMG_SHOPPING = Config.BASE_URL + "/esapi/lbs/search/image";
    /*** 零售商城 搜索结果 data **/
    public static final String API_SEARCH_RESULT = Config.BASE_URL + "/esapi/lbs/search";


    /*** 同城生活 首页 广告 **/
    public static final String API_CITY_HOME_AD = SAME_CITY_URL + "/lbs/v1/user/homePage";
    /*** 同城生活 推荐商家 **/
    public static final String API_CITY_HOME_MERCHANTS = SAME_CITY_URL + "/lbs/shop";

    /*** 同城生活 城市列表 **/
    public static final String API_CITY_HOME_CITY_LIST = SAME_CITY_URL + "/user/regionCityAll";
    /*** 同城生活 区县列表 **/
    public static final String API_CITY_HOME_COUNTY_LIST = SAME_CITY_URL + "/user/regionCity";

    /*** 同城生活 附近 头部全部（团购美食 品类） **/
    public static final String API_CITY_HOME_NEAR_TITLE = SAME_CITY_URL + "/user/getProductCategoryByMoudle";

    /*** 同城生活 主页  美食 中的筛选 几人套餐品类 **/
    public static final String API_CITY_HOME_FOODS_SELECT = SAME_CITY_URL + "/lbs/selectPersonTypeAllList";

    /*** 同城生活 主页  美食 筛选请求结果 **/
    public static final String API_CITY_HOME_FOODS_SELECT_RESULT = SAME_CITY_URL + "/lbs/classShop";
    /*** 同城生活 附近 列表数据 **/
    public static final String API_CITY_HOME_NEAR_LIST = SAME_CITY_URL + "/lbs/classShop";
    /***  优惠券列表**/
    public static final String API_COUPON_LIST_ONLINE = SAME_CITY_URL + "/lbs/bonus/getShopBonusList";

    /***  核销券列表**/
    public static final String API_COUPON_LIST_OFFLINE = SAME_CITY_URL + "/lbs/group/productGrouponList";
    /*** 同城生活 订单 ***/
    public static final String API_CITY_ORDER_LIST = SAME_CITY_URL + "/lbs/order/getOrdersByUserID";
    /*** 同城生活 订单详情*/
    public static final String API_CITY_ORDER_DETAIL = SAME_CITY_URL + "/lbs/order/getOrderInfo";

    /*** 同城生活 订单列表 取消订单 **/
    public static final String API_CITY_CANCLE_ORDER = SAME_CITY_URL + "/lbs/order/cancelOrder";
    /*** 同城生活 订单列表 删除订单 **/
    public static final String API_CITY_DELETE_ORDER = SAME_CITY_URL + "/lbs/order/hideOrder";
    /*** 同城生活 外卖 详情 确认收货 **/
    public static final String API_CITY_TAKE_ORDER_SUBMIT = SAME_CITY_URL + "/lbs/order/takeOver";
    /*** 同城生活 外卖 申请退款 **/
    public static final String API_CITY_ORDER_APPLY_REFUND = SAME_CITY_URL + "/lbs/order/orderRefund";

    /*** 同城生活 外卖 收货地址列表 **/
    public static final String API_CITY_ADDRESS_LIST = SAME_CITY_URL + "/lbs/user/address/list";

    /*** 同城生活 分享地址加密 **/
    public static final String API_ENC_SHAREURL = SAME_CITY_URL + "/sep";

    /*** 同城生活 分类更多 **/
    public static final String API_MORE_SORT_URL = SAME_CITY_URL + "/user/categoryList";
    /*** 同城生活 店铺搜索 **/
    public static final String API_SEACH_SHOP_URL = SAME_CITY_URL + "/lbs/searchShop";
    /*** 同城生活 团购 分类列表 **/
    public static final String API_SORT_GROUP_URL = SAME_CITY_URL + "/lbs/group/productList";
    /*** 同城 银联支付订单数据 **/
    public static final String API_BANK_PAY_DATA = SAME_CITY_URL + "/lbs/order/orderPay";

    /*** 同城生活 店铺详情 **/
    public static final String API_SHOP_DETAIL_URL = SAME_CITY_URL + "/lbs/shopById";

    /*** 同城生活 店铺详情浮窗数据 **/
    public static final String API_SHOP_DETAIL_FLOATING_URL = SAME_CITY_URL + "/lbs/user/advertByPosition";

    /*** 同城生活 店铺详情全部评论 **/
    public static final String API_SHOP_DETAIL_All_EVALUATION_URL = SAME_CITY_URL + "/lbs/commentList";

    /*** 同城生活 商品详情 **/
    public static final String API_PRODUCT_DETAIL_URL = SAME_CITY_URL + "/lbs/productById";


    /*** 同城生活 下单 **/
    public static final String API_ADD_ORDER_URL = SAME_CITY_URL + "/lbs/order/addOrder";
    /*** 同城生活 支付订单 **/
    public static final String API_PAY_ORDER_URL = SAME_CITY_URL + "/lbs/order/orderPay";


    /***首页 tablebar 图片链接 **/
    public static final String API_HOME_TABLE_BAR_URL = SAME_CITY_URL + "/lbs/home/jbxTabbar";
    /***登录是否完善信息 **/
    public static final String API_LOGIN_IS_PERFECT_MSG = SAME_CITY_URL + "/lbs/gs/user/queryIsPerfect";

    /***IM 获取IM信息*/
    public static final String API_CITY_IM_GET_IMINFO = SAME_CITY_URL + "/lbs/queryUserNameAndPassword";
    public static final String API_CITY_IM_GET_IMINFO_LOGIN = SAME_CITY_URL + "/lbs/saveAndUpdateIMUser";//登陆时候获取IM用户

    /***IM 发送消息*/
    public static final String API_CITY_IM_SENDMESSAGE = SAME_CITY_URL + "/lbs/sendToMessage";


    /****
     * IM 群组内群员信息list
     */
    public static final String API_IM_GROUP_LIST = SAME_CITY_URL + "/lbs/queryGroupMembers";

    /****
     * IM 群组 用户退出群
     *
     */
    public static final String API_IM_GROUP_USER_EXIT = SAME_CITY_URL + "/lbs/deleteGroupMembers";


    /****
     * IM 获取直播推送过来的相关信息
     *
     */
    public static final String API_IM_LIVE_MSG = SAME_CITY_URL + "/live/lite/getLiveRecord";


}