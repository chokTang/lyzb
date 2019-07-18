package com.szy.yishopcustomer.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.common.Util.ImageLoader;
import com.szy.common.Util.JSON;
import com.szy.yishopcustomer.Activity.GoodsRankPriceActivity;
import com.szy.yishopcustomer.Activity.PromotionActivity;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.GoodsMix;
import com.szy.yishopcustomer.Util.Utils;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PromotionPopFragment extends YSCBaseFragment {

    List<GoodsMix> mixes = null;
    private String goodsFullCutStr;
    private String memberPriceMessage;

    @BindView(R.id.linearlayout_goods_mix)
    LinearLayout linearlayout_goods_mix;

    @BindView(R.id.textView_save)
    TextView textView_save;
    @BindView(R.id.linearlayout_close)
    View linearlayout_close;
    @BindView(R.id.linearlayout_click)
    View linearlayout_click;
    @BindView(R.id.tv_full_cut)
    TextView tv_full_cut;
    @BindView(R.id.ll_full_cut)
    View ll_full_cut;
    @BindView(R.id.ll_member_price)
    View ll_member_price;
    @BindView(R.id.tv_member_price)
    TextView tv_member_price;

    private Context mContext;

    private String skuid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_promotion_pop;

        mContext = getActivity();
        Intent intent = getActivity().getIntent();
        String goodMixStr = intent.getStringExtra("goodMix");
        goodsFullCutStr = intent.getStringExtra("goodsFullCutStr");
        memberPriceMessage = intent.getStringExtra("memberPriceMessage");
        skuid = intent.getStringExtra(Key.KEY_SKU_ID.getValue());

        if (!TextUtils.isEmpty(goodMixStr)) {
            mixes = JSON.parseArray(goodMixStr, GoodsMix.class);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        //搭配套餐
        if(!Utils.isNull(mixes)){
            linearlayout_click.setVisibility(View.VISIBLE);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,Utils.dpToPx(mContext,15),0,0);

            double max_goods_diff = 0;
            String max_goods_diff_format = "";
            for (GoodsMix goodsMix : mixes) {

//                max_goods_diff = Math.max(goodsMix.max_goods_diff,max_goods_diff);
                if(goodsMix.max_goods_diff > max_goods_diff) {
                    max_goods_diff = goodsMix.max_goods_diff;
                    max_goods_diff_format = goodsMix.max_goods_diff_format;
                }



                LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView textView1 = new TextView(mContext);
                textView1.setText(goodsMix.act_name);
                textView1.setTextColor(Color.parseColor("#222222"));
                textParams.setMargins(0,Utils.dpToPx(mContext,15),0,Utils.dpToPx(mContext,10));
                textView1.setLayoutParams(params);
                textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);


                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setGravity(Gravity.CENTER_VERTICAL);
                linearLayout.setLayoutParams(params);

                for(int i = 0 , len = goodsMix.goods_info.size() ; i < len ; i ++) {
                    ImageView imageView = new ImageView(mContext);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(Utils.dpToPx(mContext,40),Utils.dpToPx(mContext,40)));
                    ImageLoader.displayImage(Utils.urlOfImage(goodsMix.goods_info.get(i).goods_image),imageView);
                    imageView.setBackgroundResource(R.drawable.gray_border_button_two);
                    imageView.setPadding(1,1,1,1);
                    linearLayout.addView(imageView);

                    if(i < len -1) {
                        TextView plusTv = new TextView(mContext);
                        plusTv.setTextColor(Color.parseColor("#222222"));
                        plusTv.setText(" + ");
                        linearLayout.addView(plusTv);
                    }
                }

                linearlayout_goods_mix.addView(textView1);
                linearlayout_goods_mix.addView(linearLayout);

                if("1".equals(goodsMix.discount_show)) {
                    TextView textView2 = new TextView(mContext);
                    textView2.setText("省¥" + goodsMix.min_goods_diff + "-¥" + goodsMix.max_goods_diff);
                    textView2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,13);
                    textView2.setHeight(Utils.dpToPx(mContext,25));
                    textView2.setLayoutParams(params);
                    textView2.setBackgroundColor(Color.parseColor("#eeeeee"));
                    textView2.setTextColor(Color.parseColor("#888888"));
                    textView2.setLayoutParams(params);
                    textView2.setGravity(Gravity.CENTER);
                    linearlayout_goods_mix.addView(textView2);
                }
            }

            textView_save.setText("最高省" + max_goods_diff_format + "元，共" + mixes.size() + "款");


            View.OnClickListener jumpListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //进入详情
                    Intent intent = new Intent(getActivity(), PromotionActivity.class);
                    intent.putExtra(Key.KEY_SKU_ID.getValue(),skuid);
                    startActivity(intent);
                    finish();
                }
            };
            linearlayout_click.setOnClickListener(jumpListener);
            linearlayout_goods_mix.setOnClickListener(jumpListener);
        }else {
            linearlayout_click.setVisibility(View.GONE);
        }

        //满减
        if(!Utils.isNull(goodsFullCutStr)) {
            ll_full_cut.setVisibility(View.VISIBLE);
            tv_full_cut.setText(goodsFullCutStr);
        }else{
            ll_full_cut.setVisibility(View.GONE);
        }

        //会员特价
        if(!Utils.isNull(memberPriceMessage)){
            ll_member_price.setVisibility(View.VISIBLE);
            tv_member_price.setText(memberPriceMessage);

            ll_member_price.setOnClickListener(this);
        }else {
            ll_member_price.setVisibility(View.GONE);
        }
        linearlayout_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {

        if (Utils.isDoubleClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.ll_member_price:
                openRankPrice();
                finish();
                break;
            default:

                break;
        }
    }

    public void openRankPrice() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(Key.KEY_RANK_PRICE.getValue(), GoodsIndexFragment.rankPrices);
        intent.setClass(getActivity(), GoodsRankPriceActivity.class);
        startActivity(intent);
    }
}
