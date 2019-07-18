package com.lyzb.jbx.fragment.message;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.adapter.EaseConversationAdapter;
import com.hyphenate.easeui.widget.EaseConversationList;
import com.like.longshaolib.base.BaseToolbarFragment;
import com.like.utilslib.other.LogUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.eventbus.MessageEventBus;
import com.lyzb.jbx.model.eventbus.MessageLoginEventBus;
import com.szy.yishopcustomer.Activity.im.ImCommonActivity;
import com.szy.yishopcustomer.Activity.im.LyMessageListActivity;
import com.szy.yishopcustomer.Activity.im.LyMessageSetActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.ResponseModel.ImGroup.MembersModel;
import com.szy.yishopcustomer.Util.App;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 消息页面
 */
public class MessageFragment extends BaseToolbarFragment implements View.OnClickListener {

    //小宝消息
    View mLayout;
    TextView tv_unread_xb;

    //消息列表
    EaseConversationList mConversationList;
    private List<EMConversation> conversationList = new ArrayList<>();

    private boolean isBanned = false;           //是否已被禁言

    //处理在线客服信息
    View layout_service, view_middle;
    TextView tv_kefu_number;

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        setToolbarTitle("消息");
        mToolbar.inflateMenu(R.menu.setting_union);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(getContext(), LyMessageSetActivity.class));
                return false;
            }
        });

        mLayout = findViewById(R.id.rela_ly_message);
        tv_unread_xb = (TextView) findViewById(R.id.tv_unread_xb);
        mConversationList = (EaseConversationList) findViewById(R.id.conver_message_list);
        layout_service = findViewById(R.id.layout_service);
        tv_kefu_number = (TextView) findViewById(R.id.tv_kefu_number);
        view_middle = findViewById(R.id.view_middle);

        mLayout.setOnClickListener(this);
        layout_service.setOnClickListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        conversationList.clear();
        conversationList.addAll(loadConversationList());
        mConversationList.init(conversationList);
        initData();

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

                    Intent intent = new Intent(getContext(), ImCommonActivity.class);
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
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onItemDelete(int position) {
                EMConversation conversation = conversationList.get(position);
                EMClient.getInstance().chatManager().deleteConversation(conversation.conversationId(), true);
                handler.sendEmptyMessage(0);
            }
        });
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_msg_union;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_ly_message_set:
                startActivity(new Intent(getContext(), LyMessageSetActivity.class));
                break;
            case R.id.rela_ly_message:
                ImUtil.setMessageRead(ImUtil.XIAOBAO);
                tv_unread_xb.setText(String.valueOf(0));
                tv_unread_xb.setVisibility(View.GONE);
                startActivity(new Intent(getContext(), LyMessageListActivity.class));
                break;
            //跳转到在线客服
            case R.id.layout_service:
                //在线客服
                ImUtil.setMessageRead(ImUtil.JBX_ONLINE);
                tv_kefu_number.setText(String.valueOf(0));
                tv_kefu_number.setVisibility(View.GONE);
                Intent intent = new Intent(getContext(), ImCommonActivity.class);
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
            int number = ImUtil.getMessageNoReadNumber(ImUtil.XIAOBAO);
            if (number == 0) {
                tv_unread_xb.setVisibility(View.GONE);
            } else {
                tv_unread_xb.setText(String.valueOf(number));
                tv_unread_xb.setVisibility(View.VISIBLE);
            }
        }
        //处理官方客服消息
        lastMessage = ImUtil.getLastmessage(ImUtil.JBX_ONLINE);
        if (lastMessage != null) {
            int number = ImUtil.getMessageNoReadNumber(ImUtil.JBX_ONLINE);
            if (number == 0) {
                tv_kefu_number.setVisibility(View.GONE);
            } else {
                tv_kefu_number.setText(String.valueOf(number));
                tv_kefu_number.setVisibility(View.VISIBLE);
            }
        }

        //初始化官方客服是否显示
        if (ImUtil.JBX_ONLINE.equals(EaseConstant.CHAT_USER_ID)) {
            layout_service.setVisibility(View.GONE);
            view_middle.setVisibility(View.GONE);
        } else {
            layout_service.setVisibility(View.VISIBLE);
            view_middle.setVisibility(View.VISIBLE);
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
                            int number = ImUtil.getMessageNoReadNumber(ImUtil.XIAOBAO);
                            tv_unread_xb.setText(String.valueOf(number));
                            tv_unread_xb.setVisibility(View.VISIBLE);
                        } else if (ImUtil.JBX_ONLINE.equals(message.getFrom())) {
                            int number = ImUtil.getMessageNoReadNumber(ImUtil.JBX_ONLINE);
                            tv_kefu_number.setText(String.valueOf(number));
                            tv_kefu_number.setVisibility(View.VISIBLE);
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
    public void onSupportVisible() {
        super.onSupportVisible();
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
        RequestAddHead.addNoHttpHead(request, getContext(), Api.API_IM_GROUP_LIST, "GET");

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
                        showToast(getString(R.string.data_error));
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
        Intent intent = new Intent(getContext(), ImCommonActivity.class);

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNoReadMessage(MessageEventBus eventBus) {
        Message message = handler.obtainMessage(0);
        message.obj = eventBus.getList();
        handler.sendMessage(message);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogin(MessageLoginEventBus eventBus) {
        refreshData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean isDelayedData() {
        return false;
    }
}
