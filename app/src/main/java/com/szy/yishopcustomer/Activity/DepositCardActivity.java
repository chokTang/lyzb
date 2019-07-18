package com.szy.yishopcustomer.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.*;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Fragment.DepositCardFragment;
import com.szy.yishopcustomer.Fragment.DepositCardPlatformFragment;
import com.szy.yishopcustomer.Fragment.RecommStoreFragment;
import com.lyzb.jbx.R;

public class DepositCardActivity extends YSCBaseActivity implements DepositCardFragment.OnChangeMenuItem {

    private static final int REQUEST_RECHARGE_CARD = 0;

    private MenuItem rightMenu;
    private int type = 0;

    @Override
    protected CommonFragment createFragment() {
        return new DepositCardFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        mCustemMenuStyle = 0;
        mBaseMenuId = R.menu.activity_menu_recomm_stroe;
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean ree = super.onCreateOptionsMenu(menu);

        rightMenu = menu.findItem(R.id.action_recomm);

        changeMenuItem(type);
        return ree;
    }


        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_recomm:
                switch(type) {
                    case 0:
                        openRechargeCard();
                        break;
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openRechargeCard(){
        Intent intent = new Intent();
        intent.setClass(this,RechargeCardActivity.class);
        startActivityForResult(intent,REQUEST_RECHARGE_CARD);
    }

    @Override
    public void changeMenuItem(int type1) {
        if(rightMenu != null) {
            type = type1;
            switch(type1) {
                case 0:
                    rightMenu.setTitle("储值卡充值");
                    break;
            }
        }

    }
}

