package com.szy.yishopcustomer.Other;

import android.content.Context;

import com.szy.common.Other.BasePatternModel;
import com.szy.yishopcustomer.Util.Utils;

import java.util.regex.Matcher;

/**
 * Created by 宗仁 on 2016/12/15.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class PhonePatternModel extends BasePatternModel {
    public PhonePatternModel(String pattern) {
        super(pattern);
    }

    @Override
    public boolean handlePattern(Context context, Matcher matcher) {
        Utils.openPhoneDialog(context, matcher.replaceAll("$1"));
        return true;
    }
}
