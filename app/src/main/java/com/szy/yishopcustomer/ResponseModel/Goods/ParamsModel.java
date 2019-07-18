package com.szy.yishopcustomer.ResponseModel.Goods;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhifeng on 2017/3/29.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ParamsModel implements Parcelable {

    public static final Creator<ParamsModel> CREATOR = new Creator<ParamsModel>() {
        @Override
        public ParamsModel createFromParcel(Parcel source) {
            return new ParamsModel(source);
        }

        @Override
        public ParamsModel[] newArray(int size) {
            return new ParamsModel[size];
        }
    };

    public String help_bargain_list;//帮砍list的key
    public List<GrouponLogListModel> groupon_log_list;

    public String help_bargain_num;
    public BargainInfo bargain_info;

    /***
     * 砍价商品 基本信息
     */
    public class BargainInfo {

        public String act_id;
        public String act_type;

        public String start_time;
        public String end_time;
        public String is_recommend;

        public String is_finish;//0 未开始  1 进行中  2 已结束

        public String goods_image;

        public String goods_name;
        public String goods_price;

        public String part_num;//参与人数
        public String original_price;//原始价格
        public String base_price;//低价

    }

    public ParamsModel() {
    }

    protected ParamsModel(Parcel in) {
        this.groupon_log_list = new ArrayList<GrouponLogListModel>();
        in.readList(this.groupon_log_list, GrouponLogListModel.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.groupon_log_list);
    }
}
