package com.szy.yishopcustomer.ResponseModel.PhoneInfo;

/**
 * @author wyx
 * @role 视频信息 model
 * @time 2018 2018/9/12 9:36
 */

public class Video {

    /***
     * type=0 表示为视频文件
     * type=1 表示为视频录制
     */
    public int type=0;

    public int id = 0;
    public String path = null;
    public String name = null;
    public String resolution = null;// 分辨率
    public long size = 0;
    public long date = 0;
    public long duration = 0;
}
