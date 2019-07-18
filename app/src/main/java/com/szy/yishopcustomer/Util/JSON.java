package com.szy.yishopcustomer.Util;

import com.google.gson.reflect.TypeToken;
import com.szy.yishopcustomer.ViewModel.samecity.Comment;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by 宗仁 on 2017/1/13.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class JSON extends com.szy.common.Util.JSON {
    public static <T> T parseObject(String text, Class<T> clazz) {
        return new JSON().realParseObject(text, clazz);
    }

    public <T> T realParseObject(String text, Class<T> clazz) {
        return super.realParseObject(text, clazz, null);
    }
}
