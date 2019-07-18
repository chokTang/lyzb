package com.szy.yishopcustomer.Activity.im;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.adapter.EaseConversationAdapter;
import com.hyphenate.easeui.widget.EaseConversationList;
import com.like.utilslib.other.LogUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.activity.HomeActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.ResponseModel.ImGroup.MembersModel;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.DateUtil;
import com.szy.yishopcustomer.Util.ImUtil;
import com.szy.yishopcustomer.Util.RequestAddHead;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.im.ImHeaderGoodsModel;
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
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author wyx
 * @role 消息列表 ac
 * @time 2018 2018/8/22 10:42
 */

public class LyMessageActivity extends Activity implements View.OnClickListener {
    @BindView(R.id.toolbar_ly_message)
    Toolbar mToolbar;

    @BindView(R.id.img_ly_message_set)
    ImageView mImageView_Back;
    @BindView(R.id.rela_ly_message)
    LinearLayout mLayout;
    @BindView(R.id.tv_message_hint_content)
    TextView mTextView_Content;
    @BindView(R.id.tv_message_hint_time)
    TextView mTextView_Time;
    @BindView(R.id.tv_unread_xb)
    TextView tv_unread_xb;

    @BindView(R.id.conver_message_list)
    EaseConversationList mConversationList;
    private List<EMConversation> conversationList = new ArrayList<>();

    private boolean isBanned = false;           //是否已被禁言

