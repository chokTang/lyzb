package com.szy.yishopcustomer.Adapter.im;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.like.utilslib.image.config.GlideApp;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.PhoneInfo.Video;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role
 * @time 2018 2018/9/12 11:35
 */

public class VideoListAdapter extends RecyclerView.Adapter {

    public List<Video> mList;
    private Context mContext;

    public VideoListAdapter(Context context) {
        this.mContext = context;
        mList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_list, parent, false);
        VideoHolder holder = new VideoHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Video video = mList.get(position);
        VideoHolder videoHolder = (VideoHolder) holder;

        if (video.type == 1) {

            videoHolder.mTextView_Size.setText("拍摄视频");
            videoHolder.mImageView_Thumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "拍摄视频", Toast.LENGTH_SHORT).show();
                }
            });

        } else if (video.type == 0) {
            //缩略图
            GlideApp.with(videoHolder.mImageView_Thumb.getContext())
                    .load(Uri.fromFile(new File(video.path)))
                    .centerCrop()
                    .into(videoHolder.mImageView_Thumb);


            long video_size = video.size / 1024 / 1024;

            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
            String time = sdf.format(new Date(video.duration));

            videoHolder.mTextView_Size.setText(video_size + "MB");
            videoHolder.mTextView_Time.setText(time);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class VideoHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_video_thumb)
        ImageView mImageView_Thumb;

        @BindView(R.id.tv_video_size)
        TextView mTextView_Size;
        @BindView(R.id.tv_video_time)
        TextView mTextView_Time;


        public VideoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
