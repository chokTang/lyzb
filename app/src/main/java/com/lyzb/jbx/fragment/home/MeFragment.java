package com.lyzb.jbx.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.longshaolib.base.BaseFragment;
import com.like.utilslib.image.LoadImageUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.me.MeFunctionAdapter;
import com.lyzb.jbx.api.UrlConfig;
import com.lyzb.jbx.dialog.MeGrowDialog;
import com.lyzb.jbx.fragment.circle.MyCircleFragment;
import com.lyzb.jbx.fragment.common.ProjectH5Fragment;
import com.lyzb.jbx.fragment.me.AccountCorrelationFragment;
import com.lyzb.jbx.fragment.me.CollectFragment;
import com.lyzb.jbx.fragment.me.FansFragment;
import com.lyzb.jbx.fragment.me.FocusFragment;
import com.lyzb.jbx.fragment.me.MyFootFragment;
import com.lyzb.jbx.fragment.me.SetShareWordFragment;
import com.lyzb.jbx.fragment.me.SystemSetFragment;
import com.lyzb.jbx.fragment.me.basic.CardCilpFrgament;
import com.lyzb.jbx.fragment.me.card.AccessFragment;
import com.lyzb.jbx.fragment.me.card.CardFragment;
import com.lyzb.jbx.fragment.me.card.UserInfoFragment;
import com.lyzb.jbx.fragment.me.company.MyCompanyFragment;
import com.lyzb.jbx.fragment.me.company.OrganDetailFragment;
import com.lyzb.jbx.fragment.me.customerManage.CustomerManageFragment;
import com.lyzb.jbx.fragment.me.publish.PublishFragment;
import com.lyzb.jbx.fragment.school.BusinessSchoolListFragment;
import com.lyzb.jbx.inter.IRecycleAnyClickListener;
import com.lyzb.jbx.model.cenum.FunctionItemEnum;
import com.lyzb.jbx.model.me.CardModel;
import com.lyzb.jbx.model.me.FuctionItemModel;
import com.lyzb.jbx.mvp.presenter.me.MePresenter;
import com.lyzb.jbx.mvp.view.me.IMeView;
import com.lyzb.jbx.util.AppPreference;
import com.szy.yishopcustomer.Activity.AccountSecurityActivity;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Activity.im.ImCommonActivity;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.ImUtil;
import com.szy.yishopcustomer.ViewModel.im.ImHeaderGoodsModel;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;

/**
 * @author wyx
 * @role 我的-Fragment
 * @time 2019 2019/3/4 14:08
 */

public class MeFragment extends BaseFragment<MePresenter> implements IMeView, View.OnClickListener, IRecycleAnyClickListener {

    //个人信息模块
    private ImageView cim_union_me_img;
    private TextView tv_union_me_name;
    private ImageView img_union_me_vip;
    private TextView tv_union_me_card;
    private ProgressBar progress_card_info;
    private TextView tv_card_info;

    //我的4个模块
    private LinearLayout lin_union_me_publish;
    private TextView tv_un_me_pub;
    private LinearLayout lin_union_me_focus;
    private TextView tv_un_me_focus;
    private LinearLayout lin_union_me_fans;
    private TextView tv_un_me_fans;
    private LinearLayout lin_union_me_collect;
    private LinearLayout ll_message;
    private TextView tv_un_me_coll;

    //我的4个模块下-vip经验条
    private ImageView img_me_card_grow;
    private TextView text_me_card_grow;

    //我的默认企业
    private ImageView img_company_header;
    private TextView tv_company_title;
    private TextView tv_company_into;
    private LinearLayout linear_company_info;

    //功能模块
    private RecyclerView recycle_me;
    private MeFunctionAdapter mAdapter;
    private String userId = null;

    //我的名片实体
    public CardModel mModel;

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        cim_union_me_img = findViewById(R.id.cim_union_me_img);
        ll_message = findViewById(R.id.ll_message);
        tv_union_me_name = findViewById(R.id.tv_union_me_name);
        img_union_me_vip = findViewById(R.id.img_union_me_vip);
        tv_union_me_card = findViewById(R.id.tv_union_me_card);
        progress_card_info = findViewById(R.id.progress_card_info);
        tv_card_info = findViewById(R.id.tv_card_info);
        lin_union_me_publish = findViewById(R.id.lin_union_me_publish);
        tv_un_me_pub = findViewById(R.id.tv_un_me_pub);
        lin_union_me_focus = findViewById(R.id.lin_union_me_focus);
        tv_un_me_focus = findViewById(R.id.tv_un_me_focus);
        lin_union_me_fans = findViewById(R.id.lin_union_me_fans);
        tv_un_me_fans = findViewById(R.id.tv_un_me_fans);
        lin_union_me_collect = findViewById(R.id.lin_union_me_collect);
        tv_un_me_coll = findViewById(R.id.tv_un_me_coll);
        img_me_card_grow = findViewById(R.id.img_me_card_grow);
        text_me_card_grow = findViewById(R.id.text_me_card_grow);
        img_company_header = findViewById(R.id.img_company_header);
        tv_company_title = findViewById(R.id.tv_company_title);
        tv_company_into = findViewById(R.id.tv_company_into);
        recycle_me = findViewById(R.id.recycle_me);
        linear_company_info = findViewById(R.id.linear_company_info);

