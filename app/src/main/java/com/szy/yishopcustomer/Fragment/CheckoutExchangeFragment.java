package com.szy.yishopcustomer.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Activity.AddressActivity;
import com.szy.yishopcustomer.Activity.AddressListActivity;
import com.szy.yishopcustomer.Activity.BestTimeActivity;
import com.szy.yishopcustomer.Activity.ResultActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AddressList.AddressItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.BestTimeModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.GoodsItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.Model;
import com.szy.yishopcustomer.ResponseModel.Checkout.OrderInfoModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.SendTimeItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.ShopItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.SubmitModel;
import com.szy.yishopcustomer.ResponseModel.CommonModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 积分兑换结算页.
 */
public class CheckoutExchangeFragment extends YSCBaseFragment {

    public static final String ORDER_TYPE = "order_type";

    //积分兑换
    public static final int TYPE_EXCHANGE = 0;
    //提货券兑换
    public static final int TYPE_GIFTCARD = 1;

    @BindView(R.id.fragment_checkout_empty_address_layout)
    View fragment_checkout_empty_address_layout;

    @BindView(R.id.fragment_checkout_address_layout)
    public RelativeLayout addressLayout;
    @BindView(R.id.fragment_checkout_address_consigneeTextView)
    public TextView consigneeTextView;
    @BindView(R.id.fragment_checkout_address_phoneTextView)
    public TextView phoneTextView;
    @BindView(R.id.fragment_checkout_address_consigneeAddressTextView)
    public TextView consigneeAddressTextView;
    @BindView(R.id.identity_verification_layout)
    public LinearLayout realNameLayout;
    @BindView(R.id.fragment_checkout_address_id_textView)
    public TextView idTextView;
    @BindView(R.id.fragment_checkout_address_name_textView)
    public TextView nameTextView;

    @BindView(R.id.item_order_list_goods_imageView)
    ImageView item_order_list_goods_imageView;
    @BindView(R.id.item_order_list_goods_name_textView)
    TextView item_order_list_goods_name_textView;
    @BindView(R.id.fragment_checkout_goods_numberTextView)
    TextView fragment_checkout_goods_numberTextView;
    @BindView(R.id.item_order_list_goods_attribute_textView)
    TextView item_order_list_goods_attribute_textView;
    @BindView(R.id.fragment_order_list_goods_number)
    TextView fragment_order_list_goods_number;
    @BindView(R.id.item_order_list_shop_name_textView)
    TextView item_order_list_shop_name_textView;

    @BindView(R.id.fragment_checkout_shop_order_labelTextView)
    TextView fragment_checkout_shop_order_labelTextView;

    @BindView(R.id.fragment_checkout_shop_order_totalPriceTextView)
    TextView fragment_checkout_shop_order_totalPriceTextView;

    @BindView(R.id.fragment_checkout_group_layout)
    View fragment_checkout_group_layout;
    @BindView(R.id.fragment_checkout_group_selectedItemTextView)
    TextView selectedItemTextView;
    @BindView(R.id.fragment_checkout_group_indicatorImageView)
    ImageView indicatorImageView;

    @BindView(R.id.fragment_checkout_submitOrderButton)
    Button fragment_checkout_submitOrderButton;

    @BindView(R.id.linearlayout_total_price)
    View linearlayout_total_price;

    @BindView(R.id.linearlayout_time_parent)
    LinearLayout linearlayout_time_parent;

    private boolean isExpanded = false;

    @BindView(R.id.edittext_leaving_message)
    EditText edittext_leaving_message;

    private Model mModel;
    private int selectedSendTimeId = 0;

    private int type = TYPE_EXCHANGE;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_checkout_exhchange;

