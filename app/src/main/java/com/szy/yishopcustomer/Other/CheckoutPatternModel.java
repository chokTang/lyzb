package com.szy.yishopcustomer.Other;

import android.content.Context;

import com.szy.common.Other.BasePatternModel;
import com.szy.yishopcustomer.Constant.Business;
import com.szy.yishopcustomer.Util.Utils;

import java.util.regex.Matcher;

/**
 * Created by 宗仁 on 2017/1/7.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class CheckoutPatternModel extends BasePatternModel {

    public CheckoutPatternModel() {
        super(Business.PATTERN_CHECKOUT);
    }

    @Override
    public boolean handlePattern(Context context, Matcher matcher) {
        Utils.openActivity(context, "CheckoutActivity");
        return true;
    }
}
