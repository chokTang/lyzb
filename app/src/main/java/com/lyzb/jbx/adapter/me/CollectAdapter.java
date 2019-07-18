package com.lyzb.jbx.adapter.me;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import com.lyzb.jbx.R;
import com.lyzb.jbx.activity.CricleDetailActivity;
import com.lyzb.jbx.adapter.common.SingleImageAdapter;
import com.lyzb.jbx.adapter.dynamic.DynamicGoodsAdapter;
import com.lyzb.jbx.model.dynamic.DynamicFileModel;
import com.lyzb.jbx.model.me.CollectModel;
import com.lyzb.jbx.util.ImageUtil;
import com.lyzb.jbx.widget.link.AutoLinkMode;
import com.lyzb.jbx.widget.link.AutoLinkOnClickListener;
import com.lyzb.jbx.widget.link.AutoLinkTextView;
import com.szy.yishopcustomer.Activity.TxCustomVideoPlayerController;
import com.szy.yishopcustomer.ResponseModel.GoodsModel;
import com.szy.yishopcustomer.Util.BrowserUrlManager;
import com.xiao.nicevideoplayer.NiceVideoPlayer;

import java.util.ArrayList;
import java.util.List;

/****
 * 我的-我的收藏
 *
 */
public class CollectAdapter extends BaseRecyleAdapter<CollectModel> {

    private static final int TYPE_PICTORE = 0x263;//图片
    private static final int TYPE_VIDEO = 0x234;//视频
    private static final int TYPE_CARD = 0x235;//名片
    private static final int TYPE_ARTICE = 0x236;//热文
    private static final int TYPE_GOODS = 0x237;//商品

    public CollectAdapter(Context context, List<CollectModel> list) {
        super(context, -1, list);
    }

    @Override
    protected BaseViewHolder onChildCreateViewHolder(int viewType, ViewGroup parent) {
        switch (viewType) {
            case TYPE_PICTORE:
                return new PictureHodler(_context, getItemView(R.layout.recycle_dynamic_picture, parent));
            case TYPE_VIDEO:
                return new VideoHolder(_context, getItemView(R.layout.recycle_dynamic_video, parent));
            case TYPE_CARD:
                return new BaseViewHolder(_context, getItemView(R.layout.recycle_dynamic_card, parent));
            case TYPE_ARTICE:
                return new BaseViewHolder(_context, getItemView(R.layout.recycle_dynamic_article, parent));
            case TYPE_GOODS:
                return new GoodsHolder(_context, getItemView(R.layout.recycle_dynamic_goods, parent));
            default:
                return new PictureHodler(_context, getItemView(R.layout.recycle_dynamic_picture, parent));
        }
    }

