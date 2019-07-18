package com.szy.yishopcustomer.ResponseModel.Checkout;

import android.os.Parcel;
import android.os.Parcelable;

import com.szy.yishopcustomer.ViewModel.EmailViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 宗仁 on 2016/6/12.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class InvoiceItemModel implements Parcelable {
    public static final Creator<InvoiceItemModel> CREATOR = new Creator<InvoiceItemModel>() {
        @Override
        public InvoiceItemModel createFromParcel(Parcel source) {
            return new InvoiceItemModel(source);
        }

        @Override
        public InvoiceItemModel[] newArray(int size) {
            return new InvoiceItemModel[size];
        }
    };
    public int invoiceType;
    public String name;
    public String selected;//"selected"/,""
    public String disabled;//"disabled",""
//    public InvoiceCompanyContentModel contents;
    public String contents;
    public EmailViewModel email;
    public List<InvoiceTitleModel> invoiceTitle;
    public InvoiceCompanyModel company;
    public List<VatLabelModel> vatLabelList;
    public List<ContentItemModel> content_list;
    public ArrayList<InvoiceGoodsModel> goods_list;//GoodsModel,nulls

    public InvoiceItemModel() {
    }

    protected InvoiceItemModel(Parcel in) {
        this.invoiceType = in.readInt();
        this.name = in.readString();
        this.selected = in.readString();
        this.disabled = in.readString();
//        this.contents = in.readParcelable(InvoiceCompanyContentModel.class.getClassLoader());
        this.contents = in.readString();
        this.invoiceTitle = in.createTypedArrayList(InvoiceTitleModel.CREATOR);
        this.email = in.readParcelable(EmailViewModel.class.getClassLoader());
        this.company = in.readParcelable(InvoiceCompanyModel.class.getClassLoader());
        this.vatLabelList = in.createTypedArrayList(VatLabelModel.CREATOR);
        this.content_list = in.createTypedArrayList(ContentItemModel.CREATOR);
        this.goods_list = in.createTypedArrayList(InvoiceGoodsModel.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.invoiceType);
        dest.writeString(this.name);
        dest.writeString(this.selected);
        dest.writeString(this.disabled);
//        dest.writeParcelable(this.contents,flags);
        dest.writeString(this.contents);
        dest.writeTypedList(this.invoiceTitle);
        dest.writeParcelable(this.company, flags);
        dest.writeParcelable(this.email, flags);
        dest.writeTypedList(this.vatLabelList);
        dest.writeTypedList(this.content_list);
        dest.writeTypedList(this.goods_list);
    }




}
