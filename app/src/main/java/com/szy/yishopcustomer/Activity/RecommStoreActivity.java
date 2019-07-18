package com.szy.yishopcustomer.Activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Fragment.RecommStoreFragment;
import com.lyzb.jbx.R;

/**
 * 推荐店铺
 * Created by Smart on 2017/5/16.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class RecommStoreActivity extends YSCBaseActivity{

    RecommStoreFragment mRecommStoreFragment;

    @Override
    protected CommonFragment createFragment() {
        return mRecommStoreFragment = new RecommStoreFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        mCustemMenuStyle = 0;
        mBaseMenuId = R.menu.activity_menu_recomm_stroe;
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_recomm:
                mRecommStoreFragment.recommStore();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
