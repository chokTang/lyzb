package com.lyzb.jbx.fragment.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.like.longshaolib.base.BaseStatusFragment;
import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 身份认证-认证
 * @time 2019 2019/3/5 10:01
 */

public class StatusValFragment extends BaseStatusFragment {

    @BindView(R.id.img_union_me_sas_val_back)
    ImageView backImg;


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
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public Integer getMainResId() {
        return R.layout.fragment_union_me_status_val;
    }

    @Override
    public void onLazyRequest() {

    }

    @Override
    public void onAgainRequest() {

    }
}
