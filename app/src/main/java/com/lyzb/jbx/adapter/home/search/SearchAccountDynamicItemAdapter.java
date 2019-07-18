package com.lyzb.jbx.adapter.home.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.longshaolib.adapter.rvhelper.DividerGridItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.utilslib.date.DateStyle;
import com.like.utilslib.date.DateUtil;
import com.like.utilslib.screen.DensityUtil;
import com.like.utilslib.screen.ScreenUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.common.SingleImageAdapter;
import com.lyzb.jbx.model.common.DynamicItemModel;
import com.lyzb.jbx.model.dynamic.DynamicFileModel;
import com.lyzb.jbx.util.AppCommonUtil;
import com.lyzb.jbx.util.ImageUtil;
import com.lyzb.jbx.widget.link.AutoLinkTextView;
import com.szy.yishopcustomer.Activity.TxCustomVideoPlayerController;
import com.xiao.nicevideoplayer.NiceVideoPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索--用户---item适配器
 */
public class SearchAccountDynamicItemAdapter extends BaseRecyleAdapter<DynamicItemModel> {

    private static final int TYPE_TEXT = 0x52;//图片或者文字
    private static final int TYPE_VIDEO = 0x53;//视频
    private static final int TYPE_GOODS = 0x54;//商品
    private static final int TYPE_CARD = 0x55;//名片
    private static final int TYPE_ACTRICE = 0x56;//热文

