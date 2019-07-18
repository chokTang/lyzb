package com.szy.yishopcustomer.Activity;

import android.content.Intent;

import com.szy.yishopcustomer.Fragment.ImgSearchFragment;

/**
 * 拍照搜索
 */
public class ImgSearchActivity extends YSCBaseActivity {
    private ImgSearchFragment fragment;

    @Override
    protected ImgSearchFragment createFragment() {
        fragment = new ImgSearchFragment();
        fragment.setArguments(getIntent().getExtras());
        return fragment;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (fragment != null)
            fragment.onActivityResult(requestCode, resultCode, data);
    }

    public void hideTopBar() {
        if (mActionBar != null)
            mActionBar.hide();
    }

    @Override
    public void onBackPressed() {
        if (fragment != null) {
            fragment.onBackPressed();
            return;
        }
        super.onBackPressed();
    }
}
