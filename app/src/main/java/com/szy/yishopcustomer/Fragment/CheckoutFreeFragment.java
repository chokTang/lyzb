package com.szy.yishopcustomer.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.szy.common.Adapter.TextWatcherAdapter;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.AddressActivity;
import com.szy.yishopcustomer.Activity.AddressListActivity;
import com.szy.yishopcustomer.Activity.BestTimeActivity;
import com.szy.yishopcustomer.Activity.InvoiceGoodsActivity;
import com.szy.yishopcustomer.Activity.InvoiceInfoActivity;
import com.szy.yishopcustomer.Activity.PickUpActivity;
import com.szy.yishopcustomer.Activity.ResultFreeActivity;
import com.szy.yishopcustomer.Adapter.CheckoutAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.Macro;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AddressList.AddressItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.BalanceInputModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.BestTimeModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.BonusItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.ChangePaymentModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.GoodsItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.GroupModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.InvoiceGoodsModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.InvoiceItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.InvoiceResultModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.Model;
import com.szy.yishopcustomer.ResponseModel.Checkout.OrderInfoModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.PayItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.PlatformBonusItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.PostscriptModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.SendTimeItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.ShipItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.ShopItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.ShopOrderModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.StoreCardModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.SubmitModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.UserInfoModel;
import com.szy.yishopcustomer.ResponseModel.CommonModel;
import com.szy.yishopcustomer.ResponseModel.Goods.PickUpModel;
import com.szy.yishopcustomer.ResponseModel.Payment.ResponsePaymentModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Checkout.ChildViewHolderTwo;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

//import com.szy.common.View.CustomProgressDialog;

/**
 * 自由购结算页
 */
