package com.szy.yishopcustomer.ResponseModel.AddressList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 宗仁 on 16/7/23.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class AddressItemModel implements Parcelable {
    public static final Creator<AddressItemModel> CREATOR = new Creator<AddressItemModel>() {
        @Override
        public AddressItemModel createFromParcel(Parcel source) {
            return new AddressItemModel(source);
        }

        @Override
        public AddressItemModel[] newArray(int size) {
            return new AddressItemModel[size];
        }
    };
    public boolean editing;
    public String address_id;//"70",
    public String address_name;//"",
    public String user_id;//"4",
    public String consignee;//"1111",
    public String region_code;//"12,01,02",
    public String address_detail;//"111111",
    public String mobile;//"13323425678",
    public String tel;//null,
    public String email;//null,
    public String zipcode;//null,
    public String is_real;//"1",
    public String real_name;//"张先生",
    public String id_code;//"234567776655456778",
    public int is_default;//"0",
    public String region_name;//"天津市 天津市 河东区",
    public int selected;//0,
    public String mobile_format;//"133****5678",
    public String id_code_format;//"2345**********6778"
    public String region_code_format;//"天津市 天津市 河东区",
    public String address_lng;
    public String address_lat;

    public AddressItemModel() {
    }

    protected AddressItemModel(Parcel in) {
        this.editing = in.readByte() != 0;
        this.address_id = in.readString();
        this.address_name = in.readString();
        this.user_id = in.readString();
        this.consignee = in.readString();
        this.region_code = in.readString();
        this.address_detail = in.readString();
        this.mobile = in.readString();
        this.tel = in.readString();
        this.email = in.readString();
        this.zipcode = in.readString();
        this.is_real = in.readString();
        this.real_name = in.readString();
        this.id_code = in.readString();
        this.is_default = in.readInt();
        this.region_name = in.readString();
        this.selected = in.readInt();
        this.mobile_format = in.readString();
        this.id_code_format = in.readString();
        this.region_code_format = in.readString();
        this.address_lng = in.readString();
        this.address_lat = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.editing ? (byte) 1 : (byte) 0);
        dest.writeString(this.address_id);
        dest.writeString(this.address_name);
        dest.writeString(this.user_id);
        dest.writeString(this.consignee);
        dest.writeString(this.region_code);
        dest.writeString(this.address_detail);
        dest.writeString(this.mobile);
        dest.writeString(this.tel);
        dest.writeString(this.email);
        dest.writeString(this.zipcode);
        dest.writeString(this.is_real);
        dest.writeString(this.real_name);
        dest.writeString(this.id_code);
        dest.writeInt(this.is_default);
        dest.writeString(this.region_name);
        dest.writeInt(this.selected);
        dest.writeString(this.mobile_format);
        dest.writeString(this.id_code_format);
        dest.writeString(this.region_code_format);
        dest.writeString(this.address_lng);
        dest.writeString(this.address_lat);
    }
}
