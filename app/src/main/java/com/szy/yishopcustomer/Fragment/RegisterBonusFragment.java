package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.szy.common.Other.CommonEvent;
import com.szy.yishopcustomer.Activity.BonusActivity;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.Utils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

//import com.szy.common.View.CustomProgressDialog;

/**
 * Created by liwei on 2016/12/13.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class RegisterBonusFragment extends YSCBaseFragment {
    @BindView(R.id.close_button)
    ImageView mCloseButton;
    @BindView(R.id.check_bonus_button)
    ImageView mCheckBonusButton;
    @BindView(R.id.register_bonus_tip)
    TextView mRegisterBonusTip;
    @BindView(R.id.register_bonus_value)
    TextView mRegisterBonusValue;
    @BindView(R.id.register_bonus_value_tip)
    TextView mRegisterBonusValueTip;
    private String registerTip;
    private String bonusValue;
    private String bonusName;
    private int type;

    @Override
    public void onClick(View v) {
        ViewType viewType = Utils.getViewTypeOfTag(v);
        int position = Utils.getPositionOfTag(v);
        int extraInfo = Utils.getExtraInfoOfTag(v);
        switch (viewType) {
            case VIEW_TYPE_CLOSE:
                getActivity().finish();
                break;
            case VIEW_TYPE_BONUS:
                openBonusActivity();
                getActivity().finish();
                break;
            default:
                super.onClick(v);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mRegisterBonusValue.setText(bonusValue);
        mRegisterBonusTip.setText(registerTip);
        mRegisterBonusValueTip.setText(bonusValue + "的" + bonusName);
        Utils.setViewTypeForTag(mCloseButton, ViewType.VIEW_TYPE_CLOSE);
        mCloseButton.setOnClickListener(this);
        Utils.setViewTypeForTag(mCheckBonusButton, ViewType.VIEW_TYPE_BONUS);
        mCheckBonusButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_LOGIN.getValue()));
        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_CLOSE.getValue()));
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_register_bonus;

        Intent intent = getActivity().getIntent();
        String bonusNumber = intent.getStringExtra(Key.KEY_BONUS_NUMBER.getValue());
        bonusValue = intent.getStringExtra(Key.KEY_BONUS_VALUE.getValue());
        registerTip = "恭喜您！获得" + bonusNumber + "张总价值为";
        bonusName = intent.getStringExtra(Key.KEY_BONUS_NAME.getValue());
        type = intent.getIntExtra(Key.KEY_BONUS_TYPE.getValue(), 0);
    }

    private void openBonusActivity() {
        Intent intent = new Intent(getContext(), BonusActivity.class);
        intent.putExtra(Key.KEY_BONUS_TYPE.getValue(), type);
        startActivity(intent);
    }
}
