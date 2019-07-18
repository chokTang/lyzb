package com.lyzb.jbx.util;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.widget.ImageView;

import java.io.IOException;

public class MediaPlayerUtil {
    private MediaPlayer player = null;
    private boolean isPlaying = false;
    private AnimationDrawable animationDrawable;
    private int position = 0;
    private String voiceUrl = "";

    private static class Holder {
        private static final MediaPlayerUtil intance = new MediaPlayerUtil();
    }

    public static MediaPlayerUtil getInstance() {
        return Holder.intance;
    }

    // 初始化MediaPlayer
    private void initMediaPlayer() {
        if (player == null)
            player = new MediaPlayer();
        // 设置音量，参数分别表示左右声道声音大小，取值范围为0~1
        player.setVolume(0.5f, 0.5f);
        // 设置是否循环播放
        player.setLooping(false);
    }

    // 播放
    public void play(String url, final ImageView imge) {
        try {
            if (player == null) {
                initMediaPlayer();
            } else {
                if (TextUtils.equals(url, voiceUrl)) {
                    if (isPlaying || player.isPlaying()) {
                        pause();
                    } else {
                        reStart(imge);
                    }
                    return;
                } else {
                    if (player != null) {//释放上一个播放器
                        stop();
                        initMediaPlayer();
                    }
                }
            }
            voiceUrl = url;
            if (imge != null) {
                animationDrawable = (AnimationDrawable) imge.getBackground();
                if (animationDrawable != null) {
                    animationDrawable.setOneShot(false);
                    animationDrawable.start();
                }
            }
            isPlaying = true;
            player.setDataSource(url);
            player.prepareAsync();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    player.start();
                    isPlaying = true;
                }
            });
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (animationDrawable != null) {
                        animationDrawable.stop();
                        animationDrawable = null;
                    }
                    stop();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停
     */
    private void pause() {
        if (player == null) return;
        isPlaying = false;
        position = player.getCurrentPosition();
        player.pause();
        if (animationDrawable != null) {
            animationDrawable.stop();
        }
    }

    /**
     * 再次播放
     */
    private void reStart(ImageView imageView) {
        if (player == null) return;
        try {
            if (position > 0) player.seekTo(position);
            player.start();
            if (imageView != null) {
                animationDrawable = (AnimationDrawable) imageView.getBackground();
                if (animationDrawable != null) {
                    animationDrawable.setOneShot(false);
                    animationDrawable.start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        isPlaying = false;
        // 释放mediaPlayer
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
        if (animationDrawable != null) {
            animationDrawable.stop();
            animationDrawable.selectDrawable(0);
            animationDrawable = null;
        }
    }
}
