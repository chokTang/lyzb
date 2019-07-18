package com.lyzb.jbx.fragment.me.access;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.like.longshaolib.base.BaseToolbarFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.cenum.AccessType;
import com.lyzb.jbx.model.cenum.DataType;
import com.lyzb.jbx.model.cenum.DayEnum;
import com.lyzb.jbx.model.me.access.AccessShareDetailModel;
import com.lyzb.jbx.mvp.presenter.me.AccessShareDetailPresenter;
import com.lyzb.jbx.mvp.view.me.IAccessShareDetailView;
import com.szy.yishopcustomer.Util.App;

/**
 * xx某人的30天|7天|今日的分享数据
 */
public class AccessShareDetailFragment extends BaseToolbarFragment<AccessShareDetailPresenter> implements IAccessShareDetailView, View.OnClickListener {

    private static final String PARAMS_ID = "params_id";
    private static final String PARAMS_NAME = "params_name";
    private static final String PARAMS_DAYTYPE = "params_daytype";
    private String mUserId = "";//如果是自己的话，可以传入空值
    private String mUserName = "";
    private String mDayType = DayEnum.DAY_THIRTY.getValue();

    private TextView tv_share_number;
    private TextView btn_find_detail;
    private TextView tv_card_value;
    private TextView tv_dynamic_value;
    private TextView tv_hot_value;

    private LinearLayout layout_card;
    private LinearLayout layout_dynamic;
    private LinearLayout layout_hot;

    public static AccessShareDetailFragment newIntance(String userId, String userName, String dayType) {
        AccessShareDetailFragment fragment = new AccessShareDetailFragment();
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
        if (TextUtils.isEmpty(mUserId) || mUserId.equals(App.getInstance().userId)) {
            setToolbarTitle(String.format("%s分享数据分析", mDayType));
        } else {
            setToolbarTitle(String.format("%s的%s分享数据分析", mUserName, mDayType));
        }

        tv_share_number = findViewById(R.id.tv_share_number);
        btn_find_detail = findViewById(R.id.btn_find_detail);
        tv_card_value = findViewById(R.id.tv_card_value);
        tv_dynamic_value = findViewById(R.id.tv_dynamic_value);
        tv_hot_value = findViewById(R.id.tv_hot_value);
        layout_card = findViewById(R.id.layout_card);
        layout_dynamic = findViewById(R.id.layout_dynamic);
        layout_hot = findViewById(R.id.layout_hot);

        btn_find_detail.setOnClickListener(this);
        layout_card.setOnClickListener(this);
        layout_dynamic.setOnClickListener(this);
        layout_hot.setOnClickListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mPresenter.getShareDetail(mUserId, mDayType);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_access_share_detail;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //查看明细
            case R.id.btn_find_detail:
                start(AccessDetailFragment.newIntance(mUserId, mUserName, mDayType, AccessType.SHARE.name()));
                break;
            //名片
            case R.id.layout_card:
                start(AccessDetailFragment.newIntance(mUserId, mUserName, mDayType, AccessType.SHARE.name(), DataType.CARD.getValue()));
                break;
            //动态
            case R.id.layout_dynamic:
                start(AccessDetailFragment.newIntance(mUserId, mUserName, mDayType, AccessType.SHARE.name(), DataType.DYNAMIC.getValue()));
                break;
            //热文
            case R.id.layout_hot:
                start(AccessDetailFragment.newIntance(mUserId, mUserName, mDayType, AccessType.SHARE.name(), DataType.ACRTICE.getValue()));
                break;
            default:
                break;
        }
    }

    @Override
    public void onShareDetailResult(AccessShareDetailModel model) {
        if (model == null) return;
        tv_share_number.setText(String.valueOf(model.getShareSum()));
        tv_card_value.setText(String.format("分享%d次，被%d人访问了%d次", model.getShareExtSum(), model.getUserExtNum(), model.getUserExtSumNum()));
        tv_dynamic_value.setText(String.format("分享%d次，被%d人访问了%d次", model.getShareTopicSum(), model.getUserTopicNum(), model.getUserTopicSumNum()));
        tv_hot_value.setText(String.format("分享%d次，被%d人访问了%d次", model.getShareHotSum(), model.getUserHotNum(), model.getUserHotSumNum()));
    }
}
