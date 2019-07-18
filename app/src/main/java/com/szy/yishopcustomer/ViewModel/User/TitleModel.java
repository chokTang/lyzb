package com.szy.yishopcustomer.ViewModel.User;

/**
 * Created by 宗仁 on 16/7/27.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class TitleModel {
    //是否是标题，标题字体加重，然后占据一整行
    public boolean isTitle = false;

    public boolean editable;
    public String title;
    public String subTitle;
    public int imageSource;
    public int extraInfo;

    public TitleModel(String title, boolean editable, String subTitle, int imageSource, int extraInfo) {
        this.title = title;
        this.editable = editable;
        this.subTitle = subTitle;
        this.imageSource = imageSource;
        this.extraInfo = extraInfo;
    }

    public TitleModel(int imageSource, int extraInfo, String title, boolean editable) {
        this.imageSource = imageSource;
        this.extraInfo = extraInfo;
        this.title = title;
        this.editable = editable;
    }

    public TitleModel(boolean editable, String title, String subTitle, int extraInfo) {
        this.editable = editable;
        this.title = title;
        this.subTitle = subTitle;
        this.extraInfo = extraInfo;
    }
}
