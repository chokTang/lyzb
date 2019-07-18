package com.szy.yishopcustomer.Interface;

import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by Smart on 2017/9/1.
 */
public class OnOrderButtonListener{
    public LinearLayout linearlayout_buttons;

    protected OnOrderButtonListener(LinearLayout linearLayout){
        linearlayout_buttons = linearLayout;
    }

    public void setButtons(Button button){
        if(linearlayout_buttons != null) {
            linearlayout_buttons.addView(button);
        }
    }

    public void cancel_order(Button button){
        setButtons(button);
    }

    public void view_logistics(Button button){
        setButtons(button);
    }

    public void to_pay(Button button){
        setButtons(button);
    }

    public void confirm_order(Button button){
        setButtons(button);
    }

    public void to_comment(Button button){
        setButtons(button);
    }

    public void add_comment(Button button){
        setButtons(button);
    }

    public void commented(Button button){
        setButtons(button);
    }

    public void ot_fight_group(Button button){
        setButtons(button);
    }

    public void del_order(Button button){
        setButtons(button);
    }
    public void delay_order(Button button){
        setButtons(button);
    }
}