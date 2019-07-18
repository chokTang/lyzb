package com.szy.yishopcustomer.Fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Activity.AddressActivity;
import com.szy.yishopcustomer.Activity.AddressListActivity;
import com.szy.yishopcustomer.Activity.BestTimeActivity;
import com.szy.yishopcustomer.Activity.PickUpActivity;
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
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutExchangeModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.SendTimeItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.SubmitModel;
import com.szy.yishopcustomer.ResponseModel.CommonModel;
import com.szy.yishopcustomer.ResponseModel.Goods.PickUpModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckoutIntegralFragment extends YSCBaseFragment {

    public static final String ORDER_TYPE = "order_type";


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
    @BindView(R.id.fragment_order_list_goods_number)
    TextView fragment_order_list_goods_number;

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

    @BindView(R.id.linearlayout_since_some)
    View linearlayout_since_some;
    @BindView(R.id.textView_since_some_tip)
    TextView textView_since_some_tip;
    @BindView(R.id.textView_since_some_modify)
    TextView textView_since_some_modify;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    private CheckoutExchangeModel mModel;
    List<CheckoutExchangeModel.DataBean.CartInfoBean.ShippingListBean> shipping_list;

    private int selectedSendTimeId = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_checkout_integral;
    }

    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

            for (int j = 0; j < shipping_list.size(); j++) {
                if (i == j) {
                    shipping_list.get(j).selected = true;
                } else {
                    shipping_list.get(j).selected = false;
                }
            }

            swtichCheck(shipping_list.get(i));

            if ("1".equals(shipping_list.get(i).id)) {
                if (TextUtils.isEmpty(shipping_list.get(i).pickup_name)) {
                    openPickUpActivity(i, "");
                } else {
                    changeShipping(shipping_list.get(i).pickup_id);
                }
            } else {
                changeShipping(shipping_list.get(i).pickup_id);
            }
        }
    };

    public void checkShipping(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        fragment_checkout_submitOrderButton.setOnClickListener(this);
        fragment_checkout_empty_address_layout.setOnClickListener(this);
        fragment_checkout_group_layout.setOnClickListener(this);

        refresh();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_INTEGRAL_CHECKOUT, HttpWhat.HTTP_CHECKOUT
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
            default:
                ViewType viewType = Utils.getViewTypeOfTag(view);
                int position = Utils.getPositionOfTag(view);
                switch (viewType) {
                    case VIEW_TYPE_ADDRESS:
                        openAddressActivity();
                        break;
                    case VIEW_TYPE_ADDRESS_CHOICE:
                        openAddressListActivity(mModel.data.address_list);
                        break;
                    case VIEW_TYPE_PICK_UP:
                        CheckoutExchangeModel.DataBean.CartInfoBean.ShippingListBean shipItemModel = (CheckoutExchangeModel.DataBean.CartInfoBean.ShippingListBean) view.getTag(R.id
                                .textView_since_some_modify);
                        openPickUpActivity((Integer) view.getTag(), shipItemModel.pickup_id);
                        break;
                    default:
                        super.onClick(view);
                }
                break;

        }
    }

    public void openPickUpActivity(int listPostion, String pickid) {
        tempPosition = listPostion;

        Intent intent = new Intent(getActivity(), PickUpActivity.class);
        intent.putExtra("pickid", pickid);
        intent.putExtra("listPostion", listPostion);
        intent.putParcelableArrayListExtra(Key.KEY_PICKUP_LIST.getValue(), shipping_list.get(listPostion).pickup_list);
        startActivityForResult(intent, RequestCode.REQUEST_PICK.getValue());
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

        CommonRequest request = new CommonRequest(Api.API_INTEGRAL_CHECKOUT_SUBMIT, HttpWhat
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
            case HTTP_CHECKOUT_SUBMIT:
                submitOrderCallback(response);
                break;
            case HTTP_CHANGE_BEST_TIME:
                changeBestTimeCallback(response);
                break;
            case HTTP_CHANGE_ADDRESS:
                changeAddressCallback(response);
                break;
            case HTTP_CHANGE_SHIPPING:
                changeShippingCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    /**
     * 切换配送方式
     *
     * @param pickupid
     */
    private void changeShipping(String pickupid) {

        CommonRequest request = new CommonRequest(Api.API_INTEGRAL_CHANGE_SHIPPING, HttpWhat
                .HTTP_CHANGE_SHIPPING.getValue(), RequestMethod.POST);
        request.add("pickup_id", pickupid);
        request.setAjax(true);
        addRequest(request);
    }


    private void changeBestTimeCallback(String response) {
        HttpResultManager.resolve(response, CommonModel.class, new HttpResultManager
                .HttpResultCallBack<CommonModel>() {
            @Override
            public void onSuccess(CommonModel back) {
            }
        }, true);
    }

    private void changeShippingCallback(String response) {
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
        HttpResultManager.resolve(response, CheckoutExchangeModel.class, new HttpResultManager
                .HttpResultCallBack<CheckoutExchangeModel>() {
            @Override
            public void onSuccess(CheckoutExchangeModel back) {
                mModel = back;
                setUpAdapterData();
            }
        });
    }

    public void swtichCheck(CheckoutExchangeModel.DataBean.CartInfoBean.ShippingListBean shipItemModel) {
        if ("1".equals(shipItemModel.id)) {
            if (!TextUtils.isEmpty(shipItemModel.pickup_name) && !"0".equals(shipItemModel.pickup_id)) {
                ArrayList<PickUpModel> arrayList = shipItemModel.pickup_list;
                for (int i = 0; i < arrayList.size(); i++) {
                    if (arrayList.get(i).pickup_id.equals(shipItemModel.pickup_id)) {
                        shipItemModel.pickup_name = arrayList.get(i).pickup_name;
                    }
                }
            }

            if (!TextUtils.isEmpty(shipItemModel.pickup_name)) {
                textView_since_some_tip.setText("自提地点：" + shipItemModel.pickup_name);

                textView_since_some_modify.setTag(R.id.textView_since_some_modify, shipItemModel);
                textView_since_some_modify.setTag(radioGroup.getCheckedRadioButtonId());
                Utils.setViewTypeForTag(textView_since_some_modify, ViewType.VIEW_TYPE_PICK_UP);
                textView_since_some_modify.setOnClickListener(this);

                textView_since_some_modify.setVisibility(View.VISIBLE);
            }

        } else {
            if ("0".equals(shipItemModel.price)) {
                textView_since_some_tip.setText("免运费");
            } else {
                textView_since_some_tip.setText(shipItemModel.name + shipItemModel.price);
            }
            textView_since_some_modify.setVisibility(View.GONE);
        }
    }


    private void setUpAdapterData() {
        Context context = getContext();
        CheckoutExchangeModel.DataBean.CartInfoBean.GoodsListBean goodsItemModel = null;
        List<CheckoutExchangeModel.DataBean.CartInfoBean.GoodsListBean> goods_list = mModel.data.cart_info.goods_list;
        if (!Utils.isNull(goods_list)) {
            goodsItemModel = goods_list.get(0);
        }

        shipping_list = mModel.data.cart_info.shipping_list;
        radioGroup.removeAllViews();
        radioGroup.setOnCheckedChangeListener(null);
        for (int i = 0, len = shipping_list.size(); i < len; i++) {
            RadioButton rb = (RadioButton) LayoutInflater.from(context).inflate(R.layout.fragment_checkout_child_two_item, null);
            rb.setText(shipping_list.get(i).name);
            rb.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
            rb.setId(i);

            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);

            if (i == 0) {
                layoutParams.setMargins(0, Utils.dpToPx(context, 5), Utils.dpToPx(context, 10), Utils.dpToPx(context, 5));
            } else {
                layoutParams.setMargins(Utils.dpToPx(context, 10), Utils.dpToPx(context, 5), 0, Utils.dpToPx(context, 5));
            }
            if (shipping_list.get(i).selected) {
                swtichCheck(shipping_list.get(i));
            }
            rb.setChecked(shipping_list.get(i).selected);
            rb.setLayoutParams(layoutParams);
            radioGroup.addView(rb);
        }


        radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);

        if (goodsItemModel != null) {
            //设置总金额

            linearlayout_total_price.setVisibility(View.VISIBLE);
            fragment_checkout_goods_numberTextView.setVisibility(View.VISIBLE);
            fragment_checkout_shop_order_labelTextView.setText("消费积分：");
            fragment_checkout_shop_order_totalPriceTextView.setText(goodsItemModel.goods_integral + "积分");
            fragment_checkout_goods_numberTextView.setText(goodsItemModel.goods_integral + "积分");
            fragment_checkout_submitOrderButton.setText("确认兑换");

            ImageLoader.displayImage(Utils.urlOfImage(goodsItemModel.goods_image), item_order_list_goods_imageView);
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
        intent.putExtra(Key.KEY_EXCHANGE.getValue(), true);
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


    private int tempPosition = -1;

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
            case REQUEST_PICK:
                if (data != null && resultCode == Activity.RESULT_OK) {
                    int listPostion = data.getIntExtra("listPostion", -1);
                    //获取点击的代收地址
                    int itemPosition = data.getIntExtra(PickUpFragment.ITEM_POSITION, 0);

                    if (!Utils.isNull(shipping_list)) {
                        PickUpModel tPickUpModel = shipping_list.get(listPostion).pickup_list.get(itemPosition);
                        shipping_list.get(listPostion).pickup_name = tPickUpModel.pickup_name;
                        shipping_list.get(listPostion).pickup_id = tPickUpModel.pickup_id;
                        swtichCheck(shipping_list.get(listPostion));
                        changeShipping(tPickUpModel.pickup_id);
                    }
                } else {
                    if (!Utils.isNull(shipping_list)) {
                        //取消的配送方式选第一个
                        if (tempPosition >= 0) {
                            if (!TextUtils.isEmpty(shipping_list.get(tempPosition).pickup_name)) {
                                return;
                            }
                        }

//                        radioGroup.check(0);
                        ((RadioButton)radioGroup.getChildAt(0)).toggle();

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
        CommonRequest request = new CommonRequest(Api.API_INTEGRAL_CHANGE_CHANGE_BEST_TIME, HttpWhat
                .HTTP_CHANGE_BEST_TIME.getValue(), RequestMethod.POST);
        request.setAjax(true);
        request.add("send_time_id", sendTimeId);
        request.add("send_time", sendTime);
        addRequest(request);
    }
}
