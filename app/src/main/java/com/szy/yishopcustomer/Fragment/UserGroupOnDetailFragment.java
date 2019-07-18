package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.*;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.Util.ImageLoader;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.CheckoutActivity;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.GroupOnListActivity;
import com.szy.yishopcustomer.Activity.GroupOnRulesActivity;
import com.szy.yishopcustomer.Activity.RootActivity;
import com.szy.yishopcustomer.Activity.ShareActivity;
import com.szy.yishopcustomer.Activity.UserGroupOnDetailActivity;
import com.szy.yishopcustomer.Adapter.UserGrouponHeadImageAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.CommonModel;
import com.szy.yishopcustomer.ResponseModel.UserGroupOn.GroupOnLogItemModel;
import com.szy.yishopcustomer.ResponseModel.UserGroupOn.Model;
import com.szy.yishopcustomer.ResponseModel.UserGroupOn.SkuModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import cn.iwgang.countdownview.CountdownView;

/**
 * Created by liwei on 2017/3/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class UserGroupOnDetailFragment extends YSCBaseFragment {
    @BindView(R.id.fragment_user_groupon_goodsImg)
    ImageView goodsImage;
    @BindView(R.id.fragment_user_groupon_goodsName)
    TextView goodsName;
    @BindView(R.id.fragment_user_groupon_goodsPrice)
    TextView goodsPrice;
    @BindView(R.id.fragment_user_groupon_goodsCount)
    TextView goodsCount;
    @BindView(R.id.fragment_user_groupon_status)
    ImageView groupOnStatus;
    @BindView(R.id.fragment_user_groupon_goodsLayout)
    RelativeLayout goodsLayout;

    @BindView(R.id.fragment_user_groupon_tip_four)
    TextView diffNum;
    @BindView(R.id.cv_countdownViewTestHasBg)
    CountdownView mCvCountdownViewTestHasBg;
    @BindView(R.id.fragment_user_groupon_tip)
    TextView mGroupOnTip;
    @BindView(R.id.fragment_groupon_detail_image)
    ImageView mGroupOnImage;
    @BindView(R.id.fragment_user_groupon_detail_buy)
    Button mGroupOnBuy;
    @BindView(R.id.fragment_user_groupon_detail_more)
    Button mGroupOnMore;


    @BindView(R.id.fragment_user_groupon_detail_headImgRecyclerView)
    CommonRecyclerView mRecyclerView;

    @BindView(R.id.fragment_user_groupon_detail_check)
    TextView checkRule;
    @BindView(R.id.groupon_rules_result)
    TextView grouponResultTip;
    @BindView(R.id.groupon_rules_image)
    ImageView imageViewNext;
    @BindView(R.id.fragment_user_groupon_time)
    TextView grouponTime;
    @BindView(R.id.fragment_user_groupon_time_tip)
    TextView grouponTip;
    @BindView(R.id.fragment_user_groupon_detail_share)
    Button shareButton;

    private AlertDialog mConfirmDialog;
    private String groupSn;
    private String share;

    private Model mModel;
    private String actId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_user_group_on_order;

        Intent intent = getActivity().getIntent();
        groupSn = intent.getStringExtra(Key.KEY_GROUP_SN.getValue());
        share = intent.getStringExtra(Key.KEY_BOOLEAN.getValue());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        goodsLayout.setOnClickListener(this);
        checkRule.setOnClickListener(this);
        mGroupOnBuy.setOnClickListener(this);
        mGroupOnMore.setOnClickListener(this);
        shareButton.setOnClickListener(this);
        refresh();
        return v;
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_DETAIL:
                refreshCallback(response);
                break;
            case HTTP_QUICK_BUY:
                quickBuyCallback(response);
                break;
            case HTTP_ORDER_CANCEL:
                cancelGrouponCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
        }
    }

    private void cancelGrouponCallback(String response) {
        HttpResultManager.resolve(response, CommonModel.class, new HttpResultManager
                .HttpResultCallBack<CommonModel>() {
            @Override
            public void onSuccess(CommonModel back) {
                Toast.makeText(getActivity(), back.message, Toast.LENGTH_SHORT).show();
            }
        }, true);
        refresh();
    }

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_TIME_OUT:
                cancelGroupon(groupSn);
                //Toast.makeText(getActivity(),"test",Toast.LENGTH_SHORT).show();
                break;
            default:
                super.onEvent(event);
                break;
        }
    }

    private void cancelGroupon(String groupSn) {
        CommonRequest mQuickBuyRequest = new CommonRequest(Api.API_CANCEL_GROUPON, HttpWhat
                .HTTP_ORDER_CANCEL.getValue());
        mQuickBuyRequest.add("group_sn", groupSn);
        addRequest(mQuickBuyRequest);
    }

    @Override
    public void onClick(View v) {
        ViewType viewType = Utils.getViewTypeOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_DETAIL:
                openGroupOnRule();
                break;
            case VIEW_TYPE_INDEX:
                openIndex();
                break;
            case VIEW_TYPE_MORE:
                openMoreGroupOn();
                break;
            case VIEW_TYPE_BUY_NOW:
                quickBuy(mModel.data.sku.sku_id, groupSn);
                break;
            case VIEW_TYPE_QR_CODE:
                Utils.qRCodeDialog(getActivity(), Config.BASE_URL + mModel.data.groupon_log
                        .qr_code, "拼团二维码", "用手机微信扫一扫二维码，参加我的团");
                break;
            case VIEW_TYPE_GOODS:
                openGoodsActivity(mModel.data.sku.sku_id);
                break;
            case VIEW_TYPE_SHARE:
                openShareActivity();
                break;
            default:
                super.onClick(v);
        }
    }

    void openShareActivity() {
        Intent intent = new Intent(getActivity(), ShareActivity.class);
        intent.putStringArrayListExtra(ShareActivity.SHARE_DATA, UserGroupOnDetailActivity
                .shareData);
        intent.putExtra(ShareActivity.SHARE_TYPE, ShareActivity.TYPE_USER_GROUP_ON);
        intent.putExtra(Key.KEY_DIFF_NUM.getValue(), UserGroupOnDetailActivity.diffNumber + "");
        startActivity(intent);
    }

    public void openGoodsActivity(String skuId) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_SKU_ID.getValue(), skuId);
        intent.setClass(getActivity(), GoodsActivity.class);
        startActivity(intent);
    }

    public void refresh() {
        CommonRequest mGroupBuyRequest = new CommonRequest(Api.API_USER_GROUP_0N_DETAIL +
                groupSn, HttpWhat.HTTP_DETAIL.getValue());
        addRequest(mGroupBuyRequest);
    }

    public void refreshCallback(String response) {

        HttpResultManager.resolve(response, Model.class, new HttpResultManager
                .HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model back) {
                mModel = back;

                //拼团商品赋值
                SkuModel skuModel = mModel.data.sku;
                if (!Utils.isNull(back.data.goods.goods_image)) {
                    ImageLoader.displayImage(Utils.urlOfImage(back.data.goods.goods_image),
                            goodsImage);
                } else {
                    ImageLoader.displayImage(Utils.urlOfImage(skuModel.goods_image), goodsImage);
                }

                goodsName.setText(skuModel.sku_name);
                goodsPrice.setText(Utils.formatMoney(goodsPrice.getContext(), mModel.data
                        .groupon_info.act_price));
                goodsCount.setText(mModel.data.fight_num + "人团");
                if (mModel.data.groupon_log.status == 0) {
                    groupOnStatus.setBackgroundResource(R.mipmap.bg_groupon_ing);
                } else if (mModel.data.groupon_log.status == 1) {
                    groupOnStatus.setBackgroundResource(R.mipmap.bg_groupon_success);
                } else {
                    groupOnStatus.setBackgroundResource(R.mipmap.bg_groupon_fail);
                }

                Utils.setViewTypeForTag(goodsLayout, ViewType.VIEW_TYPE_GOODS);

/*
                Utils.setViewTypeForTag(goodsLayout, ViewType.VIEW_TYPE_GOODS);
                goodsLayout.setOnClickListener(this);*/

                //分享数据
                if (!Utils.isNull(mModel.data.share)) {
                    UserGroupOnDetailActivity.shareData.clear();
                    UserGroupOnDetailActivity.shareData.add(Utils.getMallMBaseUrl() + "/"+skuModel.sku_id);
                    UserGroupOnDetailActivity.shareData.add(mModel.data.share
                            .seo_groupon_info_title);
                    UserGroupOnDetailActivity.shareData.add(mModel.data.share
                            .seo_groupon_info_discription);
                    UserGroupOnDetailActivity.shareData.add(Utils.urlOfImage(mModel.data.share
                            .seo_groupon_info_image));
                    UserGroupOnDetailActivity.shareData.add(mModel.data.goods.goods_id);

                    actId = mModel.data.groupon_info.act_id;

                    UserGroupOnDetailActivity.diffNumber = mModel.data.diff_num;
                }

                //拼团买家
                boolean flag = false;//是否参加过拼团
                ArrayList headImageList = new ArrayList();
                for (GroupOnLogItemModel groupOnLogItemModel : mModel.data.groupon_log_list) {
                    headImageList.add(groupOnLogItemModel.headimg);

                    if(mModel.data.context.user_info.user_id.equals(groupOnLogItemModel.user_id)){
                        flag = true;
                    }
                }
                if (mModel.data.groupon_log.status != 1) {
                    for (int i = 0; i < mModel.data.diff_num; i++) {
                        headImageList.add("");
                    }
                }

                if (headImageList.size() < 6) {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mRecyclerView
                            .getContext());
                    layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
                    mRecyclerView.setLayoutManager(layoutManager);
                }else {
                    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(6,
                            StaggeredGridLayoutManager.VERTICAL);
                    mRecyclerView.setLayoutManager(layoutManager);
                }

                UserGrouponHeadImageAdapter imageAdapter = new UserGrouponHeadImageAdapter
                        (headImageList);
                mRecyclerView.setAdapter(imageAdapter);

                //如果参加过拼团
                //如果是第一个参团的，显示“开团时间”
                //如果不是第一个参团，显示“参团时间”
                if(flag){
                    if (mModel.data.groupon_log_list.get(0).user_id.equals(mModel.data.context
                            .user_info.user_id)) {
                        grouponTip.setText("开团时间");
                    }else{
                        grouponTip.setText("参团时间");
                    }
                }else{
                    //没有参加过拼团，显示"开团时间"
                    grouponTip.setText("开团时间");
                }

                //拼团时间/拼团规则
                grouponTime.setText(Utils.times(mModel.data.groupon_log.add_time));

                if (mModel.data.groupon_log.status == 0) {
                    //拼团中
                    mGroupOnImage.setVisibility(View.GONE);

                    diffNum.setText(Utils.spanStringColor("仅剩" + mModel.data.diff_num + "个名额，", ContextCompat.getColor(getActivity(), R.color.colorPrimary), 2, (mModel.data.diff_num+"").length()));

                    mCvCountdownViewTestHasBg.setVisibility(View.VISIBLE);
                    mCvCountdownViewTestHasBg.start((mModel.data.end_time - mModel.data.context
                            .current_time) * 1000);
                    //倒计时结束
                    mCvCountdownViewTestHasBg.setOnCountdownEndListener(new CountdownView
                            .OnCountdownEndListener() {
                        @Override
                        public void onEnd(CountdownView cv) {
                            EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_TIME_OUT
                                    .getValue()));
                        }
                    });

                    mGroupOnTip.setVisibility(View.VISIBLE);

                    if (flag) {
                        mGroupOnBuy.setVisibility(View.VISIBLE);
                        mGroupOnMore.setVisibility(View.VISIBLE);
                        shareButton.setVisibility(View.VISIBLE);
                        mGroupOnBuy.setBackgroundResource(R.drawable.gray_border_button_one);
                        mGroupOnMore.setBackgroundResource(R.drawable.gray_border_button_one);
                        mGroupOnBuy.setText("面对面扫码参团");
                        mGroupOnMore.setText("查看更多拼团");


                        Utils.setViewTypeForTag(mGroupOnBuy, ViewType.VIEW_TYPE_QR_CODE);

                        Utils.setViewTypeForTag(mGroupOnMore, ViewType.VIEW_TYPE_MORE);

                        Utils.setViewTypeForTag(shareButton, ViewType.VIEW_TYPE_SHARE);

                    } else {
                        mGroupOnBuy.setVisibility(View.VISIBLE);
                        mGroupOnMore.setVisibility(View.GONE);
                        shareButton.setVisibility(View.GONE);
                        mGroupOnBuy.setBackgroundResource(R.drawable.enable_button);
                        mGroupOnBuy.setTextColor(Color.parseColor("#ffffff"));
                        mGroupOnBuy.setText("一键参团");

                        Utils.setViewTypeForTag(mGroupOnBuy, ViewType.VIEW_TYPE_BUY_NOW);
                    }

                } else if (mModel.data.groupon_log.status == 1) {
                    //拼团成功
                    mGroupOnImage.setVisibility(View.VISIBLE);
                    mGroupOnImage.setImageResource(R.mipmap.ic_alert_success);
                    diffNum.setText("拼团成功");
                    diffNum.setTextColor(diffNum.getResources().getColor(R.color.colorGreen));

                    mCvCountdownViewTestHasBg.setVisibility(View.GONE);
                    mGroupOnTip.setVisibility(View.GONE);

                    //按钮
                    mGroupOnBuy.setVisibility(View.VISIBLE);
                    mGroupOnMore.setVisibility(View.VISIBLE);
                    shareButton.setVisibility(View.GONE);
                    mGroupOnBuy.setBackgroundResource(R.drawable.enable_button);
                    mGroupOnBuy.setTextColor(Color.parseColor("#ffffff"));
                    mGroupOnMore.setBackgroundResource(R.drawable.gray_border_button_one);
                    mGroupOnBuy.setText("去首页逛逛");
                    mGroupOnMore.setText("查看更多拼团");


                    Utils.setViewTypeForTag(mGroupOnBuy, ViewType.VIEW_TYPE_INDEX);
                    Utils.setViewTypeForTag(mGroupOnMore, ViewType.VIEW_TYPE_MORE);


                } else {
                    //拼团失败
                    mGroupOnImage.setVisibility(View.VISIBLE);
                    mGroupOnImage.setImageResource(R.mipmap.ic_alert_circled);
                    diffNum.setText("拼团不成功，款项将原路退回");
                    diffNum.setTextColor(diffNum.getResources().getColor(R.color.colorPrimary));

                    mCvCountdownViewTestHasBg.setVisibility(View.GONE);
                    mGroupOnTip.setVisibility(View.GONE);
                    //按钮
                    mGroupOnBuy.setVisibility(View.VISIBLE);
                    mGroupOnMore.setVisibility(View.VISIBLE);
                    shareButton.setVisibility(View.GONE);
                    mGroupOnBuy.setBackgroundResource(R.drawable.enable_button);
                    mGroupOnBuy.setTextColor(Color.parseColor("#ffffff"));
                    mGroupOnMore.setBackgroundResource(R.drawable.gray_border_button_one);
                    mGroupOnBuy.setText("去首页逛逛");
                    mGroupOnMore.setText("查看更多拼团");

                    Utils.setViewTypeForTag(mGroupOnBuy, ViewType.VIEW_TYPE_INDEX);
                    Utils.setViewTypeForTag(mGroupOnMore, ViewType.VIEW_TYPE_MORE);

                }

                //组团中
                if (mModel.data.groupon_log.status == 0) {
                    grouponResultTip.setText("达到人数\n拼团成功");
                    grouponResultTip.setTextColor(ContextCompat.getColor(grouponResultTip
                            .getContext(), R.color.colorThree));

                    imageViewNext.setImageResource(R.mipmap.ic_next_dark);
                    //组团成功
                } else if (mModel.data.groupon_log.status == 1) {
                    grouponResultTip.setText("达到人数\n拼团成功");
                    grouponResultTip.setTextColor(ContextCompat.getColor(grouponResultTip
                            .getContext(), R.color.colorPrimary));

                    imageViewNext.setImageResource(R.mipmap.ic_next_red);
                    //组团失败
                } else {
                    grouponResultTip.setText("时间超时\n拼团失败");
                    grouponResultTip.setTextColor(ContextCompat.getColor(grouponResultTip
                            .getContext(), R.color.colorPrimary));

                    imageViewNext.setImageResource(R.mipmap.ic_next_red);
                }

                Utils.setViewTypeForTag(checkRule, ViewType.VIEW_TYPE_DETAIL);

                if (!Utils.isNull(share) && share.equals("1")) {
                    showConfirmDialog();
                }
            }
        });
    }

    private void openGroupOnRule() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), GroupOnRulesActivity.class);
        intent.putExtra(Key.KEY_ACT_ID.getValue(), actId);
        startActivity(intent);
    }

    public void openMoreGroupOn() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), GroupOnListActivity.class);
        startActivity(intent);
    }

    public void openIndex() {
        bringRootToFront();
        CommonEvent event = new CommonEvent(EventWhat.EVENT_OPEN_INDEX.getValue(), this.getClass
                ().getSimpleName());
        EventBus.getDefault().post(event);
    }

    private void bringRootToFront() {
        Intent intent = new Intent(getContext(), RootActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    /*private void changeBottomButton(Integer status) {
        if (status == 0) {
            if(buttonType == 0){
                mIndexButton.setBackgroundResource(R.drawable.enable_button);
                mIndexButton.setText("立即参团" + Utils.formatMoney(getActivity(),mModel.data
                .groupon_info.act_price));
                mIndexButton.setTextColor(Color.parseColor("#ffffff"));

                mIndexButton.setClickable(true);
                Utils.setViewTypeForTag(mIndexButton, ViewType.VIEW_TYPE_BUY_NOW);
                mIndexButton.setOnClickListener(this);
            }else{
                mIndexButton.setBackgroundResource(R.drawable.disable_button);
                mIndexButton.setText("立即参团" + Utils.formatMoney(getActivity(),mModel.data
                .groupon_info.act_price));
                mIndexButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorSix));

                mIndexButton.setClickable(false);
            }

        }else {
            mIndexButton.setBackgroundResource(R.drawable.enable_button);
            mIndexButton.setText("返回首页");
            mIndexButton.setTextColor(Color.parseColor("#ffffff"));

            Utils.setViewTypeForTag(mIndexButton, ViewType.VIEW_TYPE_INDEX);
            mIndexButton.setOnClickListener(this);
        }
    }*/

    public void quickBuy(String skuId, String grouponSn) {
        CommonRequest mQuickBuyRequest = new CommonRequest(Api.API_QUICK_BUY, HttpWhat
                .HTTP_QUICK_BUY.getValue(), RequestMethod.POST);
        mQuickBuyRequest.add("sku_id", skuId);
        mQuickBuyRequest.add("number", 1);
        mQuickBuyRequest.add("group_sn", grouponSn);
        addRequest(mQuickBuyRequest);
    }

    private void quickBuyCallback(String response) {
        HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager
                .HttpResultCallBack<ResponseCommonModel>() {
            @Override
            public void onSuccess(ResponseCommonModel back) {
                goCheckout();
                finish();
            }
        }, true);
    }

    public void goCheckout() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), CheckoutActivity.class);
        startActivity(intent);
    }

    public void showConfirmDialog() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout
                .item_user_groupon_share_tip, null);
        mConfirmDialog = new AlertDialog.Builder(getContext()).create();
        mConfirmDialog.show();

        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = mConfirmDialog.getWindow().getAttributes();
        lp.width = display.getWidth(); //设置宽度
        lp.height = display.getHeight();
        mConfirmDialog.getWindow().setAttributes(lp);
        mConfirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color
                .TRANSPARENT));
        mConfirmDialog.getWindow().setContentView(view);

        LinearLayout cancelButton = (LinearLayout) view.findViewById(R.id.layout);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View button) {
                mConfirmDialog.hide();
            }
        });

        TextView textView = (TextView) view.findViewById(R.id.diff_number);

        textView.setText(Utils.spanStringColor("还差" + mModel.data.diff_num +
                "人就能组团成功\n快邀请小伙伴参团吧", ContextCompat.getColor(getActivity(), R.color.colorCyan), 2, (mModel.data.diff_num+"").length()));


    }
}