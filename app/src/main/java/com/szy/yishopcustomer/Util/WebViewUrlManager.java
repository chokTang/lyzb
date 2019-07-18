package com.szy.yishopcustomer.Util;

import android.content.Context;
import android.content.Intent;

import com.szy.common.Other.BasePatternModel;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Other.ActivityPatternModel;
import com.szy.yishopcustomer.Other.CheckoutPatternModel;
import com.szy.yishopcustomer.Other.LoginPatternModel;

import java.util.Iterator;

/**
 * Created by 宗仁 on 2016/12/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class WebViewUrlManager extends YSCBaseUrlManager {
    public WebViewUrlManager() {
        super(null);
        addPattern(new LoginPatternModel());
        addPattern(new CheckoutPatternModel());
    }

    public boolean parseUrl(Context context, String url) {
        if (context == null) {
            return false;
        } else if (url != null && url.trim().length() != 0) {
            boolean consumed = false;
            Iterator var4 = this.patterns.iterator();

            while (var4.hasNext()) {
                BasePatternModel patternModel = (BasePatternModel) var4.next();
                consumed = patternModel.execute(context, url);
                if (consumed) {
                    if (patternModel instanceof ActivityPatternModel) {
                        if ("YSCWebViewActivity".equals(((ActivityPatternModel) patternModel).activityName)) {
                            consumed = false;
                        }
                    }
                    break;
                }
            }

            return consumed || this.noMatchListener != null && this.noMatchListener.noMatches(context, url);

        } else {
            return false;
        }
    }

}
