package com.szy.yishopcustomer.Activity.im;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.Toast;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessage.Type;
import com.hyphenate.easeui.EaseUI;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hyphenate.chat.EMMessage.Type.*;

/**
 * @author wyx
 * @role 消息设置 声音提醒
 * @time 2018 2018/8/22 14:56
 */

public class LyMessageSetActivity extends Activity {

    @BindView(R.id.toolbar_ly_message_set)
    Toolbar mToolbar;
    @BindView(R.id.img_message_switch)
    ImageView mImageView_Switch;

    private boolean isOn = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lymessage_set);
        ButterKnife.bind(this);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (SharedPreferencesUtils.getParam(this, Key.IM_SOUND.getValue(), false) != null) {
            isOn = (boolean) SharedPreferencesUtils.getParam(this, Key.IM_SOUND.getValue(), false);
        }

        mImageView_Switch.setImageResource(isOn ? R.mipmap.bg_mess_on : R.mipmap.bg_mess_off);
        mImageView_Switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOn) {
                    isOn = false;
                    mImageView_Switch.setImageResource(R.mipmap.bg_mess_off);
                    SharedPreferencesUtils.setParam(LyMessageSetActivity.this, Key.IM_SOUND.getValue(), false);
                    EaseUI.getInstance().setSettingsProvider(new EaseUI.EaseSettingsProvider() {
                        @Override
                        public boolean isMsgNotifyAllowed(EMMessage message) {
                            return true;
                        }

                        @Override
                        public boolean isMsgSoundAllowed(EMMessage message) {
                            return false;
                        }

                        @Override
                        public boolean isMsgVibrateAllowed(EMMessage message) {
                            return true;
                        }

                        @Override
                        public boolean isSpeakerOpened() {
                            return true;
                        }
                    });
                } else {
                    SharedPreferencesUtils.setParam(LyMessageSetActivity.this, Key.IM_SOUND.getValue(), true);
                    isOn = true;
                    mImageView_Switch.setImageResource(R.mipmap.bg_mess_on);
                    EaseUI.getInstance().setSettingsProvider(new EaseUI.EaseSettingsProvider() {
                        @Override
                        public boolean isMsgNotifyAllowed(EMMessage message) {
                            return true;
                        }

                        @Override
                        public boolean isMsgSoundAllowed(EMMessage message) {
                            return true;
                        }

                        @Override
                        public boolean isMsgVibrateAllowed(EMMessage message) {
                            return true;
                        }

                        @Override
                        public boolean isSpeakerOpened() {
                            return true;
                        }
                    });
                }
            }
        });
    }
}
