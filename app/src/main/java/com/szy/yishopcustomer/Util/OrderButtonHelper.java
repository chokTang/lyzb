package com.szy.yishopcustomer.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import com.szy.yishopcustomer.Constant.ViewType;
import com.szy.yishopcustomer.Interface.OnOrderButtonListener;
import com.lyzb.jbx.R;

import java.util.List;

/**
 * Created by Smart on 2017/9/1.
 */

public class OrderButtonHelper {

    //查看物流
    public static final String VIEW_LOGISTICS = "view_logistics";
    //用户中心-我的积分-积分兑换 的查看物流和普通订单接口不同
    public static final String VIEW_EXCHANGE_LOGISTICS = "view_exchange_logistics";
    public static final String CONFIRM_ORDER = "confirm_order";


    public static final String VIEW_PICKLIST = "view_picklist";

    public static final int TAG_BUTTON_TYPE = 111111111;

    public static void initButtons(Context context,List buttons,OnOrderButtonListener listener){
        for (int i = 0, len = buttons.size(); i < len; i++) {
            String button_type = (String) buttons.get(i);

            Button button = getButton(context,i);
            button.setTag(TAG_BUTTON_TYPE,button_type);

            switch (button_type) {
                case "cancel_order":
                    button.setText(R.string.cancelOrder);
                    Utils.setViewTypeForTag(button, ViewType
                            .VIEW_TYPE_CANCEL_ORDER);

                    button.setTextColor(0xff222222);
                    button.setBackgroundResource(R.drawable
                            .gray_border_button_one);
                    listener.cancel_order(button);
                    break;
                case VIEW_LOGISTICS:
                    button.setText(R.string.view_logistics);
                    Utils.setViewTypeForTag(button, ViewType
                            .VIEW_TYPE_VIEW_LOGISTICS);
                    button.setTextColor(0xff222222);
                    button.setBackgroundResource(R.drawable
                            .gray_border_button_one);
                    listener.view_logistics(button);
                    break;
                case VIEW_EXCHANGE_LOGISTICS:
                    button.setText(R.string.view_logistics);
                    Utils.setViewTypeForTag(button, ViewType
                            .VIEW_TYPE_VIEW_EXCHANGE_LOGISTICS);
                    button.setTextColor(0xff222222);
                    button.setBackgroundResource(R.drawable
                            .gray_border_button_one);
                    listener.view_logistics(button);
                    break;
                case "to_pay":
                    button.setText(R.string.buttonPayRightNow);

                    Utils.setViewTypeForTag(button, ViewType
                            .VIEW_TYPE_PAYMENT);
                    button.setTextColor(0xfff23030);
                    button.setBackgroundResource(R.drawable
                            .red_border_button_one);
                    listener.to_pay(button);
                    break;
                case "confirm_order":
                    button.setText(R.string.confirmOrder);

                    Utils.setViewTypeForTag(button, ViewType
                            .VIEW_TYPE_AWAIT_CONFIRM);
                    button.setTextColor(0xfff23030);
                    button.setBackgroundResource(R.drawable
                            .red_border_button_one);
                    listener.confirm_order(button);
                    break;
                case "to_comment":
                    button.setText(R.string.activityOrderReview);

                    Utils.setViewTypeForTag(button, ViewType
                            .VIEW_TYPE_COMMENT);
                    button.setTextColor(0xfff23030);
                    button.setBackgroundResource(R.drawable
                            .red_border_button_one);
                    listener.to_comment(button);
                    break;
                case "add_comment":
                    button.setText(R.string.add_comment);

                    Utils.setViewTypeForTag(button, ViewType
                            .VIEW_TYPE_COMMENT);
                    button.setTextColor(0xff222222);
                    button.setBackgroundResource(R.drawable
                            .gray_border_button_one);
                    listener.add_comment(button);
                    break;
                case "commented":
                    button.setText(R.string.viewComment);
                    button.setTextColor(0xff999999);
                    button.setBackgroundResource(R.drawable
                            .gray_border_button_two);
                    listener.commented(button);
                    break;
                case "ot_fight_group":
                    //邀请好友拼团
                    button.setText(R.string.invite_group);

                    Utils.setViewTypeForTag(button, ViewType
                            .VIEW_TYPE_INVITE_FRIEND);
                    button.setTextColor(0xfff23030);
                    button.setBackgroundResource(R.drawable
                            .red_border_button_one);
                    listener.ot_fight_group(button);
                    break;
                case "del_order":
                    button.setText(R.string.delOrder);

                    Utils.setViewTypeForTag(button, ViewType
                            .VIEW_TYPE_DEL_ORDER);
                    button.setTextColor(0xff222222);
                    button.setBackgroundResource(R.drawable
                            .gray_border_button_one);
                    listener.del_order(button);
                    break;
                case "delay_order":
                    button.setText(R.string.delayOrder);

                    Utils.setViewTypeForTag(button, ViewType
                            .VIEW_TYPE_DELAY_ORDER);
                    button.setTextColor(0xff222222);
                    button.setBackgroundResource(R.drawable
                            .gray_border_button_one);
                    listener.delay_order(button);
                    break;
                case VIEW_PICKLIST:
                    button.setText("查看自提点");
                    Utils.setViewTypeForTag(button, ViewType
                            .VIEW_TYPE_PICK_UP);
                    button.setTextColor(0xff222222);
                    button.setBackgroundResource(R.drawable
                            .gray_border_button_one);
                    listener.view_logistics(button);
                    break;
            }
        }
    }

    private static Button getButton(Context context, int i){
        Button button = (Button) LayoutInflater.from(context).inflate(
                R.layout.item_order_list_button, null);
        button.setMinWidth(Utils.dpToPx(context, 90));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,Utils.dpToPx(context, 30));
        if (i > 0) {
            layoutParams.setMargins(Utils.dpToPx(context, 5), 0, 0, 0);
        }
        button.setLayoutParams(layoutParams);

        return button;
    }

    public static void setTagForType(Button button,String button_type){
        switch (button_type) {
            case "cancel_order":
                Utils.setViewTypeForTag(button, ViewType
                        .VIEW_TYPE_CANCEL_ORDER);
                break;
            case "view_logistics":
                Utils.setViewTypeForTag(button, ViewType
                        .VIEW_TYPE_VIEW_LOGISTICS);
                break;
            case "to_pay":
                Utils.setViewTypeForTag(button, ViewType
                        .VIEW_TYPE_PAYMENT);
                break;
            case "confirm_order":
                Utils.setViewTypeForTag(button, ViewType
                        .VIEW_TYPE_AWAIT_CONFIRM);
                break;
            case "to_comment":
                Utils.setViewTypeForTag(button, ViewType
                        .VIEW_TYPE_COMMENT);
                break;
            case "add_comment":
                Utils.setViewTypeForTag(button, ViewType
                        .VIEW_TYPE_COMMENT);
                break;
            case "commented":
                break;
            case "ot_fight_group":
                Utils.setViewTypeForTag(button, ViewType
                        .VIEW_TYPE_INVITE_FRIEND);
                break;
            case "del_order":
                Utils.setViewTypeForTag(button, ViewType
                        .VIEW_TYPE_DEL_ORDER);
                break;
            case "delay_order":
                Utils.setViewTypeForTag(button, ViewType
                        .VIEW_TYPE_DELAY_ORDER);
                break;
        }
    }
}
