package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Activity.RechargeCardActivity;
import com.szy.yishopcustomer.Activity.YSCBaseActivity;
import com.szy.yishopcustomer.Fragment.CertificationFragment;
import com.szy.yishopcustomer.Fragment.DepositCardFragment;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.ProfileModel.InfoModel;

public class CertificationActivity extends YSCBaseActivity {

    public static final String PARAMS_IDCARD_DEMO = "idcard_demo_image";
    public static final String PARAMS_REAL_INFO = "real_info";

    private static final int REQUEST_RECHARGE_CARD = 0;
    CertificationFragment certificationFragment;

    private boolean isSubmitButtonShow = true;
    @Override
    protected CommonFragment createFragment() {
        return certificationFragment = new CertificationFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        mCustemMenuStyle = 0;
        mBaseMenuId = R.menu.activity_menu_confirm;
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        InfoModel.RealInfoBean real_info = intent.getParcelableExtra(CertificationActivity.PARAMS_REAL_INFO);
        if(real_info != null && real_info.status == 1) {
            isSubmitButtonShow = false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean ree = super.onCreateOptionsMenu(menu);

        if(isSubmitButtonShow) {
            menu.findItem(R.id.action_confirm).setTitle("确定");
        } else {
            menu.findItem(R.id.action_confirm).setTitle("");
        }

        return ree;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_confirm:
                certificationFragment.submit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch(requestCode) {
//            case REQUEST_RECHARGE_CARD:
//                if(resultCode == RESULT_OK) {
//                    mRecommStoreFragment.refresh();
//                }
//                break;
//        }
//    }
}
