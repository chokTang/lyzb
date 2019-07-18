package com.lyzb.jbx.fragment.me.access;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseToolbarFragment;
import com.like.utilslib.image.LoadImageUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.access.AccessDetailAdapter;
import com.lyzb.jbx.dialog.InterRemindDialog;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.model.cenum.AccessType;
import com.lyzb.jbx.model.cenum.DataType;
import com.lyzb.jbx.model.cenum.DayEnum;
import com.lyzb.jbx.model.me.access.AccessMemberModel;
import com.lyzb.jbx.mvp.presenter.me.AccessDetailPresenter;
import com.lyzb.jbx.mvp.view.me.IAccessDetailView;
import com.lyzb.jbx.util.AppPreference;
import com.lyzb.jbx.widget.GuideView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.szy.yishopcustomer.Util.App;

import java.util.List;

/**
 * 访客明细页面--公共的
 */
public class AccessDetailFragment extends BaseToolbarFragment<AccessDetailPresenter> implements IAccessDetailView, OnRefreshLoadMoreListener {

    private String mUserId = "";
    private String mUserName = "";
    private int mDataType = DataType.ALL.getValue();
    private String mDayType = DayEnum.DAY_THIRTY.getValue();
    private String mAccessType = AccessType.ACCESS.name();//访客的类型
    private boolean isShowInterAccount = false;//是否需要显示设置意向客户按钮【个人查看自己的访客分析是打开的，企业中的xxx的人访问记录是关闭的】
    private static final String PARAMS_ID = "params_id";
    private static final String PARAMS_NAME = "params_name";
    private static final String PARAMS_TYPE = "params_type";
    private static final String PARAMS_ACCESS = "params_access";
    private static final String PARAMS_DATA = "params_data";

    private SmartRefreshLayout refresh_access_detail;
    private RecyclerView recycle_detail;
    private TextView tv_total_number;
    //暂无数据
    private View empty_view;
    private TextView empty_tv;
    //单个item
    private TextView tv_inter_account;
    private TextView tv_member_name;
    private TextView tv_member_value;
    private LinearLayout item_layout;
    private ImageView img_member_header;
    private ImageView img_vip;

    private AccessDetailAdapter mAdapter;
    private int mChangePosition = -1;
    private GuideView guideView;

    public static AccessDetailFragment newIntance(String userId, String userName, String dayType, String accessType) {
        return newIntance(userId, userName, dayType, accessType, DataType.ALL.getValue());
    }

    public static AccessDetailFragment newIntance(String userId, String userName, String dayType, String accessType, int dataType) {
        AccessDetailFragment fragment = new AccessDetailFragment();
        Bundle args = new Bundle();
        args.putString(PARAMS_ID, userId);
        args.putString(PARAMS_NAME, userName);
        args.putString(PARAMS_TYPE, dayType);
        args.putString(PARAMS_ACCESS, accessType);
        args.putInt(PARAMS_DATA, dataType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mDayType = args.getString(PARAMS_TYPE);
            mUserName = args.getString(PARAMS_NAME);
            mUserId = args.getString(PARAMS_ID);
            mAccessType = args.getString(PARAMS_ACCESS);
            mDataType = args.getInt(PARAMS_DATA, DataType.ALL.getValue());
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        onBack();
        if (TextUtils.isEmpty(mUserId) || mUserId.equals(App.getInstance().userId)) {
            setToolbarTitle(String.format("%s访客明细", getDataTypeZh()));
            isShowInterAccount = true;
        } else {
            setToolbarTitle(String.format("%s的%s访客明细", mUserName, getDataTypeZh()));
            isShowInterAccount = false;
        }
        if (mDataType == DataType.ACRTICE.getValue()) {
            isShowInterAccount = false;
        }
        refresh_access_detail = findViewById(R.id.refresh_access_detail);
        recycle_detail = findViewById(R.id.recycle_detail);
        empty_view = findViewById(R.id.empty_view);
        empty_tv = findViewById(R.id.empty_tv);
        tv_inter_account = findViewById(R.id.tv_inter_account);
        tv_member_name = findViewById(R.id.tv_member_name);
        item_layout = findViewById(R.id.item_layout);
        img_member_header = findViewById(R.id.img_member_header);
        img_vip = findViewById(R.id.img_vip);
        tv_member_value = findViewById(R.id.tv_member_value);
        tv_total_number = findViewById(R.id.tv_total_number);

        refresh_access_detail.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        empty_tv.setText("暂无访客哦，请多多分享哦！");
        mAdapter = new AccessDetailAdapter(getContext(), null);
        mAdapter.setShowInterAccount(isShowInterAccount);
        mAdapter.setDataType(mDataType);
        recycle_detail.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST));
        mAdapter.setLayoutManager(recycle_detail);
        recycle_detail.setAdapter(mAdapter);

