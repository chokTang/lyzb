package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.szy.yishopcustomer.Activity.ProfileActivity;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;

import butterknife.BindView;

/**
 * Created by liuzhfieng on 16/5/7.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ChangeNickNameFragment extends YSCBaseFragment implements TextWatcher {
    private final int REQUEST_CODE_NICK_NAME = 2;
    @BindView(R.id.fragment_change_nickname_clearimageview)
    ImageView mClearInput;
    @BindView(R.id.fragment_change_nickname_editext)
    EditText mEditText;
    @BindView(R.id.submit_button)
    Button mSubmit;

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (!mEditText.getText().toString().equals("")) {
            mClearInput.setVisibility(View.VISIBLE);
        } else {
            mClearInput.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_change_nickname_clearimageview:
                mEditText.setText("");
                break;
            case R.id.submit_button:
                close();
                break;
            default:
                super.onClick(v);
                break;
        }
    }

    private void close() {
        if (Utils.isNull(mEditText.getText().toString())) {
            Utils.toastUtil.showToast(getActivity(),"输入的内容不合法");
            return;
        }
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        intent.putExtra("NICKNAME", mEditText.getText().toString());
        getActivity().setResult(REQUEST_CODE_NICK_NAME, intent);
        getActivity().finish();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        Intent intent = getActivity().getIntent();
        String mNickName = intent.getStringExtra(Key.KEY_NIKE_NAME.getValue());

        if (!mNickName.equals("")) {
            mClearInput.setVisibility(View.VISIBLE);
            mEditText.setText(mNickName);
            mEditText.setSelection(mNickName.length());
        }
        mClearInput.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
        mEditText.addTextChangedListener(this);
        mSubmit.setEnabled(true);
        return v;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_change_nickname;
    }
}
