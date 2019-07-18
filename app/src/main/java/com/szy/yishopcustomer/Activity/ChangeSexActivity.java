package com.szy.yishopcustomer.Activity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Fragment.ChangeSexFragment;

/**
 * Created by liuzhifeng on 16/7/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ChangeSexActivity extends YSCBaseActivity {

    private ImageButton mBackImageView;
    private TextView mTitleTextView;

    @Override
    protected CommonFragment createFragment() {
        return new ChangeSexFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(false);
            //mActionBar.setHomeAsUpIndicator(R.mipmap.btn_back_circled_white);
            mActionBar.setBackgroundDrawable(new ColorDrawable(Color.argb(0, 0, 0, 0)));
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setCustomView(R.layout.actionbar_shop_street);
            mBackImageView = (ImageButton) mActionBar.getCustomView().findViewById(R.id.action_bar_collection_back_imageButton);
            mTitleTextView = (TextView) mActionBar.getCustomView().findViewById(R.id.action_bar_shop_street_title_textView);
            mTitleTextView.setText("修改性别");
        }
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });*/

    }
}
