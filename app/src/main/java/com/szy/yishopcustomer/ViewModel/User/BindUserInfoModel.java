package com.szy.yishopcustomer.ViewModel.User;

/**
 * @author wyx
 * @role
 * @time 2019 2019/4/3 19:35
 */

/**
 * {
 "code": 0,
 "data": null,
 "message": "登录成功！",
 "user_info": {
 "user_id": "110342",
 "user_name": "JBX177PUNR2859",
 "mobile": "17783842859",
 "email": null,
 "is_seller": "0",
 "shop_id": "0",
 "store_id": "0",
 "password": "c77f4950d17b0ea47adc85405602cdf2",
 "password_reset_token": "",
 "salt": null,
 "nickname": "买卖",
 "sex": "0",
 "birthday": "0",
 "address_now": "",
 "detail_address": "",
 "headimg": "http:\/\/thirdwx.qlogo.cn\/mmopen\/vi_32\/NgylWI0rjic3hBuXXqjJ5wib1OS5GtfrWXAV2QGHpxldMy6Dw6J2IQc2DqmjHHoqHM4oWSCsN4ibRYibJibFdpXPbHQ\/132",
 "user_money": "451.87",
 "user_money_limit": "0.00",
 "frozen_money": "0.00",
 "pay_point": "150",
 "rank_point": "-194",
 "address_id": "36490",
 "price_rank": "goods_price",
 "rank_id": "1",
 "rank_start_time": "0",
 "rank_end_time": "0",
 "mobile_validated": "1",
 "email_validated": "0",
 "reg_time": "1543910282",
 "reg_ip": "192.168.200.57",
 "last_time": "1554290782",
 "last_ip": "192.168.200.57",
 "visit_count": "1787",
 "mobile_supplier": "中国电信",
 "mobile_province": "重庆",
 "mobile_city": null,
 "reg_from": "4",
 "surplus_password": "$2y$13$DV9BgHyP3lX1wSNUUcmfhOpIc5OG8Ouu.QVq3FAhHjwN9QoMWZwGO",
 "status": "1",
 "auth_key": "bxf9RJYyaG7AQbg38G5FdT2Lg3rK-HmB",
 "type": "0",
 "is_real": "0",
 "shopping_status": "1",
 "comment_status": "1",
 "role_id": "0",
 "auth_codes": null,
 "company_name": null,
 "company_region_code": null,
 "company_address": null,
 "purpose_type": null,
 "referral_mobile": null,
 "employees": null,
 "industry": null,
 "nature": null,
 "contact_name": null,
 "department": null,
 "company_tel": null,
 "qq_key": "qq_657C8594F53D4749E143F6910D7ED4C5",
 "weibo_key": "",
 "weixin_key": "weixin_oVqv2s5sPDYmGRuOTFlC0jhwtxpQ",
 "gs_weixin_key": "",
 "user_remark": null,
 "invite_code": "YR8M",
 "parent_id": "0",
 "is_recommend": "0",
 "integral_num": "15",
 "invitation_code": "",
 "integral": "0",
 "parent_guid": null,
 "mq_syn_user": "1",
 "device_type": "android",
 "integral_expire": "0",
 "LBS_TOKEN": "8198e5be7e924c62b93722fdfcb70f59",
 "IS_LBS_SUPPLIER": 0
 }
 }
 */
public class BindUserInfoModel {
    public int code;
    public String message;
    public String data;
    public UserInfoModel user_info;



    public class UserInfoModel{
        public String user_id;
        public String user_name;
        public String mobile;
        public String email;
        public String is_seller;
        public String shop_id;
        public String store_id;
        public String password;
        public String password_reset_token;
        public String salt;
        public String nickname;
        public String sex;
        public String birthday;
        public String address_now;
        public String detail_address;
        public String headimg;
        public String user_money;
        public String user_money_limit;
        public String frozen_money;
        public String pay_point;
        public String rank_point;
        public String address_id;
        public String price_rank;
        public String rank_id;
        public String rank_start_time;
        public String rank_end_time;
        public String mobile_validated;
        public String reg_time;
        public String reg_ip;
        public String last_time;
        public String last_ip;
        public String visit_count;
        public String mobile_supplier;
        public String mobile_province;
        public String mobile_city;
        public String reg_from;
        public String surplus_password;
        public String status;
        public String auth_key;
        public String type;
        public String is_real;
        public String shopping_status;
        public String comment_status;
        public String role_id;
        public String auth_codes;
        public String LBS_TOKEN;
        public String user_guid;//用于--徐志.net那边的用户USerID
    }
}
