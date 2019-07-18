package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.TextView;

import com.szy.common.View.CommonRecyclerView;
import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 砍价推荐+我的砍价 列表  首页banner进入 ac
 * @time 2018 2018/9/10 10:19
 */

public class BargainListActivity extends Activity {

    @BindView(R.id.toolbar_bargain_list)
    Toolbar mToolbar;
    @BindView(R.id.tv_bargain_list_title)
    TextView mTextView_Title;

    @BindView(R.id.recy_bargain_list)
    CommonRecyclerView mRecyclerView;
    @BindView(R.id.tv_bargain_recomd)
    TextView mTextView_Recomd;
    @BindView(R.id.tv_bargain_me)
    TextView mTextView_Me;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bargain_list);
        ButterKnife.bind(this);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
