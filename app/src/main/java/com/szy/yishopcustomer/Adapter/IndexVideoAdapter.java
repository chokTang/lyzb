package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Index.VideoItemViewHolder;

import java.util.List;

/**
 * Created by liwei on 17/10/31.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class IndexVideoAdapter extends RecyclerView.Adapter<VideoItemViewHolder> {

    public static final String TAG = "IndexVideoAdapter";

    public int itemPosition;
    public List<String> data;
    public View.OnClickListener onClickListener;
    public int itemWidth;
    public int dividerWidth;

    private boolean isFullVideo;
    private Context mContext;

    public IndexVideoAdapter(List data,Context context) {
        this.data = data;
        this.mContext = context;
    }

    @Override
    public VideoItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_index_video_item, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = itemWidth;

        return new VideoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VideoItemViewHolder viewHolder, int position) {
        final String url = data.get(position);

//        ImageLoader.displayImage(url,viewHolder.imageView);

        Utils.setPositionForTag(viewHolder.imageView, itemPosition);
        Utils.setViewTypeForTag(viewHolder.imageView, ViewType.VIEW_TYPE_VIDEO);
        Utils.setExtraInfoForTag(viewHolder.imageView, position);
        viewHolder.imageView.setOnClickListener(onClickListener);



        if (!TextUtils.isEmpty(url)&&null!=viewHolder.imageView){
            Utils.asyncloadImage(viewHolder.imageView,url);
        }

    }






    @Override
    public int getItemCount() {
        return data.size();
    }

}
