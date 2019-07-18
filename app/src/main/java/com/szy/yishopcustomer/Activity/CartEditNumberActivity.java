package com.szy.yishopcustomer.Activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.WindowManager;

import com.szy.yishopcustomer.Fragment.CartEditNumberFragment;
import com.lyzb.jbx.R;

/**
 * Created by liwei on 17/5/15.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CartEditNumberActivity extends YSCBaseActivity {

    public static final String TYPE = "type";
    public static final int TYPE_FREE_CART = 1;
    public static final int TYPE_REACHBUY_CART = 2;

    protected int activityCloseEnterAnimation;
    protected int activityCloseExitAnimation;

    @Override
    protected CartEditNumberFragment createFragment() {
        return new CartEditNumberFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBar.hide();
        overridePendingTransition(R.anim.pop_enter_anim, R.anim.pop_exit_anim);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams
                .WRAP_CONTENT);
        TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[]{android.R.attr
                .windowAnimationStyle});
        int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);
        activityStyle.recycle();
        activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new
                int[]{android.R.attr.activityCloseEnterAnimation, android.R.attr
                .activityCloseExitAnimation});
        activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
        activityCloseExitAnimation = activityStyle.getResourceId(1, 0);
        activityStyle.recycle();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
    }
}
