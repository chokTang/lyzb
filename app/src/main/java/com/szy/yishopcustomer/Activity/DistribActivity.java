package com.szy.yishopcustomer.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szy.common.Fragment.CommonFragment;
import com.szy.common.Other.CommonEvent;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Fragment.DistribCategoryFragment;
import com.szy.yishopcustomer.Fragment.DistribFragment;
import com.szy.yishopcustomer.Fragment.DistribGoodsListFragment;
import com.szy.yishopcustomer.Fragment.DistribHelpFragment;
import com.szy.yishopcustomer.Fragment.DistribIncomeAddFragment;
import com.szy.yishopcustomer.Fragment.DistribIncomeDetialsFragment;
import com.szy.yishopcustomer.Fragment.DistribIncomeFragment;
import com.szy.yishopcustomer.Fragment.DistribIncomeRecordFragment;
import com.szy.yishopcustomer.Fragment.DistribOrderFragment;
import com.szy.yishopcustomer.Fragment.DistribShopSetFragment;
import com.szy.yishopcustomer.Fragment.DistribTeamFragment;
import com.szy.yishopcustomer.Fragment.DistributorIndexFragment;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.App;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的分销
 * Created by liwei on 2017/7/17.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DistribActivity extends YSCBaseActivity {
    private static final String DISTRIB_FRAGMENT_TAG = "HOME";
    private static final String DISTRIB_INCOME_FRAGMENT_TAG = "INCOME";
    private static final String DISTRIB_INCOME_ADD_FRAGMENT_TAG = "INCOME_ADD";
    private static final String DISTRIB_INCOME_RECORD_FRAGMENT_TAG = "INCOME_RECORD";
    private static final String DISTRIB_INCOME_DETAILS_FRAGMENT_TAG = "INCOME_DETAILS";
    private static final String DISTRIB_ORDER_FRAGMENT_TAG = "INCOME_ORDER";
    private static final String DISTRIB_TEAM_FRAGMENT_TAG = "INCOME_TEAM";
    private static final String DISTRIB_HELP_FRAGMENT_TAG = "INCOME_HELP";
    private static final String DISTRIB_DISTRIBUTOR_INDEX_TAG = "DISTRIBUTOR_INDEX";
    private static final String DISTRIB_DISTRIB_CATEGORY_TAG = "DISTRIB_CATEGORY";
    private static final String DISTRIB_GOODS_LIST_TAG = "DISTRIB_GOODS_LIST";
    private static final String DISTRIB_DISTRIB_SHOP_SET_TAG = "DISTRIB_SHOP_SET";

    @BindView(R.id.activity_root_tabHome)
    LinearLayout mTabHome;
    @BindView(R.id.activity_root_tabCategory)
    LinearLayout mTabCategory;
    @BindView(R.id.activity_root_tabDistrib)
    LinearLayout mTabDistrib;
    @BindView(R.id.activity_root_tabCart)
    RelativeLayout mTabCart;
    @BindView(R.id.activity_root_tabUser)
    LinearLayout mTabUser;

    @BindView(R.id.activity_root_tab_cart_badgeTextView)
    TextView mCartNumberTextView;

    @BindView(R.id.activity_common_toolbar)
    Toolbar mToolbar;

    private DistribFragment distribFragment;
    private DistribIncomeFragment distribIncomeFragment;
    private DistribIncomeAddFragment distribIncomeAddFragment;
    private DistribIncomeRecordFragment distribIncomeRecordFragment;
    private DistribIncomeDetialsFragment distribIncomeDetailsFragment;
    private DistribOrderFragment distribOrderFragment;
    private DistribTeamFragment distribTeamFragment;
    private DistribHelpFragment distribHelpFragment;
    private DistributorIndexFragment distributorIndexFragment;
    private DistribCategoryFragment distribCategoryFragment;
    private DistribGoodsListFragment distribGoodsListFragment;
    private DistribShopSetFragment distribShopSetFragment;

    @BindView(R.id.activity_root_tab_distrib_textView)
    TextView mTabDistribTextView;

    private List<View> mTabs;
    private String distribText;

    private int lastPage;
    private String currentTag;

    @Override
    public CommonFragment createFragment() {
        return null;
    }

    @Override
    @SuppressLint("InflateParams")
    protected void onCreate(Bundle savedInstanceState) {
        mLayoutId = R.layout.activity_distrib;
        super.onCreate(savedInstanceState);

        mTabHome.setOnClickListener(this);
        mTabCategory.setOnClickListener(this);
        mTabDistrib.setOnClickListener(this);
        mTabCart.setOnClickListener(this);
        mTabUser.setOnClickListener(this);

        mTabs = new ArrayList<>();
        mTabs.add(mTabHome);
        mTabs.add(mTabCategory);
        mTabs.add(mTabDistrib);
        mTabs.add(mTabCart);
        mTabs.add(mTabUser);

        Intent intent = getIntent();
        distribText = intent.getStringExtra(Key.KEY_DISTRIB_TEXT.getValue());
        mTabDistribTextView.setText(distribText + "中心");
        updateCartView();
        setCurrentTab(2);
    }

    public void changeMenu(String type){
        mToolbar.getMenu().clear();

        //编辑模式
        if(type.equals("edit")){
            mToolbar.inflateMenu(R.menu.activity_menu_confirm);

            //编辑状态下点击返回按钮返回 “微店信息” 首页
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    distribShopSetFragment.showNormalLayout();
                    //mToolbar.inflateMenu(R.menu.activity_base_custem);//设置右上角的填充菜单
                }
            });

            //改为：编辑状态下点击返回按钮返回“分销中心”
            /*mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    distribShopSetFragment.showNormalLayout();
                    setCurrentTab(2);
                }
            });*/

            mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int menuItemId = item.getItemId();
                    switch (menuItemId) {
                        case R.id.action_confirm:
                            distribShopSetFragment.submit();
                            break;
                    }
                    return true;
                }
            });
        }else {
            //非编辑状态下 点击返回 返回“分销中心”
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setCurrentTab(2);
                }
            });
        }

    }


    @Override
    public void onBackPressed() {
        if (!currentTag.equals(DISTRIB_FRAGMENT_TAG) && !currentTag.equals
                (DISTRIB_DISTRIB_SHOP_SET_TAG)) {
            setCurrentTab(2);
        } else if (currentTag.equals(DISTRIB_DISTRIB_SHOP_SET_TAG)) {
            if(distribShopSetFragment.layouttype.equals("edit")){
                distribShopSetFragment.showNormalLayout();
            }else{
                setCurrentTab(2);
            }
        } else {
            // handle by activity
            super.onBackPressed();
        }
    }

    @Subscribe
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_OPEN_DISTRIB_INCOME:
                setCurrentTab(5);
                break;
            case EVENT_OPEN_DISTRIB_INCOME_ADD:
                setCurrentTab(6);
                break;
            case EVENT_OPEN_DISTRIB_INCOME_RECORD:
                setCurrentTab(7);
                break;
            case EVENT_OPEN_DISTRIB_INCOME_DETAILS:
                setCurrentTab(8);
                break;
            case EVENT_OPEN_DISTRIB_ORDER:
                setCurrentTab(9);
                break;
            case EVENT_OPEN_DISTRIB_TEAM:
                setCurrentTab(10);
                break;
            case EVENT_OPEN_DISTRIB_HELP:
                setCurrentTab(11);
                break;
            case EVENT_OPEN_DISTRIBUTOR_INDEX:
                setCurrentTab(12);
                break;
            case EVENT_OPEN_DISTRIB_CATEGORY:
                setCurrentTab(13);
                break;
            case EVENT_OPEN_DISTRIB_GOODS_LIST:
                setCurrentTab(14);
                break;
            case EVENT_OPEN_DISTRIB_SET:
                setCurrentTab(15);
                break;
            case EVENT_UPDATE_CART_NUMBER:
                updateCartView();
                break;
            case EVENT_DISTRIB_MENU_CHANGE:
                String type = event.getMessage();
                changeMenu(type);
                break;
            default:
                super.onEvent(event);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_root_tabHome:
                setCurrentTab(0);
                break;
            case R.id.activity_root_tabCategory:
                setCurrentTab(1);
                break;
            case R.id.activity_root_tabDistrib:
                setCurrentTab(2);
                break;
            case R.id.activity_root_tabCart:
                setCurrentTab(3);
                break;
            case R.id.activity_root_tabUser:
                setCurrentTab(4);
                break;
            default:
                super.onClick(v);
        }
    }

    public void setCurrentTab(final int index) {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index == 2) {
                    finish();
                } else if (index == 5) {
                    setCurrentTab(2);
                } else if (index == 14) {
                    setCurrentTab(13);
                } else {
                    setCurrentTab(lastPage);
                }
            }
        });

        switch (index) {
            case 0:
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_INDEX.getValue()));
                startActivity(new Intent().setClass(this, RootActivity.class));
                finish();
                break;
            case 1:
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_CATEGORY_TAB
                        .getValue()));
                startActivity(new Intent().setClass(this, RootActivity.class));
                finish();
                break;
            case 2:
                lastPage = 2;
                openDistribFragment();
                break;
            case 3:
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_CART_TAB.getValue
                        ()));
                startActivity(new Intent().setClass(this, RootActivity.class));
                this.finish();
                break;
            case 4:
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_OPEN_USER_TAB.getValue
                        ()));
                startActivity(new Intent().setClass(this, RootActivity.class));
                this.finish();
                break;
            case 5:
                lastPage = 5;
                openDistribIncomeFragment();
                break;
            case 6:
                openDistribIncomeAddFragment();
                break;
            case 7:
                openDistribIncomeRecordFragment();
                break;
            case 8:
                openDistribIncomeDetailsFragment();
                break;
            case 9:
                openDistribOrderFragment();
                break;
            case 10:
                openDistribTeamFragment();
                break;
            case 11:
                openDistribHelpFragment();
                break;
            case 12:
                openDistributorIndexFragment();
                break;
            case 13:
                openDistribCategoryFragment();
                break;
            case 14:
                openDistribGoodsListFragment();
                break;
            case 15:
                openDistribShopSetFragment();
                break;
        }
        updateTabSelectedStatus(index);
    }


    @SuppressLint("RestrictedApi")
    private void showFragment(String tag, Fragment showFragment) {
        boolean mExist = false;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (getSupportFragmentManager().getFragments() != null) {
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                if (!fragment.getTag().equalsIgnoreCase(tag)) {
                    transaction.hide(fragment);
                } else {
                    showFragment = fragment;
                    mExist = true;
                }
            }
        }
        if (!mExist) {
            transaction.add(R.id.activity_distrib_fragmentContainer, showFragment, tag);
        }
        transaction.show(showFragment);
        currentTag = tag;
        //        transaction.commitAllowingStateLoss();
        safeCommit(transaction);
    }

    private void openDistribFragment() {
        if (distribFragment == null) {
            distribFragment = new DistribFragment();
        }
        showFragment(DISTRIB_FRAGMENT_TAG, distribFragment);
        //mToolbar.setVisibility(View.GONE);
        this.setTitle(distribText + "中心");
    }

    private void openDistribIncomeFragment() {
        if (distribIncomeFragment == null) {
            distribIncomeFragment = new DistribIncomeFragment();
        }
        showFragment(DISTRIB_INCOME_FRAGMENT_TAG, distribIncomeFragment);
        this.setTitle("我的账户");
    }

    private void openDistribIncomeAddFragment() {
        if (distribIncomeAddFragment == null) {
            distribIncomeAddFragment = new DistribIncomeAddFragment();
        }
        showFragment(DISTRIB_INCOME_ADD_FRAGMENT_TAG, distribIncomeAddFragment);
        this.setTitle("我要提现");
    }

    private void openDistribIncomeRecordFragment() {
        if (distribIncomeRecordFragment == null) {
            distribIncomeRecordFragment = new DistribIncomeRecordFragment();
        }
        showFragment(DISTRIB_INCOME_RECORD_FRAGMENT_TAG, distribIncomeRecordFragment);
        this.setTitle(getResources().getString(R.string.withdrawalrecord));
    }

    private void openDistribIncomeDetailsFragment() {
        if (distribIncomeDetailsFragment == null) {
            distribIncomeDetailsFragment = new DistribIncomeDetialsFragment();
        }
        showFragment(DISTRIB_INCOME_DETAILS_FRAGMENT_TAG, distribIncomeDetailsFragment);
        this.setTitle("收入明细");
    }

    private void openDistribOrderFragment() {
        if (distribOrderFragment == null) {
            distribOrderFragment = new DistribOrderFragment();
        }
        showFragment(DISTRIB_ORDER_FRAGMENT_TAG, distribOrderFragment);
        this.setTitle(distribText + "订单");
    }

    private void openDistribTeamFragment() {
        if (distribTeamFragment == null) {
            distribTeamFragment = new DistribTeamFragment();
        }
        showFragment(DISTRIB_TEAM_FRAGMENT_TAG, distribTeamFragment);
        this.setTitle("我的团队");
    }

    private void openDistribHelpFragment() {
        if (distribHelpFragment == null) {
            distribHelpFragment = new DistribHelpFragment();
        }
        showFragment(DISTRIB_HELP_FRAGMENT_TAG, distribHelpFragment);
        this.setTitle("分销帮助中心");

    }

    private void openDistributorIndexFragment() {
        if (distributorIndexFragment == null) {
            distributorIndexFragment = new DistributorIndexFragment();
        }
        showFragment(DISTRIB_DISTRIBUTOR_INDEX_TAG, distributorIndexFragment);
        this.setTitle("我的微店");
    }


    private void openDistribCategoryFragment() {
        if (distribCategoryFragment == null) {
            distribCategoryFragment = new DistribCategoryFragment();
        }

        showFragment(DISTRIB_DISTRIB_CATEGORY_TAG, distribCategoryFragment);
        this.setTitle("分销商商品分类");
    }

    private void openDistribGoodsListFragment() {
        String catId = distribCategoryFragment.getCatId();

        if (distribGoodsListFragment == null) {
            distribGoodsListFragment = new DistribGoodsListFragment();
            Bundle arguments = new Bundle();
            arguments.putString(Key.KEY_REQUEST_CATEGORY_ID.getValue(), catId);
            distribGoodsListFragment.setArguments(arguments);
        }

        distribGoodsListFragment.setCatId(catId);
        showFragment(DISTRIB_GOODS_LIST_TAG, distribGoodsListFragment);
        this.setTitle("选择分销商商品");
    }

    private void openDistribShopSetFragment() {
        if (distribShopSetFragment == null) {
            distribShopSetFragment = new DistribShopSetFragment();
        }

        showFragment(DISTRIB_DISTRIB_SHOP_SET_TAG, distribShopSetFragment);
        this.setTitle("微店信息");
    }

    private void updateTabSelectedStatus(int index) {
        for (int i = 0; i < 5; i++) {
            if (index == i) {
                mTabs.get(i).setSelected(true);
            } else {
                mTabs.get(i).setSelected(false);
            }
        }
    }


    private void updateCartView() {
        mCartNumberTextView.setVisibility(View.VISIBLE);
        if ("99+".equals(App.getCartString())) {
            mCartNumberTextView.setTextSize(8);
        } else {
            if (Integer.parseInt(App.getCartString()) >= 10) {
                mCartNumberTextView.setTextSize(8);
            } else {
                mCartNumberTextView.setTextSize(10);
            }
        }
        mCartNumberTextView.setText(App.getCartString());
    }
}
