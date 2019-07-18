package com.hyphenate.easeui.ui;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVideoMessageBody;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.utils.TxCustomVideoPlayerController;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import java.io.File;

/**
 * show the video
 */
public class EaseShowVideoActivity extends EaseBaseActivity {
    private static final String TAG = "ShowVideoActivity";

    private String localFilePath;
    private NiceVideoPlayer nice_video;
    private ProgressBar progressBar;
    private LinearLayout progress_layout;
    private TxCustomVideoPlayerController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.ease_showvideo_activity);
        nice_video = (NiceVideoPlayer) findViewById(R.id.nice_video);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progress_layout = (LinearLayout) findViewById(R.id.progress_layout);

        final EMMessage message = getIntent().getParcelableExtra("msg");
        if (!(message.getBody() instanceof EMVideoMessageBody)) {
            Toast.makeText(EaseShowVideoActivity.this, "Unsupported message body", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        EMVideoMessageBody messageBody = (EMVideoMessageBody) message.getBody();

        localFilePath = messageBody.getLocalUrl();

        controller = new TxCustomVideoPlayerController(this);
        controller.setTitle("");

        nice_video.setPlayerType(NiceVideoPlayer.TYPE_NATIVE);
        nice_video.setController(controller);
        controller.setDoVideoPlayer(new TxCustomVideoPlayerController.DoVideoPlayer() {
            @Override
            public void isPlay(boolean isplay, boolean isShow, boolean iscontroller) {
                if (isplay && !isShow) {
                    if (nice_video.isIdle()) {
                        nice_video.start();
                    } else {
                        if (nice_video.isPlaying() || nice_video.isBufferingPlaying()) {
                            nice_video.pause();
                        } else if (nice_video.isPaused() || nice_video.isBufferingPaused()) {
                            nice_video.restart();
                        }
                    }
                }
            }
        });

        if (localFilePath != null && new File(localFilePath).exists()) {
            showLocalVideo(localFilePath);
        } else {
            downloadVideo(message);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        NiceVideoPlayerManager.instance().onBackPressd();
        finish();
    }

    /**
     * show local video
     *
     * @param localPath -- local path of the video file
     */
    private void showLocalVideo(String localPath) {
        nice_video.setVisibility(View.VISIBLE);
        nice_video.setUp(localPath, null);
        nice_video.start();
    }

    /**
     * download video file
     */
    private void downloadVideo(EMMessage message) {
        progress_layout.setVisibility(View.VISIBLE);
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        progress_layout.setVisibility(View.GONE);
                        progressBar.setProgress(0);
                        showLocalVideo(localFilePath);
                    }
                });
            }

            @Override
            public void onProgress(final int progress, String status) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        progressBar.setProgress(progress);
                    }
                });

            }

            @Override
            public void onError(int error, String msg) {
                File file = new File(localFilePath);
                if (file.exists()) {
                    file.delete();
                }
            }
        });
        EMClient.getInstance().chatManager().downloadAttachment(message);
    }
}
