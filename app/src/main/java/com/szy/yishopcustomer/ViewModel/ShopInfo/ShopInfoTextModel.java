package com.szy.yishopcustomer.ViewModel.ShopInfo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 宗仁 on 2017/1/10.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class ShopInfoTextModel implements Parcelable {
    public static final Creator<ShopInfoTextModel> CREATOR = new Creator<ShopInfoTextModel>() {
        @Override
        public ShopInfoTextModel createFromParcel(Parcel source) {
            return new ShopInfoTextModel(source);
        }

        @Override
        public ShopInfoTextModel[] newArray(int size) {
            return new ShopInfoTextModel[size];
        }
    };
    public String name;
    public String value;
    public int icon;
    public int type;

    public ShopInfoTextModel() {
        this.type = -1;
    }

    protected ShopInfoTextModel(Parcel in) {
        this.name = in.readString();
        this.value = in.readString();
        this.icon = in.readInt();
        this.type = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.value);
        dest.writeInt(this.icon);
        dest.writeInt(this.type);
    }
}