    public SearchAccountDynamicItemAdapter(Context context, List<DynamicItemModel> list) {
        super(context, -1, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, final DynamicItemModel item) {
        switch (holder.getItemViewType()) {
            case TYPE_ACTRICE:
                break;
            case TYPE_CARD:
                break;
            case TYPE_GOODS:
                break;
            case TYPE_TEXT:
                ImageHolder imageHolder = (ImageHolder) holder;
                //处理内容
                AutoLinkTextView tv_item_text_content = imageHolder.cdFindViewById(R.id.tv_item_text_content);
                AppCommonUtil.autoLinkText(tv_item_text_content, item.getContent(), item.getId());

                imageHolder.gridAdapter.update(item.getFileList());
                imageHolder.setText(R.id.tv_item_time, DateUtil.StringToString(item.getCreateDate(), DateStyle.YYYY_MM_DD));
                imageHolder.setText(R.id.tv_item_scan_number, String.format("%d次浏览", item.getViewCount()));
                break;
            case TYPE_VIDEO:
                final VideoHolder videoHolder = (VideoHolder) holder;
                videoHolder.addItemClickListener(R.id.video_play);
                videoHolder.videoPlayer.setPlayerType(NiceVideoPlayer.TYPE_NATIVE);
                videoHolder.videoPlayer.setUp(item.getFileList().get(0).getFile(), null);
                videoHolder.controller.setTitle("");
                Glide.with(_context)
                        .setDefaultRequestOptions(new RequestOptions()
                                .frame(1)
                                .centerCrop()).load(item.getFileList().get(0).getFile())
                        .into(videoHolder.controller.imageView());

                videoHolder.videoPlayer.setController(videoHolder.controller);
                videoHolder.controller.setDoVideoPlayer(new TxCustomVideoPlayerController.DoVideoPlayer() {
                    @Override
                    public void isPlay(boolean isplay, boolean isShow, boolean iscontroller) {
                        if (isplay && !isShow) {
                            if (videoHolder.videoPlayer.isIdle()) {
                                videoHolder.videoPlayer.start();
                                item.setViewCount(item.getViewCount()+1);
                                videoHolder.setText(R.id.tv_item_scan_number, String.format("%d次浏览", item.getViewCount()));
                            } else {
                                if (videoHolder.videoPlayer.isPlaying() || videoHolder.videoPlayer.isBufferingPlaying()) {
                                    videoHolder.videoPlayer.pause();
                                } else if (videoHolder.videoPlayer.isPaused() || videoHolder.videoPlayer.isBufferingPaused()) {
                                    videoHolder.videoPlayer.restart();
                                }
                            }
                        }
                    }
                });

                holder.setText(R.id.tv_item_time, DateUtil.StringToString(item.getCreateDate(), DateStyle.YYYY_MM_DD));
                holder.setText(R.id.tv_item_scan_number, String.format("%d次浏览", item.getViewCount()));
                //处理内容
                AutoLinkTextView videoText = videoHolder.cdFindViewById(R.id.tv_item_text_content);
                AppCommonUtil.autoLinkText(videoText, item.getContent(), item.getId());

                if (item.getFileList().size() > 0) {
                    holder.setVisible(R.id.layout_video, true);
                    holder.setImageUrl(R.id.img_item_first_img, item.getFileList().get(0));
                } else {
                    holder.setVisible(R.id.layout_video, false);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected BaseViewHolder onChildCreateViewHolder(int viewType, ViewGroup parent) {
        switch (viewType) {
            case TYPE_ACTRICE:
                return new BaseViewHolder(_context, getItemView(R.layout.recycle_account_dynamic_item_article, parent));
            case TYPE_CARD:
                return new BaseViewHolder(_context, getItemView(R.layout.recycle_account_dynamic_item_card, parent));
            case TYPE_TEXT:
                return new ImageHolder(_context, getItemView(R.layout.recycle_account_dynamic_item_text_img, parent));
            case TYPE_VIDEO:
                return new VideoHolder(_context, getItemView(R.layout.recycle_account_dynamic_item_video, parent));
            case TYPE_GOODS:
                return new BaseViewHolder(_context, getItemView(R.layout.recycle_account_dynamic_item_goods, parent));
            default:
                return new BaseViewHolder(_context, getItemView(R.layout.recycle_account_dynamic_item_text_img, parent));
        }
    }

    @Override
    protected int getChildItemViewType(int position) {
        if (_list.get(position).getFileList().size() == 0)
            return TYPE_TEXT;
        int viewType = _list.get(position).getFileList().get(0).getItemType();
        switch (viewType) {
            case 3:
                return TYPE_ACTRICE;
            case 4:
                return TYPE_CARD;
            case 5:
                return TYPE_GOODS;
            case 2:
                return TYPE_VIDEO;
            case 1:
                return TYPE_TEXT;
        }
        return TYPE_TEXT;
    }

    public class VideoHolder extends BaseViewHolder {

        public NiceVideoPlayer videoPlayer;
        TxCustomVideoPlayerController controller;

        public VideoHolder(Context context, View itemView) {
            super(context, itemView);
            videoPlayer = itemView.findViewById(R.id.video_play);
            controller = new TxCustomVideoPlayerController(context);
        }
    }

    class ImageHolder extends BaseViewHolder {

        RecyclerView recycle_multiple_img;
        SingleImageAdapter gridAdapter;

        public ImageHolder(final Context context, View itemView) {
            super(context, itemView);
            int width = (ScreenUtil.getScreenWidth() - DensityUtil.dpTopx(98)) / 3;
            recycle_multiple_img = itemView.findViewById(R.id.recycle_multiple_img);
            gridAdapter = new SingleImageAdapter(context, width, null);
            recycle_multiple_img.setAdapter(gridAdapter);
            gridAdapter.setGridLayoutManager(recycle_multiple_img, 3);
            recycle_multiple_img.addItemDecoration(new DividerGridItemDecoration(R.drawable.listdivider_window_4));
            recycle_multiple_img.addOnItemTouchListener(new OnRecycleItemClickListener() {
                @Override
                public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                    View parentView = (View) recycle_multiple_img.getParent();
                    parentView.setTag("img have click");
                    List<String> urllist = new ArrayList<>();
                    for (int i = 0; i < adapter.getList().size(); i++) {
                        DynamicFileModel model = (DynamicFileModel) adapter.getList().get(i);
                        urllist.add(model.getFile());
                    }
                    ImageUtil.Companion.statPhotoViewActivity(context, position, urllist);
                }
            });
        }
    }
}
