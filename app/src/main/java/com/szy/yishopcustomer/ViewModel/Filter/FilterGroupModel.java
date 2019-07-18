package com.szy.yishopcustomer.ViewModel.Filter;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 宗仁 on 2016/8/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class FilterGroupModel implements Parcelable {
    public static final String FILTER_TYPE_NORMAL = "FILTER_TYPE_NORMAL";
    public static final String FILTER_TYPE_PRICE = "FILTER_TYPE_PRICE";
    public static final String FILTER_TYPE_BRAND = "FILTER_TYPE_BRAND";
    public static final String FILTER_TYPE_ATTRIBUTE = "FILTER_TYPE_ATTRIBUTE";
    public static final String FILTER_TYPE_DIVIDER = "FILTER_TYPE_DIVIDER";
    public static final String FILTER_TYPE_LINE = "FILTER_TYPE_LINE";
    public static final String FILTER_TYPE_PRICE_SEEKBAR = "FILTER_TYPE_PRICE_SEEKBAR";

    public static final Creator<FilterGroupModel> CREATOR = new Creator<FilterGroupModel>() {
        @Override
        public FilterGroupModel createFromParcel(Parcel source) {
            return new FilterGroupModel(source);
        }

        @Override
        public FilterGroupModel[] newArray(int size) {
            return new FilterGroupModel[size];
        }
    };
    public List<FilterChildModel> children;
    public String title;
    public boolean expanded;
    public boolean expandEnabled;
    public String type;

    public FilterGroupModel() {
        children = new ArrayList<>();
    }

    protected FilterGroupModel(Parcel in) {
        this.children = in.createTypedArrayList(FilterChildModel.CREATOR);
        this.title = in.readString();
        this.expanded = in.readByte() != 0;
        this.expandEnabled = in.readByte() != 0;
        this.type = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.children);
        dest.writeString(this.title);
        dest.writeByte(this.expanded ? (byte) 1 : (byte) 0);
        dest.writeByte(this.expandEnabled ? (byte) 1 : (byte) 0);
        dest.writeString(this.type);
    }
}
