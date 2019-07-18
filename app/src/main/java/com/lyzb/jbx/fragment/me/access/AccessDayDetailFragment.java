package com.lyzb.jbx.fragment.me.access;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.like.longshaolib.base.BaseToolbarFragment;
import com.like.longshaolib.widget.BezierView;
import com.like.utilslib.app.CommonUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.cenum.AccessType;
import com.lyzb.jbx.model.cenum.DataType;
import com.lyzb.jbx.model.cenum.DayEnum;
import com.lyzb.jbx.model.me.access.AccessNumberDetailModel;
import com.lyzb.jbx.mvp.presenter.me.AccessDayDetailPresenter;
import com.lyzb.jbx.mvp.view.me.IAccessDayDetailView;
import com.szy.yishopcustomer.Util.App;

/**
 * 客户追踪-访客数据
 */
public class AccessDayDetailFragment extends BaseToolbarFragment<AccessDayDetailPresenter>
        implements IAccessDayDetailView, View.OnClickListener {

    private static final String PARAMS_ID = "params_id";
    private static final String PARAMS_NAME = "params_name";
    private static final String PARAMS_DAYTYPE = "params_daytype";

    private String mUserId = "";//如果是自己的话，可以传入空值
    private String mUserName = "";
    private String mDayType = DayEnum.DAY_THIRTY.getValue();

    private TextView tv_access_number;
    private TextView tv_card_number;
    private TextView tv_goods_number;
    private TextView tv_dynamic_number;
    private TextView tv_article_number;
    private TextView btn_find_detail;

    private BezierView bezier_app_number;
    private BezierView bezier_wxmimi_number;
    private BezierView bezier_share_number;
    private BezierView bezier_other_number;

    public static AccessDayDetailFragment newIntance(String userId, String userName, String dayType) {
        AccessDayDetailFragment fragment = new AccessDayDetailFragment();
        Bundle args = new Bundle();
        args.putString(PARAMS_ID, userId);
        args.putString(PARAMS_NAME, userName);
        args.putString(PARAMS_DAYTYPE, dayType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mUserId = args.getString(PARAMS_ID);
            mUserName = args.getString(PARAMS_NAME);
            mDayType = args.getString(PARAMS_DAYTYPE);
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        onBack();
        mToolbarTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        mToolbar.setBackgroundColor(Color.parseColor("#212837"));
        if (TextUtils.isEmpty(mUserId) || mUserId.equals(App.getInstance().userId)) {
            setToolbarTitle(String.format("%s访客分析", mDayType));
        } else {
            setToolbarTitle(String.format("%s的%s访客分析", mUserName, mDayType));
        }

        tv_access_number = findViewById(R.id.tv_access_number);
        tv_card_number = findViewById(R.id.tv_card_number);
        tv_goods_number = findViewById(R.id.tv_goods_number);
        tv_dynamic_number = findViewById(R.id.tv_dynamic_number);
        tv_article_number = findViewById(R.id.tv_article_number);
        btn_find_detail = findViewById(R.id.btn_find_detail);
        bezier_app_number = findViewById(R.id.bezier_app_number);
        bezier_wxmimi_number = findViewById(R.id.bezier_wxmimi_number);
        bezier_share_number = findViewById(R.id.bezier_share_number);
        bezier_other_number = findViewById(R.id.bezier_other_number);

        btn_find_detail.setOnClickListener(this);
        tv_card_number.setOnClickListener(this);
        tv_goods_number.setOnClickListener(this);
        tv_dynamic_number.setOnClickListener(this);
        tv_article_number.setOnClickListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mPresenter.getAccessDetail(mUserId, mDayType);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_access_day_detail;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //查看明细
            case R.id.btn_find_detail:
                start(AccessDetailFragment.newIntance(mUserId, mUserName, mDayType, AccessType.ACCESS.name(), DataType.ALL.getValue()));
                break;
            //名片
            case R.id.tv_card_number:
                start(AccessDetailFragment.newIntance(mUserId, mUserName, mDayType, AccessType.ACCESS.name(), DataType.CARD.getValue()));
                break;
            //商品
            case R.id.tv_goods_number:
                start(AccessDetailFragment.newIntance(mUserId, mUserName, mDayType, AccessType.ACCESS.name(), DataType.GOODS.getValue()));
                break;
            //动态
            case R.id.tv_dynamic_number:
                start(AccessDetailFragment.newIntance(mUserId, mUserName, mDayType, AccessType.ACCESS.name(), DataType.DYNAMIC.getValue()));
                break;
            //热文
            case R.id.tv_article_number:
                start(AccessDetailFragment.newIntance(mUserId, mUserName, mDayType, AccessType.ACCESS.name(), DataType.ACRTICE.getValue()));
                break;
            default:
                break;
        }
    }

    @Override
    public void onDetailResult(AccessNumberDetailModel model) {
        if (model == null) return;
        tv_access_number.setText(String.valueOf(model.getViewNum()));
        tv_card_number.setText(String.format("%d次\n名片", model.getExtNum()));
        tv_goods_number.setText(String.format("%d次\n商品", model.getShopNum()));
        tv_dynamic_number.setText(String.format("%d次\n动态", model.getTopicNum()));
        tv_article_number.setText(String.format("%d次\n热文", model.getHotNum()));

        bezier_app_number.setMaxValue(CommonUtil.converToT(model.getAppRatioNum(), 0.0f));
        bezier_app_number.startAnimation();

        bezier_wxmimi_number.setMaxValue(CommonUtil.converToT(model.getMiniRatioNum(), 0.0f));
        bezier_wxmimi_number.startAnimation();

        bezier_share_number.setMaxValue(CommonUtil.converToT(model.getWxRatioNum(), 0.0f));
        bezier_share_number.startAnimation();

        bezier_other_number.setMaxValue(CommonUtil.converToT(model.getOtherRatioNum(), 0.0f));
        bezier_other_number.startAnimation();
    }
}
