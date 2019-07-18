package com.lyzb.jbx.fragment.me.access;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseToolbarFragment;
import com.like.utilslib.app.AppUtil;
import com.like.utilslib.image.LoadImageUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.AcsListAdapter;
import com.lyzb.jbx.dialog.InterRemindDialog;
import com.lyzb.jbx.fragment.dynamic.DynamicDetailFragment;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.model.cenum.AccessType;
import com.lyzb.jbx.model.cenum.DataType;
import com.lyzb.jbx.model.cenum.DayEnum;
import com.lyzb.jbx.model.me.AcsRecomdModel;
import com.lyzb.jbx.model.me.access.AccessMemberModel;
import com.lyzb.jbx.mvp.presenter.me.AcsDetPresenter;
import com.lyzb.jbx.mvp.view.me.IAcsDetView;
import com.lyzb.jbx.util.AppPreference;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.szy.yishopcustomer.Activity.im.ImCommonActivity;
import com.szy.yishopcustomer.ViewModel.im.ImHeaderGoodsModel;

import java.util.List;

/**
 * @author wyx
 * @role 某个人的全部访问记录
 * @time 2019 2019/3/8 15:23
 */

public class AccessDetailItemFragment extends BaseToolbarFragment<AcsDetPresenter> implements OnRefreshLoadMoreListener,
        IAcsDetView, View.OnClickListener {
    private static final String KEY_MODEL = "key_model";
    private AccessMemberModel mBean = null;
    private String mAccessType = AccessType.ACCESS.name();//访客的类型
    private String mDayType = DayEnum.DAY_THIRTY.getValue();//天行数据
    private static final String PARAMS_ACCESS = "params_access";
    private static final String PARAMS_USERNAME = "params_username";
    private static final String PARAM_DATA = "param_data";
    private static final String PARAMS_DAY = "PARAMS_DAY";
    private int mDataType = DataType.ALL.getValue();
    private String mUserName = "";//xx访问人的名字（点击我的企业-数据统计下-我去查看xx人的访客信息的xx人的名字） 与当前xx的人名字不一样 如果为空，表示我的访客信息的访问人的信息

    ImageView headImg;
    ImageView vipImg;
    TextView nameText;
    TextView numberText;
    private TextView tv_inter_account;

    ImageView phoneImg;
    ImageView wxImg;

    SmartRefreshLayout mRefreshLayout;
    RecyclerView acsRecy;

    private AcsListAdapter mAdapter = null;

    public static AccessDetailItemFragment newIntance(AccessMemberModel bean, String accessType, String dayType, int dataType) {
        return newIntance(bean, accessType, null, dayType, dataType);
    }

    /**
     * 初始化
     *
     * @param bean
     * @param accessType
     * @param userName   xx的访问记录，如果是自己的话，userName为空
     * @return
     */
    public static AccessDetailItemFragment newIntance(AccessMemberModel bean, String accessType, String userName, String dayType, int dataType) {
        AccessDetailItemFragment fragment = new AccessDetailItemFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_MODEL, bean);
        args.putString(PARAMS_ACCESS, accessType);
        args.putString(PARAMS_USERNAME, userName);
        args.putString(PARAMS_DAY, dayType);
        args.putInt(PARAM_DATA, dataType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mBean = (AccessMemberModel) bundle.getSerializable(KEY_MODEL);
            mAccessType = bundle.getString(PARAMS_ACCESS);
            mUserName = bundle.getString(PARAMS_USERNAME);
            mDataType = bundle.getInt(PARAM_DATA, DataType.ALL.getValue());
            mDayType = bundle.getString(PARAMS_DAY);
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        onBack();
        headImg = findViewById(R.id.img_acs_head);
        vipImg = findViewById(R.id.img_acs_vip);
        nameText = findViewById(R.id.tv_acs_name);
        numberText = findViewById(R.id.tv_acs_text);
        phoneImg = findViewById(R.id.img_acs_phone);
        wxImg = findViewById(R.id.img_acs_wx);
        mRefreshLayout = findViewById(R.id.sf_un_me_access);
        acsRecy = findViewById(R.id.recy_un_me_access);
        tv_inter_account = findViewById(R.id.tv_inter_account);

        mRefreshLayout.setOnRefreshListener(this);
        tv_inter_account.setOnClickListener(this);
        phoneImg.setOnClickListener(this);
        wxImg.setOnClickListener(this);
        headImg.setOnClickListener(this);

        acsRecy.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                AcsRecomdModel model = mAdapter.getPositionModel(position);
                if (model.getType() == 2) {//表示动态
                    start(DynamicDetailFragment.Companion.newIntance(model.getTypeId()));
                }
            }
        });
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        setToolbarTitle(mBean.getGaName() + "的访问记录");
        nameText.setText(mBean.getGaName());
        if (mDataType == DataType.ALL.getValue()) {
            numberText.setText(String.format("看了名片%d次，动态%d次，商品%d次,热文%d次", mBean.getUserExtNum(), mBean.getTopicNum(), mBean.getGoodsNum(), mBean.getHotNum()));
        } else if (mDataType == DataType.CARD.getValue()) {
            numberText.setText(String.format("看了名片%d次", mBean.getUserExtNum()));
        } else if (mDataType == DataType.DYNAMIC.getValue()) {
            numberText.setText(String.format("看了动态%d次", mBean.getTopicNum()));
        } else if (mDataType == DataType.GOODS.getValue()) {
            numberText.setText(String.format("看了商品%d次", mBean.getGoodsNum()));
        } else if (mDataType == DataType.ACRTICE.getValue()) {
            numberText.setText(String.format("看了热文%d次", mBean.getVisitcount()));
            setToolbarTitle(mBean.getGsName() + "的访问记录");
            nameText.setText(mBean.getGsName());
        }
        LoadImageUtil.loadRoundSizeImage(headImg, mBean.getHeadimg(), 40);

        if (mBean.getUserVipAction().size() > 0) {
            vipImg.setVisibility(View.VISIBLE);
        }

        //初始化是否有设置有意向客户的按钮
        if (!TextUtils.isEmpty(mUserName) || mDataType == DataType.ACRTICE.getValue()) {
            tv_inter_account.setVisibility(View.GONE);
        } else {
            tv_inter_account.setVisibility(View.VISIBLE);
            if (mBean.isCustomersStatus()) {
                tv_inter_account.setText("已为意向");
                tv_inter_account.setTextColor(ContextCompat.getColor(getContext(), R.color.fontcColor3));
                tv_inter_account.setBackgroundResource(R.drawable.shape_gray_round);
            } else {
                tv_inter_account.setText("设为意向");
                tv_inter_account.setTextColor(ContextCompat.getColor(getContext(), R.color.fontcColor1));
                tv_inter_account.setBackgroundResource(R.drawable.shape_gray_round_f3);
            }
        }

        mAdapter = new AcsListAdapter(getContext(), null);
        mAdapter.setmAccessType(mAccessType);
        mAdapter.setmUserName(mUserName);
        mAdapter.setmDataType(mDataType);
        mAdapter.setLayoutManager(acsRecy);
        acsRecy.setAdapter(mAdapter);

        mPresenter.getAcsList(true, mBean.getUserId(), mAccessType, mDataType, mDayType, mBean.getOpenId(), mBean.getUserGuid());
    }

    @Override
    public void onAcsList(boolean isRefresh, List<AcsRecomdModel> list) {
        if (isRefresh) {
            mAdapter.update(list);
            mRefreshLayout.finishRefresh();
            if (list.size() < 50) {
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            }
        } else {
            mAdapter.addAll(list);
            if (list.size() < 50) {
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            } else {
                mRefreshLayout.finishLoadMore();
            }
        }
    }

    @Override
    public void onSettingAccountResult() {
        if (AppPreference.getIntance().getUserShowInterAccount()) {
            new InterRemindDialog()
                    .show(getFragmentManager(), "showInterHint");
        }
        mBean.setCustomersStatus(!mBean.isCustomersStatus());
        if (mBean.isCustomersStatus()) {
            tv_inter_account.setText("已为意向");
            tv_inter_account.setTextColor(ContextCompat.getColor(getContext(), R.color.fontcColor3));
            tv_inter_account.setBackgroundResource(R.drawable.shape_gray_round);
        } else {
            tv_inter_account.setText("设为意向");
            tv_inter_account.setTextColor(ContextCompat.getColor(getContext(), R.color.fontcColor1));
            tv_inter_account.setBackgroundResource(R.drawable.shape_gray_round_f3);
        }
        if (getPreFragment() instanceof AccessDetailFragment) {
            AccessDetailFragment detailFragment = (AccessDetailFragment) getPreFragment();
            detailFragment.noticeItemChange(mBean);
        }
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_un_me_access_all;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getAcsList(false, mBean.getUserId(), mAccessType, mDataType, mDayType, mBean.getOpenId(), mBean.getUserGuid());
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getAcsList(true, mBean.getUserId(), mAccessType, mDataType, mDayType, mBean.getOpenId(), mBean.getUserGuid());
    }

    @Override
    public void onClick(View v) {
        if (mBean == null) {
            return;
        }
        switch (v.getId()) {
            //进入个人名片
            case R.id.img_acs_head:
                start(CardFragment.newIntance(2, mBean.getUserId()));
                break;
            //设置为意向客户
            case R.id.tv_inter_account:
                if (!mBean.isCustomersStatus()) {
                    mPresenter.settingInterAccount(mBean.getId());
                }
                break;
            //拨打电话
            case R.id.img_acs_phone:
                //拨打电话
                if (!TextUtils.isEmpty(mBean.getMobile())) {
                    AppUtil.openDial(getContext(), mBean.getMobile());
                } else {
                    showToast("未设置电话号码,不可以拨打");
                }
                break;
            //聊天
            case R.id.img_acs_wx:
                Intent intent = new Intent(getContext(), ImCommonActivity.class);
                ImHeaderGoodsModel model = new ImHeaderGoodsModel();

                model.setChatType(EaseConstant.CHATTYPE_SINGLE);
                model.setShopImName("jbx" + mBean.getUserId());
                model.setShopName(mBean.getGaName());
                model.setShopHeadimg(mBean.getHeadimg());
                model.setShopId("");

                Bundle args = new Bundle();
                args.putSerializable(ImCommonActivity.PARAMS_GOODS, model);
                intent.putExtras(args);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
