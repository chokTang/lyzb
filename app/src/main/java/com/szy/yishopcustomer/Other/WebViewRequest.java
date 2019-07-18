package com.szy.yishopcustomer.Other;

import com.szy.common.Other.CommonRequest;

/**
 * Created by 宗仁 on 2017/1/6.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class WebViewRequest extends CommonRequest {
    public WebViewRequest(String url, int what) {
        super(url, what);
        setUserAgent("");
    }
}
