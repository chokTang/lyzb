package com.szy.yishopcustomer.ViewModel;

/**
 * Created by liuzhifeng on 2016/9/2.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class SelectedModel {
    public String value;
    public String name;

    public SelectedModel() {
        super();
    }

    public SelectedModel(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
