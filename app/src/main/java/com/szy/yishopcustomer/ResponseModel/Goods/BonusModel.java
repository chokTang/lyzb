package com.szy.yishopcustomer.ResponseModel.Goods;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liwei on 2016/12/10.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BonusModel implements Parcelable {
    public static final Creator<BonusModel> CREATOR = new Creator<BonusModel>() {
        @Override
        public BonusModel createFromParcel(Parcel in) {
            return new BonusModel(in);
        }

        @Override
        public BonusModel[] newArray(int size) {
            return new BonusModel[size];
        }
    };
    public String bonus_id;//"122",
    public String shop_id;//"2",
    public String bonus_type;//"1",
    public String bonus_name;//"双十二红包",
    public String bonus_desc;//null,
    public String bonus_image;//null,
    public String send_type;//"0",
    public String bonus_amount;//"10.00",
    public String receive_count;//"2",
    public String bonus_number;//"30",
    public String use_range;//"0",
    public String bonus_data;//null,
    public String min_goods_amount;//"20.00",
    public String is_original_price;//"1",
    public String start_time;//"1481126400",
    public String end_time;//"1483113600",
    public String is_enable;//"1",
    public String is_delete;//"0",
    public String add_time;//"1481260515",
    public String send_id;//null,
    public String send_number;//"1",
    public String receive_number;//"1",
    public String is_receive;//1,
    public String start_time_format;//"2016.12.08",
    public String end_time_format;//"2016.12.31",
    public String bonus_amount_format;//"￥10.00"

    public BonusModel() {
    }

    protected BonusModel(Parcel in) {
        bonus_id = in.readString();
        shop_id = in.readString();
        bonus_type = in.readString();
        bonus_name = in.readString();
        bonus_desc = in.readString();
        bonus_image = in.readString();
        send_type = in.readString();
        bonus_amount = in.readString();
        receive_count = in.readString();
        bonus_number = in.readString();
        use_range = in.readString();
        bonus_data = in.readString();
        min_goods_amount = in.readString();
        is_original_price = in.readString();
        start_time = in.readString();
        end_time = in.readString();
        is_enable = in.readString();
        is_delete = in.readString();
        add_time = in.readString();
        send_id = in.readString();
        send_number = in.readString();
        receive_number = in.readString();
        start_time_format = in.readString();
        end_time_format = in.readString();
        bonus_amount_format = in.readString();
        is_receive = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bonus_id);
        dest.writeString(shop_id);
        dest.writeString(bonus_type);
        dest.writeString(bonus_name);
        dest.writeString(bonus_desc);
        dest.writeString(bonus_image);
        dest.writeString(send_type);
        dest.writeString(bonus_amount);
        dest.writeString(receive_count);
        dest.writeString(bonus_number);
        dest.writeString(use_range);
        dest.writeString(bonus_data);
        dest.writeString(min_goods_amount);
        dest.writeString(is_original_price);
        dest.writeString(start_time);
        dest.writeString(end_time);
        dest.writeString(is_enable);
        dest.writeString(is_delete);
        dest.writeString(add_time);
        dest.writeString(send_id);
        dest.writeString(send_number);
        dest.writeString(receive_number);
        dest.writeString(start_time_format);
        dest.writeString(end_time_format);
        dest.writeString(bonus_amount_format);
        dest.writeString(is_receive);
    }
}
