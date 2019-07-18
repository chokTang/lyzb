package com.szy.yishopcustomer.ResponseModel.Attribute;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 宗仁 on 16/8/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class AttributeModel implements Parcelable {
    public String spec_id;
    public String spec_image;//"/shop/2/2016/06/08/14653734065508.jpg",
    public String attr_vid;//"45",
    public String attr_value;//"蓝色",
    public String attr_desc;//null

    public boolean selected = false;
    public boolean actived = true;

    //用来判断是否是属性中的最后一组
    public boolean isLast = false;
    //记录商品选择数量和库存，方便展示和计算
    public int last_goodNum = 0;
    public int last_stock = 0;
    //记录商品的单位
    public String last_company = "件";
    public String spec_ids;
    //------------


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.spec_id);
        dest.writeString(this.spec_image);
        dest.writeString(this.attr_vid);
        dest.writeString(this.attr_value);
        dest.writeString(this.attr_desc);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
        dest.writeByte(this.actived ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isLast ? (byte) 1 : (byte) 0);
        dest.writeInt(this.last_goodNum);
        dest.writeInt(this.last_stock);
        dest.writeString(this.last_company);
        dest.writeString(this.spec_ids);
    }

    public AttributeModel() {
    }

    protected AttributeModel(Parcel in) {
        this.spec_id = in.readString();
        this.spec_image = in.readString();
        this.attr_vid = in.readString();
        this.attr_value = in.readString();
        this.attr_desc = in.readString();
        this.selected = in.readByte() != 0;
        this.actived = in.readByte() != 0;
        this.isLast = in.readByte() != 0;
        this.last_goodNum = in.readInt();
        this.last_stock = in.readInt();
        this.last_company = in.readString();
        this.spec_ids = in.readString();
    }

    public static final Creator<AttributeModel> CREATOR = new Creator<AttributeModel>() {
        @Override
        public AttributeModel createFromParcel(Parcel source) {
            return new AttributeModel(source);
        }

        @Override
        public AttributeModel[] newArray(int size) {
            return new AttributeModel[size];
        }
    };
}
