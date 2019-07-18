package com.szy.yishopcustomer.ResponseModel.AppIndex;

/**
 * Created by 宗仁 on 16/8/29.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class PageModel {
    public int cur_page;
    public int page_size;
    public int record_count;//"record_count": 272,
    public int page_count;//"page_count": 14,

    public PageModel() {
        cur_page = 0;
        page_count = -1;
    }
}
