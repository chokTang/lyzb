package com.szy.yishopcustomer.ViewModel.Attribute;

import android.os.Parcel;
import android.os.Parcelable;

import com.szy.yishopcustomer.Constant.Macro;

/**
 * Created by 宗仁 on 16/8/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ResultModel implements Parcelable {
    public static final Creator<ResultModel> CREATOR = new Creator<ResultModel>() {
        @Override
        public ResultModel createFromParcel(Parcel source) {
            return new ResultModel(source);
        }

        @Override
        public ResultModel[] newArray(int size) {
            return new ResultModel[size];
        }
    };
    public String resultType;
    public String goodsNumber;
    public String skuId;
    public String specValue;

    //商品的库存
    public String goodStock;

    public ResultModel() {
        resultType = Macro.RESULT_TYPE_ADD_TO_CART;
        goodsNumber = "1";
        skuId = "0";
        specValue = "";
        goodStock = "0";
    }

    protected ResultModel(Parcel in) {
        this.resultType = in.readString();
        this.goodsNumber = in.readString();
        this.skuId = in.readString();
        this.specValue = in.readString();
        this.goodStock = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.resultType);
        dest.writeString(this.goodsNumber);
        dest.writeString(this.skuId);
        dest.writeString(this.specValue);
        dest.writeString(this.goodStock);
    }
}
