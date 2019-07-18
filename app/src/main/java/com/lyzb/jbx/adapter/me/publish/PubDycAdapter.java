package com.lyzb.jbx.adapter.me.publish;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.longshaolib.adapter.rvhelper.DividerGridItemDecoration;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.utilslib.date.DateUtil;
import com.like.utilslib.screen.DensityUtil;
import com.like.utilslib.screen.ScreenUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.common.SingleImageAdapter;
import com.lyzb.jbx.adapter.dynamic.DynamicGoodsAdapter;
import com.lyzb.jbx.model.dynamic.DynamicFileModel;
import com.lyzb.jbx.model.dynamic.DynamicModel;
import com.lyzb.jbx.util.AppCommonUtil;
import com.lyzb.jbx.util.ImageUtil;
import com.lyzb.jbx.widget.link.AutoLinkTextView;
import com.szy.yishopcustomer.Activity.TxCustomVideoPlayerController;
import com.szy.yishopcustomer.ResponseModel.GoodsModel;
import com.xiao.nicevideoplayer.NiceVideoPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的发表-动态
 */
public class PubDycAdapter extends BaseRecyleAdapter<DynamicModel> {

    private static final int TYPE_PICTORE = 0x263;//图片
    private static final int TYPE_VIDEO = 0x234;//视频
    private static final int TYPE_CARD = 0x235;//名片
    private static final int TYPE_ARTICE = 0x236;//热文
    private static final int TYPE_GOODS = 0x237;//商品

    public PubDycAdapter(Context context, List<DynamicModel> list) {
        super(context, -1, list);
    }

