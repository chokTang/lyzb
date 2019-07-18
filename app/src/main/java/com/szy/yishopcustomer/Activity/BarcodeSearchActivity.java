package com.szy.yishopcustomer.Activity;

import android.view.Menu;
import android.view.MenuItem;

import com.szy.yishopcustomer.Fragment.BarcodeSearchFragment;
import com.lyzb.jbx.R;

/**
 * Created by 宗仁 on 2016/9/7.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BarcodeSearchActivity extends YSCBaseActivity {

    @Override
    protected BarcodeSearchFragment createFragment() {
        return new BarcodeSearchFragment();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_scan, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.activity_scan:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
