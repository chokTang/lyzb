package com.szy.yishopcustomer.ResponseModel.AppIndex;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 宗仁 on 16/6/2.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ArticleItemModel implements Parcelable {
    public static final Creator<ArticleItemModel> CREATOR = new Creator<ArticleItemModel>() {
        @Override
        public ArticleItemModel createFromParcel(Parcel source) {
            return new ArticleItemModel(source);
        }

        @Override
        public ArticleItemModel[] newArray(int size) {
            return new ArticleItemModel[size];
        }
    };
    public String article_id;//"article_id;//54,
    public String title;//"title;//"关于我们",
    public String cat_id;//"cat_id;//83,
    public String content;//"content":
    public String keywords;//"",
    public String jurisdiction;//null,
    public String article_thumb;//"",
    public String add_time;//1465812204,
    public String is_comment;//1,
    public String click_number;//21,
    public String is_show;//1,
    public String user_id;//1,
    public String status;//1,
    public String link;//"",
    public String source;//null,
    public String summary;//"翼商城火热招商中-摘要",
    public String goods_ids;//"",
    public String shop_id;//0,
    public String sort;//"1"

    public ArticleItemModel() {
    }

    protected ArticleItemModel(Parcel in) {
        this.article_id = in.readString();
        this.title = in.readString();
        this.cat_id = in.readString();
        this.content = in.readString();
        this.keywords = in.readString();
        this.jurisdiction = in.readString();
        this.article_thumb = in.readString();
        this.add_time = in.readString();
        this.is_comment = in.readString();
        this.click_number = in.readString();
        this.is_show = in.readString();
        this.user_id = in.readString();
        this.status = in.readString();
        this.link = in.readString();
        this.source = in.readString();
        this.summary = in.readString();
        this.goods_ids = in.readString();
        this.shop_id = in.readString();
        this.sort = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.article_id);
        dest.writeString(this.title);
        dest.writeString(this.cat_id);
        dest.writeString(this.content);
        dest.writeString(this.keywords);
        dest.writeString(this.jurisdiction);
        dest.writeString(this.article_thumb);
        dest.writeString(this.add_time);
        dest.writeString(this.is_comment);
        dest.writeString(this.click_number);
        dest.writeString(this.is_show);
        dest.writeString(this.user_id);
        dest.writeString(this.status);
        dest.writeString(this.link);
        dest.writeString(this.source);
        dest.writeString(this.summary);
        dest.writeString(this.goods_ids);
        dest.writeString(this.shop_id);
        dest.writeString(this.sort);
    }
}
