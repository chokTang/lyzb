package com.szy.yishopcustomer.ViewModel.Filter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liuzhifeng on 2016/8/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class FilterChildModel implements Parcelable {
    public static final String FILTER_TYPE_SELLER = "FILTER_TYPE_SELLER";
    public static final String FILTER_TYPE_SHIP = "FILTER_TYPE_SHIP";
    public static final String FILTER_TYPE_STOCK = "FILTER_TYPE_STOCK";
    public static final String FILTER_TYPE_BRAND = "FILTER_TYPE_BRAND";
    public static final String FILTER_TYPE_ATTRIBUTE = "FILTER_TYPE_ATTRIBUTE";
    public static final String FILTER_TYPE_PRICE = "FILTER_TYPE_PRICE";
    public static final String FILTER_TYPE_PRICE_SEEKBAR = "FILTER_TYPE_PRICE_SEEKBAR";

    public static final Creator<FilterChildModel> CREATOR = new Creator<FilterChildModel>() {
        @Override
        public FilterChildModel createFromParcel(Parcel source) {
            return new FilterChildModel(source);
        }

        @Override
        public FilterChildModel[] newArray(int size) {
            return new FilterChildModel[size];
        }
    };
    public String title;
    public String type;
    public String value;
    public String minimumValue;
    public String maximumValue;
    public String rangeStart;
    public String rangeEnd;
    public boolean selected;

    public FilterChildModel() {
    }

    protected FilterChildModel(Parcel in) {
        this.title = in.readString();
        this.type = in.readString();
        this.value = in.readString();
        this.minimumValue = in.readString();
        this.maximumValue = in.readString();
        this.rangeStart = in.readString();
        this.rangeEnd = in.readString();
        this.selected = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.type);
        dest.writeString(this.value);
        dest.writeString(this.minimumValue);
        dest.writeString(this.maximumValue);
        dest.writeString(this.rangeStart);
        dest.writeString(this.rangeEnd);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
    }
}
