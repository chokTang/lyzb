package com.szy.yishopcustomer.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ResponseModel.Message.ListModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.SlidingButtonView;
import com.szy.yishopcustomer.ViewHolder.DividerViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zongren on 16/7/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class MessageAdapter extends RecyclerView.Adapter implements SlidingButtonView
        .IonSlidingButtonListener {
    private static final int ITEM_TYPE_MESSAGE = 0;
    private static final int ITEM_TYPE_BLANK = 1;
    public static RelativeLayout.OnClickListener onClickListener;
    public List<Object> data;

    private SlidingButtonView mMenu = null;

    public MessageAdapter() {
        data = new ArrayList<>();
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;

    }
    /**
     * 判断是否有菜单打开
     */
    public Boolean menuIsOpen() {
        return mMenu != null;
    }

    /**
     * 滑动或者点击了Item监听
     *
     * @param slidingButtonView
     */
    @Override
    public void onDownOrMove(SlidingButtonView slidingButtonView) {
        if (menuIsOpen()) {
            if (mMenu != slidingButtonView) {
                closeMenu();
            }
        }
    }

    /**
     * 删除菜单打开信息接收
     */
    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (SlidingButtonView) view;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM_TYPE_MESSAGE:
                return createMessageViewHolder(parent, inflater);
            case ITEM_TYPE_BLANK:
                return createBlankViewHolder(parent, inflater);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM_TYPE_MESSAGE:
                MessageViewHolder viewHolder = (MessageViewHolder) holder;
                ListModel model = (ListModel) data.get(position);
                viewHolder.titleTextView.setText(model.title);
                viewHolder.contentTextView.setText(model.content);
                viewHolder.dateTextView.setText(Utils.date(model.send_time));

                if (!Utils.isNull(model.read_time)) {
                    viewHolder.messageStatus.setVisibility(View.GONE);
                    viewHolder.mmark.setVisibility(View.GONE);
                } else {
                    viewHolder.messageStatus.setVisibility(View.VISIBLE);
                    viewHolder.mmark.setVisibility(View.VISIBLE);
                }
                if (model.push_type != null) {
                    if (model.push_type.equals("0")) {
                        viewHolder.iconImageView.setBackgroundResource(R.mipmap.bg_message_goods);
                    } else if (model.push_type.equals("1")) {
                        viewHolder.iconImageView.setBackgroundResource(R.mipmap.bg_message_shop);
                    } else if (model.push_type.equals("2")) {
                        viewHolder.iconImageView.setBackgroundResource(R.mipmap.bg_message_article);
                    } else if (model.push_type.equals("3")) {
                        viewHolder.iconImageView.setBackgroundResource(R.mipmap
                                .bg_message_category);
                    } else if (model.push_type.equals("4")) {
                        viewHolder.iconImageView.setBackgroundResource(R.mipmap.bg_message_goods);
                    } else if (model.push_type.equals("5")) {
                        viewHolder.iconImageView.setBackgroundResource(R.mipmap.bg_message_brand);
                    } else if (model.push_type.equals("6")) {
                        viewHolder.iconImageView.setBackgroundResource(R.mipmap.bg_message_link);
                    } else {
                        viewHolder.iconImageView.setBackgroundResource(R.mipmap.bg_message_notice);
                    }
                } else {
                    viewHolder.iconImageView.setBackgroundResource(R.mipmap.bg_message_notice);
                }

                viewHolder.imageLayout.getLayoutParams().width = Utils.getWindowWidth(viewHolder
                        .imageLayout.getContext());


                Utils.setViewTypeForTag(viewHolder.mdelete, ViewType.VIEW_TYPE_DELETE);
                Utils.setPositionForTag(viewHolder.mdelete, position);
                viewHolder.mdelete.setOnClickListener(onClickListener);
                Utils.setViewTypeForTag(viewHolder.mmark, ViewType.VIEW_TYPE_MARK);
                Utils.setPositionForTag(viewHolder.mmark, position);
                viewHolder.mmark.setOnClickListener(onClickListener);

                Utils.setViewTypeForTag(viewHolder.imageLayout, ViewType.VIEW_TYPE_MESSAGE);
                Utils.setPositionForTag(viewHolder.imageLayout, position);
                Utils.setExtraInfoForTag(viewHolder.imageLayout, Integer.valueOf(model.msg_id));
                viewHolder.imageLayout.setOnClickListener(onClickListener);
                break;
            case ITEM_TYPE_BLANK:
                DividerViewHolder blankViewHolder = (DividerViewHolder) holder;
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof ListModel) {
            return ITEM_TYPE_MESSAGE;
        } else if (object instanceof CheckoutDividerModel) {
            return ITEM_TYPE_BLANK;
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
    private RecyclerView.ViewHolder createBlankViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.fragment_goods_list_item_empty, parent, false);
        return new DividerViewHolder(view);
    }

    @NonNull
    private RecyclerView.ViewHolder createMessageViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.message_layout)
        public RelativeLayout imageLayout;
        @BindView(R.id.item_message_imageView)
        public ImageView iconImageView;
        @BindView(R.id.item_message_titleTextView)
        public TextView titleTextView;
        @BindView(R.id.item_message_contentTextView)
        public TextView contentTextView;
        @BindView(R.id.item_message_dateTextView)
        public TextView dateTextView;
        @BindView(R.id.item_message_status)
        public TextView messageStatus;
        @BindView(R.id.tv_delete)
        public TextView mdelete;;
        @BindView(R.id.tv_mark)
        public TextView mmark;;

        public MessageViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            ((SlidingButtonView) itemView).setSlidingButtonListener(MessageAdapter.this);
        }

    }

}
