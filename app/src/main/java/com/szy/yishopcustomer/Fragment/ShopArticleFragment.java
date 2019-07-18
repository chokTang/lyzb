package com.szy.yishopcustomer.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.Toast;

import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Adapter.ShopArticleAdapter;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Shop.ShopModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.ShopArticleDetailModel;
import com.szy.yishopcustomer.ViewModel.ShopArticleTitleModel;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by liuzhifeng on 2016/3/16.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShopArticleFragment extends YSCBaseFragment {
    @BindView(R.id.fragment_shop_article_RecyclerView)
    CommonRecyclerView mRecyclerView;
    @BindView(R.id.fragment_shop_article_close)
    ImageView mClose;
    private ShopArticleAdapter mAdapter;
    private ShopModel mShopModel;

    @Override
    public void onClick(View view) {
        if(Utils.isDoubleClick()) {
            return;
        }
        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_SHOP_ATRICLE_CLOSE:
                finish();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        Utils.setViewTypeForTag(mClose, ViewType.VIEW_TYPE_SHOP_ATRICLE_CLOSE);
        mClose.setOnClickListener(this);
        ArrayList<Object> list = new ArrayList<>();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ShopArticleAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter.onClickListener = this;
        ShopArticleTitleModel mShopArticleTitleModel = new ShopArticleTitleModel();
        mShopArticleTitleModel.shopName = mShopModel.shop_name;
        mShopArticleTitleModel.url = mShopModel.url;
        mShopArticleTitleModel.detail = "描述"+mShopModel.desc_score+" | 服务"+mShopModel.service_score+" | 发货"+mShopModel.send_score;
        list.add(mShopArticleTitleModel);
        ShopArticleDetailModel mShipArticleDetailModel = new ShopArticleDetailModel();
        mShipArticleDetailModel.detail = mShopModel.detail_introduce;
        list.add(mShipArticleDetailModel);
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_shop_article;
        Bundle arguments = getArguments();
        if (arguments == null) {
            Toast.makeText(getContext(), "Empty arguments", Toast.LENGTH_SHORT).show();
            return;
        }
        mShopModel = (ShopModel)arguments.getParcelable(Key.KEY_SHOP_MODEL.getValue());

    }
}