    //处理在线客服信息
    @BindView(R.id.tv_kefu_last_message)
    TextView tv_kefu_last_message;//最后一条消息
    @BindView(R.id.tv_kefu_message_time)
    TextView tv_kefu_message_time;//时间
    @BindView(R.id.layout_service)
    LinearLayout layout_service;
    @BindView(R.id.tv_kefu_number)
    TextView tv_kefu_number;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lymessage);
        ButterKnife.bind(this);

        /*注册消息监听*/
        EMClient.getInstance().chatManager().addMessageListener(listener);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.getInstance().msg_info_type) {
                    App.getInstance().msg_info_type = false;
                    startActivity(new Intent(LyMessageActivity.this, HomeActivity.class));
                    finish();
                } else {
                    finish();
                }
            }
        });

        mImageView_Back.setOnClickListener(this);
        layout_service.setOnClickListener(this);

        conversationList.clear();
        conversationList.addAll(loadConversationList());
        mConversationList.init(conversationList);

        mConversationList.getAdapter().setOnItemClickListener(new EaseConversationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                EMConversation conversation = conversationList.get(position);
                conversation.markAllMessagesAsRead();
                if (conversation.getType() == EMConversation.EMConversationType.GroupChat) {
                    //群组聊天
                    EMMessage message_group = conversation.getLastMessage();

                    /********
                     * 群消息 不区分方向  直接取TO
                     *
                     */

                    getGroupStatus(message_group, EaseConstant.CHAT_USER_ID);

                } else if (conversation.getType() == EMConversation.EMConversationType.Chat) {
                    //单聊
                    EMMessage other = conversation.getLatestMessageFromOthers();
                    EMMessage lastMessage = conversation.getLastMessage();

                    Intent intent = new Intent(LyMessageActivity.this, ImCommonActivity.class);
                    ImHeaderGoodsModel model = new ImHeaderGoodsModel();

                    model.setChatType(EaseConstant.CHATTYPE_SINGLE);
                    if (other == null) {
                        /** 我发出的消息 */
                        model.setShopImName(lastMessage.getTo());
                        model.setShopName(lastMessage.getStringAttribute(EaseConstant.EXTRA_TO_NICKNAME, lastMessage.getUserName()));
                        model.setShopHeadimg(lastMessage.getStringAttribute(EaseConstant.EXTRA_TO_NICKNAME, ""));
                        model.setShopId(lastMessage.getStringAttribute(EaseConstant.EXTRA_TO_USERID, ""));
                    } else {
                        /** 别人发出的消息 */
                        model.setShopImName(other.getFrom());
                        model.setShopName(other.getStringAttribute(EaseConstant.EXTRA_FROM_NICKNAME, other.getUserName()));
                        model.setShopHeadimg(other.getStringAttribute(EaseConstant.EXTRA_FROM_HEADER, ""));
                        model.setShopId(other.getStringAttribute(EaseConstant.EXTRA_FROM_USERID, ""));
                    }

                    Bundle args = new Bundle();
                    args.putSerializable(ImCommonActivity.PARAMS_GOODS, model);
                    intent.putExtras(args);
                    startActivity(intent);
                }
            }

            @Override
            public void onItemDelete(int position) {
                EMConversation conversation = conversationList.get(position);
                EMClient.getInstance().chatManager().deleteConversation(conversation.conversationId(), true);
                handler.sendEmptyMessage(0);
            }
        });


        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_ly_message_set:
                startActivity(new Intent(this, LyMessageSetActivity.class));
                break;
            case R.id.rela_ly_message:
                ImUtil.setMessageRead(ImUtil.XIAOBAO);
                tv_unread_xb.setText(String.valueOf(0));
                tv_unread_xb.setVisibility(View.GONE);
                startActivity(new Intent(this, LyMessageListActivity.class));
                break;
            //跳转到在线客服
            case R.id.layout_service:
                //在线客服
                ImUtil.setMessageRead(ImUtil.JBX_ONLINE);
                tv_kefu_number.setText(String.valueOf(0));
                tv_kefu_number.setVisibility(View.GONE);
                Intent intent = new Intent(this, ImCommonActivity.class);
                ImHeaderGoodsModel model = new ImHeaderGoodsModel();

                model.setChatType(EaseConstant.CHATTYPE_SINGLE);
                model.setShopImName(ImUtil.JBX_ONLINE);
                model.setShopName(ImUtil.JXB_ONLIN_NAME);
                model.setShopHeadimg("");
                model.setShopId("");

                Bundle args = new Bundle();
                args.putSerializable(ImCommonActivity.PARAMS_GOODS, model);
                intent.putExtras(args);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        EMMessage lastMessage = ImUtil.getLastmessage(ImUtil.XIAOBAO);
        if (lastMessage != null) {
            EMTextMessageBody body = (EMTextMessageBody) lastMessage.getBody();
            mTextView_Content.setText(body.getMessage());

            mTextView_Time.setText(DateUtil.format(lastMessage.getMsgTime()));
            mTextView_Time.setVisibility(View.VISIBLE);
            int number = ImUtil.getMessageNoReadNumber(ImUtil.XIAOBAO);
            if (number == 0) {
                tv_unread_xb.setVisibility(View.GONE);
            } else {
                tv_unread_xb.setText(String.valueOf(number));
                tv_unread_xb.setVisibility(View.VISIBLE);
            }
            mLayout.setOnClickListener(this);
        } else {
            mTextView_Content.setText("暂无消息");
            mTextView_Time.setVisibility(View.INVISIBLE);
            mLayout.setOnClickListener(null);
        }

        //处理官方客服消息
        lastMessage = ImUtil.getLastmessage(ImUtil.JBX_ONLINE);
        if (lastMessage != null) {
            EMTextMessageBody body = (EMTextMessageBody) lastMessage.getBody();
            tv_kefu_last_message.setText(body.getMessage());

            tv_kefu_message_time.setText(DateUtil.format(lastMessage.getMsgTime()));
            tv_kefu_message_time.setVisibility(View.VISIBLE);
            int number = ImUtil.getMessageNoReadNumber(ImUtil.JBX_ONLINE);
            if (number == 0) {
                tv_kefu_number.setVisibility(View.GONE);
            } else {
                tv_kefu_number.setText(String.valueOf(number));
                tv_kefu_number.setVisibility(View.VISIBLE);
            }
        } else {
            tv_kefu_last_message.setText("暂无消息");
            tv_kefu_message_time.setVisibility(View.INVISIBLE);
        }


        //初始化官方客服是否显示
        if (ImUtil.JBX_ONLINE.equals(EaseConstant.CHAT_USER_ID)) {
            layout_service.setVisibility(View.GONE);
        } else {
            layout_service.setVisibility(View.VISIBLE);
        }
    }

    /***
     * im 消息数据
     *
     * im账号
     * 3039931970@qq.com
     * lyzb_miniapp
     *
     * @return
     */
    protected List<EMConversation> loadConversationList() {
        // get all conversations
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        /**
         * lastMsgTime will change if there is new message during sorting
         * so use synchronized to make sure timestamp of last message won't change.
         */
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    /***
                     * 过滤小宝消息的会话
                     * 将其他消息添加到会话列表
                     */
                    if (ImUtil.JBX_ONLINE.equals("jbx" + App.getInstance().userId)) {
                        if (!ImUtil.XIAOBAO.equals(conversation.getLastMessage().getFrom()) &&
                                !ImUtil.XIAOBAO.equals(conversation.getLastMessage().getTo())) {
                            sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
                            LogUtil.loge("--IM测试--" + conversation.getUnreadMsgCount() + "----" + conversation.getLastMessage().getBody().toString());
                        }
                    } else {
                        if (!ImUtil.XIAOBAO.equals(conversation.getLastMessage().getFrom()) &&
                                !ImUtil.XIAOBAO.equals(conversation.getLastMessage().getTo()) &&
                                !ImUtil.JBX_ONLINE.equals(conversation.getLastMessage().getFrom()) &&
                                !ImUtil.JBX_ONLINE.equals(conversation.getLastMessage().getTo())) {
                            sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
                            LogUtil.loge("--IM测试--" + conversation.getUnreadMsgCount() + "----" + conversation.getLastMessage().getBody().toString());
                        }
                    }
                }
            }
        }
        try {
            // Internal is TimSort algorithm, has bug
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EMConversation> list = new ArrayList<EMConversation>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list.add(sortItem.second);
        }
        return list;
    }

    /**
     * sort conversations according time stamp of last message
     *
     * @param conversationList
     */
    private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
        Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
            @Override
            public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {
                if (con1.first.equals(con2.first)) {
                    return 0;
                } else if (con2.first.longValue() > con1.first.longValue()) {
                    return 1;
                } else {
                    return -1;
                }
            }

        });
    }

    private EMMessageListener listener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> list) {
            Message message = handler.obtainMessage(0);
            message.obj = list;
            handler.sendMessage(message);
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> list) {
        }

        @Override
        public void onMessageRead(List<EMMessage> list) {
        }

        @Override
        public void onMessageDelivered(List<EMMessage> list) {
        }

        @Override
        public void onMessageRecalled(List<EMMessage> list) {
        }

        @Override
        public void onMessageChanged(EMMessage emMessage, Object o) {
        }
    };

    protected Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                //刷新列表 刷新小宝消息和客服聊天消息
                case 0:
                    List<EMMessage> list = (List<EMMessage>) msg.obj;
                    if (list != null && list.size() > 0) {
                        EMMessage message = list.get(list.size() - 1);
                        if (ImUtil.XIAOBAO.equals(message.getFrom())) {
                            EMTextMessageBody body = (EMTextMessageBody) message.getBody();
                            mTextView_Content.setText(body.getMessage());
                            mTextView_Time.setText(DateUtil.format(message.getMsgTime()));

                            int number = ImUtil.getMessageNoReadNumber(ImUtil.XIAOBAO);
                            mTextView_Time.setVisibility(View.VISIBLE);
                            tv_unread_xb.setText(String.valueOf(number));
                            tv_unread_xb.setVisibility(View.VISIBLE);
                            mLayout.setOnClickListener(LyMessageActivity.this);
                        } else if (ImUtil.JBX_ONLINE.equals(message.getFrom())) {
                            EMTextMessageBody body = (EMTextMessageBody) message.getBody();
                            tv_kefu_last_message.setText(body.getMessage());
                            tv_kefu_message_time.setText(DateUtil.format(message.getMsgTime()));
                            int number = ImUtil.getMessageNoReadNumber(ImUtil.JBX_ONLINE);
                            tv_kefu_number.setText(String.valueOf(number));
                            tv_kefu_number.setVisibility(View.VISIBLE);
                            tv_kefu_message_time.setVisibility(View.VISIBLE);
                        }
                    }

                    conversationList.clear();
                    conversationList.addAll(loadConversationList());
                    mConversationList.refresh();
                    break;
            }

            return true;
        }
    });

    @Override
    protected void onResume() {
        super.onResume();
        handler.sendEmptyMessage(0);
        initData();
        if (EaseConstant.IS_EXIT_GROUP) {
            refreshData();
            EaseConstant.IS_EXIT_GROUP = false;
        }
    }

    /**
     * 当前页面 后台通知栏再次进入
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        refreshData();
    }

    private void refreshData() {

        conversationList.clear();
        conversationList.addAll(loadConversationList());
        mConversationList.refresh();

        initData();
    }

    private List<MembersModel> mModelList = new ArrayList<>();

    /****
     *
     * 查询该用户在该群的身份
     *
     * @param message     消息体
     * @param userId            当前用户在环信的ID
     */
    private void getGroupStatus(final EMMessage message, String userId) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_IM_GROUP_LIST, RequestMethod.GET);
        RequestAddHead.addNoHttpHead(request, this, Api.API_IM_GROUP_LIST, "GET");

        request.add("groupId", message.getTo());
        request.add("memberId", userId);
        request.add("page", 1);
        request.add("rows", 1);

        requestQueue.add(HttpWhat.HTTP_IM_GROUP_MEMBER.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                if (response.responseCode() == Api.SUCCESS_INT) {

                    String status = JSONObject.parseObject(response.get()).getString("status");
                    if (status.equals(Api.SUCCESS_STRING)) {

                        String data = JSONObject.parseObject(response.get()).getString("data");
                        String list = JSONObject.parseObject(data).getString("list");

                        mModelList = JSONObject.parseArray(list, MembersModel.class);

                        isBanned = mModelList.get(0).banned;
                        toGroupIm(message);
                    } else {
                        Toast.makeText(LyMessageActivity.this, R.string.data_error, Toast.LENGTH_SHORT).show();
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


    /*****
     * intent 传递 EaseConstant.EXTRA_TO_NICKNAME 群名称
     *            EaseConstant.EXTRA_TO_HEADER 群头像
     *            EXTRA_GROUP_BANNED_ID     群组内 被禁言者的ID     （用于 商城端）
     *            EXTRA_GEOUP_BANNED_ALL    群组 是否已开启全员禁言  （用于 商城端）
     *
     *            group_message.getUserName() 群ID
     *
     */
    private void toGroupIm(EMMessage message) {
        Intent intent = new Intent(LyMessageActivity.this, ImCommonActivity.class);

        ImHeaderGoodsModel model = new ImHeaderGoodsModel();
        model.setChatType(EaseConstant.CHATTYPE_GROUP);

        String headImg = Utils.setImgNetUrl(Api.API_CITY_HOME_MER_IMG_URL, message.getStringAttribute(EaseConstant.EXTRA_TO_HEADER, ""));
        model.setShopHeadimg(headImg);
        model.setShopName(message.getStringAttribute(EaseConstant.EXTRA_TO_NICKNAME, ""));

        model.setShopImName(message.getTo());
        model.setShopId(message.getTo());

        model.setIsBanned(isBanned);
        Bundle args = new Bundle();
        args.putSerializable(ImCommonActivity.PARAMS_GOODS, model);
        intent.putExtras(args);

        startActivity(intent);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN) {

            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (App.getInstance().msg_info_type) {
                    App.getInstance().msg_info_type = false;
                    startActivity(new Intent(LyMessageActivity.this, HomeActivity.class));
                    finish();
                } else {
                    finish();
                }
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(listener);
    }
}
