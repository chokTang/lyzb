package com.szy.yishopcustomer.Other;

import android.content.Context;

import com.szy.common.Other.BasePatternModel;
import com.szy.common.Other.CommonEvent;
import com.szy.yishopcustomer.Constant.EventWhat;

import org.greenrobot.eventbus.EventBus;

import java.util.regex.Matcher;

/**
 * Created by 宗仁 on 2016/12/15.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class EventPatternModel extends BasePatternModel {
    public EventWhat event;

    public EventPatternModel(String pattern, EventWhat event) {
        super(pattern);
        this.event = event;
    }

    @Override
    public boolean handlePattern(Context context, Matcher matcher) {
        EventBus.getDefault().post(new CommonEvent(event.getValue()));
        return true;
    }
}
