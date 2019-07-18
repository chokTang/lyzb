package com.szy.yishopcustomer.Adapter.im;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.szy.common.Util.ImageLoader;
import com.szy.common.View.BaseRecyclerView;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.DateStyle;
import com.szy.yishopcustomer.Util.DateUtil;
import com.szy.yishopcustomer.Util.SharedPreferencesUtils;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.AutoSplitTextView;
import com.szy.yishopcustomer.ViewModel.im.ImHeaderGoodsModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/7/9.
 */

public class ImAdapter extends BaseRecyclerView.Adapter {

    private final static int TYPE_ME = 0x235;
    private final static int TYPE_OTHER = 0x236;
    private final static int TYPE_GOODS = 0x237;
    private final static long interval_Time = 3 * 60 * 1000;//间隔时间：毫秒 3分钟
    private final String mCurrentDayStart;
    private final long mCurrentDayStartTime;

    private List<Object> mList = new ArrayList<>();
    private String mAccountId;
    private String mUserNickName;
    private String mShopNickName;
    private LayoutInflater mInflater;

    public ImAdapter(Context context,String shopNickName, List<Object> list) {
        if (list != null && list.size() > 0) {
            mList.addAll(list);
        }
        mInflater = LayoutInflater.from(context);
        mCurrentDayStart = String.format("%s %s", DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD), "00:00:00");
        mCurrentDayStartTime = DateUtil.StringToDate(mCurrentDayStart, DateStyle.YYYY_MM_DD_HH_MM_SS).getTime();
        mAccountId = SharedPreferencesUtils.getParam(context, Key.IM_USERNAME.name(), "").toString();
        mUserNickName = SharedPreferencesUtils.getParam(context, Key.IM_USERNICK.name(), "").toString();
        mShopNickName=shopNickName;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_ME:
                view = mInflater.inflate(R.layout.recycle_im_me_text_layout, parent, false);
                return new ImHodler(view);
            case TYPE_OTHER:
                view = mInflater.inflate(R.layout.recycle_im_other_text_layout, parent, false);
                return new ImHodler(view);
            case TYPE_GOODS:
                view = mInflater.inflate(R.layout.recycle_goods_header_layout, parent, false);
                return new GoodsHodler(view);
            default:
                view = mInflater.inflate(R.layout.recycle_im_other_text_layout, parent, false);
                return new ImHodler(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_ME:
                EMMessage message = (EMMessage) mList.get(position);
                ImHodler imHodler = (ImHodler) holder;
                EMTextMessageBody body = (EMTextMessageBody) message.getBody();
                imHodler.content_tv.setText(body.getMessage());
                imHodler.userName_tv.setText(mUserNickName);
                showMeesageTime(position, message, imHodler.time_tv);
                break;
            case TYPE_OTHER:
                EMMessage otherMessage = (EMMessage) mList.get(position);
                ImHodler otherHodler = (ImHodler) holder;
                EMTextMessageBody otherBody = (EMTextMessageBody) otherMessage.getBody();
                otherHodler.content_tv.setText(otherBody.getMessage());
                otherHodler.userName_tv.setText(mShopNickName);
                showMeesageTime(position, otherMessage, otherHodler.time_tv);
                break;
            case TYPE_GOODS:
                GoodsHodler goodsHodler = (GoodsHodler) holder;
                ImHeaderGoodsModel goodsModel = (ImHeaderGoodsModel) mList.get(position);
                ImageLoader.displayImage(Utils.urlOfImage(goodsModel.getImageUrl(), false), goodsHodler.goods_img);
                goodsHodler.goods_name_tv.setText(goodsModel.getGoodsName());
                goodsHodler.goods_price_tv.setText(String.format("￥%s", goodsModel.getGoodPrice()));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object itemObject = mList.get(position);
        if (itemObject instanceof ImHeaderGoodsModel) {
            return TYPE_GOODS;
        } else {
            EMMessage message = (EMMessage) itemObject;
            if (mAccountId.equals(message.getFrom())) {
                return TYPE_ME;
            } else {
                return TYPE_OTHER;
            }
        }
    }

    public class ImHodler extends BaseRecyclerView.ViewHolder {

        public TextView time_tv;
        public TextView userName_tv;
        public AutoSplitTextView content_tv;

        public ImHodler(View itemView) {
            super(itemView);
            content_tv = (AutoSplitTextView) itemView.findViewById(R.id.content_tv);
            time_tv = (TextView) itemView.findViewById(R.id.time_tv);
            userName_tv = (TextView) itemView.findViewById(R.id.userName_tv);
        }
    }

    public class GoodsHodler extends BaseRecyclerView.ViewHolder {

        public ImageView goods_img;
        public TextView goods_name_tv;
        public TextView goods_price_tv;

        public GoodsHodler(View itemView) {
            super(itemView);
            goods_img = (ImageView) itemView.findViewById(R.id.goods_img);
            goods_name_tv = (TextView) itemView.findViewById(R.id.goods_name_tv);
            goods_price_tv = (TextView) itemView.findViewById(R.id.goods_price_tv);
        }
    }

    public List<Object> getmList() {
        return mList;
    }

    public void add(EMMessage message) {
        mList.add(mList.size(), message);
        notifyItemInserted(mList.size());
    }

    /**
     * 是否显示时间
     *
     * @param postion
     * @param message
     * @param timeTv
     */
    private final void showMeesageTime(int postion, EMMessage message, TextView timeTv) {
        if (postion == 0) {
            timeTv.setVisibility(View.GONE);
        } else {
            Object oldItem = mList.get(postion - 1);
            if (oldItem instanceof ImHeaderGoodsModel) {
                timeTv.setVisibility(View.VISIBLE);
                timeTv.setText(getMessageTime(message.getMsgTime()));
            } else {
                EMMessage oldMessage = (EMMessage) oldItem;
                long oldtime = oldMessage.getMsgTime();
                long nowTim = message.getMsgTime();
                if (nowTim - oldtime > interval_Time) {
                    timeTv.setVisibility(View.VISIBLE);
                    timeTv.setText(getMessageTime(nowTim));
                } else {
                    timeTv.setVisibility(View.GONE);
                }
            }
        }
    }

    private final String getMessageTime(long timeValue) {
        String timeValueZh = "";
        if (timeValue >= mCurrentDayStartTime) {//表示在今天
            timeValueZh = DateUtil.DateToString(new Date(timeValue), DateStyle.HH_MM);
        } else {//表示在昨天及以前
            if (DateUtil.getIntervalDays(new Date(timeValue), new Date(mCurrentDayStartTime)) == 0) {//昨天
                timeValueZh = String.format("昨天 %s", DateUtil.DateToString(new Date(timeValue), DateStyle.HH_MM));
            } else {
                timeValueZh = DateUtil.DateToString(new Date(timeValue), DateStyle.YYYY_MM_DD_HH_MM);
            }
        }
        return timeValueZh;
    }
}
