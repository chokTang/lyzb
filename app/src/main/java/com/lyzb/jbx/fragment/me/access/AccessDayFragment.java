package com.lyzb.jbx.fragment.me.access;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.like.longshaolib.base.BaseFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.cenum.DayEnum;
import com.lyzb.jbx.model.me.AcceNumberModel;
import com.lyzb.jbx.mvp.presenter.me.AccessPresenter;
import com.lyzb.jbx.mvp.view.me.IAccessView;

/**
 * 客户追踪的近30天|7天|今日的信息fragment
 *
 * @author shidengzhong
 */
public class AccessDayFragment extends BaseFragment<AccessPresenter> implements IAccessView, View.OnClickListener {

    //参数
    private static final String PARAMS_ID = "params_id";
    private static final String PARAMS_DAY = "params_day";
    private static final String PARAMS_NAME = "params_name";
    private String mUserId = "";//如果为空 则是自己的数据
    private String mDayType = DayEnum.DAY_THIRTY.getValue();
    private String mUserName = "";

    private RelativeLayout layout_access;
    private TextView tv_access_number;

    private RelativeLayout layout_share;
    private TextView tv_share_number;

    private RelativeLayout layout_account;
    private TextView tv_account_number;

    private RelativeLayout layout_goods;
    private TextView tv_goods_number;

    public static AccessDayFragment newIntance(String userId, String userName, String dayType) {
        AccessDayFragment fragment = new AccessDayFragment();
        Bundle args = new Bundle();
        args.putString(PARAMS_ID, userId);
        args.putString(PARAMS_DAY, dayType);
        args.putString(PARAMS_NAME, userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mUserId = args.getString(PARAMS_ID);
            mDayType = args.getString(PARAMS_DAY);
            mUserName = args.getString(PARAMS_NAME);
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        layout_access = findViewById(R.id.layout_access);
        layout_share = findViewById(R.id.layout_share);
        layout_account = findViewById(R.id.layout_account);
        layout_goods = findViewById(R.id.layout_goods);
        tv_access_number = findViewById(R.id.tv_access_number);
        tv_share_number = findViewById(R.id.tv_share_number);
        tv_account_number = findViewById(R.id.tv_account_number);
        tv_goods_number = findViewById(R.id.tv_goods_number);

        layout_access.setOnClickListener(this);
        layout_share.setOnClickListener(this);
        layout_account.setOnClickListener(this);
        layout_goods.setOnClickListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mPresenter.getNumber(mDayType, mUserId);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_access_day;
    }

    @Override
    public void onAccessNumber(AcceNumberModel model) {
        if (model == null) return;
        tv_access_number.setText(String.valueOf(model.getViewNum()));
        tv_share_number.setText(String.valueOf(model.getShareNum()));
        tv_account_number.setText(String.valueOf(model.getUserNum()));
        tv_goods_number.setText(String.valueOf(model.getOrderNum()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击访问次数
            case R.id.layout_access:
                childStart(AccessDayDetailFragment.newIntance(mUserId, mUserName, mDayType));
                break;
            //点击分享次数
            case R.id.layout_share:
                childStart(AccessShareDetailFragment.newIntance(mUserId, mUserName, mDayType));
                break;
            //引流新用户
            case R.id.layout_account:
                childStart(AccessNewAccountFragment.newIntance(mUserId, mUserName, mDayType));
                break;
            //商城名片交易次数
            case R.id.layout_goods:
                childStart(AccessGoodsFragment.newIntance(mUserId, mUserName, mDayType));
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isDelayedData() {
        return false;
    }
}
