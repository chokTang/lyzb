package com.szy.yishopcustomer.ResponseModel.AppInfo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by 宗仁 on 16/9/8.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class DataModel {
    public String is_open;
    @JSONField(name = "close_rseason")
    public String close_reason;
    @JSONField(name = "version")
    public String latest_version;
    public String app_android_update_content;
    public String update_url;
    public String aliim_appkey;//"23463685",
    public String aliim_secret_key;//"66960316c2a0f820247abd44824fff61",
    public String aliim_main_customer;//"商之翼service001",
    public String aliim_customer_logo;//"/system/config/aliim/aliim_customer_logo_0.jpg",
    public String aliim_welcome_words;//" 尊敬的客户您好",
    public String aliim_uid;//"d41d8cd98f00b204e9800998ecf8427e"
    public String aliim_pwd;//"d41d8cd98f00b204e9800998ecf8427e"

    public String aliim_icon;
    public String aliim_icon_show;

    public String image_url;
    public String user_id;
    public String LBS_TOKEN;//token信息
    public String user_guid;//用于--徐志.net那边的用户USerID
    public boolean aliim_enable;
    public String aliim_headimg;
    public String mall_phone;
    public String SYS_SITE_MODE;// "0",
    public String site_id;// null,
    public String region_code;
    public String site_name;// null,
    public String use_weixin_login;//"1",
    public String wx_login_logo;//"",
    public String login_bgimg;//"",
    public String login_logo;//"",
    public String m_user_center_bgimage;//用户中心背景
    public String default_lazyload;

    public int app_android_is_open;
    public int app_android_is_force;/*** 是否强制更新 0:强制更新  1:不强制更新 **/
    public String app_android_use_version;

    //默认用户图片
    public String default_shop_image;
    public String default_user_portrait;
    public String default_micro_shop_image;

    public int is_freebuy_enable;
    public int is_reachbuy_enable;

    public List<SiteNavListBean> site_nav_list;

    public static class SiteNavListBean {
        /**
         * nav_id : 110
         * site_id : 0
         * nav_type : 1
         * nav_name : 首页
         * nav_link : /index.html
         * is_show : 1
         * new_open : 1
         * nav_sort : 255
         * nav_position : 3
         * nav_layout : null
         * nav_page : m_site
         * nav_icon : /backend/1/images/2017/03/01/14883532856648.png
         * nav_icon_active : /backend/1/images/2017/03/01/14883532969138.png
         * active : 0
         */
        public String nav_id;
        public String site_id;
        public String nav_type;
        public String nav_name;
        public String nav_link;
        public String is_show;
        public String new_open;
        public String nav_sort;
        public String nav_position;
        public String nav_layout;
        public String nav_page;
        public String nav_icon;
        public String nav_icon_active;
        public int active;

        public String getNav_id() {
            return nav_id;
        }

        public void setNav_id(String nav_id) {
            this.nav_id = nav_id;
        }

        public String getSite_id() {
            return site_id;
        }

        public void setSite_id(String site_id) {
            this.site_id = site_id;
        }

        public String getNav_type() {
            return nav_type;
        }

        public void setNav_type(String nav_type) {
            this.nav_type = nav_type;
        }

        public String getNav_name() {
            return nav_name;
        }

        public void setNav_name(String nav_name) {
            this.nav_name = nav_name;
        }

        public String getNav_link() {
            return nav_link;
        }

        public void setNav_link(String nav_link) {
            this.nav_link = nav_link;
        }

        public String getIs_show() {
            return is_show;
        }

        public void setIs_show(String is_show) {
            this.is_show = is_show;
        }

        public String getNew_open() {
            return new_open;
        }

        public void setNew_open(String new_open) {
            this.new_open = new_open;
        }

        public String getNav_sort() {
            return nav_sort;
        }

        public void setNav_sort(String nav_sort) {
            this.nav_sort = nav_sort;
        }

        public String getNav_position() {
            return nav_position;
        }

        public void setNav_position(String nav_position) {
            this.nav_position = nav_position;
        }

        public String getNav_layout() {
            return nav_layout;
        }

        public void setNav_layout(String nav_layout) {
            this.nav_layout = nav_layout;
        }

        public String getNav_page() {
            return nav_page;
        }

        public void setNav_page(String nav_page) {
            this.nav_page = nav_page;
        }

        public String getNav_icon() {
            return nav_icon;
        }

        public void setNav_icon(String nav_icon) {
            this.nav_icon = nav_icon;
        }

        public String getNav_icon_active() {
            return nav_icon_active;
        }

        public void setNav_icon_active(String nav_icon_active) {
            this.nav_icon_active = nav_icon_active;
        }

        public int getActive() {
            return active;
        }

        public void setActive(int active) {
            this.active = active;
        }
    }
}
