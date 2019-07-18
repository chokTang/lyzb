package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Fragment.ShareFragment;

/**
 * Created by 宗仁 on 16/8/17.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShareActivity extends YSCBaseActivity {
    public static final String SHARE_DATA = "share_data";
    public static final String SHARE_TYPE = "share_type";
    public static final String SHARE_SOURCE = "share_source";
    /*** 分享范围 0:全部平台  1:微信+微信朋友圈  2：微信好友**/
    public static final String SHARE_SCOPE = "share_scope";

    public static final int TYPE_GOODS = 1;//商品详情
    public static final int TYPE_SHOP = 2;//店铺首页第二种样式下
    public static final int TYPE_GROUP_ON_LIST = 3;//拼团列表
    public static final int TYPE_USER_GROUP_ON = 4;//用户中心拼团订单
    public static final int TYPE_SUPPORT_SHOP = 5;//店铺首页第二种样式下

    public static final int TYPE_SOURCE = 101;//同城生活 分享

    @Override
    public ShareFragment createFragment() {
        return new ShareFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLayoutId = R.layout.activity_common_no_toolbar;
        super.onCreate(savedInstanceState);
    }
}
