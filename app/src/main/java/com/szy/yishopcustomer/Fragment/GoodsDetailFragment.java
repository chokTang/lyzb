package com.szy.yishopcustomer.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.TextView;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liuzhifeng on 2017/2/21.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsDetailFragment extends LazyFragment {
    //    @BindView(R.id.v_tab_cursor)
//    View v_tab_cursor;
    @BindView(R.id.tv_goods_detail)
    TextView tv_goods_detail;
    @BindView(R.id.tv_goods_config)
    TextView tv_goods_config;

    private List<TextView> tabTextList;
    private float fromX;
    private Fragment nowFragment;
    private int nowIndex;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private List<Fragment> fragmentList = new ArrayList<>();
    private GoodsAttrDetailFragment goodsConfigFragment;
    private GoodsImageDetailFragment goodsInfoWebFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_goods_detail_tab;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setDetailData();
        initData();
        if (goodsInfoWebFragment.getArguments() == null) {
            goodsInfoWebFragment.setArguments(getActivity().getIntent().getExtras());
        } else {
            goodsInfoWebFragment.getArguments().putAll(getActivity().getIntent().getExtras());
        }
        nowFragment = goodsInfoWebFragment;
        fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_content, nowFragment)
                .commitAllowingStateLoss();
        tv_goods_detail.setOnClickListener(this);
        tv_goods_config.setOnClickListener(this);
        tabTextList = new ArrayList<>();
        tabTextList.add(tv_goods_detail);
        tabTextList.add(tv_goods_config);


        return view;
    }

    @Override
    public void onClick(View v) {
        if(Utils.isDoubleClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.tv_goods_detail:
                //商品详情tab
                nowIndex = 0;
                switchFragment(nowFragment, goodsInfoWebFragment);
                nowFragment = goodsInfoWebFragment;
                scrollCursor();
                break;

            case R.id.tv_goods_config:
                //规格参数tab
                nowIndex = 1;
                switchFragment(nowFragment, goodsConfigFragment);
                nowFragment = goodsConfigFragment;
                scrollCursor();
                break;
            default:
                break;
        }
    }

    /**
     * 切换Fragment
     * <p>(hide、show、add)
     *
     * @param fromFragment
     * @param toFragment
     */
    private void switchFragment(Fragment fromFragment, Fragment toFragment) {
        if (nowFragment != toFragment) {
            fragmentTransaction = fragmentManager.beginTransaction();
            if (!toFragment.isAdded()) {    // 先判断是否被add过
                fragmentTransaction.hide(fromFragment).add(R.id.fl_content, toFragment)
                        .commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到activity中
            } else {
                fragmentTransaction.hide(fromFragment).show(toFragment).commitAllowingStateLoss()
                ; // 隐藏当前的fragment，显示下一个
            }
        }
    }

    private void initData() {
        fragmentList = new ArrayList<>();
    }

    public void setDetailData() {
        goodsConfigFragment = new GoodsAttrDetailFragment();
        goodsInfoWebFragment = new GoodsImageDetailFragment();
        fragmentList.add(goodsConfigFragment);
        fragmentList.add(goodsInfoWebFragment);
        nowFragment = goodsInfoWebFragment;
        fragmentManager = getChildFragmentManager();
    }

    /**
     * 滑动游标
     */
    private void scrollCursor() {
//        TranslateAnimation anim = new TranslateAnimation(fromX, nowIndex * v_tab_cursor
// .getWidth(), 0, 0);
//        anim.setFillAfter(true);//设置动画结束时停在动画结束的位置
//        anim.setDuration(50);
//        //保存动画结束时游标的位置,作为下次滑动的起点
//        fromX = nowIndex * v_tab_cursor.getWidth();
//        v_tab_cursor.startAnimation(anim);

        //设置Tab切换颜色
        for (int i = 0; i < tabTextList.size(); i++) {
            tabTextList.get(i).setTextColor(i == nowIndex ? getResources().getColor(R.color
                    .colorPrimary) : getResources().getColor(R.color.colorOne));
        }
    }
}
