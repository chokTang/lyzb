package com.szy.yishopcustomer.View;


import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Smart on 2017/5/15.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class ReadView extends android.support.v7.widget.AppCompatTextView {
    private List<String> info = new ArrayList<>();

    public ReadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ReadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ReadView(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    public List<String> getCharArray() {
        if (info == null) {
            info = new ArrayList<>();
        }
        info.clear();
        if(!TextUtils.isEmpty(getText().toString())) {
            int count = getLineCount();

            int frontNum = 0;
            for (int i = 0, len = count; i < len; i++) {
                int num = getLayout().getLineEnd(i);

                if(getText().toString().length()-1 <= num) {
                    info.add(getText().toString().substring(frontNum));
                } else {
                    info.add(getText().toString().substring(frontNum, num));
                }

                frontNum = num <= 0 ? 0 : num;
            }

        }
        return info;
    }


}