public class CheckoutFreeFragment extends CommonPayFragment implements TextView
        .OnEditorActionListener, TextWatcherAdapter.TextWatcherListener {
    private static final String TAG = "CheckoutFragment";

    @BindView(R.id.fragment_checkout_recyclerView)
    CommonRecyclerView mRecyclerView;
    @BindView(R.id.fragment_checkout_totalPriceTextView)
    TextView mTotalPriceTextView;
    @BindView(R.id.fragment_checkout_submitOrderButton)
    View mSubmitOrderButton;

    private Model mModel;
    private BalanceInputModel balanceInputModel;
    private UserInfoModel userInfoModel;
    private boolean balancePasswordEnalbed;
    private String balance_password;
    private LinearLayoutManager mLayoutManager;
    private CheckoutAdapter mCheckoutAdapter;
    private int selectedSendTimeId;

    private GroupModel mSendTimeGroupModel;
    private GroupModel mInvoiceGroupModel;
    private OrderInfoModel orderInfoModel;
    private List<PayItemModel> payItemModel;
    private String order_sn;
    private int order_type;
    private String group_sn;
    private String payCode;

    private ArrayList<PickUpModel> mPickUpList;

    private String rc_id;
    private String shop_id;

    @Override
    public String getPayType() {
        if (order_type == Macro.OT_FIGHT_GROUP) {
            return Macro.PAY_TYPE_GROUPON;
        } else {
            return Macro.PAY_TYPE_CHECKOUT;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (RequestCode.valueOf(requestCode)) {
            case REQUEST_CODE_BEST_TIME:
                if (resultCode == Activity.RESULT_OK) {
                    String time = data.getStringExtra(Key.KEY_BEST_TIME.getValue());
                    changeBestTime("" + selectedSendTimeId, time);
                    GroupModel groupModel = mSendTimeGroupModel;
                    groupModel.selectedItem = time;
                    for (int i = 0; i < groupModel.items.size(); i++) {
                        SendTimeItemModel sendTimeItemModel = (SendTimeItemModel) groupModel
                                .items.get(i);
                        if (i == selectedSendTimeId) {
                            sendTimeItemModel.checked = "checked";
                        } else {
                            sendTimeItemModel.checked = "";
                        }
                    }
                    mCheckoutAdapter.notifyDataSetChanged();
                }
                break;
            case REQUEST_CODE_INVOICE_INFO:
                if (resultCode == Activity.RESULT_OK) {
                    InvoiceResultModel result = data.getParcelableExtra(Key.KEY_INVOICE_INFO
                            .getValue());
                    changeInvoice(result);
                }
                break;
            case REQUEST_CODE_ADDRESS:
                if (resultCode == Activity.RESULT_OK) {
                    String addressId = data.getStringExtra(Key.KEY_ADDRESS_ID.getValue());
                    changeAddress(addressId);
                }
                break;
            case REQUEST_PICK:
                if (data != null && resultCode == Activity.RESULT_OK) {
                    int position = data.getIntExtra("position", -1);
                    int listPostion = data.getIntExtra("listPostion", -1);
                    //获取点击的代收地址
                    int itemPosition = data.getIntExtra(PickUpFragment.ITEM_POSITION, 0);
                    PickUpModel tPickUpModel = mPickUpList.get(itemPosition);

                    if (position >= 0) {
                        List<ShipItemModel> tempList = (List<ShipItemModel>) mCheckoutAdapter.data.get(position);
                        tempList.get(listPostion).pickup_name = tPickUpModel.pickup_name;
                        tempList.get(listPostion).pickup_id = tPickUpModel.pickup_id;
                        changeDeliveryMode(tempList.get(listPostion));
                    }

                    mCheckoutAdapter.notifyDataSetChanged();
                } else {
                    //取消的配送方式选第一个
                    if (tempPosition >= 0) {
                        List<ShipItemModel> tempList = (List<ShipItemModel>) mCheckoutAdapter.data.get(tempPosition);
                        if (templistPostion >= 0) {
                            if (!TextUtils.isEmpty(tempList.get(templistPostion).pickup_name)) {
                                return;
                            }
                        }

                        for (int i = 0; i < tempList.size(); i++) {
                            if (i == 0) {
                                tempList.get(i).isCheck = true;
//                                changeDeliveryMode(tempList.get(i));
                            } else {
                                tempList.get(i).isCheck = false;
                            }
                        }
                    }

                    mCheckoutAdapter.notifyDataSetChanged();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultClass = ResultFreeActivity.class;

        rc_id = getActivity().getIntent().getStringExtra("rc_id");
        shop_id = getActivity().getIntent().getStringExtra(Key.KEY_SHOP_ID.getValue());

        if (!TextUtils.isEmpty(rc_id)) {
            getActivity().setTitle("确认订单-堂内点餐");
        }

        mLayoutId = R.layout.fragment_checkout;
        mCheckoutAdapter = new CheckoutAdapter(getActivity());
        mCheckoutAdapter.onClickListener = this;
        mCheckoutAdapter.onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                ChildViewHolderTwo viewHolder = (ChildViewHolderTwo) radioGroup.getTag();
                int position = Utils.getPositionOfTag(radioGroup);
                List<ShipItemModel> tempList = (List<ShipItemModel>) mCheckoutAdapter.data.get(position);
                if ("1".equals(tempList.get(i).id)) {

                    mCheckoutAdapter.notifyItemRangeChanged(mCheckoutAdapter.data.size() - 5, 4);

                    if (TextUtils.isEmpty(tempList.get(i).pickup_name)) {
                        openPickUpActivity(position, i, "");
                    } else {
                        changeDeliveryMode(tempList.get(i));
                    }
                } else {
                    changeDeliveryMode(tempList.get(i));
                }

                //调用
//                mCheckoutAdapter.notifyDataSetChanged();
            }
        };
        mCheckoutAdapter.onTextChangeListener = this;
        mCheckoutAdapter.onEditorActionListener = this;
    }

    private int tempPosition = -1;
    private int templistPostion = -1;


    public void openPickUpActivity(int postion, int listPostion, String pickid) {
        tempPosition = postion;
        templistPostion = listPostion;

        Intent intent = new Intent(getActivity(), PickUpActivity.class);
        intent.putExtra("pickid", pickid);
        intent.putExtra("position", postion);
        intent.putExtra("listPostion", listPostion);
        intent.putParcelableArrayListExtra(Key.KEY_PICKUP_LIST.getValue(), mPickUpList);
        startActivityForResult(intent, RequestCode.REQUEST_PICK.getValue());
    }

    @Override
    public void onEvent(CommonEvent event) {
        switch (EventWhat.valueOf(event.getWhat())) {
            case EVENT_ADD_ADDRESS:
            case EVENT_DELETE_ADDRESS:
            case EVENT_UPDATE_ADDRESS:
                refresh();
                break;
            case EVENT_CHECKOUT_FINISH:
                finish();
                break;
            default:
                super.onEvent(event);
                break;
        }
    }

    public void goResult() {
        goResult("1");
    }

    public void goResult(String success) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ResultFreeActivity.class);
//        intent.putExtra(Key.KEY_ORDER_PAY_SUCCESS.getValue(), success);
        intent.putExtra(Key.KEY_ORDER_PAY_TYPE.getValue(), getPayType());
        intent.putExtra(Key.KEY_APP_KEY.getValue(), mModel.data.cart_info.key);
        intent.putExtra(Key.KEY_ORDER_SN.getValue(), order_sn);
        startActivity(intent);
        getActivity().finish();

    }

    @Override
    public void onClick(View v) {
        ViewType viewType = Utils.getViewTypeOfTag(v);
        int position = Utils.getPositionOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_ADDRESS:
                openAddressActivity();
                break;
            case VIEW_TYPE_ADDRESS_CHOICE:
                openAddressListActivity(mModel.data.address_list);
                break;
            case VIEW_TYPE_GROUP:
                groupClicked(position);
                break;
            case VIEW_TYPE_SHIP_ITEM:
                shipItemClicked(position);
                break;
            case VIEW_TYPE_SEND_TIME_ITEM:
                sendTimeItemClicked(position);
                break;
            case VIEW_TYPE_BONUS:
                bonusItemClicked(position);
                break;
            case VIEW_TYPE_STORE_CARD_ITEM:
                storeCardClicked(position);
                break;
            case VIEW_TYPE_PLATFORM_BONUS:
                platformBonusItemClicked(position);
                break;
            case VIEW_TYPE_SWITCH:
                balanceSwitchClicked(position);
                break;
            case VIEW_TYPE_INVOICE:
                openInvoiceActivity(mModel.data.invoice_info);
                break;
            case VIEW_TYPE_INVOICE_GOODS:
                ArrayList<InvoiceGoodsModel> goodsList = mModel.data.invoice_info.get(0).goods_list;
                openInvoiceGoodsActivity(goodsList);
                break;
            case VIEW_TYPE_CONFIRM:
                mSubmitOrderButton.setEnabled(false);
                orderConfirmClicked(v);
                break;
            case VIEW_TYPE_PAYMENT_ITEM:
                paymentClicked(position);
                break;
            case VIEW_TYPE_PICK_UP:
                ShipItemModel shipItemModel = (ShipItemModel) v.getTag(R.id.textView_since_some_modify);
                openPickUpActivity(position, (Integer) v.getTag(), shipItemModel.pickup_id);
                break;
            default:
                super.onClick(v);
        }
    }


    private void storeCardClicked(int position) {
        int groupPosition = mCheckoutAdapter.findGroupModelBefore(position);
        GroupModel groupModel = (GroupModel) mCheckoutAdapter.data.get(groupPosition);
        for (int i = 0; i < groupModel.items.size(); i++) {
            StoreCardModel storeCardModel = (StoreCardModel) groupModel.items.get(i);
            if (i == (position - groupPosition - 1)) {
                storeCardModel.selected = "selected";
                groupModel.selectedItem = storeCardModel.type_name;
                changeStoreCard(storeCardModel.shop_id, storeCardModel.card_id, groupPosition);
            } else {
                storeCardModel.selected = "";
            }
        }
        groupClicked(mCheckoutAdapter.findGroupModelBefore(groupPosition));
    }

    private void changeStoreCard(String shopId, String cardId, int groupPosition) {
        CommonRequest request = new CommonRequest(Api.API_CHANGE_PAYMENT, HttpWhat
                .HTTP_CHANGE_PAYMENT.getValue(), RequestMethod.POST);
        request.add("integral_enable", 0);
        request.add("balance", getBalance());
        request.add("balance_enable", isBalanceEnabled());
        request.add("store_card_list[0][shop_id]", shopId);
        if (TextUtils.isEmpty(cardId)) {
            request.add("store_card_list[0][card_id]", "");
        } else {
            request.add("store_card_list[0][card_id]", cardId);
        }

        request.setAjax(true);
        addRequest(request);
    }


    int i = 0;

    @Override
    public void onResume() {
        super.onResume();
        //调用支付的时候，会直接触发onResume,
        if (isRefresh && ++i > 1) {
            isRefresh = !isRefresh;
            i = 0;
            goResult("0");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mCheckoutAdapter);

        Utils.setViewTypeForTag(mSubmitOrderButton, ViewType.VIEW_TYPE_CONFIRM);
        mSubmitOrderButton.setOnClickListener(this);

        refresh();
        return view;
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_CHECKOUT:
                refreshCallback(response);
                break;
            case HTTP_CHANGE_PAYMENT:
                changePaymentCallback(response);
                break;
            case HTTP_CHECKOUT_SUBMIT:
                submitOrderCallback(response);
                break;
            case HTTP_CHANGE_BEST_TIME:
                changeBestTimeCallback(response);
                break;
            case HTTP_CHANGE_INVOICE:
                changeInvoiceCallback(response);
                break;
            case HTTP_CHANGE_ADDRESS:
                changeAddressCallback(response);
                break;
            case HTTP_PAYMENT:
                HttpResultManager.resolve(response, ResponsePaymentModel.class, new
                        HttpResultManager.HttpResultCallBack<ResponsePaymentModel>() {
                            @Override
                            public void onSuccess(ResponsePaymentModel back) {

                            }
                        });
                getPaymentCallback(response);
                //finish();
                break;
            case HTTP_CHANGE_PICKUP:
                changeDeliveryModeCallback(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        switch (view.getId()) {
            case R.id.fragment_checkout_balance_input_balanceEditText:
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    setBalance(view.getText().toString());
                    changeBalance(isBalanceEnabled(), getBalance());
                    hideKeyboard(view);
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }

    @Override
    public void onTextChanged(EditText view, String text) {
        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_POSTSCRIPT:
                PostscriptModel postscriptModel = (PostscriptModel) mCheckoutAdapter.data.get
                        (position);
                postscriptModel.content = text;
                break;
            case VIEW_TYPE_CHECKOUT:
                setBalance(view.getText().toString());
                break;
        }
    }

    /**
     *
     */
    private void addPayments() {
        if (balanceInputModel == null) {
            balanceInputModel = mModel.data.cart_info.user_info.balanceInputModel;
        }

        mCheckoutAdapter.data.add(balanceInputModel);

        //取出支付方式,不包含货到付款
        payItemModel = mModel.data.pay_list;
        int flag = 0;
        for (PayItemModel payItemModel : mModel.data.pay_list) {
            if (payItemModel.code.equals(Macro.COD_CODE)) {
                if (mModel.data.cart_info.order.is_cash) {
                    payItemModel.label = "加价" + mModel.data.cart_info.order.cash_more_format;
                    mCheckoutAdapter.data.add(payItemModel);
                }
            } else if (payItemModel.code.equals(Macro.TO_PAY_CODE)) {

            } else {
                payItemModel.label = null;
                mCheckoutAdapter.data.add(payItemModel);
            }
        }

        for (PayItemModel payItemModel : mModel.data.pay_list) {
            //和pc端相同，默认选中一个支付方式
            if (payItemModel.checked.equals("checked")) {
                changePayment(payItemModel.code);
            }

            /*//如果有微信支付默认选中微信支付
            if (flag == 1) {
                if (payItemModel.code.equals("app_weixin")) {
                    payItemModel.checked = "checked";
                    changePayment(payItemModel.code);
                }
            } else {
                if (payItemModel.code.equals(Macro.COD_CODE)) {
                    //货到付款不会默认选中
                } else {
                    //默认选中除了货到付款之外的第一个支付方式
                    if (flag > 1) {
                        return;
                    } else {
                        payItemModel.checked = "checked";
                        changePayment(payItemModel.code);
                        flag++;
                    }
                }

            }*/
        }
    }

    private void balanceSwitchClicked(int position) {
        UserInfoModel userInfoModel = (UserInfoModel) mCheckoutAdapter.data.get(position);
        if (userInfoModel.balancedEnabled) {
            /*mCheckoutAdapter.data.remove(balanceInputModel);
            mCheckoutAdapter.notifyItemRangeRemoved(position + 1, 1);

            balanceInputModel = null;*/
            changeBalance(0, "");
            userInfoModel.balancedEnabled = false;
        } else {
            /*if (balanceInputModel == null) {
                balanceInputModel = mModel.data.cart_info.user_info.balanceInputModel;
            }
            mCheckoutAdapter.data.add(position + 1, balanceInputModel);
            mCheckoutAdapter.notifyItemRangeInserted(position + 1, 1);*/
            balanceInputModel = mModel.data.cart_info.user_info.balanceInputModel;

            changeBalance(1, "");
            userInfoModel.balancedEnabled = true;
        }
        mCheckoutAdapter.notifyDataSetChanged();
    }

    private void bonusItemClicked(int position) {
        int groupPosition = mCheckoutAdapter.findGroupModelBefore(position);
        GroupModel groupModel = (GroupModel) mCheckoutAdapter.data.get(groupPosition);
        for (int i = 0; i < groupModel.items.size(); i++) {
            BonusItemModel bonusItemModel = (BonusItemModel) groupModel.items.get(i);
            if (i == (position - groupPosition - 1)) {
                bonusItemModel.selected = "selected";
                groupModel.selectedItem = bonusItemModel.bonus_name;
                changeBonus(bonusItemModel.shop_id, bonusItemModel.id);
            } else {
                bonusItemModel.selected = "";
            }
        }
        groupClicked(mCheckoutAdapter.findGroupModelBefore(groupPosition));
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

    private void changeBalance(int enable, String balance) {
        CommonRequest request = new CommonRequest(Api.API_CHANGE_PAYMENT, HttpWhat
                .HTTP_CHANGE_PAYMENT.getValue(), RequestMethod.POST);
        request.add("integral_enable", 0);
        request.add("balance", balance);
        request.add("balance_enable", enable);
        request.setAjax(true);
        addRequest(request);
    }

    private void changeBestTime(String sendTimeId, String sendTime) {
        CommonRequest request = new CommonRequest(Api.API_CHANGE_BEST_TIME, HttpWhat
                .HTTP_CHANGE_BEST_TIME.getValue(), RequestMethod.POST);
        request.setAjax(true);
        request.add("send_time_id", sendTimeId);
        request.add("send_time", sendTime);
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

    private void changeBonus(String shopId, String bonusId) {
        CommonRequest request = new CommonRequest(Api.API_CHANGE_PAYMENT, HttpWhat
                .HTTP_CHANGE_PAYMENT.getValue(), RequestMethod.POST);
        request.add("integral_enable", 0);
        request.add("balance", getBalance());
        request.add("balance_enable", isBalanceEnabled());
        request.add("bonus_list[0][shop_id]", shopId);
        request.add("bonus_list[0][bonus_id]", bonusId);
        request.setAjax(true);
        addRequest(request);
    }

    private void changeInvoice(InvoiceResultModel result) {
        CommonRequest request = new CommonRequest(Api.API_CHANGE_INVOICE, HttpWhat
                .HTTP_CHANGE_INVOICE.getValue(), RequestMethod.POST);
        request.setAjax(true);
        if (result.inv_type == 0) {

        } else if (result.inv_type == 1) {
            Map<String, String> temp = JSON.parseObject(mModel.data.invoice_info.get(1).contents,
                    Map.class);
            temp.put("inv_title", result.inv_title);
            temp.put("inv_company", result.inv_company);
            temp.put("inv_content", result.inv_content + "");
            mModel.data.invoice_info.get(1).contents = JSON.toJSONString(temp);

            request.add("inv_type", result.inv_type);
            request.add("inv_name", result.inv_name);
            request.add("inv_title", result.inv_title);
            request.add("inv_company", result.inv_company);
            request.add("inv_content", result.inv_content);
        } else if (result.inv_type == 2) {
            List<Map<String, String>> list = JSON.parseObject(mModel.data.invoice_info.get(2)
                    .contents, List.class);

            setInvoice_info(list, "inv_company", result.inv_company);
            setInvoice_info(list, "inv_taxpayers", result.inv_taxpayers);
            setInvoice_info(list, "inv_address", result.inv_address);
            setInvoice_info(list, "inv_tel", result.inv_tel);
            setInvoice_info(list, "inv_account", result.inv_account);
            setInvoice_info(list, "inv_bank", result.inv_bank);

            mModel.data.invoice_info.get(2).contents = JSON.toJSONString(list);

            request.add("inv_type", result.inv_type);
            request.add("inv_name", result.inv_name);
            //request.add("inv_title", result.inv_title);
            request.add("inv_content", "明细");
            request.add("inv_company", result.inv_company);
            request.add("inv_taxpayers", result.inv_taxpayers);
            request.add("inv_account", result.inv_account);
            request.add("inv_bank", result.inv_bank);
            request.add("inv_address", result.inv_address);
            request.add("inv_tel", result.inv_tel);
        }
        addRequest(request);
    }

    public void setInvoice_info(List<Map<String, String>> list, String key, String value) {
        for (int i = 0, len = list.size(); i < len; i++) {
            Map temp = list.get(i);
            if (temp.get("name").equals(key)) {
                temp.put("value", value);
                return;
            }
        }
    }

    private void changeInvoiceCallback(String response) {
        HttpResultManager.resolve(response, CommonModel.class, new HttpResultManager
                .HttpResultCallBack<CommonModel>() {
            @Override
            public void onSuccess(CommonModel mModel) {
                mInvoiceGroupModel.selectedItem = mModel.data;
                mCheckoutAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), R.string.operationSucceed, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 切换配送方式
     *
     * @param result
     */
    private void changeDeliveryMode(ShipItemModel result) {
        CommonRequest request = new CommonRequest(Api.API_CHANGE_PICKUP, HttpWhat
                .HTTP_CHANGE_PICKUP.getValue(), RequestMethod.POST);
        request.setAjax(true);
        request.add("pickup_id", result.pickup_id);
        request.add("shop_id", result.shop_id);
        addRequest(request);

        changeShipment(result.shop_id, result.id, 0);
    }


    /**
     * 切换配送方式回掉
     *
     * @param response
     */
    private void changeDeliveryModeCallback(String response) {
        HttpResultManager.resolve(response, CommonModel.class, new HttpResultManager
                .HttpResultCallBack<CommonModel>() {
            @Override
            public void onSuccess(CommonModel mModel) {
            }
        }, true);
    }

    private void changePayment(String payCode) {
        CommonRequest request = new CommonRequest(Api.API_CHANGE_PAYMENT, HttpWhat
                .HTTP_CHANGE_PAYMENT.getValue(), RequestMethod.POST);
        request.add("integral_enable", 0);
        request.add("balance", getBalance());
        request.add("balance_enable", isBalanceEnabled());
        request.add("pay_code", payCode);
        request.setAjax(true);
        addRequest(request);
    }

    private void changePaymentCallback(String response) {
        HttpResultManager.resolve(response, ChangePaymentModel.class, new HttpResultManager
                .HttpResultCallBack<ChangePaymentModel>() {
            @Override
            public void onSuccess(ChangePaymentModel changePaymentModel) {
                if (!TextUtils.isEmpty(changePaymentModel.message)) {
                    Toast.makeText(getActivity(), changePaymentModel.message, Toast.LENGTH_SHORT)
                            .show();
                }

                payCode = changePaymentModel.order.pay_code;

                for (int i = 0; i < changePaymentModel.shop_orders.size(); i++) {
                    int j = 0;
                    for (Map.Entry<String, ShopItemModel> entry : mModel.data.cart_info.shop_list
                            .entrySet()) {
                        if (j == i) {
                            ShopOrderModel aa = changePaymentModel.shop_orders.get(i);
                            mModel.data.cart_info.shop_list.get(entry.getKey()).order
                                    .order_amount_format = aa.order_amount_format;
                        }
                        j++;
                    }
                }

//                for (Map.Entry<String, ShopOrderModel> iEntry : changePaymentModel.shop_orders.entrySet()) {
//                    for (Map.Entry<String, ShopItemModel> entry : mModel.data.cart_info.shop_list.entrySet()) {
//                        if (!TextUtils.isEmpty(changePaymentModel.shop_orders.get(iEntry.getKey()).shop_id)) {
//                            if (changePaymentModel.shop_orders.get(iEntry.getKey()).shop_id.equals(
//                                    mModel.data.cart_info.shop_list.get(entry.getKey()).order.shop_id
//                            )) {
//                                mModel.data.cart_info.shop_list.get(entry.getKey()).order
//                                        .order_amount_format = changePaymentModel.shop_orders.get(iEntry.getKey()).order_amount_format;
//                            }
//                        }
//                    }
//                }

                if (balanceInputModel != null) {
                    balanceInputModel.balance = changePaymentModel.order.balance;
                    balanceInputModel.balance_format = changePaymentModel.order.balance_format;
                    balanceInputModel.money_pay = changePaymentModel.order.money_pay;
                    balanceInputModel.money_pay_format = changePaymentModel.order.money_pay_format;
                }

                userInfoModel.balanceUsed = changePaymentModel.order.balance_format;

                if (Double.parseDouble(changePaymentModel.order.money_pay) == 0) {
                    if (!Utils.isNull(payItemModel)) {
                        removePayments();
                    }
                } else {

                    if (Utils.isNull(payItemModel)) {
                        addPayments();
                    }
                }
                //订单信息
                if (orderInfoModel == null) {
                    orderInfoModel = changePaymentModel.order;
                    orderInfoModel.is_syunfei = false;
                    for (int i = 0; i < mCheckoutAdapter.data.size(); i++) {
                        if (mCheckoutAdapter.data.get(i) instanceof UserInfoModel) {
                            mCheckoutAdapter.data.add(i - 1, orderInfoModel);
                            mCheckoutAdapter.data.add(i - 1, new CheckoutDividerModel());
                            mCheckoutAdapter.notifyItemRangeChanged(i - 1, mCheckoutAdapter.data.size() - 1);
//                            mCheckoutAdapter.notifyDataSetChanged();
                            return;
                        }
                    }
                } else {
                    for (int i = 0; i < mCheckoutAdapter.data.size(); i++) {
                        if (mCheckoutAdapter.data.get(i) instanceof OrderInfoModel) {
                            OrderInfoModel orderInfoModel = (OrderInfoModel) mCheckoutAdapter
                                    .data.get(i);
                            orderInfoModel.is_syunfei = false;
                            orderInfoModel.shipping_fee_format = changePaymentModel.order.shipping_fee_format;
                            orderInfoModel.cash_more = changePaymentModel.order.cash_more;
                            orderInfoModel.total_bonus_amount_format = changePaymentModel.order
                                    .total_bonus_amount_format;
                            orderInfoModel.balance_format = changePaymentModel.order.balance_format;
                            orderInfoModel.is_cod = changePaymentModel.order.is_cod;
                        }
                    }
                }

                mTotalPriceTextView.setText(changePaymentModel
                        .order.money_pay_format);
                mCheckoutAdapter.notifyDataSetChanged();

            }
        }, true);
    }

    private void changeShipment(String shopId, String shipId, int groupPosition) {
        CommonRequest request = new CommonRequest(Api.API_CHANGE_PAYMENT, HttpWhat
                .HTTP_CHANGE_PAYMENT.getValue(), RequestMethod.POST);
        request.add("integral_enable", 0);
        request.add("balance", getBalance());
        request.add("balance_enable", isBalanceEnabled());
        request.add("shipping_list[0][shop_id]", shopId);
        request.add("shipping_list[0][shipping_id]", shipId);
        request.setAjax(true);
        addRequest(request);
    }

    private String getBalance() {
        BalanceInputModel balanceInputModel = mModel.data.cart_info.user_info.balanceInputModel;
        if (!Utils.isNull(balanceInputModel)) {
            return balanceInputModel.balance;
        } else {
            return "0";
        }
    }

    public void setBalance(String balance) {
        balanceInputModel = mModel.data.cart_info.user_info.balanceInputModel;
        balanceInputModel.balance = balance;
    }

    private void groupClicked(int position) {
        GroupModel groupModel = (GroupModel) mCheckoutAdapter.data.get(position);

        groupClicked(groupModel);
    }

    private void groupClicked(GroupModel groupModel) {
        if (groupModel.isExpanded) {
            mCheckoutAdapter.data.removeAll(groupModel.items);
        } else {
            mCheckoutAdapter.data.addAll(mCheckoutAdapter.data.indexOf(groupModel) + 1,
                    groupModel.items);
        }
        groupModel.isExpanded = !groupModel.isExpanded;
        mCheckoutAdapter.notifyDataSetChanged();
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private int isBalanceEnabled() {
        UserInfoModel userInfoModel = mModel.data.cart_info.user_info;
        if (userInfoModel.balancedEnabled) {
            return 1;
        } else {
            return 0;
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

    private void openBestTimeActivity(ArrayList<BestTimeModel> items) {
        Intent intent = new Intent(getContext(), BestTimeActivity.class);
        intent.putParcelableArrayListExtra(Key.KEY_BEST_TIME.getValue(), items);
        getActivity().overridePendingTransition(R.anim.slide_from_bottom_in, R.anim
                .slide_from_bottom_out);
        startActivityForResult(intent, RequestCode.REQUEST_CODE_BEST_TIME.getValue());
    }

    private void openInputBalancePassword() {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        final View balancePasswordDialogView = layoutInflater.inflate(R.layout
                .dialog_input_balance_password, null);

        final AlertDialog mBalancePasswordDialog = new AlertDialog.Builder(getActivity()).setView
                (balancePasswordDialogView).create();

        TextView cancel = (TextView) balancePasswordDialogView.findViewById(R.id
                .dialog_balance_password_cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBalancePasswordDialog.dismiss();
            }
        });

        TextView confirm = (TextView) balancePasswordDialogView.findViewById(R.id
                .dialog_bonus_confirm_confirmButton);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText balancePasswordEditText = (EditText) balancePasswordDialogView
                        .findViewById(R.id.dialog_balance_password_editText);
                String balancePassword = balancePasswordEditText.getText().toString();
                if (Utils.isNull(balancePassword)) {
                    Utils.makeToast(getActivity(), getResources().getString(R.string
                            .hintPleaseEnterBalancePassword));
                } else {
                    balance_password = balancePassword;
                    submitOrder();
                }
            }
        });

        mBalancePasswordDialog.show();
        mBalancePasswordDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color
                .TRANSPARENT));
    }

    private void openInvoiceActivity(ArrayList<InvoiceItemModel> invoiceInfo) {
        Intent intent = new Intent(getContext(), InvoiceInfoActivity.class);
        intent.putParcelableArrayListExtra(Key.KEY_INVOICE_INFO.getValue(), invoiceInfo);
        startActivityForResult(intent, RequestCode.REQUEST_CODE_INVOICE_INFO.getValue());
    }

    private void openInvoiceGoodsActivity(ArrayList<InvoiceGoodsModel> invoiceInfoGoods) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), InvoiceGoodsActivity.class);
        intent.putParcelableArrayListExtra(Key.KEY_INVOICE_INFO_GOODS.getValue(), invoiceInfoGoods);
        startActivity(intent);
    }

    private void orderConfirmClicked(View v) {
        if (userInfoModel.balancedEnabled) {
            //已经去掉余额输入功能，隐藏此段代码
            /*if (Double.parseDouble(balanceInputModel.balance) < Double.parseDouble
                    (balanceInputModel.balance_format.substring(1))) {
                changeBalance(isBalanceEnabled(), getBalance());
                hideKeyboard(v);
                mSubmitOrderButton.setEnabled(true);
                return;
            } else {
                changeBalance(isBalanceEnabled(), getBalance());
                if (balancePasswordEnalbed) {
                    openInputBalancePassword();
                } else {
                    submitOrder();
                }
                mSubmitOrderButton.setEnabled(true);
                return;
            }*/
            changeBalance(isBalanceEnabled(), getBalance());
            if (balancePasswordEnalbed) {
                openInputBalancePassword();
            } else {
                submitOrder();
            }
            mSubmitOrderButton.setEnabled(true);
            return;

        } else {
            submitOrder();
            mSubmitOrderButton.setEnabled(true);
            return;
        }
    }

    private void paymentClicked(int position) {
        PayItemModel selectedPayItemModel = (PayItemModel) mCheckoutAdapter.data.get(position);
        for (PayItemModel item : mModel.data.pay_list) {
            if (item.code.equals(selectedPayItemModel.code)) {
                item.checked = "checked";
            } else {
                item.checked = "";
            }
        }
        mCheckoutAdapter.notifyDataSetChanged();
        changePayment(selectedPayItemModel.code);
    }

    private void platformBonusItemClicked(int position) {
        PlatformBonusItemModel platformBonusItemModel = (PlatformBonusItemModel) mCheckoutAdapter
                .data.get(position);

        int mPlatformBonusGroupPosition = mCheckoutAdapter.findGroupModelBefore(position);

        GroupModel groupModel = (GroupModel) mCheckoutAdapter.data.get(mPlatformBonusGroupPosition);
        groupModel.selectedItem = platformBonusItemModel.bonus_name;

        for (int i = 0; i < mModel.data.cart_info.bonus_list.size(); i++) {
            if (i == (position - mPlatformBonusGroupPosition - 1)) {
                mModel.data.cart_info.bonus_list.get(i).selected = "selected";
                changeBonus(platformBonusItemModel.shop_id, platformBonusItemModel.user_bonus_id);
            } else {
                mModel.data.cart_info.bonus_list.get(i).selected = "";
            }
        }
        groupClicked(mPlatformBonusGroupPosition);
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_CHECKOUT, HttpWhat.HTTP_CHECKOUT
                .getValue());
        if (!TextUtils.isEmpty(rc_id)) {
            request.add("rc_id", rc_id);
        }

        if (!TextUtils.isEmpty(shop_id)) {
            request.add("shop_id", shop_id);
        }

        request.setAjax(true);
        addRequest(request);
    }


    private void refreshCallback(String response) {
        HttpResultManager.resolve(response, Model.class, new HttpResultManager
                .HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model back) {
                mModel = back;
                balancePasswordEnalbed = back.data.cart_info.user_info.balance_password_enable;
                setUpAdapterData();
            }
        });
    }

    private void removePayments() {
        mCheckoutAdapter.data.removeAll(payItemModel);
        payItemModel = null;

        mCheckoutAdapter.data.remove(balanceInputModel);
        balanceInputModel = null;

        mCheckoutAdapter.notifyDataSetChanged();
    }


    private void sendTimeItemClicked(int position) {
        SendTimeItemModel selectedSendTimeItemModel = (SendTimeItemModel) mCheckoutAdapter.data
                .get(position);
        if (selectedSendTimeItemModel.set_time) {
            openBestTimeActivity(selectedSendTimeItemModel.best_time);
        }
        GroupModel groupModel = mSendTimeGroupModel;
        groupModel.selectedItem = selectedSendTimeItemModel.value;

        selectedSendTimeId = 0;
        int i = 0;
        for (SendTimeItemModel model : mModel.data.send_time_list) {
            if (model.value.contentEquals(selectedSendTimeItemModel.value)) {
                selectedSendTimeId = i;
                model.checked = "checked";
            } else {
                model.checked = "";
            }
            i++;
        }
        changeBestTime("" + selectedSendTimeId, "");
        groupClicked(groupModel);
    }

    private void setUpAdapterData() {
        //设置总金额
//        mTotalPriceTextView.setText(Utils.formatMoney(getActivity(), mModel.data.cart_info.order
//                .order_amount));

        mTotalPriceTextView.setText(mModel.data.cart_info.order
                .money_pay_format);

        mCheckoutAdapter.rc_code = mModel.data.rc_model.code;
        mCheckoutAdapter.data.clear();
        CheckoutDividerModel dividerModel = new CheckoutDividerModel();
        //取出选中的地址(AddressItemModel),如果没有选中的地址,del
        boolean addressSelected = false;
//        if (mModel.data.address_list != null) {
//            for (AddressItemModel addressItemModel : mModel.data.address_list) {
//                if (mModel.data.address_list.size() == 1) {
//                    mCheckoutAdapter.data.add(addressItemModel);
//                    addressSelected = true;
//
//                    if (Utils.isNull(addressItemModel.address_lat)) {
//                        showConfirmDialog(R.string.addressDialogLabel, ViewType
//                                .VIEW_TYPE_ADDRESS_CHOICE.ordinal(), 0, Integer.valueOf
//                                (addressItemModel.address_id));
//                    }
//                    break;
//                } else {
//
//                    if (addressItemModel.selected == 1) {
//                        mCheckoutAdapter.data.add(addressItemModel);
//                        addressSelected = true;
//
//                        if (Utils.isNull(addressItemModel.address_lat)) {
//                            showConfirmDialog(R.string.addressDialogLabel, ViewType
//                                    .VIEW_TYPE_ADDRESS_CHOICE.ordinal(), 0, Integer.valueOf
//                                    (addressItemModel.address_id));
//
//                        }
//                        break;
//                    }
//                }
//            }
//        }
//        if (!addressSelected) {
//            mCheckoutAdapter.data.add(new EmptyAddressModel());
//        }

        mCheckoutAdapter.data.add(dividerModel);

        int i = 0;
        //依次取出店铺信息,店铺下的商品信息,店铺下的配送信息,红包,留言,店铺结算信息
        for (Map.Entry<String, ShopItemModel> entry : mModel.data.cart_info.shop_list.entrySet()) {
            ShopItemModel shopItemModel = entry.getValue();
            if (mPickUpList == null) {
                mPickUpList = new ArrayList<>();
            }
            mPickUpList.clear();
            if (shopItemModel.shop_info.pickup_list != null) {
                mPickUpList.addAll(shopItemModel.shop_info.pickup_list);
            }

            if (i == 0) {
                order_type = shopItemModel.order.order_type;
                i++;
            }
            if (shopItemModel.shop_info != null) {
                mCheckoutAdapter.data.add(shopItemModel.shop_info);
                if (!Utils.isNull(shopItemModel.goods_list)) {
                    for (GoodsItemModel goodsItemModelEntry : shopItemModel
                            .goods_list) {
                        GoodsItemModel goodsItemModel = goodsItemModelEntry;
                        if (shopItemModel.limit_goods_ids != null) {
                            goodsItemModel.limit = shopItemModel.limit_goods_ids.contains(Integer
                                    .valueOf(goodsItemModel.goods_id));
                        } else {
                            goodsItemModel.limit = false;
                        }
                        mCheckoutAdapter.data.add(goodsItemModel);
                        //取出赠品
                        if (goodsItemModel.gift_list != null) {
                            mCheckoutAdapter.data.addAll(goodsItemModel.gift_list);
                        }
                    }
                }

//                if (shopItemModel.shipping_list == null || shopItemModel.shipping_list.size() <= 0) {
//                    mCheckoutAdapter.data.add(new EmptyShipModel());
//                } else {
//                    shopItemModel.shipping_list.get(0).isCheck = true;
//
//                    GroupModel groupModel = new GroupModel(getString(R.string.shippingList),
//                            getString(R.string.pleaseSelect), shopItemModel.shipping_list);
//
//                    groupModel.isExpanded = false;
//                    mCheckoutAdapter.data.add(groupModel);
//
//                    boolean isZiti = false;
//                    for (ShipItemModel shipItemModel : shopItemModel.shipping_list) {
//                        if (shipItemModel.selected.equals("selected")) {
//                            groupModel.selectedItem = "";
//                        }
//                        shipItemModel.shop_id = shopItemModel.shop_info.shop_id;
//
//                        //如果有自提，就还另一种样式
//                        if(shipItemModel.id.equals("1")) {
//                            isZiti = true;
//                        }
//                    }
//
//                    if(isZiti) {
//                        mCheckoutAdapter.data.add(groupModel.items);
//                        groupModel.isRightArrow = false;
//                    }
//                }

                //取出红包信息
                if (!Utils.isNull(shopItemModel.bonus_list)) {
                    GroupModel groupModel = new GroupModel(getString(R.string.shopBonus),
                            getString(R.string.pleaseSelect), shopItemModel.bonus_list);
                    for (BonusItemModel bonusItemModel : shopItemModel.bonus_list) {
                        bonusItemModel.shop_id = shopItemModel.shop_info.shop_id;
                        if ("selected".equals(bonusItemModel.selected)) {
                            groupModel.selectedItem = bonusItemModel.bonus_name;
                        }
                    }
                    mCheckoutAdapter.data.add(groupModel);
                }

                PostscriptModel postscriptModel = new PostscriptModel(shopItemModel.shop_info
                        .shop_id);
                mCheckoutAdapter.data.add(postscriptModel);

                //店铺购物卡
                if (!Utils.isNull(shopItemModel.store_card_list)) {
                    //判断条件
                    GroupModel groupModel = new GroupModel("店铺购物卡",
                            getString(R.string.pleaseSelect), shopItemModel.store_card_list);
                    for (StoreCardModel bonusItemModel : shopItemModel.store_card_list) {
                        bonusItemModel.shop_id = shopItemModel.shop_info.shop_id;
                        if (bonusItemModel.selected.equals("selected")) {
                            groupModel.selectedItem = bonusItemModel.type_name;
                        }
                    }

                    mCheckoutAdapter.data.add(groupModel);
                }

                mCheckoutAdapter.data.add(shopItemModel.order);
                mCheckoutAdapter.data.add(dividerModel);
            }
        }

        //取出商城红包
        if (!Utils.isNull(mModel.data.cart_info.bonus_list)) {
            GroupModel groupModel = new GroupModel(getString(R.string.platformBonus), getString(R
                    .string.pleaseSelect), mModel.data.cart_info.bonus_list);
            for (PlatformBonusItemModel bonusItemModel : mModel.data.cart_info.bonus_list) {
                bonusItemModel.shop_id = "0";
                if (bonusItemModel.selected.equals("selected")) {
                    groupModel.selectedItem = bonusItemModel.bonus_name;
                }
            }
            mCheckoutAdapter.data.add(groupModel);
            mCheckoutAdapter.data.add(dividerModel);
        }

        //取出配送时间
//        GroupModel groupModel = new GroupModel(getString(R.string.sendTimeList), getString(R
//                .string.pleaseSelect), mModel.data.send_time_list);
//        mSendTimeGroupModel = groupModel;
//        groupModel.isExpanded = false;
//
//        mCheckoutAdapter.data.add(groupModel);
//        for (SendTimeItemModel sendTimeItemModel : mModel.data.send_time_list) {
//            if (sendTimeItemModel.checked.equals("checked")) {
//                groupModel.selectedItem = sendTimeItemModel.value;
//            }
//        }
//        mCheckoutAdapter.data.add(dividerModel);
//
//        if (mModel.data.cart_info.invoice_type != 0) {
//            //取出发票信息
//            groupModel = new GroupModel(getString(R.string.askForInvoice), getString(R.string
//                    .pleaseSelect), new ArrayList<>());
//            groupModel.viewType = ViewType.VIEW_TYPE_INVOICE;
//            /*for (InvoiceItemModel invoiceItemModel : mModel.data.invoice_info) {
//                if (invoiceItemModel.selected.equals("selected")) {
//                    groupModel.selectedItem = invoiceItemModel.name;
//                }
//            }*/
//            groupModel.selectedItem = mModel.data.invoice_desc;
//            mInvoiceGroupModel = groupModel;
//            mCheckoutAdapter.data.add(groupModel);
//
//            //取出不能开发票的商品
//            if (mModel.data.invoice_info.size() > 0 && !Utils.isNull(mModel.data.invoice_info.get(0).goods_list)) {
//                groupModel = new GroupModel(getString(R.string
//                        .theFollowingGoodsDontSupportInvoice), "", mModel.data.invoice_info.get
//                        (0).goods_list);
//                groupModel.viewType = ViewType.VIEW_TYPE_INVOICE_GOODS;
//                mCheckoutAdapter.data.add(groupModel);
//            }
//
//            mCheckoutAdapter.data.add(dividerModel);
//        }

        //取出余额
        userInfoModel = mModel.data.cart_info.user_info;
        userInfoModel.balanceInputModel = new BalanceInputModel(getString(R.string
                .placeholderBalance), getString(R.string.placeholderBalanceFormat), getString(R
                .string.placeholderMoneyPay), getString(R.string.placeholderMoneyPayFormat));

        if (!userInfoModel.balance.equals("0")) {
            mCheckoutAdapter.data.add(userInfoModel);
        }

/*        mModel.data.cart_info.order.balance_format = getString(R.string
.placeholderMoneyPayFormat);
        mCheckoutAdapter.data.add(mModel.data.cart_info.order);
        mCheckoutAdapter.data.add(dividerModel);*/

        if (Double.parseDouble(mModel.data.cart_info.order.money_pay) != 0) {
            addPayments();
        }

        mCheckoutAdapter.notifyDataSetChanged();
    }

    private void shipItemClicked(int position) {
        int groupPosition = mCheckoutAdapter.findGroupModelBefore(position);
        GroupModel groupModel = (GroupModel) mCheckoutAdapter.data.get(groupPosition);
        for (int i = 0; i < groupModel.items.size(); i++) {
            ShipItemModel shipItemModel = (ShipItemModel) groupModel.items.get(i);
            if (i == (position - groupPosition - 1)) {
                shipItemModel.selected = "selected";
                groupModel.selectedItem = shipItemModel.name;
                changeShipment(shipItemModel.shop_id, shipItemModel.id, groupPosition);
            } else {
                shipItemModel.selected = "";
            }
        }
        groupClicked(mCheckoutAdapter.findGroupModelBefore(groupPosition));
    }

    private void submitOrder() {

        if (!Utils.isWeixinAvilible(getActivity())) {
            Toast.makeText(getActivity(), "未安装微信客户端", Toast.LENGTH_SHORT).show();
            return;
        }

        CommonRequest request = new CommonRequest(Api.API_CHECKOUT_SUBMIT, HttpWhat
                .HTTP_CHECKOUT_SUBMIT.getValue(), RequestMethod.POST);
        if (!TextUtils.isEmpty(rc_id)) {
            request.add("rc_id", rc_id);
        }
        request.setAjax(true);

        for (Object object : mCheckoutAdapter.data) {
            if (object instanceof PostscriptModel) {
                PostscriptModel postscriptModel = (PostscriptModel) object;
                if (!Utils.isNull(postscriptModel.content)) {
                    request.add("postscript[" + postscriptModel.shopId + "]", postscriptModel
                            .content);
                } else {
                    request.add("postscript[" + postscriptModel.shopId + "]", "");
                }
            }
        }
        if (!Utils.isNull(balance_password)) {
            request.add("balance_password", balance_password);
        }
        addRequest(request);
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
                group_sn = submitModel.group_sn;
                for (int i = 0; i < mModel.data.pay_list.size(); i++) {
                    if (mModel.data.pay_list.get(i).checked.equals("checked")) {
                        if (mModel.data.pay_list.get(i).code.equals("cod")) {
                            goResult();
                            return;
                        } else {
                            getPayment(submitModel.order_sn, getPayType(), mModel.data.cart_info
                                    .key, group_sn);
                            return;
                        }
                    }
                }
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


}
