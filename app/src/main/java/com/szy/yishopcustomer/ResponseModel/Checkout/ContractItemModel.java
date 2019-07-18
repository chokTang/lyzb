package com.szy.yishopcustomer.ResponseModel.Checkout;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 宗仁 on 16/8/5.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ContractItemModel implements Parcelable {
    public static final Creator<ContractItemModel> CREATOR = new Creator<ContractItemModel>() {
        @Override
        public ContractItemModel createFromParcel(Parcel source) {
            return new ContractItemModel(source);
        }

        @Override
        public ContractItemModel[] newArray(int size) {
            return new ContractItemModel[size];
        }
    };
    public String shop_id;//"7",
    public String contract_id;//"3",
    public String contract_name;//"破损补寄",
    public String contract_fee;//"1200.00",
    public String contract_image;//"/contract/2016/06/07/14653028611314.jpg",
    public String contract_type;//"1",
    public String contract_desc;//"卖家就该商品签收状态作出承诺，自商品签收之日起至卖家承诺保障时间内，如发现商品在运输途中出现破损，买家可申请破损部分商品补寄。",
    public String is_open;//"1",
    public String contract_sort;//"3"

    public ContractItemModel() {
    }

    protected ContractItemModel(Parcel in) {
        this.shop_id = in.readString();
        this.contract_id = in.readString();
        this.contract_name = in.readString();
        this.contract_fee = in.readString();
        this.contract_image = in.readString();
        this.contract_type = in.readString();
        this.contract_desc = in.readString();
        this.is_open = in.readString();
        this.contract_sort = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.shop_id);
        dest.writeString(this.contract_id);
        dest.writeString(this.contract_name);
        dest.writeString(this.contract_fee);
        dest.writeString(this.contract_image);
        dest.writeString(this.contract_type);
        dest.writeString(this.contract_desc);
        dest.writeString(this.is_open);
        dest.writeString(this.contract_sort);
    }
}
