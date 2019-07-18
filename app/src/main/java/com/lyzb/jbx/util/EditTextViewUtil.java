package com.lyzb.jbx.util;

import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by :TYK
 * Date: 2019/4/29  11:56
 * Desc:
 */
public class EditTextViewUtil {
    /**
     * 设置相关监听器
     */
    public static void setListener(EditText editText){
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return (event.getKeyCode()==KeyEvent.KEYCODE_ENTER);
            }
        });


    }


}
