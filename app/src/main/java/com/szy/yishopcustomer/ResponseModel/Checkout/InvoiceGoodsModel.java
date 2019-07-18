package com.szy.yishopcustomer.ResponseModel.Checkout;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 宗仁 on 16/7/22.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class InvoiceGoodsModel implements Parcelable {
    public static final Creator<InvoiceGoodsModel> CREATOR = new Creator<InvoiceGoodsModel>() {
        @Override
        public InvoiceGoodsModel createFromParcel(Parcel source) {
            return new InvoiceGoodsModel(source);
        }

        @Override
        public InvoiceGoodsModel[] newArray(int size) {
            return new InvoiceGoodsModel[size];
        }
    };
    public String sku_id;//": "126",
    public String goods_id;//goods_id": "56",
    public String sku_name;//sku_name": "Midea/美的 T3-L385C家用烘焙烤箱38升多功能电烤箱蛋糕L正品特价",
    public String sku_image;//"sku_image": "/shop/1/2016/06/12/14657315883938.jpg",
    public String shop_id;//"shop_id": 1,
    public String shop_name;//"shop_name": "美的旗舰店"

    public InvoiceGoodsModel() {
    }

    protected InvoiceGoodsModel(Parcel in) {
        this.sku_id = in.readString();
        this.goods_id = in.readString();
        this.sku_name = in.readString();
        this.sku_image = in.readString();
        this.shop_id = in.readString();
        this.shop_name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sku_id);
        dest.writeString(this.goods_id);
        dest.writeString(this.sku_name);
        dest.writeString(this.sku_image);
        dest.writeString(this.shop_id);
        dest.writeString(this.shop_name);
    }
}
