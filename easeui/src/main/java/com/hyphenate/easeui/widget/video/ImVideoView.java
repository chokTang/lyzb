package com.hyphenate.easeui.widget.video;

import android.content.Context;
import android.util.AttributeSet;

import com.superrtc.sdk.VideoView;

/**
 * @author wyx
 * @role IM 视频通话 view 拉伸至全屏
 * @time 2018 2018/9/14 14:12
 */

public class ImVideoView extends VideoView {

    public ImVideoView(Context context) {
        super(context);
    }

    public ImVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width;
        int height;

        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        width = widthSpecSize;
        height = heightSpecSize;
        setMeasuredDimension(width, height);
    }
}
