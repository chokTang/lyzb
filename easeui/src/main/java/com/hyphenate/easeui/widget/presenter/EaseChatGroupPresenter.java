package com.hyphenate.easeui.widget.presenter;

import android.content.Context;
import android.widget.BaseAdapter;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.chatrow.EaseChatGroupInfoExp;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;

/**
 * 群组
 */

public class EaseChatGroupPresenter extends EaseChatTextPresenter {

    @Override
    protected EaseChatRow onCreateChatRow(Context cxt, EMMessage message, int position, BaseAdapter adapter) {
        return new EaseChatGroupInfoExp(cxt, message, position, adapter);
    }

    @Override
    protected void handleReceiveMessage(EMMessage message) {

    }
}
