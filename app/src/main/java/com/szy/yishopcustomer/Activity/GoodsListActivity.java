package com.szy.yishopcustomer.Activity;

import android.os.Bundle;
import android.widget.TextView;

import com.szy.common.Adapter.TextWatcherAdapter;
import com.szy.common.Fragment.CommonFragment;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Fragment.GoodsListFragment;
import com.szy.yishopcustomer.Interface.CartAnimationMaker;
import com.szy.yishopcustomer.Interface.CoordinatorLayoutObservable;
import com.szy.yishopcustomer.Interface.ScrollObserver;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;

/**
 * Created by 宗仁 on 2017/1/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class GoodsListActivity extends ShopGoodsListActivity implements TextWatcherAdapter
        .TextWatcherListener, TextView.OnEditorActionListener, CoordinatorLayoutObservable,
        CartAnimationMaker, ScrollObserver {
    @Override
    public CommonFragment createFragment() {
        mFragment = new GoodsListFragment();
//        mFragment.defaultApi = Api.API_GOODS_LIST;
        Bundle arguments = new Bundle();

        if (getIntent() != null) {
            if (getIntent().getIntExtra(Key.KEY_KEYWORD_ACTION.getValue(), 0) == 1) {
                arguments.putString(Key.KEY_API.getValue(), Api.API_SEARCH_RESULT);
            }else{
                arguments.putString(Key.KEY_API.getValue(),Api.API_GOODS_LIST);
            }
        }


        if (getIntent() != null) {

            arguments.putString(Key.KEY_REQUEST_CATEGORY_ID.getValue(), getIntent()
                    .getStringExtra(Key.KEY_CATEGORY.getValue()));
            arguments.putString(Key.KEY_REQUEST_KEYWORD.getValue(), getIntent().getStringExtra
                    (Key.KEY_KEYWORD.getValue()));
            arguments.putString(Key.KEY_REQUEST_BRAND_ID.getValue(), getIntent().getStringExtra
                    (Key.KEY_GROUP_BUY_ACT_ID.getValue()));

            arguments.putString(Key.KEY_REQUEST_SHOP_ID.getValue(), getIntent().getStringExtra
                    (Key.KEY_SHOP_ID.getValue()));

            arguments.putString(Key.KEY_ACT_ID.getValue(), getIntent().getStringExtra
                    (Key.KEY_ACT_ID.getValue()));
        }

        arguments.putInt(Key.KEY_VISIBLE_MODEL.getValue(), GoodsListFragment.TOP_MODEL |
                GoodsListFragment.RIGHT_MODEL);

        mFragment.setArguments(arguments);
        mFragment.addScrollObserver(this);
        return mFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String keyWord = getIntent().getStringExtra(Key.KEY_KEYWORD.getValue());
        if (!Utils.isNull(keyWord)) {
            mKeywordEditText.setText(keyWord);
            mKeywordEditText.setSelection(keyWord.length());
        }
        mKeywordEditText.setHint(R.string.hintEnterGoodsName);
    }

}