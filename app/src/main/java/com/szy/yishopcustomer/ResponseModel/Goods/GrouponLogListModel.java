package com.szy.yishopcustomer.ResponseModel.Goods;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liuzhifeng on 2017/3/29.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GrouponLogListModel implements Parcelable {
    public static final Creator<GrouponLogListModel> CREATOR = new Creator<GrouponLogListModel>() {
        @Override
        public GrouponLogListModel createFromParcel(Parcel source) {
            return new GrouponLogListModel(source);
        }

        @Override
        public GrouponLogListModel[] newArray(int size) {
            return new GrouponLogListModel[size];
        }
    };
    public String log_id;//public String 61",
    public String goods_id;//public String 39021",
    public String act_id;//public String 106",
    public String user_id;//public String 4",
    public String user_type;//public String 0",
    public String group_sn;//public String 20170328660063",
    public String order_sn;//public String 20170328406190",
    public String add_time;//public String 1490684328",
    public String status;//public String 0",
    public String user_name;//public String 卖家",
    public String headimg;//public String http://wx.qlogo
    public int start_time;//public String 1489468868",
    public int end_time;// 1490770728,
    public String ext_info;//public String a:3:{s:9:\"fight_num\";s:1:\"3\";s:10:\"fight_time\";
    public String order_id;//public String 3306",
    public String diff_num;// 2

    public GrouponLogListModel() {
    }

    protected GrouponLogListModel(Parcel in) {
        this.log_id = in.readString();
        this.goods_id = in.readString();
        this.act_id = in.readString();
        this.user_id = in.readString();
        this.user_type = in.readString();
        this.group_sn = in.readString();
        this.order_sn = in.readString();
        this.add_time = in.readString();
        this.status = in.readString();
        this.user_name = in.readString();
        this.headimg = in.readString();
        this.start_time = in.readInt();
        this.end_time = in.readInt();
        this.ext_info = in.readString();
        this.order_id = in.readString();
        this.diff_num = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.log_id);
        dest.writeString(this.goods_id);
        dest.writeString(this.act_id);
        dest.writeString(this.user_id);
        dest.writeString(this.user_type);
        dest.writeString(this.group_sn);
        dest.writeString(this.order_sn);
        dest.writeString(this.add_time);
        dest.writeString(this.status);
        dest.writeString(this.user_name);
        dest.writeString(this.headimg);
        dest.writeInt(this.start_time);
        dest.writeInt(this.end_time);
        dest.writeString(this.ext_info);
        dest.writeString(this.order_id);
        dest.writeString(this.diff_num);
    }
}
