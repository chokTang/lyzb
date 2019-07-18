package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.szy.yishopcustomer.Activity.IngotRobRecordActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Config;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.IngotList.DetailModel;
import com.szy.yishopcustomer.ResponseModel.IngotList.IngotShareRecordModel;
import com.szy.yishopcustomer.Util.RequestAddHead;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.ShareDialog;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 元宝赠送记录 元宝分享记录 adapter
 * @time 2018 2018/7/12 9:56
 */

public class SendRecordAdapter extends RecyclerView.Adapter {

    public static final int TYPE_SEND = 1;
    public static final int TYPE_SHARE = 2;

    public List<Object> mList;
    public Context mContext;

    public String mHeadImg, mGuid, mInviteCode;
    public ShareDialog mShareDialog;


    public SendRecordAdapter(Context context) {
        this.mList = new ArrayList<>();
        this.mContext = context;
    }

    public RecyclerView.ViewHolder createSendHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingot_send_record, parent, false);
        SendHolder holder = new SendHolder(view);
        return holder;
    }

    public RecyclerView.ViewHolder createShareHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingot_share_record, parent, false);
        ShareHolder holder = new ShareHolder(view);
        return holder;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case TYPE_SEND:
                return createSendHolder(parent);
            case TYPE_SHARE:
                return createShareHolder(parent);
            default:
                break;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case TYPE_SEND:
                DetailModel model = (DetailModel) mList.get(position);
                bindSendItem((SendHolder) holder, model);
                break;
            case TYPE_SHARE:
                IngotShareRecordModel recordModel = (IngotShareRecordModel) mList.get(position);
                bindShareItem((ShareHolder) holder, recordModel);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {

        Object object = mList.get(position);
        if (object instanceof DetailModel) {
            return TYPE_SEND;
        } else if (object instanceof IngotShareRecordModel) {
            return TYPE_SHARE;
        }

        return position;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class SendHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_send_record_name)
        TextView mTextView_Nmae;
        @BindView(R.id.tv_send_record_time)
        TextView mTextView_Time;
        @BindView(R.id.tv_send_record_number)
        TextView mTextView_Number;

        public SendHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ShareHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rela_ingot_share)
        RelativeLayout mRelativeLayout_Share;
        @BindView(R.id.tv_ingot_share_time)
        TextView mTextView_Time;
        @BindView(R.id.tv_ingot_share_btn)
        TextView mTextView_ShareBtn;

        @BindView(R.id.tv_ingot_share_total)
        TextView mTextView_Total;
        @BindView(R.id.tv_ingot_share_number)
        TextView mTextView_Number;
        @BindView(R.id.tv_ingot_share_back)
        TextView mTextView_Back;
        @BindView(R.id.img_ingot_share_state)
        ImageView mImageView_State;

        public ShareHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void bindSendItem(SendHolder holder, DetailModel model) {

        holder.mTextView_Nmae.setText(model.note);
        holder.mTextView_Time.setText(Utils.times(model.add_time));
        holder.mTextView_Number.setText(model.changed_points + "元宝");
    }

    public void bindShareItem(ShareHolder holder, final IngotShareRecordModel model) {

        holder.mTextView_Time.setText(Utils.times(model.add_time));

        switch (model.status) {
            case 0:
                //进行中
                holder.mImageView_State.setBackgroundResource(R.mipmap.rob_run);
                holder.mTextView_Total.setTextColor(mContext.getResources().getColor(R.color.ing_number));
                break;
            case 1:
                //已抢光
                holder.mImageView_State.setBackgroundResource(R.mipmap.rob_gone);
                holder.mTextView_Total.setTextColor(mContext.getResources().getColor(R.color.colorOne));
                break;
            case 2:
                //已结束
                holder.mImageView_State.setBackgroundResource(R.mipmap.rob_over);
                holder.mTextView_Total.setTextColor(mContext.getResources().getColor(R.color.colorOne));
                break;
        }

        if (model.status == 0) {
            holder.mTextView_ShareBtn.setVisibility(View.VISIBLE);
            holder.mTextView_ShareBtn.setText("分享");
        } else {
            holder.mTextView_ShareBtn.setVisibility(View.GONE);
        }


        if (model.is_back == 1) {
            holder.mTextView_Back.setVisibility(View.VISIBLE);
            holder.mTextView_Back.setText("元宝退回:" + model.integral_back + "个");
        }

        holder.mTextView_Total.setText("赠送元宝总数:" + model.integral_total + "个,剩余" + model.integral_surplus + "个");
        holder.mTextView_Number.setText("赠送人数:" + model.num_total + ",剩余" + model.num_surplus );


        /****
         * 判断分享的内容是否健全
         *
         */
        if (!TextUtils.isEmpty(model.share_id) && !TextUtils.isEmpty(mGuid) && !TextUtils.isEmpty(mInviteCode) && !TextUtils.isEmpty(mHeadImg)) {
            holder.mTextView_ShareBtn.setVisibility(View.VISIBLE);
        } else {
            holder.mTextView_ShareBtn.setVisibility(View.GONE);
        }

        //继续分享元宝
        holder.mTextView_ShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mShareUrl = Config.BASE_URL + Api.API_INGOT_PAGEURL + "shareid=" + model.share_id + "&invite_guid=" + mGuid + "&invite_code=" + mInviteCode;
                encyShareUrl(mShareUrl, mHeadImg);
            }
        });

        //查看抢元宝记录
        holder.mRelativeLayout_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, IngotRobRecordActivity.class);
                intent.putExtra(IngotRobRecordActivity.KEY_SHAREID, model.share_id);
                mContext.startActivity(intent);
            }
        });

    }

    public void encyShareUrl(String shareUrl, final String shareImg) {

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_ENC_SHAREURL, RequestMethod.GET);
        RequestAddHead.addNoHttpHead(request, mContext, Api.API_ENC_SHAREURL, "GET");
        request.add("U", shareUrl);

        requestQueue.add(HttpWhat.HTTP_ENCY_SHARE.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                String ency_url = JSONObject.parseObject(response.get()).getString("u");

                //弹出分享弹框
                mShareDialog = new ShareDialog(mContext, R.style.ShareDialog);
                mShareDialog.mShareUrl = Config.BASE_URL + "/lbsapi/rrp?U=" + ency_url;
                mShareDialog.mGuid = mGuid;
                mShareDialog.mCode = mInviteCode;
                mShareDialog.mShareUserImg = shareImg;

                mShareDialog.show();
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Toast.makeText(mContext, "分享异常,请稍候尝试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }
}
