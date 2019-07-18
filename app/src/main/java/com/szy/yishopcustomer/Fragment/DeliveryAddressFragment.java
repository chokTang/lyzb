package com.szy.yishopcustomer.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonEditText;
import com.szy.yishopcustomer.Activity.ScanActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.RequestCode;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.DeliveryAddressModel.Model;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by liuzhifeng on 16/4/26.
 */
public class DeliveryAddressFragment extends YSCBaseFragment {
    public String shippingId;
    @BindView(R.id.delivery_company_layout)
    LinearLayout mLayout;
    @BindView(R.id.delivery_sn_layout)
    LinearLayout mDeliverSnLayout;

    @BindView(R.id.ll_wuliu_msg)
    LinearLayout ll_wuliu_msg;

    @BindView(R.id.fragment_delivery_address_textTwo)
    TextView mTextView;

    @BindView(R.id.tv_type1)
    TextView tv_type1;
    @BindView(R.id.bottom_button)
    TextView mSubmit;

    @BindView(R.id.edt_cash)
    CommonEditText edt_cash;

    @BindView(R.id.commonEditText)
    CommonEditText mEditText;
    @BindView(R.id.scan_bar_code)
    ImageView mScanBarCode;
    private AlertDialog mDialogDialog;
    private ArrayList list;
    private ArrayList idList;
    private String backId;
    private String backType;
    private int curent = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_delivery_address;
        Intent intent = getActivity().getIntent();
        backId = intent.getStringExtra(Key.KEY_BACK_ID.getValue());
        backType = intent.getStringExtra(Key.KEY_BACK_TYPE.getValue());

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (RequestCode.valueOf(requestCode)) {
            case REQUEST_CODE_SCAN:
                if (resultCode == Activity.RESULT_OK) {
                    String sn = data.getStringExtra(Key.KEY_RESULT.getValue());
                    mEditText.setText(sn);
                    mEditText.setSelection(sn.length());
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delivery_company_layout:
                if (list.size() != 0) {
                    mDialogDialog.show();
                } else {
                    Utils.toastUtil.showToast(getActivity(), "获取数据失败");
                }
                break;
            case R.id.bottom_button:
                submit();
                break;
            case R.id.scan_bar_code:
                openScanActivity();
                break;
            default:
                super.onClick(view);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        mLayout.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
        mScanBarCode.setOnClickListener(this);
        list = new ArrayList<String>();
        idList = new ArrayList<String>();
        mSubmit.setText(getResources().getString(R.string.confirm));

        tv_type1.setSelected(true);
        if (backType.equals("4")){//上门取件
            tv_type1.setText("上门取件");
            ll_wuliu_msg.setVisibility(View.GONE);
        }else {//40用户自己发货
            tv_type1.setText("物流配送");
            ll_wuliu_msg.setVisibility(View.VISIBLE);
        }


        refresh();
        return v;
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_BACK_DELIVERY, HttpWhat
                .HTTP_BACK_DETLIVERY.getValue());
        request.add("id", backId);
        request.add("type", "shipping");
        addRequest(request);
    }

    private void submit() {

        if (!check()) {
            return;
        }

        CommonRequest request = new CommonRequest(Config.BASE_URL + "/user/back/edit-order",
                HttpWhat.HTTP_BACK_DETLIVERY_SUBMIT.getValue(), RequestMethod.POST);
        request.add("id", backId);
        request.add("type", "shipping");
        request.add("jd_shipping_freight", edt_cash.getText().toString().trim());
        request.add("shipping_id", shippingId);
        if (!mTextView.getText().toString().equals("自行配送")) {
            request.add("shipping_sn", mEditText.getText().toString());
        }
        addRequest(request);
    }

