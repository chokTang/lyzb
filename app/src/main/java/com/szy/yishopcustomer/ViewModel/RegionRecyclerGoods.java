package com.szy.yishopcustomer.ViewModel;

public class RegionRecyclerGoods {

    String name;
    String code;

    public RegionRecyclerGoods(String name, String code) {
        super();
        this.name = name;
        this.code = code;
    }

    public RegionRecyclerGoods() {
        super();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
