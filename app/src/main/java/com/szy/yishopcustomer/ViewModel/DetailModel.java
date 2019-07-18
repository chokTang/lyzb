package com.szy.yishopcustomer.ViewModel;

/**
 * Created by liuzhifeng on 2016/7/19 0019.
 */
public class DetailModel {
    String mName;
    String mMoney;
    String mType;
    String mTime;

    public DetailModel() {
    }

    public DetailModel(String name, String money, String type, String time) {
        mName = name;
        mMoney = money;
        mType = type;
        mTime = time;
    }

    public String getMoney() {
        return mMoney;
    }

    public void setMoney(String money) {
        mMoney = money;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}
