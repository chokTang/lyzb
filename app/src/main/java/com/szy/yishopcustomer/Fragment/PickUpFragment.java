package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.Toast;

import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Adapter.PickUpAdapter;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Goods.PickUpModel;
import com.szy.yishopcustomer.Util.Utils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by liwei on 17/6/13.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class PickUpFragment extends YSCBaseFragment {
    public static final String ITEM_POSITION = "item_position";

    @BindView(R.id.fragment_pick_up_recyclerView)
    CommonRecyclerView mRecyclerView;
    @BindView(R.id.fragment_pick_up_cancel_button)
    Button mCancel;

    @BindView(R.id.relativeLayout_root)
    View relativeLayout_root;

    private LinearLayoutManager mLayoutManager;
    private PickUpAdapter mAdapter;

    private List<PickUpModel> mPickUpList;
    private String pickid;

    @Override
    public void onClick(View view) {
        if(Utils.isDoubleClick()) {
            return;
        }

        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        int extraInfo = Utils.getExtraInfoOfTag(view);

        switch (viewType) {
//            case VIEW_TYPE_PHONE:
//                PickUpModel pickUpModel = (PickUpModel) mAdapter.data.get(position);
//                Utils.Dial(getActivity(), pickUpModel.pickup_tel);
//                break;
            case VIEW_TYPE_ITEM:
                PickUpModel pickUpModel1 = (PickUpModel) mAdapter.data.get(position);
                Intent intent = getActivity().getIntent();
                intent.putExtra(ITEM_POSITION,position);
                setResult(getActivity().RESULT_OK,intent);
                finish();
                break;
            case VIEW_TYPE_CANCEL:
                finish();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        relativeLayout_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mAdapter = new PickUpAdapter();

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.onClickListener = this;
        mAdapter.pickid = pickid;

        Utils.setViewTypeForTag(mCancel, ViewType.VIEW_TYPE_CANCEL);
        mCancel.setOnClickListener(this);

        setUpData();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_pick_up;

        Bundle arguments = getArguments();
        if (arguments == null) {
            Toast.makeText(getContext(), "Empty arguments", Toast.LENGTH_SHORT).show();
            return;
        }
        mPickUpList = arguments.getParcelableArrayList(Key.KEY_PICKUP_LIST.getValue());
        pickid = arguments.getString("pickid");
    }

    private void setUpData() {
        if(mPickUpList != null) {


        for (PickUpModel pickUpModel : mPickUpList) {
            mAdapter.data.add(pickUpModel);
        }

        mAdapter.notifyDataSetChanged();
        }
    }
}
