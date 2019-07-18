package com.szy.yishopcustomer.Adapter.im;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.like.utilslib.image.LoadImageUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.activity.GoodToMeActivity;
import com.lyzb.jbx.api.UrlConfig;
import com.szy.yishopcustomer.Activity.GoodsActivity;
import com.szy.yishopcustomer.Activity.ProjectH5Activity;
import com.szy.yishopcustomer.Activity.YSCWebViewActivity;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.DateUtil;
import com.szy.yishopcustomer.Util.JSONUtil;
import com.szy.yishopcustomer.Util.LogUtils;
import com.szy.yishopcustomer.Util.UrlUtil;
import com.szy.yishopcustomer.Util.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 消息通知list adapter
 * @time 2018
 */

public class MessageListAdapter extends RecyclerView.Adapter {

    private static final int ITEM_DYNAMIC = 0x5632;
    private static final int ITEM_NAMORE = 0x5633;
    private static final int ITEM_CAMPAIGN = 0x5634;
    private static final int ITEM_GOODS = 0x5635;

    public List<EMMessage> mList;
    public Context mContext;
    private LayoutInflater inflater;

    public MessageListAdapter(Context context) {
        this.mList = new ArrayList<>();
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_CAMPAIGN:
                return new CampaignHolder(inflater.inflate(R.layout.recycle_message_campaign, parent, false));
            case ITEM_DYNAMIC:
                return new DynamicHolder(inflater.inflate(R.layout.recycle_message_dynamic, parent, false));
            case ITEM_NAMORE:
                return new ListHolder(inflater.inflate(R.layout.item_lymessage_list, parent, false));
            case ITEM_GOODS:
                return new GoodsHolder(inflater.inflate(R.layout.recycle_message_campaign, parent, false));
            default:
                return new ListHolder(inflater.inflate(R.layout.item_lymessage_list, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EMMessage model = mList.get(position);
        final String messageValue = model.getStringAttribute(EaseConstant.EXTRA_XB_CONTENT, "");
        switch (holder.getItemViewType()) {
            case ITEM_CAMPAIGN:
                final CampaignHolder campaignHolder = (CampaignHolder) holder;
                campaignHolder.tv_message_list_time.setText(DateUtil.format(model.getMsgTime()));
                campaignHolder.tv_title.setText(UrlUtil.parse(messageValue).params.get("title"));
                campaignHolder.tv_message_list_content.setText(UrlUtil.parse(messageValue).params.get("describe"));
                LoadImageUtil.loadImage(campaignHolder.img_brackground, UrlUtil.parse(messageValue).params.get("imageUrl"));

                campaignHolder.lin_message_list.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GoodToMeActivity.startCampaignInfo(mContext, UrlUtil.parse(messageValue).params.get("activityId"));
                    }
                });
                break;
            case ITEM_DYNAMIC:
                final DynamicHolder dynamicHolder = (DynamicHolder) holder;
                dynamicHolder.tv_message_list_time.setText(DateUtil.format(model.getMsgTime()));

                LoadImageUtil.loadImage(dynamicHolder.img_header, UrlUtil.parse(messageValue).params.get("headImg"));
                dynamicHolder.tv_name.setText(UrlUtil.parse(messageValue).params.get("userName"));
                dynamicHolder.tv_value.setText(UrlUtil.parse(messageValue).params.get("describe"));
                dynamicHolder.lin_message_list.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GoodToMeActivity.startIntoDynamicDetail(mContext, UrlUtil.parse(messageValue).params.get("topicId"));
                    }
                });
                dynamicHolder.img_header.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GoodToMeActivity.startIntoCard(mContext, UrlUtil.parse(messageValue).params.get("userId"));
                    }
                });
                break;
            case ITEM_GOODS:
                final GoodsHolder goodsHolder = (GoodsHolder) holder;
                goodsHolder.tv_message_list_time.setText(DateUtil.format(model.getMsgTime()));

                goodsHolder.tv_title.setText(UrlUtil.parse(messageValue).params.get("title"));
                goodsHolder.tv_message_list_content.setText(UrlUtil.parse(messageValue).params.get("describe"));
                LoadImageUtil.loadImage(goodsHolder.img_brackground, UrlUtil.parse(messageValue).params.get("imageUrl"));

                goodsHolder.lin_message_list.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, GoodsActivity.class);
                        intent.putExtra(Key.KEY_GOODS_ID.getValue(), UrlUtil.parse(messageValue).params.get("goodsId"));
                        mContext.startActivity(intent);
                    }
                });
                break;
            default:
                bindItem((ListHolder) holder, model);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rela_ly_message)
        RelativeLayout mLayout_Mes;

        @BindView(R.id.lin_message_list)
        LinearLayout mLayout;
        @BindView(R.id.tv_message_list_time)
        TextView mTime;
        @BindView(R.id.tv_message_list_title)
        TextView mTitle;
        @BindView(R.id.img_message_list_link)
        ImageView mLink;
        @BindView(R.id.tv_message_list_content)
        TextView mContent;

        @BindView(R.id.view_message_list)
        View mView;

        public ListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void bindItem(ListHolder holder, final EMMessage model) {
        holder.mTitle.setText(model.getStringAttribute(EaseConstant.EXTRA_XB_TITLE, ""));
        holder.mContent.setText(((EMTextMessageBody) model.getBody()).getMessage());
        holder.mTime.setText(DateUtil.format(model.getMsgTime()));
        final String messageType = model.getStringAttribute(EaseConstant.EXTRA_XB_MESSAGE_TYPE, "");
        final String messageValue = model.getStringAttribute(EaseConstant.EXTRA_XB_CONTENT, "");
        if (!TextUtils.isEmpty(messageType)) {
            if (messageType.equals("link")) {
                //messageValue 是URL链接 跳转到webView
                holder.mLink.setVisibility(View.VISIBLE);
                holder.mLayout_Mes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (messageValue.contains("/live?")) {//如果是直播跳转到直播原生界面
                            if (App.getInstance().isLogin()) {
                                if (!TextUtils.isEmpty(UrlUtil.parse(messageValue).params.get("channelId"))) {
                                    onclickListener.onclick(UrlUtil.parse(messageValue).params.get("channelId"));
                                }
                            } else {
                                LogUtils.Companion.e("当前未登录");
                            }
                        } else if (messageValue.contains("app/intelligent_card")) {//智能名片
                            GoodToMeActivity.startIntoCard(mContext, UrlUtil.parse(messageValue).params.get("userId"));
                        } else if (messageValue.contains("app/circle_apply")) {//圈子申请列表
                            GoodToMeActivity.startIntoCircleApply(mContext, UrlUtil.parse(messageValue).params.get("groupId"));
                        } else if (messageValue.contains("app/circle_detail")) {//圈子详情
                            GoodToMeActivity.startIntoCircleDetail(mContext, UrlUtil.parse(messageValue).params.get("groupId"));
                        } else if (messageValue.contains("app/dynamic_detail")) {//动态详情
                            GoodToMeActivity.startIntoDynamicDetail(mContext, UrlUtil.parse(messageValue).params.get("topicId"));
                        } else if (messageValue.contains("app/join_company_applay")) {//企业申请列表
                            GoodToMeActivity.startCompanyApplyList(mContext, UrlUtil.parse(messageValue).params.get("companyId"));
                        } else if (messageValue.contains("app/join_company_info")) {//企业信息
                            GoodToMeActivity.startCompanyInfo(mContext, UrlUtil.parse(messageValue).params.get("companyId"));
                        } else if (messageValue.contains("app/open_service")) {//开通服务
                            Intent intent = new Intent(mContext, ProjectH5Activity.class);
                            intent.putExtra(Key.KEY_URL.getValue(), UrlConfig.H5_OPEN_VIP);
                            mContext.startActivity(intent);
                        } else if (messageValue.contains("/goods/")) {
                            try {
                                String goodsFirst = messageValue.split("\\?")[0];
                                String webList = goodsFirst.substring(goodsFirst.lastIndexOf("/") + 1);
                                goodsFirst = webList.substring(0, webList.indexOf("."));
                                Intent intent = new Intent(mContext, GoodsActivity.class);
                                intent.putExtra(Key.KEY_GOODS_ID.getValue(), goodsFirst);
                                mContext.startActivity(intent);
                            } catch (Exception e) {
                                startToWeb(messageValue);
                            }
                        } else if (messageValue.contains("/goods-")) {
                            try {
                                String goodId = messageValue.substring(messageValue.indexOf("-") + 1, messageValue.indexOf("."));
                                Intent intent = new Intent(mContext, GoodsActivity.class);
                                intent.putExtra(Key.KEY_GOODS_ID.getValue(), goodId);
                                mContext.startActivity(intent);
                            } catch (Exception e) {
                                startToWeb(messageValue);
                            }
                        } else {
                            startToWeb(messageValue);
                        }
                    }
                });
            } else if (messageType.equals("goods")) {
                //跳转到零售店商品详情
                holder.mLink.setVisibility(View.VISIBLE);
                JSONObject goodsObject = JSONUtil.toJsonObject(messageValue);
                final String goodsId = JSONUtil.get(goodsObject, "goodsId", "");
                holder.mLayout_Mes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, GoodsActivity.class);
                        intent.putExtra(Key.KEY_GOODS_ID.getValue(), goodsId);
                        mContext.startActivity(intent);
                    }
                });
            } else {
                holder.mLayout_Mes.setOnClickListener(null);
                holder.mLink.setVisibility(View.GONE);
            }
        } else {
            holder.mLayout_Mes.setOnClickListener(null);
            holder.mLink.setVisibility(View.GONE);
        }
    }

    public interface ClickListener {
        void onclick(String hannelID);
    }

    public ClickListener onclickListener;

    public void setClickListener(ClickListener onclickListener) {
        this.onclickListener = onclickListener;
    }

    /**
     * 跳转到同城链接 还是零售
     *
     * @param messageValue
     */
    private void startToWeb(String messageValue) {
        if (Utils.verCityLifeUrl(messageValue)) {
            Intent intent = new Intent(mContext, ProjectH5Activity.class);
            intent.putExtra(Key.KEY_URL.getValue(), messageValue);
            mContext.startActivity(intent);
        } else {
            Intent intent = new Intent(mContext, YSCWebViewActivity.class);
            intent.putExtra(Key.KEY_URL.getValue(), messageValue);
            mContext.startActivity(intent);
        }
    }

    @Override
    public int getItemViewType(int position) {
        EMMessage message = mList.get(position);
        final String messageValue = message.getStringAttribute(EaseConstant.EXTRA_XB_CONTENT, "");
        if (messageValue.contains("app/dynamic_detail")) {
            return ITEM_DYNAMIC;
        } else if (messageValue.contains("app/activity_detail")) {
            return ITEM_CAMPAIGN;
        } else if (messageValue.contains("app/goods_detail")) {
            return ITEM_GOODS;
        } else {
            return ITEM_NAMORE;
        }
    }

    public class DynamicHolder extends RecyclerView.ViewHolder {

        LinearLayout lin_message_list;
        ImageView img_header;
        TextView tv_name;
        TextView tv_value;
        TextView tv_look_new;
        TextView tv_message_list_time;

        public DynamicHolder(View itemView) {
            super(itemView);
            tv_message_list_time = itemView.findViewById(R.id.tv_message_list_time);
            img_header = itemView.findViewById(R.id.img_header);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_value = itemView.findViewById(R.id.tv_value);
            tv_look_new = itemView.findViewById(R.id.tv_look_new);
            lin_message_list = itemView.findViewById(R.id.lin_message_list);
        }
    }

    public class CampaignHolder extends RecyclerView.ViewHolder {

        LinearLayout lin_message_list;
        TextView tv_message_list_time;
        TextView tv_title;
        TextView tv_message_list_content;
        ImageView img_brackground;

        public CampaignHolder(View itemView) {
            super(itemView);
            tv_message_list_time = itemView.findViewById(R.id.tv_message_list_time);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_message_list_content = itemView.findViewById(R.id.tv_message_list_content);
            img_brackground = itemView.findViewById(R.id.img_brackground);
            lin_message_list = itemView.findViewById(R.id.lin_message_list);
        }
    }

    public class GoodsHolder extends RecyclerView.ViewHolder {

        LinearLayout lin_message_list;
        TextView tv_message_list_time;
        TextView tv_title;
        TextView tv_message_list_content;
        ImageView img_brackground;

        public GoodsHolder(View itemView) {
            super(itemView);
            tv_message_list_time = itemView.findViewById(R.id.tv_message_list_time);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_message_list_content = itemView.findViewById(R.id.tv_message_list_content);
            img_brackground = itemView.findViewById(R.id.img_brackground);
            lin_message_list = itemView.findViewById(R.id.lin_message_list);
        }
    }
}
