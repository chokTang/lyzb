package com.hyphenate.easeui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMError;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.model.EaseVoiceRecorder;
import com.hyphenate.easeui.model.VoiceMessageBean;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowVoicePlayer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Voice recorder view
 *
 */
public class EaseVoiceRecorderView extends RelativeLayout {
    protected Context context;
    protected LayoutInflater inflater;
    protected Drawable[] micImages;
    protected EaseVoiceRecorder voiceRecorder;

    protected PowerManager.WakeLock wakeLock;
    protected ImageView micImage;
    protected TextView recordingHint;
    Timer timer;

    protected Handler micImageHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            // change image
            int index = msg.what;
            if (index < 0 || index > micImages.length - 1) {
                return;
            }
            micImage.setImageDrawable(micImages[index]);
        }
    };

    public EaseVoiceRecorderView(Context context) {
        super(context);
        init(context);
    }

    public EaseVoiceRecorderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EaseVoiceRecorderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.ease_widget_voice_recorder, this);

        micImage = (ImageView) findViewById(R.id.mic_image);
        recordingHint = (TextView) findViewById(R.id.recording_hint);

        voiceRecorder = new EaseVoiceRecorder(micImageHandler);

        // animation resources, used for recording
        micImages = new Drawable[] { getResources().getDrawable(R.drawable.ease_record_animate_01),
                getResources().getDrawable(R.drawable.ease_record_animate_02),
                getResources().getDrawable(R.drawable.ease_record_animate_03),
                getResources().getDrawable(R.drawable.ease_record_animate_04),
                getResources().getDrawable(R.drawable.ease_record_animate_05),
                getResources().getDrawable(R.drawable.ease_record_animate_06),
                getResources().getDrawable(R.drawable.ease_record_animate_07),
                getResources().getDrawable(R.drawable.ease_record_animate_08),
                getResources().getDrawable(R.drawable.ease_record_animate_09),
                getResources().getDrawable(R.drawable.ease_record_animate_10),
                getResources().getDrawable(R.drawable.ease_record_animate_11),
                getResources().getDrawable(R.drawable.ease_record_animate_12),
                getResources().getDrawable(R.drawable.ease_record_animate_13),
                getResources().getDrawable(R.drawable.ease_record_animate_14), };

        wakeLock = ((PowerManager) context.getSystemService(Context.POWER_SERVICE)).newWakeLock(
                PowerManager.SCREEN_DIM_WAKE_LOCK, "demo");
    }

    /**
     * on speak button touched
     * 
     * @param v
     * @param event
     */
    public boolean onPressToSpeakBtnTouch(View v, MotionEvent event, EaseVoiceRecorderCallback recorderCallback) {
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            try {
                EaseChatRowVoicePlayer voicePlayer = EaseChatRowVoicePlayer.getInstance(context);
                if (voicePlayer.isPlaying())
                    voicePlayer.stop();
                v.setPressed(true);
                startRecording();
            } catch (Exception e) {
                v.setPressed(false);
            }
            return true;
        case MotionEvent.ACTION_MOVE:
            if (event.getY() < 0) {
                showReleaseToCancelHint();
            } else {
                showMoveUpToCancelHint();
            }
            return true;
        case MotionEvent.ACTION_UP:
            recorderCallback.onVoiceRecordComplete(null, 0);
            v.setPressed(false);
            if (event.getY() < 0) {
                // discard the recorded audio.
                discardRecording();
            } else {
                // stop recording and send voice file
                try {
                    int length = stopRecoding();
                    if (length > 0) {
                        if (recorderCallback != null) {
                            recorderCallback.onVoiceRecordComplete(getVoiceFilePath(), length);
                        }
                    } else if (length == EMError.FILE_INVALID) {
                        recorderCallback.onVoiceRecordComplete(getVoiceFilePath(), length);
                        Toast.makeText(context, R.string.Recording_without_permission, Toast.LENGTH_SHORT).show();
                    } else {
                        recorderCallback.onVoiceRecordComplete(null, 0);
                        Toast.makeText(context, R.string.The_recording_time_is_too_short, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    recorderCallback.onVoiceRecordComplete(null, 0);
                    Toast.makeText(context, R.string.send_failure_please, Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        default:
            discardRecording();
            return false;
        }
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            VoiceMessageBean bean = (VoiceMessageBean) msg.obj;
            switch (msg.what) {
                case 1:
                    bean.getRecorderCallback().onVoiceRecordComplete(null, 0);
                    bean.getV().setPressed(false);
                    if (bean.getEvent().getY() < 0) {
                        // discard the recorded audio.
                        discardRecording();
                    } else {
                        // stop recording and send voice file
                        try {
                            int length = stopRecoding();
                            if (length > 0) {
                                if (bean.getRecorderCallback() != null) {
                                    bean.getRecorderCallback().onVoiceRecordComplete(getVoiceFilePath(), length);
                                }
                            } else if (length == EMError.FILE_INVALID) {
                                bean.getRecorderCallback().onVoiceRecordComplete(getVoiceFilePath(), length);
                                Toast.makeText(context, R.string.Recording_without_permission, Toast.LENGTH_SHORT).show();
                            } else {
                                bean.getRecorderCallback().onVoiceRecordComplete(null, 0);
                                Toast.makeText(context, R.string.The_recording_time_is_too_short, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            bean.getRecorderCallback().onVoiceRecordComplete(null, 0);
                            Toast.makeText(context, R.string.send_failure_please, Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
            }
        }
    };

    /**
     * on speak button touched  60s
     * 超过60s就自动取消录音 并上传
     * @param v
     * @param event
     */
    public boolean onPressToSpeakBtnTouchTime(final View v, final MotionEvent event, final EaseVoiceRecorderCallback recorderCallback, int second) {
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            timer = new Timer();
            final VoiceMessageBean bean = new VoiceMessageBean();
            bean.setEvent(event);
            bean.setV(v);
            bean.setRecorderCallback(recorderCallback);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.what = 1;
                    message.obj = bean;
                    handler.sendMessage(message);
                }
            }, 1000*second); // 按下时长设置

            try {
                EaseChatRowVoicePlayer voicePlayer = EaseChatRowVoicePlayer.getInstance(context);
                if (voicePlayer.isPlaying())
                    voicePlayer.stop();
                v.setPressed(true);
                startRecording();
            } catch (Exception e) {
                v.setPressed(false);
            }
            return true;
        case MotionEvent.ACTION_MOVE:
            if (event.getY() < 0) {
                showReleaseToCancelHint();
            } else {
                showMoveUpToCancelHint();
            }
            return true;
        case MotionEvent.ACTION_UP:
            recorderCallback.onVoiceRecordComplete(null, 0);
            v.setPressed(false);
            if (event.getY() < 0) {
                // discard the recorded audio.
                discardRecording();
            } else {
                // stop recording and send voice file
                try {
                    int length = stopRecoding();
                    if (length > 0) {
                        if (recorderCallback != null) {
                            recorderCallback.onVoiceRecordComplete(getVoiceFilePath(), length);
                        }
                    } else if (length == EMError.FILE_INVALID) {
                        recorderCallback.onVoiceRecordComplete(getVoiceFilePath(), length);
                        Toast.makeText(context, R.string.Recording_without_permission, Toast.LENGTH_SHORT).show();
                    } else {
                        recorderCallback.onVoiceRecordComplete(null, 0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    recorderCallback.onVoiceRecordComplete(null, 0);
                    Toast.makeText(context, R.string.send_failure_please, Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        default:
            discardRecording();
            return false;
        }
    }

    public interface EaseVoiceRecorderCallback {
        /**
         * on voice record complete
         * 
         * @param voiceFilePath
         *            录音完毕后的文件路径
         * @param voiceTimeLength
         *            录音时长
         */
        void onVoiceRecordComplete(String voiceFilePath, int voiceTimeLength);
    }

    public void startRecording() {

        if (!EaseCommonUtils.isSdcardExist()) {
            Toast.makeText(context, R.string.Send_voice_need_sdcard_support, Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            wakeLock.acquire();
            this.setVisibility(View.VISIBLE);
            recordingHint.setText(context.getString(R.string.move_up_to_cancel));
            recordingHint.setBackgroundColor(Color.TRANSPARENT);
            voiceRecorder.startRecording(context);
        } catch (Exception e) {

            e.printStackTrace();

            if (wakeLock.isHeld()){
                wakeLock.release();
            }

            if (voiceRecorder != null){
                voiceRecorder.discardRecording();
            }

            this.setVisibility(View.INVISIBLE);
            Toast.makeText(context, R.string.recoding_fail, Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void showReleaseToCancelHint() {
        recordingHint.setText(context.getString(R.string.release_to_cancel));
        recordingHint.setBackgroundResource(R.drawable.ease_recording_text_hint_bg);
    }

    public void showMoveUpToCancelHint() {
        recordingHint.setText(context.getString(R.string.move_up_to_cancel));
        recordingHint.setBackgroundColor(Color.TRANSPARENT);
    }

    public void discardRecording() {
        if (wakeLock.isHeld())
            wakeLock.release();
        try {
            // stop recording
            if (voiceRecorder.isRecording()) {
                voiceRecorder.discardRecording();
                this.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
        }
    }

    public int stopRecoding() {
        this.setVisibility(View.INVISIBLE);
        if (wakeLock.isHeld())
            wakeLock.release();
        return voiceRecorder.stopRecoding();
    }

    public String getVoiceFilePath() {
        return voiceRecorder.getVoiceFilePath();
    }

    public String getVoiceFileName() {
        return voiceRecorder.getVoiceFileName();
    }

    public boolean isRecording() {
        return voiceRecorder.isRecording();
    }

}
