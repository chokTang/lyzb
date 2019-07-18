package com.szy.yishopcustomer.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.szy.common.Util.CommonUtils;
import com.szy.common.View.CommonEditText;

/**
 * Created by 宗仁 on 2017/1/17.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class YSCBaseEditText extends CommonEditText {
    public YSCBaseEditText(Context context) {
        super(context);
    }

    public YSCBaseEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YSCBaseEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onTextChanged(EditText view, String text) {
        if (isFocused()) {
            setClearIconVisible(!CommonUtils.isNull(text));
            if (mTextWatcherListener != null) {
                mTextWatcherListener.onTextChanged(view, text);
            }
        }
    }
}
