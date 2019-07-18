package com.szy.yishopcustomer.core.voice;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.szy.yishopcustomer.core.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Smart on 2017/12/26.
 */

public class CustomVolumeView extends LinearLayout {

    private int level = 1;
    private int levelNum = 8;
    private int minHeight = 10;
    private int minWidth = 10;

    private List<View> levelView;
    public CustomVolumeView(Context context)
    {
        this(context, null);
    }

    public CustomVolumeView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public CustomVolumeView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        levelView = new ArrayList<>();

        float defaultHeightMultiple = 1;
        for(int i = 0 ; i < levelNum ; i ++) {
            View rootView = LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_volume, null);
            LinearLayout.LayoutParams layoutParams = new LayoutParams(minWidth, (int) (minHeight * (defaultHeightMultiple)));
            defaultHeightMultiple = defaultHeightMultiple+0.5f;
            if(i > 0) {
                layoutParams.setMargins(8,0,0,0);
            }

            rootView.setLayoutParams(layoutParams);
            addView(rootView,i);
            levelView.add(rootView);

            setGravity(Gravity.CENTER| Gravity.BOTTOM);
        }

        updateLevel();
    }

    private void updateLevel(){
        for(int i = 1 ; i <= levelView.size() ; i ++) {
            if(i > level) {
                levelView.get(i-1).setVisibility(INVISIBLE);
            } else {
                levelView.get(i-1).setVisibility(VISIBLE);
            }
        }
    }

    //1到100
    public void setProgress(int progress) {
        if(progress > 100) {
            progress = 100;
        }
        if(progress < 0) {
            progress = 0;
        }

        level = (int) (progress / 100f * levelNum);

        if(level <= 0) {
            level = 1;
        }
        updateLevel();
    }
}
