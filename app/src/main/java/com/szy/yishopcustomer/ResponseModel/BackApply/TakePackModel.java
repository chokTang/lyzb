package com.szy.yishopcustomer.ResponseModel.BackApply;

import com.google.gson.annotations.SerializedName;

/**
 * Created by :TYK
 * Date: 2018/12/22  15:11
 * Desc:
 */
public class TakePackModel {


    /**
     * 0 : 无包装
     * 10 : 包装完整
     * 20 : 无包破损
     */

    @SerializedName("0")
    private String _$0;
    @SerializedName("10")
    private String _$10;
    @SerializedName("20")
    private String _$20;

    public String get_$0() {
        return _$0;
    }

    public void set_$0(String _$0) {
        this._$0 = _$0;
    }

    public String get_$10() {
        return _$10;
    }

    public void set_$10(String _$10) {
        this._$10 = _$10;
    }

    public String get_$20() {
        return _$20;
    }

    public void set_$20(String _$20) {
        this._$20 = _$20;
    }
}
