package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.szy.yishopcustomer.Fragment.PickUpFragment;
import com.szy.yishopcustomer.Fragment.ShopCartFragment;
import com.szy.yishopcustomer.Fragment.YSCBaseFragment;
import com.lyzb.jbx.R;

public class ShopCartActivity extends YSCBaseActivity {

    @Override
    public ShopCartFragment createFragment() {
        return new ShopCartFragment();
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
