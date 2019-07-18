package com.szy.yishopcustomer.ResponseModel.Checkout;

import com.szy.yishopcustomer.ResponseModel.Goods.PickUpModel;

import java.util.List;

/**
 * Created by 宗仁 on 2016/6/3.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShopInfoModel {
    public String shop_id;//"8",
    public String user_id;//"1",
    public String site_id;//"2",
    public String shop_name;//"美妆护肤旗舰店",
    public String shop_image;//"/shop/8/images/2016/06/20/14664058633715.png",
    public String shop_logo;//"/shop/8/logos/2016/06/20/14664058631685.jpg",
    public String shop_poster;//"/shop/8/posters/2016/06/20/14664058637184.png",
    public String shop_sign;//"/shop/8/signs/2016/06/20/14664107024703.png",
    public String shop_type;//"0",
    public String is_supply;//"0",
    public String cat_id;//"7",
    public String credit;//"4",
    public String desc_score;//"4.60",
    public String service_score;//"4.20",
    public String send_score;//"4.20",
    public String logistics_score;//"4.20",
    public String region_code;//"13,03,02",
    public String address;//"秦皇半岛2区物业楼3楼",
    public String add_time;//"1465729936",
    public String duration;//"0",
    public String unit;//"0",
    public String open_time;//"1465729936",
    public String end_time;//"0",
    public String system_fee;//"0.00",
    public String insure_fee;//"0.00",
    public String goods_status;//"0",
    public String shop_status;//"1",
    public String close_info;//null,
    public String shop_sort;//"255",
    public String shop_audit;//"1",
    public String fail_info;//null,
    public String simply_introduce;//"美妆狂欢，美出公主范",
    public String detail_introduce;//"",
    public String service_tel;//"400-078-5268",
    public String service_hours;//"AM 9:00 - PM 21:00",
    public CustomerModel customer;
    public List<PickUpModel> pickup_list;
}
