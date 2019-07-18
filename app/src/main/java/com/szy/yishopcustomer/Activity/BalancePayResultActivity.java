package com.szy.yishopcustomer.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Fragment.BalancePayResultFragment;
import com.lyzb.jbx.R;

public class BalancePayResultActivity extends YSCBaseActivity {

    BalancePayResultFragment balancePayResultFragment;

    public CommonFragment createFragment() {
        return balancePayResultFragment = new BalancePayResultFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        mCustemMenuStyle = 0;
        mBaseMenuId = R.menu.activity_menu_recomm_stroe;
        super.onCreate(savedInstanceState);

        setTitle("支付结果");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean ree = super.onCreateOptionsMenu(menu);

        MenuItem rightMenu = menu.findItem(R.id.action_recomm);
        rightMenu.setTitle("完成");
        return ree;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_recomm:
                if (balancePayResultFragment != null) {
                    balancePayResultFragment.openShop();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
