package com.lyzb.jbx.fragment.circle;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.dialog.fragment.AlertDialogFragment;
import com.like.longshaolib.widget.AutoWidthTabLayout;
import com.like.utilslib.app.CommonUtil;
import com.like.utilslib.image.BitmapUtil;
import com.like.utilslib.image.LoadImageUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.activity.CricleDetailActivity;
import com.lyzb.jbx.activity.GoodToMeActivity;
import com.lyzb.jbx.dialog.CircleSetTabDialog;
import com.lyzb.jbx.fragment.base.BaseVideoFrgament;
import com.lyzb.jbx.fragment.me.card.CardCompanyFragment;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.model.common.WeiXinMinModel;
import com.lyzb.jbx.model.me.CircleDetModel;
import com.lyzb.jbx.model.me.CompanyCircleTabModel;
import com.lyzb.jbx.model.params.ApplyCircleBody;
import com.lyzb.jbx.model.params.SetCompanyTabBody;
import com.lyzb.jbx.mvp.presenter.me.CircleDetPresenter;
import com.lyzb.jbx.mvp.view.me.ICircleDetView;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.ShareActivity;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Util.App;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author wyx
 * @role 圈子详情, 先获取圈子详情，再判断是否为企业圈子
 * @time 2019 2019/3/13 15:05
 */

