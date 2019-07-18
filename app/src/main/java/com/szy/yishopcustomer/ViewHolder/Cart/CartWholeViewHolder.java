package com.szy.yishopcustomer.ViewHolder.Cart;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.szy.yishopcustomer.Adapter.CartWholeListAdapter;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.CartIndex.SkuListBean;
import com.szy.yishopcustomer.ResponseModel.CartIndex.WholeModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Smart on 2017/9/7.
 */

public class CartWholeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image_checkbox)
    public ImageView treeCheckBox;
    @BindView(R.id.item_cart_goods_name_textView)
    public TextView nameTextView;
    @BindView(R.id.item_cart_goods_imageView)
    public ImageView goodsImageView;
    @BindView(R.id.fragment_cart_goods_moq)
    public TextView goodsMoq;
    @BindView(R.id.fragment_cart_whole_list)
    public RecyclerView fragment_cart_whole_list;

    @BindView(R.id.relativeLayout_goods)
    public View relativeLayout_goods;

    //创建批发商品的适配器
    public CartWholeListAdapter cartWholeListAdapter;
    public List<SkuListBean> data;

    public CartWholeViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
        data = new ArrayList<>();
        cartWholeListAdapter = new CartWholeListAdapter(data);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        fragment_cart_whole_list.setLayoutManager(layoutManager);
        fragment_cart_whole_list.setAdapter(cartWholeListAdapter);
    }
}
