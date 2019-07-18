package com.szy.yishopcustomer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Fragment.AddressFragment;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;

/**
 * Created by zongren on 16/4/26.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class AddressActivity extends YSCBaseActivity {

    private String mAddressId;
    private int mAddressType;
    private AddressFragment mFragment;

    @Override
    public AddressFragment createFragment() {
        mFragment = new AddressFragment();
        return mFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            mAddressId = intent.getStringExtra(Key.KEY_ADDRESS_ID.getValue());
            mAddressType = intent.getIntExtra(Key.KEY_ADDRESS_TYPE.getValue(), 1);
        }

        if (mAddressId != null) {
            this.setTitle("编辑收货地址");
        } else {
            this.setTitle("新增收货地址");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mAddressId != null) {
            getMenuInflater().inflate(R.menu.activity_address, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (Utils.isDoubleClick()) {
            return false;
        }
        switch (item.getItemId()) {
            case R.id.activity_address_delete:
                mFragment.deleteAddress(R.string.areYouSureToDeleteTheAddress);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
