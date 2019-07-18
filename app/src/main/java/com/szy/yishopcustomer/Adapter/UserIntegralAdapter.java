package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.UserIntegralViewHolder;
import com.szy.yishopcustomer.ViewModel.UserIntegralModel;

import java.util.ArrayList;

/**
 * Created by Smart on 2017/7/3.
 */

public class UserIntegralAdapter extends HeaderFooterAdapter<UserIntegralModel.DataBean.ListBean> {

    public View.OnClickListener onClickListener;

    public UserIntegralAdapter() {
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
        View view = inflater.inflate(R.layout.fragment_child_user_integral_item, parent, false);
        return new UserIntegralViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(getItemViewType(position) == TYPE_NORMAL){
            if(viewHolder instanceof UserIntegralViewHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
                int tempposition = position;
                if(mHeaderView != null) {
                    tempposition = position-1;
                }
                UserIntegralModel.DataBean.ListBean object = data.get(tempposition);
                bindShopViewHolder((UserIntegralViewHolder) viewHolder, object,
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

    private void bindShopViewHolder(UserIntegralViewHolder viewHolder, UserIntegralModel.DataBean.ListBean object, int
            position) {

        ImageLoader.displayImage(Utils.urlOfImage(object.shop_logo),viewHolder.imageView_shop);
        viewHolder.textView_shop_name.setText(object.shop_name);
        viewHolder.textView_store_points.setText("店铺积分：" + object.current_points + " 积分");

        Utils.setViewTypeForTag(viewHolder.itemView, ViewType.VIEW_TYPE_SHIPPING_TYPE);
        Utils.setPositionForTag(viewHolder.itemView, position);
        viewHolder.itemView.setOnClickListener(onClickListener);

    }

}