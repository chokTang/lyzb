package com.szy.yishopcustomer.Other;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.szy.common.Other.BasePatternModel;
import com.szy.yishopcustomer.Interface.OnLoginListener;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 宗仁 on 2016/12/15.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ActivityPatternModel extends BasePatternModel {
    public boolean needLogin = false;
    Map<String, String> replacements;
    public String activityName;

    public ActivityPatternModel(String pattern, String activityName) {
        this(pattern, activityName, false);
    }

    public ActivityPatternModel(String pattern, String activityName, boolean needLogin) {
        super(pattern);
        this.activityName = activityName;
        this.replacements = new HashMap<>();
        this.needLogin = needLogin;
    }

    public ActivityPatternModel addReplacement(String key, String replacement) {
        replacements.put(key, replacement);
        return this;
    }

    @Override
    public boolean handlePattern(final Context context, Matcher matcher) {
        final Map<String, String> parameters = new HashMap<>();
        Set<Map.Entry<String, String>> entries = this.replacements.entrySet();

        try {
            for (Map.Entry<String, String> entry : entries) {
                String key = entry.getKey();
                String value = entry.getValue();
                //matcher的replaceAll方法 $数字10以上的获取不到
                //parameters.put(key,matcher.replaceAll(value));

                Map<String, Integer> group = getNumber(value);
                for (Map.Entry<String, Integer> number : group.entrySet()) {
                    value = value.replace(number.getKey(), matcher.group(number.getValue()));
                }
                parameters.put(key, value);
            }
        } catch (Exception e) {

        }

        if (needLogin && !App.getInstance().isLogin()) {
            if (context instanceof Activity) {
                App.getInstance().onLoginListener = new OnLoginListener() {
                    @Override
                    public void onLogin() {
                        Utils.openActivity(context, activityName, parameters);
                    }
                };
                Utils.openActivity(context, "LoginActivity");
            }
            return true;
        }

        return Utils.openActivity(context, this.activityName, parameters);
    }


    private Map<String, Integer> getNumber(String str) {
        Map<String, Integer> map = new HashMap<>();

        Pattern p = Pattern.compile("[$](\\d+)");
        Matcher m = p.matcher(str);

        try {
            while (m.find()) {
                map.put(m.group(0), Integer.valueOf(m.group(1)));
            }
        } catch (Exception e) {
        }
        return map;
    }
}
