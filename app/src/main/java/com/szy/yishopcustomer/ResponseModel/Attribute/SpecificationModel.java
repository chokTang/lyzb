package com.szy.yishopcustomer.ResponseModel.Attribute;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by 宗仁 on 16/8/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class SpecificationModel implements Parcelable {

    public String cat_id;//"228",
    public String attr_id;//13,
    public String attr_name;//"颜色",
    public String is_default;//"1",
    public List<AttributeModel> attr_values;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cat_id);
        dest.writeString(this.attr_id);
        dest.writeString(this.attr_name);
        dest.writeString(this.is_default);
        dest.writeTypedList(this.attr_values);
    }

    public SpecificationModel() {
    }

    protected SpecificationModel(Parcel in) {
        this.cat_id = in.readString();
        this.attr_id = in.readString();
        this.attr_name = in.readString();
        this.is_default = in.readString();
        this.attr_values = in.createTypedArrayList(AttributeModel.CREATOR);
    }

    public static final Creator<SpecificationModel> CREATOR = new Creator<SpecificationModel>() {
        @Override
        public SpecificationModel createFromParcel(Parcel source) {
            return new SpecificationModel(source);
        }

        @Override
        public SpecificationModel[] newArray(int size) {
            return new SpecificationModel[size];
        }
    };
}
