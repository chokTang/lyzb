package com.szy.yishopcustomer.ViewModel;

/**
 * Created by liuzhifeng on 2016/8/25.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class SiteEntity {

    private String mTitle;
    private String mContent;

    public SiteEntity(String pTitle, String pContent) {
        mTitle = pTitle;
        mContent = pContent;
    }

    public String getContent() {
        return mContent;
    }

    public String getTitle() {
        return mTitle;
    }

}
