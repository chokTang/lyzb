package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.ResponseModel.ScanShop.GoodsListModel;
import com.szy.yishopcustomer.ResponseModel.ScanShop.ListModel;
import com.szy.yishopcustomer.ResponseModel.ScanShop.PageModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.DividerViewHolder;
import com.szy.yishopcustomer.ViewHolder.ScanShopBlankViewHolder;
import com.szy.yishopcustomer.ViewHolder.ScanShopGoodsViewHolder;
import com.szy.yishopcustomer.ViewHolder.ScanShopTitleViewHolder;

import java.util.List;

/**
 * Created by liuzhifeng on 2016/9/7.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ScanShopAdapter extends RecyclerView.Adapter {
    public static final int ITEM_TYPE_GOODS = 0;
    public static final int ITEM_TYPE_SHOP = 1;
    public static final int ITEM_TYPE_TITLE = 2;
    private static final int ITEM_TYPE_EMPTY = 3;
    public List<Object> data;
    public View.OnClickListener onClickListener;

    public ScanShopAdapter(List<Object> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_GOODS:
                return new ScanShopGoodsViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_scan_shop_goods, parent, false));
            case ITEM_TYPE_SHOP:
                return new ScanShopTitleViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_scan_shop_title, parent, false));
            case ITEM_TYPE_TITLE:
                return new ScanShopBlankViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_scan_item_blank, parent, false));
            case ITEM_TYPE_EMPTY:
                return new DividerViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_goods_list_item_empty, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case ITEM_TYPE_GOODS:
                ScanShopGoodsViewHolder scanShopGoodsViewHolder = (ScanShopGoodsViewHolder)
                        viewHolder;
                final GoodsListModel goodsListModel = (GoodsListModel) data.get(position);
                scanShopGoodsViewHolder.mGoodsName.setText(goodsListModel.goods_name);

                scanShopGoodsViewHolder.mPriceTextView.setText(goodsListModel.goods_price);
                if (!Utils.isNull(goodsListModel.goods_image)) {
                    ImageLoader.displayImage(Utils.urlOfImage(goodsListModel.goods_image),
                            scanShopGoodsViewHolder.mImageView);
                }
                if (!Utils.isNull(goodsListModel.cart_num) && !goodsListModel.cart_num.equals
                        ("0")) {
                    scanShopGoodsViewHolder.mGoodsNumberTextView.setText(goodsListModel.cart_num
                            + "");
                    scanShopGoodsViewHolder.mGoodsNumberTextView.setVisibility(View.VISIBLE);
                    scanShopGoodsViewHolder.mMinusImageView.setVisibility(View.VISIBLE);
                } else {
                    scanShopGoodsViewHolder.mGoodsNumberTextView.setVisibility(View.INVISIBLE);
                    scanShopGoodsViewHolder.mMinusImageView.setVisibility(View.INVISIBLE);
                }
                Utils.setViewTypeForTag(scanShopGoodsViewHolder.mGoodsLayout, ViewType
                        .VIEW_TYPE_SCAN_SHOP_GOODS);
                Utils.setExtraInfoForTag(scanShopGoodsViewHolder.mGoodsLayout, goodsListModel
                        .goods_id);
                Utils.setPositionForTag(scanShopGoodsViewHolder.mGoodsLayout, position);
                scanShopGoodsViewHolder.mGoodsLayout.setOnClickListener(onClickListener);

                Utils.setViewTypeForTag(scanShopGoodsViewHolder.mPlusImageView, ViewType
                        .VIEW_TYPE_SCAN_SHOP_PLUS);
                Utils.setExtraInfoForTag(scanShopGoodsViewHolder.mPlusImageView, goodsListModel
                        .goods_id);
                Utils.setPositionForTag(scanShopGoodsViewHolder.mPlusImageView, position);
                scanShopGoodsViewHolder.mPlusImageView.setOnClickListener(onClickListener);

                Utils.setViewTypeForTag(scanShopGoodsViewHolder.mMinusImageView, ViewType
                        .VIEW_TYPE_SCAN_SHOP_MINUS);
                Utils.setExtraInfoForTag(scanShopGoodsViewHolder.mMinusImageView, goodsListModel
                        .goods_id);
                Utils.setPositionForTag(scanShopGoodsViewHolder.mMinusImageView, position);
                scanShopGoodsViewHolder.mMinusImageView.setOnClickListener(onClickListener);

                scanShopGoodsViewHolder.percentSaleProgressBar.setProgress(TextUtils.isEmpty(goodsListModel.sold_rate) ? 0 : Integer.parseInt(goodsListModel.sold_rate));
                scanShopGoodsViewHolder.percentSaleTextView.setText("已售"+(TextUtils.isEmpty(goodsListModel.sold_rate) ? "0" : goodsListModel.sold_rate)+"%");
                if (!TextUtils.isEmpty(goodsListModel.max_integral_num) && Integer.parseInt(goodsListModel.max_integral_num) > 0) {
                    scanShopGoodsViewHolder.deductibleTextView.setVisibility(View.VISIBLE);
                    scanShopGoodsViewHolder.deductibleTextView.setText("元宝最高抵扣:"+ Utils.formatMoney(scanShopGoodsViewHolder.deductibleTextView.getContext(), goodsListModel.max_integral_num));
                } else {
                    scanShopGoodsViewHolder.deductibleTextView.setVisibility(View.INVISIBLE);
                }
                break;
            case ITEM_TYPE_SHOP:
                ScanShopTitleViewHolder scanShopTitleViewHolder = (ScanShopTitleViewHolder)
                        viewHolder;
                final ListModel orderTitleModel = (ListModel) data.get(position);
                scanShopTitleViewHolder.mNanmeTextView.setText(orderTitleModel.shop_name);
                Utils.setViewTypeForTag(scanShopTitleViewHolder.mTitleLayout, ViewType
                        .VIEW_TYPE_SCAN_SHOP_TITLE);
                Utils.setExtraInfoForTag(scanShopTitleViewHolder.mTitleLayout, orderTitleModel
                        .shop_id);
                scanShopTitleViewHolder.mTitleLayout.setOnClickListener(onClickListener);
                break;
            case ITEM_TYPE_TITLE:
                ScanShopBlankViewHolder mScanShopBlankViewHolder = (ScanShopBlankViewHolder)
                        viewHolder;
                final PageModel mPageModel = (PageModel) data.get(position);
                mScanShopBlankViewHolder.mTextView.setText("搜索全站，为您找到" + mPageModel.record_count
                        + "个在售商品");
                break;
            case ITEM_TYPE_EMPTY:
                break;

        }
    }

    @Override
    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof ListModel) {
            return ITEM_TYPE_SHOP;
        } else if (object instanceof GoodsListModel) {
            return ITEM_TYPE_GOODS;
        } else if (object instanceof PageModel) {
            return ITEM_TYPE_TITLE;
        } else if (object instanceof DividerModel) {
            return ITEM_TYPE_EMPTY;
        } else {
            throw new RuntimeException("Unknown error");
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
