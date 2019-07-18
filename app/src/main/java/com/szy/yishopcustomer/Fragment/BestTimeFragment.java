package com.szy.yishopcustomer.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.common.Adapter.CommonAdapter;
import com.szy.common.ViewHolder.CommonViewHolder;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Checkout.BestTimeModel;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by 宗仁 on 16/7/21.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BestTimeFragment extends YSCBaseFragment {
    private static final String TIME_ONE = "09:00-15:00";
    private static final String TIME_TWO = "15:00-19:00";
    private static final String TIME_THREE = "19:00-22:00";
    @BindView(R.id.fragment_best_time_leftListView)
    ListView mLeftListView;
    @BindView(R.id.fragment_best_time_rightListView)
    ListView mRightListView;
    @BindView(R.id.fragment_best_time_cancelButton)
    Button mCancelButton;
    @BindView(R.id.fragment_best_time_cancelArea)
    TextView mCancelArea;
    private int mCurrentDateId;
    private ArrayList<BestTimeModel> mLeftData;
    private ArrayList<String> mRightData;
    private CommonAdapter<BestTimeModel, BestTimeViewHolder> mLeftAdapter;
    private CommonAdapter<String, BestTimeViewHolder> mRightAdapter;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_best_time_cancelButton:
                getActivity().finish();
                break;
            case R.id.fragment_best_time_cancelArea:
                getActivity().finish();
                break;
            default:
                super.onClick(view);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, parent, savedInstanceState);
        mLeftListView.setAdapter(mLeftAdapter);
        mLeftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapterView.setSelection(i);
                mCurrentDateId = i;
                mRightAdapter.setData(getRightData(i));
                mRightAdapter.notifyDataSetChanged();
            }
        });
        mRightListView.setAdapter(mRightAdapter);
        mRightListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String time = mRightAdapter.getData().get(i);
                BestTimeModel bestTimeModel = mLeftAdapter.getData().get(mCurrentDateId);
                String date = bestTimeModel.name + bestTimeModel.week;
                Intent result = new Intent();
                result.putExtra(Key.KEY_BEST_TIME.getValue(), date + ":" + time);
                getActivity().setResult(Activity.RESULT_OK, result);
                getActivity().finish();
            }
        });
        mLeftListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        mCancelButton.setOnClickListener(this);
        mCancelArea.setOnClickListener(this);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments == null) {
            Toast.makeText(getContext(), R.string.emptyArguments, Toast.LENGTH_SHORT).show();
            return;
        }
        mCurrentDateId = 0;
        mLeftData = arguments.getParcelableArrayList(Key.KEY_BEST_TIME.getValue());

        mLayoutId = R.layout.fragment_best_time;

        mLeftAdapter = new CommonAdapter<BestTimeModel, BestTimeViewHolder>(mLeftData, R.layout
                .fragment_best_time_item_date) {
            @Override
            public void onBindViewHolder(int position, int viewType, BestTimeViewHolder
                    viewHolder, BestTimeModel item) {
                viewHolder.titleTextView.setText(String.format(getString(R.string
                        .formatBestTimeDate), item.name, item.week));
            }

            @Override
            public BestTimeViewHolder onCreateViewHolder(int position, View itemView, int
                    viewType) {
                BestTimeViewHolder viewHolder = new BestTimeViewHolder(itemView);
                return viewHolder;
            }

        };

        mRightAdapter = new CommonAdapter<String, BestTimeViewHolder>(R.layout
                .fragment_best_time_item_time) {
            @Override
            public BestTimeViewHolder onCreateViewHolder(int position, View itemView, int
                    viewType) {
                BestTimeViewHolder viewHolder = new BestTimeViewHolder(itemView);
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(int position, int viewType, BestTimeViewHolder
                    viewHolder, String item) {
                viewHolder.titleTextView.setText(item);
            }
        };
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mLeftListView.performItemClick(null, 0, 0);
    }

    private ArrayList<String> getRightData(int dateId) {
        BestTimeModel bestTimeModel = mLeftData.get(dateId);
        ArrayList<String> timeList = new ArrayList<>();
        if (bestTimeModel.time1.equals("1")) {
            timeList.add(TIME_ONE);
        }
        if (bestTimeModel.time2.equals("1")) {
            timeList.add(TIME_TWO);
        }
        if (bestTimeModel.time3.equals("1")) {
            timeList.add(TIME_THREE);
        }
        return timeList;
    }

    protected class BestTimeViewHolder extends CommonViewHolder {
        @BindView(R.id.fragment_best_time_titleTextView)
        public TextView titleTextView;

        public BestTimeViewHolder(View view) {
            super(view);
        }
    }
}
