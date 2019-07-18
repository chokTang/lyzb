/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hyphenate.easeui;

public class EaseConstant {
    public static final String MESSAGE_ATTR_IS_VOICE_CALL = "is_voice_call";
    public static final String MESSAGE_ATTR_IS_VIDEO_CALL = "is_video_call";

    public static final String MESSAGE_TYPE_RECALL = "message_recall";

    public static final String MESSAGE_ATTR_IS_BIG_EXPRESSION = "em_is_big_expression";
    public static final String MESSAGE_ATTR_EXPRESSION_ID = "em_expression_id";

    public static final String MESSAGE_ATTR_AT_MSG = "em_at_list";
    public static final String MESSAGE_ATTR_VALUE_AT_MSG_ALL = "ALL";

    public static String SC_IMG_BASE = null;
    //标记当前用户是否 退出群聊 动作
    public static boolean IS_EXIT_GROUP=false;


    public static final int CHATTYPE_SINGLE = 1;
    public static final int CHATTYPE_GROUP = 2;
    public static final int CHATTYPE_CHATROOM = 3;

    public static final String EXTRA_CHAT_TYPE = "chatType";
    public static final String EXTRA_USER_ID = "userId";

    public static final String EXTRA_GROUP_BANNED_ID = "bannedId";      //群组 被禁言者的ID
    public static final String EXTRA_GROUP_BANNED_ALL = "bannedGroup";  //群组 是否全员禁言 1 全员禁言  0 解除全员禁言

    public static final String EXTRA_GROUP_IS_ME = "groupMe";        //此群是自己的群
    public static final String EXTRA_GROUP_IS_BANNED = "isBanned";     //是否已被禁言

    public static final String EXTRA_OTHER_NAME="otherName";        //EaseChatFragment 页面 titlebar的文本  单聊:店铺名称  群聊:群组名称

    /** 当前用户的环信ID */
    public static String CHAT_USER_ID = null;

    /*用户自定义的字段*/
    /****
     * from 表示-主动发送消息时  自己的信息  被动接受消息时  对方的信息
     */
    public static final String EXTRA_FROM_NICKNAME = "fromName";
    public static final String EXTRA_FROM_HEADER = "fromHeader";
    public static final String EXTRA_FROM_USERID = "fromId";
    public static final String EXTRA_FROM_ADMIN = "fromAdmin";

    public static final String EXTRA_TO_NICKNAME = "toName";
    public static final String EXTRA_TO_HEADER = "toHeader";
    public static final String EXTRA_TO_USERID = "toId";

    public static final String EXTRA_GOODSPRICE = "goodsPrice";
    public static final String EXTRA_GOODSNAME = "goodsName";
    public static final String EXTRA_GOODSID = "goodsId";
    public static final String EXTRA_GOODSURL = "goodsUrl";

    public static final String EXTRA_XB_TITLE = "title";
    public static final String EXTRA_XB_CONTENT = "value";
    public static final String EXTRA_XB_LINK_URL = "xbLinkUrl";
    public static final String EXTRA_XB_MESSAGE_TYPE = "type";
}
