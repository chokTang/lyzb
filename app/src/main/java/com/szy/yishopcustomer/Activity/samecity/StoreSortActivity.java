package com.szy.yishopcustomer.Activity.samecity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.ListPopupWindow;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szy.common.View.CommonRecyclerView;
import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zongren.pullablelayout.Main.PullableLayout;

/**
 * @author wyx
 * @role 店铺 分类页面 ac
 * @time 2018 2018/8/6 10:43
 */

public class StoreSortActivity extends Activity implements View.OnClickListener {

    private static final int MENU_ALL = 1;
    private static final int MENU_NEAR = 2;
    private static final int MENU_SALE = 3;

    @BindView(R.id.tv_sort_title)
    TextView mTextView_Title;

    @BindView(R.id.linea_sort_seach)
    LinearLayout mLayout_Seach;

    @BindView(R.id.rela_sort_all)
    RelativeLayout mRelativeLayout_All;
    @BindView(R.id.tv_sort_title_all)
    TextView mTextView_All;
    @BindView(R.id.img_sort_title_all)
    ImageView mImageView_All;

    @BindView(R.id.rela_sort_near)
    RelativeLayout mRelativeLayout_Near;
    @BindView(R.id.tv_sort_title_near)
    TextView mTextView_Near;
    @BindView(R.id.img_sort_title_near)
    ImageView mImageView_Near;

    @BindView(R.id.rela_sort_sale)
    RelativeLayout mRelativeLayout_Sale;
    @BindView(R.id.tv_sort_title_sale)
    TextView mTextView_Sale;
    @BindView(R.id.img_sort_title_sale)
    ImageView mImageView_Sale;

    @BindView(R.id.pull_store_sort)
    PullableLayout mPullableLayout_Sort;
    @BindView(R.id.recy_store_sort)
    CommonRecyclerView mRecyclerView_Sort;

    private ListPopupWindow mListPopupWindow;

    private boolean isSaleClick = false;
    private String saleDefault;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_sort);
        ButterKnife.bind(this);

        mRelativeLayout_All.setOnClickListener(this);
        mRelativeLayout_Near.setOnClickListener(this);
        mRelativeLayout_Sale.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rela_sort_all:
                chanageBtn(MENU_ALL);
                break;
            case R.id.rela_sort_near:
                chanageBtn(MENU_NEAR);
                break;
            case R.id.rela_sort_sale:
                chanageBtn(MENU_SALE);
                break;
        }
    }


    /****
     * 按钮状态改变
     * @param menu
     */
    public void chanageBtn(int menu) {

        switch (menu) {
            case MENU_ALL:

                mTextView_All.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                mImageView_All.setBackgroundResource(R.mipmap.near_tab_selected);

                mTextView_Near.setTextColor(ContextCompat.getColor(this, R.color.aliwx_black));
                mImageView_Near.setBackgroundResource(R.mipmap.near_tab_normal);

                mTextView_Sale.setTextColor(ContextCompat.getColor(this, R.color.aliwx_black));
                if (!isSaleClick) {
                    mImageView_Sale.setBackgroundResource(R.mipmap.near_tab_normal);
                } else {
                    mImageView_Sale.setBackgroundResource(R.mipmap.near_sale_top_normal);
                }
                break;
            case MENU_NEAR:

                mTextView_Near.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                mImageView_Near.setBackgroundResource(R.mipmap.near_tab_selected);

                mTextView_All.setTextColor(ContextCompat.getColor(this, R.color.aliwx_black));
                mImageView_All.setBackgroundResource(R.mipmap.near_tab_normal);

                mTextView_Sale.setTextColor(ContextCompat.getColor(this, R.color.aliwx_black));
                if (!isSaleClick) {
                    mImageView_Sale.setBackgroundResource(R.mipmap.near_tab_normal);
                } else {
                    mImageView_Sale.setBackgroundResource(R.mipmap.near_sale_top_normal);
                }
                break;
            case MENU_SALE:

                mTextView_All.setTextColor(ContextCompat.getColor(this, R.color.aliwx_black));
                mImageView_All.setBackgroundResource(R.mipmap.near_tab_normal);

                mTextView_Near.setTextColor(ContextCompat.getColor(this, R.color.aliwx_black));
                mImageView_Near.setBackgroundResource(R.mipmap.near_tab_normal);

                mTextView_Sale.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                if (!isSaleClick) {
                    mImageView_Sale.setBackgroundResource(R.mipmap.near_tab_selected);
                    isSaleClick = true;
                    saleDefault = "soldNum asc";
                } else {
                    mImageView_Sale.setBackgroundResource(R.mipmap.near_sale_top);
                    isSaleClick = false;
                    saleDefault = "soldNum DESC";
                }
                break;
        }
    }
}
