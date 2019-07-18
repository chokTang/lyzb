package com.szy.yishopcustomer.ResponseModel.SelectAddress;

import com.amap.api.services.core.LatLonPoint;

/**
 * Created by 李威 on 17/03/10.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class PoiListModel {
    //提示名称
    public String title;
    //getDistrict提示区域
    public String content;
    public String regionCode;

    //经纬度
    public LatLonPoint latLonPoint;
    //详细地址
    public String address;
    //区域编码
    public String adcode;

}
