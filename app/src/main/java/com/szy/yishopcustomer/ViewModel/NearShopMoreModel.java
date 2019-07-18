package com.szy.yishopcustomer.ViewModel;

import com.szy.yishopcustomer.ViewModel.samecity.ShopDetailFloatingBean;

import java.util.List;

/**
 * Created by :TYK
 * Date: 2018/12/21  15:16
 * Desc:
 */
public class NearShopMoreModel {

    public int type = 1;//1 代表顶部广告模块 2代表附近商店模块
    List<HomeShopAndProductBean.AdvertBean> adver;
    List<HomeShopAndProductBean.NearbyBean> nearby;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<HomeShopAndProductBean.AdvertBean> getAdver() {
        return adver;
    }

    public void setAdver(List<HomeShopAndProductBean.AdvertBean> adver) {
        this.adver = adver;
    }

    public List<HomeShopAndProductBean.NearbyBean> getNearby() {
        return nearby;
    }

    public void setNearby(List<HomeShopAndProductBean.NearbyBean> nearby) {
        this.nearby = nearby;
    }

    @Override
    public String toString() {
        return "NearShopMoreModel{" +
                "type=" + type +
                ", adver=" + adver +
                ", nearby=" + nearby +
                '}';
    }
}
