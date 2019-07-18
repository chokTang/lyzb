package com.szy.yishopcustomer.ResponseModel.SameCity.Address;

/**
 * @author wyx
 * @role
 * @time 2018 2018/6/13 10:36
 */

public class AddressList {


    /**
     * addressDetail : 渝北人和
     * addressId : 123
     * addressLat : 101.236556
     * addressLng : 10.235236
     * addressName : 重庆市渝北区
     * consignee : 小张
     * distance
     * email : 1@qq.com
     * mobile : 18723781635
     * regionCode : 654
     * tel : 023-55823379
     * userId : 321
     * zipcode : 400065
     */

    public String addressDetail;
    public int addressId;
    public String addressLat;
    public String addressLng;
    public String addressName;
    public String consignee;
    public int distance;
    public String email;
    public String mobile;
    public String regionCode;
    public String tel;
    public int userId;
    public String zipcode;
    public boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
