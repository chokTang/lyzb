package com.szy.yishopcustomer.ViewModel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by :TYK
 * Date: 2018/12/19  20:19
 * Desc:
 */
public class EmailViewModel implements Parcelable {

    public EmailViewModel(String emial) {
        this.emial = emial;
    }

    public String emial;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.emial);
    }

    public EmailViewModel() {
    }

    protected EmailViewModel(Parcel in) {
        this.emial = in.readString();
    }

    public static final Creator<EmailViewModel> CREATOR = new Creator<EmailViewModel>() {
        @Override
        public EmailViewModel createFromParcel(Parcel source) {
            return new EmailViewModel(source);
        }

        @Override
        public EmailViewModel[] newArray(int size) {
            return new EmailViewModel[size];
        }
    };
}
