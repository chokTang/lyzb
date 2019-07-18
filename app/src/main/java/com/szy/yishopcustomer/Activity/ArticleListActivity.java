package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Fragment.ArticleListFragment;
import com.szy.yishopcustomer.Util.Utils;

/**
 * Created by zongren on 16/7/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ArticleListActivity extends YSCBaseActivity {

    @Override
    protected ArticleListFragment createFragment() {
        return new ArticleListFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu= true;
        super.onCreate(savedInstanceState);
        String title = getIntent().getStringExtra(Key.KEY_TITLE.getValue());
        if (!Utils.isNull(title)) {
            setTitle(title);
        }
    }
}
