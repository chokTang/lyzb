package com.lyzb.jbx.model.me.customerManage;

import android.text.TextUtils;

import com.like.utilslib.date.DateStyle;
import com.like.utilslib.date.DateUtil;
import com.lyzb.jbx.util.richtext.Utils;
import com.szy.yishopcustomer.ViewModel.samecity.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;

/**
 * @author wyx
 * @role
 * @time 2019 2019/5/14 10:03
 */

public class CustomerVisitRecordModel {


    /**
     * pageNum : 1
     * pageSize : 50
     * total : 4
     * pages : 1
     * list : [{"userId":110009,"createTime":"2019-05-07 13:58:12","type":1,"title":"","content":"","source":1,"stayTime":2},{"userId":110009,"createTime":"2019-04-25 09:08:39","type":2,"content":"带的动tap","source":1,"stayTime":3},{"userId":110009,"createTime":"2019-04-25 09:08:38","type":2,"content":"带的动tap"},{"userId":110009,"createTime":"2019-04-23 17:13:37","type":1,"title":"","content":"","source":1,"stayTime":28}]
     */

    private int pageNum;
    private int pageSize;
    private int total;
    private int pages;
    private List<ListBean> list;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public class ListBean {
        /**
         * userId : 110009
         * createTime : 2019-05-07 13:58:12
         * type : 1
         * title :
         * content :
         * source : 1
         * stayTime : 2
         */

        private int userId;
        private String createTime;
        private int type;
        private String title;
        private String content;
        private int source;
        private int stayTime;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getCreateTime() {
            if (TextUtils.isEmpty(createTime)) return DateUtil.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM_SS);
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getSource() {
            return source;
        }

        /**
         * APP(IOS) 2：微信 3：小程序 4：APP(安卓)）5 其它
         */
        public String getSourceZh() {
            switch (source) {
                case 1:
                    return "APP(IOS)";
                case 2:
                    return "微信";
                case 3:
                    return "小程序";
                case 4:
                    return "APP(安卓)";
                case 5:
                    return "其他";
                default:
                    return "其他";
            }
        }

        public void setSource(int source) {
            this.source = source;
        }

        public int getStayTime() {
            return stayTime;
        }

        public void setStayTime(int stayTime) {
            this.stayTime = stayTime;
        }
    }
}
