package com.szy.yishopcustomer.ResponseModel.ImGroup;

/**
 * @author wyx
 * @role 群成员 model
 * @time 2018 2018/12/18 15:51
 */

public class MembersModel {

    public String id;
    public String groupId;      //群id
    public String memberId;     //成员id(禁言需要)
    public boolean banned;      //是否被禁言
    public boolean admin;       //是否为管理员
    public boolean owner;       //是否为群主
    public boolean black;       //是否黑名单
    public String userName;     //成员昵称
    public String headimg;      //成员头像
    public String userId;       //用户id

}
