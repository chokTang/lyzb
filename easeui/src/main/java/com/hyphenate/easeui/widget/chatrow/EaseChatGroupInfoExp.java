package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.utils.EaseCommonUtils;

/**
 * 群信息(群主向自己群发送的消息,XXX已被禁言,xxx已被解除禁言)
 */
public class EaseChatGroupInfoExp extends EaseChatRowText {

    public EaseChatGroupInfoExp(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(R.layout.ease_chat_group_info_exp, this);
    }

    @Override
    protected void onFindViewById() {
        percentageView = (TextView) findViewById(R.id.tv_group_info_text);
        progressBar = findViewById(R.id.progress_bar);
        statusView = findViewById(R.id.msg_status);
    }


    @Override
    public void onSetUpView() {
        percentageView.setText(EaseCommonUtils.setGroupInfo(message.getBody().toString()));
    }
}
