package com.hyphenate.easeui.model;

import android.view.MotionEvent;
import android.view.View;

import com.hyphenate.easeui.widget.EaseVoiceRecorderView;

/**
 * Created by :TYK
 * Date: 2019/4/28  19:43
 * Desc:
 */
public class VoiceMessageBean {
    public View v;
    public MotionEvent event;
    public EaseVoiceRecorderView.EaseVoiceRecorderCallback recorderCallback;

    public View getV() {
        return v;
    }

    public void setV(View v) {
        this.v = v;
    }

    public MotionEvent getEvent() {
        return event;
    }

    public void setEvent(MotionEvent event) {
        this.event = event;
    }

    public EaseVoiceRecorderView.EaseVoiceRecorderCallback getRecorderCallback() {
        return recorderCallback;
    }

    public void setRecorderCallback(EaseVoiceRecorderView.EaseVoiceRecorderCallback recorderCallback) {
        this.recorderCallback = recorderCallback;
    }
}