public class CircleDetailFragment extends BaseVideoFrgament<CircleDetPresenter>
        implements ICircleDetView {

    private static final String ID_KEY = "CIRCLE_ID";
    private static final String PARAMS_KEY = "PARAMS_KEY";

    Unbinder unbinder;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.img_me_cir_detail_bg)
    ImageView bgImg;
    @BindView(R.id.img_me_cir_detail_head)
    ImageView headImg;
    @BindView(R.id.tv_me_cir_detail_name)
    TextView nameText;
    @BindView(R.id.tv_me_cir_detail_number)
    TextView numberText;
    @BindView(R.id.img_me_cir_detail_status)
    ImageView statusImg;    //申请中 已加入  加入  管理

    @BindView(R.id.lin_me_cir_merber)
    FrameLayout merberLin;

    @BindView(R.id.img_me_cir_detail_own_head)
    ImageView ownHeadImg;
    @BindView(R.id.tv_me_cir_detail_own_name)
    TextView ownNameText;

    @BindView(R.id.img_me_cir_detail_one)
    ImageView oneImg;
    @BindView(R.id.img_me_cir_detail_two)
    ImageView twoImg;
    @BindView(R.id.img_me_cir_detail_three)
    ImageView threeImg;
    @BindView(R.id.tv_me_cir_detail_own_number)
    TextView ownNumberText;

    @BindView(R.id.tv_me_cir_detail_text)
    TextView textText;

    @BindView(R.id.appbar)
    AppBarLayout appbar;

    @BindView(R.id.iv_me_cir_detail_iscompany)
    ImageView mIvMeCirDetailIscompany;
    @BindView(R.id.tv_me_cir_detail_open)
    TextView mTvMeCirDetailOpen;
    @BindView(R.id.tab_circle_detail)
    AutoWidthTabLayout mTabCircleDetail;
    @BindView(R.id.fl_add_circle)
    FloatingActionButton mFlAddCircle;
    @BindView(R.id.ll_circle_dynamic)
    LinearLayout mDynamicLl;
    @BindView(R.id.tab_circle_layout)
    LinearLayout mTabLayout;
    @BindView(R.id.iv_circle_settab)
    ImageView mIvCircleSettab;

    private CircleDetModel mCircleDetModel = null;
    private boolean open;//简介是否展开
    private int mCurrentIndex = 0;//企业圈子时底部的选择item
    private List<BaseFragment> fragments;//企业圈子时底部的成员动态、企业动态、商城、官网
    private CircleDynamicFragment mCircleDynamicFragment;
    private CircleDynamicFragment mCompanyCircleDynamicFragment;
    /**
     * 这个id只能拿来查圈子详情！！！具体为什么不要问我！！！我也不知道cx 3.9.1
     */
    private String circleId = null;
    private String circle_key = "";//关键字
    private String companyId;
    /**
     * 这里跟列表的值不一样   注意！
     * 1加入，2管理员，3未加入,4申请中
     */
    private int isjoin;

    private List<CompanyCircleTabModel> mTabModels;
    private boolean clearFragments;

    public static CircleDetailFragment newIntance(String circleId) {
        return newIntance(circleId, "");
    }

    public static CircleDetailFragment newIntance(String circleId, String value) {
        CircleDetailFragment fragment = new CircleDetailFragment();
        Bundle args = new Bundle();
        args.putString(ID_KEY, circleId);
        args.putString(PARAMS_KEY, value);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            circleId = bundle.getString(ID_KEY);
            circle_key = bundle.getString(PARAMS_KEY);
        }
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, mView);
        mToolbar.setNavigationIcon(R.drawable.toolbar_back_circle);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getBaseActivity() instanceof GoodToMeActivity
                        || getBaseActivity() instanceof CricleDetailActivity) {
                    getBaseActivity().finish();
                } else {
                    pop();
                }
            }
        });
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) > appbar.getTotalScrollRange() / 1.2) {
                    mToolbar.setBackgroundColor(Color.parseColor("#ffffff"));
                } else {
                    mToolbar.setBackgroundColor(Color.parseColor("#00000000"));
                }
                //appbar.getTotalScrollRange() 拿到actionbar可滚动的最大距离
                //verticalOffset 当前的滚动距离
            }
        });

        mToolbar.inflateMenu(R.menu.share_union_menu_circle);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                final Intent shareIntent = new Intent(getContext(), ShareActivity.class);
                final WeiXinMinModel model = new WeiXinMinModel();
                model.setLowVersionUrl(Config.BASE_URL);                    //微信低版本 网络地址
                model.setTitle(mCircleDetModel.getGroupname());     //分享的标题
                model.setDescribe(" ");                                //描述
                model.setShareUrl("/pages/circleDetail/circleDetail?id=" + mCircleDetModel.getId()
                        + "&gsName=" + mCircleDetModel.getGroupname() + "&shareFromId=" + App.getInstance().userId
                        + "&originUserId=" + mCircleDetModel.getOwnerVo().getUserId());   //分享地址

                model.setImageUrl(BitmapUtil.bitmap2Bytes(BitmapFactory.decodeResource(getResources(), R.drawable.share_cricle_default)));

                shareIntent.putExtra(ShareActivity.SHARE_DATA, model);
                shareIntent.putExtra(ShareActivity.SHARE_SCOPE, 2);
                shareIntent.putExtra(ShareActivity.SHARE_SOURCE, ShareActivity.TYPE_SOURCE);
                startActivity(shareIntent);
                return false;
            }
        });

        merberLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (CommonUtil.converToT(isjoin, 0)) {
                    case 1:  //管理员
                    case 2:   //已加入
                        //进入全部成员列表
                        start(CircleMemberFragment.newIntance(mCircleDetModel));
                        break;
                    default:
                        showToast("您还未成功加入圈子");
                        break;
                }

            }
        });

        mTvMeCirDetailOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (open) {
                    mTvMeCirDetailOpen.setText("展开");
                    textText.setMaxLines(2);
                } else {
                    mTvMeCirDetailOpen.setText("收起");
                    textText.setMaxLines(Integer.MAX_VALUE);
                }
                open = !open;
            }
        });

        ownHeadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCircleDetModel == null) {
                    return;
                }
                start(CardFragment.newIntance(2, mCircleDetModel.getOwnerVo().getUserId()));
            }
        });
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        statusImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtil.converToT(isjoin, 0) == 2) {
                    //编辑圈子信息，管理
                    start(AddCircleFragment.newItance(3, companyId, mCircleDetModel));
                } else if (isjoin == 3) {
                    if (App.getInstance().isLogin()) {
                        //申请加入圈子
                        AlertDialogFragment.newIntance()
                                .setContent("是否加入该圈子？")
                                .setCancleBtn(null)
                                .setSureBtn(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mPresenter.applyCir(new ApplyCircleBody(mCircleDetModel.getId()));
                                    }
                                })
                                .show(getFragmentManager(), "ApplyCircleTag");
                    } else {
                        startActivity(new Intent(getContext(), LoginActivity.class));
                    }
                }
            }
        });
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_circle_detail;
    }

    @Override
    public void onData(CircleDetModel model) {
        if (model == null) {
            showToast("该圈子不存在");
            if (getBaseActivity() instanceof GoodToMeActivity
                    || getBaseActivity() instanceof CricleDetailActivity) {
                getBaseActivity().finish();
            } else {
                pop();
            }
            return;
        }
        mCircleDetModel = model;

        LoadImageUtil.loadImage(bgImg, model.getBackground());
        LoadImageUtil.loadRoundImage(headImg, model.getLogo(), 4);
        isjoin = CommonUtil.converToT(model.getIsJoin(), 0);
        if (isjoin == 1) {
            //已加入
            statusImg.setImageResource(R.mipmap.un_me_cir_join);
        } else if (isjoin == 2) {
            //管理员
            statusImg.setImageResource(R.mipmap.un_me_circle_mag);
        } else if (isjoin == 3) {
            //未加入
            statusImg.setImageResource(R.mipmap.un_me_circle_join);
        } else if (isjoin == 4) {
            //申请中
            statusImg.setImageResource(R.mipmap.un_me_cir_apply);
        } else {
            //未加入
            statusImg.setImageResource(R.mipmap.un_me_circle_join);
        }

        nameText.setText(model.getGroupname());
        textText.setMaxLines(Integer.MAX_VALUE);
        textText.setText(model.getDescription());
        if (textText.getLineCount() > 2) {
            textText.setMaxLines(2);
            mTvMeCirDetailOpen.setVisibility(View.VISIBLE);
        } else {
            mTvMeCirDetailOpen.setVisibility(View.GONE);
        }
        numberText.setText("成员" + model.getMenberNum() + "  丨  " + model.getDynamicNum() + "动态");
        //管理员
        if (isjoin == 2) {
            if (model.getNotApplyCount() > 0) {
                ownNumberText.setText(model.getNotApplyCount() + "");
            } else {
                ownNumberText.setVisibility(View.GONE);
            }
        } else {//非管理员  不显示多少个申请人数
            ownNumberText.setVisibility(View.GONE);
        }

        oneImg.setVisibility(View.GONE);
        twoImg.setVisibility(View.GONE);
        threeImg.setVisibility(View.GONE);
        if (model.getMenberList().size() > 0) {
            oneImg.setVisibility(View.VISIBLE);
            LoadImageUtil.loadRoundSizeImage(oneImg, model.getMenberList().get(0).getHeadimg(), 17);
        }
        if (model.getMenberList().size() > 1) {
            twoImg.setVisibility(View.VISIBLE);
            LoadImageUtil.loadRoundSizeImage(twoImg, model.getMenberList().get(1).getHeadimg(), 17);
        }
        if (model.getMenberList().size() > 2) {
            threeImg.setVisibility(View.VISIBLE);
            LoadImageUtil.loadRoundSizeImage(threeImg, model.getMenberList().get(2).getHeadimg(), 17);
        }

        LoadImageUtil.loadRoundImage(ownHeadImg, model.getOwnerVo().getHeadimg(), 4);
        ownNameText.setText(model.getOwnerVo().getUserName());

        companyId = mCircleDetModel.getOrgId();
        //不是企业圈子的情况
        if (TextUtils.isEmpty(companyId)) {
            mIvMeCirDetailIscompany.setVisibility(View.GONE);
            mDynamicLl.setVisibility(View.VISIBLE);
            //圈子动态,这里不能用circleId不然查不到数据，用实体类里面的id
            mCircleDynamicFragment = CircleDynamicFragment.newIntance(mCircleDetModel.getId(), 1, "");
            loadRootFragment(R.id.layout_circle_detail_bottom, mCircleDynamicFragment);
        } else {
            mPresenter.getCompanyCircleTab(model.getId());
        }

    }


    @Override
    public void onApply() {
        //申请之后重新拉数据，免得一天老是提bug
        mPresenter.getData(circleId, circle_key);
    }

    @Override
    public void onCompanyCircleTabData(List<CompanyCircleTabModel> list) {
        if (clearFragments) {
            fragments = null;
            mTabCircleDetail.getTabLayout().removeAllTabs();
            mCurrentIndex = 0;
            clearFragments = false;
        }
        initDynamic(list);
    }

    @Override
    public void onSavaCompanyCircleTabSuccess() {
        //修改栏目后重新拉数据，且清空当前的tab、fragmnet
        clearFragments = true;
        mPresenter.getData(circleId, circle_key);
    }

    @Override
    public void onFail(String msg) {

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getData(circleId, circle_key);
            }
        }, 500);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.iv_circle_settab, R.id.fl_add_circle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //企业tab设置
            case R.id.iv_circle_settab:
                CircleSetTabDialog.newIntance(mTabModels).setOnSureClickListener(new CircleSetTabDialog.OnSureClickListener() {
                    @Override
                    public void onSureClick(List<CompanyCircleTabModel> list) {
                        SetCompanyTabBody body = new SetCompanyTabBody();
                        body.setGroupId(mCircleDetModel.getId());
                        body.setType(2);
                        body.setGsCardFunctionSetList(list);
                        mPresenter.saveCompanyCircleTab(body);
                    }
                }).show(getFragmentManager(), "CircleDetailsFragment");
                break;
            //点击发布按钮(在动态页面执行具体逻辑)
            case R.id.fl_add_circle:
                //加入圈子才能发动态，1加入2管理员
                if (isjoin == 1 || isjoin == 2) {
                    //又要分是企业动态和成员动态，
                    if (mTabModels != null) {
                        if (mTabModels.get(mCurrentIndex).getFunCode().equals("member_topic") && mCircleDynamicFragment != null) {
                            mCircleDynamicFragment.onAddDynamic();
                        } else {
                            mCompanyCircleDynamicFragment.onAddDynamic();
                        }
                    } else {
                        mCircleDynamicFragment.onAddDynamic();
                    }
                } else {
                    showToast("加入圈子才可发布动态");
                }
                break;
            default:
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void chanageFragment(int postion) {
        if (postion >= fragments.size() || mCurrentIndex >= fragments.size()) {
            return;
        }
        //为了发布动态按钮能一直悬浮在页面上，所以把它放在了圈子详情，而不是动态页面，所以就要判断下显示隐藏
        //以前只需要判断下下标就行了，现在多了一堆
        if (mTabModels != null) {
            if (mTabModels.get(postion).getFunCode().equals("member_topic") || mTabModels.get(postion).getFunCode().equals("com_topic")) {
                mFlAddCircle.setVisibility(View.VISIBLE);
            } else {
                mFlAddCircle.setVisibility(View.GONE);
            }
        } else {
            mFlAddCircle.setVisibility(View.VISIBLE);
        }
        showHideFragment(fragments.get(postion), fragments.get(mCurrentIndex));
        mCurrentIndex = postion;
    }


    /**
     * 企业圈子
     */
    private void initDynamic(List<CompanyCircleTabModel> list) {
        if (fragments != null) {
            return;
        }
        if (isjoin == 2) {
            mIvCircleSettab.setVisibility(View.VISIBLE);
        } else {
            mIvCircleSettab.setVisibility(View.GONE);
        }
        mTabModels = list;
        mIvMeCirDetailIscompany.setVisibility(View.VISIBLE);
        mTabLayout.setVisibility(View.VISIBLE);
        //企业圈子、多了官网和商城
        fragments = new ArrayList<>();
        //企业圈子的tab，依次是成员动态、企业动态、官网、商城
        for (CompanyCircleTabModel b : list) {
            if (b.getStatus() == 0 && !TextUtils.isEmpty(b.getId())) {
                continue;
            }
            switch (b.getFunCode()) {
                case "member_topic":
                    //成员动态,这里不能用circleId不然查不到数据，用实体类里面的id
                    mCircleDynamicFragment = CircleDynamicFragment.newIntance(mCircleDetModel.getId(), 3, "");
                    fragments.add(mCircleDynamicFragment);
                    break;
                case "com_topic":
                    //企业动态,这里不能用circleId不然查不到数据，用实体类里面的id
                    //圈主id
                    String ownerId = mCircleDetModel.getOwnerVo().getUserId();
                    mCompanyCircleDynamicFragment = CircleDynamicFragment.newIntance(mCircleDetModel.getId(), 2, ownerId);
                    fragments.add(mCompanyCircleDynamicFragment);
                    break;
                case "com_website":
                    CardCompanyFragment fragment = new CardCompanyFragment();
                    fragment.onQuestCompany(companyId,true);
                    fragments.add(fragment);
                    break;
                case "com_shop":
                    fragments.add(CircleMallFragment.newIntance(companyId));
                    break;
                default:
            }
            mTabCircleDetail.addTab(b.getFunName());
        }
        loadMultipleRootFragment(R.id.layout_circle_detail_bottom, mCurrentIndex, fragments.toArray(new ISupportFragment[fragments.size()]));
        chanageFragment(mCurrentIndex);
        mTabCircleDetail.getTabLayout().getTabAt(mCurrentIndex).select();
        mTabCircleDetail.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                chanageFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}
