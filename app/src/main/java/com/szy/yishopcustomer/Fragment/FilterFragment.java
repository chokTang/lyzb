package com.szy.yishopcustomer.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;

import com.szy.common.Other.CommonEvent;
import com.szy.yishopcustomer.Adapter.FilterAdapter;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.Filter.FilterChildModel;
import com.szy.yishopcustomer.ViewModel.Filter.FilterGroupModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by 宗仁 on 2016/8/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class FilterFragment extends YSCBaseFragment {

    @BindView(R.id.fragment_filter_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.fragment_filter_clearButton)
    Button mClearButton;
    @BindView(R.id.fragment_filter_confirmButton)
    Button mConfirmButton;

    private FilterAdapter mAdapter;
    private GridLayoutManager.SpanSizeLookup mSpanSizeLookup = new GridLayoutManager
            .SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
            switch (mAdapter.getItemViewType(position)) {
                case FilterAdapter.VIEW_TYPE_ITEM:
                    return 1;
                default:
                    return 3;
            }
        }
    };


    private int getLoctionInAdapter(int p) {
        int i = -1;
        int position = p;
        while (mAdapter.getItemViewType(position) == FilterAdapter.VIEW_TYPE_ITEM) {
            i++;
            position--;
        }
        return i % 3;
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        public GridSpacingItemDecoration() {
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position

            switch (mAdapter.getItemViewType(position)) {
                case FilterAdapter.VIEW_TYPE_ITEM:

                    int i = getLoctionInAdapter(position);

                    switch (i) {
                        case 0:
                            //在左边，加上左边边距
                            outRect.left = Utils.dpToPx(getContext(), 15);
                            outRect.right = Utils.dpToPx(getContext(), 1.6f);
                            break;
                        case 1:
                            //在中间，加上两边的边距
                            outRect.left = Utils.dpToPx(getContext(), 8.4f);
                            outRect.right = Utils.dpToPx(getContext(), 8.4f);
                            break;
                        case 2:
                            //在左边，加上右边边距
                            outRect.left = Utils.dpToPx(getContext(), 1.6f);
                            outRect.right = Utils.dpToPx(getContext(), 15);
                            break;
                    }
                    break;
            }
        }
    }


    @Override
    public void onClick(View view) {
        ViewType viewType = Utils.getViewTypeOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_CLOSE:
                cancel();
                break;
            case VIEW_TYPE_CLEAR:
                clear();
                break;
            case VIEW_TYPE_CONFIRM:
                confirm();
                break;
            default:
                super.onClick(view);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        gridLayoutManager.setSpanSizeLookup(mSpanSizeLookup);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration());

        Utils.setViewTypeForTag(mClearButton, ViewType.VIEW_TYPE_CLEAR);
        mClearButton.setOnClickListener(this);

        Utils.setViewTypeForTag(mConfirmButton, ViewType.VIEW_TYPE_CONFIRM);
        mConfirmButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_filter;

        Bundle arguments = getArguments();
        if (arguments != null && arguments.getParcelableArrayList(Key.KEY_FILTER.getValue()) !=
                null) {
            ArrayList<FilterGroupModel> list = arguments.getParcelableArrayList(Key.KEY_FILTER
                    .getValue());
            mAdapter = new FilterAdapter(list);
        }
    }

    private void cancel() {
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_HIDE_FILTER_VIEW.getValue()));
        finish();
    }

    private void clear() {
        for (FilterGroupModel filterGroupModel : mAdapter.data) {
            for (FilterChildModel filterChildModel : filterGroupModel.children) {
                filterChildModel.selected = false;
                filterChildModel.minimumValue = null;
                filterChildModel.maximumValue = null;
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    private void confirm() {
        Intent intent = new Intent();
        ArrayList<FilterGroupModel> result = new ArrayList<>();
        result.addAll(mAdapter.data);
        intent.putParcelableArrayListExtra(Key.KEY_FILTER.getValue(), result);

        ShopGoodsListFragment.list = mAdapter.data;

        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
