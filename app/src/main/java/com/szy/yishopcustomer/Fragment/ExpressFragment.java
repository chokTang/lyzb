package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Activity.ExpressDeliveryActivity;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Adapter.ExpressAdapter;
import com.szy.yishopcustomer.Adapter.ExpressImageAdapter;
import com.szy.yishopcustomer.Adapter.ExpressTitleAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Express.ExpressModel;
import com.szy.yishopcustomer.ResponseModel.Express.GoodsListModel;
import com.szy.yishopcustomer.ResponseModel.Express.ListModel;
import com.szy.yishopcustomer.ResponseModel.Express.Model;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.ExpressFragmentModel;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;

//import com.szy.common.View.CustomProgressDialog;

/**
 * Created by liwei on 2016/7/25.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ExpressFragment extends YSCBaseFragment {
    @BindView(R.id.fragment_express_selectedRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.fragment_express_list_view)
    ListView mListView;
    @BindView(R.id.fragment_express_selectedRecyclerView_image)
    RecyclerView mRecyclerViewImage;
    @BindView(R.id.fragment_express_textview_one)
    TextView mTextViewOne;
    @BindView(R.id.fragment_express_textview_two)
    TextView mTextViewTwo;

    //物流信息 空数据 提示布局
    @BindView(R.id.relativeLayout_empty)
    LinearLayout mNullExpressLayout;
    @BindView(R.id.empty_view_titleTextView)
    TextView mEmptyTitle;

    //物流信息 ex
    @BindView(R.id.fragment_express_layout)
    LinearLayout mExpressLayout;

    @BindView(R.id.relativeLayout_empty_two)
    LinearLayout mNullExpressLayoutTwo;
    @BindView(R.id.empty_view_titleTextView_two)
    TextView mEmptyTitleTwo;

    @BindView(R.id.express_delivery)
    ImageView mExpressDelivery;

    private ExpressAdapter mExpressAdapter;
    private ArrayList<ListModel> mAdapterData;
    private ExpressTitleAdapter mExpressTitleAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private ArrayList<ExpressFragmentModel> list;
    private Model mResponseModel;
    private ExpressImageAdapter mExpressImageAdapter;
    private LinearLayoutManager mLinearLayoutManagerImage;
    private View mView;
    private String shopId;
    private String deliverySn;
    private String expressType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_express;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mView = View.inflate(getActivity(), R.layout.item_express_text_view, null);
        mAdapterData = new ArrayList<ListModel>();
        mExpressAdapter = new ExpressAdapter(mAdapterData);

        Intent intent = getActivity().getIntent();
        String order_id = intent.getStringExtra("order_id");
        expressType = intent.getStringExtra("express_type");
        refresh(order_id);

        mExpressTitleAdapter = new ExpressTitleAdapter();
        mExpressImageAdapter = new ExpressImageAdapter();

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mLinearLayoutManagerImage = new LinearLayoutManager(getActivity());
        mLinearLayoutManagerImage.setOrientation(LinearLayoutManager.HORIZONTAL);

        list = new ArrayList<ExpressFragmentModel>();

        mExpressTitleAdapter.onClickListener = this;
        mExpressImageAdapter.onClickListener = this;

        mExpressDelivery.setOnClickListener(this);
        Utils.setViewTypeForTag(mExpressDelivery, ViewType.VIEW_TYPE_DELIVERY);
        mExpressDelivery.setOnClickListener(this);

        return view;
    }

    private void openGoodsActivity(ArrayList<GoodsListModel> list, int position) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_SKU_ID.getValue(), list.get(position).sku_id);
        intent.setClass(getActivity(), GoodsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (Utils.isDoubleClick()) {
            return;
        }

        ViewType viewType = Utils.getViewTypeOfTag(v);
        int position = Utils.getPositionOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_TAB:
                openExpressTab(position);
                break;
            case VIEW_TYPE_DELIVERY:
                openExpressDelivery();
                break;
            case VIEW_TYPE_GOODS:
                openGoodsActivity(mExpressImageAdapter.data, position);
                break;
            default:
                super.onClick(v);
        }
    }

    private void openExpressTab(int position) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setSelect(false);
        }
        list.get(position).setSelect(true);
        mExpressTitleAdapter.setData(list);
        mExpressTitleAdapter.notifyDataSetChanged();

        if (mResponseModel.data.express.get(position).error.equals("0")) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mExpressLayout.setVisibility(View.VISIBLE);
            mTextViewOne.setVisibility(View.VISIBLE);
            mTextViewTwo.setVisibility(View.VISIBLE);
            mNullExpressLayout.setVisibility(View.GONE);

            mTextViewOne.setText("信息来源：" + mResponseModel.data.express.get(position).info
                    .shipping_name);
            mTextViewTwo.setText("运单号：" + mResponseModel.data.express.get(position).info
                    .express_sn);

            shopId = mResponseModel.data.express.get(position).shop_id;
            deliverySn = mResponseModel.data.express.get(position).express_sn;

            if (mResponseModel.data.express.get(position).is_logistics.equals("1")) {
                mExpressDelivery.setVisibility(View.VISIBLE);
            } else {
                mExpressDelivery.setVisibility(View.GONE);
            }

            mExpressImageAdapter.setData(mResponseModel.data.express.get(position).info.goods_list);
            mExpressImageAdapter.notifyDataSetChanged();

            mListView.setVisibility(View.VISIBLE);
            mNullExpressLayoutTwo.setVisibility(View.GONE);

            ArrayList<ListModel> tempList = mResponseModel.data.express.get(position).content.list;
            Collections.reverse(tempList);

            mExpressAdapter.setData(tempList);
            mExpressAdapter.notifyDataSetChanged();
        } else if (mResponseModel.data.express.get(position).error.equals("2")) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mExpressLayout.setVisibility(View.VISIBLE);
            mTextViewOne.setVisibility(View.GONE);
            mTextViewTwo.setVisibility(View.GONE);

            mExpressImageAdapter.setData(mResponseModel.data.express.get(position).info.goods_list);
            mExpressImageAdapter.notifyDataSetChanged();

            mListView.setVisibility(View.GONE);
            mNullExpressLayoutTwo.setVisibility(View.VISIBLE);
            mEmptyTitleTwo.setText(R.string.noNeedForLogistics);

            /*ListModel listModel = new ListModel();
            listModel.msg = getResources().getString(R.string.noNeedForLogistics);
            ArrayList<ListModel> tempList = new ArrayList<>();
            tempList.add(listModel);

            mExpressAdapter.setData(tempList);
            mExpressAdapter.notifyDataSetChanged();*/

        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mExpressLayout.setVisibility(View.GONE);
            mNullExpressLayout.setVisibility(View.VISIBLE);
            if (!Utils.isNull(mResponseModel.data.express.get(position).content.list.get(0).msg)) {
                mEmptyTitle.setText(mResponseModel.data.express.get(position).content.list.get(0).msg);
            } else {
                mEmptyTitle.setText(R.string.emptyExpressTitle);
            }
        }
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_EXPRESS:
                // mProgress.dismiss();
                mResponseModel = JSON.parseObject(response, Model.class);
                if (mResponseModel.code == 0) {

                    if (mResponseModel.data.express != null && mResponseModel.data.express.size() != 0) {

                        if ("0".equals(mResponseModel.data.express.get(0).error)) {

                            if (mResponseModel.data.express.get(0).content.list != null) {
                                mRecyclerView.setVisibility(View.VISIBLE);
                                mExpressLayout.setVisibility(View.VISIBLE);
                                mTextViewOne.setVisibility(View.VISIBLE);
                                mTextViewTwo.setVisibility(View.VISIBLE);
                                mRecyclerViewImage.setVisibility(View.VISIBLE);
                                mListView.setVisibility(View.VISIBLE);
                                mNullExpressLayoutTwo.setVisibility(View.GONE);
                                mNullExpressLayout.setVisibility(View.GONE);
                            } else {
                                mRecyclerView.setVisibility(View.GONE);
                                mExpressLayout.setVisibility(View.GONE);

                                mNullExpressLayout.setVisibility(View.VISIBLE);
                                mEmptyTitle.setText(R.string.emptyExpressTitle);
                            }

                        } else if ("2".equals(mResponseModel.data.express.get(0).error)) {
                            if (mResponseModel.data.express.size() > 1) {
                                mRecyclerView.setVisibility(View.VISIBLE);
                            } else {
                                mRecyclerView.setVisibility(View.GONE);
                            }

                            mExpressLayout.setVisibility(View.VISIBLE);
                            mTextViewOne.setVisibility(View.GONE);
                            mTextViewTwo.setVisibility(View.GONE);
                            mRecyclerViewImage.setVisibility(View.GONE);
                            mListView.setVisibility(View.GONE);
                            mNullExpressLayoutTwo.setVisibility(View.VISIBLE);
                            mEmptyTitleTwo.setText(R.string.noNeedForLogistics);

                           /* ListModel listModel = new ListModel();
                            listModel.msg = getResources().getString(R.string.noNeedForLogistics);
                            ArrayList<ListModel> tempList = new ArrayList<>();
                            tempList.add(listModel);

                            mExpressAdapter.setData(tempList);
                            mExpressAdapter.notifyDataSetChanged();*/

                        } else {
                            mRecyclerView.setVisibility(View.GONE);
                            mExpressLayout.setVisibility(View.GONE);

                            mNullExpressLayout.setVisibility(View.VISIBLE);
                            mEmptyTitle.setText(R.string.emptyExpressTitle);

//                            if (!Utils.isNull(mResponseModel.data.express.get(0).content.list.get(0).msg)) {
//                                mEmptyTitle.setText(mResponseModel.data.express.get(0).content.list.get(0).msg);
//                            } else {
//                                mEmptyTitle.setText(R.string.emptyExpressTitle);
//                            }
                        }

                        mRecyclerView.setLayoutManager(mLinearLayoutManager);
                        mRecyclerView.setAdapter(mExpressTitleAdapter);

                        mRecyclerViewImage.setLayoutManager(mLinearLayoutManagerImage);
                        mRecyclerViewImage.setAdapter(mExpressImageAdapter);

                        mListView.addHeaderView(mView);
                        mListView.setAdapter(mExpressAdapter);

                        if (mResponseModel.data.express.size() > 1) {
                            for (int i = 0; i < mResponseModel.data.express.size(); i++) {
                                ExpressFragmentModel good = new ExpressFragmentModel();
                                good.setName("包裹" + (i + 1));
                                if (i == 0) {
                                    good.setSelect(true);
                                } else {
                                    good.setSelect(false);
                                }

                                list.add(good);
                            }
                        }

                        //默认打开第一个快递员位置
                        ExpressModel firstExpress = mResponseModel.data.express.get(0);
                        shopId = firstExpress.shop_id;
                        deliverySn = firstExpress.express_sn;

                        if (firstExpress.is_logistics.equals("1")) {
                            mExpressDelivery.setVisibility(View.VISIBLE);
                        } else {
                            mExpressDelivery.setVisibility(View.GONE);
                        }

                        mExpressTitleAdapter.setData(list);
                        mExpressTitleAdapter.notifyDataSetChanged();

                        mTextViewOne.setText("信息来源：" + mResponseModel.data.express.get(0).info.shipping_name);
                        mTextViewTwo.setText("运单号：" + mResponseModel.data.express.get(0).info.express_sn);

                        if (mResponseModel.data.express.get(0).info.goods_list != null) {
                            mExpressImageAdapter.setData(mResponseModel.data.express.get(0).info
                                    .goods_list);
                            mExpressImageAdapter.notifyDataSetChanged();
                        }
                        ArrayList<ListModel> tempList = mResponseModel.data.express.get(0)
                                .content.list;
                        if (tempList != null) {
                            Collections.reverse(tempList);
                            mExpressAdapter.setData(tempList);
                        }

                    } else {
                        mRecyclerView.setVisibility(View.GONE);
                        mExpressLayout.setVisibility(View.GONE);
                        mNullExpressLayout.setVisibility(View.VISIBLE);
                        mEmptyTitle.setText(R.string.emptyExpressTitle);
                    }
                } else {
                    //Utils.makeToast(getActivity(), mResponseModel.data.);
                }

                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    @Override
    protected void onRequestFailed(int what, String response) {
        super.onRequestFailed(what, response);
        switch (HttpWhat.valueOf(what)) {
            case HTTP_EXPRESS:

                break;
        }
    }

    private String apiString;

    private void refresh(String orderId) {
        if (Utils.isNull(expressType)) {
            apiString = Api.API_VIEW_EXPRESS;
        } else {
            apiString = Api.API_VIEW_EXCHANGE_EXPRESS;
        }

        CommonRequest mExpressRequest = new CommonRequest(apiString, HttpWhat
                .HTTP_EXPRESS.getValue());
        mExpressRequest.add("id", orderId);
        addRequest(mExpressRequest);
    }

    private void openExpressDelivery() {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_SHOP_ID.getValue(), shopId);
        intent.putExtra(Key.KEY_DELIVERY_SN.getValue(), deliverySn);
        intent.setClass(getActivity(), ExpressDeliveryActivity.class);
        startActivity(intent);
    }
}
