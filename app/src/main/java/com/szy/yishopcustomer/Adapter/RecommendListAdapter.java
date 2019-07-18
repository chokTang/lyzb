package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.common.Util.ImageLoader;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.RecommendListViewHolder;
import com.szy.yishopcustomer.ViewModel.RecommendListModel;

import java.util.ArrayList;

/**
 * Created by liwei on 2017/7/14.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class RecommendListAdapter extends HeaderFooterAdapter<RecommendListModel.DataBean.ListBean> {

    public View.OnClickListener onClickListener;

    public RecommendListAdapter() {
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
        View view = inflater.inflate(R.layout.fragment_recommend_item, parent, false);
        return new RecommendListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if(getItemViewType(position) == TYPE_NORMAL){
            if(viewHolder instanceof RecommendListViewHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
                int tempposition = position;
                if(mHeaderView != null) {
                    tempposition = position-1;
                }

                RecommendListModel.DataBean.ListBean object = data.get(tempposition);

                bindRecommendViewHolder((RecommendListViewHolder) viewHolder,object,
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

    private void bindRecommendViewHolder(RecommendListViewHolder viewHolder, RecommendListModel.DataBean.ListBean object, int
            position) {
        viewHolder.textView_user_name.setText("会员："+object.user_name);
        viewHolder.textView_reg_time.setText("注册时间："+Utils.times(object.reg_time));

        if(!Utils.isNull(object.headimg)){
            ImageLoader.displayImage(Utils.urlOfImage(object.headimg),viewHolder.imageView_user_photo);
        }else {
            viewHolder.imageView_user_photo.setImageResource(R.mipmap.pl_user_avatar);
        }

    }
}