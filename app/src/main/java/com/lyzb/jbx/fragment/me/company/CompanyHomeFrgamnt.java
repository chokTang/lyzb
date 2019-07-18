package com.lyzb.jbx.fragment.me.company;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.base.BaseToolbarFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.CompanyHomeAdapter;
import com.lyzb.jbx.fragment.circle.CircleDetailFragment;
import com.lyzb.jbx.fragment.me.customerManage.CustomerManageCompanyFragment;
import com.lyzb.jbx.fragment.statistics.StatisticsHomeFragment;
import com.lyzb.jbx.model.cenum.CompanyItemType;
import com.lyzb.jbx.model.me.CompanyDetailModel;
import com.lyzb.jbx.model.me.CompanyHomeItemModel;
import com.lyzb.jbx.mvp.presenter.me.CompanyHomePresenter;
import com.lyzb.jbx.mvp.view.me.ICompanyHomeView;
import com.szy.yishopcustomer.Util.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 企业主页
 *
 * @author shidengzhong
 */
@Deprecated
public class CompanyHomeFrgamnt extends BaseToolbarFragment<CompanyHomePresenter> implements ICompanyHomeView {

    private static final String PARAMS_ID = "mOrganId";
    private String mCompanyId = "";
    private int type = 1;

    private ImageView img_company_header;
    private TextView tv_company_name;
    private TextView tv_company_create;
    private TextView tv_company_type;
    private RecyclerView recycle_company;

    private String groupId = "";
    private String companyName = "";
    private CompanyHomeAdapter homeAdapter;

    private int accountType = 0;
    private String companyType = "";


