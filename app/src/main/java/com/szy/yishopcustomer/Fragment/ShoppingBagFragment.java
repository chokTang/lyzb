package com.szy.yishopcustomer.Fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.*;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.yishopcustomer.Activity.CheckoutFreeActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.BaseEntity;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.ViewModel.ShoppingBagModel;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Smart on 2017/6/5.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class ShoppingBagFragment extends YSCBaseFragment {

    private static final int HTTP_CART_GET_SHOP_BAG = 1;
    private static final int HTTP_BATCH_ADD = 2;
    private static final int HTTP_GO_CHECKOUT = 3;

    @BindView(R.id.linearlayout_parent)
    LinearLayout linearlayout_parent;

    @BindView(R.id.linearlayout_bag)
    LinearLayout linearlayout_bag;

    @BindView(R.id.item_cart_goods_minus_button)
    View item_cart_goods_minus_button;
    @BindView(R.id.item_cart_goods_add_button)
    View item_cart_goods_add_button;
    @BindView(R.id.item_cart_goods_number)
    TextView item_cart_goods_number;

    @BindView(R.id.fragment_attribute_cancel)
    View fragment_attribute_cancel;
    @BindView(R.id.fragment_attribute_confirm)
    View fragment_attribute_confirm;

    List<Integer> goodNum;

    int[] bagImgSelect = new int[]{R.mipmap.ic_bag_small_selected, R.mipmap.ic_bag_medium_selected, R.mipmap.ic_bag_big_selected};
    int[] bagImgDefault = new int[]{R.mipmap.ic_bag_small_default, R.mipmap.ic_bag_medium_default, R.mipmap.ic_bag_big_default};

    ShoppingBagModel shoppingBagModel;
    private String shopId;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_cart_goods_add_button:
                goodNum.set(postion, goodNum.get(postion) + 1);
                updateGoodNum();
                break;
            case R.id.item_cart_goods_minus_button:
                goodNum.set(postion, goodNum.get(postion) - 1);
                if (goodNum.get(postion) <= 0) {
                    goodNum.set(postion, 0);
                }
                updateGoodNum();
                break;
            case R.id.fragment_attribute_cancel:
                goCheckOut();
                break;
            case R.id.fragment_attribute_confirm:
                //把购物袋加入购物车
                batchAdd();
                break;
        }
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_GO_CHECKOUT:
                goCheckOutCallBack(response);
                break;
            case HTTP_CART_GET_SHOP_BAG:
                refreshCallback(response);
                break;
            case HTTP_BATCH_ADD:
                batchAddCallBack(response);
                break;
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private void goCheckOutCallBack(String response) {
        HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
            ResponseCommonModel responseModel;

            @Override
            public void getObj(ResponseCommonModel back) {
                responseModel = back;
            }

            @Override
            public void onSuccess(ResponseCommonModel back) {
                openCheckoutActivity();
            }
        }, true);
    }

    private void batchAdd() {
        CommonRequest mGoCheckoutList = new CommonRequest(Api.API_FREEBUY_CART_BATCH_ADD, HTTP_BATCH_ADD, RequestMethod.POST);
        mGoCheckoutList.setAjax(true);

        for (int i = 0, len = goodNum.size(); i < len; i++) {
            if (goodNum.get(i) > 0 && shoppingBagModel != null) {
                mGoCheckoutList.add("sku_list[]", shoppingBagModel.data.get(i).sku_id + "-" + goodNum.get(i));
            }
        }

        mGoCheckoutList.add("is_sku", true);
        mGoCheckoutList.add("shop_id", shopId);
        mGoCheckoutList.add("buyer_type", 2);
        addRequest(mGoCheckoutList);
    }

    private void batchAddCallBack(String response){
        HttpResultManager.resolve(response, BaseEntity.class, new HttpResultManager.HttpResultCallBack<BaseEntity>() {
            @Override
            public void onSuccess(BaseEntity back) {
                goCheckOut();
            }
        },true);
    }

    private void goCheckOut() {
        CommonRequest mGoCheckoutList = new CommonRequest(Api.API_FREEBUY_GO_CHECKOUT, HTTP_GO_CHECKOUT, RequestMethod.GET);
        mGoCheckoutList.setAjax(true);
        mGoCheckoutList.add("shop_id", shopId);
        addRequest(mGoCheckoutList);
    }

    void openCheckoutActivity() {
        Intent intent = new Intent(getActivity(), CheckoutFreeActivity.class);
        intent.putExtra(Key.KEY_SHOP_ID.getValue(), shopId);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_shopping_bag;

        Intent intent = getActivity().getIntent();
        shopId = intent.getStringExtra(Key.KEY_SHOP_ID.getValue());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) linearlayout_parent.getLayoutParams();
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        lp.width = display.getWidth() * 5 / 6; // 设置dialog宽度为屏幕的5/6

        item_cart_goods_minus_button.setOnClickListener(this);
        item_cart_goods_add_button.setOnClickListener(this);
        fragment_attribute_cancel.setOnClickListener(this);
        fragment_attribute_confirm.setOnClickListener(this);

        for (int i = 0, len = linearlayout_bag.getChildCount(); i < len; i++) {
            View bag = linearlayout_bag.getChildAt(i);
            bag.getLayoutParams().width = lp.width/3;
        }

        refresh();

        goodNum = new ArrayList<>();
        //获取购物袋信息

        return view;
    }

    View.OnClickListener bagClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setCheck(view);
        }
    };

    int postion = 0;

    void setCheck(View v) {
        for (int i = 0, len = linearlayout_bag.getChildCount(); i < len; i++) {
            RelativeLayout view = (RelativeLayout) linearlayout_bag.getChildAt(i);

            TextView textViewGoodNum = (TextView) view.getChildAt(1);
            LinearLayout linearLayout = (LinearLayout) view.getChildAt(0);

            ImageView bag = (ImageView) linearLayout.getChildAt(0);
            TextView bagName = (TextView) linearLayout.getChildAt(1);
            TextView bagPrice = (TextView) linearLayout.getChildAt(2);

            textViewGoodNum.setText(goodNum.get(i) + "");
            if (view == v) {
                postion = i;
                item_cart_goods_number.setText(goodNum.get(postion) + "");
                linearLayout.setBackgroundResource(R.drawable.red_border_button_one);
                bag.setImageResource(bagImgSelect[1]);
                bagName.setTextColor(getResources().getColor(R.color.colorPrimary));
                bagPrice.setTextColor(getResources().getColor(R.color.colorPrimary));
            } else {
                linearLayout.setBackgroundColor(Color.WHITE);
                bag.setImageResource(bagImgDefault[1]);
                bagName.setTextColor(getResources().getColor(R.color.colorOne));
                bagPrice.setTextColor(getResources().getColor(R.color.colorOne));
            }
        }
    }


    void updateGoodNum() {
        RelativeLayout view = (RelativeLayout) linearlayout_bag.getChildAt(postion);
        TextView textViewGoodNum = (TextView) view.getChildAt(1);
        if(goodNum.get(postion) > 0) {
            textViewGoodNum.setVisibility(View.VISIBLE);
        } else {
            textViewGoodNum.setVisibility(View.GONE);
        }
        textViewGoodNum.setText(goodNum.get(postion) + "");
        item_cart_goods_number.setText(goodNum.get(postion) + "");


        int totalCount = 0;
        for(int i = 0 , len = goodNum.size() ; i < len ; i ++) {
            totalCount += goodNum.get(i);
        }

        if(totalCount > 0 ) {
//            更新按钮状态
            fragment_attribute_confirm.setEnabled(true);
        } else {
            fragment_attribute_confirm.setEnabled(false);
        }
    }


    public void refresh() {
        CommonRequest commonRequest = new CommonRequest(Api.API_FREEBUY_CART_GET_SHOP_BAG, HTTP_CART_GET_SHOP_BAG);
        commonRequest.add("shop_id", shopId);
        addRequest(commonRequest);
    }

    private void refreshCallback(String response) {
        HttpResultManager.resolve(response, ShoppingBagModel.class, new HttpResultManager.HttpResultCallBack<ShoppingBagModel>() {
            @Override
            public void onSuccess(ShoppingBagModel back) {

                shoppingBagModel = back;
                for (int i = 0, len = linearlayout_bag.getChildCount(); i < len; i++) {

                    goodNum.add(i, 0);
                    RelativeLayout view = (RelativeLayout) linearlayout_bag.getChildAt(i);

                    if (i < back.data.size() && i < 3) {
                        ShoppingBagModel.DataBean dataBean = back.data.get(i);

                        view.setOnClickListener(bagClick);

                        TextView textViewGoodNum = (TextView) view.getChildAt(1);
                        LinearLayout linearLayout = (LinearLayout) view.getChildAt(0);

                        ImageView bag = (ImageView) linearLayout.getChildAt(0);
                        TextView bagName = (TextView) linearLayout.getChildAt(1);
                        TextView bagPrice = (TextView) linearLayout.getChildAt(2);


                        textViewGoodNum.setText(goodNum.get(i) + "");
                        bagName.setText(dataBean.sku_name);
                        bagPrice.setText("¥ " + dataBean.goods_price);

//                        ImageLoader.displayImage(Utils.urlOfImage(dataBean.sku_image), bag);
                        if (i == 0) {
                            postion = i;
                            item_cart_goods_number.setText(goodNum.get(postion) + "");
                            linearLayout.setBackgroundResource(R.drawable.red_border_button_one);
                           bag.setImageResource(bagImgSelect[1]);
                            bagName.setTextColor(getResources().getColor(R.color.colorPrimary));
                            bagPrice.setTextColor(getResources().getColor(R.color.colorPrimary));
                        } else {
                            linearLayout.setBackgroundColor(Color.WHITE);
                           bag.setImageResource(bagImgDefault[1]);
                            bagName.setTextColor(getResources().getColor(R.color.colorOne));
                            bagPrice.setTextColor(getResources().getColor(R.color.colorOne));
                        }
                    } else {
                        view.setVisibility(View.GONE);
                    }
                }
                setCheck(linearlayout_bag.getChildAt(0));
                linearlayout_bag.setVisibility(View.VISIBLE);
            }
        });
    }
}
