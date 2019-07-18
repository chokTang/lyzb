package com.szy.yishopcustomer.Fragment;

import android.os.Bundle;

import com.szy.yishopcustomer.Adapter.GoodsListAdapter;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;

/**
 * Created by 宗仁 on 2017/1/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class GoodsListFragment extends ShopGoodsListFragment {

    @Override
    protected GoodsListAdapter createGoodsListAdapter() {
        return new GoodsListAdapter(getContext());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_goods_list;
        Bundle arguments = getArguments();

        mGridItemOffset = Utils.dpToPx(getContext(), 5);
    }
}
