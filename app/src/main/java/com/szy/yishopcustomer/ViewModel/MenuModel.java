package com.szy.yishopcustomer.ViewModel;

/**
 * Created by zongren on 16/5/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class MenuModel {
    private String mName;
    private String mIcon;

    public MenuModel(String name, String icon) {
        mName = name;
        mIcon = icon;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
