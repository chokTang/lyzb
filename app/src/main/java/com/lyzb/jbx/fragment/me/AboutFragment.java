package com.lyzb.jbx.fragment.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.longshaolib.base.BaseStatusFragment;
import com.like.utilslib.app.AppUtil;
import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 关于我们
 * @time 2019 2019/3/5 14:45
 */

public class AboutFragment extends BaseStatusFragment {

    @BindView(R.id.img_union_about_back)
    ImageView backImg;

    @BindView(R.id.tv_union_about_appname)
    TextView appNameText;
    @BindView(R.id.tv_union_about_version)
    TextView versionText;

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        ButterKnife.bind(this, mView);

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

        appNameText.setText(AppUtil.getAppName());
        versionText.setText(AppUtil.getVersionName());
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public Integer getMainResId() {
        return R.layout.fragment_union_about;
    }

    @Override
    public void onLazyRequest() {

    }

    @Override
    public void onAgainRequest() {

    }
}