    public static OrganDetailFragment newIntance(String companyId) {
        OrganDetailFragment frgamnt = OrganDetailFragment.Companion.newIntance(companyId);
        Bundle args = new Bundle();
        args.putString(PARAMS_ID, companyId);
        frgamnt.setArguments(args);
        return frgamnt;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mCompanyId = args.getString(PARAMS_ID);
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        onBack();
        setToolbarTitle("企业信息");

//        img_company_header = findViewById(R.id.img_company_header);
//        tv_company_name = findViewById(R.id.tv_company_name);
//        tv_company_create = findViewById(R.id.tv_company_create);
//        tv_company_type = findViewById(R.id.tv_company_type);
//        recycle_company = findViewById(R.id.recycle_company);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        homeAdapter = new CompanyHomeAdapter(getContext(), null);
        homeAdapter.setGridLayoutManager(recycle_company, 4);
        recycle_company.setAdapter(homeAdapter);

        mPresenter.getCompanyDetail(mCompanyId);

        recycle_company.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
//                CompanyHomeItemModel itemModel = homeAdapter.getPositionModel(position);
                CompanyHomeItemModel itemModel = null;
                switch (itemModel.getItemType()) {
                    //企业圈子
                    case CompanyCircle:
                        if (TextUtils.isEmpty(groupId)) {
                            showToast("该企业暂未关联圈子");
                        } else {
                            start(CircleDetailFragment.newIntance(groupId));
                        }
                        break;
                    //企业官网
                    case CompanyWeb:
                        start(CompanyWebFragment.Companion.newInstance(mCompanyId, groupId, companyName, -1));
                        break;
                    //企业信息
                    case CompanyInfo:
                        start(CompanyBaseInfoFragment.Companion.newIntance(mCompanyId, companyType));
                        break;
                    //企业账户
                    case CompanyAccount:
                        start(CompanyAccountsFragment.newIntance(mCompanyId));
                        break;
                    //数据统计
                    case CompanyStatistics:
                        start(StatisticsHomeFragment.newIntance(mCompanyId));
                        break;
                    //企业成员
                    case CompanyMember:
                        start(CompanyMembersFragment.newIntance(mCompanyId, accountType));
                        break;
                    //申请列表
                    case CompanyApply:
                        start(CompanyApplyListFragment.newIntance(mCompanyId));
                        break;
                    //企业商城
                    case CompanyShop:
                        start(CompanyMallFragment.Companion.newInstance(type, mCompanyId,""));
                        break;
                    //申请运营
                    case CompanyApplyOperate:
                        start(ApplyIdentityFragment.newIntance(2, mCompanyId));
                        break;
                    //申请经销
                    case CompanyApplyDistribution:
                        start(ApplyIdentityFragment.newIntance(1, mCompanyId));
                        break;
                    //客户管理
                    case CustomerManger:
                        start(CustomerManageCompanyFragment.newIntance(mCompanyId));
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @Override
    public Object getResId() {
        return R.layout.fragment_organ_detail;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (homeAdapter != null) {
            mPresenter.getCompanyDetail(mCompanyId);
//            mPresenter.getCompanyApplyNumber(mOrganId);
        }
    }

    /**
     * 获取主页数据
     *
     * @param isOrdinary  是否是普通企业 true:普通企业 false:团购企业
     * @param isManager   是否是管理员 true：是管理员 false:不是管理员
     * @param applyNumber 申请列表的消息数量
     * @return
     */
    private List<CompanyHomeItemModel> getHomeList(boolean isOrdinary, boolean isManager, int applyNumber) {
        List<CompanyHomeItemModel> list = new ArrayList<>();
        list.add(new CompanyHomeItemModel(CompanyItemType.CompanyCircle));
        list.add(new CompanyHomeItemModel(CompanyItemType.CompanyWeb));
        list.add(new CompanyHomeItemModel(CompanyItemType.CompanyInfo));
        if (!isOrdinary && isManager) {
            list.add(new CompanyHomeItemModel(CompanyItemType.CompanyAccount));
            list.add(new CompanyHomeItemModel(CompanyItemType.CompanyStatistics));
            list.add(new CompanyHomeItemModel(CompanyItemType.CustomerManger));
        }
        list.add(new CompanyHomeItemModel(CompanyItemType.CompanyMember));
        if (isManager) {
            list.add(new CompanyHomeItemModel(CompanyItemType.CompanyApply, applyNumber));
            list.add(new CompanyHomeItemModel(CompanyItemType.CompanyShop));
            if (isOrdinary) {
                list.add(new CompanyHomeItemModel(CompanyItemType.CompanyApplyDistribution));
            }
        }
        return list;
    }

    /**
     * 获取企业申请成员数量
     *
     * @param number
     */
    @Override
    public void companyApplyNumber(int number) {
        for (int i = 0; i < homeAdapter.getItemCount(); i++) {
//            if (homeAdapter.getPositionModel(i).getItemType() == CompanyItemType.CompanyApply) {
//                CompanyHomeItemModel itemModel = homeAdapter.getPositionModel(i);
//                itemModel.setMessageNumber(number);
//                homeAdapter.change(i, itemModel);
//                break;
//            }
        }
    }

    @Override
    public void getCompanyDetail(CompanyDetailModel detailModel) {
        if (detailModel == null) return;
        Glide.with(this).load(detailModel.getData().getDistributorLogo()).apply(GlideUtil.RadioOptionsCanpany(4)).into(img_company_header);
//        LoadImageUtil.loadRoundImage(img_company_header, detailModel.getData().getDistributorLogo(), 4,R.mipmap.icon_company_defult);
        tv_company_name.setText(detailModel.getData().getCompanyName());
        companyName = detailModel.getData().getCompanyName();
        tv_company_create.setText(detailModel.getData().getCreatorName());
        tv_company_type.setText(detailModel.getData().getIndustryName() + "·" + detailModel.getData().getMembersNum() + "成员");
        groupId = detailModel.getData().getGroupId();
        //accountType  用户账号类型（1.团购企业账号 0.普通账号）
        accountType = (detailModel.getData().getAccountType() == 0 && detailModel.getData().getIsMy() != 1) ? 1 : 0;
//        if (detailModel.getData().getAccountType() == 1) {//团购企业不可以显示退出企业，或者解散企业
//            accountType = 2;
//        }
        companyType = detailModel.getData().getDisType() + "";
        //企业类型（1.普通企业 2.团购企业）
        switch (companyType) {
            case "1"://普通企业
//                homeAdapter.update(getHomeList(true, detailModel.getData().getIsMy() == 1, 0));
                break;
            case "2"://团购企业
//                homeAdapter.update(getHomeList(false, detailModel.getData().getIsMy() == 1, 0));
                break;
        }
        mPresenter.getCompanyApplyNumber(mCompanyId);
    }


}
