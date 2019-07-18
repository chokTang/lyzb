package com.szy.yishopcustomer.Activity.im;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.lyzb.jbx.R;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.VideoActivity;
import com.szy.yishopcustomer.Adapter.im.MessageListAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Util.ImUtil;
import com.szy.yishopcustomer.Util.LogUtils;
import com.szy.yishopcustomer.Util.RequestAddHead;
import com.szy.yishopcustomer.ViewModel.HomeShopAndProductBean;
import com.szy.yishopcustomer.ViewModel.im.ImLiveMsgModel;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.szy.yishopcustomer.Activity.VideoActivity.KEY_BUNDLE;
import static com.szy.yishopcustomer.Activity.VideoActivity.KEY_VIDEO;
import static com.szy.yishopcustomer.Constant.Api.API_HEAD_IMG_URL;

/**
 * @author wyx
 * @role 消息详情 list
 * @time 2018 2018/8/22 14:56
 */

public class LyMessageListActivity extends Activity {

    @BindView(R.id.toolbar_ly_message_list)
    Toolbar mToolbar;
    @BindView(R.id.recy_message_list)
    CommonRecyclerView mRecyclerView;
    ImLiveMsgModel msgModel;
    @BindView(R.id.empty_view)
    View empty_view;
    @BindView(R.id.empty_img)
    ImageView empty_img;
    private LinearLayoutManager mLayoutManager;
    private MessageListAdapter mAdapter;

    private List<EMMessage> mMessageList = new ArrayList<>();
    private List<EMMessage> mMessageList_More = new ArrayList<>();

