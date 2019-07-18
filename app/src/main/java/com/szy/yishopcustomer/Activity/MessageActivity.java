package com.szy.yishopcustomer.Activity;

import android.os.Bundle;

import com.szy.yishopcustomer.Fragment.MessageFragment;
import com.szy.yishopcustomer.View.MenuPopwindow;

/**
 * Created by zongren on 16/7/9.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class MessageActivity extends YSCBaseActivity implements MessageFragment.UnreadMessageInterface {
    private MessageFragment mFragmentOne;
    @Override
    protected MessageFragment createFragment() {
        mFragmentOne = new MessageFragment();
        mFragmentOne.setFinishUserName(this);
        return mFragmentOne;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requiredLanding = true;
        mEnableBaseMenu = true;
        mCustemMenuStyle = MenuPopwindow.MENU_STYLE_MESSAGE;
//        mBaseMenuId = R.menu.activity_message;
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setUnreadMessageCount(int unread_message_count) {
        if(unread_message_count>0){
            this.setTitle("消息盒子("+ unread_message_count +")");
        }else{
            this.setTitle("消息盒子");
        }
    }
}
