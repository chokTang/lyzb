package com.lyzb.jbx.adapter.circle;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import com.lyzb.jbx.model.dynamic.DynamicModel;
import com.lyzb.jbx.util.ImageUtil;
import com.lyzb.jbx.webscoket.BaseClient;
import com.lyzb.jbx.widget.link.AutoLinkMode;
import com.lyzb.jbx.widget.link.AutoLinkOnClickListener;
import com.lyzb.jbx.widget.link.AutoLinkTextView;
import com.szy.yishopcustomer.Activity.TxCustomVideoPlayerController;
import com.szy.yishopcustomer.ResponseModel.GoodsModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.BrowserUrlManager;
import com.xiao.nicevideoplayer.NiceVideoPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态列表页面
 */
public class DynamicCircleListAdapter extends BaseRecyleAdapter<DynamicModel> {

    private static final int TYPE_PICTORE = 0x263;//图片
    private static final int TYPE_VIDEO = 0x234;//视频
    private static final int TYPE_CARD = 0x235;//名片
    private static final int TYPE_ARTICE = 0x236;//热文
    private static final int TYPE_GOODS = 0x237;//商品

    public DynamicCircleListAdapter(Context context, List<DynamicModel> list) {
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
    protected void convert(BaseViewHolder holder, final DynamicModel item) {
        switch (holder.getItemViewType()) {
            //图片
            case TYPE_PICTORE:
                final PictureHodler pictureHodler = (PictureHodler) holder;

                headerInit(pictureHodler, item);

                AutoLinkTextView tv_content_value = pictureHodler.cdFindViewById(R.id.tv_content_value);
                TextView tv_max = pictureHodler.cdFindViewById(R.id.tv_max);
                detailContent(tv_content_value, item.getContent(), tv_max, item.getId());

                if (item.getFileList().size() == 0) {
                    pictureHodler.setVisible(R.id.layout_first, false);
                    pictureHodler.setVisible(R.id.img_first, false);
                    pictureHodler.setVisible(R.id.recycle_multiple_img, false);
                } else if (item.getFileList().size() == 1) {
                    pictureHodler.setVisible(R.id.layout_first, true);
                    pictureHodler.setVisible(R.id.img_first, true);
                    pictureHodler.setVisible(R.id.recycle_multiple_img, false);
                    pictureHodler.setImageUrl(R.id.img_first, item.getFileList().get(0).getFile());
                } else {
                    pictureHodler.setVisible(R.id.layout_first, false);
                    pictureHodler.setVisible(R.id.img_first, false);
                    pictureHodler.setVisible(R.id.recycle_multiple_img, true);
                    pictureHodler.setTag(R.id.recycle_multiple_img, item);
                    pictureHodler.imgAdapter.update(item.getFileList());
                }

                pictureHodler.setText(R.id.tv_scan_number, String.format("%d次浏览", item.getViewCount()));
                pictureHodler.setInVisible(R.id.tv_scan_number, item.getViewCount() > 0);
                pictureHodler.setText(R.id.tv_zan_number, item.getUpCount() == 0 ? "赞" : String.valueOf(item.getUpCount()));
                pictureHodler.setText(R.id.tv_comment_number, item.getCommentCount() == 0 ? "评论" : String.valueOf(item.getCommentCount()));
//                pictureHodler.setText(R.id.tv_share_number, String.valueOf(item.getShareCount()));

                TextView tv_zan_number = pictureHodler.cdFindViewById(R.id.tv_zan_number);
                tv_zan_number.setSelected(item.getGiveLike() > 0);

                pictureHodler.addItemClickListener(R.id.layout_zan_number);
                pictureHodler.addItemClickListener(R.id.layout_share_number);
                pictureHodler.addItemClickListener(R.id.recycle_multiple_img);
                pictureHodler.addItemClickListener(R.id.layout_comment);

                pictureHodler.layout_first.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((View) v.getParent()).setTag("I have click");

                        List<String> urllist = new ArrayList<>();
                        urllist.add(item.getFileList().get(0).getFile());
                        ImageUtil.Companion.statPhotoViewActivity(_context, 0, urllist);
                        //记录发送次数
                        BaseClient.getInstance().setMessage(2, item.getId(), item.getCreateMan());
                        item.setViewCount(item.getViewCount() + 1);
                        pictureHodler.setText(R.id.tv_scan_number, String.format("%d次浏览", item.getViewCount()));
                    }
                });
                break;
            //视频
            case TYPE_VIDEO:
                final VideoHolder videoHolder = (VideoHolder) holder;
                headerInit(holder, item);

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
                                BaseClient.getInstance().setMessage(2, item.getId(), item.getCreateMan());
                                item.setViewCount(item.getViewCount() + 1);
                                videoHolder.setText(R.id.tv_scan_number, String.format("%d次浏览", item.getViewCount()));
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

