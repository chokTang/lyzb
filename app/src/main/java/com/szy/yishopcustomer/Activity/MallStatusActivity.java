package com.szy.yishopcustomer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;

import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.HttpResultManager;

/**
 * Created by liwei on 17/6/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class MallStatusActivity extends YSCBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int code = intent.getIntExtra(Key.KEY_ACT_CODE.getValue(), 97);
        if (code == HttpResultManager.CODE_STATE_MALL_CLOSED) {
            setContentView(R.layout.activity_splash_closed);

            TextView mCloseTextView = (TextView) findViewById(R.id.activity_splash_closed_textView);
            mCloseTextView.setText("网站暂时关闭...");
        } else if (code == HttpResultManager.CODE_STATE_MALL_UPDATE) {
            setContentView(R.layout.activity_splash_update);

            TextView mUpdateTextView = (TextView) findViewById(R.id
                    .activity_splash_update_textView);
            mUpdateTextView.setText("网站系统升级中...");
            Button mUpdateButton = (Button) findViewById(R.id.activity_splash_update_button);
            mUpdateButton.setVisibility(View.GONE);
        }
    }
}
