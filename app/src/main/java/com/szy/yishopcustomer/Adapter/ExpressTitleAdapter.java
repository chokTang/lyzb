package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.common.ViewHolder.Region.SelectedRegionViewHolder;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.ExpressFragmentModel;

import java.util.ArrayList;

/**
 * Created by liuzhfieng on 2016/5/25 0025.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ExpressTitleAdapter extends RecyclerView.Adapter<SelectedRegionViewHolder> {

    public ArrayList<ExpressFragmentModel> data;
    public View.OnClickListener onClickListener;

    public void setData(ArrayList<ExpressFragmentModel> data) {
        this.data = data;
    }

    public ExpressTitleAdapter() {
        this.data = new ArrayList<ExpressFragmentModel>();
    }

    @Override
    public SelectedRegionViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .item_express_title, viewGroup, false);

        float windowWidth = Utils.getWindowWidth(viewGroup.getContext());
        TextView textView = (TextView) LayoutInflater.from(view.getContext()).inflate(
                R.layout.item_express_title, null);
        textView.setGravity(Gravity.CENTER);

        if(data.size() == 1){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utils.dpToPx(viewGroup.getContext(), 40));
            textView.setLayoutParams(layoutParams);
        }else if(data.size() == 2){
            float realWidth = windowWidth / 2;
            textView.setWidth((int)realWidth);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, Utils.dpToPx(viewGroup.getContext(), 40));
            textView.setLayoutParams(layoutParams);
        }else if(data.size() == 3){
            float realWidth = windowWidth / 3;
            textView.setWidth((int)realWidth);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, Utils.dpToPx(viewGroup.getContext(), 40));
            textView.setLayoutParams(layoutParams);
        }else{
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, Utils.dpToPx(viewGroup.getContext(), 40));
            textView.setLayoutParams(layoutParams);
        }

        return new SelectedRegionViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(SelectedRegionViewHolder holder, int position) {
        holder.nameTextView.setText(data.get(position).getName());
        if (data.get(position).isSelect()) {
            holder.nameTextView.setSelected(true);
        } else {
            holder.nameTextView.setSelected(false);
        }
        Utils.setViewTypeForTag(holder.itemView, ViewType.VIEW_TYPE_TAB);
        Utils.setPositionForTag(holder.itemView, position);
        holder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
