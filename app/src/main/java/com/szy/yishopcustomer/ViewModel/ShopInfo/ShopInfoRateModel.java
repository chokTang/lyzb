package com.szy.yishopcustomer.ViewModel.ShopInfo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 宗仁 on 2017/1/10.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class ShopInfoRateModel implements Parcelable {
    public static final Creator<ShopInfoRateModel> CREATOR = new Creator<ShopInfoRateModel>() {
        @Override
        public ShopInfoRateModel createFromParcel(Parcel source) {
            return new ShopInfoRateModel(source);
        }

        @Override
        public ShopInfoRateModel[] newArray(int size) {
            return new ShopInfoRateModel[size];
        }
    };
    public String descriptionRate;
    public String serviceRate;
    public String expressRate;

    public ShopInfoRateModel() {
    }

    protected ShopInfoRateModel(Parcel in) {
        this.descriptionRate = in.readString();
        this.serviceRate = in.readString();
        this.expressRate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.descriptionRate);
        dest.writeString(this.serviceRate);
        dest.writeString(this.expressRate);
    }
}
