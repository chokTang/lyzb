package com.lyzb.jbx.adapter.home.first;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.utilslib.screen.ScreenUtil;
import com.lyzb.jbx.CdLongShaoAppaction;
import com.lyzb.jbx.R;
import com.lyzb.jbx.activity.CricleDetailActivity;
import com.lyzb.jbx.inter.IRecycleAnyClickListener;
import com.lyzb.jbx.model.video.HomeVideoModel;
import com.lyzb.jbx.widget.link.AutoLinkMode;
import com.lyzb.jbx.widget.link.AutoLinkOnClickListener;
import com.lyzb.jbx.widget.link.AutoLinkTextView;
import com.lyzb.jbx.widget.video.FullVideoPlayerController;
import com.szy.yishopcustomer.Util.BrowserUrlManager;
import com.xiao.nicevideoplayer.NiceVideoPlayer;

import java.util.List;

public class VideoListAdapter extends BaseRecyleAdapter<HomeVideoModel.ListBean> {

    private OnPlayingListener onPlayingListener;
    private IRecycleAnyClickListener iRecycleAnyClickListener;

    public VideoListAdapter(Context context, List<HomeVideoModel.ListBean> list) {
        super(context, -1, list);
    }

    @Override
    protected BaseViewHolder onChildCreateViewHolder(int viewType, ViewGroup parent) {
        return new VideoHolder(_context, getItemView(R.layout.item_video_screen_list, parent));
    }

    @Override
    protected void convert(final BaseViewHolder holder, final HomeVideoModel.ListBean item) {

//        detailContent((AutoLinkTextView) holder.cdFindViewById(R.id.tv_content_value), item.getContentX(), item.getId());
        holder.setRoundImageUrl(R.id.ic_image, item.getHeadimg(), 50);
        holder.setText(R.id.tv_name, item.getGsName());
        holder.setText(R.id.tv_comment_number, item.getCommentCount() + "");//评论


        holder.setText(R.id.tv_zan_number, item.getUpCount() + "");//点赞
        holder.cdFindViewById(R.id.tv_zan_number).setSelected(item.getGiveLike() > 0 ? true : false);

        holder.setVisible(R.id.tv_attention, item.getRelationNum() > 0 ? false : true);

        holder.cdFindViewById(R.id.tv_follow_number).setSelected(item.getCollect() > 0 ? true : false);
        holder.setText(R.id.tv_follow_number, item.getCollectCount() + "");//收藏数

        holder.addItemClickListener(R.id.btn_expand);//监听展开
        holder.setVisible(R.id.recy_goods, false);

        if (item.getGoodsList() != null && item.getGoodsList().size() > 0) {
            holder.setVisible(R.id.recy_goods, true);
            ((VideoHolder) holder).mVideoProductAdapter.update(item.getGoodsList());
        }
        holder.cdFindViewById(R.id.tv_content_value).postDelayed(new Runnable() {//延时处理，避免因为播视频造成界面卡死
            @Override
            public void run() {
                detailContent((AutoLinkTextView) holder.cdFindViewById(R.id.tv_content_value), item.getContent(), (TextView) holder.cdFindViewById(R.id.btn_expand), item.getId());
            }
        }, 100);
        holder.addItemClickListener(R.id.tv_comment_number);
        holder.addItemClickListener(R.id.tv_follow_number);
        holder.addItemClickListener(R.id.tv_zan_number);

        holder.addItemClickListener(R.id.tv_attention);

        holder.addItemClickListener(R.id.ic_image);
        holder.addItemClickListener(R.id.tv_name);
    }


