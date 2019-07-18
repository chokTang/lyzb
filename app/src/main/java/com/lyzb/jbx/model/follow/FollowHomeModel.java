package com.lyzb.jbx.model.follow;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页--头部关注信息列表
 */
public class FollowHomeModel {

    @SerializedName("toCount")
    private int followMeNumber;
    @SerializedName("fromCount")
    private int myFollowNumber;
    private List<FollowSingleUserModel> list;
    private int favourCount;//是否有新动态，0否，大于0是

    public int getFavourCount() {
        return favourCount;
    }

    public void setFavourCount(int favourCount) {
        this.favourCount = favourCount;
    }

    public int getFollowMeNumber() {
        return followMeNumber;
    }

    public void setFollowMeNumber(int followMeNumber) {
        this.followMeNumber = followMeNumber;
    }

    public int getMyFollowNumber() {
        return myFollowNumber;
    }

    public void setMyFollowNumber(int myFollowNumber) {
        this.myFollowNumber = myFollowNumber;
    }

    public List<FollowSingleUserModel> getList() {
        if (list == null)
            return new ArrayList<>();
        return list;
    }

    public void setList(List<FollowSingleUserModel> list) {
        this.list = list;
    }
}
