package com.szy.yishopcustomer.Adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Bonus.BonusItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.BlankModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.SlidingButtonView;
import com.szy.yishopcustomer.ViewHolder.DividerViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liwei on 16/11/1.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BonusAdapter extends RecyclerView.Adapter implements SlidingButtonView
        .IonSlidingButtonListener {

    private final int VIEW_BONUS = 0;
    private final int VIEW_BLANK = 1;
    private final int VIEW_DIVIDER = 2;

    public List<Object> data;
    public View.OnClickListener onClickListener;
    private SlidingButtonView mMenu = null;

    public BonusAdapter() {
        this.data = new ArrayList<>();
    }

    public BonusAdapter(List<Object> data) {
        this.data = data;
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;

    }

    public RecyclerView.ViewHolder createBlankViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_divider_one,
                parent, false);
        return new DividerViewHolder(view);
    }

    public RecyclerView.ViewHolder createBonusViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bonus, parent,
                false);
        return new BonusViewHolder(view);
    }

    public RecyclerView.ViewHolder createDividerViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_goods_list_item_empty, parent, false);
        return new DividerViewHolder(view);
    }

    /**
     * 判断是否有菜单打开
     */
    public Boolean menuIsOpen() {
        return mMenu != null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_BONUS:
                return createBonusViewHolder(parent);
            case VIEW_BLANK:
                return createBlankViewHolder(parent);
            case VIEW_DIVIDER:
                return createDividerViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case VIEW_BONUS:
                bindBonusViewHolder((BonusViewHolder) holder, position);
                break;
            case VIEW_BLANK:
                bindBlankViewHolder((DividerViewHolder) holder, position);
                break;
            case VIEW_DIVIDER:
                bindDividerViewHolder((DividerViewHolder) holder, position);
                break;
        }
    }

    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof BonusItemModel) {
            return VIEW_BONUS;
        } else if (object instanceof BlankModel) {
            return VIEW_BLANK;
        } else if (object instanceof CheckoutDividerModel) {
            return VIEW_DIVIDER;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
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

    private void bindBlankViewHolder(DividerViewHolder holder, int position) {
    }

    private void bindBonusViewHolder(BonusViewHolder holder, int position) {
        BonusItemModel item = (BonusItemModel) data.get(position);
        holder.mLayoutContent.getLayoutParams().width = Utils.getWindowWidth(holder
                .mLayoutContent.getContext());

        if (item.min_goods_amount.equals("0.00")) {
            holder.bonusDesc.setText("无门槛");
        } else {
            holder.bonusDesc.setText("购物满" + item.min_goods_amount + "元");
        }
        String format = holder.bonusPlatform.getContext().getString(R.string.issuerFormat);
        String formatTwo = holder.bonusLimit.getContext().getString(R.string.bonusLimit);
        holder.bonusPlatform.setText(String.format(format, item.shop_name));


        String bonusLimit = "";
        if (item.shop_id.equals("0") && item.is_self_shop.equals("1")) {
            bonusLimit = holder.bonusLimit.getContext().getResources().getString(R.string
                    .platformBonusShop) + " ";
        }
        if (item.use_range.equals("0")) {
            if (item.shop_id.equals("0")) {
                bonusLimit += holder.bonusLimit.getContext().getResources().getString(R.string
                        .platformUseNoLimit);
            } else {
                bonusLimit += holder.bonusLimit.getContext().getResources().getString(R.string
                        .shopUseNoLimit);
            }
        } else {
            if (item.shop_id.equals("0")) {
                bonusLimit += holder.bonusLimit.getContext().getResources().getString(R.string
                        .specificCats);
            } else {
                bonusLimit += holder.bonusLimit.getContext().getResources().getString(R.string
                        .specificGoods);
            }
        }

        holder.bonusLimit.setText(String.format(formatTwo, bonusLimit));
        holder.bonusTime.setText(item.start_time_format + "~" + item.end_time_format);
        holder.bonusPriceFormat.setText(Utils.formatMoney(holder.bonusPriceFormat.getContext(), item.bonus_price));

        Utils.setViewTypeForTag(holder.mdelete, ViewType.VIEW_TYPE_DELETE);
        Utils.setPositionForTag(holder.mdelete, position);
        holder.mdelete.setOnClickListener(onClickListener);

        //0未使用；1已使用；2已过期；3即将过期
        if (item.bonus_status.equals("0")) {
            holder.bonusUseButton.setText(holder.bonusUseButton.getContext().getResources()
                    .getString(R.string.useNow));
            holder.bonusStatusLayout.setBackgroundColor(Color.parseColor("#DC4141"));
            holder.bonusNearingDueDate.setVisibility(View.GONE);
            //holder.bonusBackgroundView.setEnabled(true);
            holder.bonusBackgroundView.setBackgroundResource(R.mipmap.bg_bonus_border);

            Utils.setViewTypeForTag(holder.bonusUseButton, ViewType.VIEW_TYPE_BONUS);
            Utils.setPositionForTag(holder.bonusUseButton, position);
            holder.bonusUseButton.setOnClickListener(onClickListener);
        } else if (item.bonus_status.equals("1")) {
            holder.bonusUseButton.setText(holder.bonusUseButton.getContext().getResources()
                    .getString(R.string.haveUsed));
            holder.bonusStatusLayout.setBackgroundColor(Color.parseColor("#c2c2c2"));
            holder.bonusNearingDueDate.setVisibility(View.GONE);
            //holder.bonusBackgroundView.setEnabled(false);
            holder.bonusBackgroundView.setBackgroundResource(R.mipmap.bg_bonus_border_right_dark);
            holder.bonusUseButton.setOnClickListener(null);
        } else if (item.bonus_status.equals("2")) {
            holder.bonusUseButton.setText(holder.bonusUseButton.getContext().getResources()
                    .getString(R.string.outOfDate));
            holder.bonusStatusLayout.setBackgroundColor(Color.parseColor("#c2c2c2"));
            holder.bonusNearingDueDate.setVisibility(View.GONE);
            //holder.bonusBackgroundView.setEnabled(false);
            holder.bonusBackgroundView.setBackgroundResource(R.mipmap.bg_bonus_border_right_dark);
            holder.bonusUseButton.setOnClickListener(null);
        } else if (item.bonus_status.equals("3")) {
            holder.bonusUseButton.setText(holder.bonusUseButton.getContext().getResources()
                    .getString(R.string.useNow));
            holder.bonusStatusLayout.setBackgroundColor(Color.parseColor("#DC4141"));
            holder.bonusNearingDueDate.setVisibility(View.VISIBLE);
            //holder.bonusBackgroundView.setEnabled(true);
            holder.bonusBackgroundView.setBackgroundResource(R.mipmap.bg_bonus_border);

            Utils.setViewTypeForTag(holder.bonusUseButton, ViewType.VIEW_TYPE_BONUS);
            Utils.setPositionForTag(holder.bonusUseButton, position);
            holder.bonusUseButton.setOnClickListener(onClickListener);
        }
    }

    private void bindDividerViewHolder(DividerViewHolder holder, int position) {
    }

    public class BonusViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.bonus_desc)
        public TextView bonusDesc;
        @BindView(R.id.bonus_platform)
        public TextView bonusPlatform;
        @BindView(R.id.bonus_limit)
        public TextView bonusLimit;
        @BindView(R.id.bonus_time)
        public TextView bonusTime;
        @BindView(R.id.bonus_price_format)
        public TextView bonusPriceFormat;
        @BindView(R.id.item_bonus_right_layout)
        public RelativeLayout bonusStatusLayout;
        @BindView(R.id.bonus_use_button)
        public TextView bonusUseButton;
        @BindView(R.id.item_bonus_top_border_view)
        public ImageView bonusBackgroundView;
        @BindView(R.id.tv_delete)
        public TextView mdelete;
        @BindView(R.id.item_bonus_nearing_due_date)
        public ImageView bonusNearingDueDate;

        @BindView(R.id.layout_content)
        public ViewGroup mLayoutContent;

        public BonusViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            ((SlidingButtonView) itemView).setSlidingButtonListener(BonusAdapter.this);
        }

    }
}
