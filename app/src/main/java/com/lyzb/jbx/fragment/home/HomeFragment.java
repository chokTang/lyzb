package com.lyzb.jbx.fragment.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.base.inter.IPermissonResultListener;
import com.like.longshaolib.widget.AutoWidthTabLayout;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.home.HomeFragmentAdapter;
import com.lyzb.jbx.fragment.home.first.HomeCardFragment;
import com.lyzb.jbx.fragment.home.first.HomeCircleFragment;
import com.lyzb.jbx.fragment.home.first.HomeFollowFrgament;
import com.lyzb.jbx.fragment.home.first.HomeVideoFragment;
import com.lyzb.jbx.fragment.home.first.RecommendFragment;
import com.lyzb.jbx.fragment.home.search.HomeSearchFragment;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.model.account.FunctionModel;
import com.lyzb.jbx.model.follow.FollowHomeModel;
import com.lyzb.jbx.mvp.presenter.home.HomePresenter;
import com.lyzb.jbx.mvp.view.home.IHomeView;
import com.lyzb.jbx.util.AppPreference;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Util.App;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements IHomeView,
        View.OnClickListener, AMapLocationListener {

    private TextView tv_search;
    private TextView tv_location;
    private ImageView img_info_close;
    private LinearLayout layout_info;
    private AutoWidthTabLayout tab_home_layout;
    private ViewPager pager_home;
    private TextView tv_go_perfect;

    private HomeFragmentAdapter fragmentAdapter;
    private List<BaseFragment> mFragmentList = null;
    private int mCurrentIndex = 1;

    //定位
    private AMapLocationClient mLocationClient = null;

    //用户身份资料是否完善信息和推荐下配置的权限
    public List<FunctionModel> entranceList = null;
    private boolean isUserClose = false;//是否用户手动关闭

    //获取父级Fragment
    private MainFragment parentFragment;

    @Override
    public Object getResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentFragment = (MainFragment) getParentFragment();
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        tv_search = findViewById(R.id.tv_search);
        tab_home_layout = findViewById(R.id.tab_home_layout);
        pager_home = findViewById(R.id.pager_home);
        tv_location = findViewById(R.id.tv_location);
        img_info_close = findViewById(R.id.img_info_close);
        layout_info = findViewById(R.id.layout_info);
        tv_go_perfect = findViewById(R.id.tv_go_perfect);

        tv_search.setOnClickListener(this);
        tv_location.setOnClickListener(this);
        img_info_close.setOnClickListener(this);
        tv_go_perfect.setOnClickListener(this);

        tab_home_layout.addTab("关注");
        tab_home_layout.addTab("推荐");
        tab_home_layout.addTab("名片");
        tab_home_layout.addTab("视频");
        tab_home_layout.addTab("圈子");
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new HomeFollowFrgament());
        mFragmentList.add(new RecommendFragment());
        mFragmentList.add(new HomeCardFragment());
        mFragmentList.add(new HomeVideoFragment());
        mFragmentList.add(new HomeCircleFragment());
        fragmentAdapter = new HomeFragmentAdapter(getChildFragmentManager(), mFragmentList);

        pager_home.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                tab_home_layout.getTabLayout().getTabAt(position).select();
                if (App.getInstance().isLogin()) {
                    mPresenter.getMyFollowList();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        //设置关联和加载fragment
        pager_home.setAdapter(fragmentAdapter);
        tab_home_layout.setupWithViewPager(pager_home);

        pager_home.setCurrentItem(mCurrentIndex);
        tab_home_layout.getTabLayout().getTabAt(mCurrentIndex).select();

        //启动定位
        onLoctaion();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //搜索页面
            case R.id.tv_search:
                childStart(new HomeSearchFragment());
                break;
            //登陆
            case R.id.tv_location:
                break;
            //点击关闭完善资料
            case R.id.img_info_close:
                //如果没有登陆的情况下，用户点击了关闭按钮
                if (!App.getInstance().isLogin()) {
                    isUserClose = true;
                }
                layout_info.setVisibility(View.GONE);
                break;
            //完善资料
            case R.id.tv_go_perfect:
                if (App.getInstance().isLogin()) {
                    childStart(CardFragment.newIntance(1));
                } else {
                    startActivity(new Intent(getBaseActivity(), LoginActivity.class));
                }
                break;
            default:
                break;
        }
    }

    private void onLoctaion() {
//        mLocationClient = new AMapLocationClient(getContext());
//        AMapLocationClientOption option = new AMapLocationClientOption();
//        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//采用高精度定位
//        option.setOnceLocationLatest(true);//获取最近3s内精度最高的一次定位结果：
//        option.setWifiActiveScan(true);
//        mLocationClient.setLocationOption(option);
//        mLocationClient.setLocationListener(this);

        setPerms();
    }

    private void setPerms() {
        onRequestPermisson(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS}, new IPermissonResultListener() {
            @Override
            public void onSuccess() {
                mPresenter.getRecommentFunctionList();//获取功能入口和是否完善信息接口

                RecommendFragment fragment = findChildFragment(RecommendFragment.class);
                if (fragment != null) {
                    fragment.onAgainRequest();
                }
//                mLocationClient.startLocation();
            }

            @Override
            public void onFail(List<String> fail) {
                tv_location.setText("定位失败");
                showToast("权限被拒绝");

                mPresenter.getRecommentFunctionList();//获取功能入口和是否完善信息接口
                RecommendFragment fragment = findChildFragment(RecommendFragment.class);
                if (fragment != null) {
                    fragment.onAgainRequest();
                }
            }
        });
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                tv_location.setText(aMapLocation.getProvince());
                AppPreference.getIntance().setMeMapLatitude((float) aMapLocation.getLatitude());
                AppPreference.getIntance().setMeMapLongitude((float) aMapLocation.getLongitude());
                AppPreference.getIntance().setMeAddress(aMapLocation.getAddress());
            } else {
                tv_location.setText("定位中...");
            }
        } else {
            tv_location.setText("定位中...");
        }
    }

    @Override
    public void onFunctionResult(List<FunctionModel> list, int hasUserInfo) {
        isUserClose = false;//表示用户登陆了
        entranceList = list;
        layout_info.setVisibility(hasUserInfo == 1 ? View.GONE : View.VISIBLE);
        RecommendFragment recommendFragment = findChildFragment(RecommendFragment.class);
        if (recommendFragment != null) {
            recommendFragment.onNoticeBannerList(list);
        }
    }

    @Override
    public void onFollowListReuslt(FollowHomeModel model) {
        if (model.getFavourCount() > 0) {
            tab_home_layout.showMessage(0, true);
        } else {
            tab_home_layout.showMessage(0, false);
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mPresenter.getRecommentFunctionList();//获取功能入口和是否完善信息接口
        if (App.getInstance().isLogin()) {
            mPresenter.getMyFollowList();
        } else {
            if (isUserClose) {
                layout_info.setVisibility(View.GONE);
            } else {
                layout_info.setVisibility(View.VISIBLE);
            }
            tab_home_layout.showMessage(0, false);
        }
        if (parentFragment!=null){
            parentFragment.isInviseble =false;
        }
    }

    @Override
    public boolean isDelayedData() {
        return false;
    }

    public void showRecommendFragment() {
        pager_home.setCurrentItem(1);
    }

    public LinearLayout getFollowLayout() {
        return layout_info;
    }

    public void isShowRefresh(boolean isShow) {
        if (parentFragment != null) {
            parentFragment.isHomeRefresh(isShow);
        }
    }

    /**
     * 刷新到顶部
     */
    public void sroclloToTop() {
        switch (tab_home_layout.getTabLayout().getSelectedTabPosition()) {
            case 0:
                HomeFollowFrgament frgament0 = findChildFragment(HomeFollowFrgament.class);
                if (frgament0 != null) {
                    frgament0.scrollToTop();
                }
                break;
            //推荐
            case 1:
                RecommendFragment fragment1 = findChildFragment(RecommendFragment.class);
                if (fragment1 != null) {
                    fragment1.scrollToTop();
                }
                break;
            case 2:
                HomeCardFragment fragment2 = findChildFragment(HomeCardFragment.class);
                if (fragment2 != null) {
                    fragment2.scrollToTop();
                }
                break;
            case 3:
                HomeVideoFragment fragment3 = findChildFragment(HomeVideoFragment.class);
                if (fragment3 != null) {
                    fragment3.scrollToTop();
                }
                break;
            case 4:
                HomeCircleFragment fragment4 = findChildFragment(HomeCircleFragment.class);
                if (fragment4 != null) {
                    fragment4.scrollToTop();
                }
                break;
        }
    }
}
