package com.szy.yishopcustomer.ViewModel;

import android.widget.ImageView;

/**
 * Created by :TYK
 * Date: 2019/1/15  11:56
 * Desc:
 */
public class MsgObj {

    public String url;
    public ImageView imageView;

    public MsgObj(String url, ImageView imageView) {
        this.url = url;
        this.imageView = imageView;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
