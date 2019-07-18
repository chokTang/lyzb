package com.szy.yishopcustomer.Activity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Fragment.AttributeFragment;
import com.szy.yishopcustomer.Fragment.PromotionPopFragment;
import com.lyzb.jbx.R;

/**
 * Created by Smart on 17/11/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class PromotionPopActivity extends YSCBaseActivity {

    @Override
    public CommonFragment createFragment() {
        return new PromotionPopFragment();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLayoutId = R.layout.activity_common_no_toolbar;
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.pop_enter_anim, R.anim.pop_exit_anim);
        Window win = this.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.pop_enter_anim, R.anim.pop_exit_anim);
    }
}
