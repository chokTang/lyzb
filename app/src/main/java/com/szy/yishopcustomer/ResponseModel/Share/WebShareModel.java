package com.szy.yishopcustomer.ResponseModel.Share;

/**
 * @author wyx
 * @role
 * @time 2018 2018/9/30 17:33
 */

public class WebShareModel {

    public int code;
    public ModelData data;

    public class ModelData {
        public String seo_topic_link;
        public String seo_topic_title;
        public String seo_topic_discription;
        public String seo_topic_image;
        public String seo_topic_keywords;
    }

    public String message;

}
