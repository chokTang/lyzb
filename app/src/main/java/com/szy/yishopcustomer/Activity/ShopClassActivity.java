package com.szy.yishopcustomer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.TextView;

import com.szy.common.View.CommonEditText;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Fragment.ShopClassFragment;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;

import butterknife.BindView;

/**
 * Created by liwei on 2017/8/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShopClassActivity extends YSCBaseActivity implements TextView.OnEditorActionListener{
    @BindView(R.id.activity_shop_class_backImageButton)
    ImageButton mBackImageButton;
    @BindView(R.id.activity_shop_class_commonEditText)
    CommonEditText mCommonEditText;
    @BindView(R.id.activity_shop_class_moreImageButton)
    ImageButton mMoreImageButton;
    @Override
    public ShopClassFragment createFragment() {
        return new ShopClassFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLayoutId = R.layout.activity_shop_class;
        super.onCreate(savedInstanceState);

        Utils.setViewTypeForTag(mBackImageButton, ViewType.VIEW_TYPE_CLOSE);
        mBackImageButton.setOnClickListener(this);
        Utils.setViewTypeForTag(mCommonEditText, ViewType.VIEW_TYPE_SEARCH);
        mCommonEditText.setOnEditorActionListener(this);
        Utils.setViewTypeForTag(mMoreImageButton, ViewType.VIEW_TYPE_MORE);
        mMoreImageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_CLOSE:
                finish();
                break;
            case VIEW_TYPE_MORE:
                openPopMenu();
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (v.getId()) {
            case R.id.activity_shop_class_commonEditText:
                if (EditorInfo.IME_ACTION_SEARCH == actionId || EditorInfo.IME_ACTION_DONE ==
                        actionId || EditorInfo.IME_ACTION_UNSPECIFIED == actionId) {
                    openShopStreetActivity();
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    private void openShopStreetActivity() {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_KEYWORD.getValue(), mCommonEditText.getText().toString());
        intent.setClass(this, ShopStreetActivity.class);
        startActivity(intent);
    }

    private void openPopMenu() {
        initCustemMenu();
        menuPopwindow.showPopupWindow(this.getWindow().getDecorView());
    }
}
