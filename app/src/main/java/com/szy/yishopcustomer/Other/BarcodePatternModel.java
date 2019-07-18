package com.szy.yishopcustomer.Other;

import android.content.Context;
import android.content.Intent;

import com.szy.common.Other.BasePatternModel;
import com.szy.yishopcustomer.Activity.BarcodeSearchActivity;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Util.Utils;

import java.util.regex.Matcher;

/**
 * Created by 宗仁 on 2016/12/17.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class BarcodePatternModel extends BasePatternModel {
    public BarcodePatternModel() {
        super("^(\\d+)$");
    }

    public boolean handlePattern(Context context, Matcher matcher) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_BARCODE.getValue(), this.target);
        intent.setClass(context, BarcodeSearchActivity.class);
        //        context.startActivity(intent);
        return Utils.openActivity(context, intent);
    }
}