    private void detailContent(final AutoLinkTextView textView, String content, TextView btnEx, final String dynamicId) {
        if (TextUtils.isEmpty(content)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setCustomRegex("#(.*?)#");
            textView.addAutoLinkMode(AutoLinkMode.MODE_CUSTOM, AutoLinkMode.MODE_URL);
            textView.setCustomModeColor(ContextCompat.getColor(_context, R.color.app_blue));
            textView.setUrlModeColor(ContextCompat.getColor(_context, R.color.app_blue));
            textView.setText(content);
        }

        if (textView.getLineCount() > 2) {
            textView.setMaxLines(2);
            btnEx.setVisibility(View.VISIBLE);
        } else {
            btnEx.setVisibility(View.GONE);
        }

        textView.setAutoLinkOnClickListener(new AutoLinkOnClickListener() {
            @Override
            public void onAutoLinkTextClick(AutoLinkMode autoLinkMode, String matchedText) {
                ((View) textView.getParent()).setTag("I have click");
                switch (autoLinkMode) {
                    //进入h5页面
                    case MODE_URL:
                        new BrowserUrlManager(matchedText).parseUrl(_context, matchedText);
                        break;
                    //进入动态详情页面
                    case MODE_CUSTOM:
                        CricleDetailActivity.start(_context, "#" + matchedText + "#", dynamicId);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onConvert(BaseViewHolder holder, HomeVideoModel.ListBean item, final int position) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.getScreenHeight() - ScreenUtil.getStatusHeight());
        holder.get_view().setLayoutParams(params);
        //拦截点击事件
        holder.cdFindViewById(R.id.layout_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //拦截点击事件
        holder.cdFindViewById(R.id.layout_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        final VideoHolder videoHolder = (VideoHolder) holder;
        videoHolder.controller.setOnProgressListener(new FullVideoPlayerController.OnProgressListener() {
            @Override
            public void onProgress(int progress, int secondaryProgress) {
                videoHolder.changeProgress.setProgress(progress);
                videoHolder.changeProgress.setSecondaryProgress(secondaryProgress);
            }
        });
        videoHolder.controller.setOnPlayingListener(new FullVideoPlayerController.OnPlayingListener() {//监听将要播放的操作
            @Override
            public void onPlaying() {
                if (getOnPlayingListener() != null) {
                    getOnPlayingListener().onPlaying(position);
                }
            }
        });
        videoHolder.controller.setOnCompleteListener(new FullVideoPlayerController.OnCompleteListener() {
            @Override
            public void onComplete() {
//                if (getItemCount() - 1 == position) {//最后一个重新播放
                videoHolder.videoPlayer.restart();
//                } else {
//                    if (getOnPlayNextListener() != null) {
//                        getOnPlayNextListener().onPlayNext(position + 1);
//                    }
//                }
            }
        });
        videoHolder.videoPlayer.setPlayerType(NiceVideoPlayer.TYPE_NATIVE);
        if (item.getFileList() != null && item.getFileList().size() > 0) {
            final String fileUrl = CdLongShaoAppaction.getProxy(_context).getProxyUrl(item.getFileList().get(0).getFile());
            videoHolder.videoPlayer.setUp(fileUrl, null);
            videoHolder.controller.setTitle("");
            Glide.with(_context)
                    .setDefaultRequestOptions(new RequestOptions()
                            .frame(1)
                            .centerInside()).load(item.getFileList().get(0).getFile())
                    .into(videoHolder.controller.imageView());
        }
        videoHolder.videoPlayer.setController(videoHolder.controller);
        videoHolder.controller.setShowView(videoHolder.cdFindViewById(R.id.showView));
        super.onConvert(holder, item, position);

    }


    public IRecycleAnyClickListener getiRecycleAnyClickListener() {
        return iRecycleAnyClickListener;
    }

    public void setiRecycleAnyClickListener(IRecycleAnyClickListener iRecycleAnyClickListener) {
        this.iRecycleAnyClickListener = iRecycleAnyClickListener;
    }

    public OnPlayingListener getOnPlayingListener() {
        return onPlayingListener;
    }

    public void setOnPlayingListener(OnPlayingListener onPlayingListener) {
        this.onPlayingListener = onPlayingListener;
    }

    public class VideoHolder extends BaseViewHolder {
        public NiceVideoPlayer videoPlayer;
        public FullVideoPlayerController controller;
        public SeekBar changeProgress;

        private RecyclerView recy_goods;//商品列表
        private VideoProductAdapter mVideoProductAdapter = null;


        public VideoHolder(Context context, View itemView) {
            super(context, itemView);
            videoPlayer = cdFindViewById(R.id.video_play);
            videoPlayer.continueFromLastPosition(false);//去缓存播放位置
            controller = new FullVideoPlayerController(context);
            changeProgress = cdFindViewById(R.id.change_progress);
            changeProgress.setOnTouchListener(new View.OnTouchListener() {//实现禁止拖动和点击
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            recy_goods = cdFindViewById(R.id.recy_goods);
            mVideoProductAdapter = new VideoProductAdapter(context, null);
            mVideoProductAdapter.setLayoutManager(recy_goods, LinearLayoutManager.HORIZONTAL);
            recy_goods.setAdapter(mVideoProductAdapter);
            recy_goods.addOnItemTouchListener(new OnRecycleItemClickListener() {//为商品添加点击事件
                @Override
                public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                    super.onItemClick(adapter, view, position);
                    if (getiRecycleAnyClickListener() != null) {
                        getiRecycleAnyClickListener().onItemClick(null, position, adapter.getPositionModel(position));//代理点商品列表
                    }
                }
            });
        }
    }

    public interface OnPlayingListener {
        void onPlaying(int positions);
    }
}
