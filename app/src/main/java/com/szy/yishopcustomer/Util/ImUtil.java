package com.szy.yishopcustomer.Util;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/22.
 */

public class ImUtil {
    public static final String XIAOBAO = "jbxxiaobao";//集宝箱小宝账号信息
    public static final String JBX_ONLINE = "jbx196057";//集宝箱在线客服账号
    public static final String JXB_ONLIN_NAME = "官方客服";

    /**
     * 获取聊天记录
     *
     * @param useName
     * @return
     */
    public static List<EMMessage> getMessageHistory(String useName) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(useName);
        List<EMMessage> messages = null;
        if (conversation != null) {
            messages = conversation.getAllMessages();
        }
        if (messages == null)
            messages = new ArrayList<>();

        return messages;
    }

    /**
     * 获取最后一条消息
     *
     * @param useName
     * @return
     */
    public static EMMessage getLastmessage(String useName) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(useName);
        if (conversation == null) {
            return null;
        }
        return conversation.getLastMessage();
    }

    /**
     * 获取未读消息数量
     *
     * @param useName
     * @return
     */
    public static int getMessageNoReadNumber(String useName) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(useName);
        if (conversation != null) {
            return conversation.getUnreadMsgCount();
        }
        return 0;
    }

    /**
     * 设置改聊天记录标记为已读
     *
     * @param useName
     */
    public static void setMessageRead(String useName) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(useName);
        if (conversation != null) {
            conversation.markAllMessagesAsRead();
        }
    }
}
