package com.szy.yishopcustomer.ViewModel;

/**
 * Created by zongren on 16/7/18.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class MessageModel {
    public String title;
    public String content;
    public String date;
    public MESSAGE_TYPE messageType;

    public MessageModel(String title, String content, String date, MESSAGE_TYPE messageType) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.messageType = messageType;
    }

    public enum MESSAGE_TYPE {
        TEXT_MESSAGE,
        GOODS_MESSAGE,
        ARTICLE_MESSAGE,
        BRAND_MESSAGE,
        CATEGORY_MESSAGE,
        LINK_MESSAGE,
        SHOP_MESSAGE,
    }
}
