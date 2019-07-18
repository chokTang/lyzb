package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Activity.AddAccountActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Deposit.Model;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.MyListView;
import com.yolanda.nohttp.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by liuzhifeng on 2016/7/23 0023.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DepositFragment extends YSCBaseFragment {
    @BindView(R.id.R1)
    LinearLayout mRelativeLayoutOne;
    @BindView(R.id.fragment_deposit_account_text_view)
    TextView mAccount;
    @BindView(R.id.relativeLayout_empty)
    LinearLayout mLayout;
    @BindView(R.id.empty_view_titleTextView)
    TextView mEmptyTitle;
    @BindView(R.id.empty_view_subtitleTextView)
    TextView mEmptySubTitle;
    @BindView(R.id.empty_view_button)
    Button mEmptyButton;

    @BindView(R.id.item_fragment_withdrawal_edit_text_one)
    EditText mEditTextOne;
    @BindView(R.id.item_fragment_withdrawal_edit_text_two)
    EditText mEditTextTwo;
    @BindView(R.id.bottom_button)
    TextView mSubmitButton;
    @BindView(R.id.my_list_view)
    MyListView mListView;
    @BindView(R.id.fragment_withdrawal_image_view)
    ImageView mImageView;

    @BindView(R.id.fragment_deposit_user_format_money)
    TextView mUserFormatMoney;
    private ArrayAdapter Adapter;
    private ArrayList<String> list;
    private Model model;
    private String id = "";
    private String userMoneyFormat;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.R1:
                if (mListView.getVisibility() == View.GONE) {
                    if (list.size() > 0) {
                        mListView.setVisibility(View.VISIBLE);
                        mImageView.setBackgroundResource(R.mipmap.bg_arrow_up_dark);
                    } else {
                        mLayout.setVisibility(View.VISIBLE);
                        mEmptyTitle.setText(R.string.noAccount);
                        mEmptySubTitle.setVisibility(View.VISIBLE);
                        mEmptySubTitle.setText(R.string.pleaseBindTheAccount);
                        mEmptyButton.setText("绑定账户");
                        mEmptyButton.setVisibility(View.VISIBLE);
                    }
                } else {
                    mListView.setVisibility(View.GONE);
                    mImageView.setBackgroundResource(R.mipmap.bg_arrow_down_dark);
                }
                break;
            case R.id.empty_view_button:
                openAddAccountActivity();
                break;
            case R.id.bottom_button:
                addDepositRequest();
                break;
            default:
                super.onClick(view);
        }
    }

    private void addDepositRequest() {
        if (!checkDepositInput()) {
            Utils.toastUtil.showToast(getActivity(), "账户和金额不能为空");
            return;
        }

        CommonRequest request = new CommonRequest(Api.API_DEPOSIT_ADD, HttpWhat
                .HTTP_GET_WITHDRAWAL.getValue(), RequestMethod.POST);
        request.setAjax(true);
        request.add("DepositModel[amount]", mEditTextOne.getText().toString());
        request.add("DepositModel[account_id]", id);
        request.add("DepositModel[user_note]", mEditTextTwo.getText().toString());
        addRequest(request);
    }

    private boolean checkDepositInput() {
        if (!"".equals(id) && !mEditTextOne.getText().toString().equals("")) {
            return true;
        } else {
            return false;
        }
    }


    private void openAddAccountActivity() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), AddAccountActivity.class);
        intent.putExtra(Key.KEY_TYPE.getValue(),"0");
        startActivity(intent);
        finish();
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GET_WITHDRAWAL_LIST:
                HttpResultManager.resolve(response, Model.class, new HttpResultManager
                        .HttpResultCallBack<Model>() {
                    @Override
                    public void onSuccess(Model back) {
                        model = back;

                        if (model.data.account_list.size() > 1) {

                            mListView.setVisibility(View.VISIBLE);
                            mImageView.setBackgroundResource(R.mipmap.bg_arrow_up_dark);
                            for (int i = 0; i < model.data.account_list.size(); i++) {
                                if (i != 0) {
                                    list.add(model.data.account_list.get(i).account_type + "" +
                                            model.data.account_list.get(i).account);
                                }
                            }

                            initPaymentList();

                        } else {
                            Utils.hideKeyboard(mLayout);
                            mLayout.setVisibility(View.VISIBLE);
                            mEmptyTitle.setText(R.string.noAccount);
                            mEmptySubTitle.setVisibility(View.VISIBLE);
                            mEmptySubTitle.setText(R.string.pleaseBindTheAccount);
                            mEmptyButton.setText("绑定账户");
                            mEmptyButton.setVisibility(View.VISIBLE);
                        }
                    }
                });
                break;
            case HTTP_GET_WITHDRAWAL:
                HttpResultManager.resolve(response, Model.class, new HttpResultManager
                        .HttpResultCallBack<Model>() {
                    @Override
                    public void onSuccess(Model back) {
                        model = back;
                        Utils.toastUtil.showToast(getActivity(), model.message);
                        getActivity().finish();
                        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_MONEY_CHANGE
                                .getValue()));
                    }
                }, true);

                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private void initPaymentList() {
        Adapter = new ArrayAdapter(getActivity(), R.layout.item_fragment_withdrawal, R.id
                .item_fragment_withdrawal_text_view, list);
        mListView.setAdapter(Adapter);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                id = model.data.account_list.get(position + 1).id;
                mListView.setVisibility(View.GONE);
                mImageView.setBackgroundResource(R.mipmap.bg_arrow_down_dark);
                mAccount.setText(model.data.account_list.get(position + 1).account_type + model
                        .data.account_list.get(position + 1).account);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_deposit;

        Intent intent = getActivity().getIntent();
        userMoneyFormat = intent.getStringExtra("user_money_format");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        initViews();
        request();
        return v;
    }

    private void initViews() {
        mEmptyButton.setOnClickListener(this);
        mRelativeLayoutOne.setOnClickListener(this);
        list = new ArrayList<String>();
        mSubmitButton.setOnClickListener(this);
        mSubmitButton.setText(getResources().getString(R.string.submit));
        mUserFormatMoney.setText(userMoneyFormat);
        Utils.showSoftInputFromWindowTwo(getActivity(),mEditTextOne);
    }

    private void request() {
        CommonRequest request = new CommonRequest(Api.API_DEPOSIT_ADD, HttpWhat
                .HTTP_GET_WITHDRAWAL_LIST.getValue());
        addRequest(request);
    }
}
