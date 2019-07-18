package com.lyzb.jbx.fragment.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.like.longshaolib.base.BaseFragment;
import com.lyzb.jbx.R;
import com.lyzb.jbx.mvp.presenter.me.ShareWordPresenter;
import com.lyzb.jbx.mvp.view.me.IShareWordView;
import com.lyzb.jbx.util.AppPreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 分享语设置
 * @author wyx
 * @role
 * @time 2019 2019/5/14 14:10
 */

public class SetShareWordFragment extends BaseFragment<ShareWordPresenter>
        implements IShareWordView {
    @BindView(R.id.statistics_title_tv)
    TextView mStatisticsTitleTv;
    @BindView(R.id.setshareword_bcard_edit)
    EditText mSetsharewordBcardEdit;
    @BindView(R.id.setshareword_bcard_number_tv)
    TextView mSetsharewordBcardNumberTv;
    @BindView(R.id.setshareword_dynamic_edit)
    EditText mSetsharewordDynamicEdit;
    @BindView(R.id.setshareword_dynamic_number_tv)
    TextView mSetsharewordDynamicNumberTv;
    Unbinder unbinder;

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {
        mStatisticsTitleTv.setText("分享语设置");
        //名片分享语的实时字数
        mSetsharewordBcardEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mSetsharewordBcardNumberTv.setText(String.format("%d/%d", s.length(), 50));
            }
        });
        //动态分享语的实时字数
        mSetsharewordDynamicEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mSetsharewordDynamicNumberTv.setText(String.format("%d/%d", s.length(), 50));
            }
        });
    }

    @Override
    public void onInitData(@Nullable Bundle savedInstanceState) {
        mPresenter.getShareWord();
    }

    @Override
    public Object getResId() {
        return R.layout.fragment_setshareword;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.statistics_back_iv, R.id.setshareword_kepp_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.statistics_back_iv:
                pop();
                break;
            case R.id.setshareword_kepp_tv:
                //名片分享语
                String bCard = mSetsharewordBcardEdit.getText().toString();
                String dynamic = mSetsharewordDynamicEdit.getText().toString();
                if (TextUtils.isEmpty(bCard) && TextUtils.isEmpty(dynamic)) {
                    pop();
                    return;
                }
                //保存分享语
                mPresenter.setShareWord(bCard, dynamic);
                break;
            default:
        }
    }

    @Override
    public void onShareWord(String bCard, String dynamic) {
        //获取分享语成功
        mSetsharewordBcardEdit.setText(bCard);
        mSetsharewordDynamicEdit.setText(dynamic);
        AppPreference.getIntance().setShareCardValue(bCard);
        AppPreference.getIntance().setShareDynamicValue(dynamic);
    }

    @Override
    public void onSetChareWordSuccess() {
        pop();
    }

    @Override
    public void onFail(String msg) {
        showToast(msg);
    }
}