    @Override
    protected BaseViewHolder onChildCreateViewHolder(int viewType, ViewGroup parent) {
        switch (viewType) {
            case TYPE_PICTORE:
                return new PictureHodler(_context, getItemView(R.layout.item_pub_dyc_img, parent));
            case TYPE_VIDEO:
                return new VideoHolder(_context, getItemView(R.layout.item_pub_dyc_video, parent));
//            case TYPE_GOODS:
//                return new GoodsHolder(_context, getItemView(R.layout.recycle_dynamic_goods, parent));
            default:
                return new PictureHodler(_context, getItemView(R.layout.item_pub_dyc_img, parent));
        }
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

    @Override
    protected void convert(BaseViewHolder holder, DynamicModel item) {
        switch (holder.getItemViewType()) {
            //图片
            case TYPE_PICTORE:
                PictureHodler pictureHodler = (PictureHodler) holder;
                TextView yearText = pictureHodler.cdFindViewById(R.id.tv_item_dynamic_year);
                TextView dayText = pictureHodler.cdFindViewById(R.id.tv_item_dynamic_day);
                TextView monthText = pictureHodler.cdFindViewById(R.id.tv_item_dynamic_month);

                ////处理时间
                yearText.setText(DateUtil.getYear(item.getCreateDate()) + "");
                monthText.setText(DateUtil.getMonth(item.getCreateDate()) + "月");
                dayText.setText(DateUtil.getDay(item.getCreateDate()) + "");

                if (holder.getAdapterPosition() == 0) {
                    yearText.setVisibility(View.VISIBLE);
                } else {
                    if (DateUtil.getYear(item.getCreateDate()) == DateUtil.getYear(getPositionModel(holder.getAdapterPosition() - 1).getCreateDate())) {
                        yearText.setVisibility(View.GONE);
                    } else {
                        yearText.setVisibility(View.VISIBLE);
                    }
                }

                AutoLinkTextView tv_content_value = pictureHodler.cdFindViewById(R.id.tv_content_value);
                TextView tv_max = pictureHodler.cdFindViewById(R.id.tv_max);

                detailContent(tv_content_value, item.getContent(), tv_max, item.getId());
                pictureHodler.addItemClickListener(R.id.tv_content_value);
                pictureHodler.addItemClickListener(R.id.recycle_multiple_img);
                pictureHodler.addItemClickListener(R.id.layout_first);

                if (item.getFileList().size() == 0) {
                    pictureHodler.setVisible(R.id.layout_first, false);
                    pictureHodler.setVisible(R.id.recycle_multiple_img, false);
                } else if (item.getFileList().size() == 1) {
                    pictureHodler.setVisible(R.id.layout_first, true);
                    pictureHodler.setVisible(R.id.recycle_multiple_img, false);
                    pictureHodler.setImageUrl(R.id.img_first, item.getFileList().get(0).getFile());
                } else {
                    pictureHodler.setVisible(R.id.layout_first, false);
                    pictureHodler.setVisible(R.id.recycle_multiple_img, true);
                    pictureHodler.imgAdapter.update(item.getFileList());
                }

                pictureHodler.setText(R.id.tv_scan_number, String.format("%d人浏览", item.getViewCount()));
                pictureHodler.setText(R.id.tv_zan_number, item.getUpCount() == 0 ? "赞" : String.valueOf(item.getUpCount()));
                pictureHodler.setText(R.id.tv_comment_number, String.valueOf(item.getCommentCount()));
                pictureHodler.setText(R.id.tv_share_number, String.valueOf(item.getShareCount()));

                TextView tv_zan_number = pictureHodler.cdFindViewById(R.id.tv_zan_number);
                tv_zan_number.setSelected(item.getGiveLike() > 0);

                pictureHodler.addItemClickListener(R.id.tv_zan_number);
                pictureHodler.addItemClickListener(R.id.ll_pop);
                break;
            //视频
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


                AutoLinkTextView tv_content_value1 = holder.cdFindViewById(R.id.tv_content_value);
                TextView tv_max1 = holder.cdFindViewById(R.id.tv_max);
                detailContent(tv_content_value1, item.getContent(), tv_max1, item.getId());

                TextView yText = holder.cdFindViewById(R.id.tv_item_dynamic_year);
                TextView dext = holder.cdFindViewById(R.id.tv_item_dynamic_day);
                TextView mText = holder.cdFindViewById(R.id.tv_item_dynamic_month);

                ////处理时间
                yText.setText(DateUtil.getYear(item.getCreateDate()) + "");
                dext.setText(DateUtil.getDay(item.getCreateDate()) + "");
                mText.setText(DateUtil.getMonth(item.getCreateDate()) + "月");

                if (holder.getAdapterPosition() == 0) {
                    yText.setVisibility(View.VISIBLE);
                } else {
                    if (DateUtil.getYear(item.getCreateDate()) == DateUtil.getYear(getPositionModel(holder.getAdapterPosition() - 1).getCreateDate())) {
                        yText.setVisibility(View.GONE);
                    } else {
                        yText.setVisibility(View.VISIBLE);
                    }
                }

                holder.addItemClickListener(R.id.tv_content_value);

                holder.setImageUrl(R.id.img_first, item.getFileList().get(0).getFile());

                holder.setText(R.id.tv_scan_number, String.format("%d人浏览", item.getViewCount()));
                holder.setText(R.id.tv_zan_number, item.getUpCount() == 0 ? "赞" : String.valueOf(item.getUpCount()));
                holder.setText(R.id.tv_comment_number, String.valueOf(item.getCommentCount()));
                holder.setText(R.id.tv_share_number, String.valueOf(item.getShareCount()));

                TextView tv_zan_number1 = holder.cdFindViewById(R.id.tv_zan_number);
                tv_zan_number1.setSelected(item.getGiveLike() > 0);

                holder.addItemClickListener(R.id.tv_zan_number);
                holder.addItemClickListener(R.id.ll_pop);
                break;
            //名片
            case TYPE_CARD:
                break;
            //热文
            case TYPE_ARTICE:
                break;
            //商品
            case TYPE_GOODS:
                GoodsHolder goodsHolder = (GoodsHolder) holder;
                List<GoodsModel> list = new ArrayList<>();
                list.add(new GoodsModel());
                list.add(new GoodsModel());
                list.add(new GoodsModel());
                list.add(new GoodsModel());
                list.add(new GoodsModel());
                goodsHolder.goodsAdapter.update(list);
                goodsHolder.addItemClickListener(R.id.ll_pop);
                break;
            default:
                break;
        }
    }

    @Override
    protected int getChildItemViewType(int position) {
        List<DynamicFileModel> fileModels = getPositionModel(position).getFileList();
        if (fileModels.size() == 0) {
            return TYPE_PICTORE;
        } else {
            int nuber = fileModels.get(0).getItemType();
            switch (nuber) {
                case 1:
                    return TYPE_PICTORE;
                case 2:
                    return TYPE_VIDEO;
                case 3:
                    return TYPE_ARTICE;
                case 4:
                    return TYPE_CARD;
                case 5:
                    return TYPE_GOODS;
                default:
                    return TYPE_PICTORE;
            }
        }
    }

    class PictureHodler extends BaseViewHolder {

        SingleImageAdapter imgAdapter;
        RecyclerView recycle_multiple_img;

        public PictureHodler(final Context context, View itemView) {
            super(context, itemView);
            recycle_multiple_img = itemView.findViewById(R.id.recycle_multiple_img);
            int width = (ScreenUtil.getScreenWidth() - DensityUtil.dpTopx(55 + 18)) / 3;
            imgAdapter = new SingleImageAdapter(context, width, null);
            imgAdapter.setGridLayoutManager(recycle_multiple_img, 3);
            recycle_multiple_img.addItemDecoration(new DividerGridItemDecoration(R.drawable.listdivider_white_4));
            recycle_multiple_img.setAdapter(imgAdapter);

            recycle_multiple_img.addOnItemTouchListener(new OnRecycleItemClickListener() {
                @Override
                public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
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

    class GoodsHolder extends BaseViewHolder {

        RecyclerView recycle_goods_list;
        DynamicGoodsAdapter goodsAdapter;

        public GoodsHolder(Context context, View itemView) {
            super(context, itemView);
            recycle_goods_list = itemView.findViewById(R.id.recycle_goods_list);
            goodsAdapter = new DynamicGoodsAdapter(context, null);
            goodsAdapter.setLayoutManager(recycle_goods_list, LinearLayoutManager.HORIZONTAL);
            recycle_goods_list.setAdapter(goodsAdapter);
        }
    }

    /**
     * 处理内容
     *
     * @param textView
     * @param content
     * @param tv_max
     */
    private void detailContent(final AutoLinkTextView textView, String content, final TextView tv_max, String dynamicId) {

        tv_max.setVisibility(View.GONE);

        textView.setMaxLines(3);
        if (TextUtils.isEmpty(content)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
        }

        AppCommonUtil.autoLinkText(textView, content, dynamicId);

        textView.post(new Runnable() {
            @Override
            public void run() {
                if (textView.getLineCount() > 3) {
                    textView.setMaxLines(3);
                    textView.setEllipsize(TextUtils.TruncateAt.END);

                    tv_max.setText("全文");
                    tv_max.setVisibility(View.VISIBLE);
                } else {
                    tv_max.setVisibility(View.GONE);
                }
            }
        });
    }
}
