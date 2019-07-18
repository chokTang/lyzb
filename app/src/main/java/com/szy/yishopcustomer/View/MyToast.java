package com.szy.yishopcustomer.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;

import com.lyzb.jbx.R;

/**
 * Created by Smart on 2017/7/19.
 */

public class MyToast{

    public static Toast toast;

    public static void warning(Context context, String text){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.toast_view_warning, null);
        TextView t = (TextView) view.findViewById(R.id.toast_text);
        t.setText(text);
        if (toast != null) {
            toast.cancel();
        }
        toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }
}
