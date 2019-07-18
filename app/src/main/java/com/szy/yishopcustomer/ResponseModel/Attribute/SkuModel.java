package com.szy.yishopcustomer.ResponseModel.Attribute;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 宗仁 on 16/8/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class SkuModel implements Parcelable {
    public String spec_ids;
    public String sku_name;
    public String sku_image;
    //商品库存

    public String sku_id;
    public String spec_vids;
    public String goods_price;
    public String goods_number;
    public String spec_names;
    public String act_price;
    public String ext_info;
    public String price_mode;
    public String discount_show;

    //存储商品添加的得数量
    public int QTY = 0;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.spec_ids);
        dest.writeString(this.sku_name);
        dest.writeString(this.sku_image);
        dest.writeString(this.sku_id);
        dest.writeString(this.spec_vids);
        dest.writeString(this.goods_price);
        dest.writeString(this.goods_number);
        dest.writeString(this.spec_names);
        dest.writeString(this.act_price);
        dest.writeString(this.ext_info);
        dest.writeString(this.price_mode);
        dest.writeString(this.discount_show);
        dest.writeInt(this.QTY);
    }

    public SkuModel() {
    }

    protected SkuModel(Parcel in) {
        this.spec_ids = in.readString();
        this.sku_name = in.readString();
        this.sku_image = in.readString();
        this.sku_id = in.readString();
        this.spec_vids = in.readString();
        this.goods_price = in.readString();
        this.goods_number = in.readString();
        this.spec_names = in.readString();
        this.act_price = in.readString();
        this.ext_info = in.readString();
        this.price_mode = in.readString();
        this.discount_show = in.readString();
        this.QTY = in.readInt();
    }

    public static final Creator<SkuModel> CREATOR = new Creator<SkuModel>() {
        @Override
        public SkuModel createFromParcel(Parcel source) {
            return new SkuModel(source);
        }

        @Override
        public SkuModel[] newArray(int size) {
            return new SkuModel[size];
        }
    };
}
