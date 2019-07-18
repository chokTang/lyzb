package com.szy.yishopcustomer.ResponseModel.AppIndex;

import java.util.List;

/**
 * Created by liwei on 17/10/31.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class VideoModel {

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {
        public String video_introduction;
        public List<String> video_list;
    }
}
