package com.szy.yishopcustomer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;

import com.szy.yishopcustomer.Fragment.GroupBuyFragment;
import com.lyzb.jbx.R;

import org.w3c.dom.Text;

/**
 * Created by liwei on 2016/5/28.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class InvalidActivity extends YSCBaseActivity {
    private Button errorButton;
    private TextView errorTitle;
    private TextView toolbar_common_titleTextView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invalid);

        initView();

        Intent intent = getIntent();
        String errorInfo = intent.getStringExtra("error_info");
        errorTitle.setText(errorInfo);
        errorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void initView() {
        errorButton = (Button) findViewById(R.id.error_view_button);
        errorTitle = (TextView) findViewById(R.id.error_view_titleTextView);
        toolbar_common_titleTextView = (TextView) findViewById(R.id.toolbar_common_titleTextView);
        mToolbar = (Toolbar)findViewById(R.id.activity_common_toolbar);

        toolbar_common_titleTextView.setText("系统提示");

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
