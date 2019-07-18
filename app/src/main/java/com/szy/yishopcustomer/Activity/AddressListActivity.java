package com.szy.yishopcustomer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Fragment.AddressListFragment;
import com.lyzb.jbx.R;

/**
 * Created by zongren on 16/4/26.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class AddressListActivity extends YSCBaseActivity {

    public static final String IS_SHOW = "3214";
    public static final int TYPE_SELECT = 1;
    public static final int TYPE_EDIT = 1 << 1;

    private String IsShowEdt;
    private int mAvailableType;
    private int mCurrentType;

    private MenuItem mMenuItem;
    private AddressListFragment mFragment;

    @Override
    public AddressListFragment createFragment() {
        mFragment = new AddressListFragment();
        return mFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            mAvailableType = intent.getIntExtra(Key.KEY_ADDRESS_LIST_AVAILABLE_TYPE.getValue(),
                    TYPE_SELECT);
            mCurrentType = intent.getIntExtra(Key.KEY_ADDRESS_LIST_CURRENT_TYPE.getValue(),
                    mAvailableType);
            IsShowEdt = intent.getStringExtra(IS_SHOW);
            if (!TextUtils.isEmpty(IsShowEdt)){
                mFragment.setIsShow(true);
            }
        } else {
            mCurrentType = TYPE_SELECT;
            mAvailableType = TYPE_SELECT;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if ((mAvailableType & TYPE_EDIT) > 0 && (mAvailableType & TYPE_SELECT) > 0) {
            getMenuInflater().inflate(R.menu.activity_address_list, menu);
            mMenuItem = menu.getItem(0);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.activity_address_list_edit:
                changeCurrentType();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void changeCurrentType() {
        mCurrentType = mCurrentType == TYPE_SELECT ? TYPE_EDIT : TYPE_SELECT;
        if (mCurrentType == TYPE_EDIT) {
            mMenuItem.setTitle(R.string.finish);
        } else {
            mMenuItem.setTitle(R.string.menuEdit);
        }
        mFragment.changeType(mCurrentType);
    }
}
