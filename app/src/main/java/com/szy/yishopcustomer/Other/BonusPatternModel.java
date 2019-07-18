package com.szy.yishopcustomer.Other;

import android.content.Context;
import android.content.Intent;

import com.szy.common.Other.BasePatternModel;
import com.szy.yishopcustomer.Activity.GoodsListActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Util.Utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;

/**
 * Created by liwei on 2016/12/27.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class BonusPatternModel extends BasePatternModel {

    public BonusPatternModel(String pattern) {
        super(pattern);
    }

    @Override
    public boolean handlePattern(Context context, Matcher matcher) {
        Intent intent = new Intent(context, GoodsListActivity.class);
        String url = Config.BASE_URL + this.target;
        try {
            URL uri = new URL(url);
            String query = uri.getQuery();
            if (!Utils.isNull(query)) {
                if (query.length() > 0) {
                    String[] queryArray = query.split("&");
                    for (String queryPair : queryArray) {
                        String[] keyValuePair = queryPair.split("=");
                        if (keyValuePair.length < 2) {
                            continue;
                        }
                        String key = keyValuePair[0];
                        String value = keyValuePair[1];
                        intent.putExtra(key, value);
                    }
                }
            }
            intent.putExtra(Key.KEY_API.getValue(), Api.API_GOODS_LIST_SEARCH);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }
        return Utils.openActivity(context, intent);
    }
}
