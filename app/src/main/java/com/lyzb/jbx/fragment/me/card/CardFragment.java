package com.lyzb.jbx.fragment.me.card;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hyphenate.easeui.EaseConstant;
import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.widget.AutoWidthTabLayout;
import com.like.utilslib.app.AppUtil;
import com.like.utilslib.app.CommonUtil;
import com.like.utilslib.image.BitmapUtil;
import com.like.utilslib.image.LoadImageUtil;
import com.like.utilslib.json.GSONUtil;
import com.like.utilslib.other.LogUtil;
import com.like.utilslib.screen.DensityUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.activity.GoodToMeActivity;
import com.lyzb.jbx.adapter.me.card.CardShowAdapter;
import com.lyzb.jbx.dialog.ShareDialog;
import com.lyzb.jbx.dialog.TabShowHideDialog;
import com.lyzb.jbx.fragment.circle.CircleDetailFragment;
import com.lyzb.jbx.fragment.circle.CircleDynamicFragment;
import com.lyzb.jbx.fragment.me.FansFragment;
import com.lyzb.jbx.fragment.me.FocusFragment;
import com.lyzb.jbx.model.common.WeiXinMinModel;
import com.lyzb.jbx.model.me.CardModel;
import com.lyzb.jbx.model.me.CardTabHideBody;
import com.lyzb.jbx.model.me.CardTemplateModel;
import com.lyzb.jbx.model.me.DoFocusModel;
import com.lyzb.jbx.model.me.DoLikeModel;
import com.lyzb.jbx.model.me.TabShowHideBean;
import com.lyzb.jbx.mvp.presenter.me.card.CardPresenter;
import com.lyzb.jbx.mvp.view.me.ICardView;
import com.lyzb.jbx.util.AppPreference;
import com.lyzb.jbx.util.ImageUtil;
import com.lyzb.jbx.util.map.MapUtil;
import com.lyzb.jbx.webscoket.BaseClient;
import com.lyzb.jbx.widget.CusPopWindow;
import com.lyzb.jbx.widget.PhotoViewActivity;
import com.szy.yishopcustomer.Activity.im.ImCommonActivity;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.WeiXinUtils;
import com.szy.yishopcustomer.ViewModel.im.ImHeaderGoodsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.yokeyword.fragmentation.ISupportFragment;

import static com.lyzb.jbx.adapter.me.card.CardShowAdapter.TEMP_ID_1;
import static com.lyzb.jbx.adapter.me.card.CardShowAdapter.TEMP_ID_2;
import static com.lyzb.jbx.fragment.account.PerfectOneFragment.RESULT_CODE;
import static com.lyzb.jbx.fragment.me.card.CardTemplateDetailFragment.KEY_IS_SELECT;
import static com.lyzb.jbx.widget.PhotoViewActivity.BIG_IMAGE_POSITION;
import static com.lyzb.jbx.widget.PhotoViewActivity.BIG_IMAGE_URL;
import static com.lyzb.jbx.widget.PhotoViewActivity.BUNDLE;

/**
 * @author tyk
 * @role 我(TA)的名片
 * @time 2019 2019/3/4 14:35
 * CoordinatorLayout嵌套AppBarLayout  滑动时抖动的一个bug
 * https://stackoverflow.com/questions/31136740/scroll-doesnt-work-in-nestedscrollview-when-try-to-scroll-from-views-with-click
 */

public class CardFragment extends BaseFragment<CardPresenter> implements ICardView, View.OnClickListener {

    private final static String FROM_TYPE = "from_type";
    private final static String CARD_USER_ID = "card_user_id";
    private final static String CARD_ID = "card_id";
    private final static String POSITION = "tab_position";//跳转到我的名片 直接加载下面的tab

    public static final String TAB_1 = "personal";//个人
    public static final String TAB_2 = "topic";//动态
    public static final String TAB_3 = "shop";//商城
    public static final String TAB_4 = "news";//新闻
    public static final String TAB_5 = "website";//官网
    private final int CALL_MOBILE_PHONE_PERMISSION = 1163;
    private final int CALL_PHONE_PERMISSION = 1133;

    private int infoPosition = 0;//个人信息tab  位置
    //数据源
    public CardModel mCardModel = null;
    /**
     * 区分 来源 1:我的智能名片  2:TA的智能名片
     **/
    private int mFromType = 0;
    private String mUserId = null;          //别人名片的userId
    private String mCardId = null;            //别人名片的名片Id
    CusPopWindow popWindow;
    Timer timer;

