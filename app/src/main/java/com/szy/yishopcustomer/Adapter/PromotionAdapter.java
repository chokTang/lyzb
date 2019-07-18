package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.PromotionListModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.PromotionViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Smart on 2017/11/21.
 */

public class PromotionAdapter extends HeaderFooterAdapter<PromotionListModel.PromotionList> {

    public View.OnClickListener onClickListener;

    public int openPosition = 0;
    private boolean isShowWraning = false;

    private View.OnClickListener openListener;
    private View.OnClickListener numberListener;
    private View.OnClickListener verListener;

    public PromotionAdapter() {
        data = new ArrayList<>();

        openListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                if(position != openPosition) {
                    openPosition = position;
                    notifyDataSetChanged();
                }
            }
        };

        verListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Utils.getPositionOfTag(v);
                if(position != openPosition) {
                    openPosition = position;
                }
                if(Verification()) {
                    onClickListener.onClick(v);
                }
            }
        };

        numberListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                TextView tv = (TextView) v.getTag(R.id.goods_number);
                PromotionListModel.PromotionList promotionList = data.get(position);
                switch (v.getId()) {
                    case R.id.minus_button:
                        if(promotionList.buy_number > 1) {
                            promotionList.buy_number -= 1;
                        }
                        break;
                    case R.id.plus_button:
                            promotionList.buy_number += 1;
                        break;
                }
                tv.setText(promotionList.buy_number + "");
            }
        };
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new ListHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new ListHolder(mFooterView);
        }

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return createViewHolder(inflater, parent);

    }

    private RecyclerView.ViewHolder createViewHolder(LayoutInflater inflater, ViewGroup
            parent) {
        View view = inflater.inflate(R.layout.fragment_promotion_item, parent, false);
        return new PromotionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            if (viewHolder instanceof PromotionViewHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
                int tempposition = position;
                if (mHeaderView != null) {
                    tempposition = position - 1;
                }
                PromotionListModel.PromotionList object = data.get(tempposition);
                bindViewHolder((PromotionViewHolder) viewHolder, object,
                        tempposition);
                return;
            }
            return;
        } else if (getItemViewType(position) == TYPE_HEADER) {
            return;
        } else {
            return;
        }
    }

    private void bindViewHolder(PromotionViewHolder viewHolder, PromotionListModel.PromotionList object, final int
            position) {
        Context mContext = viewHolder.itemView.getContext();
        List<PromotionListModel.ActInfo.GoodsInfoBean> goods = object.act_info.goods_info;

        viewHolder.linearlayout_control.setTag(position);
        viewHolder.linearlayout_control.setOnClickListener(openListener);
        viewHolder.textViewTitle.setText(object.act_info.act_name);

        if("0".equals(object.act_info.price_mode)) {
            viewHolder.textViewActPrice.setText(object.act_info.act_price_format);
        } else {
            viewHolder.textViewActPrice.setText(object.act_info.act_price_min_format + " - " + object.act_info.act_price_max_format);
        }

        if(object.act_info.min_all_goods == object.act_info.max_all_goods){
            viewHolder.textViewAllPrice.setText(object.act_info.min_all_goods_format);
        }else {
            viewHolder.textViewAllPrice.setText(object.act_info.min_all_goods_format + " - " + object.act_info.max_all_goods_format);
        }

        viewHolder.textViewAllPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
        viewHolder.item_cart_goods_number.setText(object.buy_number+"");

        viewHolder.item_cart_goods_minus_button.setOnClickListener(numberListener);
        viewHolder.item_cart_goods_minus_button.setTag(position);
        viewHolder.item_cart_goods_minus_button.setTag(R.id.goods_number,viewHolder.item_cart_goods_number);

        viewHolder.item_cart_goods_add_button.setOnClickListener(numberListener);
        viewHolder.item_cart_goods_add_button.setTag(position);
        viewHolder.item_cart_goods_add_button.setTag(R.id.goods_number,viewHolder.item_cart_goods_number);

        Utils.setViewTypeForTag(viewHolder.button_add_cart, ViewType.VIEW_TYPE_ADD_TO_CART);
        Utils.setPositionForTag(viewHolder.button_add_cart, position);
        viewHolder.button_add_cart.setOnClickListener(verListener);

        Utils.setViewTypeForTag(viewHolder.button_buy_now, ViewType.VIEW_TYPE_BUY_NOW);
        Utils.setPositionForTag(viewHolder.button_buy_now, position);
        viewHolder.button_buy_now.setOnClickListener(verListener);

        if("1".equals(object.act_info.discount_show)) {
            viewHolder.textViewSave.setVisibility(View.VISIBLE);
                viewHolder.textViewSave.setText("省" + object.act_info.min_goods_diff_format +" - " + object.act_info.max_goods_diff_format);

        } else {
            viewHolder.textViewSave.setVisibility(View.GONE);
        }

        if (position == openPosition) {
            viewHolder.linearlayout_visible.setVisibility(View.VISIBLE);
            viewHolder.linearlayout_invisible.setVisibility(View.GONE);
            viewHolder.imageViewArrow.setImageResource(R.mipmap.bg_arrow_down_gray);

            viewHolder.linearlayout_goods.removeAllViews();

            if (goods != null && goods.size() > 0) {
                for (int i = 0, len = goods.size(); i < len; i++) {
                    PromotionListModel.ActInfo.GoodsInfoBean goodsInfoBean = goods.get(i);
                    View goodView = LayoutInflater.from(mContext).inflate(
                            R.layout.fragment_promotion_item_goods, null);

                    ImageView imageView = (ImageView) goodView.findViewById(R.id.imageViewGoodNmae);
                    View linearlayout_attr = goodView.findViewById(R.id.linearlayout_attr);
                    TextView textViewAttr = (TextView) goodView.findViewById(R.id.textViewAttr);
                    TextView textViewTip = (TextView) goodView.findViewById(R.id.textViewTip);
                    TextView textViewGoodName = (TextView) goodView.findViewById(R.id.textViewGoodName);

                    ImageLoader.displayImage(Utils.urlOfImage(goodsInfoBean.goods_image),imageView);
                    textViewGoodName.setText(goodsInfoBean.goods_name);

                    if(TextUtils.isEmpty(goodsInfoBean.sku_id) || "0".equals(goodsInfoBean.sku_id)) {
                        textViewTip.setText("");
                    } else {
                        textViewTip.setText("库存："+ goodsInfoBean.goods_number + "件");
                    }

                    if(isShowWraning) {
                        int goodNum = Integer.parseInt(goodsInfoBean.goods_number);
                        if(TextUtils.isEmpty(goodsInfoBean.sku_id) || "0".equals(goodsInfoBean.sku_id)) {
                            textViewTip.setText("请选择商品信息！");
                        } else if(goodNum <object.buy_number) {
                            textViewTip.setText("商品库存不足！");
                        }
                    }

                    Utils.setViewTypeForTag(imageView, ViewType.VIEW_TYPE_GOODS);
                    Utils.setPositionForTag(imageView, position);
                    Utils.setExtraInfoForTag(imageView,i);
                    imageView.setOnClickListener(onClickListener);

                    Utils.setViewTypeForTag(textViewGoodName, ViewType.VIEW_TYPE_GOODS);
                    Utils.setPositionForTag(textViewGoodName, position);
                    Utils.setExtraInfoForTag(textViewGoodName,i);
                    textViewGoodName.setOnClickListener(onClickListener);

                    if(!Utils.isNull(goodsInfoBean.spec_list)) {
                        linearlayout_attr.setVisibility(View.VISIBLE);

                        Utils.setViewTypeForTag(linearlayout_attr, ViewType.VIEW_TYPE_SELECT);
                        Utils.setExtraInfoForTag(linearlayout_attr,i);
                        Utils.setPositionForTag(linearlayout_attr, position);
                        linearlayout_attr.setOnClickListener(onClickListener);

                        if(!TextUtils.isEmpty(goodsInfoBean.sku_id) && !TextUtils.isEmpty(goodsInfoBean.specValue)) {
                            textViewAttr.setText(goodsInfoBean.specValue);
                        }
                    } else {
                        linearlayout_attr.setVisibility(View.GONE);
                    }

                    viewHolder.linearlayout_goods.addView(goodView);
                }
            }
        } else {
            viewHolder.linearlayout_visible.setVisibility(View.GONE);
            viewHolder.linearlayout_invisible.setVisibility(View.VISIBLE);
            viewHolder.imageViewArrow.setImageResource(R.mipmap.btn_right_arrow);

            if (goods != null && goods.size() > 0) {
                for (int i = 0, len = goods.size(); i < len; i++) {
                    PromotionListModel.ActInfo.GoodsInfoBean goodsInfoBean = goods.get(i);

                    if(i < viewHolder.linearlayout_invisible.getChildCount()) {
                        ImageView im = (ImageView) viewHolder.linearlayout_invisible.getChildAt(i);
                        im.setVisibility(View.VISIBLE);
                        ImageLoader.displayImage(Utils.urlOfImage(goodsInfoBean.goods_image),im);

                        Utils.setViewTypeForTag(im, ViewType.VIEW_TYPE_GOODS);
                        Utils.setPositionForTag(im, position);
                        Utils.setExtraInfoForTag(im,i);
                        im.setOnClickListener(onClickListener);
                    }
                }
            }
        }
    }

    private boolean updateShowWarn(boolean flag){
        isShowWraning = !flag;
        if(isShowWraning) {
            this.notifyDataSetChanged();
        }
        return flag;
    }

    public boolean Verification(){
        PromotionListModel.PromotionList promotionList = data.get(openPosition);

        int buy_num = promotionList.buy_number;

        for(int i = 0 , len = promotionList.act_info.goods_info.size() ; i < len ;  i ++ ){
            PromotionListModel.ActInfo.GoodsInfoBean goodsInfoBean = promotionList.act_info.goods_info.get(i);

            if(TextUtils.isEmpty(goodsInfoBean.sku_id) || "0".equals(goodsInfoBean.sku_id)) {
                return updateShowWarn(false);
            }

            int goodNum = Integer.parseInt(goodsInfoBean.goods_number);
            if(goodNum < buy_num) {
                return updateShowWarn(false);
            }
        }
        return updateShowWarn(true);
    }
}