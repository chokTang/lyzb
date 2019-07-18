package com.szy.yishopcustomer.ResponseModel.Checkout;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 宗仁 on 16/7/22.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class InvoiceResultModel implements Parcelable {
    public static final Creator<InvoiceResultModel> CREATOR = new Creator<InvoiceResultModel>() {
        @Override
        public InvoiceResultModel createFromParcel(Parcel source) {
            return new InvoiceResultModel(source);
        }

        @Override
        public InvoiceResultModel[] newArray(int size) {
            return new InvoiceResultModel[size];
        }
    };
    public int inv_type;//1
    public int inv_content;//:2
    public String inv_name;//p:普通发票
    public String inv_title;//:个人
    public String inv_company;//:wetwerwe
    public String inv_taxpayers;//:asdasd
    public String inv_email;//:asdasd
    public String inv_address;//:asdasd
    public String inv_tel;//:asdasdasd
    public String inv_account;//:asdasd
    public String inv_bank;//:asdasdsd

    public InvoiceResultModel() {
        this.inv_type = 0;
        this.inv_name = "";
        this.inv_title = "";
        this.inv_company = "";
        this.inv_content = 0;
        this.inv_taxpayers = "";
        this.inv_address = "";
        this.inv_email = "";
        this.inv_tel = "";
        this.inv_account = "";
        this.inv_bank = "";
    }

    protected InvoiceResultModel(Parcel in) {
        this.inv_type = in.readInt();
        this.inv_name = in.readString();
        this.inv_title = in.readString();
        this.inv_company = in.readString();
        this.inv_content = in.readInt();
        this.inv_taxpayers = in.readString();
        this.inv_address = in.readString();
        this.inv_email = in.readString();
        this.inv_tel = in.readString();
        this.inv_account = in.readString();
        this.inv_bank = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.inv_type);
        dest.writeString(this.inv_name);
        dest.writeString(this.inv_title);
        dest.writeString(this.inv_company);
        dest.writeInt(this.inv_content);
        dest.writeString(this.inv_taxpayers);
        dest.writeString(this.inv_address);
        dest.writeString(this.inv_email);
        dest.writeString(this.inv_tel);
        dest.writeString(this.inv_account);
        dest.writeString(this.inv_bank);
    }
}