    private LinearLayout layout_share_view,ll_company;
    private RecyclerView rv_card;
    private View view_zanwei;
    private boolean isUpdate = true;//是否是刷新名片
    private ImageView img_edt_tab;
    private AutoWidthTabLayout tab_un_card_title;
    private AppBarLayout appbar_card;
    private List<BaseFragment> fragments;
    private int mCurrentIndex = 0;

    private boolean isUpdateInfo = false, isShowAll = false;
    private CardShowAdapter adapter;
    private ImageView img_back, img_share,img_company_logo;
    LinearLayout ll_back,ll_web;
    private RelativeLayout titlebar;
    private LinearLayout ll_show_hide_all, ll_card_more, ll_mobile_phone, ll_phone, ll_wx, ll_mail, ll_shop_more_name, ll_shop_more_address;
    private TextView tv_title, tv_show_hide_all, tv_call_mobile_phone, edt_mobile_phone, edt_phone, tv_call_phone,
            edt_wx, tv_copy_wx, edt_mail, tv_copy_mail, edt_shop_name, tv_copy_shop_name, edt_shop_address, tv_copy_shop_address
            ,tv_company_name,tv_goto_circle;

    List<CardModel> list = new ArrayList<>();
    List<TabShowHideBean> tabdialoglist = new ArrayList<>();
    List<TabShowHideBean> mTablist = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mFromType = bundle.getInt(FROM_TYPE);
            mCurrentIndex = bundle.getInt(POSITION);
            mUserId = bundle.getString(CARD_USER_ID);
            mCardId = bundle.getString(CARD_ID);
            if (App.getInstance().isLogin()) {
                if (App.getInstance().userId.equals(mUserId)) {
                    mFromType = 1;
                }
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public static CardFragment newIntance(int fromType) {
        return newIntance(fromType, null);
    }

    public static CardFragment newIntance(int fromType, String userId) {
        CardFragment cardFragment = new CardFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FROM_TYPE, fromType);
        bundle.putString(CARD_USER_ID, userId);
        cardFragment.setArguments(bundle);
        return cardFragment;
    }

    /**
     * 名片查询 根据名片ID
     *
     * @param cardId
     * @return
     */
    public static CardFragment newIntanceByCardID(String cardId) {
        CardFragment cardFragment = new CardFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CARD_ID, cardId);
        bundle.putInt(FROM_TYPE, 2);//默认写2
        cardFragment.setArguments(bundle);
        return cardFragment;
    }

    /**
     * 自己的名片 userid 传空
     *
     * @param fromType
     * @param tabposition
     * @param userId
     * @return
     */
    public static CardFragment myIntance(int fromType, int tabposition, @Nullable String userId) {
        CardFragment cardFragment = new CardFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FROM_TYPE, fromType);
        bundle.putInt(POSITION, tabposition);
        if (!TextUtils.isEmpty(userId)) {
            bundle.putString(CARD_USER_ID, userId);
        }
        cardFragment.setArguments(bundle);
        return cardFragment;
    }


    @Override
    public Object getResId() {
        return R.layout.fragment_card;

    }


    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {

        titlebar = findViewById(R.id.title_bar);
        img_back = findViewById(R.id.img_back);
        ll_back = findViewById(R.id.ll_back);
        tv_title = findViewById(R.id.tv_title);
        img_share = findViewById(R.id.img_share);
        rv_card = findViewById(R.id.rv_card);
        view_zanwei = findViewById(R.id.view_zanwei);
        layout_share_view = findViewById(R.id.layout_share_view);
        ll_company = findViewById(R.id.ll_company);
        img_edt_tab = findViewById(R.id.img_edt_tab);
        tab_un_card_title = findViewById(R.id.tab_un_card_title);
        appbar_card = findViewById(R.id.appbar_card);
        ll_show_hide_all = findViewById(R.id.ll_show_hide_all);
        tv_show_hide_all = findViewById(R.id.tv_show_hide_all);
        ll_card_more = findViewById(R.id.ll_card_more);
        tv_call_mobile_phone = findViewById(R.id.tv_call_mobile_phone);
        edt_mobile_phone = findViewById(R.id.edt_mobile_phone);
        edt_phone = findViewById(R.id.edt_phone);
        tv_call_phone = findViewById(R.id.tv_call_phone);
        tv_copy_wx = findViewById(R.id.tv_copy_wx);
        tv_goto_circle = findViewById(R.id.tv_goto_circle);
        tv_company_name = findViewById(R.id.tv_company_name);
        img_company_logo = findViewById(R.id.img_company_logo);
        ll_web = findViewById(R.id.ll_web);
        edt_wx = findViewById(R.id.edt_wx);
        tv_copy_mail = findViewById(R.id.tv_copy_mail);
        edt_mail = findViewById(R.id.edt_mail);
        tv_copy_shop_name = findViewById(R.id.tv_copy_shop_name);
        edt_shop_name = findViewById(R.id.edt_shop_name);
        edt_shop_address = findViewById(R.id.edt_shop_address);
        tv_copy_shop_address = findViewById(R.id.tv_copy_shop_address);
        ll_mobile_phone = findViewById(R.id.ll_mobile_phone);
        ll_phone = findViewById(R.id.ll_phone);
        ll_wx = findViewById(R.id.ll_wx);
        ll_mail = findViewById(R.id.ll_mail);
        ll_shop_more_name = findViewById(R.id.ll_shop_more_name);
        ll_shop_more_address = findViewById(R.id.ll_shop_more_address);


        CommonUtil.textViewClickCopy(edt_wx, tv_copy_wx);
        CommonUtil.textViewClickCopy(edt_mail, tv_copy_mail);
        CommonUtil.textViewClickCopy(edt_shop_name, tv_copy_shop_name);


        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getBaseActivity() instanceof GoodToMeActivity) {
                    getBaseActivity().finish();
                } else {
                    pop();
                }
            }
        });

        adapter = new CardShowAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_card.setLayoutManager(linearLayoutManager);
        rv_card.setAdapter(adapter);

        img_edt_tab.setOnClickListener(this);
        img_share.setOnClickListener(this);
        ll_show_hide_all.setOnClickListener(this);
        tv_call_mobile_phone.setOnClickListener(this);
        tv_call_phone.setOnClickListener(this);
