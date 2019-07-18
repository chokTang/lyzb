package com.szy.yishopcustomer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Fragment.UserGroupOnDetailFragment;
import com.lyzb.jbx.R;

import java.util.ArrayList;

/**
 * Created by liwei on 2017/3/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class UserGroupOnDetailActivity extends YSCBaseActivity {
    public final static ArrayList<String> shareData = new ArrayList();
    public static int diffNumber = 0;
    private int status;

    @Override
    protected UserGroupOnDetailFragment createFragment() {
        return new UserGroupOnDetailFragment();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        status = intent.getIntExtra(Key.KEY_GROUPON_STATUS.getValue(), 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (status == 0) {
            getMenuInflater().inflate(R.menu.share_menu, menu);
        }
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
        Intent intent = new Intent(mContext, ShareActivity.class);
        intent.putStringArrayListExtra(ShareActivity.SHARE_DATA, shareData);
        intent.putExtra(ShareActivity.SHARE_TYPE, ShareActivity.TYPE_USER_GROUP_ON);
        intent.putExtra(Key.KEY_DIFF_NUM.getValue(),diffNumber+"");
        startActivity(intent);
    }

}