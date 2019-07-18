package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Toast;

import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Adapter.GoodsRankPriceAdapter;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Goods.PickUpModel;
import com.szy.yishopcustomer.ResponseModel.Goods.RankPriceModel;
import com.szy.yishopcustomer.Util.Utils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by liwei on 17/6/13.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GoodsRankPriceFragment extends YSCBaseFragment {
    public static final String ITEM_POSITION = "item_position";

    @BindView(R.id.fragment_rank_price_recyclerView)
    CommonRecyclerView mRecyclerView;

    private LinearLayoutManager mLayoutManager;
    private GoodsRankPriceAdapter mAdapter;

    private List<RankPriceModel> mRankPriceList;

    @Override
    public void onClick(View view) {
        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        int extraInfo = Utils.getExtraInfoOfTag(view);

        switch (viewType) {
            case VIEW_TYPE_PHONE:
                PickUpModel pickUpModel = (PickUpModel) mAdapter.data.get(position);
                Utils.Dial(getActivity(), pickUpModel.pickup_tel);
                break;
            case VIEW_TYPE_ITEM:
                PickUpModel pickUpModel1 = (PickUpModel) mAdapter.data.get(position);
                Intent intent = getActivity().getIntent();
                intent.putExtra(ITEM_POSITION, position);
                setResult(getActivity().RESULT_OK, intent);
                finish();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mAdapter = new GoodsRankPriceAdapter();

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.onClickListener = this;

        setUpData();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_goods_rank_price;

        Bundle arguments = getArguments();
        if (arguments == null) {
            Toast.makeText(getContext(), "Empty arguments", Toast.LENGTH_SHORT).show();
            return;
        }
        mRankPriceList = arguments.getParcelableArrayList(Key.KEY_RANK_PRICE.getValue());
    }

    private void setUpData() {
        if (mRankPriceList != null) {


            for (RankPriceModel rankPriceModel : mRankPriceList) {
                mAdapter.data.add(rankPriceModel);
            }

            mAdapter.notifyDataSetChanged();
        }
    }
}
