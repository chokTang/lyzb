package com.szy.yishopcustomer.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;
import com.lyzb.jbx.model.common.WeiXinMinModel;
import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonStringModel;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Activity.ShareActivity;
import com.szy.yishopcustomer.Activity.TencentQzoneShareActivity;
import com.szy.yishopcustomer.Activity.TencentShareActivity;
import com.szy.yishopcustomer.Activity.WeiBoShareActivity;
import com.szy.yishopcustomer.Activity.WeiXinShareActivity;
import com.szy.yishopcustomer.Adapter.ShareAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.ShareItemModel;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by 宗仁 on 16/8/17.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShareFragment extends YSCBaseFragment {

    //如果要想在调用分享的界面中实现分享后的回掉，那么就设置这个值
    public static Object lastActivity = null;

    private static final int HTTP_WHAT_GOODS_QR_CODE = 1;
    private static final int HTTP_WHAT_QR_CODE = 2;

    @BindView(R.id.fragment_share_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.fragment_share_cancelButton)
    Button mCancelButton;
    @BindView(R.id.fragment_share_relarivelayout)
    RelativeLayout mLayout;
    @BindView(R.id.linearlayout_share)
    LinearLayout linearlayout_share;
    @BindView(R.id.fragment_share_tip)
    TextView shareTipOne;
    @BindView(R.id.fragment_share_tiptwo)
    TextView shareTipTwo;
    private GridLayoutManager mLayoutManager;
    private ShareAdapter mShareAdapter;
    private String diffNumber;

    private ArrayList<String> shareData = new ArrayList();
    private WeiXinMinModel mMinModel;//分享微信小程序的实体
    private int type = 0;
    private int share_scope = 1;
    private String mId = "";
    private String mDialogTitle = "";
    private String mDialogTip = "";

    private Context mContext;

    private boolean shareCityLife = false;
    private String goodQrCodeUrl = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();

        mLayoutId = R.layout.fragment_share;
        mShareAdapter = new ShareAdapter();
        mShareAdapter.onLickListener = this;

        Intent intent = getActivity().getIntent();
        diffNumber = intent.getStringExtra(Key.KEY_DIFF_NUM.getValue());
        try {
            mMinModel = (WeiXinMinModel) intent.getSerializableExtra(ShareActivity.SHARE_DATA);
        } catch (Exception e) {
            mMinModel = null;
        }

        /**判断分享来源为 同城生活*/
        if (intent.getIntExtra(ShareActivity.SHARE_SOURCE, 0) == ShareActivity.TYPE_SOURCE) {
            shareCityLife = true;
            if (intent.hasExtra(ShareActivity.SHARE_DATA)) {
                ArrayList<String> temp = intent.getStringArrayListExtra(ShareActivity.SHARE_DATA);

                if (temp != null && temp.size() >= 4) {
                    if (temp.size() >= 5) {
                        mId = temp.get(4);
                    }
                    shareData.addAll(temp);
                }

            }

        } else {
            /**分享来源为零售商城*/
            if (intent.hasExtra(ShareActivity.SHARE_DATA)) {
                ArrayList<String> temp = intent.getStringArrayListExtra(ShareActivity.SHARE_DATA);

                if (App.getInstance().user_inv_code != null) {
                    if (temp.get(0).contains("?")) {
                        temp.set(0, temp.get(0) + "&invite_code=" + App.getInstance().user_inv_code);
                    } else {
                        temp.set(0, temp.get(0) + "?invite_code=" + App.getInstance().user_inv_code);
                    }
                }

                goodQrCodeUrl = temp.get(0);

                if (temp != null && temp.size() >= 4) {
                    if (temp.size() >= 5) {
                        mId = temp.get(4);
                    }
                    shareData.addAll(temp);
                }
            }


        }

        type = intent.getIntExtra(ShareActivity.SHARE_TYPE, 0);

        /*这次强制把全部去掉---2019年3月23日---共商联盟第一版，去掉所有的分享，只保留微信朋友圈和微信好友*/
        share_scope = intent.getIntExtra(ShareActivity.SHARE_SCOPE, 1);
        if (share_scope == 0) {
            share_scope = 1;
        }

        setUpAdapterData();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        Window win = getActivity().getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);

        mCancelButton.setOnClickListener(this);
        mLayout.setOnClickListener(this);
        mLayoutManager = new GridLayoutManager(getContext(), 4);
        mRecyclerView.setAdapter(mShareAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (Utils.isNull(diffNumber)) {
            shareTipOne.setVisibility(View.GONE);
            shareTipTwo.setVisibility(View.GONE);
        } else {
            shareTipOne.setVisibility(View.VISIBLE);
            shareTipTwo.setVisibility(View.VISIBLE);
            shareTipOne.setText("还差" + diffNumber + "人就能组团成功");
        }
        return view;
    }

    private void setUpAdapterData() {
        mShareAdapter.data.clear();

        if (share_scope == 2) {//微信小程序分享
            mShareAdapter.data.add(new ShareItemModel(R.mipmap.btn_weixin_friends, "微信", Macro
                    .SHARE_WEIXIN_MINI));
        } else if (share_scope == 1) {
            mShareAdapter.data.add(new ShareItemModel(R.mipmap.btn_friends_circle, "朋友圈", Macro
                    .SHARE_WEIXIN_CIRCLE));
            mShareAdapter.data.add(new ShareItemModel(R.mipmap.btn_weixin_friends, "微信", Macro
                    .SHARE_WEIXIN));
            mShareAdapter.data.add(new ShareItemModel(R.mipmap.btn_link, "复制链接", Macro.SHARE_LINK));
        } else if (share_scope == 3) {
            mShareAdapter.data.add(new ShareItemModel(R.mipmap.btn_friends_circle, "朋友圈", Macro
                    .SHARE_WEIXIN_CIRCLE));
            mShareAdapter.data.add(new ShareItemModel(R.mipmap.btn_weixin_friends, "微信", Macro
                    .SHARE_WEIXIN));
        } else {
            if (!Utils.isNull(Config.WEIXIN_APP_ID)) {
                mShareAdapter.data.add(new ShareItemModel(R.mipmap.btn_friends_circle, "朋友圈", Macro
                        .SHARE_WEIXIN_CIRCLE));
                mShareAdapter.data.add(new ShareItemModel(R.mipmap.btn_weixin_friends, "微信", Macro
                        .SHARE_WEIXIN));
            }
            if (!Utils.isNull(Config.TENCENT_ID)) {
                mShareAdapter.data.add(new ShareItemModel(R.mipmap.btn_qq_friends, "QQ好友", Macro
                        .SHARE_QQ));
                mShareAdapter.data.add(new ShareItemModel(R.mipmap.btn_qq_zone, "QQ空间", Macro
                        .SHARE_QZONE));
            }
            if (!Utils.isNull(Config.TENCENT_ID)) {
                mShareAdapter.data.add(new ShareItemModel(R.mipmap.btn_sina, "新浪微博", Macro
                        .SHARE_WEIBO));
            }

            if (shareCityLife == false) {
                mShareAdapter.data.add(new ShareItemModel(R.mipmap.btn_qrcode, "二维码", Macro.SHARE_QR_CODE));
                mShareAdapter.data.add(new ShareItemModel(R.mipmap.btn_sms, "短信", Macro.SHARE_SMS));
                mShareAdapter.data.add(new ShareItemModel(R.mipmap.btn_link, "复制链接", Macro.SHARE_LINK));
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (Utils.isDoubleClick()) {
            return;
        }

        switch (view.getId()) {
            case R.id.fragment_share_cancelButton:
                setResult(Activity.RESULT_CANCELED);
                finish();
                break;
            case R.id.fragment_share_relarivelayout:
                finish();
                break;
            default:
                switch (Utils.getViewTypeOfTag(view)) {
                    case VIEW_TYPE_SHARE:
                        ShareItemModel item = mShareAdapter.data.get(Utils.getPositionOfTag(view));

                        if (shareData.size() >= 4 || mMinModel != null) {
                            shareClick(item.code);
                        } else {
                            Intent result = new Intent();
                            result.putExtra(Key.KEY_SHARE.getValue(), item.code);
                            setResult(Activity.RESULT_OK, result);
                            finish();
                        }
                        break;
                    default:
                        super.onClick(view);
                        break;
                }
        }
    }

    private void shareClick(String code) {
        if (code.equals(Macro.SHARE_QR_CODE)) {
            //这里可以加动画
//            linearlayout_share.setVisibility(View.GONE);
            mLayout.setVisibility(View.GONE);
            getQrCode();
            return;
        }
        switch (code) {
            case Macro.SHARE_QQ:
                shareQ();
                break;
            case Macro.SHARE_WEIXIN:
                shareWX();
                break;
            //微信小程序
            case Macro.SHARE_WEIXIN_MINI:
                shareWXminiProject();
                break;
            case Macro.SHARE_QZONE:
                shareQZone();
                break;
            case Macro.SHARE_WEIXIN_CIRCLE:
                shartWXCircle();
                break;
            case Macro.SHARE_WEIBO:
                shareWB();
                break;
            case Macro.SHARE_SMS:
                String shareInfo = shareData.get(1) + "  " + shareData.get(2) + "  链接：" +
                        shareData.get(0);
                sendSms(shareInfo);
                break;
            case Macro.SHARE_LINK:
                if (mMinModel != null) {//如果分享的是微信小程序
                    Utils.copyToClipboard(mContext, getString(R.string.copyUrl), mMinModel.getLowVersionUrl() + mMinModel.getShareUrl());
                } else {
                    Utils.copyToClipboard(mContext, getString(R.string.copyUrl), shareData.get(0));
                }
                Utils.toastUtil.showToast(mContext, getString(R.string.copySuccess));
                break;
        }
        finish();
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_QR_CODE:
                getQrCodeSucceed(response);
                break;
            case HTTP_WHAT_GOODS_QR_CODE:
                /*** 商品分享 二维码url **/
                getGoodsQrCodeSucceed(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    public void sendSms(String sms) {
        Uri uri = Uri.parse("smsto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", sms);
        startActivity(intent);
    }

    DialogInterface.OnCancelListener cancelListener = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            getActivity().finish();
        }
    };

    private void getQrCode() {
        if (!TextUtils.isEmpty(mId)) {
            try {
                Integer.getInteger(mId);
            } catch (Exception e) {
                Utils.qRCodeDialog(mContext, shareData.get(4), "", "", cancelListener);
                return;
            }

            if (type == ShareActivity.TYPE_GOODS) {
                CommonRequest request = new CommonRequest(Api.API_QR_CODE + mId, HTTP_WHAT_GOODS_QR_CODE);
                addRequest(request);
            } else if (type == ShareActivity.TYPE_SHOP) {
                mDialogTitle = "店铺二维码";
                mDialogTip = "邀请好友来扫一扫分享店铺给TA";
                CommonRequest request = new CommonRequest(Api.API_SHOP_QR_CODE, HTTP_WHAT_QR_CODE);
                request.add("id", mId);
                addRequest(request);
            } else if (type == ShareActivity.TYPE_GROUP_ON_LIST) {//拼团活动列表二维码
                mDialogTitle = "拼团二维码";
                mDialogTip = "邀请好友来扫一扫分享拼团活动给TA";
                Utils.qRCodeDialog(getActivity(), Utils.urlOfImage(shareData.get(3)), mDialogTitle, mDialogTip, cancelListener);
            } else if (type == ShareActivity.TYPE_USER_GROUP_ON) {
                mDialogTitle = "拼团活动";
                mDialogTip = "邀请好友来扫一扫分享拼团活动给TA";

                CommonRequest request = new CommonRequest(Api.API_QR_CODE + mId, HTTP_WHAT_QR_CODE);
                addRequest(request);
            } else if (type == ShareActivity.TYPE_SUPPORT_SHOP) {
                mDialogTitle = "店铺二维码";
                mDialogTip = "邀请好友来扫一扫分享店铺给TA";
                CommonRequest request = new CommonRequest(Api.API_SHOP_QR_CODE, HTTP_WHAT_QR_CODE);
                request.add("id", mId);
                addRequest(request);
            }
        }

    }

    private void getQrCodeSucceed(String response) {
        HttpResultManager.resolve(response, ResponseCommonStringModel
                .class, new HttpResultManager.HttpResultCallBack<ResponseCommonStringModel>() {
            @Override
            public void onSuccess(ResponseCommonStringModel back) {
                Utils.qRCodeDialog(mContext, Utils.urlOfImage(back.data), mDialogTitle, mDialogTip, cancelListener);
            }
        });
    }

    private void getGoodsQrCodeSucceed(String response) {
        HttpResultManager.resolve(response, ResponseCommonStringModel
                .class, new HttpResultManager.HttpResultCallBack<ResponseCommonStringModel>() {
            @Override
            public void onSuccess(ResponseCommonStringModel back) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                RelativeLayout layout = (RelativeLayout) getActivity().getLayoutInflater().inflate(R.layout.dialog_goods_qrcode, null);
                builder.setView(layout);

                ImageView logo = (ImageView) layout.findViewById(R.id.dialog_qrcode_logo);
                ImageView qrcode = (ImageView) layout.findViewById(R.id.dialog_qrcode_image);
                ImageView goodsImage = (ImageView) layout.findViewById(R.id.dialog_qrcode_goodsImage);
                TextView goodsName = (TextView) layout.findViewById(R.id.dialog_qrcode_goodsName);
                TextView goodsPrice = (TextView) layout.findViewById(R.id.dialog_qrcode_goodsPrice);
                ImageView close = (ImageView) layout.findViewById(R.id.dialog_goods_qrcode_close);

                ImageLoader.displayImage(Utils.urlOfImage(shareData.get(5)), logo);


                ImageLoader.displayImage(Utils.urlOfImage(back.data), qrcode);

                //ImageLoader.displayImage(shareData.get(3), goodsImage);

//                android:layout_width="104dp"
//                android:scaleType="fitXY"
//                android:layout_height="104dp"

                goodsImage.setImageBitmap(Utils.createBarCode(getActivity(), goodQrCodeUrl));

                goodsName.setText(shareData.get(6));
                goodsPrice.setText(shareData.get(7));
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().finish();
                    }
                });

                builder.setCancelable(true);
                final AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color
                        .TRANSPARENT));
                alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        getActivity().finish();
                    }
                });
                alertDialog.show();
            }
        });
    }

    private void shareQ() {
        if (Utils.isQQClientAvailable(mContext)) {

            Intent intent = new Intent();
            intent.putStringArrayListExtra(Key.KEY_SHARE_DATA.getValue(), shareData);
            intent.setClass(mContext, TencentShareActivity.class);
            mStartActivity(intent, RequestCode.REQUEST_CODE_TENCENT_SHARE.getValue());
        } else {
            Utils.toastUtil.showToast(mContext, "请先安装QQ客户端");
        }
    }

    private void shareQZone() {
        Intent intentQzone = new Intent();
        intentQzone.putStringArrayListExtra(Key.KEY_SHARE_DATA.getValue(), shareData);
        intentQzone.setClass(mContext, TencentQzoneShareActivity.class);
        mStartActivity(intentQzone, RequestCode.REQUEST_CODE_TENCENT_QZONE_SHARE.getValue
                ());
    }

    private void shareWB() {
        if (Utils.isWeiboInstalled(mContext)) {
            Intent intentWeibo = new Intent();
            intentWeibo.putStringArrayListExtra(Key.KEY_SHARE_DATA.getValue(), shareData);
            intentWeibo.setClass(mContext, WeiBoShareActivity.class);
            mStartActivity(intentWeibo, RequestCode.REQUEST_CODE_WEIBO_SHARE.getValue());
        } else {
            Utils.toastUtil.showToast(mContext, "请先安装微博客户端");
        }
    }

    /**
     * 分享微信小程序
     */
    private void shareWXminiProject() {
        if (Utils.isWeixinAvilible(mContext)) {
            Intent intentWeixin = new Intent();
            intentWeixin.putExtra(Key.KEY_SHARE_DATA.getValue(), mMinModel);
            intentWeixin.setClass(mContext, WeiXinShareActivity.class);
            intentWeixin.putExtra("mType", 1);
            mStartActivity(intentWeixin, RequestCode.REQUEST_CODE_WEIXIN_SHARE_MINI.getValue());
        } else {
            Utils.toastUtil.showToast(mContext, "请先安装微信客户端");
        }
    }

    private void shareWX() {
        if (Utils.isWeixinAvilible(mContext)) {
            Intent intentWeixin = new Intent();
            intentWeixin.putStringArrayListExtra(Key.KEY_SHARE_DATA.getValue(), shareData);
            intentWeixin.setClass(mContext, WeiXinShareActivity.class);
            mStartActivity(intentWeixin, RequestCode.REQUEST_CODE_WEIXIN_SHARE.getValue());
        } else {
            Utils.toastUtil.showToast(mContext, "请先安装微信客户端");
        }
    }

    private void shartWXCircle() {
        if (Utils.isWeixinAvilible(mContext)) {
            Intent intentWeixinCircle = new Intent();
            intentWeixinCircle.putStringArrayListExtra(Key.KEY_SHARE_DATA.getValue(), shareData);
            intentWeixinCircle.putExtra(Key.KEY_SCENE.getValue(), SendMessageToWX.Req
                    .WXSceneTimeline);
            intentWeixinCircle.setClass(mContext, WeiXinShareActivity.class);
            mStartActivity(intentWeixinCircle, RequestCode
                    .REQUEST_CODE_WEIXIN_CIRCLE_SHARE.getValue());
        } else {
            Utils.toastUtil.showToast(mContext, "请先安装微信客户端");
        }
    }

    private void mStartActivity(Intent intent, int requestCode) {
        if (lastActivity != null) {
            if (lastActivity instanceof Fragment) {
                ((Fragment) lastActivity).startActivityForResult(intent, requestCode);
            } else if (lastActivity instanceof Activity) {
                ((Activity) lastActivity).startActivityForResult(intent, requestCode);
            } else {
                startActivityForResult(intent, requestCode);
            }
        } else {
            startActivityForResult(intent, requestCode);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lastActivity = null;
    }
}
