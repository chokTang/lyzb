package com.szy.yishopcustomer.ResponseModel.Checkout;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 宗仁 on 2016/7/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BestTimeModel implements Parcelable {
    public static final Creator<BestTimeModel> CREATOR = new Creator<BestTimeModel>() {
        @Override
        public BestTimeModel createFromParcel(Parcel source) {
            return new BestTimeModel(source);
        }

        @Override
        public BestTimeModel[] newArray(int size) {
            return new BestTimeModel[size];
        }
    };
    public String name;//"07-18",
    public String week;//"今天",
    public String time1;//"1",
    public String time2;//"1",
    public String time3;//"1"

    public BestTimeModel() {
    }

    protected BestTimeModel(Parcel in) {
        this.name = in.readString();
        this.week = in.readString();
        this.time1 = in.readString();
        this.time2 = in.readString();
        this.time3 = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.week);
        dest.writeString(this.time1);
        dest.writeString(this.time2);
        dest.writeString(this.time3);
    }
}
