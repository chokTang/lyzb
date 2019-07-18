package com.szy.yishopcustomer.Util;

import android.content.Context;
import android.widget.Toast;

import com.szy.common.Other.BasePatternModel;
import com.szy.common.Other.BrowserPatternModel;

/**
 * Created by 宗仁 on 2016/12/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class BrowserUrlManager extends YSCBaseUrlManager {
    public BrowserUrlManager() {
        super(null);
//        addPattern(new ActivityPatternModel(Business.PATTERN_INSIDE, "YSCWebViewActivity")
//                .addReplacement(Key.KEY_URL.getValue(), "$1"));

        addPattern(new BrowserPatternModel());
    }
    public BrowserUrlManager(String link){
        super(link);
        addPattern(new BrowserPatternModel());
    }
    public boolean parseUrl(Context context, String url) {
        if (context == null) {
            return false;
        }
        if (url == null || url.trim().length() == 0) {
//            Toast.makeText(context, com.szy.common.R.string.emptyUrl, Toast.LENGTH_SHORT).show();
            return false;
        }

        boolean consumed = false;
        for (BasePatternModel patternModel : patterns) {
            consumed = patternModel.execute(context, url);
            if (consumed) {
                break;
            }
        }
        return consumed || noMatchListener != null && noMatchListener.noMatches(context, url);
    }


}
