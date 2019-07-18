package com.lyzb.jbx.api;

import com.lyzb.jbx.BuildConfig;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Config;

public class UrlConfig {

    public static final String Base_Url = BuildConfig.ENVIRONMENT ? "hbwhv.jibaoh" : "hottext.hbwhv";

    //优惠套餐
    public static final String H5_DISCOUNT = Config.BASE_URL + "/lbsnew/index.html#/favorable";

    //会员服务
    public static final String H5_EXTENSION = Config.BASE_URL + "/lbs/index.html#/favorable";

    //服务管理
    public static final String H5_SERVICE = Config.BASE_URL + "/lbsnew/index.html#/servicemanager?user_guid=%s";

    //批发市场
    public static final String H5_MARKET = Api.H5_MALL_URL;

    //服务协议
    public static final String H5_SERV_AGREEN = Config.BASE_URL + "/lbsnew/index.html#/distAgr";

    //升级智能名片
    public static final String H5_OPEN_VIP = Config.BASE_URL + "/lbsnew/index.html#/unionOpenVip?type=2&fromApp=1";

    //平台服务协议
    public static final String H5_SERVICE_AGR = Config.BASE_URL + "/lbsnew/index.html#/userAgr";

    //经销商合作协议
    public static final String H5_DEALER_AGR = Config.BASE_URL + "/lbsnew/index.html#/distAgr";

    //商品详情H5 goodId
    public static final String GOODS_URL = Config.BASE_URL + "/goods/spu-for-show?id=";

    //H5 商家下载地址
    public static final String H5_MERCHANTS = Config.BASE_URL + "/lbsnew/index.html#/sellerAppDownload";

    //webScoknet地址
    public static final String WEB_API = BuildConfig.ENVIRONMENT ? "ws://m.jibaoh.com/lbsapi/myGsWebsocket" : "ws://m.jbxgo.com/lbsapi/myGsWebsocket";

    //热文文库
    public static final String HOT_LITS = String.format("http://%s.com/home/index?", Base_Url) + "UserGuid=%s&UserId=%s&InviteCode=%s";

    //文章采集
    public static final String ARTICLE_MANGER = String.format("http://%s.com/ArticleManage/AddArtice_Gslm?", Base_Url) + "UserGuid=%s&UserId=%s&InviteCode=%s";

    //广告设置
    public static final String ADVERT_SETTING = String.format("http://%s.com/AdvertManager/MyAdvertList_Gslm?", Base_Url) + "UserGuid=%s&UserId=%s&InviteCode=%s";

    //我的文章
    public static final String HOT_MYARTICLE = String.format("http://%s.com/MyArticle/MyArticleList_Gslm?", Base_Url) + "UserGuid=%s&UserId=%s&InviteCode=%s";

    //热文分析
    public static final String HOT_ANALYSIS = String.format("http://%s.com/MyArticle/WhoSeeMe_Gslm?", Base_Url) + "UserGuid=%s&UserId=%s&InviteCode=%s";
}
