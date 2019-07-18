package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szy.common.Util.ImageLoader;
import com.szy.common.View.SquareImageView;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.GoodsModel;
import com.szy.yishopcustomer.Util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImgShoppingGoodsAdapter extends HeaderFooterAdapter {
    public View.OnClickListener onCLickListener;
    public List<Object> data;

    public ImgShoppingGoodsAdapter(List<Object> data) {
        this.data = data;
    }

    public ImgShoppingGoodsAdapter() {
        this.data = new ArrayList<>();
        setHasStableIds(true);
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
        return new ImgShoppingGoodsItemViewHolder(inflater.inflate(R.layout.item_fragment_imgshopping, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            int tempposition = position;
            if (mHeaderView != null) {
                tempposition = position - 1;
            }
            Object itemData = data.get(tempposition);
            if (itemData instanceof GoodsModel) {
                GoodsModel goods = (GoodsModel) itemData;
                ImgShoppingGoodsItemViewHolder holder = (ImgShoppingGoodsItemViewHolder) viewHolder;
                holder.item.setOnClickListener(onCLickListener);
                Utils.setViewTypeForTag(holder.item, ViewType.VIEW_TYPE_GOODS);
                Utils.setPositionForTag(holder.item, position);
                holder.goodsName.setText(goods.goods_name);
                ImageLoader.displayImage(Utils.urlOfImage(goods.goods_image), holder.goodsImg);
                holder.price.setText(Utils.formatMoney(viewHolder.itemView.getContext(), goods.goods_price));
                if (!TextUtils.isEmpty(goods.max_integral_num) && !"0".equals(goods.max_integral_num)) {
                    holder.discount.setText("+" + Utils.formatMoney(viewHolder.itemView.getContext(), goods.max_integral_num));
                } else {
                    holder.discount.setText("");
                }
                holder.tvBxPrice.setText(goods.goods_bxprice_format);
            }
        }else if (getItemViewType(position) == TYPE_HEADER) {
            return;
        } else {
            return;
        }
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ImgShoppingGoodsItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.goodsImg)
        SquareImageView goodsImg;
        @BindView(R.id.goodsName)
        TextView goodsName;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.discount)
        TextView discount;
        @BindView(R.id.tv_bx_price)
        TextView tvBxPrice;
        @BindView(R.id.item)
        RelativeLayout item;

        public ImgShoppingGoodsItemViewHolder(View view) {
            super(view);
//            R.layout.item_fragment_imgshopping
            ButterKnife.bind(this, view);
        }
    }
}
