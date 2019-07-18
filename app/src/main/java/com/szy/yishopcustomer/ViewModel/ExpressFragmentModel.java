package com.szy.yishopcustomer.ViewModel;

/**
 * Created by liuzhifeng on 2016/8/5 0005.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司
 */
public class ExpressFragmentModel {

    String name;
    boolean select;

    public ExpressFragmentModel() {
        super();
    }

    public ExpressFragmentModel(boolean select, String name) {
        this.select = select;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
