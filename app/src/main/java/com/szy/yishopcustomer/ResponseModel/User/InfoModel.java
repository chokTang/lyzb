package com.szy.yishopcustomer.ResponseModel.User;

/**
 * Created by 宗仁 on 16/7/27.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class InfoModel {
    public UserModel user_info;
    public int is_surplus;
    public int person_info_style;//0,
    public int real_info_style;//1,
    public SafeModel safe_info;//
    public String pay_point_format;//"0分",
    public String birthday_format;//"1901-12-14",
    public String rank_next_distance;//"~",
    public String user_rank;//"钻石会员",
    public String rank_next;//"~",
    public String rank_image;//"/user/rank/2016/06/07/14653037052976.png"
    public String money_all_format;
    public String user_money_format;

    public String pay_point;
}
