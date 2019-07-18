package com.szy.yishopcustomer.Activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.szy.common.View.CommonEditText;
import com.szy.yishopcustomer.Fragment.ShopStreetFragment;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.View.MenuPopwindow;

import butterknife.BindView;

/**
 * Created by buqingqiang on 2016/6/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShopStreetActivity extends YSCBaseActivity {

    public String id = "";
    public String name = "";

    private ShopStreetFragment mFragment;
    private String keyword = "";

    @Override
    protected ShopStreetFragment createFragment() {
        mFragment = new ShopStreetFragment();
        return mFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //mLayoutId = R.layout.activity_shop_street;
        mEnableBaseMenu = true;
        mCustemMenuStyle = MenuPopwindow.MENU_STYLE_MESSAGE;
//        mBaseMenuId = R.menu.activity_message;
        super.onCreate(savedInstanceState);

        mFragment.closeAppbar();
    }

    private void searchForKeyWord(String str) {
        if (str != null) {
            keyword = str;
        }
        mFragment.searchForKeyWord(keyword);
    }

}