//        tv_copy_wx.setOnClickListener(this);
//        tv_copy_mail.setOnClickListener(this);
//        tv_copy_shop_name.setOnClickListener(this);
        tv_copy_shop_address.setOnClickListener(this);
        tv_goto_circle.setOnClickListener(this);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    //编辑名片信息
                    case R.id.img_card_info_edt:
                    case R.id.tv_card_edt:
                        if (mFromType == 1) {
                            startForResult(UserInfoFragment.newIntance(mCardModel), 1122);
                        }
                        break;
                    case R.id.img_change_template://更换模板
                    case R.id.tv_change_template://更换模板
                        CardModel bean = (CardModel) adapter.getData().get(0);
                        startForResult(CardTemplateDetailFragment.Companion.newInstance(bean.getTemplateId()), RESULT_CODE);
                        break;
                    case R.id.img_card_qrcode://点击二维码放大
                        List<String> list = new ArrayList<>();
                        list.add(mCardModel.getWxImg());
                        statPhotoViewActivity(0, list);
                        break;
                    //发送消息
                    case R.id.tv_card_send_msg:
                        if (mCardModel == null) {
                            return;
                        }

                        Intent intent = new Intent(getContext(), ImCommonActivity.class);
                        ImHeaderGoodsModel model = new ImHeaderGoodsModel();

                        model.setChatType(EaseConstant.CHATTYPE_SINGLE);
                        model.setShopImName("jbx" + mCardModel.getUserId());
                        model.setShopName(mCardModel.getGsName());
                        model.setShopHeadimg(mCardModel.getHeadimg());
                        model.setShopId("");

                        Bundle args = new Bundle();
                        args.putSerializable(ImCommonActivity.PARAMS_GOODS, model);
                        intent.putExtras(args);
                        startActivity(intent);
                        break;
                    //拨打电话
                    case R.id.tv_card_call_phone:
                        if (!TextUtils.isEmpty(mCardModel.getMobile())) {
                            AppUtil.openDial(getContext(), mCardModel.getMobile());
                        } else {
                            showToast("当前用户未设置电话号码");
                        }
                        break;
                    //关注
                    case R.id.tv_card_focus:
                        DoFocusModel model2 = new DoFocusModel();
                        if (mCardModel.getRelation() == 0) {//未关注
                            model2.setEnabled(1);
                            model2.setToUserId(mCardModel.getUserId() + "");
                            mPresenter.doFocus(true, model2);
                        } else {
                            model2.setEnabled(0);
                            model2.setToUserId(mCardModel.getUserId() + "");
                            mPresenter.doFocus(false, model2);
                        }
                        break;
                    case R.id.ll_record:
                        if (mFromType == 1) {//自己的名片
                            start(CardAccessRecordFragment.Companion.newInstance(App.getInstance().userId, mCardModel.getViewCount()));
                        } else {//别人的名片
                            start(CardAccessRecordFragment.Companion.newInstance(mUserId, mCardModel.getViewCount()));
                        }
                        break;
                    case R.id.rl_fans://粉丝
                        start(FansFragment.newIntance(mUserId));
                        break;
                    case R.id.rl_focus://关注
                        start(FocusFragment.newIntance(mUserId));
                        break;
                    case R.id.fl_avatar://点击头像查看大图
                        CardModel bean1 = (CardModel) adapter.getData().get(position);
                        List<String> urllist = new ArrayList<>();
                        for (int i = 0; i < 1; i++) {
                            urllist.add(bean1.getHeadimg());
                        }
                        ImageUtil.Companion.statPhotoViewActivity(getActivity(), position, urllist);
                        break;

                    case R.id.img_company_logo://点击头像查看大图
                        CardModel beanlogo = (CardModel) adapter.getData().get(position);
                        List<String> urllist1 = new ArrayList<>();
                        for (int i = 0; i < 1; i++) {
                            urllist1.add(beanlogo.getShopLogo());
                        }
                        ImageUtil.Companion.statPhotoViewActivity(getActivity(), position, urllist1);
                        break;
                    case R.id.tv_card_address:
                        start(AmapFragment.Companion.newIncetance(edt_shop_address.getText().toString(),Double.valueOf(mCardModel.getMapLat()),Double.valueOf(mCardModel.getMapLng())));
                        break;
                }
            }
        });


        //发布商品气泡提示 popupwindow
        if (popWindow == null) {
            View contenView = LayoutInflater.from(getContext()).inflate(R.layout.layout_send_product_hint, null, false);

            popWindow = new CusPopWindow.PopupWindowBuilder(getActivity())
                    .setView(contenView)
                    .setOutsideTouchable(true)
                    .create();
        }


    }


    /**
     * 控制appbar的滑动
     *
     * @param isScroll true 允许滑动 false 禁止滑动
     */
    public void banAppBarScroll(boolean isScroll) {
        View mAppBarChildAt = getAppBar().getChildAt(0);
        AppBarLayout.LayoutParams mAppBarParams = (AppBarLayout.LayoutParams) mAppBarChildAt.getLayoutParams();
        if (isScroll) {
            mAppBarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
            mAppBarChildAt.setLayoutParams(mAppBarParams);
        } else {
            mAppBarParams.setScrollFlags(0);
        }

    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    popWindow.dissmiss();
                    break;
            }
        }
    };


    private void statPhotoViewActivity(int position, List<String> list) {
        Intent intent = new Intent(getActivity(), PhotoViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(BIG_IMAGE_POSITION, position);
        bundle.putStringArrayList(BIG_IMAGE_URL, (ArrayList<String>) list);
        intent.putExtra(BUNDLE, bundle);
        startActivity(intent);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        if (mFromType == 1) {
            isUpdate = false;
            mPresenter.getData(true, null, null);
            tv_title.setText("我的智能名片");
        } else {
            isUpdate = false;
            mPresenter.getData(false, mUserId, mCardId);
        }
    }


    /**
     * 初始化tabview  tab栏
     */
    private void initTabView(List<TabShowHideBean> tablist) {
        mTablist.clear();
        mTablist.addAll(tablist);
        //初始化tab
        fragments = new ArrayList<>();
        //清理了tablayout的相关数据 重新加载
        tab_un_card_title.clearTab();
        for (int i = 0; i < tablist.size(); i++) {
            tab_un_card_title.addTab(tablist.get(i).funName);
            switch (tablist.get(i).funCode) {
                case TAB_1://个人tab
                    infoPosition = i;
                    fragments.add(new CardInfoFragment());
                    break;
                case TAB_2://动态tab
                    fragments.add(new CardDynamicFragment());
                    break;
                case TAB_3://商城tab
                    fragments.add(new CardMallFragment());
                    break;
                case TAB_4://新闻tab
                    fragments.add(CircleDynamicFragment.newIntance(mCardModel.getCurrentDepartmentID(), 4, mCardModel.getDistributorCreatorID()));
                    break;
                case TAB_5://官网tab
                    fragments.add(new CardCompanyFragment());
                    break;
            }
        }


        //前面清理数据的时候清理  了监听器这里添加个
        tab_un_card_title.addOnTabSelectedListeners();

        //默认选中第0项
        loadMultipleRootFragment(R.id.lin_union_me_card_bootom, 0, fragments.toArray(new ISupportFragment[tablist.size()]));

        tab_un_card_title.getTabLayout().getTabAt(0).select();
        int textLengthSum = 0;
        boolean isFourItem = false;//是否有单个item字数超过4个
        for (TabShowHideBean bean : tablist) {
            if (bean.funName.length() >= 4) {
                isFourItem = true;
                break;
            }
            textLengthSum += bean.getFunName().length();
        }
        if (textLengthSum > 15 || (isFourItem && tablist.size() == 5)) {
            tab_un_card_title.getTabLayout().setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            tab_un_card_title.getTabLayout().setTabMode(TabLayout.MODE_FIXED);
        }

        tab_un_card_title.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

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


        if (tab_un_card_title.getCustomViewList().size() == 0) {//初始化  第一次进来的时候才显示气泡
            if (mFromType == 1) {//自己的才显示气泡
                //发布商品气泡提示
                if (AppPreference.getIntance().getKeyHintSendProduct()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        popWindow.showAsDropDown(tab_un_card_title, tab_un_card_title.getWidth() / 2, -DensityUtil.dpTopx(90), Gravity.TOP);
                    }
                    //弹出对话框
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    }, 5000);
                }

            }
        }
    }

    /**
     * 跳转到官网
     */
    public void gotoWeb() {
        int postion = 0;
        for (int i = 0; i < mTablist.size(); i++) {
            if (mTablist.get(i).funCode.equals(TAB_5)) {
                postion = i;
            }
        }
        tab_un_card_title.getTabLayout().getTabAt(postion).select();
        showHideFragment(fragments.get(postion));
        mCurrentIndex = postion;
    }

    /**
     * 跳转到商城
     */
    public void gotoShop() {
        int postion = 0;
        for (int i = 0; i < mTablist.size(); i++) {
            if (mTablist.get(i).funCode.equals(TAB_3)) {
                postion = i;
            }
        }
        tab_un_card_title.getTabLayout().getTabAt(postion).select();
        showHideFragment(fragments.get(postion));
        mCurrentIndex = postion;
    }

    /**
     * 切换下面fragment
     *
     * @param postion
     */
    public void chanageFragment(int postion) {
        mCurrentIndex = postion;
        //这里是做下面个人页面切换的时候 释放语音播放器  因为下面infofrafment里面的 onSupportInvisible 方法第一次切换的时候不执行（不知什么原因）  所以这样做
        CardInfoFragment cardInfoFragment = findChildFragment(CardInfoFragment.class);
        if (postion!=infoPosition){
            cardInfoFragment.setPlayerRelease();
        }
        showHideFragment(fragments.get(postion));
    }

    /**
     * 获取名片详情数据回调
     *
     * @param model
     */
    @Override
    public void onCardData(final CardModel model) {
        if (model == null) {
            return;
        }
        if (model.getId() == null) {
            showToast("当前用户名片信息未完善!");
            pop();
            return;
        }
        mCardModel = model;

        //发送soket通信
        BaseClient.getInstance().setMessage(1, mCardModel.getId(), mCardModel.getUserId());

        mCardId = mCardModel.getId();
        //初始标题
        if (mFromType == 2) {//别人的
            ll_company.setVisibility(View.VISIBLE);
            img_edt_tab.setVisibility(View.GONE);
            tv_title.setText(model.getGsName() + "的智能名片");
        } else {//自己的
            ll_company.setVisibility(View.GONE);
            if (mCardModel.getUserIsEnterpriseManager()) {
                img_edt_tab.setVisibility(View.VISIBLE);
            } else {
                if (mCardModel.getUserIsEnterpriseMembers()) {
                    img_edt_tab.setVisibility(View.GONE);
                } else {
                    img_edt_tab.setVisibility(View.VISIBLE);
                }
            }
        }

        //初始化头像 及个人信息
        list.clear();
        list.add(model);
        adapter.setUpdate(isUpdate);
        adapter.setMFromType(mFromType);
        adapter.setModel(model);
        adapter.setNewData(list);
        //刷新更多数据模块
        refreshMoreData(mCardModel);

        //我的企业模块
        if (TextUtils.isEmpty(model.getShopName())|| TextUtils.isEmpty(model.getCurrentDepartmentID())) {
            ll_company.setVisibility(View.GONE);
        } else {
            ll_company.setVisibility(View.VISIBLE);
            LoadImageUtil.loadRoundImage(img_company_logo, model.getDistributorLogo(),4);
            tv_company_name.setText(model.getCompanyName());
        }

        switch (adapter.getData().get(0).getTemplateId()) {
            case TEMP_ID_1://(高端款)  上下版
                img_back.setBackgroundResource(R.drawable.ic_video_back);
                img_share.setBackgroundResource(R.drawable.ic_video_share);
                tv_title.setTextColor(getResources().getColor(R.color.white));
                break;
            case TEMP_ID_2://(大气款)  左右版
                img_back.setBackgroundResource(R.drawable.toolbar_back);
                img_share.setBackgroundResource(R.drawable.union_share);
                tv_title.setTextColor(getResources().getColor(R.color.fontcColor1));
                break;
        }

        //监听toolbar
        appbar_card.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                LogUtil.loge(layout_share_view.getHeight() + ">>" + view_zanwei.getHeight() + "----》》》verticalOffset" + verticalOffset);
                if (verticalOffset == 0) {//不拉的时候 toobar全透明
                    view_zanwei.setVisibility(View.GONE);
                    titlebar.setBackgroundResource(R.color.translate00);
                } else {
                    if (verticalOffset == -layout_share_view.getHeight()) {
                        view_zanwei.setVisibility(View.VISIBLE);
                    } else {
                        view_zanwei.setVisibility(View.GONE);
                    }
                    tv_title.setTextColor(getResources().getColor(R.color.fontcColor1));
                    titlebar.setBackgroundResource(R.color.windows_bg);

                }
            }
        });

        initTabView(model.getGsCardFunctionSetList());

        //查询dialog中的显示隐藏tab
        mPresenter.getCardTabList(1, mCardModel.getUserId(), true, "");
    }

    @Override
    public void doLike(boolean isLike) {
//        if (true) {
//            tv_card_other_like.setText((Integer.valueOf(tv_card_other_like.getText().toString()) + 1) + "");
//            img_card_un_like.setImageResource(R.drawable.icon_like_select);
//        } else {
//            tv_card_other_like.setText((Integer.valueOf(tv_card_other_like.getText().toString()) - 1) + "");
//            img_card_un_like.setImageResource(R.mipmap.union_card_like);
//        }
    }

    @Override
    public void doFocus(final boolean isFocus) {
        if (isFocus) {
            mCardModel.setRelation(1);
            adapter.setModel(mCardModel);
            adapter.notifyDataSetChanged();
        } else {
            mCardModel.setRelation(0);
            adapter.setModel(mCardModel);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取dialog中的显示隐藏tab成功
     *
     * @param modelList
     */
    @Override
    public void getTabList(List<TabShowHideBean> modelList) {
        tabdialoglist.clear();
        tabdialoglist.addAll(modelList);
    }

    @Override
    public void saveSucess(List<TabShowHideBean> modelList) {
        tabdialoglist.clear();
        tabdialoglist.addAll(modelList);
        List<TabShowHideBean> list = new ArrayList<>();
        for (int i = 0; i < modelList.size(); i++) {
            if (modelList.get(i).status.equals("1")) {
                list.add(modelList.get(i));
            }
        }
        initTabView(list);

        mCardModel.setGsCardFunctionSetList(list);
        //改成功名字后要刷新个人中的 商店和官网模块名字
        CardInfoFragment cardInfoFragment = findChildFragment(CardInfoFragment.class);
        if (cardInfoFragment != null) {
            cardInfoFragment.onCardData(mCardModel);
        }
    }

    public String getCardUserId() {
        return mUserId;
    }

    public String getCardId() {
        return mCardId;
    }

    public int getFromType() {
        return mFromType;
    }

    public AppBarLayout getAppBar() {
        return appbar_card;
    }

    public LinearLayout getLayout_view() {
        return layout_share_view;
    }

    public CardModel getCardData() {
        return mCardModel;
    }


    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        fragments.get(tab_un_card_title.getTabLayout().getSelectedTabPosition()).onFragmentResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case 1122://编辑名片
                    isUpdateInfo = true;
                    mCardModel = GSONUtil.getEntity(data.getString("UserInfo"), CardModel.class);

                    isUpdate = true;
                    list.clear();
                    list.add(mCardModel);
                    adapter.setUpdate(isUpdate);
                    adapter.setNewData(list);
                    //刷新更多数据模块
                    refreshMoreData(mCardModel);

                    //这里是改名片后比如头像 什么的 刷新个人下面的数据
                    CardInfoFragment cardInfoFragment = findChildFragment(CardInfoFragment.class);
                    if (cardInfoFragment != null) {
                        cardInfoFragment.onCardData(mCardModel);
                    }

                    break;
                case RESULT_CODE://切换模板
                    CardTemplateModel model1 = (CardTemplateModel) data.getSerializable(KEY_IS_SELECT);
                    mCardModel.templateId = model1.getId();

                    isUpdate = true;
                    list.clear();
                    list.add(mCardModel);
                    adapter.setUpdate(true);
                    adapter.setNewData(list);
                    switch (adapter.getData().get(0).getTemplateId()) {
                        case TEMP_ID_1://(高端款)  上下版
                            img_back.setBackgroundResource(R.drawable.ic_video_back);
                            img_share.setBackgroundResource(R.drawable.ic_video_share);
                            tv_title.setTextColor(getResources().getColor(R.color.white));
                            break;
                        case TEMP_ID_2://(大气款)  左右版
                            img_back.setBackgroundResource(R.drawable.toolbar_back);
                            img_share.setBackgroundResource(R.drawable.union_share);
                            tv_title.setTextColor(getResources().getColor(R.color.fontcColor1));
                            break;
                    }
                    break;
            }

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_edt_tab://点击编辑tab按钮
                TabShowHideDialog.Companion.newInstance(tabdialoglist, mCardModel.getUserIsEnterpriseManager())
                        .invoke(new TabShowHideDialog.ClickListener() {
                            @Override
                            public void click(@org.jetbrains.annotations.Nullable View v, @org.jetbrains.annotations.Nullable List<TabShowHideBean> list) {
                                CardTabHideBody body = new CardTabHideBody();
                                body.type = 1;
                                body.gsCardFunctionSetList = list;
                                mPresenter.saveCardTabShowHide(body);
                            }
                        })
                        .show(getFragmentManager(), "showTabDialog");
                break;

            case R.id.img_share:
                hideView();
                ShareDialog.Companion.newInstance().invoke(new ShareDialog.ClickListener() {
                    @Override
                    public void click(@org.jetbrains.annotations.Nullable View v) {
                        if (null == v) {
                            showView();
                        } else {
                            switch (v.getId()) {
                                case R.id.tv_share_wx://分享微信
                                    if (mCardModel == null) {
                                        break;
                                    }
                                    //分享时掉次分享接口，3.9.1
                                    DoLikeModel likeModel1 = new DoLikeModel();
                                    likeModel1.setHandleId(mCardId);
                                    likeModel1.setOperType(2);
                                    likeModel1.setRecordType(0);
                                    mPresenter.doLike(false, true, likeModel1);

                                    WeiXinMinModel model = new WeiXinMinModel();
                                    model.setLowVersionUrl(Config.BASE_URL);
                                    if (mFromType == 1) {
                                        if (TextUtils.isEmpty(AppPreference.getIntance().getShareCardValue())) {
                                            model.setTitle(mCardModel.getGsName() + "的智能名片");
                                        } else {
                                            model.setTitle(AppPreference.getIntance().getShareCardValue());
                                        }
                                    } else {
                                        model.setTitle(mCardModel.getGsName() + "的智能名片");
                                    }
                                    model.setDescribe(" ");
                                    model.setShareUrl("/pages/card/card?id=" + mCardModel.getUserId() + "&gsName=" + mCardModel.getGsName()
                                            + "&shareFromId=" + App.getInstance().userId + "&originUserId=" + mCardModel.getUserId());
                                    Bitmap bitmap = BitmapUtil.createViewBitmap(layout_share_view);
                                    bitmap = BitmapUtil.zoomMaxImage(bitmap, 500, 400);//分享的图片配置

                                    WeiXinUtils.shareWXMiniProject(getContext(),
                                            model.getLowVersionUrl(), model.getTitle(), model.getDescribe(), model.getShareUrl(),
                                            BitmapUtil.zoomImage(bitmap, 500, 400));
                                    showView();
                                    break;
                                case R.id.tv_share_haibao:// 生成海报
                                    showView();
                                    start(CardPosterFragment.Companion.newIncetance(mCardModel));
                                    break;
                                case R.id.tv_cancel://取消
                                    showView();
                                    break;

                            }
                        }

                    }
                }).show(getFragmentManager(), "分享名片");

            case R.id.ll_show_hide_all://点击展示全信息
                isShowAll = !isShowAll;
                if (isShowAll) {
                    tv_show_hide_all.setText("收起以下名片信息");
                    ll_card_more.setVisibility(View.VISIBLE);
                    Drawable drawable = getContext().getResources().getDrawable(R.mipmap.icon_arrow_up);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tv_show_hide_all.setCompoundDrawables(null, null, drawable, null);
                } else {
                    tv_show_hide_all.setText("展开全部名片信息");
                    ll_card_more.setVisibility(View.GONE);
                    Drawable drawable = getContext().getResources().getDrawable(R.mipmap.icon_arrow_down);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tv_show_hide_all.setCompoundDrawables(null, null, drawable, null);
                }
                break;
            case R.id.tv_call_mobile_phone://拨打电话
                //权限检查
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_MOBILE_PHONE_PERMISSION);
                    } else {
                        callPhone(edt_mobile_phone.getText().toString());
                    }
                } else {
                    callPhone(edt_mobile_phone.getText().toString());
                }
                break;
            case R.id.tv_call_phone://拨打座机
                //权限检查
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_MOBILE_PHONE_PERMISSION);
                    } else {
                        callPhone(edt_phone.getText().toString());
                    }
                } else {
                    callPhone(edt_phone.getText().toString());
                }
                break;
