package com.szy.yishopcustomer.View;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by 宗仁 on 16/8/29.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class IndexAdColumnLayoutManager extends LinearLayoutManager {

    public IndexAdColumnLayoutManager(Context context) {
        super(context, LinearLayoutManager.HORIZONTAL, false);
    }

    @Override
    public boolean canScrollHorizontally() {
        return false;
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
}
