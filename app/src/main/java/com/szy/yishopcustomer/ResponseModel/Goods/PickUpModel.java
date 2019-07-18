package com.szy.yishopcustomer.ResponseModel.Goods;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liwei on 2017/6/13.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class PickUpModel implements Parcelable {

    public static final Creator<PickUpModel> CREATOR = new Creator<PickUpModel>() {
        @Override
        public PickUpModel createFromParcel(Parcel in) {
            return new PickUpModel(in);
        }

        @Override
        public PickUpModel[] newArray(int size) {
            return new PickUpModel[size];
        }
    };
    public String pickup_id;//4,
    public String pickup_name;//"白塔岭自提点",
    public String region_code;//"13,03,02",
    public String pickup_address;//"西港镇北京博苑发现王国幼儿园森林家园1区",
    public String address_lng;//"119.519231",
    public String address_lat;//"39.89434",
    public String pickup_tel;//"15111111111",
    public String pickup_desc;//"测试",
    public String pickup_images;//"/shop/2/images/2017/06/05/14966322781214.png",
    public String shop_id;//2,
    public String is_show;//1,
    public String sort;//255

    public PickUpModel() {
    }

    protected PickUpModel(Parcel in) {
        pickup_id = in.readString();
        pickup_name = in.readString();
        region_code = in.readString();
        pickup_address = in.readString();
        address_lng = in.readString();
        address_lat = in.readString();
        pickup_tel = in.readString();
        pickup_desc = in.readString();
        pickup_images = in.readString();
        shop_id = in.readString();
        is_show = in.readString();
        sort = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pickup_id);
        dest.writeString(pickup_name);
        dest.writeString(region_code);
        dest.writeString(pickup_address);
        dest.writeString(address_lng);
        dest.writeString(address_lat);
        dest.writeString(pickup_tel);
        dest.writeString(pickup_desc);
        dest.writeString(pickup_images);
        dest.writeString(shop_id);
        dest.writeString(is_show);
        dest.writeString(sort);
    }
}