    private boolean check() {
        String message = "";
        if (Utils.isNull(shippingId)) {
            message = "请选择物流公司";
        } else if (mEditText.getText().toString().equals("")) {
            if (!mTextView.getText().toString().equals("自行配送")) {
                message = "物流单号不能为空";
            }
        } else if ("".equals(edt_cash.getText().toString().trim())) {
            message = "运费金额不能为空";
        }
        if (Utils.isNull(message)) {
            return true;
        } else {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_BACK_DETLIVERY:
                refreshCallback(response);
                break;
            case HTTP_BACK_DETLIVERY_SUBMIT:
                HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
                    @Override
                    public void onSuccess(Model back) {
                        if (!Utils.isNull(back.message)) {
                            Utils.toastUtil.showToast(getActivity(), back.message);
                        }
                        EventBus.getDefault().post(new CommonEvent(EventWhat
                                .EVENT_REFRESH_BACK_DETAIL.getValue()));
                        finish();
                    }
                },true);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private void refreshCallback(String response) {
        HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model model) {
                for (int i = 0; i < model.data.shipping.size(); i++) {
                    list.add(model.data.shipping.get(i).shipping_name);
                    idList.add(model.data.shipping.get(i).shipping_id);
                }
                if (!Utils.isNull(model.data.back_info.shipping_name)) {
                    mTextView.setText(model.data.back_info.shipping_name);
                }
                if (!Utils.isNull(model.data.back_info.shipping_sn)) {
                    mEditText.setText(model.data.back_info.shipping_sn);
                }
                shippingId = model.data.back_info.shipping_id;
                initDialog();
            }
        });
    }

    private void initDialog() {
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.item_cancel_order, R.id
                .item_cancel_order_textView, list);

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View cancelOrderDialogView = layoutInflater.inflate(R.layout.order_cancel, null);
        final ListView listView = (ListView) cancelOrderDialogView.findViewById(R.id
                .order_cancel_reason_list_view);
        if (list.size() > 5) {
            listView.getLayoutParams().height = Utils.dpToPx(getContext(), 200);
        }
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setItemChecked(0, true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mTextView.setText(list.get(position).toString());
                shippingId = idList.get(position).toString();

                if (mTextView.getText().toString().equals("自行配送")) {
                    mDeliverSnLayout.setVisibility(View.GONE);
                }else{
                    mDeliverSnLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        mDialogDialog = new AlertDialog.Builder(getActivity()).setView(cancelOrderDialogView)
                .create();
        TextView mDialogCancel = (TextView) cancelOrderDialogView.findViewById(R.id
                .dialog_call_cancle_textView);
        TextView mTitle = (TextView) cancelOrderDialogView.findViewById(R.id
                .dialog_cancel_order_textView);
        mTitle.setText("请选择物流公司");
        mDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogDialog.dismiss();
            }
        });
        TextView mDialogConfirm = (TextView) cancelOrderDialogView.findViewById(R.id
                .dialog_call_confirm_textView);
        mDialogConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTextView.getText().toString().equals("请选择物流公司")){
                    mTextView.setText(list.get(0).toString());
                    shippingId = idList.get(0).toString();
                }
                mDialogDialog.dismiss();
            }
        });
    }

    public void openScanActivity() {
//        PackageManager pm = getActivity().getPackageManager();
//        boolean flag = (PackageManager.PERMISSION_GRANTED == pm.checkPermission("android" + "" +
//                ".permission.CAMERA", App.packageName));
        if (cameraIsCanUse()) {
            Intent intent = new Intent();
            intent.setClass(getContext(), ScanActivity.class);
            intent.putExtra(Key.KEY_ACT_CODE.getValue(), "DeliveryAddressActivity");
            startActivityForResult(intent, RequestCode.REQUEST_CODE_SCAN.getValue());
        } else {
            Utils.toastUtil.showToast(getActivity(), getResources().getString(R.string
                    .noCameraPermission));
        }
//        if (flag) {
//            Intent intent = new Intent();
//            intent.setClass(getContext(), ScanActivity.class);
//            intent.putExtra(Key.KEY_ACT_CODE.getValue(), "DeliveryAddressActivity");
//            startActivityForResult(intent, RequestCode.REQUEST_CODE_SCAN.getValue());
//        } else {
//            Utils.toastUtil.showToast(getActivity(), getResources().getString(R.string
//                    .noCameraPermission));
//        }

    }
}
