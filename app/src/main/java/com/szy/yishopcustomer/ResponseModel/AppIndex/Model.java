package com.szy.yishopcustomer.ResponseModel.AppIndex;

/**
 * Created by 宗仁 on 16/5/28.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class Model {
    public int code;
    public String message;
    public DataModel data;

    @Override
    public String toString() {
        return "Model{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
