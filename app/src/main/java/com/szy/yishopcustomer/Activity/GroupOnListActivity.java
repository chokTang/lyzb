package com.szy.yishopcustomer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.szy.yishopcustomer.Fragment.GroupOnListFragment;
import com.lyzb.jbx.R;

import java.util.ArrayList;

/**
 * Created by liuzhifeng on 2017/3/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GroupOnListActivity extends YSCBaseActivity {
    public final static ArrayList<String> shareData = new ArrayList();
    private static final int REQUEST_CODE_SHARE = 0;

    @Override
    protected GroupOnListFragment createFragment() {
        return new GroupOnListFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_share_groupon:
                openShareActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void openShareActivity() {
        Intent intent = new Intent();
        intent.setClass(this, ShareActivity.class);
        intent.putStringArrayListExtra(ShareActivity.SHARE_DATA,shareData);
        intent.putExtra(ShareActivity.SHARE_TYPE, ShareActivity.TYPE_GROUP_ON_LIST);
        startActivityForResult(intent, REQUEST_CODE_SHARE);
    }

}