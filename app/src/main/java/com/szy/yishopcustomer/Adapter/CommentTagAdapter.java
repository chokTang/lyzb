package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Goods.GoodsCommentTagModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.GoodsCommentTagViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李威 on 16/12/05.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class CommentTagAdapter extends RecyclerView.Adapter {
    public final int VIEW_TYPE_ITEM = 0;

    public View.OnClickListener onCLickListener;
    public List<Object> data;

    public CommentTagAdapter(List<Object> data) {
        this.data = data;
        setHasStableIds(true);
    }

    public CommentTagAdapter() {
        this.data = new ArrayList<>();
        setHasStableIds(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                return new GoodsCommentTagViewHolder(inflater.inflate(R.layout
                        .item_goods_comment_tag, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_ITEM:
                GoodsCommentTagViewHolder itemViewHolder = (GoodsCommentTagViewHolder) viewHolder;
                GoodsCommentTagModel goodsCommentTagModel = (GoodsCommentTagModel) data.get
                        (position);
                itemViewHolder.textView.setChecked(goodsCommentTagModel.selected);
                itemViewHolder.textView.setText(goodsCommentTagModel.comment_rank);

                Utils.setViewTypeForTag(itemViewHolder.textView, ViewType.VIEW_TYPE_ITEM);
                Utils.setPositionForTag(itemViewHolder.textView, position);
                itemViewHolder.textView.setOnClickListener(onCLickListener);

                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof GoodsCommentTagModel) {
            return VIEW_TYPE_ITEM;
        }
        return -1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
