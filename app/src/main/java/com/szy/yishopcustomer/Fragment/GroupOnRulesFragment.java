package com.szy.yishopcustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.TextView;

import com.szy.common.Other.CommonRequest;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.GroupOnModel.FightTimeModel;
import com.szy.yishopcustomer.Util.HttpResultManager;

import butterknife.BindView;

/**
 * Created by zongren on 16/7/19.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class GroupOnRulesFragment extends YSCBaseFragment {
    @BindView(R.id.textView9)
    TextView mTextViewOne;
    @BindView(R.id.textView11)
    TextView mTextViewTwo;
    private String fightTime;

    @BindView(R.id.linearlayout_rule)
    View linearlayout_rule;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_group_on_rules;

        Intent intent = getActivity().getIntent();
        String actId = intent.getStringExtra(Key.KEY_ACT_ID.getValue());
        refresh(actId);
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GROUP_ON_RULE:
                HttpResultManager.resolve(response, FightTimeModel.class, new
                        HttpResultManager.HttpResultCallBack<FightTimeModel>() {
                            @Override
                            public void onSuccess(FightTimeModel model) {
                                linearlayout_rule.setVisibility(View.VISIBLE);
                                fightTime = model.data.fight_time;
                                mTextViewOne.setText("拼团有效期是自开团时刻起的"+fightTime+"小时内，如果距离活动失效时间小于"+fightTime+"小时，则以活动的结束时间为准。");
                                mTextViewTwo.setText("超过成团有效期"+fightTime+"小时，未达成相应参团人数的团，则该团失败。\n在团有效期"+fightTime+"小时内，商品已提前售罄，若还未拼团成功，则该团失败。\n"
                                        + "高峰期间，同时支付的人过多，团人数有限制，以接收第三方支付信息时间先后为准，超出该团人数限制的部分用户则会拼团失败。\n" +
                                        "拼团失败的订单，系统会自动原路退款至支付账户中,如使用余额支付则立即退回至余额中。");
                            }
                        });
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private void refresh(String actId) {
        CommonRequest request = new CommonRequest(Api.API_GROUPON_RULE + actId, HttpWhat.HTTP_GROUP_ON_RULE.getValue());
        addRequest(request);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

}
