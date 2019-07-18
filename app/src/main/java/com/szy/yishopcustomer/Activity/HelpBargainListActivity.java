package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;

import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Adapter.HelpBargainAdapter;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Goods.HelpBargainModel;
import com.szy.yishopcustomer.ResponseModel.Goods.ParamsModel;
import com.szy.yishopcustomer.Util.ListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 帮砍名单 List  ac
 * @time 2018 2018/9/20 9:17
 */

public class HelpBargainListActivity extends Activity {

    public static final String HELP_LIST = "helpList";

    @BindView(R.id.toolbar_help_bargain)
    Toolbar mToolbar;
    @BindView(R.id.recy_help_bargain)
    CommonRecyclerView mRecyclerView;

    private List<HelpBargainModel> mLists = new ArrayList<>();
    private HelpBargainAdapter mAdapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_bargain);
        ButterKnife.bind(this);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mLists = (List<HelpBargainModel>) getIntent().getSerializableExtra(HELP_LIST);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(ListItemDecoration.createVertical(this, Color.GRAY, 1));

        mAdapter = new HelpBargainAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        if (mLists.size() > 0) {
            mAdapter.data.addAll(mLists);
            mAdapter.notifyDataSetChanged();
        }
    }

}
