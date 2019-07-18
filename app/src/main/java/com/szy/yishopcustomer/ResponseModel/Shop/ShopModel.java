package com.szy.yishopcustomer.ResponseModel.Shop;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 宗仁 on 2016/7/28.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShopModel implements Parcelable {
    public static final Creator<ShopModel> CREATOR = new Creator<ShopModel>() {
        @Override
        public ShopModel createFromParcel(Parcel source) {
            return new ShopModel(source);
        }

        @Override
        public ShopModel[] newArray(int size) {
            return new ShopModel[size];
        }
    };
    public String shop_id;//"shop_id": "4",
    public String user_id;//        "user_id": "5",
    public String kf_user_id;//        "user_id": "5",
    public String site_id;//"site_id": "0",
    public String shop_name;//"shop_name": "鞋柜自营旗舰店",
    public String shop_image;//"shop_image": "/shop/4/images/2016/06/08/14653536621976.jpg",
    public String shop_logo;//"shop_logo": "/shop/4/logos/2016/06/08/14653536623401.jpg",
    public String shop_poster;//"shop_poster": "/shop/4/posters/2016/06/20/14664123332974.jpg",
    public String shop_sign;//"shop_sign": "/shop/4/signs/2016/06/20/14664082824018.jpg",
    public String shop_type;//"shop_type": "0",
    public String is_supply;//"is_supply": "0",
    public String cat_id;//"cat_id": "6",
    public String credit;//"credit": "0",
    public String desc_score;//"desc_score": "2.00",
    public String service_score;//"service_score": "2.00",
    public String send_score;//"send_score": "2.00",
    public String logistics_score;//"logistics_score": "0.00",
    public String region_code;//"region_code": "13,03,02",
    public String address;//"address": "太阳城",
    public String add_time;//"add_time": "1465301055",
    public String duration;//"duration": "0",
    public String unit;//"unit": "0",
    public String open_time;//"open_time": "1465301055",
    public String end_time;//"end_time": "0",
    public String system_fee;//"system_fee": "0.00",
    public String insure_fee;//"insure_fee": "0.00",
    public String goods_status;//"goods_status": "0",
    public String shop_status;//"shop_status": "1",
    public String close_info;//"close_info": null,
    public String shop_sort;//"shop_sort": "1",
    public String shop_audit;//"shop_audit": "1",
    public String fail_info;//"fail_info": null,
    public String simply_introduce;//"simply_introduce": "专营鞋柜，点缀生活，让生活更美好",
    public String detail_introduce;//"detail_introduce": "",
    public String service_tel;//"service_tel": null,
    public String service_hours;//"service_hours": null,
    public String shop_sign_m;//"shop_sign_m": null,
    public String region_name;//"region_name": "河北省 秦皇岛市 海港区"
    public String duration_time;
    public int shop_header_style;
    public String url;

    public ShopModel() {
    }

    protected ShopModel(Parcel in) {
        this.shop_id = in.readString();
        this.user_id = in.readString();
        this.site_id = in.readString();
        this.shop_name = in.readString();
        this.shop_image = in.readString();
        this.shop_logo = in.readString();
        this.shop_poster = in.readString();
        this.shop_sign = in.readString();
        this.shop_type = in.readString();
        this.is_supply = in.readString();
        this.cat_id = in.readString();
        this.credit = in.readString();
        this.desc_score = in.readString();
        this.service_score = in.readString();
        this.send_score = in.readString();
        this.logistics_score = in.readString();
        this.region_code = in.readString();
        this.address = in.readString();
        this.add_time = in.readString();
        this.duration = in.readString();
        this.unit = in.readString();
        this.open_time = in.readString();
        this.end_time = in.readString();
        this.system_fee = in.readString();
        this.insure_fee = in.readString();
        this.goods_status = in.readString();
        this.shop_status = in.readString();
        this.close_info = in.readString();
        this.shop_sort = in.readString();
        this.shop_audit = in.readString();
        this.fail_info = in.readString();
        this.simply_introduce = in.readString();
        this.detail_introduce = in.readString();
        this.service_tel = in.readString();
        this.service_hours = in.readString();
        this.shop_sign_m = in.readString();
        this.region_name = in.readString();
        this.duration_time = in.readString();
        this.url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.shop_id);
        dest.writeString(this.user_id);
        dest.writeString(this.site_id);
        dest.writeString(this.shop_name);
        dest.writeString(this.shop_image);
        dest.writeString(this.shop_logo);
        dest.writeString(this.shop_poster);
        dest.writeString(this.shop_sign);
        dest.writeString(this.shop_type);
        dest.writeString(this.is_supply);
        dest.writeString(this.cat_id);
        dest.writeString(this.credit);
        dest.writeString(this.desc_score);
        dest.writeString(this.service_score);
        dest.writeString(this.send_score);
        dest.writeString(this.logistics_score);
        dest.writeString(this.region_code);
        dest.writeString(this.address);
        dest.writeString(this.add_time);
        dest.writeString(this.duration);
        dest.writeString(this.unit);
        dest.writeString(this.open_time);
        dest.writeString(this.end_time);
        dest.writeString(this.system_fee);
        dest.writeString(this.insure_fee);
        dest.writeString(this.goods_status);
        dest.writeString(this.shop_status);
        dest.writeString(this.close_info);
        dest.writeString(this.shop_sort);
        dest.writeString(this.shop_audit);
        dest.writeString(this.fail_info);
        dest.writeString(this.simply_introduce);
        dest.writeString(this.detail_introduce);
        dest.writeString(this.service_tel);
        dest.writeString(this.service_hours);
        dest.writeString(this.shop_sign_m);
        dest.writeString(this.region_name);
        dest.writeString(this.duration_time);
        dest.writeString(this.url);
    }
}
