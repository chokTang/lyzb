package com.lyzb.jbx.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.like.longshaolib.base.BaseActivity;
import com.like.longshaolib.base.BaseFragment;
import com.lyzb.jbx.fragment.campaign.CampaignDetailFragment;
import com.lyzb.jbx.fragment.circle.ApplyListFragment;
import com.lyzb.jbx.fragment.circle.CircleDetailFragment;
import com.lyzb.jbx.fragment.dynamic.DynamicDetailFragment;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.fragment.me.company.CompanyHomeFrgamnt;
import com.lyzb.jbx.fragment.me.company.OrganDetailFragment;

public class GoodToMeActivity extends BaseActivity {

    private final static int CARD = 0x565;//智能名片
    private final static int CIRCLE_APPLY = 0x596;//圈子申请列表
    private final static int CIRCLE_DETAIL = 0x569;//圈子详情
    private final static int DYNAMIC_DETAIL = 0x534;//动态详情
    private final static int COMPANY_APPLY_LIST = 0x5366;//企业圈子申请列表
    private final static int COMPANY_INFO = 0x53367;//企业信息
    private final static int CAMPAIGN_INFO = 0x562;//活动详情

    private static final String PAGE_TYPE = "PAGETYPE";
    private int mPageType = CARD;
    private static final String PAGE_PARAMS = "PAGE_PARAMS";
    private String params = "";

    public static void startIntoCard(Context context, String userId) {
        Intent intent = new Intent(context, GoodToMeActivity.class);
        intent.putExtra(PAGE_TYPE, CARD);
        intent.putExtra(PAGE_PARAMS, userId);
        context.startActivity(intent);
    }

    public static void startCompanyApplyList(Context context, String companyId) {
        Intent intent = new Intent(context, GoodToMeActivity.class);
        intent.putExtra(PAGE_TYPE, COMPANY_APPLY_LIST);
        intent.putExtra(PAGE_PARAMS, companyId);
        context.startActivity(intent);
    }

    public static void startCampaignInfo(Context context, String campaignId) {
        Intent intent = new Intent(context, GoodToMeActivity.class);
        intent.putExtra(PAGE_TYPE, CAMPAIGN_INFO);
        intent.putExtra(PAGE_PARAMS, campaignId);
        context.startActivity(intent);
    }

    public static void startCompanyInfo(Context context, String companyId) {
        Intent intent = new Intent(context, GoodToMeActivity.class);
        intent.putExtra(PAGE_TYPE, COMPANY_INFO);
        intent.putExtra(PAGE_PARAMS, companyId);
        context.startActivity(intent);
    }

    public static void startIntoCircleApply(Context context, String groupId) {
        Intent intent = new Intent(context, GoodToMeActivity.class);
        intent.putExtra(PAGE_TYPE, CIRCLE_APPLY);
        intent.putExtra(PAGE_PARAMS, groupId);
        context.startActivity(intent);
    }

    public static void startIntoCircleDetail(Context context, String circleId) {
        Intent intent = new Intent(context, GoodToMeActivity.class);
        intent.putExtra(PAGE_TYPE, CIRCLE_DETAIL);
        intent.putExtra(PAGE_PARAMS, circleId);
        context.startActivity(intent);
    }

    public static void startIntoDynamicDetail(Context context, String dynamicId) {
        Intent intent = new Intent(context, GoodToMeActivity.class);
        intent.putExtra(PAGE_TYPE, DYNAMIC_DETAIL);
        intent.putExtra(PAGE_PARAMS, dynamicId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getIntent().getExtras();
        if (args != null) {
            mPageType = args.getInt(PAGE_TYPE, CARD);
            params = args.getString(PAGE_PARAMS);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public BaseFragment setRootFragment() {
        switch (mPageType) {
            case CARD:
                if (TextUtils.isEmpty(params)) {
                    return CardFragment.newIntance(1, params);
                } else {
                    return CardFragment.newIntance(2, params);
                }
            case CIRCLE_APPLY:
                return ApplyListFragment.newTance(params);
            case CIRCLE_DETAIL:
                return CircleDetailFragment.newIntance(params);
            case DYNAMIC_DETAIL:
                return DynamicDetailFragment.Companion.newIntance(params);
            case COMPANY_APPLY_LIST:
                return ApplyListFragment.newTance(params);
            case COMPANY_INFO:
                return OrganDetailFragment.Companion.newIntance(params);
            case CAMPAIGN_INFO:
                return CampaignDetailFragment.newIntance(params);
        }
        return null;
    }
}
