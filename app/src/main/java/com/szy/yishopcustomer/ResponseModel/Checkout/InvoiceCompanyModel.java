package com.szy.yishopcustomer.ResponseModel.Checkout;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 宗仁 on 16/7/22.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class InvoiceCompanyModel implements Parcelable {
    public static final Creator<InvoiceCompanyModel> CREATOR = new Creator<InvoiceCompanyModel>() {
        @Override
        public InvoiceCompanyModel createFromParcel(Parcel source) {
            return new InvoiceCompanyModel(source);
        }

        @Override
        public InvoiceCompanyModel[] newArray(int size) {
            return new InvoiceCompanyModel[size];
        }
    };
    public String name;
    public String taxpayer;

    public InvoiceCompanyModel() {
    }

    public InvoiceCompanyModel(String name,String taxpayer) {
        this.name = name;
        this.taxpayer = taxpayer;
    }

    protected InvoiceCompanyModel(Parcel in) {
        this.name = in.readString();
        this.taxpayer = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.taxpayer);
    }
}
