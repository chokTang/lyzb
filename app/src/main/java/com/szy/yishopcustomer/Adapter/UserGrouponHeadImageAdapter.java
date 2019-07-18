package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;

import com.szy.common.Util.ImageLoader;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.GroupOn.UserGroupOnHeadImgViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwei on 2017/3/24.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class UserGrouponHeadImageAdapter extends RecyclerView
        .Adapter<UserGroupOnHeadImgViewHolder> {
    public static ImageView.OnClickListener onClickListener;
    public List<String> data;
    public int itemPosition;

    public UserGrouponHeadImageAdapter(ArrayList<String> data) {
        if (data != null) {
            this.data = data;
        } else {
            this.data = new ArrayList<>();
        }
    }

    @Override
    public UserGroupOnHeadImgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_user_groupon_detail_image, parent, false);
        return new UserGroupOnHeadImgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserGroupOnHeadImgViewHolder holder, int position) {
        if (!Utils.isNull(data.get(position))) {
            String url = data.get(position);
            //ImageLoader.displayImage(Utils.urlOfImage(data.get(position)), holder.headImage);
            ImageLoader.displayImage(Utils.urlOfImage(url), holder.headImage);
            if(position == 0){
                holder.headTipImage.setVisibility(View.VISIBLE);
            }else{
                holder.headTipImage.setVisibility(View.GONE);
            }
        } else {
            holder.headImage.setImageResource(R.mipmap.ic_groupon_num);
            //holder.headImage.setBackgroundResource(R.drawable.bg_dotted_circle);
            holder.headTipImage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