    private EMConversation conversation = EMClient.getInstance().chatManager().getConversation(ImUtil.XIAOBAO);

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            int lastItemPosition = mLayoutManager.findLastVisibleItemPosition();
            int totalItemCount = recyclerView.getAdapter().getItemCount();
            int visibleItemCount = mLayoutManager.getChildCount();

            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItemPosition == totalItemCount - 1 && visibleItemCount > 0) {
                //加载更多
                getMoreData();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lymessage_list);
        ButterKnife.bind(this);

        empty_img.setImageResource(R.drawable.union_no_message);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(mOnScrollListener);

        mAdapter = new MessageListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setClickListener(new MessageListAdapter.ClickListener() {
            @Override
            public void onclick(String hannelID) {
                getShowMsg(hannelID);
            }
        });

        getListData();
    }

    private void getListData() {
        //获取此会话的所有消息
        if (conversation == null) {
            empty_view.setVisibility(View.VISIBLE);
            return;
        }
        EMMessage message = conversation.getLastMessage();
        if (message == null) {
            empty_view.setVisibility(View.VISIBLE);
            return;
        }
        mMessageList = conversation.loadMoreMsgFromDB(message.getMsgId(), 10);
        if (!mMessageList.contains(message)) {
            mMessageList.add(0, message);

        }

        /** 根据时间 排序 根据 >0 or <0 */
        Collections.sort(mMessageList, new Comparator<EMMessage>() {
            @Override
            public int compare(EMMessage o1, EMMessage o2) {
                return o2.getMsgTime() - o1.getMsgTime() > 0 ? 1 : -1;
            }
        });
        mAdapter.mList = mMessageList;
        mAdapter.notifyDataSetChanged();
        if (mAdapter.mList == null || mAdapter.mList.size() == 0) {
            empty_view.setVisibility(View.VISIBLE);
        } else {
            empty_view.setVisibility(View.GONE);
        }
    }

    private void getMoreData() {

        /***
         * startMsgId 最后一条消息 id
         * pagesize 加载数目
         *
         */
        mMessageList_More = conversation.loadMoreMsgFromDB(mMessageList.get(mMessageList.size() - 1).getMsgId(), 10);
        if (mMessageList_More.size() > 0) {
            mMessageList.addAll(mMessageList_More);

            Collections.sort(mMessageList, new Comparator<EMMessage>() {
                @Override
                public int compare(EMMessage o1, EMMessage o2) {
                    return o2.getMsgTime() - o1.getMsgTime() > 0 ? 1 : -1;
                }
            });

            mAdapter.mList = mMessageList;
            mAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "暂无更多消息", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取直播相关信息
     */
    private void getShowMsg(String channelId) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();

        final Request<String> request = NoHttp.createStringRequest(Api.API_IM_LIVE_MSG, RequestMethod.POST);
        RequestAddHead.addNoHttpHead(request, this, Api.API_IM_LIVE_MSG, "POST");

        JSONObject object = new JSONObject();
        try {
            object.put("app", "app");
            object.put("channelId", channelId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.setDefineRequestBodyForJson(object.toJSONString());
        requestQueue.add(HttpWhat.HTTP_IM_LIVE_MSG.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtils.Companion.e("IM中的直播数据为" + response);
                if (response.responseCode() == Api.SUCCESS_INT) {
                    msgModel = JSONObject.parseObject(response.get(), ImLiveMsgModel.class);
                    if (!TextUtils.isEmpty(ImBeantoLiveBean(msgModel).getUrl_push())) {
                        Intent intent = new Intent(LyMessageListActivity.this, VideoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(KEY_VIDEO, ImBeantoLiveBean(msgModel));
                        intent.putExtra(KEY_BUNDLE, bundle);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    /**
     * 将小宝消息的model数据转换成视频直播所要的model数据
     *
     * @return
     */
    private HomeShopAndProductBean.LiveBean ImBeantoLiveBean(ImLiveMsgModel msgModel) {
        HomeShopAndProductBean.LiveBean bean = new HomeShopAndProductBean.LiveBean();

        bean.setAnchor_id(msgModel.getAnchorId());
        bean.setAvatar_url(msgModel.getAvatarUrl());
        bean.setLive_img(msgModel.getLiveImg());
        bean.setLive_title(msgModel.getLiveTitle());
        bean.setLive_type(msgModel.getLiveType());
        bean.setMember_num(msgModel.getMemberNum());
        bean.setNick_name(msgModel.getNickName());
        bean.setActivity_img_url("");
        bean.setActivity_url("");

        switch (msgModel.getStatus()) {
            case 1://直播中
                bean.setUrl_push(msgModel.getUrlPlayHls());
                break;
            case 2://已结束
            case 3://被强制结束
                if (TextUtils.isEmpty(msgModel.getUrlReview())) {//urlreview为空的话就用urlpush
                    if (msgModel.getLiveType() == 3) {//为3的时候 视频为相对地址 自己拼地址  直播不需要
                        bean.setUrl_push(API_HEAD_IMG_URL + msgModel.getUrlPush());
                    } else {
                        bean.setUrl_push(msgModel.getUrlPush());
                    }
                } else {
                    if (msgModel.getLiveType() == 3) {//为3的时候 视频为相对地址 自己拼地址  直播不需要
                        bean.setUrl_push(API_HEAD_IMG_URL + msgModel.getUrlReview().replace(";", ""));
                    } else {
                        bean.setUrl_push(msgModel.getUrlReview().replace(";", ""));
                    }
                }
                break;
            default:
                if (TextUtils.isEmpty(msgModel.getUrlReview())) {//urlreview为空的话就用urlpush
                    if (msgModel.getLiveType() == 3) {//为3的时候 视频为相对地址 自己拼地址  直播不需要
                        bean.setUrl_push(API_HEAD_IMG_URL + msgModel.getUrlPush());
                    } else {
                        bean.setUrl_push(msgModel.getUrlPush());
                    }
                } else {
                    if (msgModel.getLiveType() == 3) {//为3的时候 视频为相对地址 自己拼地址  直播不需要
                        bean.setUrl_push(API_HEAD_IMG_URL + msgModel.getUrlReview().replace(";", ""));
                    } else {
                        bean.setUrl_push(msgModel.getUrlReview().replace(";", ""));
                    }
                }
                break;
        }
        return bean;
    }

}
