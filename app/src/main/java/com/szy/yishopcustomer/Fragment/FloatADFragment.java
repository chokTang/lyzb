package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;

import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Activity.RootActivity;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.AdColumnModel;
import com.szy.yishopcustomer.ResponseModel.AppIndex.AdItemModel;
import com.szy.yishopcustomer.Util.BrowserUrlManager;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.Utils;

import java.util.List;

import butterknife.BindView;


/**
 * Created by liuzhfieng on 2017/3/30.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class FloatADFragment extends YSCBaseFragment {
    @BindView(R.id.close_button)
    ImageView mCloseButton;
    @BindView(R.id.ad_image_view)
    ImageView mAdImageView;
    private String data;
    private String link;

    @Override
    public void onClick(View v) {
        ViewType viewType = Utils.getViewTypeOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_CLOSE:
                getActivity().finish();
                break;
            case VIEW_TYPE_BONUS:
                openAd(link);
                break;
            default:
                super.onClick(v);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        Utils.setViewTypeForTag(mCloseButton, ViewType.VIEW_TYPE_CLOSE);
        mCloseButton.setOnClickListener(this);
        Utils.setViewTypeForTag(mAdImageView, ViewType.VIEW_TYPE_BONUS);
        mAdImageView.setOnClickListener(this);

        Intent intent = getActivity().getIntent();
        data = intent.getStringExtra(Key.KEY_FLOAT_DATA.getValue());
        if (!Utils.isNull(data)) {
            AdColumnModel adModel = JSON.parseObject(data, AdColumnModel.class);
            if (!Utils.isNull(adModel.pic_1)) {
                List<AdItemModel> items = adModel.pic_1;
                AdItemModel firstItem = items.get(0);
                link = firstItem.link;
                ImageLoader.displayImage(Utils.urlOfImage(firstItem.path), mAdImageView);
            }

        }
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_float_ad;
    }

    public void openAd(String link) {
        if (!Utils.isNull(link)) {
            startActivity(new Intent(getActivity(), RootActivity.class));
            getActivity().finish();
            new BrowserUrlManager().parseUrl(getContext(), link);
        } else {
            //Toast.makeText(getActivity(), R.string.emptyLink, Toast.LENGTH_SHORT).show();
        }
    }
}
