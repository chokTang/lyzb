package com.lyzb.jbx.model.params;

public class AddGoodsBody {
    private String userId;
    private String type;
    private String goods_name;
    private String goods_price;
    private String distributor_id;
    private String mobile_desc_images;
    private String goods_imgs;
    private String mobile_desc;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getDistributor_id() {
        return distributor_id;
    }

    public void setDistributor_id(String distributor_id) {
        this.distributor_id = distributor_id;
    }

    public String getMobile_desc_images() {
        return mobile_desc_images;
    }

    public void setMobile_desc_images(String mobile_desc_images) {
        this.mobile_desc_images = mobile_desc_images;
    }

    public String getGoods_imgs() {
        return goods_imgs;
    }

    public void setGoods_imgs(String goods_imgs) {
        this.goods_imgs = goods_imgs;
    }

    public String getMobile_desc() {
        return mobile_desc;
    }

    public void setMobile_desc(String mobile_desc) {
        this.mobile_desc = mobile_desc;
    }
}
