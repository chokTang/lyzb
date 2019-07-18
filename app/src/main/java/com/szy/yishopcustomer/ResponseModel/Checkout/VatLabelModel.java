package com.szy.yishopcustomer.ResponseModel.Checkout;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 宗仁 on 16/7/22.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class VatLabelModel implements Parcelable {
    public static final Creator<VatLabelModel> CREATOR = new Creator<VatLabelModel>() {
        @Override
        public VatLabelModel createFromParcel(Parcel source) {
            return new VatLabelModel(source);
        }

        @Override
        public VatLabelModel[] newArray(int size) {
            return new VatLabelModel[size];
        }
    };
    public String name;//": "inv_address",
    public String label;//"TextLabelUtil": "注册地址",
    public String value;//"value": null,
    public boolean required;//"required": true

    public VatLabelModel() {
    }

    protected VatLabelModel(Parcel in) {
        this.name = in.readString();
        this.label = in.readString();
        this.value = in.readString();
        this.required = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.label);
        dest.writeString(this.value);
        dest.writeByte(this.required ? (byte) 1 : (byte) 0);
    }
}
