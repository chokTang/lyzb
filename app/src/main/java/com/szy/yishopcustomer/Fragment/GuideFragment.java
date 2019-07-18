package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lyzb.jbx.activity.HomeActivity;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Activity.RootActivity;
import com.szy.yishopcustomer.Adapter.GuideAdapter;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by 宗仁 on 2016/5/24 0024.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GuideFragment extends YSCBaseFragment implements ViewPager.OnPageChangeListener {

    @BindView(R.id.fragment_guide_viewPager)
    ViewPager mViewPager;
    @BindView(R.id.fragment_guide_enterImageButton)
    ImageButton mEnterImageButton;
    @BindView(R.id.fragment_guide_enterButton)
    Button mEnterButton;
    @BindView(R.id.fragment_guide_enter_text_view)
    TextView mEnterTextView;

    private GuideAdapter mAdapter;
    private ArrayList<String> mImageList;
    private String mEnterButtonUrl;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_guide_enterImageButton:
            case R.id.fragment_guide_enter_text_view:
            case R.id.fragment_guide_enterButton:
                Utils.setSharedPreferences(getContext(), Key.KEY_IS_USED.toString(), true);

                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                super.onClick(view);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);
        mEnterImageButton.setOnClickListener(this);
        mEnterButton.setOnClickListener(this);
        mEnterTextView.setOnClickListener(this);

        if (!Utils.isNull(mEnterButtonUrl)) {
            ImageLoader.displayImage(Utils.urlOfImage(mEnterButtonUrl), mEnterImageButton);
        }
        if (mImageList.size() == 1) {
            if (!Utils.isNull(mEnterButtonUrl)) {
                mEnterImageButton.setVisibility(View.VISIBLE);
                mEnterButton.setVisibility(View.INVISIBLE);
            } else {
                mEnterImageButton.setVisibility(View.INVISIBLE);
                mEnterButton.setVisibility(View.VISIBLE);
            }
        }
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_guide;

        Bundle arguments = getArguments();
        if (arguments != null) {
            mImageList = arguments.getStringArrayList(Key.KEY_GUIDE_IMAGES.getValue());
            mEnterButtonUrl = arguments.getString(Key.KEY_GUIDE_BUTTON.getValue());
        } else {
            Utils.makeToast(getActivity(), getActivity().getResources().getString(R.string.unknown_error));
            EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_FINISH_GUIDE.getValue(), getActivity().getResources().getString(R.string.unknown_error)));
        }
        mAdapter = new GuideAdapter();
        mAdapter.data = mImageList;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (position == (mImageList.size() - 1)) {
            if (!Utils.isNull(mEnterButtonUrl)) {
                mEnterImageButton.setVisibility(View.VISIBLE);
                mEnterButton.setVisibility(View.INVISIBLE);
                mEnterTextView.setVisibility(View.INVISIBLE);
            } else {
                mEnterImageButton.setVisibility(View.INVISIBLE);
                mEnterButton.setVisibility(View.VISIBLE);
                mEnterTextView.setVisibility(View.INVISIBLE);
            }
        } else {
            mEnterImageButton.setVisibility(View.INVISIBLE);
            mEnterButton.setVisibility(View.INVISIBLE);
            mEnterTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

}
