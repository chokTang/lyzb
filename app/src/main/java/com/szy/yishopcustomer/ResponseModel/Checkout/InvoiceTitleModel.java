package com.szy.yishopcustomer.ResponseModel.Checkout;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 宗仁 on 16/7/22.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class InvoiceTitleModel implements Parcelable {
    public static final Creator<InvoiceTitleModel> CREATOR = new Creator<InvoiceTitleModel>() {
        @Override
        public InvoiceTitleModel createFromParcel(Parcel source) {
            return new InvoiceTitleModel(source);
        }

        @Override
        public InvoiceTitleModel[] newArray(int size) {
            return new InvoiceTitleModel[size];
        }
    };
    public String name;
    public String selected;

    public InvoiceTitleModel(String selected, String name) {
        this.selected = selected;
        this.name = name;
    }

    public InvoiceTitleModel() {
    }

    protected InvoiceTitleModel(Parcel in) {
        this.name = in.readString();
        this.selected = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.selected);
    }
}
