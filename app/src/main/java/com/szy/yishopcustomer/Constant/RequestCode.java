package com.szy.yishopcustomer.Constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zongren on 2016/3/25.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public enum RequestCode {
    REQUEST_CODE_ADDRESS,
    REQUEST_CODE_ADD_CART,
    REQUEST_CODE_BEST_TIME,
    REQUEST_CODE_FILTER,
    REQUEST_CODE_GENDER,
    REQUEST_CODE_GOODS_LIST_CODE,
    REQUEST_CODE_INVOICE_INFO,
    REQUEST_CODE_LOCAL_POSITION,
    REQUEST_CODE_LOGIN_FOR_BALANCE,
    REQUEST_CODE_LOGIN_FOR_COLLECTION,
    REQUEST_CODE_LOGIN_FOR_QUICK_BUY,
    REQUEST_CODE_NICK_NAME,
    REQUEST_CODE_PICK_PHOTO,
    REQUEST_CODE_REGION_CODE,
    REQUEST_CODE_SCAN,
    REQUEST_CODE_SERVICE,
    REQUEST_CODE_SHARE,
    REQUEST_CODE_SHARE_ORDER_IMAGE,
    REQUEST_CODE_TAKE_BONUS,
    REQUEST_CODE_TAKE_PHOTO,
    REQUEST_CODE_TENCENT_QZONE_SHARE,
    REQUEST_CODE_TENCENT_SHARE,
    REQUEST_CODE_UNIONPAY_REQUEST_CODE,
    REQUEST_CODE_WEIBO_SHARE,
    REQUEST_CODE_WEIXIN_CIRCLE_SHARE,
    REQUEST_CODE_WEIXIN_SHARE,
    REQUEST_CODE_WEIXIN_SHARE_MINI,
    REQUEST_CODE_CROP_IMAGE,
    REQUEST_PICK,
    REQUEST_CAPTURE,
    REQUEST_CODE_MESSAGE, REQUEST_CODE_LOGIN_FOR_COLLECT_SHOP, REQUEST_CODE_LOGIN_FOR_REFRESH, REQUEST_CODE_CHANGE_NUMBER;

    private static Map<Integer, RequestCode> mMap = new HashMap<>();

    static {
        for (RequestCode request_code : RequestCode.values()) {
            mMap.put(request_code.ordinal(), request_code);
        }
    }

    public static RequestCode valueOf(int value) {
        return mMap.get(value);
    }

    public int getValue() {
        return this.ordinal();
    }
}