        type = getActivity().getIntent().getIntExtra(ORDER_TYPE, TYPE_EXCHANGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        fragment_checkout_submitOrderButton.setOnClickListener(this);
        fragment_checkout_empty_address_layout.setOnClickListener(this);
        fragment_checkout_group_layout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_CHECKOUT, HttpWhat.HTTP_CHECKOUT
                .getValue());
        request.setAjax(true);
        addRequest(request);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_checkout_submitOrderButton:
                submitOrder();
                break;
            case R.id.fragment_checkout_empty_address_layout:
                openAddressActivity();
                break;
            case R.id.fragment_checkout_address_layout:
                openAddressListActivity(mModel.data.address_list);
                break;
            case R.id.fragment_checkout_group_layout:
                changeGroup();
                break;
        }
    }

    private void changeGroup() {
        if (isExpanded) {
            linearlayout_time_parent.setVisibility(View.VISIBLE);
            indicatorImageView.setImageResource(R.mipmap.btn_down_arrow);
        } else {
            linearlayout_time_parent.setVisibility(View.GONE);
            indicatorImageView.setImageResource(R.mipmap.btn_right_arrow);
        }
        isExpanded = !isExpanded;
    }

    private void submitOrder() {

        CommonRequest request = new CommonRequest(Api.API_CHECKOUT_SUBMIT, HttpWhat
                .HTTP_CHECKOUT_SUBMIT.getValue(), RequestMethod.POST);
        request.setAjax(true);

        //备注
        request.add("postscript", edittext_leaving_message.getText().toString().trim());
        addRequest(request);
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_CHECKOUT:
                refreshCallback(response);
                break;
//            case HTTP_CHANGE_PAYMENT:
//                changePaymentCallback(response);
//                break;
            case HTTP_CHECKOUT_SUBMIT:
                submitOrderCallback(response);
                break;
            case HTTP_CHANGE_BEST_TIME:
                changeBestTimeCallback(response);
                break;
//            case HTTP_CHANGE_INVOICE:
//                changeInvoiceCallback(response);
//                break;
            case HTTP_CHANGE_ADDRESS:
                changeAddressCallback(response);
                break;
//            case HTTP_PAYMENT:
//                HttpResultManager.resolve(response, ResponsePaymentModel.class, new
//                        HttpResultManager.HttpResultCallBack<ResponsePaymentModel>() {
//                            @Override
//                            public void onSuccess(ResponsePaymentModel back) {
//
//                            }
//                        });
//                getPaymentCallback(response);
//                //finish();
//                break;
//            case HTTP_CHANGE_PICKUP:
//                changeDeliveryModeCallback(response);
//                break;
//            default:
//                super.onRequestSucceed(what, response);
//                break;
        }
    }

    private void changeBestTimeCallback(String response) {
        HttpResultManager.resolve(response, CommonModel.class, new HttpResultManager
                .HttpResultCallBack<CommonModel>() {
            @Override
            public void onSuccess(CommonModel back) {
            }
        }, true);
    }

    /**
     * @param response
     */
    private void submitOrderCallback(String response) {
        HttpResultManager.resolve(response, SubmitModel.class, new HttpResultManager
                .HttpResultCallBack<SubmitModel>() {

            SubmitModel SubmitModel;

            @Override
            public void getObj(SubmitModel back) {
                super.getObj(back);
                SubmitModel = back;
            }

            @Override
            public void onSuccess(SubmitModel submitModel) {
                EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_CHECKOUT_SUCCEED
                        .getValue()));
                order_sn = submitModel.order_sn;
                goResult();
            }

            @Override
            public void ohter(int code, String message) {
                super.ohter(code, message);
                if (SubmitModel.code != 107 && SubmitModel.code != 106) {
                    order_sn = "";
                    goResult();
                }
            }
        });
    }


    private void refreshCallback(String response) {
        HttpResultManager.resolve(response, Model.class, new HttpResultManager
                .HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model back) {
                mModel = back;
                setUpAdapterData();
            }
        });
    }

    private void setUpAdapterData() {
        OrderInfoModel orderModel = mModel.data.cart_info.order;

        ShopItemModel shopItemModel = null;
        GoodsItemModel goodsItemModel = null;
        for (Map.Entry<String, ShopItemModel> entry : mModel.data.cart_info.shop_list.entrySet()) {
            shopItemModel = entry.getValue();
        }
        for (GoodsItemModel goodsItemModelEntry : shopItemModel
                .goods_list) {
            goodsItemModel = goodsItemModelEntry;
        }

        if (goodsItemModel != null) {
            //设置总金额

            switch (type) {
                case TYPE_EXCHANGE:
                    linearlayout_total_price.setVisibility(View.VISIBLE);
                    fragment_checkout_goods_numberTextView.setVisibility(View.VISIBLE);
                    fragment_checkout_shop_order_labelTextView.setText("合计：");
                    fragment_checkout_shop_order_totalPriceTextView.setText(goodsItemModel.points + "积分");
                    fragment_checkout_goods_numberTextView.setText(goodsItemModel.points + "积分");
                    fragment_checkout_submitOrderButton.setText("确认兑换");
                    break;
                case TYPE_GIFTCARD:
                    linearlayout_total_price.setVisibility(View.GONE);
                    fragment_checkout_goods_numberTextView.setVisibility(View.INVISIBLE);
                    fragment_checkout_submitOrderButton.setText("确认提货");
                    break;
            }


            item_order_list_shop_name_textView.setText(shopItemModel.shop_info.shop_name);
            ImageLoader.displayImage(Utils.urlOfImage(goodsItemModel.sku_image), item_order_list_goods_imageView);

            String specNames = "";
            if (goodsItemModel.spec_names != null) {
                for (String spec_name : goodsItemModel.spec_names) {
                    specNames += spec_name + " ";
                }
            }
            item_order_list_goods_attribute_textView.setText(specNames);
            item_order_list_goods_name_textView.setText(goodsItemModel.goods_name);
            fragment_order_list_goods_number.setText("x" + goodsItemModel.goods_number);
        }

        linearlayout_time_parent.removeAllViews();
        int position = 0;
        for (SendTimeItemModel sendTimeItemModel : mModel.data.send_time_list) {

            View v = LayoutInflater.from(getContext()).inflate(R.layout
                    .fragment_checkout_child, null);
            linearlayout_time_parent.addView(v);

            sendTimeItemModel.position = position++;
            v.setTag(R.id.fragment_checkout_child_layout, sendTimeItemModel);

            ImageView fragment_checkout_child_indicatorImageView = (ImageView) v.findViewById(R.id.image_checkbox);
            TextView fragment_checkout_child_titleTextView = (TextView) v.findViewById(R.id.fragment_checkout_child_titleTextView);

            fragment_checkout_child_titleTextView.setText(sendTimeItemModel.value);

            if (sendTimeItemModel.checked.equals("checked")) {
                if (!sendTimeItemModel.set_time) {
                    selectedItemTextView.setText(sendTimeItemModel.value);
                }
                fragment_checkout_child_indicatorImageView.setSelected(true);
            } else {
                fragment_checkout_child_indicatorImageView.setSelected(false);
            }

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SendTimeItemModel sendTimeItemModel = (SendTimeItemModel) view.getTag(R.id.fragment_checkout_child_layout);

                    selectedSendTimeId = sendTimeItemModel.position;
                    selectedItemTextView.setText(sendTimeItemModel.value);
                    if (!sendTimeItemModel.set_time) {
                        for (int i = 0; i < linearlayout_time_parent.getChildCount(); i++) {
                            View v = linearlayout_time_parent.getChildAt(i);
                            ImageView fragment_checkout_child_indicatorImageView = (ImageView) v.findViewById(R.id.image_checkbox);

                            fragment_checkout_child_indicatorImageView.setSelected(v.equals(view));
                        }

                        isExpanded = false;
                        changeGroup();
                    } else {
                        openBestTimeActivity(sendTimeItemModel.best_time);
                    }
                }
            });

        }

        if (isExpanded) {
            indicatorImageView.setImageResource(R.mipmap.btn_down_arrow);
        } else {
            indicatorImageView.setImageResource(R.mipmap.btn_right_arrow);
        }


        fragment_checkout_empty_address_layout.setVisibility(View.VISIBLE);
        addressLayout.setVisibility(View.GONE);
        if (mModel.data.address_list != null && mModel.data.address_list.size() >= 1) {

            AddressItemModel addressItemModel = mModel.data.address_list.get(0);

            for (AddressItemModel addressItemModel1 : mModel.data.address_list) {
                if (addressItemModel1.selected == 1) {
                    addressItemModel = addressItemModel1;
                }
            }

            if (TextUtils.isEmpty(addressItemModel.address_lat)) {
                showConfirmDialog(R.string.addressDialogLabel, ViewType
                        .VIEW_TYPE_ADDRESS_CHOICE.ordinal(), 0, Integer.valueOf
                        (addressItemModel.address_id));
            } else {

                if (addressItemModel.selected != 1) {
                    changeAddress(addressItemModel.address_id);
                }
                fragment_checkout_empty_address_layout.setVisibility(View.GONE);
                addressLayout.setVisibility(View.VISIBLE);


                consigneeTextView.setText(addressItemModel.consignee);
                phoneTextView.setText(addressItemModel.mobile_format);
                consigneeAddressTextView.setText(addressItemModel.region_name + "" + addressItemModel.address_detail);

                if (addressItemModel.is_real.equals("1")) {
                    realNameLayout.setVisibility(View.VISIBLE);
                    idTextView.setVisibility(View.VISIBLE);
                    idTextView.setText(addressItemModel.id_code_format);
                    nameTextView.setText(addressItemModel.real_name);
                } else {
                    realNameLayout.setVisibility(View.GONE);
                }

                addressLayout.setOnClickListener(this);

            }
        }

        if (mModel.data.send_time_list != null && mModel.data.send_time_list.size() > 0) {
            fragment_checkout_group_layout.setVisibility(View.VISIBLE);
        } else {
            fragment_checkout_group_layout.setVisibility(View.GONE);
        }
    }

    private void switchTime() {

    }

    private String order_sn;

    public void goResult() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ResultActivity.class);
        intent.putExtra(Key.KEY_ORDER_PAY_SUCCESS.getValue(), "1");
        intent.putExtra(Key.KEY_ORDER_PAY_TYPE.getValue(), "");
        intent.putExtra(Key.KEY_APP_KEY.getValue(), mModel.data.cart_info.key);
        intent.putExtra(Key.KEY_ORDER_SN.getValue(), order_sn);
        startActivity(intent);
        getActivity().finish();
    }


    @Override
    public void onConfirmDialogConfirmed(int viewType, int position, int extraInfo) {
        switch (ViewType.valueOf(viewType)) {
            case VIEW_TYPE_ADDRESS_CHOICE:
                Intent intent = new Intent(getContext(), AddressActivity.class);
                intent.putExtra(Key.KEY_ADDRESS_ID.getValue(), extraInfo + "");
                startActivity(intent);
                break;
        }
    }

    private void openAddressActivity() {
        Intent intent = new Intent(getContext(), AddressActivity.class);
        startActivity(intent);
    }


    private void openAddressListActivity(List<AddressItemModel> addressList) {
        ArrayList<AddressItemModel> addressArrayList = new ArrayList<>(addressList);
        Intent intent = new Intent(getContext(), AddressListActivity.class);
        intent.putExtra(Key.KEY_ADDRESS_LIST_CURRENT_TYPE.getValue(), AddressListActivity
                .TYPE_SELECT);
        intent.putExtra(Key.KEY_ADDRESS_LIST_AVAILABLE_TYPE.getValue(), AddressListActivity
                .TYPE_SELECT | AddressListActivity.TYPE_EDIT);
        startActivityForResult(intent, RequestCode.REQUEST_CODE_ADDRESS.getValue());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (RequestCode.valueOf(requestCode)) {
            case REQUEST_CODE_ADDRESS:
                if (resultCode == Activity.RESULT_OK) {
                    String addressId = data.getStringExtra(Key.KEY_ADDRESS_ID.getValue());
                    changeAddress(addressId);
                }
                break;
            case REQUEST_CODE_BEST_TIME:
                if (resultCode == Activity.RESULT_OK) {
                    String time = data.getStringExtra(Key.KEY_BEST_TIME.getValue());
                    changeBestTime("" + selectedSendTimeId, time);

                    selectedItemTextView.setText(time);


                    for (int i = 0; i < linearlayout_time_parent.getChildCount(); i++) {
                        View v = linearlayout_time_parent.getChildAt(i);
                        SendTimeItemModel sendTimeItemModel = mModel.data.send_time_list.get(i);
                        ImageView fragment_checkout_child_indicatorImageView = (ImageView) v.findViewById(R.id.image_checkbox);
                        if (sendTimeItemModel.set_time) {
                            fragment_checkout_child_indicatorImageView.setSelected(true);
                        } else {
                            fragment_checkout_child_indicatorImageView.setSelected(false);
                        }

                        isExpanded = false;
                        changeGroup();
                    }
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private void changeAddress(String addressId) {
        CommonRequest request = new CommonRequest(Api.API_CHECKOUT_CHANGE_ADDRESS, HttpWhat
                .HTTP_CHANGE_ADDRESS.getValue(), RequestMethod.POST);
        request.add("address_id", addressId);
        request.setAjax(true);
        addRequest(request);
    }


    private void changeAddressCallback(String response) {
        HttpResultManager.resolve(response, CommonModel.class, new HttpResultManager
                .HttpResultCallBack<CommonModel>() {
            @Override
            public void onSuccess(CommonModel back) {
                Toast.makeText(getContext(), R.string.operationSucceed, Toast.LENGTH_SHORT).show();
                refresh();
            }
        });
    }

    private void openBestTimeActivity(ArrayList<BestTimeModel> items) {
        Intent intent = new Intent(getContext(), BestTimeActivity.class);
        intent.putParcelableArrayListExtra(Key.KEY_BEST_TIME.getValue(), items);
        getActivity().overridePendingTransition(R.anim.slide_from_bottom_in, R.anim
                .slide_from_bottom_out);
        startActivityForResult(intent, RequestCode.REQUEST_CODE_BEST_TIME.getValue());
    }

    private void changeBestTime(String sendTimeId, String sendTime) {
        CommonRequest request = new CommonRequest(Api.API_CHANGE_BEST_TIME, HttpWhat
                .HTTP_CHANGE_BEST_TIME.getValue(), RequestMethod.POST);
        request.setAjax(true);
        request.add("send_time_id", sendTimeId);
        request.add("send_time", sendTime);
        addRequest(request);
    }
}
