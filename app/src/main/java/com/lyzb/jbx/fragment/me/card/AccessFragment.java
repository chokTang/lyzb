package com.lyzb.jbx.fragment.me.card;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.dialog.fragment.AlertDialogFragment;
import com.like.longshaolib.widget.AutoWidthTabLayout;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.home.HomeFragmentAdapter;
import com.lyzb.jbx.api.UrlConfig;
import com.lyzb.jbx.fragment.me.access.AccessDayFragment;
import com.lyzb.jbx.model.cenum.DayEnum;
import com.lyzb.jbx.util.AppPreference;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Constant.Key;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wyx
 * @role 访问我的人(客户追踪)
 * @time 2019 2019/3/8 15:23
 */
public class AccessFragment extends BaseFragment implements View.OnClickListener {

    private HomeFragmentAdapter mFragmentAdapter;
    private List<BaseFragment> fragmentList = new ArrayList<>();

    private AutoWidthTabLayout tab_access_day;
    private ViewPager pager_access;
    private TextView btn_go_vip;
    private LinearLayout layout_no_vip;
    private ImageView img_back;
    private ImageView img_right;

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        tab_access_day = (AutoWidthTabLayout) findViewById(R.id.tab_access_day);
        pager_access = (ViewPager) findViewById(R.id.pager_access);
        btn_go_vip = (TextView) findViewById(R.id.btn_go_vip);
        layout_no_vip = (LinearLayout) findViewById(R.id.layout_no_vip);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_right = (ImageView) findViewById(R.id.img_right);

        btn_go_vip.setOnClickListener(this);
        img_back.setOnClickListener(this);
        img_right.setOnClickListener(this);

        setIsVip();
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        tab_access_day.addTab(DayEnum.DAY_ALL.getValue());
        tab_access_day.addTab(DayEnum.DAY_THIRTY.getValue());
        tab_access_day.addTab(DayEnum.DAY_SEVEN.getValue());
        tab_access_day.addTab(DayEnum.DAY_ZERO.getValue());
        fragmentList.add(AccessDayFragment.newIntance("", "", DayEnum.DAY_ALL.getValue()));
        fragmentList.add(AccessDayFragment.newIntance("", "", DayEnum.DAY_THIRTY.getValue()));
        fragmentList.add(AccessDayFragment.newIntance("", "", DayEnum.DAY_SEVEN.getValue()));
        fragmentList.add(AccessDayFragment.newIntance("", "", DayEnum.DAY_ZERO.getValue()));

        mFragmentAdapter = new HomeFragmentAdapter(getChildFragmentManager(), fragmentList);
        pager_access.setAdapter(mFragmentAdapter);
        tab_access_day.setupWithViewPager(pager_access);
        pager_access.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tab_access_day.getTabLayout().getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_card_access;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //开通VIP
            case R.id.btn_go_vip:
                Intent tgIntent = new Intent(getContext(), ProjectH5Activity.class);
                tgIntent.putExtra(Key.KEY_URL.getValue(), UrlConfig.H5_OPEN_VIP);
                startActivity(tgIntent);
                break;
            //返回
            case R.id.img_back:
                pop();
                break;
            //点击问号
            case R.id.img_right:
                AlertDialogFragment.newIntance()
                        .setTitle("温馨提示")
                        .setContent("1、点击对应的数据可查看数据详情哦~\n2、访问次数为访问过你的名片" +
                                "、动态与商品的总次数。\n3、分析次数为你分享自己的名片、动态、商品、圈子的总次数。\n4、引流" +
                                "新用户为通过你的分享带来的新注册用户。\n5、商城交易为通过你的名片商城成交订单数，此处只记录" +
                                "完成过付款的订单，若有退款请在集宝箱商家中处理哦~")
                        .setTextGravity(Gravity.LEFT)
                        .setSureBtn("知道了", null)
                        .show(getFragmentManager(), "accessInter");
                break;
            default:
                break;
        }
    }

    /**
     * 设置是否是VIP
     */
    private void setIsVip() {
        boolean isVip = AppPreference.getIntance().getUserIsVip();
        if (isVip) {
            layout_no_vip.setVisibility(View.GONE);
        } else {
            layout_no_vip.setVisibility(View.VISIBLE);
        }
//        layout_no_vip.setVisibility(View.GONE);
    }
}