        recycle_detail.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                AccessMemberModel itemModel = mAdapter.getPositionModel(position);
                switch (view.getId()) {
                    //进入个人名片
                    case R.id.img_member_header:
                        start(CardFragment.newIntance(2, itemModel.getUserId()));
                        break;
                    //设置为意向客户
                    case R.id.tv_inter_account:
                        if (!itemModel.isCustomersStatus()) {
                            mPresenter.setInterAccount(position, itemModel.getId());
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                mChangePosition = position;
                if (TextUtils.isEmpty(mUserId) || mUserId.equals(App.getInstance().userId)) {
                    start(AccessDetailItemFragment.newIntance(mAdapter.getPositionModel(position), mAccessType, mDayType, mDataType));
                } else {
                    start(AccessDetailItemFragment.newIntance(mAdapter.getPositionModel(position), mAccessType, mUserName, mDayType, mDataType));
                }
            }
        });

        mPresenter.getAccountAccess(true, mUserId, mDayType, mAccessType, mDataType);
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_access_detail;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getAccountAccess(false, mUserId, mDayType, mAccessType, mDataType);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getAccountAccess(true, mUserId, mDayType, mAccessType, mDataType);
    }

    @Override
    public void onFinshOrLoadMore(boolean isRefresh) {
        if (isRefresh) {
            refresh_access_detail.finishRefresh();
        } else {
            refresh_access_detail.finishLoadMore();
        }
    }

    @Override
    public void onAccessResult(boolean isRefresh, int total, List<AccessMemberModel> list) {
        tv_total_number.setText(String.format("访客共%d人", total));
        if (isRefresh) {
            refresh_access_detail.finishRefresh();
            if (list.size() < 10) {
                refresh_access_detail.finishLoadMoreWithNoMoreData();
            }
            mAdapter.update(list);
        } else {
            if (list.size() < 10) {
                refresh_access_detail.finishLoadMoreWithNoMoreData();
            } else {
                refresh_access_detail.finishLoadMore();
            }
            mAdapter.addAll(list);
        }

        if (mAdapter.getItemCount() == 0) {
            empty_view.setVisibility(View.VISIBLE);
        } else {
            empty_view.setVisibility(View.GONE);
            if (!isShowInterAccount || !AppPreference.getIntance().getUserFristInterAccess()) {
                return;
            }
            tv_inter_account.setVisibility(isShowInterAccount ? View.VISIBLE : View.GONE);
            item_layout.setVisibility(isShowInterAccount ? View.VISIBLE : View.GONE);
            AccessMemberModel memberModel = mAdapter.getPositionModel(0);
            tv_member_name.setText(memberModel.getGaName());
            String contentValue = getContentValue(memberModel);
            if (!TextUtils.isEmpty(contentValue)) {
                contentValue = "看了" + contentValue;
            }
            tv_member_value.setText(contentValue);

            img_vip.setVisibility(memberModel.getUserVipAction().size() > 0 ? View.VISIBLE : View.GONE);
            LoadImageUtil.loadRoundImage(img_member_header, memberModel.getHeadimg(), 4);

            if (memberModel.isCustomersStatus()) {
                tv_inter_account.setBackgroundResource(R.mipmap.ic_intention_customer);
                tv_inter_account.setTextColor(ContextCompat.getColor(getContext(), R.color.fontcColor3));
                tv_inter_account.setText("已为意向");
            } else {
                tv_inter_account.setBackgroundResource(R.mipmap.ic_setintention_customer);
                tv_inter_account.setTextColor(ContextCompat.getColor(getContext(), R.color.fontcColor1));
                tv_inter_account.setText("设为意向");
            }
            tv_inter_account.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showGuideView();
                }
            }, 100);
        }
    }

    @Override
    public void onInterAccountResult(int poistion) {
        if (AppPreference.getIntance().getUserShowInterAccount()) {
            new InterRemindDialog()
                    .show(getFragmentManager(), "showInterHint");
        }
        AccessMemberModel itemModel = mAdapter.getPositionModel(poistion);
        itemModel.setCustomersStatus(!itemModel.isCustomersStatus());
        mAdapter.change(poistion, itemModel);
    }

    /**
     * 通知某个item的数据发生了改变
     */
    public void noticeItemChange(AccessMemberModel itemModel) {
        if (mChangePosition > -1) {
            mAdapter.change(mChangePosition, itemModel);
        }
    }

    private synchronized void showGuideView() {
        final ImageView iv1 = new ImageView(getContext());
        iv1.setImageResource(R.drawable.union_guide_access);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        iv1.setLayoutParams(params1);

        final TextView iv2 = new TextView(getContext());
        iv2.setText("设为意向客户方便管理");
        iv2.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        iv2.setTextSize(18);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        iv2.setLayoutParams(params2);

        guideView = GuideView.Builder
                .newInstance(getBaseActivity())
                .setTargetView(tv_inter_account)  //设置目标view
                .setTextGuideView(iv1)      //设置文字图片
                .setCustomGuideView(iv2)    //设置 我知道啦图片
                .setOffset(0, 50)           //偏移量  x=0 y=80
                .setDirction(GuideView.Direction.BOTTOM)   //方向
                .setShape(GuideView.MyShape.RECTANGULAR)   //矩形
                .setRadius(0)                             //圆角
                .setContain(true)                         //透明的方块时候包含目标view  默认false
                .setOnclickListener(new GuideView.OnClickCallback() {
                    @Override
                    public void onClickedGuideView() {
                        item_layout.setVisibility(View.GONE);
                        AppPreference.getIntance().setUserFristInterAccess(false);
                        guideView.hide();
                    }
                })
                .build();
        guideView.show();
    }

    private String getContentValue(AccessMemberModel item) {
        StringBuilder builder = new StringBuilder();
        if (mDataType == DataType.ALL.getValue()) {
            if (item.getUserExtNum() > 0) {
                builder.append(String.format("名片%d次", item.getUserExtNum()));
            }
            if (item.getTopicNum() > 0) {
                if (!TextUtils.isEmpty(builder.toString())) {
                    builder.append("，");
                }
                builder.append(String.format("动态%d次", item.getTopicNum()));
            }
            if (item.getGoodsNum() > 0) {
                if (!TextUtils.isEmpty(builder.toString())) {
                    builder.append("，");
                }
                builder.append(String.format("商品%d次", item.getGoodsNum()));
            }
            if (item.getHotNum() > 0) {
                if (!TextUtils.isEmpty(builder.toString())) {
                    builder.append("，");
                }
                builder.append(String.format("热文%d次", item.getHotNum()));
            }
        } else if (mDataType == DataType.CARD.getValue()) {
            if (item.getUserExtNum() > 0) {
                builder.append(String.format("名片%d次", item.getUserExtNum()));
            }
        } else if (mDataType == DataType.DYNAMIC.getValue()) {
            if (item.getTopicNum() > 0) {
                builder.append(String.format("动态%d次", item.getTopicNum()));
            }
        } else if (mDataType == DataType.GOODS.getValue()) {
            if (item.getGoodsNum() > 0) {
                builder.append(String.format("商品%d次", item.getGoodsNum()));
            }
        } else if (mDataType == DataType.ACRTICE.getValue()) {
            if (item.getHotNum() > 0) {
                builder.append(String.format("热文%d次", item.getHotNum()));
            }
        }
        return builder.toString();
    }

    private String getDataTypeZh() {
        switch (mDataType) {
            case 0:
                return "";
            case 1:
                return "名片";
            case 2:
                return "动态";
            case 3:
                return "商品";
            case 4:
                return "热文";
        }
        return "";
    }
}
