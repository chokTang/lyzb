package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;

import butterknife.BindView;

/**
 * Created by liwei on 2016/3/16.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class MessageDetailFragment extends YSCBaseFragment {
    @BindView(R.id.message_close_button)
    ImageView mCloseButton;
    @BindView(R.id.message_content)
    TextView mContentTextView;
    @BindView(R.id.message_title_textView)
    TextView mTitleTextView;

    private String mTitle;
    private String mContent;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.message_close_button:
                getActivity().finish();
                break;
            default:
                super.onClick(v);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mCloseButton.setOnClickListener(this);

        mContentTextView.setText(mContent);
        mTitleTextView.setText(mTitle);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_message_detail;

        Intent intent = getActivity().getIntent();
        mTitle = intent.getStringExtra(Key.KEY_MESSAGE_TITLE.getValue());
        mContent = intent.getStringExtra(Key.KEY_MESSAGE_CONTENT.getValue());
    }
}
