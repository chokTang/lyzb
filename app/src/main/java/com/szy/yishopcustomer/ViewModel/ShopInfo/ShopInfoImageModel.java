package com.szy.yishopcustomer.ViewModel.ShopInfo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 宗仁 on 2017/1/10.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class ShopInfoImageModel implements Parcelable {
    public static final Creator<ShopInfoImageModel> CREATOR = new Creator<ShopInfoImageModel>() {
        @Override
        public ShopInfoImageModel createFromParcel(Parcel source) {
            return new ShopInfoImageModel(source);
        }

        @Override
        public ShopInfoImageModel[] newArray(int size) {
            return new ShopInfoImageModel[size];
        }
    };
    public int type;

    public ShopInfoImageModel() {
        this.type = 0;
    }

    protected ShopInfoImageModel(Parcel in) {
        this.type = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
    }
}
