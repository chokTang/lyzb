package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.view.Gravity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.model.EaseDingMessageHelper;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

public class EaseChatRowText extends EaseChatRow {

    private TextView contentView;
    private TextView userName_tv;

    public EaseChatRowText(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                R.layout.ease_row_received_message : R.layout.ease_row_sent_message, this);
    }

    @Override
    protected void onFindViewById() {
        contentView = (TextView) findViewById(R.id.tv_chatcontent);
        userName_tv = (TextView) findViewById(R.id.tv_userid);
    }

    @Override
    public void onSetUpView() {
        EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
        Spannable span = EaseSmileUtils.getSmiledText(context, txtBody.getMessage());
        // 设置内容
        contentView.setText(span, BufferType.SPANNABLE);

        boolean MESSAGE_ATTR_IS_VOICE_CALL = message.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_VOICE_CALL, false);
        boolean MESSAGE_ATTR_IS_VIDEO_CALL = message.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_VIDEO_CALL, false);

        /****
         * 文本消息为 语音消息
         */
        if (MESSAGE_ATTR_IS_VOICE_CALL) {

            if (message.direct() == EMMessage.Direct.SEND) {

                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.voice_send_ic);
                contentView.setCompoundDrawablesWithIntrinsicBounds(null,
                        null, drawable, null);
            } else {

                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.voice_rece_ic);
                contentView.setCompoundDrawablesWithIntrinsicBounds(drawable,
                        null, null, null);
            }
            contentView.setCompoundDrawablePadding(10);
        } else if (MESSAGE_ATTR_IS_VIDEO_CALL) {//视频

            if (message.direct() == EMMessage.Direct.SEND) {
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.video_send_ic);
                contentView.setCompoundDrawablesWithIntrinsicBounds(null,
                        null, drawable, null);
            } else {
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.video_rece_ic);
                contentView.setCompoundDrawablesWithIntrinsicBounds(drawable,
                        null, null, null);
            }
            contentView.setCompoundDrawablePadding(10);
        } else {
            contentView.setCompoundDrawablesWithIntrinsicBounds(null,
                    null, null, null);
        }
    }

    @Override
    protected void onViewUpdate(EMMessage msg) {
        switch (msg.status()) {
            case CREATE:
                onMessageCreate();
                break;
            case SUCCESS:
                onMessageSuccess();
                break;
            case FAIL:
                onMessageError();
                break;
            case INPROGRESS:
                onMessageInProgress();
                break;
        }
    }

    public void onAckUserUpdate(final int count) {
        if (ackedView != null) {
            ackedView.post(new Runnable() {
                @Override
                public void run() {
                    ackedView.setVisibility(VISIBLE);
                    ackedView.setText(String.format(getContext().getString(R.string.group_ack_read_count), count));
                }
            });
        }
    }

    private void onMessageCreate() {
        progressBar.setVisibility(View.VISIBLE);
        statusView.setVisibility(View.GONE);
    }

    private void onMessageSuccess() {
        progressBar.setVisibility(View.GONE);
        statusView.setVisibility(View.GONE);

        // Show "1 Read" if this msg is a ding-type msg.
        if (EaseDingMessageHelper.get().isDingMessage(message) && ackedView != null) {
            ackedView.setVisibility(VISIBLE);
            List<String> userList = EaseDingMessageHelper.get().getAckUsers(message);
            int count = userList == null ? 0 : userList.size();
            ackedView.setText(String.format(getContext().getString(R.string.group_ack_read_count), count));
        }

        // Set ack-user list change listener.
        EaseDingMessageHelper.get().setUserUpdateListener(message, userUpdateListener);
    }

    private void onMessageError() {
        progressBar.setVisibility(View.GONE);
        statusView.setVisibility(View.VISIBLE);
    }

    private void onMessageInProgress() {
        progressBar.setVisibility(View.VISIBLE);
        statusView.setVisibility(View.GONE);
    }

    private EaseDingMessageHelper.IAckUserUpdateListener userUpdateListener =
            new EaseDingMessageHelper.IAckUserUpdateListener() {
                @Override
                public void onUpdate(List<String> list) {
                    onAckUserUpdate(list.size());
                }
            };
}
