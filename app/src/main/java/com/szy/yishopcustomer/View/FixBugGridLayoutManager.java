package com.szy.yishopcustomer.View;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by 宗仁 on 2017/1/23.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class FixBugGridLayoutManager extends GridLayoutManager {
    public FixBugGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public FixBugGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public FixBugGridLayoutManager(Context context, int spanCount, int orientation, boolean
            reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }
}
