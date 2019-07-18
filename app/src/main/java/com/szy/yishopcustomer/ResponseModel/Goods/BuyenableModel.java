package com.szy.yishopcustomer.ResponseModel.Goods;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liwei on 2016/4/17.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BuyenableModel implements Parcelable {
    public static final Creator<BuyenableModel> CREATOR = new Creator<BuyenableModel>() {
        @Override
        public BuyenableModel createFromParcel(Parcel source) {
            return new BuyenableModel(source);
        }

        @Override
        public BuyenableModel[] newArray(int size) {
            return new BuyenableModel[size];
        }
    };
    public String code;
    public String button_content;

    public BuyenableModel() {
    }

    protected BuyenableModel(Parcel in) {
        this.code = in.readString();
        this.button_content = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.button_content);
    }
}
