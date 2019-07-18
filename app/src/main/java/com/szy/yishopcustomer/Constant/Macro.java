package com.szy.yishopcustomer.Constant;

/**
 * Created by 宗仁 on 16/8/1.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class Macro {
    public static final int ORDER_CONFIRM = 0;  // 订单已确认
    public static final int ORDER_FINISHED = 1; // 交易成功
    public static final int ORDER_DISABLE = 2;  // 交易关闭-无效
    public static final int ORDER_CANCEL = 3;  // 交易关闭-取消
    public static final int ORDER_SYS_CANCEL = 4; //交易关闭-系统自动取消
    public static final int ORDER_SCRAMBLE = 10; // 抢单中

    /*
    * 投诉商家的状态
    */
    public static final int CS_WAIT = 0; //等待卖家回复
    public static final int CS_REPLIED = 1; //卖家已回复
    public static final int CS_CANCELLED = 2; //买家撤销投诉
    public static final int CS_INVOLVE = 3; //平台方介入
    public static final int CS_ARBITRATION = 4; //平台方仲裁中
    public static final int CS_ARBITRATION_SUCCESS = 5; //仲裁成功
    public static final int CS_ARBITRATION_FAIL = 6; // 仲裁失败


    public static final int PS_UNPAYED = 0; // 未付款
    public static final int PS_PAID = 1;  // 已付款

    public static final int SS_UNSHIPPED = 0;  // 待发货
    public static final int SS_SHIPPED = 1;  // 已发货
    public static final int SS_SHIPPED_PART = 2;// 发货中

    public static final int ORDER_CANCEL_APPLY = 1;// 买家申请取消订单
    public static final int ORDER_CANCEL_AGREE = 2;// 卖家同意取消订单
    public static final int ORDER_CANCEL_REFUSE = 3;// 卖家拒绝取消订单

    public static final String ORDER_TYPE_ALL = "null";
    public static final String ORDER_TYPE_UNPAID = "unpayed";
    public static final String ORDER_TYPE_AWAIT_SHIPPED = "unshipped";
    public static final String ORDER_TYPE_SHIPPED = "shipped";
    public static final String ORDER_TYPE_AWAIT_REVIEWED = "unevaluate";
    public static final String ORDER_TYPE_FINISHED = "finished";

    public static final String COD_CODE = "cod";
    public static final String ALIPAY_CODE = "alipay";
    public static final String WEIXIN_CODE = "app_weixin";
    public static final String UNIONPAY_CODE = "union";
    public static final String TO_PAY_CODE = "to_pay";
    public static final String PAY_TYPE_CHECKOUT = "PAY_TYPE_CHECKOUT";
    public static final String PAY_TYPE_ORDER = "PAY_TYPE_ORDER";
    public static final String PAY_TYPE_RECHARGE = "PAY_TYPE_RECHARGE";
    public static final String PAY_TYPE_GROUPON = "PAY_TYPE_GROUPON";

    public static final String SHARE_QQ = "SHARE_QQ";
    public static final String SHARE_QZONE = "SHARE_QZONE";
    public static final String SHARE_WEIXIN = "SHARE_WEIXIN";
    public static final String SHARE_WEIXIN_CIRCLE = "SHARE_WEIXIN_CIRCLE";
    public static final String SHARE_WEIXIN_MINI = "SHARE_WEIXIN_MINI";
    public static final String SHARE_WEIBO = "SHARE_WEIBO";
    public static final String SHARE_SMS = "SHARE_SMS";
    public static final String SHARE_LINK = "SHARE_LINK";
    public static final String SHARE_QR_CODE = "SHARE_QR_CODE";

    public static final String RESULT_TYPE_BUY_NOW = "RESULT_TYPE_BUY_NOW";
    public static final String RESULT_TYPE_ADD_TO_CART = "RESULT_TYPE_ADD_TO_CART";
    public static final String RESULT_TYPE_ATTRIBUTE_SELECTED = "RESULT_TYPE_ATTRIBUTE_SELECTED ";

    public static final int SHARE_SUCCEED = 1;
    public static final int SHARE_FAILED = 2;
    public static final int SHARE_CANCELED = 3;

    public static final int STYLE_GRID = 0;
    public static final int STYLE_LIST = 1;

    public static final String SORT_BY_PRICE = "goods_price";
    public static final String SORT_BY_COMMENT = "comment_num";
    public static final String SORT_BY_TIME = "last_time";
    public static final String SORT_BY_SALE = "sale_num";
    public static final String SORT_BY_COLLECTION = "collect_num";
    public static final String SORT_BY_COMPOSITE = "goods_sort";

    public static final String ORDER_BY_DESC = "DESC";
    public static final String ORDER_BY_ASC = "ASC";

    /*
     * 商品活动类型
     */
    public static final int GT_AUCTION_GOODS = 1;//拍卖商品
    public static final int GT_PRE_SALE_GOODS = 2;//预售商品
    public static final int GT_GROUP_BUY_GOODS = 3;//团购商品
    public static final int GT_EXCHANGE_GOODS = 4;//积分商城
    public static final int OT_FIGHT_GROUP = 6;//拼团
    public static final int GT_BARGAIN_GOODS = 8;//砍价商品
    public static final int GT_LIMITED_DISCOUNT_GOODS = 11;// 限时折扣商品
    public static final int GT_FULL_CUT_GOODS = 12;//满减送
    public static final int GT_GIFT_GOODS = 13;//赠品

    public static final String GROUPON_UNDERLINE = "-1";//拼团下期预告

    public static final int ACTIVITY_TYPE_BONUS = 0;

    public static final int ACTIVITY_TYPE_GOODS_MIX = 10;



    public static final int ACTIVITY_TYPE_NO_START_GROUP_BUY = 66;//团购

    /*
     * 服务器返回的模版代码
     */
    public static final String TEMPLATE_CODE_SHOP_STREET = "m_shop_street";
    public static final String TEMPLATE_CODE_AD_COLUMN = "m_ad_s1";
    public static final String TEMPLATE_CODE_AD_COLUMN_THREE = "m_ad_s2";
    public static final String TEMPLATE_CODE_AD_COLUMN_FOUR = "m_ad_s4";
    public static final String TEMPLATE_CODE_AD_BANNER = "m_banner";
    public static final String TEMPLATE_CODE_GOODS_TWO_COLUMN = "m_goods_floor_s1";
    public static final String TEMPLATE_CODE_GOODS_PROMOTION = "m_goods_promotion";
    public static final String TEMPLATE_CODE_GOODS_THREE_COLUMN = "m_goods_floor_s2";
    public static final String TEMPLATE_CODE_NAVIGATION = "m_nav_s1";
    public static final String TEMPLATE_CODE_NAVIGATION_FIVE = "m_nav_s2";
    public static final String TEMPLATE_CODE_BLANK_LINE = "m_blank_line";
    public static final String TEMPLATE_CODE_GOODS_TITLE = "m_goods_title_s1";
    public static final String TEMPLATE_CODE_AD_TITLE = "m_ad_title_s2";
    public static final String TEMPLATE_CODE_AD_TITLE_TWO = "m_ad_title_s1";//标题板式二
    public static final String TEMPLATE_CODE_ARTICLE = "m_article";
    public static final String TEMPLATE_CODE_FLOAT_AD = "m_ad_s5";
    public static final String TEMPLATE_CODE_GOODS_LIST_DUMMY = "m_goods_list"; //仅用来表示支持支持上拉加载商品
    public static final String TEMPLATE_CODE_SHOP_LIST_DUMMY = "m_near_shop"; //仅用来表示支持上拉加载店铺
    public static final String TEMPLATE_CODE_NOTICE = "m_article_s2"; //自定义公告
    public static final String TEMPLATE_CODE_VIDEO = "m_video"; //视频模板

    public static final String TEMPLATE_CODE_INGOT = "page/myIngot";
    /*
     * APP添加的模版代码
     */
    public static final String TEMPLATE_CODE_LOADING = "TEMPLATE_CODE_LOADING";
    public static final String TEMPLATE_CODE_LOAD_DISABLED = "TEMPLATE_CODE_LOAD_DISABLED";
    public static final String TEMPLATE_CODE_SHOP_LIST_TITLE = "TEMPLATE_CODE_SHOP_LIST_TITLE";
    public static final String TEMPLATE_CODE_SHOP_LIST = "TEMPLATE_CODE_SHOP_LIST";
    public static final String TEMPLATE_CODE_GOODS_LIST_TITLE = "TEMPLATE_CODE_GOODS_LIST_TITLE";
    public static final String TEMPLATE_CODE_GOODS_LIST = "m_goods_list";

    public static final String TEMPLATE_CODE_GOODS_DUMMY = "m_near_shop";
    public static final String TEMPLATE_CODE_GOODS_DUMMY_TITLE = "TEMPLATE_CODE_GOODS_DUMMY_TITLE";
    public static final String RESULT_TYPE_BUY_NOW_GROUP = "RESULT_TYPE_BUY_NOW_GROUP";
    public static final String TEMPLATE_CODE_GUESS_LIKE_TITLE = "INDEX_GUESS_LIKE_TITLE";/*首页 猜你喜欢title*/
    public static final String TEMPLATE_CODE_GUESS_LIKE = "INDEX_GUESS_LIKE";/*首页 猜你喜欢*/

    public static final String TEMPLATE_VIEW_LEFT_RIGHT_TITLE= "left_right_title";/*左右都有字的title*/
    public static final String TEMPLATE_VIEW_TYPE_AD_ADVERT= "advert";/*元宝换购上面的banner广告位*/
    public static final String TEMPLATE_CODE_INGOTS_BUY = "goods";/*首页元宝换购(模块码)*/
    public static final String TEMPLATE_CODE_NEAR_SHOP = "shopAndGoods";/*首页附近商家(模块码)*/
    public static final String TEMPLATE_CODE_SHOP_JION = "shop_num";/*首页商家入驻广告(模块码)*/
    public static final String TEMPLATE_CODE_SHOP_NEW_JOIN= "shopNew";/*首页最新入驻(模块码)*/
    public static final String TEMPLATE_CODE_NET_HOT_SHOP= "shopHot";/*首页网红商家(模块码)*/
    public static final String TEMPLATE_CODE_LIKE_SHOP= "shopLike";/*首页最受欢迎商家(模块码)*/
    public static final String TEMPLATE_CODE_HOME_VIDEO_SHOW= "live";/*首页视频直播(模块码)*/

    /*
     * 加入购物车按钮类型
     */
    public static final int BUTTON_TYPE_ATTRIBUTE = 0;//属性
    public static final int BUTTON_TYPE_ADD_TO_CART = 1;//加入购物车
    public static final int BUTTON_TYPE_BUY_NOW = 2;//立即购买
    public static final int BUTTON_TYPE_GROUP_ON_ATTRIBUTE = 3;//拼团页面属性按钮
    public static final int BUTTON_TYPE_GROUP_ON_NOW = 4;//x人团按钮
    public static final int BUTTON_TYPE_GROUP_ON_SINGLE = 5;//拼团单独购买
    public static final int BUTTON_TYPE_REACHBUY = 7;
    public static final int BUTTON_TYPE_ADD_TO_CART_GOODSLIST = 8;
    public static final int BUTTON_TYPE_PROMOTION = 9;

    //购买类型，可以在结果页使用来修改样式
    public static final int CART_BUY = 0;//加入购物车购买
    public static final int QUICK_BUY = 1;//立即购买
    public static final int GO_CHECKOUT = 2;//去结算
    public static final int EXCHANGE = 3;//立即兑换
    public static final int FREEBUY = 4;//自由购
    public static final int REACHBUY = 5;//到店购
    public static final int GIFT = 6;//礼品提货


}
