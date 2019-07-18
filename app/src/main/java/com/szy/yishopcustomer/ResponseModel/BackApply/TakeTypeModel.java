package com.szy.yishopcustomer.ResponseModel.BackApply;

import com.google.gson.annotations.SerializedName;

/**
 * Created by :TYK
 * Date: 2018/12/22  15:12
 * Desc:
 */
public class TakeTypeModel {


    /**
     * 4 : 上门取件
     * 40 : 客户发货
     */

    @SerializedName("4")
    private String _$4;
    @SerializedName("40")
    private String _$40;

    public String get_$4() {
        return _$4;
    }

    public void set_$4(String _$4) {
        this._$4 = _$4;
    }

    public String get_$40() {
        return _$40;
    }

    public void set_$40(String _$40) {
        this._$40 = _$40;
    }
}
