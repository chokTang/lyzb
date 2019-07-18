package com.szy.yishopcustomer.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.utilslib.image.config.GlideApp;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Constant.Api;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 相似商品 ac
 * @time 2018 2018/11/8 10:07
 */

public class SimilarGoodsActivity extends Activity {

    public static final String GOOD_IMG = "IMG_URL";
    public static final String GOOD_NAME = "GOOD_NAME";
    public static final String GOOD_PRICE = "GOOD_PRICE";

    @BindView(R.id.toolbar_similbar)
    Toolbar mToolbar;

    @BindView(R.id.img_similar_goods)
    ImageView mImg_SimilarImg;
    @BindView(R.id.tv_similar_goods_name)
    TextView mText_SimilarName;
    @BindView(R.id.tv_similar_goods_price)
    TextView mText_SimilarPrice;

    @BindView(R.id.recy_similar_goods)
    CommonRecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_goods);
        ButterKnife.bind(this);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (TextUtils.isEmpty(getIntent().getStringExtra(GOOD_IMG))) {
            GlideApp.with(this)
                    .load(R.mipmap.img_empty)
                    .into(mImg_SimilarImg);
        } else {
            GlideApp.with(this)
                    .load(Utils.setImgNetUrl(Api.API_HEAD_IMG_URL, getIntent().getStringExtra(GOOD_IMG)))
                    .error(R.mipmap.img_empty)
                    .into(mImg_SimilarImg);
        }

        mText_SimilarName.setText(getIntent().getStringExtra(GOOD_NAME));
        mText_SimilarPrice.setText(getIntent().getStringExtra(GOOD_PRICE));
    }
}
