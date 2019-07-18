package com.szy.yishopcustomer.ViewModel;

/**
 * Created by liuzhifeng on 2017/3/23.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class GroupOnLIstTabModel {
    public String name;
    public boolean selected;

    public boolean singleLine = false;

    public GroupOnLIstTabModel isSingleLine(boolean singleLine){
        this.singleLine = singleLine;

        return this;
    }
}
