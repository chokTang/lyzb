package com.szy.yishopcustomer.ResponseModel.Checkout;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 宗仁 on 2016/7/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ContentItemModel implements Parcelable {
    public static final Creator<ContentItemModel> CREATOR = new Creator<ContentItemModel>() {
        @Override
        public ContentItemModel createFromParcel(Parcel source) {
            return new ContentItemModel(source);
        }

        @Override
        public ContentItemModel[] newArray(int size) {
            return new ContentItemModel[size];
        }
    };
    public int contentPosition;
    public String name; //"明细",
    public String checked; //"checked",
    public String value; //0

    public ContentItemModel() {
    }

    protected ContentItemModel(Parcel in) {
        this.contentPosition = in.readInt();
        this.name = in.readString();
        this.checked = in.readString();
        this.value = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.contentPosition);
        dest.writeString(this.name);
        dest.writeString(this.checked);
        dest.writeString(this.value);
    }
}
