package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.szy.common.Interface.SimpleImageLoadingListener;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Activity.OrderListFreeActivity;
import com.szy.yishopcustomer.Activity.ReachbuyActivity;
import com.szy.yishopcustomer.Activity.RootActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Result.Model;
import com.szy.yishopcustomer.ResponseModel.Result.OrderListModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import me.zongren.pullablelayout.Inteface.OnPullListener;
import me.zongren.pullablelayout.Main.PullableComponent;

/**
 * Created by 宗仁 on 16/8/1.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ResultFreeFragment extends YSCBaseFragment implements OnPullListener {

    @BindView(R.id.linearlayout_parent)
    View linearlayout_parent;
    @BindView(R.id.textView_tip)
    TextView textView_tip;
    @BindView(R.id.fragment_result_top_imageView)
    ImageView fragment_result_top_imageView;
    @BindView(R.id.fragment_result_top_resultTextView)
    TextView fragment_result_top_resultTextView;
    @BindView(R.id.textView_time)
    TextView textView_time;
    @BindView(R.id.textView_order_id)
    TextView textView_order_id;

    @BindView(R.id.linearlayout_order_info)
    View linearlayout_order_info;

    @BindView(R.id.imageView_order_sn)
    ImageView imageView_order_sn;

    @BindView(R.id.textView_amount)
    TextView textView_amount;

    @BindView(R.id.fragment_result_confirm_orderListButton)
    Button fragment_result_confirm_orderListButton;
    @BindView(R.id.fragment_result_save_picture)
    Button fragment_result_save_picture;

    @BindView(R.id.textView_reachbuy)
    TextView textView_reachbuy;
    @BindView(R.id.relativeLayout_free)
    View relativeLayout_free;

    private String orderId;
    private LinearLayoutManager mLayoutManager;
    private String mPaySuccess;
    private String payType;
    private String key;
    private String order_sn;
    private boolean exchange;

    private String rc_id = "";
    private String qrcode_image;

    @Override
    public void onCanceled(PullableComponent pullableComponent) {
    }

    @Override
    public void onLoading(PullableComponent pullableComponent) {
        refresh();
    }

    @Override
    public void onSizeChanged(PullableComponent pullableComponent, int mSize) {
    }

    public void openOrderListFreeActivity(String status) {
        Intent intent = new Intent(getContext(), OrderListFreeActivity.class);
        intent.putExtra(Key.KEY_ORDER_STATUS.getValue(), status);
        if(!TextUtils.isEmpty(rc_id)) {
            intent.putExtra(OrderListFreeFragment.PARAMS_TYPE,OrderListFreeFragment.TYPE_REACHBUY);
        }
        startActivity(intent);
        getActivity().finish();
    }


    @Override
    public void onClick(View view) {
        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_ORDER_GOODS:
                openOrderListFreeActivity(Macro.ORDER_TYPE_ALL);
                break;
            case VIEW_TYPE_CONTINUE_SHOPPING:
                if (isPay) {
                    //保存图片到相册
                    ImageLoader.loadImage(qrcode_image, new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                            super.onLoadingComplete(imageUri, view, loadedImage);
                            Utils.saveImageToGallery(getActivity(), loadedImage);
                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, String failReason) {
                            toast("下载图片失败！");
                        }
                    });
                } else {
                    openIndex();
                }

                break;
            default:
                super.onClick(view);
                break;
        }
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_RESULT:
                refreshCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_result_free;

        Bundle arguments = getArguments();
        if (arguments != null) {
            orderId = arguments.getString(Key.KEY_ORDER_ID.getValue());
            mPaySuccess = arguments.getString(Key.KEY_ORDER_PAY_SUCCESS.getValue());
            payType = arguments.getString(Key.KEY_ORDER_PAY_TYPE.getValue());
            order_sn = arguments.getString(Key.KEY_ORDER_SN.getValue());
            key = arguments.getString(Key.KEY_APP_KEY.getValue());
            exchange = arguments.getBoolean(Key.KEY_EXCHANGE.getValue());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        Utils.setViewTypeForTag(fragment_result_confirm_orderListButton, ViewType
                .VIEW_TYPE_ORDER_GOODS);
        fragment_result_confirm_orderListButton.setOnClickListener(this);

        Utils.setViewTypeForTag(fragment_result_save_picture, ViewType
                .VIEW_TYPE_CONTINUE_SHOPPING);
        fragment_result_save_picture.setOnClickListener(this);

        refresh();
        return view;
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(getApi(), HttpWhat.HTTP_RESULT.getValue());

        //从结算页支付跳到支付结果需要以下两个参数
        if (Utils.isNull(orderId)) {
            request.add("order_sn", order_sn);
            if (!Utils.isNull(key)) {
                request.add("key", key);
            }
        }

        request.add("is_success", mPaySuccess);
        addRequest(request);
    }

    boolean isPay = true;

    private void refreshCallback(String response) {
        HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model model) {

                rc_id = model.data.order.rc_id;

                linearlayout_parent.setVisibility(View.VISIBLE);

                textView_amount.setText(model.data.order.order_amount_format);

                if (TextUtils.isEmpty(rc_id)) {

                    textView_reachbuy.setVisibility(View.GONE);
                    relativeLayout_free.setVisibility(View.VISIBLE);

                    if (model.data.order.is_pay == Macro.PS_PAID) {
                        isPay = true;

                        fragment_result_save_picture.setText("保存图片");
                        linearlayout_order_info.setVisibility(View.VISIBLE);
                        fragment_result_top_imageView.setImageResource(R.mipmap.ic_free_payment_success);
                        fragment_result_top_resultTextView.setText("支付成功!");

                        textView_tip.setText(Html.fromHtml("请前往<font color='#f23030'>\"自由购买单（免排队）核验通道\"</font>收银台。出示二维码交由工作人员验证"));

                        OrderListModel temp = model.data.order_list.get(0);
                        if (temp != null) {
                            qrcode_image = temp.qrcode_image;

                            imageView_order_sn.setImageBitmap(CodeUtils.createImage(order_sn, 400, 400, null));
//                            ImageLoader.displayImage(Utils.urlOfImage(temp.qrcode_image), imageView_order_sn);
                            textView_order_id.setText(temp.order_sn);
                            textView_time.setText(Utils.times(temp.add_time));
                        }

                        fragment_result_confirm_orderListButton.setText("查询订单");
                        fragment_result_confirm_orderListButton.setOnClickListener(ResultFreeFragment.this);
                    } else {
                        isPay = false;
                        fragment_result_save_picture.setText("回首页");
                        linearlayout_order_info.setVisibility(View.GONE);
                        fragment_result_top_imageView.setImageResource(R.mipmap.ic_free_payment_failed);
                        fragment_result_top_resultTextView.setText(R.string.payResultFail);

                        textView_tip.setText(Html.fromHtml("请查看订单重新付款或返回首页"));

                        fragment_result_confirm_orderListButton.setText("点击付款");
                        fragment_result_confirm_orderListButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                openOrderListFreeActivity(Macro.ORDER_TYPE_UNPAID);
                            }
                        });

                    }
                } else {

                    textView_reachbuy.setVisibility(View.VISIBLE);
                    relativeLayout_free.setVisibility(View.GONE);

                    fragment_result_save_picture.setText("继续购物");
                    fragment_result_save_picture.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), ReachbuyActivity.class);
                            intent.putExtra(Key.KEY_SHOP_ID.getValue(),rc_id);
                            startActivity(intent);
                        }
                    });

                    if (model.data.order.is_pay == Macro.PS_PAID) {
                        isPay = true;

                        linearlayout_order_info.setVisibility(View.VISIBLE);
                        fragment_result_top_imageView.setImageResource(R.mipmap.ic_free_payment_success);
                        fragment_result_top_resultTextView.setText("支付成功!");

                        OrderListModel temp = model.data.order_list.get(0);
                        if (temp != null) {
                            qrcode_image = temp.qrcode_image;

                            ImageLoader.displayImage(Utils.urlOfImage(temp.qrcode_image), imageView_order_sn);
                            textView_order_id.setText(temp.order_sn);
                            textView_time.setText(Utils.times(temp.add_time));
                        }
                    } else {
                        isPay = false;
                        linearlayout_order_info.setVisibility(View.GONE);
                        fragment_result_top_imageView.setImageResource(R.mipmap.ic_free_payment_failed);
                        fragment_result_top_resultTextView.setText(R.string.payResultFail);
//
                    }
                }

//                mResultAdapter.data.clear();
//                mResultAdapter.data.add(model.data.order);
//                //mResultAdapter.data.addAll(model.data.order_list);
//                ConfirmModel confirmModel = new ConfirmModel();
//                confirmModel.payStatus = model.data.order.is_pay;
//                confirmModel.is_cod = model.data.order.is_cod;
//                confirmModel.pay_type = payType;
//                confirmModel.group_sn = model.data.group_sn;
//
//                mResultAdapter.data.add(confirmModel);
//                mResultAdapter.notifyDataSetChanged();
//                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_REFRESH_INVENTORY.getValue
//                        ()));
//                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_CHECKOUT_FINISH.getValue()));

            }
        });
    }

    private void bringRootToFront() {
        Intent intent = new Intent(getContext(), RootActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private String getApi() {
        if (orderId != null) {
            //return Api.API_RESULT_PAY_AGAIN + "?order_id=" + orderId;
            return Api.API_RESULT_PAY_AGAIN + "?id=" + orderId;
        } else {
            return Api.API_RESULT;
        }
    }

    private void openIndex() {
        bringRootToFront();
        CommonEvent event = new CommonEvent(EventWhat.EVENT_OPEN_INDEX.getValue(), this.getClass
                ().getSimpleName());
        EventBus.getDefault().post(event);
    }

}
