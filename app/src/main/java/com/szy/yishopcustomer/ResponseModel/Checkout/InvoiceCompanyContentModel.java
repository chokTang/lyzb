package com.szy.yishopcustomer.ResponseModel.Checkout;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 宗仁 on 2016/12/30.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class InvoiceCompanyContentModel implements Parcelable {


    //{"inv_company":"爱上打算打算","inv_content":"明细","inv_title":"单位"}
    public String inv_company;
    public String inv_taxpayers;
    public String inv_content;
    public String inv_title;
    public String inv_email;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.inv_company);
        dest.writeString(this.inv_taxpayers);
        dest.writeString(this.inv_content);
        dest.writeString(this.inv_title);
        dest.writeString(this.inv_email);
    }

    public InvoiceCompanyContentModel() {
    }

    protected InvoiceCompanyContentModel(Parcel in) {
        this.inv_company = in.readString();
        this.inv_taxpayers = in.readString();
        this.inv_content = in.readString();
        this.inv_title = in.readString();
        this.inv_email = in.readString();
    }

    public static final Creator<InvoiceCompanyContentModel> CREATOR = new Creator<InvoiceCompanyContentModel>() {
        @Override
        public InvoiceCompanyContentModel createFromParcel(Parcel source) {
            return new InvoiceCompanyContentModel(source);
        }

        @Override
        public InvoiceCompanyContentModel[] newArray(int size) {
            return new InvoiceCompanyContentModel[size];
        }
    };
}