//            case R.id.tv_copy_wx://复制微信
//                CommonUtil.textViewClickCopy(edt_wx, tv_copy_wx);
//                break;
//            case R.id.tv_copy_mail://复制邮箱
//                CommonUtil.textViewClickCopy(edt_mail, tv_copy_mail);
//                break;
//            case R.id.tv_copy_shop_name://复制公司名字
//                CommonUtil.textViewClickCopy(edt_shop_name, tv_copy_shop_name);
//                break;
            case R.id.tv_copy_shop_address://导航地址
                start(AmapFragment.Companion.newIncetance(edt_shop_address.getText().toString(),Double.valueOf(mCardModel.getMapLat()),Double.valueOf(mCardModel.getMapLng())));
                break;
            case R.id.tv_goto_circle://进入企业详情(圈子详情)
                start(CircleDetailFragment.newIntance(mCardModel.getGroupId()));
                break;
            default:
                break;
        }
    }

    /**
     * 刷新更多数据
     */
    private void refreshMoreData(CardModel mCardModel) {
        //更多数据全为空的时候 不显示按钮
        if (TextUtils.isEmpty(mCardModel.getMobile()) && TextUtils.isEmpty(mCardModel.getTel()) && TextUtils.isEmpty(mCardModel.getWxNum())
                && TextUtils.isEmpty(mCardModel.getEmail()) && TextUtils.isEmpty(mCardModel.getShopName()) && TextUtils.isEmpty(mCardModel.getShopAddress())) {
            ll_show_hide_all.setVisibility(View.GONE);
        } else {
            ll_show_hide_all.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(mCardModel.getMobile())) {
            ll_mobile_phone.setVisibility(View.GONE);
        } else {
            ll_mobile_phone.setVisibility(View.VISIBLE);
            edt_mobile_phone.setText(mCardModel.getMobile());
        }
        if (TextUtils.isEmpty(mCardModel.getTel())) {
            ll_phone.setVisibility(View.GONE);
        } else {
            ll_phone.setVisibility(View.VISIBLE);
            edt_phone.setText(mCardModel.getTel());
        }
        if (TextUtils.isEmpty(mCardModel.getWxNum())) {
            ll_wx.setVisibility(View.GONE);
        } else {
            ll_wx.setVisibility(View.VISIBLE);
            edt_wx.setText(mCardModel.getWxNum());
        }
        if (TextUtils.isEmpty(mCardModel.getEmail())) {
            ll_mail.setVisibility(View.GONE);
        } else {
            ll_mail.setVisibility(View.VISIBLE);
            edt_mail.setText(mCardModel.getEmail());
        }
        if (TextUtils.isEmpty(mCardModel.getShopName())) {
            ll_shop_more_name.setVisibility(View.GONE);
        } else {
            ll_shop_more_name.setVisibility(View.VISIBLE);
            edt_shop_name.setText(mCardModel.getShopName());
        }
        if (TextUtils.isEmpty(mCardModel.getShopAddress())) {
            ll_shop_more_address.setVisibility(View.GONE);
        } else {
            ll_shop_more_address.setVisibility(View.VISIBLE);
            edt_shop_address.setText(mCardModel.getShopAddress());
        }

    }

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param string 电话号码
     */
    public void callPhone(String string) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri date = Uri.parse("tel:" + string);
        intent.setData(date);
        startActivity(intent);
    }

    /**
     * 隐藏 “编辑”，“更换模板”模块
     */
    private void hideView() {
        adapter.setShowView(false);
        adapter.notifyDataSetChanged();
    }

    /**
     * 显示 “编辑”，“更换模板”模块
     */
    private void showView() {
        adapter.setShowView(true);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case CALL_MOBILE_PHONE_PERMISSION:
                    callPhone(edt_mobile_phone.getText().toString());
                    break;
                case CALL_PHONE_PERMISSION:
                    callPhone(edt_phone.getText().toString());
                    break;
                default:
                    Toast.makeText(getActivity(), "缺少拨打电话的权限,无法拨打客服电话", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (mCardModel != null) {
            BaseClient.getInstance().setMessage(1, mCardModel.getId(), mCardModel.getUserId());
        }
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        BaseClient.getInstance().close();
    }
}