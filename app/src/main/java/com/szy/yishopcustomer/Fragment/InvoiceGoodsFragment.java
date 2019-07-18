package com.szy.yishopcustomer.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;

import com.szy.yishopcustomer.Adapter.InvoiceGoodsAdapter;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Checkout.InvoiceGoodsModel;
import com.szy.yishopcustomer.Util.Utils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by liwei on 2016/11/07.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class InvoiceGoodsFragment extends YSCBaseFragment {
    @BindView(R.id.fragment_invoice_goods_closeImageView)
    ImageView mCloseButton;
    @BindView(R.id.fragment_invoice_goods_goodsRecyclerView)
    RecyclerView mRecyclerView;

    private ArrayList<InvoiceGoodsModel> mInvoiceInfoGoods;
    private LinearLayoutManager mLinearLayoutManager;
    private InvoiceGoodsAdapter mInvoiceGoodsAdapter;

    @Override
    public void onClick(View v) {
        if(Utils.isDoubleClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.fragment_invoice_goods_closeImageView:
                getActivity().finish();
                break;
            default:
                super.onClick(v);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mCloseButton.setOnClickListener(this);

        mInvoiceGoodsAdapter = new InvoiceGoodsAdapter(mInvoiceInfoGoods);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mInvoiceGoodsAdapter);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_invoice_goods;

        Bundle arguments = getArguments();
        if (arguments == null) {
            return;
        }
        mInvoiceInfoGoods = arguments.getParcelableArrayList(Key.KEY_INVOICE_INFO_GOODS.getValue());
        if (mInvoiceInfoGoods == null) {
            return;
        }
    }
}
