package com.lyzb.jbx.model.account;

/**
 * 首页-推荐下-功能配置item
 */
public class FunctionModel {

    private int id;//@mock=$order(1,2,3,4,5)
    private String img;
    private String name;//@mock=$order('推广工具','集宝箱商家','共商學院','集宝箱商城',"批发商城"
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
