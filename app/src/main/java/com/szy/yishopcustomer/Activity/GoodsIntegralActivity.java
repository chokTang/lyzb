package com.szy.yishopcustomer.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.common.Other.CommonEvent;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Fragment.GoodsIndexFragment;
import com.szy.yishopcustomer.Fragment.GoodsIndexIntegralFragment;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.MenuPopwindow;

public class GoodsIntegralActivity extends YSCBaseActivity implements GoodsIndexFragment.OnClickListener {

    private GoodsIndexIntegralFragment goodsInfoFragment;

    public static LinearLayout mIndexButton;
    public static LinearLayout mCollectButton;

    public static LinearLayout mBuyNowButton;
    public static TextView mButtonTwo;
    public static ImageView mTelImage;
    public static TextView mTelText;

    Toolbar toolbar;

    private TextView radioButtonIndex;
    private TextView radioButtonDetail;

    boolean autoCheck = false;

    private void initBottomLayout() {
        radioButtonIndex = (TextView) findViewById(R.id.radioButtonIndex);
        radioButtonDetail = (TextView) findViewById(R.id.radioButtonDetail);

        mBuyNowButton = (LinearLayout) findViewById(R.id.fragment_goods_buy_now_button);
        mButtonTwo = (TextView) findViewById(R.id.fragment_goods_add_to_cart_button_two);
        mTelImage = (ImageView) findViewById(R.id.tab_star);
        mTelText = (TextView) findViewById(R.id.textView14);

        mIndexButton = (LinearLayout) findViewById(R.id.fragment_goods_index_button);
        mCollectButton = (LinearLayout) findViewById(R.id.fragment_goods_collect_button);


        mIndexButton.setOnClickListener(this);
        mCollectButton.setOnClickListener(this);

        radioButtonIndex.setOnClickListener(this);
        radioButtonDetail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (Utils.isDoubleClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.fragment_goods_collect_button:
                if (goodsInfoFragment.mResponseGoodsModel != null && !goodsInfoFragment.mResponseGoodsModel.data.shop_info.aliim_enable
                        .equals("0")) {
                    goodsInfoFragment.openServiceActivity();
                } else {
                    if (!Utils.isNull(goodsInfoFragment.mResponseGoodsModel.data.shop_info.shop.service_tel)) {
                        mTelText.setText("电话");
                        mTelImage.setImageResource(R.mipmap.ic_details_phone);

                        Utils.openPhoneDialog(this,goodsInfoFragment.mResponseGoodsModel.data.shop_info.shop.service_tel);
                    } else {
                        mTelText.setText("暂无");
                        mTelImage.setImageResource(R.mipmap.tab_shop_contact);

                        Toast.makeText(this, "商家未填写电话", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.fragment_goods_index_button:
                goodsInfoFragment.openShopActivity(goodsInfoFragment.shopId);
                break;
            case R.id.radioButtonIndex:
                changeTopTab(0);
                goodsInfoFragment.scrollBy(0);
                break;
            case R.id.radioButtonDetail:
                changeTopTab(1);
                goodsInfoFragment.scrollBy(1);
                break;
            default:
                ViewType viewType = Utils.getViewTypeOfTag(v);
                int position = Utils.getPositionOfTag(v);
                int extra = Utils.getExtraInfoOfTag(v);
                switch (viewType) {
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral_goods);
        initToolbar();


        FragmentManager fm = getSupportFragmentManager();
        if (goodsInfoFragment == null) {
            goodsInfoFragment = new GoodsIndexIntegralFragment();
            fm.beginTransaction().add(R.id.id_fragment_container, goodsInfoFragment).commit();
        }

        goodsInfoFragment.setOnClickListener(this);

        initBottomLayout();

        changeTopTab(0);
    }


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.activity_goods_toolbar);
        toolbar.setBackgroundResource(R.drawable.toolbar_bottom_border);
        toolbar.setNavigationIcon(R.mipmap.btn_back_dark);//设置返回
        if (App.getCartNumber() > 0) {
            toolbar.inflateMenu(R.menu.activity_base_goods_custem2);//设置右上角的填充菜单
        } else {
            toolbar.inflateMenu(R.menu.activity_base_goods_custem);//设置右上角的填充菜单
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoodsIntegralActivity.this.finish();
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();

                switch (menuItemId) {
                    case R.id.activity_base_moreMenu:
                        mCustemMenuStyle = MenuPopwindow.MENU_STYLE_BASE;
                        initCustemMenu();
                        menuPopwindow.showPopupWindow(GoodsIntegralActivity.this.getWindow().getDecorView
                                ());
                        break;
                    case R.id.activity_base_cartMenu:
                        openCartActivity();
                        break;
                }
                return true;
            }
        });
    }


    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_UPDATE_CART_NUMBER:
                toolbar.getMenu().clear();
                if (App.getCartNumber() > 0) {
                    toolbar.inflateMenu(R.menu.activity_base_goods_custem2);//设置右上角的填充菜单
                } else {
                    toolbar.inflateMenu(R.menu.activity_base_goods_custem);//设置右上角的填充菜单
                }
                break;
        }
    }

/*    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.activity_base_cartMenu).setVisible(false);
        menu.findItem(R.id.activity_base_cartNumMenu).setVisible(true);
        menu.findItem(R.id.activity_base_moreMenu).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }*/

    private void openCartActivity() {
        startActivity(new Intent().setClass(this, CartActivity.class));
    }

    private void changeTopTab(int i){
        if(i == 0) {
            radioButtonIndex.setSelected(true);
            radioButtonDetail.setSelected(false);
        } else {
            radioButtonIndex.setSelected(false);
            radioButtonDetail.setSelected(true);
        }
    }

    @Override
    public void onClickListener(String id) {

        if ("0".equals(id)) {
            changeTopTab(0);
        } else {
            changeTopTab(1);
        }
    }
}