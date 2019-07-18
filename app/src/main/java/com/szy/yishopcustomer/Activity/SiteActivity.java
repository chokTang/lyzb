package com.szy.yishopcustomer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Fragment.SiteFragment;
import com.lyzb.jbx.R;

public class SiteActivity extends YSCBaseActivity {

    private boolean mEnableCloseButton;

    @Override
    public void fragmentFinish() {
        if (mEnableCloseButton) {
            this.finish();
        } else {
            Intent intent = new Intent(this, RootActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && !mEnableCloseButton) {

            Intent intent = new Intent(Intent.ACTION_MAIN);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            intent.addCategory(Intent.CATEGORY_HOME);

            startActivity(intent);

            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    @Override
    protected CommonFragment createFragment() {
        return new SiteFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mActionBar.setHomeAsUpIndicator(R.mipmap.btn_close_red);
        mEnableCloseButton = intent.getBooleanExtra(Key.KEY_ENABLE_CLOSE_BUTTON.getValue(), false);
        mActionBar.setDisplayHomeAsUpEnabled(mEnableCloseButton);
    }

}
