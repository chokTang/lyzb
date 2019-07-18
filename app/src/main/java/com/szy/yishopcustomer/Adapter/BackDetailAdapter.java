package com.szy.yishopcustomer.Adapter;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.RelativeLayout;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.BackDetailViewHolder.BackDetailTitleFourViewHolder;
import com.szy.yishopcustomer.ViewHolder.BackDetailViewHolder.BackDetailTitleOneViewHolder;
import com.szy.yishopcustomer.ViewHolder.BackDetailViewHolder.BackDetailTitleThreeViewHolder;
import com.szy.yishopcustomer.ViewHolder.BackDetailViewHolder.BackDetailTitleTwoViewHolder;
import com.szy.yishopcustomer.ViewModel.BackDetailModel.BackDetailTitleModel;
import com.szy.yishopcustomer.ViewModel.BackDetailModel.BackDetailTitleTwoModel;
import com.szy.yishopcustomer.ViewModel.BackDetailModel.BackDetailValueImageModel;
import com.szy.yishopcustomer.ViewModel.BackDetailModel.BackDetailValueModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhfieng on 16/7/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BackDetailAdapter extends RecyclerView.Adapter {
    private final int ITEM_TYPE_TITLE = 0;
    private final int ITEM_TYPE_TITLE_TWO = 1;
    private final int ITEM_TYPE_VALUE = 3;
    private final int ITEM_TYPE_VALUE_IMAGE = 4;
    public static RelativeLayout.OnClickListener onClickListener;
    public List<Object> data;
    private boolean is = true;

    public BackDetailAdapter() {
        data = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM_TYPE_TITLE:
                return createTitleViewHolder(parent, inflater);
            case ITEM_TYPE_TITLE_TWO:
                return createTitleTwoViewHolder(parent, inflater);
            case ITEM_TYPE_VALUE:
                return createValueViewHolder(parent, inflater);
            case ITEM_TYPE_VALUE_IMAGE:
                return createValueImageViewHolder(parent, inflater);
            default:
                return null;
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM_TYPE_TITLE:
                setUpTitle((BackDetailTitleOneViewHolder) holder, position);
                break;
            case ITEM_TYPE_TITLE_TWO:
                setUpTitleTwo((BackDetailTitleTwoViewHolder) holder, position);
                break;
            case ITEM_TYPE_VALUE:
                setUpValue((BackDetailTitleFourViewHolder) holder, position);
                break;
            case ITEM_TYPE_VALUE_IMAGE:
                setUpImageValue((BackDetailTitleThreeViewHolder) holder, position);
                break;
        }

    }

    private void setUpImageValue(BackDetailTitleThreeViewHolder holder, int position) {
        BackDetailTitleThreeViewHolder viewHolder = holder;
        BackDetailValueImageModel mData = (BackDetailValueImageModel) data.get(position);
        viewHolder.mName.setText(mData.name);
        viewHolder.mValue.setText(mData.value);
        if (is) {
            if (!Utils.isNull(mData.url)) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(viewHolder
                        .mRecyclerView.getContext());
                layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
                ArrayList<String> imageList = new ArrayList<String>();
                for (int i = 0; i < mData.url.size(); i++) {
                    if (!Utils.isNull(mData.url.get(i)) && !mData.url.get(i).toString().equals
                            ("")) {
                        imageList.add(mData.url.get(i).toString());
                    }
                }
                viewHolder.mRecyclerView.setLayoutManager(layoutManager);
                GoodsCommentImageAdapter goodsCommentImageAdapter = new GoodsCommentImageAdapter
                        (imageList);
                goodsCommentImageAdapter.itemPosition = position;
                viewHolder.mRecyclerView.setAdapter(goodsCommentImageAdapter);
            }
        }
        is = false;
    }

    private void setUpValue(BackDetailTitleFourViewHolder holder, int position) {
        BackDetailTitleFourViewHolder viewHolder = holder;
        BackDetailValueModel mData = (BackDetailValueModel) data.get(position);
        viewHolder.mName.setText(mData.name);
        viewHolder.mValue.setText(mData.value);
        if (mData.color != 0) {
            viewHolder.mValue.setTextColor(mData.color);
        } else {
            viewHolder.mValue.setTextColor(ContextCompat.getColor(viewHolder.mValue.getContext(),
                    R.color.colorOne));
        }
    }

    private void setUpTitle(BackDetailTitleOneViewHolder holder, int position) {
        BackDetailTitleOneViewHolder viewHolder = holder;
        BackDetailTitleModel model = (BackDetailTitleModel) data.get(position);

        if (model.buttonType == 4) {//表示后台处理成功
            viewHolder.mStatusImageview.setImageResource(R.mipmap.ic_success_red);
        } else {
            viewHolder.mStatusImageview.setImageResource(R.mipmap.ic_remind_red);
        }

        viewHolder.mTitle.setText(model.title);
        if (!Utils.isNull(model.nameOne)) {
            viewHolder.mNameOne.setVisibility(View.VISIBLE);
            viewHolder.mNameOne.setText(Utils.spanStringColor(ContextCompat.getColor(viewHolder
                    .mTitle.getContext(), R.color.colorOne), ContextCompat.getColor(viewHolder
                    .mTitle.getContext(), R.color.colorPrimary), model.nameOne));
        } else {
            viewHolder.mNameOne.setVisibility(View.GONE);
        }

        if (!Utils.isNull(model.nameTwo)) {
            viewHolder.mNameTwo.setVisibility(View.VISIBLE);
            viewHolder.mNameTwo.setText(Utils.spanStringColor(ContextCompat.getColor(viewHolder
                    .mTitle.getContext(), R.color.colorOne), ContextCompat.getColor(viewHolder
                    .mTitle.getContext(), R.color.colorPrimary), model.nameTwo));
        } else {
            viewHolder.mNameTwo.setVisibility(View.GONE);
        }
        if (!Utils.isNull(model.nameThree)) {
            viewHolder.mNameThree.setVisibility(View.VISIBLE);
            viewHolder.mNameThree.setText(Utils.spanStringColor(ContextCompat.getColor(viewHolder
                    .mTitle.getContext(), R.color.colorOne), ContextCompat.getColor(viewHolder
                    .mTitle.getContext(), R.color.colorPrimary), model.nameThree));
        } else {
            viewHolder.mNameThree.setVisibility(View.GONE);
        }

        //按钮
        if(TextUtils.isEmpty(model.textOne)&&Utils.isNull(model.textTwo)){
            viewHolder.mButtonLayout.setVisibility(View.GONE);
        }else {
            viewHolder.mButtonLayout.setVisibility(View.VISIBLE);

            if (!TextUtils.isEmpty(model.textOne)) {
                viewHolder.mTextViewOne.setVisibility(View.VISIBLE);
                viewHolder.mTextViewOne.setText(model.textOne);
            } else {
                viewHolder.mTextViewOne.setVisibility(View.GONE);
            }

            if (!Utils.isNull(model.textTwo)) {
                viewHolder.mTextViewTwo.setVisibility(View.VISIBLE);
                viewHolder.mTextViewTwo.setText(model.textTwo);
            } else {
                viewHolder.mTextViewTwo.setVisibility(View.GONE);
            }
        }

        if (!Utils.isNull(model.textThree)) {
            viewHolder.mTextViewThree.setVisibility(View.VISIBLE);
            viewHolder.mTextViewThree.setText(model.textThree);
        } else {
            viewHolder.mTextViewThree.setVisibility(View.GONE);
        }

        Utils.setViewTypeForTag(viewHolder.mTextViewOne, ViewType.VIEW_TYPE_BACK_DETAIL_BUTTON_ONE);
        Utils.setPositionForTag(viewHolder.mTextViewOne, position);
        Utils.setExtraInfoForTag(viewHolder.mTextViewOne, model.buttonType);
        viewHolder.mTextViewOne.setOnClickListener(onClickListener);

        Utils.setViewTypeForTag(viewHolder.mTextViewTwo, ViewType.VIEW_TYPE_BACK_DETAIL_BUTTON_TWO);
        Utils.setPositionForTag(viewHolder.mTextViewTwo, position);
        Utils.setExtraInfoForTag(viewHolder.mTextViewOne, model.buttonType);
        viewHolder.mTextViewTwo.setOnClickListener(onClickListener);
    }

    private void setUpTitleTwo(BackDetailTitleTwoViewHolder holder, int position) {
        BackDetailTitleTwoViewHolder viewHolder = holder;
        BackDetailTitleTwoModel model = (BackDetailTitleTwoModel) data.get(position);
        holder.mTitle.setText(model.title);
    }

    @Override
    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof BackDetailTitleModel) {
            return ITEM_TYPE_TITLE;
        } else if (object instanceof BackDetailTitleTwoModel) {
            return ITEM_TYPE_TITLE_TWO;
        } else if (object instanceof BackDetailValueModel) {
            return ITEM_TYPE_VALUE;
        } else if (object instanceof BackDetailValueImageModel) {
            return ITEM_TYPE_VALUE_IMAGE;
        } else {
            return -1;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(ArrayList<Object> data) {
        this.data = data;
    }

    @NonNull
    private RecyclerView.ViewHolder createTitleTwoViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.item_back_detail_two, parent, false);
        return new BackDetailTitleTwoViewHolder(view);
    }

    @NonNull
    private RecyclerView.ViewHolder createTitleViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.item_back_detail_one, parent, false);
        return new BackDetailTitleOneViewHolder(view);
    }

    private RecyclerView.ViewHolder createValueViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.item_back_detail_four, parent, false);
        return new BackDetailTitleFourViewHolder(view);
    }

    private RecyclerView.ViewHolder createValueImageViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.item_back_detail_three, parent, false);
        return new BackDetailTitleThreeViewHolder(view);
    }
}