        tv_union_me_card.setOnClickListener(this);
        lin_union_me_publish.setOnClickListener(this);
        lin_union_me_focus.setOnClickListener(this);
        lin_union_me_fans.setOnClickListener(this);
        lin_union_me_collect.setOnClickListener(this);
        tv_company_into.setOnClickListener(this);
        cim_union_me_img.setOnClickListener(this);
        ll_message.setOnClickListener(this);

        findViewById(R.id.layout_length).setOnClickListener(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        recycle_me.setNestedScrollingEnabled(false);
        mAdapter = new MeFunctionAdapter(getContext(), null);
        recycle_me.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_window_10));
        mAdapter.setLayoutManager(recycle_me);
        recycle_me.setAdapter(mAdapter);
        mAdapter.setClickListener(this);
    }

    @Override
    public void onMeData(CardModel model) {
        if (model == null) {
            return;
        }
        mModel = model;
        //赋值个人信息
        LoadImageUtil.loadRoundSizeImage(cim_union_me_img, model.getHeadimg(), 60);
        tv_union_me_name.setText(model.getGsName());
        if (model.getUserVipAction().size() > 0) {
            img_union_me_vip.setVisibility(View.VISIBLE);
        } else {
            img_union_me_vip.setVisibility(View.GONE);
        }
        progress_card_info.setProgress(model.getPerfectRatio());
        tv_card_info.setText(model.getPerfectStr());
        tv_union_me_card.setText(model.getPerfectRatio() == 100 ? "进入名片" : "完善名片");

        //赋值我的4个模块的数字
        tv_un_me_pub.setText(String.valueOf(model.getTopicCount()));
        tv_un_me_focus.setText(String.valueOf(model.getRelationGz()));
        tv_un_me_fans.setText(String.valueOf(model.getRelationFs()));
        tv_un_me_coll.setText(String.valueOf(model.getCollectionCount()));

        //我的4个模块下-经验条赋值
        if (model.getIntegralImg().indexOf(".gif") != -1) {
            com.szy.yishopcustomer.Util.image.LoadImageUtil.loadImageGif(img_me_card_grow, model.getIntegralImg());
        } else {
            LoadImageUtil.loadImage(img_me_card_grow, model.getIntegralImg());
        }
        text_me_card_grow.setText(model.getIntegralNum());

        //赋值我的默认企业
        if (TextUtils.isEmpty(model.getCompanyName()) && TextUtils.isEmpty(model.getDistributorLogo())) {
            linear_company_info.setVisibility(View.GONE);
        } else {
            linear_company_info.setVisibility(View.VISIBLE);
            LoadImageUtil.loadRoundImage(img_company_header, model.getDistributorLogo(), 4, R.mipmap.icon_company_defult);
            tv_company_title.setText(model.getCompanyName());
        }

        //初始化本次登录以后appcation缓存中的数据
        App.getInstance().cardFansNumber = String.valueOf(model.getRelationFs());
        App.getInstance().cardFocusNumber = String.valueOf(model.getRelationGz());
        App.getInstance().cardDycNumber = String.valueOf(model.getTopicCount());
        App.getInstance().nickName = model.getGsName();
        App.getInstance().headimg = model.getHeadimg();
        App.getInstance().isGoBuyGood = model.isShopIsBuy();

        AppPreference.getIntance().setHxHeaderImg(model.getHeadimg());
        AppPreference.getIntance().setHxNickName(model.getGsName());

        if (model.getUserVipAction().size() > 0) {
            App.getInstance().isUserVip = true;
        } else {
            App.getInstance().isUserVip = false;
        }

        if (model.getDistributorCreatorID().equals(App.getInstance().userId)) {
            App.getInstance().isMeComd = true;
        } else {
            App.getInstance().isMeComd = false;
        }
    }

    @Override
    public void onFunctionList(List<FuctionItemModel> list) {
        if (list != null) {
            mAdapter.update(list);
        }
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_union_me;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (App.getInstance().isLogin()) {
            mPresenter.getData();
            if (!TextUtils.equals(userId, App.getInstance().userId)) {
                mPresenter.getFunctionData();
                userId = App.getInstance().userId;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (mModel == null) {
            return;
        }
        switch (v.getId()) {
            //进入智能名片
            case R.id.tv_union_me_card:
            case R.id.ll_message:
                childStart(CardFragment.newIntance(1));
                break;
            //进入我的企业
            case R.id.tv_company_into:
                childStart(OrganDetailFragment.Companion.newIntance(mModel.getCurrentDepartmentID()));
                break;
            //头像-进入个人资料编辑页面
            case R.id.cim_union_me_img:
                childStart(UserInfoFragment.newIntance(mModel));
                break;
            //我的发表
            case R.id.lin_union_me_publish:
                childStart(new PublishFragment());
                break;
            //我的关注
            case R.id.lin_union_me_focus:
                childStart(new FocusFragment());
                break;
            //我的粉丝
            case R.id.lin_union_me_fans:
                childStart(new FansFragment());
                break;
            //我的收藏
            case R.id.lin_union_me_collect:
                childStart(new CollectFragment());
                break;
            //经验值
            case R.id.layout_length:
                boolean b = mModel.getIntegralImg().indexOf(".gif") != -1;
                MeGrowDialog.Companion.newIntance(mModel.getIntegralImg(), b).show(getFragmentManager(), "megrow");
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isDelayedData() {
        return false;
    }

    @Override
    public void onItemClick(View view, int position, Object item) {
        FunctionItemEnum itemEnum = (FunctionItemEnum) item;
        if (itemEnum.getClass2().toLowerCase().equals("H5".toLowerCase())) {//打开h5
            childStart(ProjectH5Fragment.newIntance(getUrl(itemEnum.getPathAndorid()), itemEnum.getDisplay()));
            return;
        }
        if (itemEnum.getClass2().toLowerCase().equals("WXMINI".toLowerCase())) {//打开微信小程序
            String appId = Config.WEIXIN_APP_ID; // 填应用AppId
            IWXAPI api = WXAPIFactory.createWXAPI(getContext(), appId);

            WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
            req.userName = Config.WXMINI_APP_ID; // 填小程序原始id
            req.path = getUrl(itemEnum.getPathWXMini());                  //拉起小程序页面的可带参路径，不填默认拉起小程序首页
            req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
            api.sendReq(req);
            return;
        }
        switch (itemEnum.getCode()) {
            //我的足迹
            case "APP010105":
                childStart(new MyFootFragment());
                break;
            //访客统计
            case "APP010301":
                childStart(new AccessFragment());
                break;
            //我的圈子
            case "APP010102":
                childStart(new MyCircleFragment());
                break;
            //我的企业
            case "APP010101":
                childStart(new MyCompanyFragment());
                break;
            //分享语设置
            case "APP010103":
                childStart(new SetShareWordFragment());
                break;
            //服务管理
            case "APP010104":
                Intent pfIntent = new Intent(getContext(), ProjectH5Activity.class);
                pfIntent.putExtra(Key.KEY_URL.getValue(), String.format(UrlConfig.H5_SERVICE, AppPreference.getIntance().getUserGuid()));
                startActivity(pfIntent);
                break;
            //热文文库
            case "APP010201":
                childStart(ProjectH5Fragment.newIntance(String.format(UrlConfig.HOT_LITS, AppPreference.getIntance().getUserGuid(), App.getInstance().userId, App.getInstance().user_inv_code),
                        "热文文库"));
                break;
            //文章采集
            case "APP010202":
                childStart(ProjectH5Fragment.newIntance(String.format(UrlConfig.ARTICLE_MANGER, AppPreference.getIntance().getUserGuid(), App.getInstance().userId, App.getInstance().user_inv_code),
                        "文章采集"));
                break;
            //广告设置
            case "APP010203":
                childStart(ProjectH5Fragment.newIntance(String.format(UrlConfig.ADVERT_SETTING, AppPreference.getIntance().getUserGuid(), App.getInstance().userId, App.getInstance().user_inv_code),
                        "广告设置"));
                break;
            //我的文章
            case "APP010204":
                childStart(ProjectH5Fragment.newIntance(String.format(UrlConfig.HOT_MYARTICLE, AppPreference.getIntance().getUserGuid(), App.getInstance().userId, App.getInstance().user_inv_code),
                        "我的文章"));
                break;
            //客户管理
            case "APP010302":
                childStart(CustomerManageFragment.newIntance("", ""));
                break;
            //账户安全
            case "APP010401":
                startActivity(new Intent(getContext(), AccountSecurityActivity.class));
                break;
            //在线客服
            case "APP010402":
                Intent intent = new Intent(getContext(), ImCommonActivity.class);
                ImHeaderGoodsModel model = new ImHeaderGoodsModel();

                model.setChatType(EaseConstant.CHATTYPE_SINGLE);
                model.setShopImName(ImUtil.JBX_ONLINE);
                model.setShopName(ImUtil.JXB_ONLIN_NAME);
                model.setShopHeadimg("");
                model.setShopId("");

                Bundle args = new Bundle();
                args.putSerializable(ImCommonActivity.PARAMS_GOODS, model);
                intent.putExtras(args);
                startActivity(intent);
                break;
            //系统设置
            case "APP010403":
                childStart(new SystemSetFragment());
                break;
            //帐号关联
            case "APP010404":
                childStart(new AccountCorrelationFragment());
                break;
            //帮助中心
            case "APP010405":
                childStart(BusinessSchoolListFragment.newIntance(2));
                break;
            //名片夹
            case "APP010106":
                childStart(new CardCilpFrgament());
                break;
            default:
                break;
        }
    }

    private String getUrl(String url) {
        if (TextUtils.isEmpty(url))
            return url;
        if (url.contains("?")) {
            url += ("&userId=" + App.getInstance().userId);
        } else {
            url += ("?userId=" + App.getInstance().userId);
        }
        return url;
    }
}
