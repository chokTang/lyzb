package com.szy.yishopcustomer.ResponseModel.ProfileModel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liuzhifeng on 2016/8/16.
 */
public class InfoModel {
    public UserInfoModel user_info;
    public RealInfoBean real_info;

    public static class RealInfoBean implements Parcelable {

        /**
         * real_id : 19
         * user_id : 83
         * real_name : 患得患失
         * id_code : 152123199408171213
         * card_pic1 : http://68yun.oss-cn-beijing.aliyuncs.com/images/746/user/83/images/2017/08/18/15030462355576.jpg
         * card_pic2 : http://68yun.oss-cn-beijing.aliyuncs.com/images/746/user/83/images/2017/08/18/15030462530107.jpg
         * card_pic3 : http://68yun.oss-cn-beijing.aliyuncs.com/images/746/user/83/images/2017/08/18/15030462615370.jpg
         * address_now : null
         * address_info : null
         * status : 0
         * add_time : 1502923140
         * reason : null
         */

        public int real_id;
        public int user_id;
        public String real_name;
        public String id_code;
        public String card_pic1;
        public String card_pic2;
        public String card_pic3;
        public String address_now;
        public String address_info;
        public int status = -1;
        public int add_time;
        public String reason;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.real_id);
            dest.writeInt(this.user_id);
            dest.writeString(this.real_name);
            dest.writeString(this.id_code);
            dest.writeString(this.card_pic1);
            dest.writeString(this.card_pic2);
            dest.writeString(this.card_pic3);
            dest.writeString(this.address_now);
            dest.writeString(this.address_info);
            dest.writeInt(this.status);
            dest.writeInt(this.add_time);
            dest.writeString(this.reason);
        }

        public RealInfoBean() {
        }

        protected RealInfoBean(Parcel in) {
            this.real_id = in.readInt();
            this.user_id = in.readInt();
            this.real_name = in.readString();
            this.id_code = in.readString();
            this.card_pic1 = in.readString();
            this.card_pic2 = in.readString();
            this.card_pic3 = in.readString();
            this.address_now = in.readString();
            this.address_info = in.readString();
            this.status = in.readInt();
            this.add_time = in.readInt();
            this.reason = in.readString();
        }

        public static final Creator<RealInfoBean> CREATOR = new Creator<RealInfoBean>() {
            @Override
            public RealInfoBean createFromParcel(Parcel source) {
                return new RealInfoBean(source);
            }

            @Override
            public RealInfoBean[] newArray(int size) {
                return new RealInfoBean[size];
            }
        };
    }
}
