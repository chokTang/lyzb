package com.lyzb.jbx.api;

import com.like.longshaolib.net.BaseHttpUtil;

/**
 * Created by Administrator on 2017/12/31.
 */

public class ApiFrotacy {

    private static final Object monitor = new Object();//防止同时在初始化

    private static IAccountApi accountApi = null;
    private static ICommonApi commonApi = null;
    private static ICampaginApi campaginApi = null;
    private static ICircleApi circleApi = null;
    private static ISchoolApi schoolApi = null;
    private static ISendApi sendApi = null;
    private static IMeApi meApi = null;
    private static IFollowApi followApi = null;
    private static IDynamicApi dynamicApi = null;
    private static ICardCilpApi cardCilpApi = null;
    private static IPhpCommonApi phpCommonApi = null;
    private static IStatisticsApi statisticsApi = null;
    private static IWXApi wxapi = null;

    public static IAccountApi getAccountApiSingleton() {
        synchronized (monitor) {
            if (accountApi == null) {
                accountApi = BaseHttpUtil.getIntance().createReqonseHaveLbsApi(IAccountApi.class);
            }
            return accountApi;
        }
    }

    public static ICommonApi getCommonApiSingleton() {
        synchronized (monitor) {
            if (commonApi == null) {
                commonApi = BaseHttpUtil.getIntance().createReqonseNoLbsapiApi(ICommonApi.class);
            }
            return commonApi;
        }
    }

    public static ICampaginApi getCampaginApiSingleton() {
        synchronized (monitor) {
            if (campaginApi == null) {
                campaginApi = BaseHttpUtil.getIntance().createReqonseHaveLbsApi(ICampaginApi.class);
            }
            return campaginApi;
        }
    }

    public static ICircleApi getCircleApiSingleton() {
        synchronized (monitor) {
            if (circleApi == null) {
                circleApi = BaseHttpUtil.getIntance().createReqonseHaveLbsApi(ICircleApi.class);
            }
            return circleApi;
        }
    }

    public static ISchoolApi getSchoolApiSingleton() {
        synchronized (monitor) {
            if (schoolApi == null) {
                schoolApi = BaseHttpUtil.getIntance().createReqonseHaveLbsApi(ISchoolApi.class);
            }
            return schoolApi;
        }
    }

    public static IMeApi getMeApiSingleton() {
        synchronized (monitor) {
            if (meApi == null) {
                meApi = BaseHttpUtil.getIntance().createReqonseHaveLbsApi(IMeApi.class);
            }
            return meApi;
        }
    }

    public static ISendApi getSendApiSingleton() {
        synchronized (monitor) {
            if (sendApi == null) {
                sendApi = BaseHttpUtil.getIntance().createReqonseHaveLbsApi(ISendApi.class);
            }
            return sendApi;
        }
    }

    public static IPhpCommonApi getPhpCommonApiSingleton() {
        synchronized (monitor) {
            if (phpCommonApi == null) {
                phpCommonApi = BaseHttpUtil.getIntance().createHttpResutApi(IPhpCommonApi.class);
            }
            return phpCommonApi;
        }
    }

    public static IFollowApi getFollowApiSingleton() {
        synchronized (monitor) {
            if (followApi == null) {
                followApi = BaseHttpUtil.getIntance().createReqonseHaveLbsApi(IFollowApi.class);
            }
            return followApi;
        }
    }

    public static IDynamicApi getDynamicApiSingleton() {
        synchronized (monitor) {
            if (dynamicApi == null) {
                dynamicApi = BaseHttpUtil.getIntance().createReqonseHaveLbsApi(IDynamicApi.class);
            }
            return dynamicApi;
        }
    }

    public static ICardCilpApi getCardCilpApiSingleton() {
        synchronized (monitor) {
            if (cardCilpApi == null) {
                cardCilpApi = BaseHttpUtil.getIntance().createReqonseHaveLbsApi(ICardCilpApi.class);
            }
            return cardCilpApi;
        }
    }

    public static IStatisticsApi getStatisticsApiSingleton() {
        synchronized (monitor) {
            if (statisticsApi == null) {
                statisticsApi = BaseHttpUtil.getIntance().createReqonseHaveLbsApi(IStatisticsApi.class);
            }
            return statisticsApi;
        }
    }

    public static IWXApi getWXApi() {
        synchronized (monitor) {
            if (wxapi == null) {
                wxapi = BaseHttpUtil.getIntance().createWXHttpResutApi(IWXApi.class);
            }
            return wxapi;
        }
    }
}
