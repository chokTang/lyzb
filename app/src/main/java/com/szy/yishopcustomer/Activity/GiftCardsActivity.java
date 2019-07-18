package com.szy.yishopcustomer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.szy.common.Fragment.CommonFragment;
import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Fragment.GiftCardsFragment;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.GiftCard.GiftCardModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.yolanda.nohttp.RequestMethod;

/*
*  我的提货列表
* */

public class GiftCardsActivity extends YSCBaseActivity {

    GiftCardsFragment cardsFragment;
    @Override
    protected CommonFragment createFragment() {
        return cardsFragment = new GiftCardsFragment();
    }

    private String redirect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mEnableBaseMenu = true;
        mCustemMenuStyle = 0;
        mBaseMenuId = R.menu.activity_menu_recomm_stroe;
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean ree = super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.action_recomm).setTitle("提货券提货");
        return ree;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_recomm:
                //提货券提货
                getRedirect();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getRedirect(){
        request("goods");
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_INDEX:
                HttpResultManager.resolve(response, GiftCardModel.class, new HttpResultManager.HttpResultCallBack<GiftCardModel>() {
                    @Override
                    public void onSuccess(GiftCardModel back) {
                        if(back.data.list.size()>0){
                            openGiftCardPickUpListActivity();
                        }else if(!TextUtils.isEmpty(back.redirect)) {
                            request(back.redirect);
                        }else {
                                startActivity(new Intent(GiftCardsActivity.this,GiftCardPickUpActivity.class));
                        }
                    }
                },true);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    void openGiftCardPickUpListActivity(){
        startActivity(new Intent(this,GiftCardPickUpListActivity.class));
    }

    public void request(String redirect) {
        this.redirect = redirect;
        CommonRequest request = new CommonRequest(Api.API_USER_GIFT_CARD+redirect, HttpWhat
                .HTTP_INDEX.getValue(), RequestMethod.GET);
        addRequest(request);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        cardsFragment.mOrderListAdapter.data.clear();
        cardsFragment.refreshOrderList();
    }

}
