package com.szy.yishopcustomer.Activity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.szy.yishopcustomer.Fragment.ViewOriginalImageFragment;

public class ViewOriginalImageActivity extends YSCBaseActivity {
    @Override
    public ViewOriginalImageFragment createFragment() {
        return new ViewOriginalImageFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBar.hide();

        Window win = this.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
    }

}
