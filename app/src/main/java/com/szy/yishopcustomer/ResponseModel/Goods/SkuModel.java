package com.szy.yishopcustomer.ResponseModel.Goods;

import android.os.Parcel;
import android.os.Parcelable;

import com.szy.yishopcustomer.ResponseModel.GoodsMix;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by 宗仁 on 2016/7/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class SkuModel implements Parcelable {
    public String sku_id;//"421",
    public String goods_id;//"24",
    public String sku_name;//"Daphne/达芙妮2016夏季新款露趾坡跟松糕厚底女凉鞋1016303045 白色 37 白色 38",
    public String sku_image;//"/shop/2/2016/06/08/14653733580015.jpg",
    public String goods_price;//"180.00",
    public String market_price;//"190.00",
    public String goods_number;//"10",
    public List<String> spec_ids;
    public String is_enable;//1,
    public String shop_id;//"2",
    public String goods_status;//"1",
    public String goods_audit;//"1",
    public String act_id;//"0",
    public String market_price_format;//"￥190.00",
    public String spec_attr_value;// "白色 38",
    public List<List<String>> sku_images;
    public List<GiftModel> gift_list;
    public List<BargainUsers> bargain_users;

    public int limitActivity;
    public String buy_start_time;
    public String buy_end_time;
    public int limitStatus;
    public long limitTime;
    public String limitString;

    public String original_price;// "180.00",
    public String purchase_num;// 0,
    public ActivityModel activity;// 活动商品-activity.act_type="8" 砍价商品
    public OrderActivityModel order_activity;
    public String original_price_format;// "￥180.00",
    public String goods_price_format;// "￥180.00"

    public String goods_dk_price;//抵后价

    public ActivityNavi activity_navi;//活动商品标识


    public String description;
    public BuyenableModel buy_enable;
    public String original_number;
    public String goods_image;
    public int goods_moq;
    public String unit_name;
    public String member_price_format;//会员等级价格
    public String member_price_message;//您享受吃瓜群众价：￥8.00",;
    public String show_price;//0
    public String is_supply;//0
    public String goods_bxprice_format;//宝箱价

    public String max_integral_num;//0

    public List<GoodsMix> goods_mix;

    public ExchangeGoods exchange_goods;
    //1为阶梯价格，0为零售
    public String sales_model = "0";

    public TreeMap<String, String> step_price;

    public SkuModel() {
    }


    public List<BargainUsers> getBargain_users() {
        return bargain_users;
    }

    public void setBargain_users(List<BargainUsers> bargain_users) {
        this.bargain_users = bargain_users;
    }

    public class ExchangeGoods {

        /**
         * record_id : 35
         * goods_id : 258
         * shop_id : 2
         * sku_id : 787
         * begin_time : 1498629600
         * end_time : 1501221600
         * points : 1
         * goods_number : 99
         * exchanged_number : 1
         * add_time : 1498630354
         * sort : 255
         */

        public String record_id;
        public String goods_id;
        public String shop_id;
        public String sku_id;
        public String begin_time;
        public String end_time;
        public String points;
        public String goods_number;
        public String exchanged_number;
        public String add_time;
        public String sort;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sku_id);
        dest.writeString(this.goods_id);
        dest.writeString(this.sku_name);
        dest.writeString(this.sku_image);
        dest.writeString(this.goods_price);
        dest.writeString(this.market_price);
        dest.writeString(this.goods_number);
        dest.writeStringList(this.spec_ids);
        dest.writeString(this.is_enable);
        dest.writeString(this.shop_id);
        dest.writeString(this.goods_status);
        dest.writeString(this.goods_audit);
        dest.writeString(this.act_id);
        dest.writeString(this.market_price_format);
        dest.writeString(this.spec_attr_value);
        dest.writeList(this.sku_images);
        dest.writeList(this.gift_list);
        dest.writeString(this.original_price);
        dest.writeString(this.purchase_num);

        dest.writeString(this.original_price_format);
        dest.writeString(this.goods_price_format);
        dest.writeString(this.description);
        dest.writeParcelable(this.buy_enable, flags);
        dest.writeString(this.original_number);
        dest.writeString(this.goods_image);
        dest.writeInt(this.goods_moq);
        dest.writeString(this.sales_model);
        dest.writeSerializable(this.step_price);
    }

    protected SkuModel(Parcel in) {
        this.sku_id = in.readString();
        this.goods_id = in.readString();
        this.sku_name = in.readString();
        this.sku_image = in.readString();
        this.goods_price = in.readString();
        this.market_price = in.readString();
        this.goods_number = in.readString();
        this.spec_ids = in.createStringArrayList();
        this.is_enable = in.readString();
        this.shop_id = in.readString();
        this.goods_status = in.readString();
        this.goods_audit = in.readString();
        this.act_id = in.readString();
        this.market_price_format = in.readString();
        this.spec_attr_value = in.readString();
        this.sku_images = new ArrayList<List<String>>();
        in.readList(this.sku_images, List.class.getClassLoader());
        this.gift_list = new ArrayList<GiftModel>();
        in.readList(this.gift_list, GiftModel.class.getClassLoader());
        this.original_price = in.readString();
        this.purchase_num = in.readString();
        this.activity = in.readParcelable(ActivityModel.class.getClassLoader());
        this.original_price_format = in.readString();
        this.goods_price_format = in.readString();
        this.description = in.readString();
        this.buy_enable = in.readParcelable(BuyenableModel.class.getClassLoader());
        this.original_number = in.readString();
        this.goods_image = in.readString();
        this.goods_moq = in.readInt();
        this.sales_model = in.readString();
        this.step_price = (TreeMap<String, String>) in.readSerializable();
    }

    public static final Creator<SkuModel> CREATOR = new Creator<SkuModel>() {
        @Override
        public SkuModel createFromParcel(Parcel source) {
            return new SkuModel(source);
        }

        @Override
        public SkuModel[] newArray(int size) {
            return new SkuModel[size];
        }
    };
}
