package com.lyzb.jbx.widget.video;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lyzb.jbx.R;
import com.xiao.nicevideoplayer.NiceUtil;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerController;

public class FullVideoPlayerController extends NiceVideoPlayerController
        implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener {

    private Context mContext;
    private ImageView mImage;
    private ImageView mCenterStart;

    private ImageView mRestartPause;

    private LinearLayout mBottom;
    private TextView mPosition;
    private TextView mDuration;
    private SeekBar mSeek;

    private LinearLayout mLoading;

    private LinearLayout mError;
    private TextView mRetry;

    private boolean topBottomVisible;
    private CountDownTimer mDismissTopBottomCountDownTimer;

    private View showView;
    private OnProgressListener onProgressListener;//播放进度
    private OnCompleteListener onCompleteListener;//播放完成
    private OnPlayingListener onPlayingListener;//及将播放


    public FullVideoPlayerController(Context context) {
        super(context);
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.full_video_palyer_controller, this, true);

        mCenterStart = (ImageView) findViewById(R.id.center_start);

        mRestartPause = (ImageView) findViewById(R.id.restart_or_pause);

        mImage = (ImageView) findViewById(R.id.image);


        mBottom = (LinearLayout) findViewById(R.id.bottom);
        mPosition = (TextView) findViewById(R.id.position);
        mDuration = (TextView) findViewById(R.id.duration);
        mSeek = (SeekBar) findViewById(R.id.seek);


        mLoading = (LinearLayout) findViewById(R.id.loading);


        mError = (LinearLayout) findViewById(R.id.error);
        mRetry = (TextView) findViewById(R.id.retry);


        mCenterStart.setOnClickListener(this);
        mRestartPause.setOnClickListener(this);

        mRetry.setOnClickListener(this);

        mSeek.setOnSeekBarChangeListener(this);


        this.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mCenterStart) {
            if (mNiceVideoPlayer.isIdle()) {
                mNiceVideoPlayer.start();
            } else if (mNiceVideoPlayer.isPaused() || mNiceVideoPlayer.isBufferingPaused()) {
                mNiceVideoPlayer.restart();
            }
        } else if (v == mRestartPause) {
            if (mNiceVideoPlayer.isPlaying() || mNiceVideoPlayer.isBufferingPlaying()) {
                mNiceVideoPlayer.pause();
            } else if (mNiceVideoPlayer.isPaused() || mNiceVideoPlayer.isBufferingPaused()) {
                mNiceVideoPlayer.restart();
            }
        } else if (v == mRetry) {
            mNiceVideoPlayer.restart();
        } else if (v == this) {
            if (mBottom.getVisibility() == VISIBLE) {
                if (getShowView() != null) {
                    getShowView().setVisibility(VISIBLE);
                }
                mBottom.setVisibility(GONE);
            } else {
                if (getShowView() != null) {
                    getShowView().setVisibility(GONE);
                }
                mBottom.setVisibility(VISIBLE);
            }

        }
    }


    public void resetShowView() {
        if (getShowView() != null) {
            getShowView().setVisibility(VISIBLE);
        }
        mBottom.setVisibility(GONE);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mNiceVideoPlayer.isBufferingPaused() || mNiceVideoPlayer.isPaused()) {
            mNiceVideoPlayer.restart();
        }
        long position = (long) (mNiceVideoPlayer.getDuration() * seekBar.getProgress() / 100f);
        mNiceVideoPlayer.seekTo(position);
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public ImageView imageView() {
        return mImage;
    }

    @Override
    public void setImage(@DrawableRes int resId) {
        mImage.setImageResource(resId);
    }

    @Override
    public void setLenght(long length) {

    }

    @Override
    protected void onPlayStateChanged(int playState) {
        switch (playState) {
            case NiceVideoPlayer.STATE_IDLE:
                break;
            case NiceVideoPlayer.STATE_PREPARING:
                mLoading.setVisibility(View.VISIBLE);
                mError.setVisibility(View.GONE);
                mBottom.setVisibility(View.GONE);
//                mCenterStart.setVisibility(View.GONE);
                break;
            case NiceVideoPlayer.STATE_PREPARED:

                startUpdateProgressTimer();
                break;
            case NiceVideoPlayer.STATE_PLAYING:
                if (getOnPlayingListener() != null) {
                    getOnPlayingListener().onPlaying();
                }
                mLoading.setVisibility(View.GONE);
                mImage.setVisibility(View.GONE);
                mRestartPause.setImageResource(R.drawable.ic_video_pause);
                break;
            case NiceVideoPlayer.STATE_PAUSED:
                mLoading.setVisibility(View.GONE);
                mRestartPause.setImageResource(R.drawable.ic_video_start);
                break;
            case NiceVideoPlayer.STATE_BUFFERING_PLAYING:
                if (getOnPlayingListener() != null) {
                    getOnPlayingListener().onPlaying();
                }
                mRestartPause.setImageResource(R.drawable.ic_video_pause);
                mLoading.setVisibility(View.VISIBLE);
                break;
            case NiceVideoPlayer.STATE_BUFFERING_PAUSED:
                mLoading.setVisibility(View.VISIBLE);
                mRestartPause.setImageResource(R.drawable.ic_video_start);
                break;
            case NiceVideoPlayer.STATE_ERROR:
                cancelUpdateProgressTimer();
                mError.setVisibility(View.VISIBLE);
                break;
            case NiceVideoPlayer.STATE_COMPLETED:
                cancelUpdateProgressTimer();
                if (getOnCompleteListener() != null) {
                    getOnCompleteListener().onComplete();
                }
                mImage.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void onPlayModeChanged(int playMode) {

    }

    @Override
    protected void reset() {
        topBottomVisible = false;
        cancelUpdateProgressTimer();
        mSeek.setProgress(0);
        mSeek.setSecondaryProgress(0);
//        mCenterStart.setVisibility(View.VISIBLE);
        mImage.setVisibility(View.VISIBLE);
        mBottom.setVisibility(View.GONE);
        mLoading.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
    }

    @Override
    protected void updateProgress() {
        long position = mNiceVideoPlayer.getCurrentPosition();
        long duration = mNiceVideoPlayer.getDuration();
        int bufferPercentage = mNiceVideoPlayer.getBufferPercentage();
        mSeek.setSecondaryProgress(bufferPercentage);
        int progress = (int) (100f * position / duration);
        mSeek.setProgress(progress);
        mPosition.setText(NiceUtil.formatTime(position));
        mDuration.setText(NiceUtil.formatTime(duration));
        if (getOnProgressListener() != null) {
            getOnProgressListener().onProgress(progress, bufferPercentage);
        }
    }

    @Override
    protected void showChangePosition(long duration, int newPositionProgress) {

    }

    @Override
    protected void hideChangePosition() {

    }

    @Override
    protected void showChangeVolume(int newVolumeProgress) {

    }

    @Override
    protected void hideChangeVolume() {

    }

    @Override
    protected void showChangeBrightness(int newBrightnessProgress) {

    }

    @Override
    protected void hideChangeBrightness() {

    }

    public OnProgressListener getOnProgressListener() {
        return onProgressListener;
    }

    public void setOnProgressListener(OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
    }

    public OnCompleteListener getOnCompleteListener() {
        return onCompleteListener;
    }

    public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

    public OnPlayingListener getOnPlayingListener() {
        return onPlayingListener;
    }

    public void setOnPlayingListener(OnPlayingListener onPlayingListener) {
        this.onPlayingListener = onPlayingListener;
    }

    public interface OnProgressListener {
        void onProgress(int progress, int secondaryProgress);
    }

    public interface OnCompleteListener {
        void onComplete();
    }

    public interface OnPlayingListener {
        void onPlaying();
    }

    public void setShowView(View showView) {
        this.showView = showView;
    }

    public View getShowView() {
        return showView;
    }
}
