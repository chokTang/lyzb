package com.szy.yishopcustomer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.unionpay.UPPayAssistEx;

/**
 * Created by 宗仁 on 2016/12/24.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class UnionpayDummyActivity extends YSCBaseActivity {
    @Override
    public CommonFragment createFragment() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mLayoutId = R.layout.activity_unionpay_dummy;
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }

        String orderInfo = intent.getStringExtra(Key.KEY_ORDER_ID.getValue());
        String mode = intent.getStringExtra(Key.KEY_UNIONPAY_MODE.getValue());

        startUnionPay(orderInfo, mode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        unionPayCallback(data.getStringExtra("pay_result"));
    }

    public void startUnionPay(String orderInfo, String mode) {
        int result = UPPayAssistEx.startPay(this, null, null, orderInfo, mode);
        if (result == UPPayAssistEx.PLUGIN_NOT_FOUND) {
            Toast.makeText(this.getApplicationContext(), R.string.pleaseInstallUnionPayPlugin,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void unionPayCallback(String payResult) {
        String message = "";
        if (payResult.equalsIgnoreCase("success")) {
            message = "支付成功！";
        } else if (payResult.equalsIgnoreCase("fail")) {
            message = "支付失败！";
        } else if (payResult.equalsIgnoreCase("cancel")) {
            message = "用户取消了支付";
        }
        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_RESULT.getValue(), payResult);
        setResult(RESULT_OK, intent);
        finish();
    }
}
