package com.szy.yishopcustomer.ResponseModel.Goods;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liwei on 2016/12/10.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class RankPriceModel implements Parcelable {

    public static final Creator<RankPriceModel> CREATOR = new Creator<RankPriceModel>() {
        @Override
        public RankPriceModel createFromParcel(Parcel in) {
            return new RankPriceModel(in);
        }

        @Override
        public RankPriceModel[] newArray(int size) {
            return new RankPriceModel[size];
        }
    };
    public String rank_name;//"测试特殊会员",
    public String rank_price;//178
    public String rank_price_format;//"￥177.6"

    public RankPriceModel() {

    }

    protected RankPriceModel(Parcel in) {
        rank_name = in.readString();
        rank_price = in.readString();
        rank_price_format = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rank_name);
        dest.writeString(rank_price);
        dest.writeString(rank_price_format);
    }
}