                holder.setImageUrl(R.id.img_first, item.getFileList().get(0).getFile());
                holder.setText(R.id.tv_scan_number, String.format("%d次浏览", item.getViewCount()));
                holder.setInVisible(R.id.tv_scan_number, item.getViewCount() > 0);
                holder.setText(R.id.tv_zan_number, item.getUpCount() == 0 ? "赞" : String.valueOf(item.getUpCount()));
                holder.setText(R.id.tv_comment_number, item.getCommentCount() == 0 ? "评论" : String.valueOf(item.getCommentCount()));
//                holder.setText(R.id.tv_share_number, String.valueOf(item.getShareCount()));

                TextView tv_zan_number1 = holder.cdFindViewById(R.id.tv_zan_number);
                tv_zan_number1.setSelected(item.getGiveLike() > 0);

                holder.addItemClickListener(R.id.layout_zan_number);
                holder.addItemClickListener(R.id.layout_share_number);
                holder.addItemClickListener(R.id.layout_comment);
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
        List<DynamicFileModel> fileModels = _list.get(position).getFileList();
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
        ImageView img_first;
        FrameLayout layout_first;
        TextView tv_scan_number;

        public PictureHodler(final Context context, View itemView) {
            super(context, itemView);
            recycle_multiple_img = itemView.findViewById(R.id.recycle_multiple_img);
            img_first = itemView.findViewById(R.id.img_first);
            layout_first = itemView.findViewById(R.id.layout_first);
            tv_scan_number = itemView.findViewById(R.id.tv_scan_number);

            imgAdapter = new SingleImageAdapter(context, null);
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
                    ImageUtil.Companion.statPhotoViewActivity(_context, position, urllist);
                    final DynamicModel item = (DynamicModel) recycle_multiple_img.getTag();
                    //记录发送次数
                    BaseClient.getInstance().setMessage(2, item.getId(), item.getCreateMan());
                    item.setViewCount(item.getViewCount() + 1);
                    tv_scan_number.setText(String.format("%d人浏览", item.getViewCount()));
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


    public class VideoHolder extends BaseViewHolder {

        public NiceVideoPlayer videoPlayer;
        TxCustomVideoPlayerController controller;

        public VideoHolder(Context context, View itemView) {
            super(context, itemView);
            videoPlayer = itemView.findViewById(R.id.video_play);
            controller = new TxCustomVideoPlayerController(context);
        }
    }

    /**
     * 处理公共的头部
     *
     * @param holder
     * @param model
     */
    private void headerInit(BaseViewHolder holder, DynamicModel model) {
        holder.setVisible(R.id.tv_circle_dynamic, !TextUtils.isEmpty(model.getGroupname()));
        holder.setText(R.id.tv_circle_dynamic, model.getGroupname());
        holder.setVisible(R.id.img_voice_play, model.getIntroductionAudioFile().size() > 0);

        holder.setRadiusImageUrl(R.id.img_header, model.getHeadimg(), 4);
        holder.setText(R.id.tv_header_name, model.getGsName());
        holder.setVisible(R.id.img_vip, model.getUserActionVos().size() > 0);
        holder.setText(R.id.tv_header_company, model.getShopName());

        holder.setVisible(R.id.tv_follow, false);
        holder.setVisible(R.id.tv_no_follow, false);//处理关注和在线沟通
        if (model.getCreateMan().equals(App.getInstance().userId)) {
            holder.setVisible(R.id.tv_no_follow, false);
            holder.setVisible(R.id.tv_follow, false);
        } else {
            holder.setVisible(R.id.tv_no_follow, true);
            TextView tv_no_follow = holder.cdFindViewById(R.id.tv_no_follow);
            if (model.getRelationNum() > 0) {//已关注--在线沟通
                holder.setText(R.id.tv_no_follow, "在线沟通");
                tv_no_follow.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            } else {
                holder.setText(R.id.tv_no_follow, "关注");
                tv_no_follow.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(_context, R.drawable.union_add_gray),
                        null, null, null);
            }
        }

        holder.addItemClickListener(R.id.tv_no_follow);
        holder.addItemClickListener(R.id.tv_circle_dynamic);
        holder.addItemClickListener(R.id.img_header);
        holder.addItemClickListener(R.id.img_voice_play);
    }

    /**
     * 处理内容
     *
     * @param textView
     * @param content
     * @param tv_max
     */
    private void detailContent(final AutoLinkTextView textView, String content, final TextView tv_max, final String dynamicId) {
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
