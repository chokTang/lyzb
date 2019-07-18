package com.like.longshaolib.base.inter;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by longshao on 2017/10/12.
 */

public interface IFragment {

    void onInitView(@Nullable Bundle savedInstanceState);

    void onInitData(@Nullable Bundle savedInstanceState);

    Object getResId();

    boolean isSwipeBack();

    boolean isDelayedData();
}
