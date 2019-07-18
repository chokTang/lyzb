package com.szy.yishopcustomer.ResponseModel.Shop;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 宗仁 on 2016/7/28.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class RealModel implements Parcelable {
    public static final Creator<RealModel> CREATOR = new Creator<RealModel>() {
        @Override
        public RealModel createFromParcel(Parcel source) {
            return new RealModel(source);
        }

        @Override
        public RealModel[] newArray(int size) {
            return new RealModel[size];
        }
    };

    public String special_aptitude;

    public RealModel() {
    }

    protected RealModel(Parcel in) {
        this.special_aptitude = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.special_aptitude);
    }
}
