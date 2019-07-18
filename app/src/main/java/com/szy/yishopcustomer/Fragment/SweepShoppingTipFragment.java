package com.szy.yishopcustomer.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.*;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.szy.yishopcustomer.Activity.ScanActivityTwo;
import com.szy.yishopcustomer.Activity.SelectShopActivity;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;

import butterknife.BindView;


public class SweepShoppingTipFragment extends YSCBaseFragment {

    @BindView(R.id.linearlayout_parent)
    LinearLayout linearlayout_parent;
    @BindView(R.id.fragment_attribute_cancel)
    Button mCloseButton;
    @BindView(R.id.fragment_attribute_confirm)
    Button mConfirm;
    @BindView(R.id.imageView_close)
    View imageView_close;

    private String shopId;

    @Override
    public void onClick(View v) {
        ViewType viewType = Utils.getViewTypeOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_CLOSE:
                getActivity().finish();
                break;
            case VIEW_TYPE_CONFIRM:
                Intent intent = new Intent();
                if(TextUtils.isEmpty(shopId) || "0".equals(shopId)) {
                    intent.setClass(getActivity(), SelectShopActivity.class);
                } else {
                    intent.setClass(getActivity(), ScanActivityTwo.class);
                    intent.putExtra(Key.KEY_SHOP_ID.getValue(),shopId);
                }
                startActivity(intent);
                getActivity().finish();
                break;
            default:
                super.onClick(v);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_sweep_shopping_tip;

        Intent intent = getActivity().getIntent();
        shopId = intent.getStringExtra(Key.KEY_SHOP_ID.getValue());
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
        savedInstanceState) {
            View view = super.onCreateView(inflater, container, savedInstanceState);

            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) linearlayout_parent.getLayoutParams();
            WindowManager windowManager = getActivity().getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            lp.width = display.getWidth() * 5 / 6; // 设置dialog宽度为屏幕的2/3

            Utils.setViewTypeForTag(mCloseButton, ViewType.VIEW_TYPE_CLOSE);
        mCloseButton.setOnClickListener(this);
        Utils.setViewTypeForTag(mConfirm, ViewType.VIEW_TYPE_CONFIRM);
        mConfirm.setOnClickListener(this);

        Utils.setViewTypeForTag(imageView_close, ViewType.VIEW_TYPE_CLOSE);
        imageView_close.setOnClickListener(this);

        return view;
    }

}
