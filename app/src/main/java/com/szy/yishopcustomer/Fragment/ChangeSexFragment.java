package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.szy.yishopcustomer.Activity.ProfileActivity;
import com.lyzb.jbx.R;

import butterknife.BindView;

/**
 * Created by liuzhfieng on 16/5/27.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ChangeSexFragment extends YSCBaseFragment {
    private final int REQUESTCODE_SEX = 3;
    @BindView(R.id.fragment_change_sex_man)
    RelativeLayout mSelectman;
    @BindView(R.id.fragment_change_sex_wman)
    RelativeLayout mSelectWoman;
    @BindView(R.id.fragment_change_sex_secrecy)
    RelativeLayout mSelectSecrecy;
    @BindView(R.id.fragment_change_sex_checked_man)
    ImageView mSelectImageMan;
    @BindView(R.id.fragment_change_sex_checked_wman)
    ImageView mSelectImageWoman;
    @BindView(R.id.fragment_change_sex_checked_secrecy)
    ImageView mSelectImageSecrecy;
    private String sex;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_change_sex_man:
                mSelectImageMan.setVisibility(View.VISIBLE);
                mSelectImageWoman.setVisibility(View.INVISIBLE);
                mSelectImageSecrecy.setVisibility(View.INVISIBLE);
                close("1");
                break;
            case R.id.fragment_change_sex_wman:
                mSelectImageMan.setVisibility(View.INVISIBLE);
                mSelectImageWoman.setVisibility(View.VISIBLE);
                mSelectImageSecrecy.setVisibility(View.INVISIBLE);
                close("2");
                break;
            case R.id.fragment_change_sex_secrecy:
                mSelectImageMan.setVisibility(View.INVISIBLE);
                mSelectImageWoman.setVisibility(View.INVISIBLE);
                mSelectImageSecrecy.setVisibility(View.VISIBLE);
                close("0");
                break;
            default:
                super.onClick(v);
                break;
        }
    }

    private void close(String value) {
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        intent.putExtra("SEX", value);
        getActivity().setResult(REQUESTCODE_SEX, intent);
        getActivity().finish();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        mSelectman.setOnClickListener(this);
        mSelectWoman.setOnClickListener(this);
        mSelectSecrecy.setOnClickListener(this);
        Intent intent = getActivity().getIntent();
        sex = intent.getStringExtra("sex");
        if (sex.equals("男")) {
            mSelectImageMan.setVisibility(View.VISIBLE);
        } else if (sex.equals("女")) {
            mSelectImageWoman.setVisibility(View.VISIBLE);
        } else {
            mSelectImageSecrecy.setVisibility(View.VISIBLE);
        }
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_change_sex;

    }
}
