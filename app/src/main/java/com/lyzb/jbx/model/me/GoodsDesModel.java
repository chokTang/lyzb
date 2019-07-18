package com.lyzb.jbx.model.me;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by :TYK
 * Date: 2019/6/12  10:31
 * Desc: 商品描述图片 list  转换成json字符串装
 */
public class GoodsDesModel implements Serializable {
    public String decContent;
    public String decPic;

    public String getDecContent() {
        if (TextUtils.isEmpty(decContent)) return "";
        return decContent;
    }

    public void setDecContent(String decContent) {
        this.decContent = decContent;
    }

    public String getDecPic() {
        return decPic;
    }

    public void setDecPic(String decPic) {
        this.decPic = decPic;
    }
}