    @Override
    protected void convert(BaseViewHolder holder, CollectModel item) {
        switch (holder.getItemViewType()) {
            //图片
            case TYPE_PICTORE:
                PictureHodler pictureHodler = (PictureHodler) holder;

                headerInit(pictureHodler, item);

                AutoLinkTextView tv_content_value = pictureHodler.cdFindViewById(R.id.tv_content_value);
                TextView tv_max = pictureHodler.cdFindViewById(R.id.tv_max);
                detailContent(tv_content_value, item.getContent(), item.getTopicId(), tv_max);

                pictureHodler.addItemClickListener(R.id.recycle_multiple_img);
                pictureHodler.addItemClickListener(R.id.layout_first);

                if (item.getGsTopicFileList().size() == 0) {
                    pictureHodler.setVisible(R.id.layout_first, false);
                    pictureHodler.setVisible(R.id.recycle_multiple_img, false);
                } else if (item.getGsTopicFileList().size() == 1) {
                    pictureHodler.setVisible(R.id.layout_first, true);
                    pictureHodler.setVisible(R.id.recycle_multiple_img, false);
                    pictureHodler.setImageUrl(R.id.img_first, item.getGsTopicFileList().get(0).getFile());
                } else {
                    pictureHodler.setVisible(R.id.layout_first, false);
                    pictureHodler.setVisible(R.id.recycle_multiple_img, true);
                    pictureHodler.imgAdapter.update(item.getGsTopicFileList());
                }

                pictureHodler.setText(R.id.tv_scan_number, String.format("%d人浏览", item.getViewCount()));
                pictureHodler.setText(R.id.tv_zan_number, item.getUpCount() == 0 ? "赞" : String.valueOf(item.getUpCount()));
                pictureHodler.setText(R.id.tv_comment_number, String.valueOf(item.getCommentCount()));
//                pictureHodler.setText(R.id.tv_share_number, String.valueOf(item.getShareCount()));

                TextView tv_zan_number = pictureHodler.cdFindViewById(R.id.tv_zan_number);
                tv_zan_number.setSelected(item.getGiveLike() > 0);

                pictureHodler.addItemClickListener(R.id.layout_zan_number);
                pictureHodler.addItemClickListener(R.id.layout_share_number);
                break;
            //视频
            case TYPE_VIDEO:
                final VideoHolder videoHolder = (VideoHolder) holder;
                headerInit(holder, item);

                AutoLinkTextView tv_content_value1 = holder.cdFindViewById(R.id.tv_content_value);
                TextView tv_max1 = holder.cdFindViewById(R.id.tv_max);
                detailContent(tv_content_value1, item.getContent(), item.getTopicId(), tv_max1);

                videoHolder.video_play.setUp(item.getGsTopicFileList().get(0).getFile(), null);
                Glide.with(_context)
                        .setDefaultRequestOptions(new RequestOptions()
                                .frame(1)
                                .centerCrop()).load(item.getGsTopicFileList().get(0).getFile())
                        .into(videoHolder.controller.imageView());

                videoHolder.video_play.setController(videoHolder.controller);
                videoHolder.controller.setDoVideoPlayer(new TxCustomVideoPlayerController.DoVideoPlayer() {
                    @Override
                    public void isPlay(boolean isplay, boolean isShow, boolean iscontroller) {
                        if (isplay && !isShow) {
                            if (videoHolder.video_play.isIdle()) {
                                videoHolder.video_play.start();
                            } else {
                                if (videoHolder.video_play.isPlaying() || videoHolder.video_play.isBufferingPlaying()) {
                                    videoHolder.video_play.pause();
                                } else if (videoHolder.video_play.isPaused() || videoHolder.video_play.isBufferingPaused()) {
                                    videoHolder.video_play.restart();
                                }
                            }
                        }
                    }
                });

                holder.setText(R.id.tv_scan_number, String.format("%d人浏览", item.getViewCount()));
                holder.setText(R.id.tv_zan_number, item.getUpCount() == 0 ? "赞" : String.valueOf(item.getUpCount()));
                holder.setText(R.id.tv_comment_number, String.valueOf(item.getCommentCount()));
//                holder.setText(R.id.tv_share_number, String.valueOf(item.getShareCount()));

                TextView tv_zan_number1 = holder.cdFindViewById(R.id.tv_zan_number);
                tv_zan_number1.setSelected(item.getGiveLike() > 0);

                holder.addItemClickListener(R.id.layout_zan_number);
                holder.addItemClickListener(R.id.layout_share_number);
                holder.addItemClickListener(R.id.video_play);
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
                break;
            default:
                break;
        }
    }

    @Override
    protected int getChildItemViewType(int position) {
        List<DynamicFileModel> fileModels = getPositionModel(position).getGsTopicFileList();
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
            imgAdapter = new SingleImageAdapter(context, null);
            imgAdapter.setGridLayoutManager(recycle_multiple_img, 3);
            recycle_multiple_img.addItemDecoration(new DividerGridItemDecoration(R.drawable.listdivider_white_10));
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

    class VideoHolder extends BaseViewHolder {

        NiceVideoPlayer video_play;
        TxCustomVideoPlayerController controller;

        public VideoHolder(Context context, View itemView) {
            super(context, itemView);
            video_play = itemView.findViewById(R.id.video_play);
            controller = new TxCustomVideoPlayerController(context);
            video_play.setPlayerType(NiceVideoPlayer.TYPE_NATIVE);
            controller.setTitle("");
        }
    }

    /**
     * 处理公共的头部
     *
     * @param holder
     * @param model
     */
    private void headerInit(BaseViewHolder holder, CollectModel model) {
        holder.setVisible(R.id.tv_circle_dynamic, false);

        holder.setRadiusImageUrl(R.id.img_header, model.getHeadimg(), 4);
        holder.setText(R.id.tv_header_name, model.getGsName());
        holder.setVisible(R.id.img_vip, model.getUserVipAction().size() > 0);
        holder.setText(R.id.tv_header_company, model.getShopName());
        holder.setVisible(R.id.tv_follow, false);
        holder.setVisible(R.id.tv_no_follow, false);


        holder.addItemClickListener(R.id.tv_follow);
        holder.addItemClickListener(R.id.tv_no_follow);
        holder.addItemClickListener(R.id.tv_circle_dynamic);
        holder.addItemClickListener(R.id.img_header);
    }

    /**
     * 处理内容
     *
     * @param textView
     * @param content
     * @param tv_max
     */
    private void detailContent(final AutoLinkTextView textView, String content, final String dynamicId, final TextView tv_max) {
        tv_max.setVisibility(View.GONE);
        textView.setMaxLines(4);

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

        textView.post(new Runnable() {
            @Override
            public void run() {
                if (textView.getLineCount() > 3) {
                    textView.setMaxLines(3);
                    textView.setEllipsize(TextUtils.TruncateAt.END);
                    tv_max.setVisibility(View.VISIBLE);
                } else {
                    tv_max.setVisibility(View.GONE);
                }
            }
        });
    }
}
