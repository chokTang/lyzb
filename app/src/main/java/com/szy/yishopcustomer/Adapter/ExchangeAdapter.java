package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Interface.OnOrderButtonListener;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.OrderButtonHelper;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.RadiusBackgroundSpan;
import com.szy.yishopcustomer.ViewHolder.ExchangeViewHolder;
import com.szy.yishopcustomer.ViewModel.ExchangeModel;

import java.util.ArrayList;

/**
 * Created by Smart on 2017/7/6.
 */
public class ExchangeAdapter extends HeaderFooterAdapter<ExchangeModel.DataBean.ListBean> {

    public View.OnClickListener onClickListener;

    public ExchangeAdapter() {
        data = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(mHeaderView != null && viewType == TYPE_HEADER) {
            return new ListHolder(mHeaderView);
        }
        if(mFooterView != null && viewType == TYPE_FOOTER){
            return new ListHolder(mFooterView);
        }

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return createViewHolder(inflater, parent);

    }

    private RecyclerView.ViewHolder createViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = inflater.inflate(R.layout.fragment_exchange_item, parent, false);
        return new ExchangeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(getItemViewType(position) == TYPE_NORMAL){
            if(viewHolder instanceof ExchangeViewHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
                int tempposition = position;
                if(mHeaderView != null) {
                    tempposition = position-1;
                }
                ExchangeModel.DataBean.ListBean object = data.get(tempposition);
                bindShopViewHolder((ExchangeViewHolder) viewHolder, object,
                        tempposition);
                return;
            }
            return;
        }else if(getItemViewType(position) == TYPE_HEADER){
            return;
        }else{
            return;
        }
    }

    private void bindShopViewHolder(ExchangeViewHolder viewHolder, final ExchangeModel.DataBean.ListBean object, final int
            position) {

        Context mContext = viewHolder.itemView.getContext();
        LinearLayout.LayoutParams rlayoutParams = (LinearLayout.LayoutParams) viewHolder.relativeLayout_title.getLayoutParams();
        if(position == 0) {
            rlayoutParams.setMargins(rlayoutParams.leftMargin,0,rlayoutParams.rightMargin,rlayoutParams.bottomMargin);
        } else {
            rlayoutParams.setMargins(rlayoutParams.leftMargin,Utils.dpToPx(mContext,10),rlayoutParams.rightMargin,rlayoutParams.bottomMargin);
        }

        //商品部分
        final ExchangeModel.DataBean.ListBean.GoodsListBean goodsListModel = object.goods_list.get(0);
        if (!Utils.isNull(goodsListModel.goods_image)) {
            ImageLoader.displayImage(Utils.urlOfImage(goodsListModel.goods_image),
                    viewHolder.item_order_list_goods_imageView);
        }

        String points = "积分兑换";
        SpannableString spanString = new SpannableString(points +"  "+ goodsListModel.goods_name);
        spanString.setSpan(new RadiusBackgroundSpan(Color.parseColor("#F0AA4A"), 4,Utils.dpToPx(viewHolder.itemView.getContext(),13),Color.parseColor("#FFFFFF")), 0, points.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        viewHolder.item_order_list_goods_name_textView.setText(spanString);
        viewHolder.fragment_checkout_goods_numberTextView.setText(goodsListModel.goods_points + " 积分");
        viewHolder.fragment_order_list_goods_number.setText("x" + goodsListModel.goods_number);
        viewHolder.item_order_list_goods_attribute_textView.setText(goodsListModel.spec_info);

        Utils.setViewTypeForTag(viewHolder.mItemGoods, ViewType.VIEW_TYPE_ORDER_GOODS);
        Utils.setPositionForTag(viewHolder.mItemGoods, position);
        Utils.setExtraInfoForTag(viewHolder.mItemGoods, Integer.valueOf(goodsListModel
                .order_id));
        viewHolder.mItemGoods.setOnClickListener(onClickListener);

        //标题部分
        viewHolder.item_order_list_shop_name_textView.setText("兑换单号：" +  object.order_sn);
        viewHolder.item_order_list_order_status.setText(object.order_status_format);

        Utils.setViewTypeForTag(viewHolder.itemView, ViewType.VIEW_TYPE_SHOP);
        Utils.setPositionForTag(viewHolder.itemView, position);
        Utils.setExtraInfoForTag(viewHolder.itemView, Integer.valueOf
                (object.shop_id));
        viewHolder.itemView.setOnClickListener(onClickListener);

        //按钮部分
        viewHolder.linearlayout_buttons.removeAllViews();
        Context context = viewHolder.itemView.getContext();

        TextView tv = (TextView) LayoutInflater.from(context).inflate(
                R.layout.item_order_list_textview, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        tv.setLayoutParams(layoutParams);
        tv.setTextColor(Color.parseColor("#999999"));
        tv.setText(object.cancelTip);
        tv.setPadding(0,0,Utils.dpToPx(context,5),0);
        tv.setGravity(Gravity.RIGHT);

        viewHolder.linearlayout_buttons.addView(tv);

        if (object.buttons.size() > 0) {
            viewHolder.linearlayout_buttons.setVisibility(View.VISIBLE);
            OrderButtonHelper.initButtons(context,object.buttons,new OnOrderButtonListener(viewHolder.linearlayout_buttons){
                @Override
                public void setButtons(Button button) {
//                    String button_type = (String) button.getTag(OrderButtonHelper.TAG_BUTTON_TYPE);
                    super.setButtons(button);
                    Utils.setPositionForTag(button, position);
                    Utils.setExtraInfoForTag(button, Integer.valueOf
                            (object.order_id));
                    button.setOnClickListener(onClickListener);
                }

                @Override
                public void commented(Button button) {
                    button.setEnabled(false);
                    linearlayout_buttons.addView(button);
                }
            });

        } else if(TextUtils.isEmpty(object.cancelTip)) {
            viewHolder.linearlayout_buttons.setVisibility(View.GONE);
        }
    }
}